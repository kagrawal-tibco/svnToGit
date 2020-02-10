package com.tibco.cep.studio.core.configuration.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.INodeChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IImportContainer;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeParameter;
import org.eclipse.jdt.core.JavaModelException;
import org.osgi.framework.BundleException;
import org.osgi.service.prefs.BackingStoreException;

import com.tibco.cep.cep_commonVersion;
import com.tibco.cep.studio.common.configuration.BpmnProcessSettings;
import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.CoreJavaLibEntry;
import com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry;
import com.tibco.cep.studio.common.configuration.IStudioProjectConfigurationChangeListener;
import com.tibco.cep.studio.common.configuration.JavaLibEntry;
import com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationCache;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationChangeEvent;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.common.configuration.update.IStudioProjectConfigurationDelta;
import com.tibco.cep.studio.common.configuration.update.StudioProjectConfigurationDelta;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.PathVariablesOperation;
import com.tibco.cep.studio.core.nature.StudioProjectNature;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.StudioJavaUtil;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.tpcl.IRuntimeBundleClasspathProvider;

@SuppressWarnings("rawtypes")
public class StudioProjectConfigurationManager implements IResourceChangeListener, IElementChangedListener {

	private class ConfigUpdateVisitor implements IResourceDeltaVisitor {

