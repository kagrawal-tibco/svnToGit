package com.tibco.cep.studio.core;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.util.IOUtils;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ISavedState;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.JavaCore;
import org.osgi.framework.BundleContext;

import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.FunctionsCategoryChangeListener;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.DriverManager;
import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.common.configuration.IStudioProjectConfigurationChangeListener;
import com.tibco.cep.studio.common.resources.ecore.ANTLRResourceFactory;
import com.tibco.cep.studio.core.configuration.ChannelDriverLoader;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectChangeListener;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.functions.model.EMFOntologyFunctionResourceChangeListener;
import com.tibco.cep.studio.core.index.StudioModelManager;
import com.tibco.cep.studio.core.index.update.IStudioModelChangedListener;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener;
import com.tibco.cep.studio.core.index.update.StudioModelChangedEvent;
import com.tibco.cep.studio.core.index.update.StudioModelDelta;
import com.tibco.cep.studio.core.mapper.EntitySchemaCache;
import com.tibco.cep.studio.core.mapper.EntitySchemaCacheManager;
import com.tibco.cep.studio.core.preferences.StudioCorePreferenceConstants;
import com.tibco.cep.studio.core.projlib.ProjectLibraryConfigurationChangeListener;
import com.tibco.cep.studio.core.repo.AdapterCacheManager;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.repo.TnsCacheManager;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.StudioJavaUtil;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.thirdparty.DelegatePlugin;

/**
 * The activator class controls the plug-in life cycle
 */
public class StudioCorePlugin extends Plugin implements IPropertyChangeListener {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.studio.core";
	
	// The id of the global ignore extension point
	public static final String IGNORE_SHARED_ARCHIVE_RESOURCE_PATTERNS_EXTENSION = "ignoreSharedArchiveResourcePatterns"; //$NON-NLS-1$;



	// whether or not to print debug statements in this code.  For internal use only
	public static boolean fDebug = false;

	// The shared instance
	private static StudioCorePlugin plugin;

	private IndexResourceChangeListener fResourceChangeListener = new IndexResourceChangeListener();
//	private ExternalProjectsImportResourceChangeListener projectsImportListener = new ExternalProjectsImportResourceChangeListener();
	private EMFOntologyFunctionResourceChangeListener fOntologyFunctionRCL = EMFOntologyFunctionResourceChangeListener.getInstance();
	private static StudioModelManager fModelManager = new StudioModelManager();
	private static List<IStudioModelChangedListener> fModelChangedListeners = new ArrayList<IStudioModelChangedListener>();
	private static List<FunctionsCategoryChangeListener> fCategoryChangeListeners = new ArrayList<FunctionsCategoryChangeListener>();
	private static StudioProjectChangeListener fStudioProjectChangeListener;
	private static IStudioProjectConfigurationChangeListener fStudioConfigChangeListener= new ProjectLibraryConfigurationChangeListener();
	
	private static AdapterCacheManager fAdapterCacheManager = new AdapterCacheManager();
	private static TnsCacheManager fTnsCacheManager = new TnsCacheManager();
	private static EntitySchemaCacheManager fSchemaCacheManager = new EntitySchemaCacheManager();
	private DriverManager driverManager;
	private static HashMap<String, UIAgent> fAgents = new HashMap<String, UIAgent>();
	
    public static boolean DEBUG = false;
    
	public boolean stateMachineBundleInstalled = false;

	private ANTLRResourceFactory factory;

	
	/**
	 * The constructor
	 */
	public StudioCorePlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
//		StudioConfig.init();
		
		//Activate the Delegator plugin, so that classpath is initialized correctly
		DelegatePlugin.activatePlugin();
		
		//setting the default setting for the Studio Warnings
		StudioCorePlugin.getDefault().getPluginPreferences().setDefault(StudioCorePreferenceConstants.STUDIO_SHOW_WARNINGS, true);
		String value = CommonUtil.getUpdatedValues(CommonUtil.getAllExtensions());
		StudioCorePlugin.getDefault().getPluginPreferences().setDefault(StudioCorePreferenceConstants.STUDIO_SHOW_WARNINGS_PATTERNS, value);
		
