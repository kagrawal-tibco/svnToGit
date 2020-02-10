package com.tibco.cep.bpmn.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ISavedState;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;

import com.tibco.cep.bpmn.core.index.BpmnModelManager;
import com.tibco.cep.bpmn.core.index.update.BpmnIndexResourceChangeListener;
import com.tibco.cep.bpmn.core.index.update.BpmnModelChangedEvent;
import com.tibco.cep.bpmn.core.index.update.BpmnModelDelta;
import com.tibco.cep.bpmn.core.index.update.BpmnTnsCacheManager;
import com.tibco.cep.bpmn.core.index.update.IBpmnModelChangedListener;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonModelUtils;
import com.tibco.cep.studio.common.configuration.BpmnProcessSettings;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.NamePrefix;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.repo.EMFTnsCache;




/**
 * BPMN Core plugin class
 * @author pdhar
 *
 */
public class BpmnCorePlugin extends Plugin {
	
	// The plug-in id
	public static final String PLUGIN_ID="com.tibco.cep.bpmn.core";
	
	
	public static boolean DEBUG = false;
	
	public static BpmnCorePlugin plugin;
	
	private BpmnModelManager bpmnModelManager;
	
	private static List<IBpmnModelChangedListener> fModelChangedListeners = new ArrayList<IBpmnModelChangedListener>();
	
	private static BpmnTnsCacheManager fTnsCacheManager = new BpmnTnsCacheManager();
	 