		public boolean visit(IResourceDelta delta) throws CoreException {
			try {
				IResource resource = delta.getResource();
				if (resource instanceof IFile) {
					IFile file = (IFile) resource;
					processFile(file, delta);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}

	public static final String STUDIO_PROJECT_CONFIG_FILENAME = ".beproject"; //$NON-NLS-1$
	public static final String DOTCLASSPATH_FILENAME = ".classpath"; //$NON-NLS-1$
	public static final String EXTERNAL_LIB_PREFIX = "external:";//$NON-NLS-1$
	//	private static final String REFERENCE_LIB_PREFIX = "reference:file:"; //$NON-NLS-1$
	public static final String VARIABLE_DELIM_STRING = "$"; //$NON-NLS-1$
	public static final char VARIABLE_DELIM_CHAR = '$'; //$NON-NLS-1$
	private static final String[] configFiles = new String[]{STUDIO_PROJECT_CONFIG_FILENAME};
	/**
	 * Classpath variables pool
	 */
	public HashMap variables = new HashMap(5);
	public HashSet variablesWithInitializer = new HashSet(5);
	public HashMap deprecatedVariables = new HashMap(5);
	public HashSet readOnlyVariables = new HashSet(5);
	public HashMap previousSessionVariables = new HashMap(5);
	private ThreadLocal variableInitializationInProgress = new ThreadLocal();
	private static final String RUNTIME_CLASSPATH_PROVIDER = "com.tibco.cep.tpcl.runtimeBundleClasspathProvider";
	private static final String RUNTIME_CLASSPATH_PROVIDER_ATTR = "classpathProvider";
	public final IEclipsePreferences[] preferencesLookup = new IEclipsePreferences[2];
	static final int PREF_INSTANCE = 0;
	static final int PREF_DEFAULT = 1;
	public final static String PATH_VARIABLE_PREFERENCES_PREFIX = StudioCorePlugin.PLUGIN_ID + ".pathVariable."; //$NON-NLS-1$
	public final static String PATH_CONTAINER_PREFERENCES_PREFIX = StudioCorePlugin.PLUGIN_ID + ".pathContainer."; //$NON-NLS-1$
	public final static String PATH_USERLIBRARY_PREFERENCES_PREFIX = StudioCorePlugin.PLUGIN_ID + ".userLibrary."; //$NON-NLS-1$
	public final static String PATH_ENTRY_IGNORE = "##<cp entry ignore>##"; //$NON-NLS-1$
	public final static IPath PATH_ENTRY_IGNORE_PATH = new Path(PATH_ENTRY_IGNORE);
	public final static String TRUE = "true"; //$NON-NLS-1$
	/**
	 * Special value used for recognizing ongoing initialization and breaking
	 * initialization cycles
	 */
	public final static IPath VARIABLE_INITIALIZATION_IN_PROGRESS = new Path("Variable Initialization In Progress"); //$NON-NLS-1$
	/**
	 * Name of the extension point for contributing classpath variable
	 * initializers
	 */
	public static final String PATHVARIABLE_INITIALIZER_EXTPOINT_ID = "pathVariableInitializer"; //$NON-NLS-1$
	public static boolean VERBOSE = false;
	public static boolean PATH_RESOLVE_VERBOSE = false;
	public static boolean PATH_RESOLVE_VERBOSE_ADVANCED = false;
	public static boolean PATH_RESOLVE_VERBOSE_FAILURE = false;
	public static boolean ZIP_ACCESS_VERBOSE = false;

	private ConfigUpdateVisitor fVisitor = new ConfigUpdateVisitor();
	private List<IStudioProjectConfigurationChangeListener> fConfigurationChangeListeners = new CopyOnWriteArrayList<IStudioProjectConfigurationChangeListener>();
	@SuppressWarnings("unused")
	private INodeChangeListener instanceNodeChangeListener;
	private static StudioProjectConfigurationCache fConfigurations = StudioProjectConfigurationCache.getInstance();
	// private static StudioProjectChangeListener fListener;

	private static StudioProjectConfigurationManager fInstance;
	private static String projectPath;

	private static boolean fInitialized = false;

	/**
	 * Listener on eclipse preferences changes.
	 */
	EclipsePreferencesListener instancePreferencesListener = new EclipsePreferencesListener();
	/**
	 * Listener on eclipse preferences default/instance node changes.
	 */
	IEclipsePreferences.INodeChangeListener instanceNodeListener = new IEclipsePreferences.INodeChangeListener() {
		public void added(IEclipsePreferences.NodeChangeEvent event) {
			// do nothing
		}

		@SuppressWarnings("deprecation")
		public void removed(IEclipsePreferences.NodeChangeEvent event) {
			if (event.getChild() == preferencesLookup[PREF_INSTANCE]) {
				preferencesLookup[PREF_INSTANCE] = ((IScopeContext) new InstanceScope()).getNode(StudioCorePlugin.PLUGIN_ID);
				preferencesLookup[PREF_INSTANCE].addPreferenceChangeListener(new EclipsePreferencesListener());
			}
		}
	};
	IEclipsePreferences.INodeChangeListener defaultNodeListener = new IEclipsePreferences.INodeChangeListener() {
		public void added(IEclipsePreferences.NodeChangeEvent event) {
			// do nothing
		}

		@SuppressWarnings("deprecation")
		public void removed(IEclipsePreferences.NodeChangeEvent event) {
			if (event.getChild() == preferencesLookup[PREF_DEFAULT]) {
				preferencesLookup[PREF_DEFAULT] = ((IScopeContext) new DefaultScope()).getNode(StudioCorePlugin.PLUGIN_ID);
			}
		}
	};

	/**
	 * Update the classpath variable cache
	 */
	public static class EclipsePreferencesListener implements IEclipsePreferences.IPreferenceChangeListener {
		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			String propertyName = event.getKey();
			if (propertyName.startsWith(PATH_VARIABLE_PREFERENCES_PREFIX)) {
				String varName = propertyName.substring(PATH_VARIABLE_PREFERENCES_PREFIX.length());
				StudioProjectConfigurationManager configuration = StudioProjectConfigurationManager.getInstance();
				if (configuration.variablesWithInitializer.contains(varName)) {
					// revert preference value as we will not apply it to
					// JavaCore classpath variable
					String oldValue = (String) event.getOldValue();
					if (oldValue == null) {
						// unexpected old value => remove variable from set
						configuration.variablesWithInitializer.remove(varName);
					} else {
						configuration.getInstancePreferences().put(varName, oldValue);
					}
				} else {
					String newValue = (String) event.getNewValue();
					IPath newPath;
					if (newValue != null && !(newValue = newValue.trim()).equals(PATH_ENTRY_IGNORE)) {
						newPath = new Path(newValue);
					} else {
						newPath = null;
					}
					try {
						PathVariablesOperation operation = new PathVariablesOperation(new String[] { varName }, new IPath[] { newPath }, false/*
						 * don
						 * 't
						 * update
						 * preferences
						 */);
						operation.runOperation(null/* no progress available */);
					} catch (CoreException e) {
						StudioCorePlugin.log("Could not set classpath variable " + varName + " to " + newPath, e); //$NON-NLS-1$ //$NON-NLS-2$
					}
				}

			} else if (propertyName.startsWith(PATH_CONTAINER_PREFERENCES_PREFIX)) {
				// TODO
			} else if (propertyName.startsWith(PATH_USERLIBRARY_PREFERENCES_PREFIX)) {
				// TODO
			}

		}
	}


	private StudioProjectConfigurationManager() {
	}



	private void processFile(IFile file, IResourceDelta delta) throws Exception {
		if (delta.getKind() == IResourceDelta.CHANGED) {
			if (Arrays.asList(configFiles).contains(file.getName()) &&
					file.getParent().getFullPath().equals(file.getProject().getFullPath())) {
				synchronized (fConfigurations) {
					StudioProjectConfiguration configuration = fConfigurations.get(file.getProject().getName());
					if (configuration != null) {
						if (configuration.getFileTimeStamp() < file.getLocalTimeStamp()) {
							// reload, file was modified externally
							reloadProjectConfiguration(file.getProject().getName());
						} else {
							// presumably, this delta has already been created and fired, directly from the method that changed it
							// for instance, the Studio build path page.  So, do nothing here, as the delta has already been processed
//							StudioProjectConfigurationDelta configdelta = new StudioProjectConfigurationDelta(configuration, IStudioProjectConfigurationDelta.CHANGED);
//							fireDelta(configdelta);
							//							fireConfigurationChangeEvent(new StudioProjectConfigurationChangeEvent(configdelta));
						}
					} else {
						reloadProjectConfiguration(file.getProject().getName());
					}
				}

			} 
		}
	}

	public synchronized static StudioProjectConfigurationManager getInstance() {
		/**
		 * using double checked singleton pattern
		 */
		if (fInstance == null) {
			synchronized(StudioProjectConfigurationManager.class) {
				fInstance = new StudioProjectConfigurationManager();
				fInstance.initialize();
			}
			// try {
			// load();
			// // fListener = new StudioProjectChangeListener(fInstance);
			// // ResourcesPlugin.getWorkspace().addResourceChangeListener(
			// // fListener, IResourceChangeEvent.POST_CHANGE);
			// } catch (IllegalStateException e) {
			// // No workspace just return the instance so the user can fill the
			// projectConfig
			// // this is used for commandline project config read from the
			// project folder.
			// }
		}
		return fInstance;
	}

	synchronized private void initialize() {
		if (fInitialized) {
			return;
		}
		fInitialized = true;
		try {
			startup();
			load();
			// fListener = new StudioProjectChangeListener(fInstance);
			// ResourcesPlugin.getWorkspace().addResourceChangeListener(
			// fListener, IResourceChangeEvent.POST_CHANGE);
		} catch (IllegalStateException e) {
			// No workspace just return the instance so the user can fill the
			// projectConfig
			// this is used for commandline project config read from the project
			// folder.
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	synchronized private void load() {
		// load all project configurations from disk
		IProject[] projects = CommonUtil.getAllStudioProjects();
		for (IProject project : projects) {
			createConfigurationFromProject(project);
		}
	}

	private StudioProjectConfiguration createProjectConfiguration(IProject project, IFile file) {
		String fileLoc = null;
		if (file.getLocation() != null)
			fileLoc = file.getLocation().toString();
		else
			fileLoc = projectPath + "/" + file.getName();
		StudioProjectConfiguration configuration = createProjectConfiguration(project.getName(), fileLoc);
		try {
			CommonUtil.refresh(file, IResource.DEPTH_ZERO, false);
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		return configuration;
	}

	private StudioProjectConfiguration createProjectConfiguration(String projectName, String resourceLocation) {
		return (createProjectConfiguration(projectName, resourceLocation, null, null));
	}

	public StudioProjectConfiguration createProjectConfiguration(String projectName, String resourceLocation, String[] coreInternalLibs, XPATH_VERSION xpath_version) {
		StudioProjectConfiguration configuration = ConfigurationFactory.eINSTANCE.createStudioProjectConfiguration();
		configuration.setName(projectName);
		configuration.setVersion(cep_commonVersion.version);
		configuration.setBuild(cep_commonVersion.build);
		if (xpath_version != null) {
			configuration.setXpathVersion(xpath_version);
		}
		// IPath outputPath =
		// projPath.append(EnterpriseArchiveEntry.BUILD_OUTPUT_FOLDER_NAME);
		// getEnterpriseArchiveConfig(configuration).setOutputPath(outputPath.makeRelative().toString());
		getEnterpriseArchiveConfig(configuration);
		getBpmnProcessConfiguration(configuration);
		if (coreInternalLibs == null) {
			loadCoreInternalLibraries(configuration);
		} else {
			loadCoreInternalLibraries(configuration, coreInternalLibs);
		}
		loadThirdPartyLibraries(configuration);
		FileOutputStream fos = null;
		try {
			XMIResource resource = new XMIResourceImpl(URI.createFileURI(resourceLocation));
			resource.getContents().add(configuration);
			// File newFile = file.getLocation().toFile();
			File newFile = new File(resourceLocation);
			// File may be non-existent
			// if (newFile.exists() && newFile.canWrite()) {
			fos = new FileOutputStream(newFile);
			resource.save(fos, null);
			configuration.setFileTimeStamp(newFile.lastModified());
			// }
		} catch (IOException e) {
			StudioCorePlugin.log(e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
		resolveLibraryEntriesPath(configuration);
		synchronized (fConfigurations) {

			fConfigurations.put(projectName, configuration);
		}
		return configuration;
	}

	public static BpmnProcessSettings getBpmnProcessConfiguration(StudioProjectConfiguration configuration) {
		//		initialize();
		BpmnProcessSettings settings = configuration.getBpmnProcessSettings();
		if (settings == null) {
			settings = ConfigurationFactory.eINSTANCE.createBpmnProcessSettings();
			configuration.setBpmnProcessSettings(settings);
		}

		return settings;

	}

	private static EnterpriseArchiveEntry getEnterpriseArchiveConfig(StudioProjectConfiguration config) {
		EnterpriseArchiveEntry eaconfig = config.getEnterpriseArchiveConfiguration();
		if (eaconfig == null) {
			eaconfig = ConfigurationFactory.eINSTANCE.createEnterpriseArchiveEntry();
			config.setEnterpriseArchiveConfiguration(eaconfig);
			eaconfig.setName(config.getName());
			String projectName = config.getName();
			String locationProp = projectName + ".build.location";
			String versionProp = projectName + ".version.number";
			String authorProp = projectName + ".author.name";
			String loc = System.getProperty(locationProp);
			if (loc != null) {
				eaconfig.setPath(loc);
			} else {
				String path = System.getProperty("user.home") + System.getProperty("file.separator") + config.getName() + ".ear";
				eaconfig.setPath(path);
			}
			String version = System.getProperty(versionProp);
			if (version != null) {
				try {
					eaconfig.setVersion(Integer.parseInt(version));
				} catch (Exception e) {
					eaconfig.setVersion(1);
				}
			} else {
				eaconfig.setVersion(1);
			}
			String author = System.getProperty(authorProp, System.getProperty("user.name"));
			eaconfig.setAuthor(author);
		}
		return eaconfig;
	}

	private static void loadThirdPartyLibraries(StudioProjectConfiguration configuration) {
		// EList<ThirdPartyLibEntry> entries =
		// configuration.getThirdpartyLibEntries();
	}

	private static void loadCoreInternalLibraries(StudioProjectConfiguration config, String[] coreInternalLibs) {
		for (String jarPath : coreInternalLibs) {
			addCoreInternalLibEntry(config, jarPath);
		}
	}

	public static void loadCoreInternalLibraries(StudioProjectConfiguration config) {
		try {
			if (config.getCoreInternalLibEntries().size() > 0) {
				config.getCoreInternalLibEntries().clear();
			}
			IExtensionRegistry reg = Platform.getExtensionRegistry();
			if (reg != null) {
				IConfigurationElement[] extensions = reg.getConfigurationElementsFor(RUNTIME_CLASSPATH_PROVIDER);
				for (int i = 0; i < extensions.length; i++) {
					IConfigurationElement element = extensions[i];
					final Object o = element.createExecutableExtension(RUNTIME_CLASSPATH_PROVIDER_ATTR);
					if (o != null && o instanceof IRuntimeBundleClasspathProvider) {
						File[] classpath = ((IRuntimeBundleClasspathProvider) o).getClasspath();
						if (classpath == null) {
							return;
						}
						for (File file : classpath) {
							//	System.out.println("Adding "+file.getPath());
							addCoreInternalLibEntry(config, file.getPath());
						}
						//						org.eclipse.osgi.framework.internal.core.BundleHost b = (org.eclipse.osgi.framework.internal.core.BundleHost) ((IRuntimeBundleClasspathProvider) o)
						//								.getBundle();
						//						for (String s : classpath) {
						//							if (s.startsWith(".")) {
						//								File runtimeJar = ((org.eclipse.osgi.baseadaptor.BaseData) b.getBundleData()).getBundleFile().getFile(s, true);
						//								if (runtimeJar != null) {
						//									IPath fPath = new Path(runtimeJar.getPath());
						//									if (fPath.getFileExtension() != null && fPath.getFileExtension().equalsIgnoreCase("jar")) {
						//										addCoreInternalLibEntry(config, new Path(runtimeJar.getPath()).toPortableString());
						//									} else {
						//										fPath = fPath.append("bin");
						//										addCoreInternalLibEntry(config, fPath.toPortableString());
						//									}
						//								}
						//								continue;
						//							}
						//							if (s.startsWith(REFERENCE_LIB_PREFIX)) {
						//								Path p = new Path(s.substring(REFERENCE_LIB_PREFIX.length()));
						//								String jpath = p.toOSString();
						//								if (jpath != null && !jpath.isEmpty()) {
						//									addCoreInternalLibEntry(config, new Path(jpath).toPortableString());
						//									continue;
						//								}
						//							}
						//							if (s.startsWith(EXTERNAL_LIB_PREFIX)) { // RUNTIME
						//								String path = substituteVars(s.substring(EXTERNAL_LIB_PREFIX.length()));
						//								addCoreInternalLibEntry(config, new Path(path).toPortableString());
						//								continue;
						//							}
						//							File file = new File(s);
						//							if (file.exists() == true) {
						//								String path = file.getPath();
						//								addCoreInternalLibEntry(config, new Path(path).toPortableString());
						//							} else {
						//								// DEV environment
						//								File runtimeJarFile = ((org.eclipse.osgi.baseadaptor.BaseData) b.getBundleData()).getBundleFile().getFile(s, true);
						//								if (runtimeJarFile == null) {
						//									System.err.println("Could not find runtime jar " + s + " in bundle " + b.getSymbolicName());
						//									continue;
						//								}
						//								String path = runtimeJarFile.getPath();
						//								addCoreInternalLibEntry(config, new Path(path).toPortableString());
						//							}
						//						}
					}
				}
			}
		} catch (BundleException e) {
			StudioCorePlugin.log(e);
		} catch (CoreException e) {
			StudioCorePlugin.log(e);
		}
	}

	/**
	 * @param config
	 * @param jarPath
	 */
	public static void addCoreInternalLibEntry(StudioProjectConfiguration config, String jarPath) {
		File nativeFile = new File(jarPath);
		if (nativeFile.exists()) {
			BuildPathEntry entry = checkExistingEntry(config, nativeFile);
			if (entry != null) {
				if (entry.getTimestamp() != nativeFile.lastModified()) {
					entry.setTimestamp(nativeFile.lastModified());
				}
			} else {
				CoreJavaLibEntry libentry = ConfigurationFactory.eINSTANCE.createCoreJavaLibEntry();
				libentry.setEntryType(LIBRARY_PATH_TYPE.CORE_INTERNAL_LIBRARY);
				IPath npath = new Path(nativeFile.getPath());
				// String path = appendVar(nativeFile.getPath(), libentry);
				libentry.setPath(npath.toPortableString());
				libentry.setTimestamp(nativeFile.lastModified());
				config.getCoreInternalLibEntries().add(libentry);
			}
		}
	}

	/**
	 * @param path
	 * @param libentry
	 * @return
	 */
	public static String appendVar(String path, CoreJavaLibEntry libentry) {
		String vars[] = getRegisteredVariableNames();
		for (String var : vars) {
			IPath classPathVar = StudioCore.getClasspathVariable(var);
			if (classPathVar != null) {
				String varPath = classPathVar.toPortableString();
				if (path.startsWith(varPath)) {
					path = path.replace(varPath, var);
					libentry.setVar(true);
					libentry.setResolvedPath(path);
					return path;
				}
			}
		}
		return path;
	}

	protected static BuildPathEntry checkExistingEntry(StudioProjectConfiguration config, File jarFile) {
		EList<BuildPathEntry> entries = config.getEntriesByType(LIBRARY_PATH_TYPE.CORE_INTERNAL_LIBRARY);
		for (BuildPathEntry entry : entries) {
			IPath jpath = new Path(jarFile.getPath());
			if (entry == null || jpath == null) {
				break;
			}
			if (entry.getPath(false).equals(jpath.toPortableString())) {
				return entry;
			}
		}
		return null;
	}

	void removeConfiguration(String projectName) throws Exception {
		synchronized (fConfigurations) {
			if (fConfigurations.containsKey(projectName)) {
				StudioProjectConfiguration config = fConfigurations.remove(projectName);
				StudioProjectConfigurationDelta delta = new StudioProjectConfigurationDelta(config, IStudioProjectConfigurationDelta.REMOVED);
				fireDelta(delta);
				//				fireConfigurationChangeEvent(new StudioProjectConfigurationChangeEvent(delta));
			}
		}
	}

	public void reloadProjectConfiguration(String projectName) throws Exception {
		StudioProjectConfiguration config = createProjectConfiguration(projectName);
		StudioProjectConfigurationDelta delta = new StudioProjectConfigurationDelta(config, IStudioProjectConfigurationDelta.REMOVED);
		fireDelta(delta);
		//		fireConfigurationChangeEvent(new StudioProjectConfigurationChangeEvent(delta));
	}

	public StudioProjectConfiguration getProjectConfiguration(String projectName) {
		//		initialize();
		synchronized (fConfigurations) {

			StudioProjectConfiguration configuration = fConfigurations.get(projectName);
			if (configuration == null) {
				try {
					configuration = createProjectConfiguration(projectName);
				} catch (Exception e) {
					StudioCorePlugin.log(String.format("Failed to load configuration for project: %s",projectName), e);
				}
			}
			return configuration;
		}
	}

	public String getProjectName(StudioProjectConfiguration configuration) {
		//		initialize();
		synchronized (fConfigurations) {

			Set<String> keySet = fConfigurations.keySet();
			for (String projName : keySet) {
				StudioProjectConfiguration config = fConfigurations.get(projName);
				if (config.equals(configuration)) {
					return projName;
				}
			}
			return null;
		}
	}

	private StudioProjectConfiguration createProjectConfiguration(String projectName) throws Exception {
		if (projectName == null) {
			return null;
		}
		if (projectName.isEmpty()) {
			return null;
		}
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (project == null || !project.isAccessible() || !CommonUtil.isStudioProject(project)) {
			return null;
		}
		final StudioProjectConfiguration config = createConfigurationFromProject(project);
		StudioJavaUtil.loadJavaConfiguration(project);
		return config;
	}

	private  StudioProjectConfiguration createConfigurationFromProject(IProject project) {
		if (!project.isAccessible() || !CommonUtil.isStudioProject(project)) {
			return null;
		}
		IPath dtlPath = Path.fromOSString(STUDIO_PROJECT_CONFIG_FILENAME);
		final IFile resolvedRes = project.getFile(dtlPath);
		// check if resource already exists
		if (resolvedRes != null && resolvedRes.exists()) {
			StudioProjectConfiguration config = null;
			try {
				config = (StudioProjectConfiguration) CommonUtil.loadEObject(resolvedRes);
				config.setFileTimeStamp(resolvedRes.getLocalTimeStamp());
			} catch (Exception e) {
				config = createProjectConfiguration(project, resolvedRes);
				StudioCorePlugin
				.getDefault()
				.getLog()
				.log(new Status(IStatus.WARNING, StudioCorePlugin.PLUGIN_ID, "Could not load project configuration for '" + project.getName()
						+ "'.  A new configuration will be created, and any build path settings will be lost.", e));
			}
			synchronized (fConfigurations) {

				fConfigurations.put(project.getName(), (StudioProjectConfiguration) config);
			}
			loadCoreInternalLibraries((StudioProjectConfiguration) config);
			resolveLibraryEntriesPath(config);
			return (StudioProjectConfiguration) config;
		} else {
			// try to load from disk, as the IFile may not yet be accessible
			if (resolvedRes != null && resolvedRes.getLocation() != null) {
				File file = new File(resolvedRes.getLocation().toString());
				if (file.exists()) {
					EObject config = null;
					try {
						config = CommonUtil.loadEObject(resolvedRes.getLocation().toString());
					} catch (Exception e) {
						config = createProjectConfiguration(project, resolvedRes);
						StudioCorePlugin.log("Could not load project configuration for '" + project.getName()
								+ "'.  A new configuration will be created, and any build path settings will be lost.", e);
					}
					if (config instanceof StudioProjectConfiguration) {
						((StudioProjectConfiguration) config).setFileTimeStamp(file.lastModified());
						synchronized (fConfigurations) {
							fConfigurations.put(project.getName(), (StudioProjectConfiguration) config);
						}
						loadCoreInternalLibraries((StudioProjectConfiguration) config);
						resolveLibraryEntriesPath((StudioProjectConfiguration) config);
						StudioJavaUtil.loadJavaConfiguration(project);
						return (StudioProjectConfiguration) config;
					}
				}
			}
			return createProjectConfiguration(project, resolvedRes);
		}

	}

	public static void resolveLibraryEntriesPath(StudioProjectConfiguration projectConfiguration) {
		List<BuildPathEntry> libPaths = new ArrayList<BuildPathEntry>();

		libPaths.addAll(projectConfiguration.getCoreInternalLibEntries());
		libPaths.addAll(projectConfiguration.getThirdpartyLibEntries());
		libPaths.addAll(projectConfiguration.getCustomFunctionLibEntries());
		libPaths.addAll(projectConfiguration.getProjectLibEntries());

		for (BuildPathEntry buildPathEntry : libPaths) {
			if (buildPathEntry.isVar()) {
				String pathStr = buildPathEntry.getPath();
				IPath path = new Path(pathStr);
				path = StudioCore.getResolvedVariablePath(path);
				if (path != null)
					buildPathEntry.setResolvedPath(path.toPortableString());
			}
		}

	}

	public StudioProjectConfiguration[] getAllConfigurations() {
		//		initialize();
		synchronized (fConfigurations) {

			Collection<StudioProjectConfiguration> values = fConfigurations.values();
			return values.toArray(new StudioProjectConfiguration[values.size()]);
		}
	}

	public void addConfigurationChangeListener(IStudioProjectConfigurationChangeListener listener) {
		addConfigurationChangeListener(listener, -1);
	}

	public void addConfigurationChangeListener(IStudioProjectConfigurationChangeListener listener, int index) {
		if (!fConfigurationChangeListeners.contains(listener)) {
			if (index < 0) {
				fConfigurationChangeListeners.add(listener);
			} else {
				fConfigurationChangeListeners.add(index, listener);
			}
		}
	}

	public void removeConfigurationChangeListener(IStudioProjectConfigurationChangeListener listener) {
		if (fConfigurationChangeListeners.contains(listener)) {
			fConfigurationChangeListeners.remove(listener);
		}
	}

	//	private void fireConfigurationChangeEvent(StudioProjectConfigurationChangeEvent event) throws Exception {
	//		for (IStudioProjectConfigurationChangeListener listener : fConfigurationChangeListeners) {
	//			listener.configurationChanged(event);
	//		}
	//	}

	private void startup() throws CoreException {
		try {

			// Initialize eclipse preferences
			initializePreferences();

			initializeSystemProperties();

			// retrieve variable values
			loadVariables();

		} catch (RuntimeException e) {
			//			StudioCore.shutdown();
			throw e;
		}
	}

	private void initializeSystemProperties() {
		String[] vars = getRegisteredVariableNames();
		for (String var : vars) {
			StudioCore.getClasspathVariable(var); // this will initialize the
			// vars
		}
	}

	public void saveConfiguration(String projectName, StudioProjectConfiguration configuration) throws Exception {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		IPath dtlPath = Path.fromOSString(STUDIO_PROJECT_CONFIG_FILENAME);
		IFile file = project.getFile(dtlPath);
		FileOutputStream fos = null;
		try {
			if (configuration.eResource() == null) {
				File configFile = file.getLocation().toFile();
				XMIResource resource = new XMIResourceImpl();
				resource.getContents().add(configuration);
				fos = new FileOutputStream(configFile);
				resource.save(fos, null);
			} else {
				ModelUtils.saveEObject(configuration);
				CommonUtil.refresh(file, IResource.DEPTH_ZERO, false);
			}
			long timeStamp = file.getLocation() != null ? file.getLocation().toFile().lastModified() : file.getLocalTimeStamp();
			configuration.setFileTimeStamp(timeStamp);
		} catch (IOException e) {
			StudioCorePlugin.log(new Status(Status.ERROR, StudioCorePlugin.PLUGIN_ID, Status.ERROR, "Unable to save project configuration", e));
			throw e;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e2) {
				}
			}
		}
		// now done in resource change listener
		// fireConfigurationChangeEvent(new
		// StudioProjectConfigurationChangeEvent(configuration, projectName,
		// StudioProjectConfigurationChangeEvent.CHANGED));
	}

	public void saveAllConfigurations() throws Exception {
		//		initialize();
		synchronized (fConfigurations) {
			Set<String> keySet = fConfigurations.keySet();
			for (String projName : keySet) {
				StudioProjectConfiguration configuration = fConfigurations.get(projName);
				saveConfiguration(projName, configuration);
			}
		}
	}

	public void saveConfiguration(String projectName) throws Exception {
		//		initialize();
		synchronized (fConfigurations) {
			Set<String> keySet = fConfigurations.keySet();
			for (String projName : keySet) {
				if (projName.equals(projectName)) {
					StudioProjectConfiguration configuration = fConfigurations.get(projName);
					saveConfiguration(projName, configuration);
				}
			}
		}
	}

	public void reload() {
		synchronized (fConfigurations) {
			fConfigurations.clear();
			load();
			fInitialized = true;
		}
	}

	public StudioProjectConfigurationCache getConfigurationsCache() {
		//		initialize();
		return StudioProjectConfigurationCache.getInstance();
	}

	public static String substituteVars(String path) {
		StringBuffer buf = new StringBuffer(path.length());
		StringTokenizer st = new StringTokenizer(path, VARIABLE_DELIM_STRING, true);
		boolean varStarted = false; // indicates we are processing a var
		// subtitute
		String var = null; // the current var key
		while (st.hasMoreElements()) {
			String tok = st.nextToken();
			if (VARIABLE_DELIM_STRING.equals(tok)) {
				if (!varStarted) {
					varStarted = true; // we found the start of a var
					var = "";
				} else {
					// we have found the end of a var
					String prop = null;
					// get the value of the var from system properties
					if (var != null && var.length() > 0)
						prop = System.getProperty(var);
					if (prop == null) {
						try {
							// try using the System.getenv method if it exists
							Method getenv = System.class.getMethod("getenv", new Class[] { String.class }); //$NON-NLS-1$
							prop = (String) getenv.invoke(null, new Object[] { var });
						} catch (Throwable t) {
							// do nothing;
						}
					}
					if (prop != null)
						// found a value; use it
						buf.append(prop);
					else
						// could not find a value append the var name w/o delims
						buf.append(var == null ? "" : var);
					varStarted = false;
					var = null;
				}
			} else {
				if (!varStarted)
					buf.append(tok); // the token is not part of a var
				else
					var = tok; // the token is the var key; save the key to
				// process when we find the end token
			}
		}
		if (var != null)
			// found a case of $var at the end of the path with no trailing $;
			// just append it as is.
			buf.append(VARIABLE_DELIM_CHAR).append(var);
		return buf.toString();
	}

	// public static StudioProjectConfiguration loadProject(String
	// projectName,File projectFile) {
	// initialize();
	// StudioProjectConfiguration config = null;
	// URI uri = URI.createFileURI(projectFile.getPath());
	// ResourceSet rs = new ResourceSetImpl();
	// Resource res = rs.getResource(uri, true);
	// if(res.getContents().size() >0){
	// config = (StudioProjectConfiguration) res.getContents().get(0);
	// fConfigurations.put(projectName, (StudioProjectConfiguration) config);
	// }
	// return config;
	// }

	public void setProjectPath(String path) {
		projectPath = path;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		try {
			if (IResourceChangeEvent.POST_CHANGE == event.getType()) {
				IResourceDelta delta = event.getDelta();
				if (delta == null)
					return;
				// IResource resource = delta.getResource();
				delta.accept(fVisitor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param name
	 * @return
	 */
	public ClassLoader getProjectConfigClassloader(String name) {
		StudioProjectConfiguration pconfig = getProjectConfiguration(name);
		return getProjectConfigClassloader(pconfig, true);
	}

	/**
	 * @param pconfig
	 * @return
	 */
	public ClassLoader getProjectConfigClassloader(StudioProjectConfiguration pconfig, boolean includeCoreLibs) {

		ClassLoader parentClassLoader = StudioCorePlugin.class.getClassLoader();

		List<URL> classpath = getProjectLibURLs(pconfig, includeCoreLibs);
		URLClassLoader clr = new URLClassLoader(classpath.toArray(new URL[classpath.size()]), parentClassLoader);
		return clr;
	}

	/**
	 * @param pconfig
	 * @param includeCorelibs
	 * @return
	 */
	public List<URL> getProjectLibURLs(StudioProjectConfiguration pconfig, boolean includeCorelibs) {
		List<URL> classpath = new ArrayList<URL>();
		List<JavaLibEntry> libPaths = getProjectLibEntries(pconfig, includeCorelibs);
		for (JavaLibEntry lib : libPaths) {
			URL url2;
			try {
				String path = lib.getPath(lib.isVar());
				URL url = new File(path).toURI().toURL();
				String filePath = "jar:file:" + url.getPath() + "!/";
				url2 = new java.net.URI(filePath).toURL();
				classpath.add(url2);
			} catch (MalformedURLException e) {
				StudioCorePlugin.log(e);
			} catch (URISyntaxException e) {
				StudioCorePlugin.log(e);
			}
		}
		return classpath;
	}

	/**
	 * @param pconfig
	 * @param includeCoreLibs
	 * @return
	 */
	public List<JavaLibEntry> getProjectLibEntries(StudioProjectConfiguration pconfig, boolean includeCoreLibs) {
		boolean isEclipseAvailable = false;
		List<JavaLibEntry> libPaths = new ArrayList<JavaLibEntry>();
		try {
			Class<?> platformClass = Class.forName("org.eclipse.core.runtime.Platform");
			Method extRegMethod = platformClass.getMethod("getBundle", String.class);
			Object bundleObject = extRegMethod.invoke(platformClass, StudioCorePlugin.PLUGIN_ID);
			Method loadClassMethod = bundleObject.getClass().getMethod("loadClass", String.class);
			@SuppressWarnings("unused")
			Class<?> pluginClass = (Class<?>) loadClassMethod.invoke(bundleObject, "com.tibco.cep.studio.core.StudioCorePlugin");
			isEclipseAvailable = true;
		} catch (ClassNotFoundException cnfe) {
			isEclipseAvailable = false;
		} catch (SecurityException e) {
			isEclipseAvailable = false;
		} catch (NoSuchMethodException e) {
			isEclipseAvailable = false;
		} catch (IllegalArgumentException e) {
			isEclipseAvailable = false;
		} catch (IllegalAccessException e) {
			isEclipseAvailable = false;
		} catch (InvocationTargetException e) {
			isEclipseAvailable = false;
		}

		if (isEclipseAvailable && includeCoreLibs) {
			libPaths.addAll(pconfig.getCoreInternalLibEntries());
		}
		libPaths.addAll(pconfig.getThirdpartyLibEntries());
		libPaths.addAll(pconfig.getCustomFunctionLibEntries());
		return libPaths;
	}

	@SuppressWarnings("deprecation")
	public void initializePreferences() {
		preferencesLookup[PREF_DEFAULT] = ((IScopeContext) new DefaultScope()).getNode(StudioCorePlugin.PLUGIN_ID);
		preferencesLookup[PREF_INSTANCE] = ((IScopeContext) new InstanceScope()).getNode(StudioCorePlugin.PLUGIN_ID);
		try {
			preferencesLookup[PREF_INSTANCE].clear(); //BE-23751 - clear the older preferences first and then load everything again.
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.instanceNodeChangeListener = new IEclipsePreferences.INodeChangeListener() {
			public void added(IEclipsePreferences.NodeChangeEvent event) {
				// do nothing
			}

			public void removed(IEclipsePreferences.NodeChangeEvent event) {
				if (event.getChild() == preferencesLookup[PREF_INSTANCE]) {
					preferencesLookup[PREF_INSTANCE] = ((IScopeContext) new InstanceScope()).getNode(StudioCorePlugin.PLUGIN_ID);
					preferencesLookup[PREF_INSTANCE].addPreferenceChangeListener(new EclipsePreferencesListener());
				}
			}
		};
		((IEclipsePreferences) preferencesLookup[PREF_INSTANCE].parent()).addNodeChangeListener(this.instanceNodeListener);
		preferencesLookup[PREF_INSTANCE].addPreferenceChangeListener(this.instancePreferencesListener = new EclipsePreferencesListener());

		// Listen to default preferences node removal from parent in order to
		// refresh stored one
		this.defaultNodeListener = new IEclipsePreferences.INodeChangeListener() {
			public void added(IEclipsePreferences.NodeChangeEvent event) {
				// do nothing
			}

			public void removed(IEclipsePreferences.NodeChangeEvent event) {
				if (event.getChild() == preferencesLookup[PREF_DEFAULT]) {
					preferencesLookup[PREF_DEFAULT] = ((IScopeContext) new DefaultScope()).getNode(StudioCorePlugin.PLUGIN_ID);
				}
			}
		};
		((IEclipsePreferences) preferencesLookup[PREF_DEFAULT].parent()).addNodeChangeListener(this.defaultNodeListener);
	}

	@SuppressWarnings("unchecked")
	public void loadVariables() throws CoreException {
		loadVariables(getDefaultPreferences());
		loadVariables(getInstancePreferences());

		// override persisted values for variables which have a registered
		// initializer
		String[] registeredVariables = getRegisteredVariableNames();
		for (int i = 0; i < registeredVariables.length; i++) {
			String varName = registeredVariables[i];
			this.variables.put(varName, null); // reset variable, but leave its
			// entry in the Map, so it will
			// be part of variable names.
		}
	}

	@SuppressWarnings("unchecked")
	private void loadVariables(IEclipsePreferences instancePreferences) {
		try {
			// only get variable from preferences not set to their default
			String[] propertyNames = instancePreferences.keys();
			int variablePrefixLength = PATH_VARIABLE_PREFERENCES_PREFIX.length();
			for (int i = 0; i < propertyNames.length; i++) {
				String propertyName = propertyNames[i];
				if (propertyName.startsWith(PATH_VARIABLE_PREFERENCES_PREFIX)) {
					String varName = propertyName.substring(variablePrefixLength);
					String propertyValue = instancePreferences.get(propertyName, null);
					if (propertyValue != null) {
						String pathString = propertyValue.trim();

						if (PATH_ENTRY_IGNORE.equals(pathString)) {
							// cleanup old preferences
							instancePreferences.remove(propertyName);
							continue;
						}

						// add variable to table
						IPath varPath = new Path(pathString);
						this.variables.put(varName, varPath);
						this.previousSessionVariables.put(varName, varPath);
					}
				}
			}
		} catch (BackingStoreException e) {
			StudioCorePlugin.log(e);
		}

	}

	/**
	 * Returns the name of the variables for which an CP variable initializer is
	 * registered through an extension point
	 */
	@SuppressWarnings("unchecked")
	public static String[] getRegisteredVariableNames() {

		Plugin jdtCorePlugin = StudioCorePlugin.getDefault();
		if (jdtCorePlugin == null)
			return new String[0];

		ArrayList variableList = new ArrayList(5);
		IExtensionPoint extension = Platform.getExtensionRegistry().getExtensionPoint(StudioCorePlugin.PLUGIN_ID,
				StudioProjectConfigurationManager.PATHVARIABLE_INITIALIZER_EXTPOINT_ID);
		if (extension != null) {
			IExtension[] extensions = extension.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement[] configElements = extensions[i].getConfigurationElements();
				for (int j = 0; j < configElements.length; j++) {
					String varAttribute = configElements[j].getAttribute("variable"); //$NON-NLS-1$
					if (varAttribute != null)
						variableList.add(varAttribute);
				}
			}
		}
		String[] variableNames = new String[variableList.size()];
		variableList.toArray(variableNames);
		return variableNames;
	}

	private IEclipsePreferences getInstancePreferences() {
		// TODO Auto-generated method stub
		return preferencesLookup[PREF_INSTANCE];
	}

	private IEclipsePreferences getDefaultPreferences() {
		// TODO Auto-generated method stub
		return preferencesLookup[PREF_DEFAULT];
	}

	@SuppressWarnings("unchecked")
	public synchronized void variablePut(String variableName, IPath variablePath) {

		// set/unset the initialization in progress
		HashSet initializations = variableInitializationInProgress();
		if (variablePath == VARIABLE_INITIALIZATION_IN_PROGRESS) {
			initializations.add(variableName);

			// do not write out intermediate initialization value
			return;
		} else {
			initializations.remove(variableName);

			// update cache - do not only rely on listener refresh
			if (variablePath == null) {

				this.variables.put(variableName, PATH_ENTRY_IGNORE_PATH);
				// clean other variables caches
				this.variablesWithInitializer.remove(variableName);
				this.deprecatedVariables.remove(variableName);
			} else {
				this.variables.put(variableName, variablePath);
			}
			// discard obsoleted information about previous session
			this.previousSessionVariables.remove(variableName);
		}
	}

	public void variablePreferencesPut(String variableName, IPath variablePath) {
		String variableKey = PATH_VARIABLE_PREFERENCES_PREFIX + variableName;
		if (variablePath == null) {
			getInstancePreferences().remove(variableKey);
		} else {
			getInstancePreferences().put(variableKey, variablePath.toString());
		}
		try {
			getInstancePreferences().flush();
		} catch (BackingStoreException e) {
			// ignore exception
		}
	}

	/*
	 * Returns the set of variable names that are being initialized in the
	 * current thread.
	 */
	@SuppressWarnings("unchecked")
	private HashSet variableInitializationInProgress() {
		HashSet initializations = (HashSet) this.variableInitializationInProgress.get();
		if (initializations == null) {
			initializations = new HashSet();
			this.variableInitializationInProgress.set(initializations);
		}
		return initializations;
	}

	public String[] variableNames() {
		int length = this.variables.size();
		String[] result = new String[length];
		Iterator vars = this.variables.keySet().iterator();
		int index = 0;
		while (vars.hasNext()) {
			result[index++] = (String) vars.next();
		}
		return result;
	}

	public synchronized IPath variableGet(String variableName) {
		// check initialization in progress first
		HashSet initializations = variableInitializationInProgress();
		if (initializations.contains(variableName)) {
			return VARIABLE_INITIALIZATION_IN_PROGRESS;
		}
		return (IPath) this.variables.get(variableName);
	}

	/**
	 * Returns a persisted container from previous session if any
	 */
	public IPath getPreviousSessionVariable(String variableName) {
		IPath previousPath = (IPath) this.previousSessionVariables.get(variableName);
		if (previousPath != null) {
			if (PATH_RESOLVE_VERBOSE_ADVANCED)
				verbose_reentering_variable_access(variableName, previousPath);
			return previousPath;
		}
		return null; // break cycle
	}

	public IPath getResolvedVariablePath(IPath variablePath, boolean usePreviousSession) {

		if (variablePath == null)
			return null;
		int count = variablePath.segmentCount();
		if (count == 0)
			return null;

		// lookup variable
		String variableName = variablePath.segment(0);
		IPath resolvedPath = usePreviousSession ? getPreviousSessionVariable(variableName) : StudioCore.getClasspathVariable(variableName);
		if (resolvedPath == null)
			return null;

		// append path suffix
		if (count > 1) {
			resolvedPath = resolvedPath.append(variablePath.removeFirstSegments(1));
		}
		return resolvedPath;
	}

	private void verbose_reentering_variable_access(String variableName, IPath previousPath) {
		StudioCorePlugin.debug("PathVariable INIT - reentering access to variable during its initialization, will see previous value\n" + //$NON-NLS-1$
				"	variable: " + variableName + '\n' + //$NON-NLS-1$
				"	previous value: " + previousPath); //$NON-NLS-1$
		new Exception("<Fake exception>").printStackTrace(System.out); //$NON-NLS-1$
	}

	/*
	 * Optimize startup case where 1 variable is initialized at a time with the
	 * same value as on shutdown.
	 */
	public boolean variablePutIfInitializingWithSameValue(String[] variableNames, IPath[] variablePaths) {
		if (variableNames.length != 1)
			return false;
		String variableName = variableNames[0];
		IPath oldPath = variableGetDefaultToPreviousSession(variableName);
		if (oldPath == null)
			return false;
		IPath newPath = variablePaths[0];
		if (!oldPath.equals(newPath))
			return false;
		variablePut(variableName, newPath);
		return true;
	}

	private synchronized IPath variableGetDefaultToPreviousSession(String variableName) {
		IPath variablePath = (IPath) this.variables.get(variableName);
		if (variablePath == null)
			return getPreviousSessionVariable(variableName);
		return variablePath;
	}

	public void fireDelta(StudioProjectConfigurationDelta configDelta) {
		System.out.println("Delta:");
		System.out.println(configDelta.toString());
		StudioProjectConfigurationChangeEvent event = new StudioProjectConfigurationChangeEvent(configDelta);
		List<IStudioProjectConfigurationChangeListener> listeners = fConfigurationChangeListeners;
		for (IStudioProjectConfigurationChangeListener listener : listeners) {
			listener.configurationChanged(event);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.IElementChangedListener#elementChanged(org.eclipse.jdt.core.ElementChangedEvent)
	 */
	@Override
	public void elementChanged(ElementChangedEvent event) {
		IJavaElementDelta delta= event.getDelta();
		if (delta != null) {
			//			System.out.println("DELTA--->" + delta);
			traverseJavaElement(delta);
		}
	}

	/**
	 * @param delta {@link IJavaElementDelta}
	 */
	private void traverseJavaElement(IJavaElementDelta delta) {

		IJavaElement element = delta.getElement();

		switch (delta.getKind()) {

		case IJavaElementDelta.ADDED:
			processJavaElementAdded(element);
			break;

		case IJavaElementDelta.REMOVED:
			processJavaElementRemoved(element);
			break;

		case IJavaElementDelta.CHANGED:
			if ((delta.getFlags() & IJavaElementDelta.F_ADDED_TO_CLASSPATH) != 0) {
				processJavaElementAdded(element);
				break;
			}
			if ((delta.getFlags() & IJavaElementDelta.F_REMOVED_FROM_CLASSPATH) != 0) {
				processJavaElementRemoved(element);
				break;
			}

			processJavaElementChanged(element);
			if ((delta.getFlags() & IJavaElementDelta.F_CHILDREN) != 0) {
				//The change was in its children
				//TODO:
			}
			if ((delta.getFlags() & IJavaElementDelta.F_CONTENT) != 0) {
				//The change was in its content
				//TODO:
			}
			/* Others flags can also be checked */
			break;
		}

		IJavaElementDelta[] children = delta.getAffectedChildren();
		for (int i = 0; i < children.length; i++) {
			traverseJavaElement(children[i]);
		}

	}

	/**
	 * @param element
	 */
	@SuppressWarnings("unused")
	private void processJavaElementAdded(IJavaElement element) {

		switch(element.getElementType()) {

		case IJavaElement.JAVA_MODEL:
			IJavaModel model = (IJavaModel)element;
			//TODO:
			break;

		case IJavaElement.JAVA_PROJECT:
//			IJavaProject javaProject = (IJavaProject)element;
			//TODO:
			break;

		case IJavaElement.PACKAGE_FRAGMENT_ROOT:
			try {
				IPackageFragmentRoot javaSrcFolder = (IPackageFragmentRoot)element;
				IJavaProject javaProject = javaSrcFolder.getJavaProject();
				if (!javaProject.getProject().hasNature(StudioProjectNature.STUDIO_NATURE_ID)) {
					break;
				}
//				StudioProjectConfiguration pconfig = getInstance().getProjectConfiguration(javaProject.getElementName());
//				JavaSourceFolderEntry srcFolderEntry = ConfigurationFactory.eINSTANCE.createJavaSourceFolderEntry();
//				srcFolderEntry.setEntryType(LIBRARY_PATH_TYPE.JAVA_SOURCE_FOLDER);
//				srcFolderEntry.setPath(javaSrcFolder.getPath().toString());
//				srcFolderEntry.setReadOnly(true);
//				pconfig.getJavaSourceFolderEntries().add(srcFolderEntry);
//				saveConfiguration(javaProject.getElementName(), pconfig);
			
				if (javaSrcFolder.getPath().getFileExtension() == null && 
						!javaSrcFolder.getPath().segment(javaSrcFolder.getPath().segmentCount() -1).startsWith(".")) {
					List<IPath> paths =  new ArrayList<IPath>();
					paths.add(javaSrcFolder.getPath());
					StudioJavaUtil.addJavaSourceFolderEntry(javaProject, paths, null);
				}
				
			} catch (JavaModelException e) {
				StudioCorePlugin.log(e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case IJavaElement.PACKAGE_FRAGMENT:

			IPackageFragment packageFolder = (IPackageFragment)element;
			//TODO:


			break;

		case IJavaElement.COMPILATION_UNIT:

			ICompilationUnit javaFile = (ICompilationUnit)element;
			//TODO:

			break;

		case IJavaElement.CLASS_FILE:
			break;

		case IJavaElement.TYPE:
			IType type = (IType)element;
			//TODO:
			break;

		case IJavaElement.FIELD:
			IField field = (IField)element;
			//TODO:
			break;

		case IJavaElement.METHOD:
			IMethod method = (IMethod)element;
			//TODO:
			break;

		case IJavaElement.INITIALIZER:
			break;

		case IJavaElement.PACKAGE_DECLARATION:
			IPackageDeclaration packageDeclaration = (IPackageDeclaration)element;
			//TODO:
			break;

		case IJavaElement.IMPORT_CONTAINER:
			IImportContainer importContainer = (IImportContainer)element;
			//TODO:
			break;

		case IJavaElement.IMPORT_DECLARATION:
			IImportDeclaration importDeclaration = (IImportDeclaration)element;
			//TODO:
			break;

		case IJavaElement.LOCAL_VARIABLE:
			ILocalVariable localVariable = (ILocalVariable)element;
			//TODO:
			break;

		case IJavaElement.TYPE_PARAMETER:
			ITypeParameter typeParameter = (ITypeParameter)element;
			//TODO:
			break;

		case IJavaElement.ANNOTATION:
			IAnnotation annotation = (IAnnotation)element;
			//TODO:
			break;
		}
	}


	/**
	 * @param element
	 */
	@SuppressWarnings("unused")
	private void processJavaElementRemoved(IJavaElement element) {

		switch(element.getElementType()) {

		case IJavaElement.JAVA_MODEL:
			IJavaModel model = (IJavaModel)element;
			//TODO:
			break;

		case IJavaElement.JAVA_PROJECT:
//			IJavaProject javaProject = (IJavaProject)element;
			//TODO:
			break;

		case IJavaElement.PACKAGE_FRAGMENT_ROOT:
			try {
				IPackageFragmentRoot javaSrcFolder = (IPackageFragmentRoot)element;
				IJavaProject javaProject = javaSrcFolder.getJavaProject();
				if (!javaProject.getProject().hasNature(StudioProjectNature.STUDIO_NATURE_ID)) {
					break;
				}
				StudioProjectConfiguration pconfig = getInstance().getProjectConfiguration(javaProject.getElementName());
				IPath ipath = javaSrcFolder.getPath();
				String path = ipath.toString();
				EList<JavaSourceFolderEntry> entries = pconfig.getJavaSourceFolderEntries();
				JavaSourceFolderEntry jentry = null;
				for (JavaSourceFolderEntry entry :  entries) {
					if (entry.getPath(true).equals(path)) {
						jentry = entry;
						break;
					}
				}
				if (jentry != null) {
					pconfig.getJavaSourceFolderEntries().remove(jentry);
				}
//				StudioJavaUtil.removeClassPathEntryForPath(ipath, javaProject, IClasspathEntry.CPE_SOURCE);
				saveConfiguration(javaProject.getElementName(), pconfig);

			} catch (JavaModelException e) {
				StudioCorePlugin.log(e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case IJavaElement.PACKAGE_FRAGMENT:

			IPackageFragment packageFolder = (IPackageFragment)element;
			//TODO:


			break;

		case IJavaElement.COMPILATION_UNIT:

			ICompilationUnit javaFile = (ICompilationUnit)element;
			//TODO:

			break;

		case IJavaElement.CLASS_FILE:
			break;

		case IJavaElement.TYPE:
			IType type = (IType)element;
			//TODO:
			break;

		case IJavaElement.FIELD:
			IField field = (IField)element;
			//TODO:
			break;

		case IJavaElement.METHOD:
			IMethod method = (IMethod)element;
			//TODO:
			break;

		case IJavaElement.INITIALIZER:
			break;

		case IJavaElement.PACKAGE_DECLARATION:
			IPackageDeclaration packageDeclaration = (IPackageDeclaration)element;
			//TODO:
			break;

		case IJavaElement.IMPORT_CONTAINER:
			IImportContainer importContainer = (IImportContainer)element;
			//TODO:
			break;

		case IJavaElement.IMPORT_DECLARATION:
			IImportDeclaration importDeclaration = (IImportDeclaration)element;
			//TODO:
			break;

		case IJavaElement.LOCAL_VARIABLE:
			ILocalVariable localVariable = (ILocalVariable)element;
			//TODO:
			break;

		case IJavaElement.TYPE_PARAMETER:
			ITypeParameter typeParameter = (ITypeParameter)element;
			//TODO:
			break;

		case IJavaElement.ANNOTATION:
			IAnnotation annotation = (IAnnotation)element;
			//TODO:
			break;
		}
	}

	/**
	 * @param element
	 */
	@SuppressWarnings("unused")
	private void processJavaElementChanged(IJavaElement element) {

		switch(element.getElementType()) {

		case IJavaElement.JAVA_MODEL:
			IJavaModel model = (IJavaModel)element;
			//TODO:


			break;

		case IJavaElement.JAVA_PROJECT:

			IJavaProject javaProject = (IJavaProject)element;
			//TODO:

			break;

		case IJavaElement.PACKAGE_FRAGMENT_ROOT:

			IPackageFragmentRoot javaSrcFolder = (IPackageFragmentRoot)element;
			//TODO:

			break;

		case IJavaElement.PACKAGE_FRAGMENT:

			IPackageFragment packageFolder = (IPackageFragment)element;
			//TODO:


			break;

		case IJavaElement.COMPILATION_UNIT:

			ICompilationUnit javaFile = (ICompilationUnit)element;
			//TODO:

			break;

		case IJavaElement.CLASS_FILE:
			break;

		case IJavaElement.TYPE:
			IType type = (IType)element;
			//TODO:
			break;

		case IJavaElement.FIELD:
			IField field = (IField)element;
			//TODO:
			break;

		case IJavaElement.METHOD:
			IMethod method = (IMethod)element;
			//TODO:
			break;

		case IJavaElement.INITIALIZER:
			break;

		case IJavaElement.PACKAGE_DECLARATION:
			IPackageDeclaration packageDeclaration = (IPackageDeclaration)element;
			//TODO:
			break;

		case IJavaElement.IMPORT_CONTAINER:
			IImportContainer importContainer = (IImportContainer)element;
			//TODO:
			break;

		case IJavaElement.IMPORT_DECLARATION:
			IImportDeclaration importDeclaration = (IImportDeclaration)element;
			//TODO:
			break;

		case IJavaElement.LOCAL_VARIABLE:
			ILocalVariable localVariable = (ILocalVariable)element;
			//TODO:
			break;

		case IJavaElement.TYPE_PARAMETER:
			ITypeParameter typeParameter = (ITypeParameter)element;
			//TODO:
			break;

		case IJavaElement.ANNOTATION:
			IAnnotation annotation = (IAnnotation)element;
			//TODO:
			break;
		}
	}

}