		try {
			InputStream resourceInputStream = getClass().getResourceAsStream("/doc-template/root/stylesheet.css");  
			if(resourceInputStream!=null){
				byte[] bytes = IOUtils.toByteArray(resourceInputStream);
				DefaultScope.INSTANCE.getNode(PLUGIN_ID).put(StudioCorePreferenceConstants.DOC_GEN_CSS_STRING, new String(bytes));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("concept", getResourceFactory());
        ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("event", getResourceFactory());

		/**
		 * Order of these listeners should not be changed
		 */
//		ResourcesPlugin.getWorkspace().addResourceChangeListener(projectsImportListener, IResourceChangeEvent.POST_CHANGE | IResourceChangeEvent.PRE_DELETE);
        ResourcesPlugin.getWorkspace().addResourceChangeListener(fResourceChangeListener , IResourceChangeEvent.PRE_DELETE | IResourceChangeEvent.POST_CHANGE);
        ResourcesPlugin.getWorkspace().addResourceChangeListener(fOntologyFunctionRCL , IResourceChangeEvent.PRE_DELETE | IResourceChangeEvent.POST_CHANGE);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(fTnsCacheManager, IResourceChangeEvent.PRE_DELETE | IResourceChangeEvent.POST_CHANGE);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(fAdapterCacheManager, IResourceChangeEvent.PRE_DELETE | IResourceChangeEvent.POST_CHANGE);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(StudioProjectConfigurationManager.getInstance(), IResourceChangeEvent.POST_CHANGE);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(fSchemaCacheManager, IResourceChangeEvent.PRE_DELETE | IResourceChangeEvent.POST_CHANGE);
		
		JavaCore.addElementChangedListener(StudioProjectConfigurationManager.getInstance(), ElementChangedEvent.POST_CHANGE);
		
		addDesignerModelChangedListener(fTnsCacheManager);
		addDesignerModelChangedListener(fAdapterCacheManager);
		addDesignerModelChangedListener(fSchemaCacheManager);
		StudioProjectConfigurationManager.getInstance().addConfigurationChangeListener(fStudioConfigChangeListener);
//		StudioProjectConfigurationManager.getInstance().addConfigurationChangeListener(fResourceChangeListener);
		StudioProjectConfigurationManager.getInstance().addConfigurationChangeListener(fOntologyFunctionRCL);
		fStudioProjectChangeListener = new StudioProjectChangeListener(StudioProjectConfigurationManager.getInstance());
		ResourcesPlugin.getWorkspace().addResourceChangeListener(fStudioProjectChangeListener,IResourceChangeEvent.POST_CHANGE);
	      // Load all Project Libraries (after listener has been added)
       // ProjectLibraryResourceProvider.getInstance().loadDependencies();
//		StudioProjectConfigurationManager.getInstance();
		
		driverManager = ChannelFactory.eINSTANCE.createDriverManager();
		driverManager.registerDrivers(ChannelDriverLoader.loadDrivers());
		stateMachineBundleInstalled = findStateMachineBundleInstalled(context);
		
		//Migrate channels if required
//		runChannelMigrator(); // this is now done via the 4.0->5.0 import wizard
		//load path variables
//		StudioProjectConfigurationManager.getInstance().startup();
		AddSaveParticipantJob job = new AddSaveParticipantJob(plugin, fModelManager);
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
        job.schedule();
        
	}
	
	
	private Object getResourceFactory() {
		if (factory == null) {
			factory = new ANTLRResourceFactory();
		}
		return factory;
	}


	public static class AddSaveParticipantJob extends Job {
		private StudioCorePlugin plugin;
		private StudioModelManager modelManager;

		public AddSaveParticipantJob(StudioCorePlugin plugin,StudioModelManager modelManager) {
			super("AddSaveParticipant");
			this.plugin = plugin;
			this.modelManager = modelManager;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			ISavedState lastState;
			try {
				StudioJavaUtil.loadJavaConfiguration();
				lastState = ResourcesPlugin.getWorkspace()
				    	.addSaveParticipant(plugin.PLUGIN_ID, modelManager);
				if (lastState == null) {
					fModelManager.loadAllIndexes();
					return Status.OK_STATUS;
				}
				fModelManager.loadAllIndexes();
			} catch (Exception e) {
				return new Status(IStatus.ERROR,plugin.PLUGIN_ID,"Failed to add Save participant",e);
			}
			return Status.OK_STATUS;
		}
		
		
	}
	

	/**
	 * @param context
	 * @return
	 */
	private boolean findStateMachineBundleInstalled(BundleContext context){
		return Platform.getBundle("com.tibco.cep.studio.ui.statemachine") != null;
//		Bundle[] bundles = context.getBundles();
//		for(Bundle b:bundles){
//			if(b.getLocation().contains("com.tibco.cep.studio.ui.statemachine")){
//				return true;
//			}
//		}
//		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		StudioCore.shutdown();
		plugin = null;
		if (fResourceChangeListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(fResourceChangeListener);
		}
		if (fOntologyFunctionRCL != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(fOntologyFunctionRCL);
		}
		if (fTnsCacheManager != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(fTnsCacheManager);
			removeDesignerModelChangedListener(fTnsCacheManager);
		}
		if (fAdapterCacheManager != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(fAdapterCacheManager);
			removeDesignerModelChangedListener(fAdapterCacheManager);
		}
		if (fResourceChangeListener != null) {
//			StudioProjectConfigurationManager.getInstance().removeConfigurationChangeListener(fResourceChangeListener);
		}
		if (fOntologyFunctionRCL != null) {
			StudioProjectConfigurationManager.getInstance().removeConfigurationChangeListener(fOntologyFunctionRCL);
		}
		if (fStudioProjectChangeListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(fStudioProjectChangeListener);
		}
		if(fStudioConfigChangeListener != null){
			StudioProjectConfigurationManager.getInstance().removeConfigurationChangeListener(fStudioConfigChangeListener);
		}
		if (fSchemaCacheManager != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(fSchemaCacheManager);
			removeDesignerModelChangedListener(fSchemaCacheManager);
		}

		ResourcesPlugin.getWorkspace().removeResourceChangeListener(StudioProjectConfigurationManager.getInstance());
	}
	
	/**
	 * Run channel migrator for all projects
	 * @throws Exception
	 */
//	private void runChannelMigrator() throws Exception {
//		IProject[] projects = CommonUtil.getAllStudioProjects();
//
//		try {
//			for (IProject project : projects) {
//				File locationFile = project.getLocation().toFile();
//				ChannelMigrator.migrateChannels(locationFile, "channel", true);
//			}
//		} catch (Exception e) {
//			log(e);
//		}
//	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static StudioCorePlugin getDefault() {
		return plugin;
	}

