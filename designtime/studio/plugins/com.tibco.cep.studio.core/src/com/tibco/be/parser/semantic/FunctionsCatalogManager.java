package com.tibco.be.parser.semantic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.xml.sax.InputSource;

import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.FunctionsCategoryChangeListener;
import com.tibco.be.model.functions.IFunctionCatalogProvider;
import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.FunctionsCategoryImpl;
import com.tibco.be.model.functions.impl.JavaAnnotationLookup;
import com.tibco.be.model.functions.impl.JavaStaticFunctionsFactory.IFunctionClassLoader;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.rt.CoreLibPathProvider;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.IStudioProjectConfigurationChangeListener;
import com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationCache;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationChangeEvent;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;
import com.tibco.cep.studio.common.configuration.update.IStudioProjectConfigurationDelta;
import com.tibco.cep.studio.common.configuration.update.StudioProjectConfigurationDelta;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.FunctionsHelpBundleMananger;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.StudioUIAgent;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.functions.annotations.AnnotationHandler;
import com.tibco.cep.studio.core.functions.annotations.AnnotationInfoCollector;
import com.tibco.cep.studio.core.functions.annotations.CatalogInfo;
import com.tibco.cep.studio.core.functions.annotations.CategoryInfo;
import com.tibco.cep.studio.core.functions.annotations.FunctionCatalogHandler;
import com.tibco.cep.studio.core.functions.annotations.FunctionInfo;
import com.tibco.cep.studio.core.functions.model.EMFModelFunctionsFactory;
import com.tibco.cep.studio.core.functions.model.EMFModelJavaFunction;
import com.tibco.cep.studio.core.functions.model.EMFOntologyFunctionResourceChangeListener;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.JavaElement;
import com.tibco.cep.studio.core.index.model.SharedJavaElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.cep.studio.core.util.packaging.impl.PackagingHelper;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;

import javassist.ClassPool;
import javassist.LoaderClassPath;

public class FunctionsCatalogManager {

	private class FunctionsCatalogResourceChangeVisitor implements IStudioProjectConfigurationChangeListener, IResourceChangeListener {