	private BpmnIndexResourceChangeListener fResourceChangeListener = new BpmnIndexResourceChangeListener();
	// Preferences
	public final IEclipsePreferences[] preferencesLookup = new IEclipsePreferences[2];
	static final int PREF_INSTANCE = 0;
	static final int PREF_DEFAULT = 1;
	
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
		public void removed(IEclipsePreferences.NodeChangeEvent event) {
			if (event.getChild() == BpmnCorePlugin.this.preferencesLookup[PREF_INSTANCE]) {
				BpmnCorePlugin.this.preferencesLookup[PREF_INSTANCE] = InstanceScope.INSTANCE.getNode(PLUGIN_ID);
				BpmnCorePlugin.this.preferencesLookup[PREF_INSTANCE].addPreferenceChangeListener(new EclipsePreferencesListener());
			}
		}
	};
	IEclipsePreferences.INodeChangeListener defaultNodeListener = new IEclipsePreferences.INodeChangeListener() {
		public void added(IEclipsePreferences.NodeChangeEvent event) {
			// do nothing
		}
		public void removed(IEclipsePreferences.NodeChangeEvent event) {
			if (event.getChild() == BpmnCorePlugin.this.preferencesLookup[PREF_DEFAULT]) {
				BpmnCorePlugin.this.preferencesLookup[PREF_DEFAULT] = DefaultScope.INSTANCE.getNode(PLUGIN_ID);
			}
		}
	};
	/**
	 * Listener on properties changes.
	 */
	IEclipsePreferences.IPreferenceChangeListener propertyListener;
	IEclipsePreferences.IPreferenceChangeListener resourcesPropertyListener;
	
	/**
	 * Update the classpath variable cache
	 */
	public static class EclipsePreferencesListener implements IEclipsePreferences.IPreferenceChangeListener {

		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public BpmnCorePlugin() {
		plugin = this;
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static BpmnCorePlugin getDefault() {
		return plugin;
	}
	

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		BpmnMetaModel.getInstance(); // start the metamodel init
		
		bpmnModelManager = new BpmnModelManager();
		
		initializePreferences();

		ResourcesPlugin.getWorkspace().addResourceChangeListener(fTnsCacheManager, IResourceChangeEvent.PRE_DELETE | IResourceChangeEvent.POST_CHANGE);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				fResourceChangeListener,
				IResourceChangeEvent.PRE_DELETE
						| IResourceChangeEvent.POST_CHANGE);
		

		ISavedState lastState = ResourcesPlugin.getWorkspace()
				.addSaveParticipant(PLUGIN_ID, bpmnModelManager);
		if (lastState == null) {
			bpmnModelManager.loadAllIndexes();
			return;
		}
		bpmnModelManager.loadAllIndexes();
	}

	

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		try {
			
		} finally {
			plugin = null;
			bpmnModelManager = null;
			super.stop(context);
			if(fResourceChangeListener != null) {
				ResourcesPlugin.getWorkspace().removeResourceChangeListener(fResourceChangeListener);
			}
			if(fTnsCacheManager != null) {
				ResourcesPlugin.getWorkspace().removeResourceChangeListener(fTnsCacheManager);
			}
		}
	}
	
	
	
	
	
	/**
	 * @return
	 */
	public BpmnModelManager getBpmnModelManager() {
		return bpmnModelManager;
	}


	public IEclipsePreferences[] getPreferencesLookup() {
		return preferencesLookup;
	}
	
	
	/**
	 * Initialize preferences lookups for JavaCore plug-in.
	 */
	public void initializePreferences() {

		// Create lookups
		this.preferencesLookup[PREF_INSTANCE] = InstanceScope.INSTANCE.getNode(PLUGIN_ID);
		this.preferencesLookup[PREF_DEFAULT] = DefaultScope.INSTANCE.getNode(PLUGIN_ID);

		// Listen to instance preferences node removal from parent in order to refresh stored one
		this.instanceNodeListener = new IEclipsePreferences.INodeChangeListener() {
			public void added(IEclipsePreferences.NodeChangeEvent event) {
				// do nothing
			}
			public void removed(IEclipsePreferences.NodeChangeEvent event) {
				if (event.getChild() == BpmnCorePlugin.this.preferencesLookup[PREF_INSTANCE]) {
					BpmnCorePlugin.this.preferencesLookup[PREF_INSTANCE] = InstanceScope.INSTANCE.getNode(PLUGIN_ID);
					BpmnCorePlugin.this.preferencesLookup[PREF_INSTANCE].addPreferenceChangeListener(new EclipsePreferencesListener());
				}
			}
		};
		((IEclipsePreferences) this.preferencesLookup[PREF_INSTANCE].parent()).addNodeChangeListener(this.instanceNodeListener);
		this.preferencesLookup[PREF_INSTANCE].addPreferenceChangeListener(this.instancePreferencesListener = new EclipsePreferencesListener());

		// Listen to default preferences node removal from parent in order to refresh stored one
		this.defaultNodeListener = new IEclipsePreferences.INodeChangeListener() {
			public void added(IEclipsePreferences.NodeChangeEvent event) {
				// do nothing
			}
			public void removed(IEclipsePreferences.NodeChangeEvent event) {
				if (event.getChild() == BpmnCorePlugin.this.preferencesLookup[PREF_DEFAULT]) {
					BpmnCorePlugin.this.preferencesLookup[PREF_DEFAULT] = DefaultScope.INSTANCE.getNode(PLUGIN_ID);
				}
			}
		};
		((IEclipsePreferences) this.preferencesLookup[PREF_DEFAULT].parent()).addNodeChangeListener(this.defaultNodeListener);
	}

	/**
	 * @param projectName
	 * @return
	 */
	public static BpmnProcessSettings getBpmnProjectConfiguration(String projectName) {
		// load all project configurations from disk
		StudioProjectConfiguration config = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
		BpmnProcessSettings settings = config.getBpmnProcessSettings();
		IPreferencesService pservice = Platform.getPreferencesService();
		if(settings == null) {
			settings = StudioProjectConfigurationManager.getBpmnProcessConfiguration(config);
		} 
		if(settings.getBuildFolder() == null) {
			String propertyValue = pservice.get(BpmnCoreConstants.PREF_CODEGEN_FOLDER, BpmnCoreConstants.PREF_CODEGEN_FOLDER, BpmnCorePlugin.getDefault().getPreferencesLookup());
			String defaultFolder = new Path(propertyValue).toPortableString();
			settings.setBuildFolder(defaultFolder);
		}
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_FOLDER, BpmnCommonModelUtils.CODE_GEN_FOLDER_PATH);
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_PROCESS_PREFIX, BpmnCommonModelUtils.BPMN_PROCESS_PREFIX);
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_RULE_PREFIX, BpmnCommonModelUtils.BPMN_RULE_PREFIX);
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_RULE_FUNCTION_PREFIX, BpmnCommonModelUtils.BPMN_RULE_FUNCTION_PREFIX);
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_CONCEPT_PREFIX, BpmnCommonModelUtils.BPMN_CONCEPT_PREFIX);
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_EVENT_PREFIX, BpmnCommonModelUtils.BPMN_EVENT_PREFIX);
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_TIME_EVENT_PREFIX, BpmnCommonModelUtils.BPMN_TIME_EVENT_PREFIX);
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_SCORECARD_PREFIX, BpmnCommonModelUtils.BPMN_SCORECARD_PREFIX);
		if(settings.getConceptPrefix() == null) {
			String propertyValue = pservice.get(BpmnCoreConstants.PREF_CODEGEN_CONCEPT_PREFIX, BpmnCommonModelUtils.BPMN_CONCEPT_PREFIX, BpmnCorePlugin.getDefault().getPreferencesLookup());
			settings.setConceptPrefix(propertyValue);
		}
		if(settings.getProcessPrefix() == null) {
			String propertyValue = pservice.get(BpmnCoreConstants.PREF_CODEGEN_PROCESS_PREFIX, BpmnCommonModelUtils.BPMN_PROCESS_PREFIX, BpmnCorePlugin.getDefault().getPreferencesLookup());
			settings.setConceptPrefix(propertyValue);
		}
		if(settings.getRulePrefix() == null) {
			String propertyValue = pservice.get(BpmnCoreConstants.PREF_CODEGEN_RULE_PREFIX, BpmnCommonModelUtils.BPMN_RULE_PREFIX, BpmnCorePlugin.getDefault().getPreferencesLookup());
			settings.setRulePrefix(propertyValue);
		}
		if(settings.getRuleFunctionPrefix() == null) {
			String propertyValue = pservice.get(BpmnCoreConstants.PREF_CODEGEN_RULE_FUNCTION_PREFIX, BpmnCommonModelUtils.BPMN_RULE_FUNCTION_PREFIX, BpmnCorePlugin.getDefault().getPreferencesLookup());
			settings.setRuleFunctionPrefix(propertyValue);
		}
		if(settings.getEventPrefix() == null) {
			String propertyValue = pservice.get(BpmnCoreConstants.PREF_CODEGEN_EVENT_PREFIX, BpmnCommonModelUtils.BPMN_EVENT_PREFIX, BpmnCorePlugin.getDefault().getPreferencesLookup());
			settings.setEventPrefix(propertyValue);
		}
		if(settings.getTimeEventPrefix() == null) {
			String propertyValue = pservice.get(BpmnCoreConstants.PREF_CODEGEN_TIME_EVENT_PREFIX, BpmnCommonModelUtils.BPMN_TIME_EVENT_PREFIX, BpmnCorePlugin.getDefault().getPreferencesLookup());
			settings.setTimeEventPrefix(propertyValue);
		}
		if(settings.getScorecardPrefix() == null) {
			String propertyValue = pservice.get(BpmnCoreConstants.PREF_CODEGEN_SCORECARD_PREFIX, BpmnCommonModelUtils.BPMN_SCORECARD_PREFIX, BpmnCorePlugin.getDefault().getPreferencesLookup());
			settings.setScorecardPrefix(propertyValue);
		}
		if(settings.getNamePrefixes() == null || settings.getNamePrefixes().isEmpty()) {
			
			 try {
				String [] propertyNames = pservice.getRootNode().node(DefaultScope.SCOPE).node("com.tibco.cep.bpmn.core").keys();
				for(String propertyName:propertyNames) {
					if(propertyName.startsWith(BpmnCoreConstants.BPMN_NAME_PREFIX+".")){
						String pfx = pservice
								.get(propertyName,
										"Unknown",
										BpmnCorePlugin.getDefault()
												.getPreferencesLookup());
						String typeName = propertyName.substring(propertyName.indexOf(".")+1);
						NamePrefix prefix = ConfigurationFactory.eINSTANCE.createNamePrefix();
						prefix.setName(typeName);
						prefix.setPrefix(pfx);
						settings.getNamePrefixes().add(prefix);
					}
				}
			} catch (BackingStoreException e) {
				BpmnCorePlugin.log(e);
			}
		}
		return settings;
	}
	
	public static EMFTnsCache getCache(String projectName, boolean waitForProcessLoad) {
		return fTnsCacheManager.getCache(projectName, waitForProcessLoad);
	}
	
	public static EMFTnsCache getCache(String projectName) {
		return fTnsCacheManager.getCache(projectName, false);
	}
	
	public static void removeCache(String projectName) {
		fTnsCacheManager.removeCache(projectName);
	}
	

	/**
	 * Return a <code>java.io.File</code> object that corresponds to the specified
	 * <code>IPath</code> in the plugin directory.
	 */
	public static File getFileInPlugin(IPath path) {
		try {
			URL installURL =
				new URL(getDefault().getBundle().getEntry("/"), path.toString()); //$NON-NLS-1$
			URL localURL = FileLocator.toFileURL(installURL);
			return new File(localURL.getFile());
		} catch (IOException ioe) {
			return null;
		}
	}
	/****************************************************** DEBUG ******************************/
	/**
	 * @param className
	 * @param message
	 */
	public static void debug(String className, String message) {
		debug(MessageFormat.format("[{0}] {1}", className,message));
		
	}
	
	/**
	 * If the debug flag is set the specified message is printed to the console
	 * 
	 * @param message
	 */
	public static void debug(String message) {
		if(getDefault() != null && getDefault().isDebugging()) { // run eclipse with -debug option
			System.out.println(MessageFormat.format("[{0}] {1}",PLUGIN_ID,message));
		}
	}
	
	public static void log(String msg) {
		log(msg, null);
	}

	public static void log(String msg, Exception e) {
		if (getDefault() != null)
			getDefault().getLog().log(
					new Status(Status.INFO, PLUGIN_ID, Status.OK, msg, e));
	}
	
	/**
	 * Logs the specified status with this plug-in's log.
	 * 
	 * @param status status to log
	 */
	public static void log(IStatus status) {
		if (getDefault() != null)
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


	public static String getUniqueIdentifier() {
		return PLUGIN_ID;
	}
	
	public void addBpmnModelChangedListener(IBpmnModelChangedListener listener) {
		synchronized (fModelChangedListeners) {
			if (!fModelChangedListeners.contains(listener)) {
				fModelChangedListeners.add(listener);
			}
		}
	}
	
	public void removeBpmnModelChangedListener(IBpmnModelChangedListener listener) {
		synchronized (fModelChangedListeners) {
			if (fModelChangedListeners.contains(listener)) {
				fModelChangedListeners.remove(listener);
			}
		}
	}

	public void fireModelChangedEvent(BpmnModelDelta delta) {
		synchronized (fModelChangedListeners) {
			BpmnModelChangedEvent changedEvent = new BpmnModelChangedEvent(delta);
			for (IBpmnModelChangedListener listener : fModelChangedListeners) {
				// protect against any one listener throwing exception, causing
				// other listeners not to be notified
				try {
					listener.modelChanged(changedEvent);
				} catch (Exception e) {
					log(e);
				}
			}
		}
		
	}

	public static void saveBpmnProjectConfiguration(String projectName) throws Exception {
		StudioProjectConfigurationManager.getInstance().saveConfiguration(projectName);
		
	}

	

}