	public static StudioModelManager getDesignerModelManager() {
		return fModelManager;
	}

    /**
     * Hack to make the XML Mapper work.
     * @return
     */
//    public static DesignerDocument getDesignerDocument() {
//        return doc;
//    }
	
    public synchronized static UIAgent getUIAgent(String projectName) {
    	if (!fAgents.containsKey(projectName)) {
    		
    		UIAgent agent = new StudioUIAgent(projectName);
    		fAgents.put(projectName, agent);
    	}
    	return fAgents.get(projectName);
    }

    public void addFunctionsCategoryChangeListener(FunctionsCategoryChangeListener listener) {
		if (!fCategoryChangeListeners.contains(listener)) {
			fCategoryChangeListeners.add(listener);
		}
	}
	
	public void removeFunctionsCategoryChangeListener(FunctionsCategoryChangeListener listener) {
		if (fCategoryChangeListeners.contains(listener)) {
			fCategoryChangeListeners.remove(listener);
		}
	}
	
	public void fireCatalogChangedEvent(FunctionsCategory category) {
		synchronized (fCategoryChangeListeners) {
			for (FunctionsCategoryChangeListener listener : fCategoryChangeListeners) {
				try {
					listener.onCategoryChange(category);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void addDesignerModelChangedListener(IStudioModelChangedListener listener) {
		synchronized (fModelChangedListeners) {
			if (!fModelChangedListeners.contains(listener)) {
				fModelChangedListeners.add(listener);
			}
		}
	}
	
	public void removeDesignerModelChangedListener(IStudioModelChangedListener listener) {
		synchronized (fModelChangedListeners) {
			if (fModelChangedListeners.contains(listener)) {
				fModelChangedListeners.remove(listener);
			}
		}
	}
	
	public DriverManager getDriverManager() {
		return driverManager;
	}

	public void fireModelChangedEvent(StudioModelDelta delta) {
		synchronized (fModelChangedListeners) {
			StudioModelChangedEvent changedEvent = new StudioModelChangedEvent(delta);
			for (IStudioModelChangedListener listener : fModelChangedListeners) {
				// protect against any one listener throwing exception, causing
				// other listeners not to be notified
				try {
					listener.modelChanged(changedEvent);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
	

	
	public static EMFTnsCache getCache(String projectName) {
		return fTnsCacheManager.getCache(projectName);
	}
	
	public static void clearCaches() {
		fTnsCacheManager.clearCaches();
		fAdapterCacheManager.clearCaches();
		fSchemaCacheManager.clearCaches();
	}

	public static void removeCache(String projectName) {
		fTnsCacheManager.removeCache(projectName);
		fAdapterCacheManager.removeCache(projectName);
		fSchemaCacheManager.removeCache(projectName);
		FunctionsCatalogManager.getInstance().clearOntologyCategory(projectName);
		AddonUtil.adaptersMap.remove(projectName);
	}
	
	/**
	 * @param className
	 * @param message
	 */
	public static void debug(String className, String message, Object... args) {
		String debugMessage = MessageFormat.format(message, args);
		debug(MessageFormat.format("[{0}] {1}", className, debugMessage));
	}
	
	/**
	 * If the debug flag is set the specified message is printed to the console
	 * 
	 * @param message
	 */
	public static void debug(String message) {
		if (getDefault() != null && getDefault().isDebugging()) { // run eclipse with -debug option
			System.err.println(MessageFormat.format("[{0}] {1}",PLUGIN_ID,message));
		}
	}
	
	public static void debug(String message, Object...args) {
		if (getDefault().isDebugging()) { // run eclipse with -debug option
			String debugMessage = MessageFormat.format(message, args);
			System.err.println(MessageFormat.format("[{0}] {1}",PLUGIN_ID, debugMessage));
		}
	}
	
	public static void log(String msg) {
		log(msg, null);
	}

	public static void log(String msg, Throwable e) {
		getDefault().getLog().log(new Status(Status.INFO, PLUGIN_ID, Status.OK, msg, e));
	}
	
	/**
	 * Logs the specified status with this plug-in's log.
	 * 
	 * @param status status to log
	 */
	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}
	
	/**
	 * Logs the specified throwable with this plug-in's log.
	 * 
	 * @param t throwable to log
	 */
	public static void log(Throwable t) {
		log(newErrorStatus(MessageFormat.format("Error logged from {0} Plugin: ",PLUGIN_ID), t)); //$NON-NLS-1$
	}
	
	/**
	 * Logs an internal error with the specified message.
	 * 
	 * @param message the error message to log
	 */
	public static void logErrorMessage(String message) {
		// this message is intentionally not internationalized, as an exception may
		// be due to the resource bundle itself
		log(newErrorStatus(MessageFormat.format("Internal message logged from {0} Plugin: {1}",PLUGIN_ID,message), null)); //$NON-NLS-1$
	}
	
	/**
	 * Returns a new error status for this plug-in with the given message
	 * @param message the message to be included in the status
	 * @param exception the exception to be included in the status or <code>null</code> if none
	 * @return a new error status
	 */
	public static IStatus newErrorStatus(String message, Throwable exception) {
		return new Status(IStatus.ERROR, PLUGIN_ID, message, exception);
	}

	/**
	 * @return
	 */
	public boolean isStateMachineBundleInstalled() {
		return stateMachineBundleInstalled;
	}
	
	// Preference property change listener
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	public static EntitySchemaCache getSchemaCache(String projectName) {
		return fSchemaCacheManager.getCache(projectName);
	}

}