		@Override
		public void configurationChanged(
				StudioProjectConfigurationChangeEvent event) {
			StudioProjectConfigurationDelta delta = event.getDelta();
			String projectName = delta.getAffectedChild().getName();
			if (delta.getType() == IStudioProjectConfigurationDelta.REMOVED) {
				removeCustomRegistryEntry(projectName);
			} else {
				try {
					reloadCustomRegistryEntry(projectName, delta.getAffectedChild());
				} catch (Exception e) {
					StudioCorePlugin.log(e.getMessage(), e);
				}
			}
		}

		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			try {
				if (IResourceChangeEvent.POST_CHANGE == event.getType()
						|| IResourceChangeEvent.PRE_DELETE == event.getType()) {

					IResourceDelta delta = event.getDelta();
					if (delta == null) return;
					IResource resource = delta.getResource();				
					if (IResourceChangeEvent.PRE_DELETE == event.getType()) {
						return;
					}
					delta.accept(new CustomFunctionsUpdateVisitor());

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		private class CustomFunctionsUpdateVisitor implements IResourceDeltaVisitor {

			public boolean visit(IResourceDelta delta) throws CoreException {
				try {
					IResource resource = delta.getResource();
					if (resource instanceof IFile) {
						IFile file = (IFile) resource;
						processFile(file, delta);
					} else if (resource instanceof IFolder && delta.getKind() == IResourceDelta.REMOVED) {
						removeFolder((IFolder) resource, delta.getKind());
						return false;
					} else if (resource instanceof IProject && delta.getKind() == IResourceDelta.REMOVED) {
						removeCustomRegistryEntry(resource.getName());
						FunctionsCatalog catalog = FunctionsCatalogManager.getInstance().getCustomRegistry(resource.getName());
						if(catalog==null) 
						{
							//The project is deleted
							return false;
						}
						catalog.deregister(FunctionsCatalogManager.getInstance().getCustomCategoryName(resource.getName()));
						StudioCorePlugin.getDefault().fireCatalogChangedEvent(null);
						return false;
					}
				} catch (Exception e){
					e.printStackTrace();
					StudioCorePlugin.debug(e.getMessage());
//					StudioCorePlugin.log(e);
					return false;
				}
				return true;
			}

			private void removeFolder(IFolder file, int deltaKind) throws Exception {
				FunctionsCatalog factory = getCustomRegistry(file.getProject().getName());
				if (factory == null) {
					return;
				}
				if (deltaKind == IResourceDelta.REMOVED) {
					//					factory.removeFolder(file);
					return;
				}

			}

			private void processFile(IFile file, IResourceDelta delta) throws Exception {
				if(!CommonIndexUtils.isJavaType(file.getFileExtension())){
					return;
				}
				final String projectName = file.getProject().getName();
				final StudioProjectConfiguration spc = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
				FunctionsCatalog factory = getCustomRegistry(projectName);
				if (factory == null) {
					return;
				}
				if (delta.getKind() == IResourceDelta.REMOVED) {
					if(CommonIndexUtils.isJavaType(file.getFileExtension())){
						// if a java source is removed reload custom registry
						reloadCustomRegistryEntry(projectName, spc);
					}
//					factory.removeElement(file);
					return;
				}
				if (delta.getFlags() == IResourceDelta.MARKERS) {
					// no need to update function
					return;
				}
				
				
				
				IndexUtils.waitForUpdateAll();
				DesignerElement element = IndexUtils.getElement(file);
				if (element == null) {
					StudioCorePlugin.debug("Could not get element from file "+file.getName());
				}
				if (element instanceof EntityElement) {
					Entity entity = ((EntityElement) element).getEntity();
					if (entity instanceof JavaSource) {
						// if a java source is changed the whole custom registry needs to be updated
						// because custom functions contributed by custom function jars and java sources 
						// can share same category namespaces a.b.f1(tp jar) and a.b.f2(java source) 
						// so it becomes difficult to determine what changed
						reloadCustomRegistryEntry(projectName, spc);
					}
				}
			}

		}

	}
	
	public FunctionsCategory addPredicate(Predicate predicate, String fullPath, FunctionsCategory root) throws Exception {

		String names[] = fullPath.split("\\(|\\)|\\.");
		FunctionsCategory foundOne = root;
		String ns = null;

		if (names.length >= 0) {
			for (int i = 0; i < names.length; i++) {
				FunctionsCategory fc = foundOne.getSubCategory(ExpandedName.makeName(names[i]));
				if (fc == null) {
					if (ns != null) {
						fc = new FunctionsCategoryImpl(ExpandedName.makeName(ns, names[i]));
					} else {
						fc = new FunctionsCategoryImpl(ExpandedName.makeName(names[i]));
					}

					foundOne.registerSubCategory(fc);
					if (ns != null) {
						ns += '.'+fc.getName().getLocalName();
					} else {
						ns = fc.getName().getLocalName();
					}
				}
				foundOne = fc;
			}
			foundOne.registerPredicate(predicate);
		}

		return foundOne;
	}

	void javaFunctionAdded(JavaSource javaSource, IPath projectPath, List<SharedJavaElement> sharedElements) throws Exception {
		// Get Annotation Info
		FunctionsCatalog customRegistry = FunctionsCatalogManager.getInstance().getCustomRegistry(javaSource.getOwnerProjectName());
		StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(javaSource.getOwnerProjectName());
		String classPath = CoreLibPathProvider.getCoreLibPathsAsString() + File.pathSeparator + getThirdPartyJarsPathAsString(configuration);
		
		String projectClassPath = PackagingHelper.getProjectPath(configuration);
		if (projectClassPath != null && projectClassPath.trim().length() > 0) {
			classPath += File.pathSeparator + projectClassPath;
		}
		
		EList<JavaSourceFolderEntry> javaSourceFolderEntries = configuration.getJavaSourceFolderEntries();
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(javaSource.getOwnerProjectName());
		
		IPath location=null;
		String projLoc=null;
		if (project.isAccessible()) {
			location = project.getLocation().removeLastSegments(1);
			projLoc = location.toOSString();
		}else{
			String prop = System.getProperty("studio.console","false");
			if (!prop.equalsIgnoreCase("false")){
				if(projectPath!=null){
					location = projectPath.removeLastSegments(1);
					projLoc = location.toOSString();
				}
			}
		}
		for (JavaSourceFolderEntry javaSourceFolderEntry : javaSourceFolderEntries) {
			String jsFolderEntry = javaSourceFolderEntry.getPath(true);
			if (jsFolderEntry.contains(Path.JAVA_SRC_PATH)) {
				classPath += File.pathSeparator + projLoc + jsFolderEntry;
			} else {
				classPath += File.pathSeparator + jsFolderEntry;
			}
		}
		AnnotationHandler<?>[] handlers = new AnnotationHandler[] { new FunctionCatalogHandler() };
		AnnotationInfoCollector collector = AnnotationInfoCollector.newInstance(classPath, handlers);
		AnnotationInfoCollector.JavaFileInfo info = AnnotationInfoCollector.JavaFileInfo.createJavaFileInfo(javaSource);
		collector.addFile(info);

		for (SharedJavaElement sharedJavaElement : sharedElements) {
			info = AnnotationInfoCollector.JavaFileInfo.createJavaFileInfo(sharedJavaElement.getJavaSource());
			collector.addFile(info);
		}

		Map<Class<?>, Object> result = collector.collect();
		if (result.isEmpty())
			return;

		CatalogInfo catalogInfo = (CatalogInfo) result.get(CatalogInfo.class);

		String key = catalogInfo.getName();
		String catalogName = key;// + " Functions";
		FunctionsCategory category = customRegistry.getCatalog(catalogName);
		if (category == null) {
			category = new FunctionsCategoryImpl(ExpandedName.makeName(catalogName));
			customRegistry.register(catalogName, category);
		}
		for (CategoryInfo categoryInfo : catalogInfo.getCategories()) {
			for (FunctionInfo fnInfo : categoryInfo.getFunctions()) {
				String catName = categoryInfo.getCategory();
				ExpandedName rfName = ExpandedName.makeName(categoryInfo.getCategory(), fnInfo.getName());
				EMFModelJavaFunction fn = new EMFModelJavaFunction(javaSource, rfName, fnInfo.getName(), categoryInfo, fnInfo);
				String fullPath = catName;
				addPredicate(fn, fullPath, category);
			}
		}
	}
	
	private String getThirdPartyJarsPathAsString(StudioProjectConfiguration configuration) {
		if (configuration != null) {
			EList<ThirdPartyLibEntry> entries = configuration.getThirdpartyLibEntries();
			List<String> tpURLS = new ArrayList<String>();

			for (ThirdPartyLibEntry bpe : entries) {		
				String filePath = bpe.getPath(bpe.isVar());
				tpURLS.add(filePath);
			}
			return String.join(File.pathSeparator, tpURLS);
		} else {
			return "";
		}
	}
	
	public static class ObservableMapStore<K,V> extends Observable implements Map<K,V>{
		public static enum ObservableActions{
			QUERY,
			GET,
			PUT,
			REMOVE;
		}
		private Map<K,V> delegate;
		
		public ObservableMapStore() {
			delegate = new HashMap<K,V>();
		}

		@Override
		public int size() {
			try{
				setChanged();
				return delegate.size();
			}finally {
				notifyObservers(ObservableActions.QUERY);
				clearChanged();
			}
		}

		@Override
		public boolean isEmpty() {
			try{
				setChanged();
				return delegate.isEmpty();
			} finally {
				notifyObservers(ObservableActions.QUERY);
				clearChanged();
			}
		}

		@Override
		public boolean containsKey(Object key) {
			try {
				setChanged();
				return delegate.containsKey(key);
			} finally {
				notifyObservers(ObservableActions.QUERY);
				clearChanged();
			}
		}

		@Override
		public boolean containsValue(Object value) {
			try {
				setChanged();
				return delegate.containsValue(value);
			} finally {
				notifyObservers(ObservableActions.QUERY);
				clearChanged();
			}
		}

		@Override
		public V get(Object key) {
			try {
				setChanged();
				return delegate.get(key);
			} finally {
				notifyObservers(ObservableActions.GET);
				clearChanged();
			}
		}

		@Override
		public V put(K key, V value) {
			try {
				setChanged();
				return delegate.put(key, value);
			} finally {
				notifyObservers(ObservableActions.PUT);
				clearChanged();
			}
		}

		@Override
		public V remove(Object key) {
			try {
				setChanged();
				return delegate.remove(key);
			} finally {
				notifyObservers(ObservableActions.REMOVE);
				clearChanged();
			}
		}

		@Override
		public void putAll(Map<? extends K, ? extends V> m) {
			try {
				delegate.putAll(m);
				setChanged();
			} finally {
				notifyObservers(ObservableActions.PUT);
				clearChanged();
			}
		}

		@Override
		public void clear() {
			try {
				setChanged();
				delegate.clear();
			} finally {
				notifyObservers(ObservableActions.REMOVE);
				clearChanged();
			}
		}

		@Override
		public Set<K> keySet() {
			try {
				setChanged();
				return Collections.unmodifiableSet(delegate.keySet());
			} finally {
				notifyObservers(ObservableActions.QUERY);
				clearChanged();
			}
		}

		@Override
		public Collection<V> values() {
			try {
				setChanged();
				return Collections.unmodifiableCollection(delegate.values());
			} finally {
				notifyObservers(ObservableActions.QUERY);
				clearChanged();
			}
		}

		@Override
		public Set<java.util.Map.Entry<K, V>> entrySet() {
			try {
				HashSet<Entry<K,V>> immutableSet = new HashSet<Entry<K,V>>();
				for(java.util.Map.Entry<K, V> entry:delegate.entrySet()){
					SimpleImmutableEntry<K,V> imEntry = new AbstractMap.SimpleImmutableEntry<K,V>(entry.getKey(),entry.getValue());
					immutableSet.add(imEntry);
				}
				setChanged();
				return Collections.unmodifiableSet(immutableSet);
			} finally {
				notifyObservers(ObservableActions.QUERY);
				clearChanged();
			}
		}
		
	}
	public static final String ONTOLOGY_FUNCTIONS = "Ontology Functions";
	public static final String ONTOLOGY			  = "Ontology";
	public static final String CUSTOM_FUNCTIONS   = "Custom Functions";
	public static final String CUSTOM			  = "Custom";
	private static final String FUNCTION_CATALOG_PROVIDER = "com.tibco.cep.rt.functionCatalogProvider";
	private static final String FUNCTION_CATALOG_PROVIDER_ATTR = "provider";
	
    protected static FunctionsCatalog registry = null;
    protected static FunctionsCatalog ontology_registry = null;
    protected static ObservableMapStore<String/*projectName*/, FunctionsCatalog> custom_registry = null;

    private static FunctionsCatalogManager fInstance;
    private static FunctionsCatalogResourceChangeVisitor fConfigChangeListener;
	private IFunctionCatalogProvider[] fCatalogProviders;
	private Map<String,EMFModelFunctionsFactory> modelFunctionsFactoryMap = new HashMap<String,EMFModelFunctionsFactory>();
    
    private HashMap<String, HashSet<String>> fLoggedErrors = new HashMap<String, HashSet<String>>();
	private IPath projPath;
	List<URL> extendedClassPath = new ArrayList<>();
    
	private FunctionsCatalogManager() {
		super();
	}

	public synchronized static FunctionsCatalogManager getInstance() {
		if (fInstance == null) {
			fInstance = new FunctionsCatalogManager();
			fConfigChangeListener = fInstance.new FunctionsCatalogResourceChangeVisitor();
			StudioProjectConfigurationManager.getInstance().addConfigurationChangeListener(fConfigChangeListener, 0);
			ResourcesPlugin.getWorkspace().addResourceChangeListener(fConfigChangeListener);
		}
		return fInstance;
	}
	
	public synchronized void clearOntologyCategory(String projectName) {
		String catalogName = getOntologyCatalogName(projectName);
        if (getOntologyRegistry().getCatalog(catalogName) != null) {
            try {
            	getOntologyRegistry().deregister(catalogName);
            	EMFOntologyFunctionResourceChangeListener.getInstance().removeModelFactory(projectName);
            	this.modelFunctionsFactoryMap.remove(catalogName);
			} catch (Exception e) {
				e.printStackTrace();
			}
        } else if (this.modelFunctionsFactoryMap.containsKey(catalogName)) {
        	// a backup just in case the category was deregistered but the model functions factory map was not
        	EMFOntologyFunctionResourceChangeListener.getInstance().removeModelFactory(projectName);
        	this.modelFunctionsFactoryMap.remove(catalogName);
        }
	}

	public synchronized FunctionsCategory getOntologyCategory(String projectName) {
		String catalogName = getOntologyCatalogName(projectName);
        if (getOntologyRegistry().getCatalog(catalogName) == null) {
            try {
            	getModelFunctionsFactory(projectName);
			} catch (Exception e) {
				StudioCorePlugin.log(e);
			}
        }
        return getOntologyRegistry().getCatalog(catalogName);
	}
	
	/**
	 * @param projectName
	 * @return
	 * @throws Exception 
	 */
	public synchronized EMFModelFunctionsFactory getModelFunctionsFactory(String projectName) throws Exception {
		String catalogName = getOntologyCatalogName(projectName);
		if(!this.modelFunctionsFactoryMap.containsKey(catalogName)){
			EMFModelFunctionsFactory modelFunctionsfactory = new EMFModelFunctionsFactory(getOntologyRegistry(), projectName);
			modelFunctionsfactory.loadModelFunctions();
			EMFOntologyFunctionResourceChangeListener.getInstance().addModelFactory(projectName, modelFunctionsfactory);
			this.modelFunctionsFactoryMap.put(catalogName,modelFunctionsfactory);
			return modelFunctionsfactory;
		}  else {
			return this.modelFunctionsFactoryMap.get(catalogName);
		}
		
	}
	
	public synchronized FunctionsCatalog getOntologyRegistry() {
        if (ontology_registry == null) {
        	ontology_registry = new FunctionsCatalog();
        }
		return ontology_registry;
	}

	public synchronized ObservableMapStore<String, FunctionsCatalog> getCustomRegistry() {
		if (custom_registry == null) {
			custom_registry = new ObservableMapStore<String, FunctionsCatalog>();
			try {
				loadCustomRegistry();
			} catch (Exception e) {
				e.printStackTrace();
				StudioCorePlugin.debug(e.getMessage());
//				StudioCorePlugin.log(e);
			}
		}
		return custom_registry;
	}
	
	public synchronized FunctionsCatalog getCustomRegistry(String projectName) throws Exception {
		FunctionsCatalog catalog = getCustomRegistry().get(projectName);
		if (catalog == null) {
			//TODO: need to check if the project exists
			if(ResourcesPlugin.getWorkspace().getRoot().findMember(projectName) == null) {
				return null;
			}
			StudioProjectConfiguration projectConfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
			if(projectConfig == null){
				//projectConfig would be null in case of non BE proejcts
				return null;
			}
			createCustomRegistryEntry(projectName, projectConfig);
		}
		return getCustomRegistry().get(projectName);
	}
	
	private void loadCustomRegistry() throws Exception {
		StudioProjectConfigurationCache map = StudioProjectConfigurationManager.getInstance().getConfigurationsCache();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			StudioProjectConfiguration configuration = map.get(key);
			createCustomRegistryEntry(key, configuration);
		}
	}

