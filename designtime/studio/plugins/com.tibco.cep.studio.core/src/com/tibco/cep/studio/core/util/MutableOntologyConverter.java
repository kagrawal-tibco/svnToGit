package com.tibco.cep.studio.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.be.util.SimpleXslTransformer;
import com.tibco.cep.designtime.core.model.archive.ArchiveFactory;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.RuleSet;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.BEWorkspace;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.repo.Workspace;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.repo.provider.SharedArchiveResourceProvider;
import com.tibco.cep.repo.provider.impl.BEArchiveResourceProviderImpl;
import com.tibco.cep.repo.provider.impl.GlobalVariablesProviderImpl;
import com.tibco.cep.repo.provider.impl.JavaArchiveResourceProviderImpl;
import com.tibco.cep.repo.provider.impl.SharedArchiveResourceProviderImpl;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.converter.ChannelConverter;
import com.tibco.cep.studio.core.converter.ConceptConverter;
import com.tibco.cep.studio.core.converter.EARConverter;
import com.tibco.cep.studio.core.converter.EntityConverter;
import com.tibco.cep.studio.core.converter.PropertyDefinitionConverter;
import com.tibco.cep.studio.core.converter.RuleConverter;
import com.tibco.cep.studio.core.converter.RuleFunctionConverter;
import com.tibco.cep.studio.core.converter.RuleSetConverter;
import com.tibco.cep.studio.core.converter.SimpleEventConverter;
import com.tibco.cep.studio.core.converter.StandaloneStateMachineConverter;
import com.tibco.cep.studio.core.converter.TimeEventConverter;
import com.tibco.cep.studio.core.converter.sharedresource.CustomSharedResourceProvider;
import com.tibco.cep.studio.core.converter.sharedresource.GlobalVariableConverter;
import com.tibco.cep.studio.core.converter.sharedresource.SharedResourceElements;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.repo.emf.providers.EMFBEArchiveResourceProvider;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class MutableOntologyConverter {
	
	

//	public static HashMap<Class<? extends Entity>, EntityConverter> registeredConverters = new HashMap<Class<? extends Entity>, EntityConverter>();
	public static List<EntityConverter> registeredConverters = new ArrayList<EntityConverter>();
	
	static {
		registeredConverters.add(new ChannelConverter());
		registeredConverters.add(new ConceptConverter());
		registeredConverters.add(new SimpleEventConverter());
		registeredConverters.add(new TimeEventConverter());
		registeredConverters.add(new PropertyDefinitionConverter());
		registeredConverters.add(new RuleSetConverter());
		registeredConverters.add(new RuleConverter());
		registeredConverters.add(new RuleFunctionConverter());
		
		if(StudioCorePlugin.getDefault().isStateMachineBundleInstalled()){
			registeredConverters.add(new StandaloneStateMachineConverter(new HashMap<String, StateEntity>()));
		}
		
	}
	
	public static boolean convertOntology(File ontologyEarFile, String projectName, File projectPath, IProgressMonitor monitor) throws Exception {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		monitor.setTaskName("Obtaining existing ontology (this may take several seconds)");
		//DefaultMutableOntology ontologyFromEAR = ontologyFromEAR(ontologyEarFile);
		// get BE project
		BEProject beProject = getBEProject(ontologyEarFile);	
		Ontology ontologyFromEAR = beProject.getOntology();		
		Collection<Entity> entities = ontologyFromEAR.getEntities();
		List<com.tibco.cep.designtime.core.model.Entity> convertedEntities = new ArrayList<com.tibco.cep.designtime.core.model.Entity>();
		monitor.setTaskName("Converting Designer Project");
		monitor.beginTask("Converting Designer Project", entities.size()*2+15);
		for (Entity entity : entities) {
			monitor.subTask("converting "+entity.getName());
			EntityConverter converter = getConverter(entity);
			if (converter == null) {
				continue;
			}
			converter.setEntityList(convertedEntities);
			com.tibco.cep.designtime.core.model.Entity convertedEntity = converter.convertEntity(entity, projectName);
			if (convertedEntity != null) {
				convertedEntities.add(convertedEntity);
			}
			monitor.worked(1);
		}
		persistConvertedEntities(convertedEntities, projectPath, monitor);
		
		// process enterprise archives
		// get enterprise archive resource provider
		ArchiveResourceProvider archiveResourceProvider = (ArchiveResourceProvider) getResourceProvider(beProject, ArchiveResourceProvider.class);
		if (archiveResourceProvider != null){			
			SubProgressMonitor spm = new SubProgressMonitor(monitor, 5);
			Map ears = archiveResourceProvider.getEars();
//			SimpleXslTransformer transformer = new SimpleXslTransformer(null);
			processMap(projectName, ears, ontologyEarFile, projectPath, spm);
			processMapOld(projectName, ears, ontologyEarFile, projectPath, spm);
		}	
		boolean isEar = false;
		
		String absPath = ontologyEarFile.getAbsolutePath();
		int index = absPath.lastIndexOf('.');
		String ext = absPath.substring(index+1);	
		if (SharedResourceElements.EAR_FILE_EXTENSION.equalsIgnoreCase(ext)){
			isEar = true;
		}
		
		// process shared resources
		// get shared archive resource provider
//		SharedArchiveResourceProvider sharedArchiveResourceProvider = getSharedarchiveResourceProvider(beProject);
		if (isEar){
			SubProgressMonitor spm = new SubProgressMonitor(monitor, 5);
			copySharedResources(ontologyEarFile, projectPath);
		}	
	
		// This is only needed on an ear import. Otherwise we just do a blind copy of the Global variables.
		// process Global variables 
		if (isEar) {
			GlobalVariablesProvider gvProvider = getGlobalVariablesProvider(beProject);
			if (gvProvider != null){
				SubProgressMonitor spm = new SubProgressMonitor(monitor, 5);
				processGlobalVariables(projectPath, isEar, gvProvider, spm);
			}
		}
		
		
		// process non BE artifacts
		if (!isEar){
			CustomSharedResourceProvider customSharedResourceProvider = new CustomSharedResourceProvider(beProject);
			customSharedResourceProvider.copyResources(projectPath);
		}
		
		monitor.done();
		return true;
	}
	
	private static void copySharedResources(File ontologyEarFile,
			File projectPath) {
		CustomSharedResourceProvider csrp = new CustomSharedResourceProvider(null); // used to filter resources
		InputStream inputStream = null;
		ZipInputStream zipInputStream = null;
		try {
			ZipFile earFile = new ZipFile(ontologyEarFile);
			ZipEntry entry = earFile.getEntry("Shared Archive.sar");
			if (entry != null) {
				inputStream = earFile.getInputStream(entry);
				zipInputStream = new ZipInputStream(inputStream);
				ZipEntry ze = null;
				while ((ze = zipInputStream.getNextEntry()) != null) {
					File srFile = new File(projectPath+"/"+ze.getName());
					int idx = srFile.getName().lastIndexOf('.');
					String ext = "";
					if (idx >= 0) {
						ext = srFile.getName().substring(idx+1);
					}
					if (csrp.shouldIgnoreFile(srFile.getName()) || csrp.shouldIgnoreExtension(ext)) {
						continue;
					}
					if (!srFile.getParentFile().exists()) {
						srFile.getParentFile().mkdirs();
					}
					if (!srFile.exists()) {
						// don't overwrite if it already exists(?)  
						srFile.createNewFile();
						FileOutputStream fout = new FileOutputStream(srFile);
						try {
							for (int c = zipInputStream.read(); c != -1; c = zipInputStream.read()) {
								fout.write(c);
							}
						} catch (Exception e) {
						} finally {
							fout.close();
						}
					}
					zipInputStream.closeEntry();
				}
			}
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (zipInputStream != null) {
					zipInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static GlobalVariablesProvider getGlobalVariablesProvider(BEProject beProject){
		if (beProject == null) return null;
		LinkedHashSet resourceProviders = beProject.getProviderFactory().getProviders();
		if (resourceProviders != null){
			Iterator it = resourceProviders.iterator();
			while (it.hasNext()){
				Object resourceProvider = it.next();
				if (resourceProvider != null && resourceProvider instanceof GlobalVariablesProvider){
					return (GlobalVariablesProvider)resourceProvider;
				}
			}
		}
		return null;
		
	}
	
	private static void processMapOld(String projectName, Map map, File fromLocation, File toLocation, IProgressMonitor monitor) {
		monitor.subTask("Converting Enterprise Archives");
		Collection<EnterpriseArchive> archives = new ArrayList<EnterpriseArchive>();
		if (map.size() > 0) {
			Set keySet = map.keySet();
			for (Object key : keySet) {
				Object object = map.get(key);
				if (object instanceof Map) {
					EnterpriseArchive ear = ArchiveFactory.eINSTANCE.createEnterpriseArchive();
					EARConverter converter = new EARConverter(ear);
					int idx = ((String)key).lastIndexOf('/');
					if (idx >=0) {
						String folder = ((String)key).substring(0, idx+1);
						ear.setFolder(folder);
					} else {
						ear.setFolder("/");
					}
					ear.setOwnerProjectName(projectName);
					converter.convertArchive((String)key, (Map) object);
					setSystemProperties(ear);
					archives.add(ear);
					monitor.worked(1);
				}
			}
		}
		// do not persist archive
//		persistArchive(archives, toLocation, monitor);
		monitor.done();
	}

	private static void setSystemProperties(EnterpriseArchive ear) {
		String projectName = ear.getOwnerProjectName();
		String locationProp = projectName + ".build.location";
		String versionProp = projectName + ".version.number";
		String authorProp = projectName + ".author.name";
		String fileLoc = ear.getFileLocation();
		String version = ear.getVersion();
		String author = ear.getAuthor();
		if (System.getProperty(locationProp) == null) {
			System.setProperty(locationProp, fileLoc);
		}
		if (System.getProperty(versionProp) == null) {
			System.setProperty(versionProp, version);
		}
		if (System.getProperty(authorProp) == null) {
			System.setProperty(authorProp, author);
		}
	}

	private static void processMap(String projectName, Map map, File fromLocation, File toLocation, IProgressMonitor monitor) {
		System.setProperty("javax.xml.parsers.SAXParserFactory", "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");	
		monitor.subTask("Converting Enterprise Archives");
		InputStream inputStream = SimpleXslTransformer.class.getResourceAsStream("ArchiveToCdd.xslt");
		SimpleXslTransformer transformer = null;
		try {
			transformer = new SimpleXslTransformer(inputStream);
		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		}
		if (transformer == null) {
			System.err.println("Could not initialize archive transformer, archives not converted to cdd files");
			return;
		}
		
		if (map.size() > 0) {
			Set keySet = map.keySet();
			for (Object key : keySet) {
				Object object = map.get(key);
				if (object instanceof Map) {
					InputStream is = null;
					OutputStream os = null;
					try {
						is = new FileInputStream(new File(fromLocation, (String) key));
						File outputFile = getOutputCDDFile(toLocation, (String)key);
						outputFile.getParentFile().mkdirs();
						os = new FileOutputStream(outputFile);
						transformer.transform(is, os);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (TransformerConfigurationException e) {
						e.printStackTrace();
					} catch (TransformerException e) {
						e.printStackTrace();
					} finally {
						try {
							if (is != null) {
								is.close();
							}
							if (os != null) {
								os.flush();
								os.close();
							}
						} catch (Exception e2) {
						}
					}
					monitor.worked(1);
				}
			}
		}
		// do not persist archive
		monitor.done();
	}
	
	private static File getOutputCDDFile(File toLocation, String key) {
		int idx = key.lastIndexOf('.');
		String newFileName = key;
		if (idx > 0) {
			newFileName = newFileName.substring(0, idx);
			newFileName += ".cdd";
		}
		return new File(toLocation, newFileName);
	}
	
	private static ResourceProvider getResourceProvider(BEProject beProject, Class providerClass){
		LinkedHashSet resourceProviders = beProject.getProviderFactory().getProviders();
		if (resourceProviders != null){
			Iterator it = resourceProviders.iterator();
			while (it.hasNext()){
				Object resourceProvider = it.next();
				if (resourceProvider != null && resourceProvider.getClass() == providerClass){
					return (ResourceProvider)resourceProvider;
				}
			}
		}
		return null;
	}
	
	private static SharedArchiveResourceProvider getSharedarchiveResourceProvider(BEProject beProject){
		LinkedHashSet resourceProviders = beProject.getProviderFactory().getProviders();
		if (resourceProviders != null){
			Iterator it = resourceProviders.iterator();
			while (it.hasNext()){
				Object resourceProvider = it.next();
				if (resourceProvider != null && resourceProvider.getClass() == SharedArchiveResourceProvider.class){
					return (SharedArchiveResourceProvider)resourceProvider;
				}
			}
		}
		return null;
	}
	
	/* TODO - This is no longer needed
	private static void processSharedResources(File ontologyEarFile,File targetLocation ,
			SharedArchiveResourceProvider sharedArchiveResourceProvider,boolean isEAR, IProgressMonitor monitor)throws Exception{	
		if (sharedArchiveResourceProvider == null) return;
		// chain all converters 
		ISharedResourceConverter startConverter = new JDBCSharedResourceConverter();
		// chain JNDI converter
		ISharedResourceConverter jndiSharedResourceConverter = new JNDISharedResourceConverter();
		startConverter.addNextSharedResourceConverter(jndiSharedResourceConverter);
	
		// chain JMS converter
		ISharedResourceConverter jmsconResourceConverter = new JMSConnectionSharedResourceConverter();
		jndiSharedResourceConverter.addNextSharedResourceConverter(jmsconResourceConverter);
		// chain JMS Application Property Converter
		ISharedResourceConverter jmsAppPropConverter = new JMSPropSharedResourceConeverter();
		jmsconResourceConverter.addNextSharedResourceConverter(jmsAppPropConverter);
		
		// chain HTTP Connection Converter
		ISharedResourceConverter httpconConverter = new HTTPSharedResourceConverter();
		jmsAppPropConverter.addNextSharedResourceConverter(httpconConverter);
		if (isEAR){
			// chain XSD converter
			ISharedResourceConverter xsdConverter = new XSDSharedResourceConverter();
			httpconConverter.addNextSharedResourceConverter(xsdConverter);
		
			// chain XML converter
			ISharedResourceConverter xmlConverter = new XMLSharedResourceConverter();
			xsdConverter.addNextSharedResourceConverter(xmlConverter);
		
			// chain Common converter//it is used to copy all other txt files etc
			ISharedResourceConverter commonConverter = new CommonConverter();
			xmlConverter.addNextSharedResourceConverter(commonConverter);
		}
	
		
		Collection shrResourceURICollection = sharedArchiveResourceProvider.getAllResourceURI();
		Iterator resIt = shrResourceURICollection.iterator();
		while (resIt.hasNext()){
			String relURI = (String)resIt.next();
			if (relURI != null){				
				monitor.subTask("Converting "+relURI);
				startConverter.convert(targetLocation.getAbsolutePath(),relURI, sharedArchiveResourceProvider );
				monitor.worked(1);
			}
		}
		monitor.done();
	}
	*/
	
	private static void processGlobalVariables(File projectPath, boolean isEAR,GlobalVariablesProvider gvProvider, IProgressMonitor monitor) throws Exception{
		Map<String, List<GlobalVariableDescriptor>> gvMap = new HashMap<String, List<GlobalVariableDescriptor>>();
		Collection gvURICollection = gvProvider.getVariables();		
		Iterator resIt = gvURICollection.iterator();
		while (resIt.hasNext()){
			GlobalVariableDescriptor gvDescriptor = (GlobalVariableDescriptor)resIt.next();			
			if (gvDescriptor != null){				
				String path = gvDescriptor.getPath();
				String fullNmae = gvDescriptor.getFullName();
				if (isEAR){
					String qualifiedName = gvDescriptor.getName();
					String localName = getNameFromPath(qualifiedName);
					path = qualifiedName.substring(0,qualifiedName.length()-localName.length());
				}
				List<GlobalVariableDescriptor> glvList = gvMap.get(path);				
				if (glvList == null){
					List<GlobalVariableDescriptor> gvDesList = new ArrayList<GlobalVariableDescriptor>();
					gvDesList.add(gvDescriptor);
					gvMap.put(path, gvDesList);
				}
				else {
					glvList.add(gvDescriptor);
				}
	
			}
		}
		monitor.subTask("Converting Global Variables");
		GlobalVariableConverter.convert(projectPath, isEAR, SharedResourceElements.GLOBAL_VARIABLE_DEFAULT_FOLDER, gvMap);	
		
		monitor.done();
		
	}
	private static String getNameFromPath(String path){
		int index = path.lastIndexOf(SharedResourceElements.SEGMENT_SEPARATOR);
		if (index == -1){
			return path;
		}
		String name = path.substring(index+1);
		return name;
	}
	
	private static void persistArchive(Collection<EnterpriseArchive> convertedArchives, File toLocation, IProgressMonitor monitor) {
		String baseURI = toLocation.getPath();
		Map<?, ?> options = ModelUtils.getPersistenceOptions();
		for (EnterpriseArchive convertedArchive : convertedArchives) {
			monitor.subTask("persisting "+convertedArchive.getName());
			String extension = IndexUtils.getFileExtension(ELEMENT_TYPES.ENTERPRISE_ARCHIVE);
			URI uri = null;
			if (convertedArchive.getFolder() != null) {
				uri = URI.createFileURI(baseURI+File.separator+convertedArchive.getFolder()+File.separator+convertedArchive.getName()+"."+extension);
			} else {
				uri = URI.createFileURI(baseURI+File.separator+convertedArchive.getName()+"."+extension);
			}
			
			ResourceSet resourceSet = new ResourceSetImpl();
			if (resourceSet.getResourceFactoryRegistry().getFactory(uri) == null) {
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
					.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
			}
			Resource resource = resourceSet.createResource(uri);
			if (resource == null)
				continue;
			resource.getContents().add(convertedArchive);
			try {
				resource.save(options);
			} catch (IOException e) {
				e.printStackTrace();
			}
			monitor.worked(1);
		}
		
	}
	
	private static void persistConvertedEntities(
			Collection<com.tibco.cep.designtime.core.model.Entity> convertedEntities, File projectPath, IProgressMonitor monitor) {
		String baseURI = projectPath.getPath();
		Map<?, ?> options = ModelUtils.getPersistenceOptions();
		for (com.tibco.cep.designtime.core.model.Entity convertedEntity : convertedEntities) {
			if (convertedEntity instanceof RuleSet) {
				continue;
			}
			monitor.subTask("persisting "+convertedEntity.getName());
			String folder = convertedEntity.getFolder();
			String extension = IndexUtils.getFileExtension(convertedEntity);
			URI uri = URI.createFileURI(baseURI+folder+convertedEntity.getName()+"."+extension);
			if (convertedEntity instanceof Rule) {
				try {
					ModelUtilsCore.persistRule((Rule)convertedEntity, uri);
				} catch (Exception e) {
					e.printStackTrace();
				}
				continue;
			}

			if (convertedEntity instanceof RuleFunction) {
				try {
					ModelUtilsCore.persistRuleFunction((RuleFunction)convertedEntity, uri);
				} catch (Exception e) {
					e.printStackTrace();
				}
				continue;
			}
			
			// using XMI
			ResourceSet resourceSet = new ResourceSetImpl();
			if (resourceSet.getResourceFactoryRegistry().getFactory(uri) == null) {
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
					.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
			}
			Resource resource = resourceSet.createResource(uri);
			if (resource == null)
				continue;

			resource.getContents().add(convertedEntity);
			try {
				resource.save(options);
			} catch (IOException e) {
				e.printStackTrace();
			}
			monitor.worked(1);
		}
		
	}

	private static EntityConverter getConverter(Entity entity) {
		Collection<EntityConverter> converters = registeredConverters;
		for (EntityConverter entityConverter : converters) {
			if (entityConverter.canConvert(entity)) {
				return entityConverter;
			}
		}
		return null;
	}
/*
	public static DefaultMutableOntology ontologyFromEAR(File earFile) throws Exception {
		Workspace ws = BEWorkspace.getInstance();
		System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
				"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
		
		ArrayList<ResourceProvider> providers = new ArrayList<ResourceProvider>();
		providers.add(new JavaArchiveResourceProvider());
		providers.add(new SharedArchiveResourceProvider());
		providers.add(new BEArchiveResourceProvider());		
		
		BEProject proj = (BEProject) ws.loadProject(providers, earFile
				.getAbsolutePath());
		return (DefaultMutableOntology) proj.getOntology();
	}
	*/
	
	public static BEProject getBEProject(File repoPath) throws Exception {
		// create a new one each time, otherwise this could return an EMFWorkspace, causing ontology loading to fail
		final Workspace ws = new BEWorkspace();//BEWorkspace.getInstance();

		System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
				"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
		
		ArrayList<ResourceProvider> providers = new ArrayList<ResourceProvider>();
		providers.add(new JavaArchiveResourceProviderImpl());
		providers.add(new SharedArchiveResourceProviderImpl());
		providers.add(new BEArchiveResourceProviderImpl());
		providers.add(new EMFBEArchiveResourceProvider());
		providers.add(new ArchiveResourceProvider());
		providers.add(new GlobalVariablesProviderImpl());
		
		BEProject proj = (BEProject) ws.reloadProject(providers, repoPath.getAbsolutePath());
		return proj;
		
	}
	

}