	public void reloadCustomRegistryEntry(String projectName,
			StudioProjectConfiguration configuration) throws Exception {
		fLoggedErrors.remove(projectName);
		removeCustomRegistryEntry(projectName);
		createCustomRegistryEntry(projectName, configuration);
		// also reload the function help bundle for this project
		FunctionsHelpBundleMananger.getInstance().reloadHelpBundle(projectName);
		((StudioUIAgent)StudioCorePlugin.getUIAgent(projectName)).reloadFunctionResolver();
	}

	private void removeCustomRegistryEntry(String projectName) {
		if (custom_registry != null) {
			custom_registry.remove(projectName);
		}
	}

	private void createCustomRegistryEntry(String projectName,
			StudioProjectConfiguration configuration) throws Exception {
		EList<CustomFunctionLibEntry> entries = configuration.getCustomFunctionLibEntries();
		if (entries.size() == 0) {
			FunctionsCatalog catalog = getCustomRegistry().get(projectName);
			if (catalog == null) {
				// still need to create a catalog for source based custom functions on the buildpath
				catalog = new FunctionsCatalog();
				getCustomRegistry().put(projectName, catalog);
			}
		}
		for (CustomFunctionLibEntry bpe : entries) {		

			String filePath = bpe.getPath(bpe.isVar());
			File file = new File(filePath);
			if (!file.exists()) {
				String errorMsg = "Custom function jar "+filePath+ " does not exist.  Ensure the build path for project "+configuration.getName()+" is correct";
				Status status = new Status(Status.WARNING, StudioCorePlugin.PLUGIN_ID, errorMsg);
				logStatus(configuration.getName(), status);
				return;
			}
			JarFile archiveFile = new JarFile(file);
			XiNode document = null;
			try {
				document = FunctionsCatalogManager.parseArchiveFile(archiveFile);
			} catch (Exception e) {
				throw new Exception("Could not read custom functions from "+archiveFile.getName()+".  Ensure that it has a functions.catalog file and that it is properly formatted", e);
			}
			if (document == null) {
				throw new Exception("Could not read custom functions from "+archiveFile.getName()+".  Ensure that it has a functions.catalog file and that it is properly formatted");
			}
			FunctionsCatalog catalog = getCustomRegistry().get(projectName);
			if (catalog == null) {
				catalog = new FunctionsCatalog();
				getCustomRegistry().put(projectName, catalog);
			}
			//	            final String catalogName = XiChild.getChild(document, ExpandedName.makeName("catalog")).getAttributeStringValue(ExpandedName.makeName("name"));
			//				unfortunately this presupposes that the custom classes are already on the classpath...
			JavaCustomFunctionsFactory.loadFunctionCategoriesFromDocument(catalog, document, archiveFile,configuration,null);
			//				JavaStaticFunctionsFactory.loadFunctionCategoryURLs(custom_registry, urlList);
		}
		loadThirdPartyCustomFunctions(projectName, configuration);
		loadWorkspaceCustomFunctions(projectName,projPath);
	}
	
	private void logStatus(String projectName, Status status) {
		String errorMsg = status.getMessage();
		HashSet<String> hashSet = fLoggedErrors.get(projectName);
		if (hashSet == null) {
			hashSet = new HashSet<>();
			fLoggedErrors.put(projectName, hashSet);
		}
		if (!hashSet.contains(errorMsg)) {
			hashSet.add(errorMsg);
			StudioCorePlugin.log(status);
		}
	}

	/**
	 * This function loads the catalog functions from third party libraries which dont use functions.catalog using annotation lookup 
	 * @param projectName
	 * @param configuration
	 * @throws Exception
	 */
	public  void loadThirdPartyCustomFunctions(String projectName,StudioProjectConfiguration configuration) throws Exception {
		EList<ThirdPartyLibEntry> entries = configuration.getThirdpartyLibEntries();
		FunctionsCatalog catalog = getCustomRegistry().get(projectName);
		if (catalog == null) {
			if (entries.size() == 0) {
				// still need to create a catalog for source based custom
				// functions on the buildpath
				catalog = new FunctionsCatalog();
				getCustomRegistry().put(projectName, catalog);
			}
		}
		List<URL> tpURLS = new ArrayList<URL>();
		for (ThirdPartyLibEntry bpe : entries) {		

			String filePath = bpe.getPath(bpe.isVar());
			File file = new File(filePath);
			if (!file.exists()) {
				Status status = new Status(Status.WARNING, StudioCorePlugin.PLUGIN_ID, "Third party library jar "+filePath+ " does not exist.  Ensure the build path for project "+configuration.getName()+" is correct");
				logStatus(projectName, status);
				return;
			}
			tpURLS.add(file.toURI().toURL());
			
		}
		if(tpURLS.isEmpty())
			return;
		
		loadThirdPartyCustomFunctions(catalog, tpURLS);
		
	}

	private void loadThirdPartyCustomFunctions(FunctionsCatalog catalog, List<URL> tpURLS) throws Exception {
		/**
		 * Since these are third party jars coming from folders outside the project we have to use the URL classloader
		 */
		List<URL> paths = new ArrayList<URL>();
		paths.addAll(Arrays.asList(CoreLibPathProvider.getCoreLibPaths(null)));
		paths.addAll(tpURLS);
		ClassPool parent = JavaAnnotationLookup.createClassPoolFromClassPath(paths.toArray(new URL[0]));
		final URLClassLoader urlCldr = new URLClassLoader(paths.toArray(new URL[0]));
		ClassPool cp = JavaAnnotationLookup.createClassPoolFromClassPath(tpURLS.toArray(new URL[0]));
		cp.insertClassPath(new LoaderClassPath(com.tibco.cep.rt.Activator.class.getClassLoader()));
		ConcurrentHashMap<URL, Boolean> loadedURLS = new ConcurrentHashMap<URL, Boolean>();
		final IFunctionClassLoader fcldr = new IFunctionClassLoader() {
			
			
			@Override
			public ClassLoader getLoader() {
				return urlCldr;
			}

			@Override
			public Class<?> getClass(String className) throws ClassNotFoundException {
				try {
					return urlCldr.loadClass(className);
				} catch(ClassNotFoundException ce) {
					return urlCldr.getClass().getClassLoader().loadClass(className);
				}
			}
		};
		FunctionsCatalogManager.waitForStaticRegistryUpdates();
		File jsonFile = new File(IndexUtils.getIndexLocationBaseDir(), "_annotations.idx"); // use underscore in front to distinguish between project index files
		Map<String, Object> jsonVals = new LinkedHashMap<String, Object>();
		if (jsonFile.exists()) {
			JavaAnnotationLookup.readAnnotationsFile(jsonFile, jsonVals);
		}
		JavaAnnotationLookup.processCachedURLs(loadedURLS, jsonVals, tpURLS);
		JavaAnnotationLookup.lookupCategories(catalog, tpURLS.toArray(new URL[0]), fcldr, cp, loadedURLS,true);
		jsonVals.clear();
		JavaAnnotationLookup.writeAnnotationsFile(jsonFile, jsonVals, loadedURLS);
	}
	
	
	
	/**
	 * This function loads the workspace custom functions declared in java source files using {@link BEFunction} annotations
	 * @param projectName
	 * @param projectPath 
	 * @param configuration
	 * @throws Exception
	 */
	public void loadWorkspaceCustomFunctions(String projectName, IPath projectPath) throws Exception {
		projPath=projectPath;
		List<DesignerElement> allJavaSource = IndexUtils.getAllElements(projectName, ELEMENT_TYPES.JAVA_SOURCE);
		List<SharedJavaElement> sharedElements = new ArrayList<>();
		for (DesignerElement el : allJavaSource) {
			if (el instanceof SharedJavaElement) {
				sharedElements.add((SharedJavaElement) el);
			}
		}

		for (DesignerElement el : allJavaSource) {
			if (el instanceof JavaElement) {
				javaFunctionAdded(((JavaElement) el).getJavaSource(),projectPath, sharedElements);
			}
		}
	}

	public static XiNode parseArchiveFile(JarFile archiveFile) throws Exception {
		ZipEntry entry = archiveFile.getEntry("functions.catalog");
		if (entry != null) {
			InputStream inputStream = archiveFile.getInputStream(entry);
			XiNode document = XiParserFactory.newInstance().parse(new InputSource(inputStream));
			return document;
		}
		return null;
	}

	public synchronized FunctionsCatalog getStaticRegistry() {
		if (registry == null) {

			registry = new FunctionsCatalog();
			LoadStaticRegistryJob loadJob = new LoadStaticRegistryJob(getFunctionCatalogProviders(),registry);
			loadJob.schedule();
			/**
			 * In console commandline mode the job exec needs to be synchronous
			 */
			String prop = System.getProperty("studio.console","false");
			if (!prop.equalsIgnoreCase("false")){
				try {
					loadJob.join();
				} catch (InterruptedException e) {
					StudioCorePlugin.log(e);
				}
			}
			
		}
		return registry;
	}
	
	public static class LoadStaticRegistryJob extends Job {

		private IFunctionCatalogProvider[] providers;
		private FunctionsCatalog catalog;
		public static Object LOAD_STATIC_REGISTRY_FAMILY = new Object();
		
		public LoadStaticRegistryJob(IFunctionCatalogProvider[] providers,FunctionsCatalog registry) {
			super("Load Static Registry"); // //$NON-NLS-1$
			this.providers = providers;
			this.catalog = registry;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				URL[] paths = CoreLibPathProvider.getCoreLibPaths(null);
				
				ClassPool cp = JavaAnnotationLookup.createClassPoolFromClassPath(paths);
				ConcurrentHashMap<URL, Boolean> loadedURLS = new ConcurrentHashMap<URL, Boolean>();

				File jsonFile = new File(IndexUtils.getIndexLocationBaseDir(), "_annotations.idx"); // use underscore in front to distinguish between project index files

				Map<String, Object> jsonVals = new LinkedHashMap<String, Object>();

				if (jsonFile.exists()) {
					JavaAnnotationLookup.readAnnotationsFile(jsonFile, jsonVals);
				}

				for (final IFunctionCatalogProvider provider : providers) {
					try {
						provider.loadCatalogs(catalog);
					} catch (java.lang.Exception e) {
						StudioCorePlugin.log("Failed to load from catalog provider:" + provider, e);
					}
					List<URL> jarURLs = new ArrayList<>();
					jarURLs.addAll(fInstance.extendedClassPath);
					jarURLs.addAll(Arrays.asList(CoreLibPathProvider.getCoreLibPaths(provider.getClass().getClassLoader())));
					final IFunctionClassLoader fcldr = new IFunctionClassLoader() {
						@Override
						public Class<?> getClass(String className) throws ClassNotFoundException {
							return provider.getClass().getClassLoader().loadClass(className);
						}

						@Override
						public ClassLoader getLoader() {
							return provider.getClass().getClassLoader();
						}

					};
					JavaAnnotationLookup.processCachedURLs(loadedURLS, jsonVals, jarURLs);
					JavaAnnotationLookup.lookupCategories(catalog,  jarURLs.toArray(new URL[0]), fcldr, cp, loadedURLS, false);
				}
				try {
					jsonVals.clear();
					JavaAnnotationLookup.writeAnnotationsFile(jsonFile, jsonVals, loadedURLS);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				// StudioCorePlugin.log(e);
				return new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, "Failed to load Static Registry", e);
			}
			return Status.OK_STATUS;
		}
		
		@Override
		public boolean belongsTo(Object family) {
			return LOAD_STATIC_REGISTRY_FAMILY.equals(family);
		}
		
	}
	
	public static void waitForStaticRegistryUpdates() {
        IJobManager jobManager = Job.getJobManager();
        try {
			jobManager.join(LoadStaticRegistryJob.LOAD_STATIC_REGISTRY_FAMILY, null);
		} catch (OperationCanceledException e){
			e.printStackTrace();
		} catch(InterruptedException e) {
			// thread was interrupted, recursively call to re-join the job so we don't return before the job completes
			waitForStaticRegistryUpdates();
		}
	}

	
	
	
	public IFunctionCatalogProvider[] getFunctionCatalogProviders() {
		if (fCatalogProviders == null) {
			try {
				List<IFunctionCatalogProvider> list = new ArrayList<IFunctionCatalogProvider>();
				IConfigurationElement[] extensions = getFunctionCatalogProviderElements();
				for (int i = 0; i < extensions.length; i++) {
					IConfigurationElement element = extensions[i];
					final Object o = element.createExecutableExtension(FUNCTION_CATALOG_PROVIDER_ATTR);
					if (o != null && o instanceof IFunctionCatalogProvider) {
						list.add((IFunctionCatalogProvider) o);
					}
				}
				IFunctionCatalogProvider[] providers = new IFunctionCatalogProvider[list.size()];
				list.toArray(providers);
				fCatalogProviders = providers;
			} catch (Exception e) {
				StudioCorePlugin.log(e);
			}
		}
		return fCatalogProviders;
	}
	
	public IConfigurationElement[] getFunctionCatalogProviderElements() {
        IExtensionRegistry reg = Platform.getExtensionRegistry();
        if (reg != null) {
        	return reg.getConfigurationElementsFor(FUNCTION_CATALOG_PROVIDER);
        }
		return new IConfigurationElement[0];
	}

	public void addCatalogChangeListener(String catalogName, FunctionsCategoryChangeListener listener) {
		getOntologyRegistry().addCatalogChangeListener(getOntologyRegistry().getCatalog(getOntologyCatalogName(catalogName)), listener);
	}
	public void addCustomCategoryChangeListener(Observer o) {
		getCustomRegistry().addObserver(o);
	}
	public void removeCustomCategoryChangeListener(Observer o) {
		getCustomRegistry().deleteObserver(o);
	}

	public String getOntologyCategoryName(String projectName) {
		return projectName+"."+ONTOLOGY;
	}
	
	public String getCustomCategoryName(String projectName) {
		return projectName+"."+CUSTOM;
	}
	
	public String getOntologyCatalogName(String projectName) {
		return ONTOLOGY_FUNCTIONS+projectName;
	}
	
	public void loadFunctionCatalogFromClasspath(String projectName,String classsPath) throws Exception {
		if (classsPath == null || projectName == null) {
			return;
		}
		List<URL> jarURLs = new ArrayList<>();
		FunctionsCatalog catalog = getCustomRegistry().get(projectName);
		if (catalog == null) {
			catalog = new FunctionsCatalog();
			getCustomRegistry().put(projectName,catalog);
		}
		jarURLs.addAll(Arrays.asList(CoreLibPathProvider.getCoreLibPaths(null)));
		String classpathEntries[] = classsPath.split(File.pathSeparator);
		for (String classpathEntry : classpathEntries) {
			File jFile = new File(classpathEntry);
			if(jFile.exists()){
				jarURLs.add(jFile.toURI().toURL());
			}
		}
		
		loadThirdPartyCustomFunctions(catalog, jarURLs);
		
	}
	public void loadFunctionCatalogFromClasspath(String classsPath,
			EMFProject project) throws Exception {
		if (project != null) {
			projPath = new org.eclipse.core.runtime.Path(project.getRepoPath());
		}
		if (classsPath == null || project == null) {
			return;
		}
		List<URL> jarURLs = new ArrayList<>();
		FunctionsCatalog catalog = getCustomRegistry().get(
				project.getName());
		if (catalog == null) {
			catalog = new FunctionsCatalog();
			getCustomRegistry().put(project.getName(),
					catalog);
		}
		String classpathEntries[] = classsPath.split(File.pathSeparator);
		for (String classpathEntry : classpathEntries) {
			File jFile = new File(classpathEntry);
			if(jFile.exists()){
				jarURLs.add(jFile.toURI().toURL());
			}
		}
		for (URL url : jarURLs){
			 if (!fInstance.extendedClassPath.contains(url))
				 fInstance.extendedClassPath.add(url);
		}
		jarURLs.addAll(Arrays.asList(CoreLibPathProvider.getCoreLibPaths(null)));
		final URLClassLoader urlCldr = new URLClassLoader(jarURLs.toArray(new URL[0]));
		for (String classpathEntry : classpathEntries) {
			if (classpathEntry.endsWith(".jar")) {
				File jFile = new File(classpathEntry);
				jarURLs.add(jFile.toURI().toURL());
				if (jFile.exists()) {
					try {
						JarFile archiveFile = new JarFile(classpathEntry);
						ZipEntry entry = archiveFile
								.getEntry("functions.catalog");
						if (entry != null) {
							XiNode document = null;
							document = FunctionsCatalogManager.parseArchiveFile(archiveFile);
							
							JavaCustomFunctionsFactory.loadFunctionCategoriesFromDocument(
											catalog, document, archiveFile,
											project.getProjectConfiguration(),urlCldr);
						}
					} catch (IOException e) {

					} catch (Exception e) {
					}
				}
			}
		}
		loadThirdPartyCustomFunctions(catalog, jarURLs);

	}

	

}