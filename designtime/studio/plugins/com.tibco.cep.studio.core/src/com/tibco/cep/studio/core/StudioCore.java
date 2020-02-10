package com.tibco.cep.studio.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;

import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.cep.studio.core.configuration.PathVariableInitializer;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.preferences.StudioCorePreferenceConstants;
import com.tibco.cep.studio.core.util.IIgnoreInfo;
import com.tibco.cep.studio.core.util.Messages;
import com.tibco.cep.studio.core.util.StringPatternMatcher;
import com.tibco.cep.studio.core.util.packaging.impl.DefaultRuntimeClassesPackager;

public class StudioCore {
	
	private static final String STUDIO_COMMON_PLUGIN_ID = "com.tibco.cep.studio.common";//$NON-NLS-1$
	public static final String SHARED_RESOURCE_EXTENSIONS = "sharedResourceExtensions";//$NON-NLS-1$
	public static final String EXPORT_LIBRARY_EXTENSIONS = "exportLibraryExtensions";//$NON-NLS-1$
	public static final String TEST_DATA_EXTENSIONS = "testDataExtensions";//$NON-NLS-1$
	protected static SortedMap<String,Boolean> globalIgnore, pluginIgnore;
	private static StringPatternMatcher[] ignoreMatchers;
	
	public static final String HTTP_SSL_SERVER_ENABLED_PROTOCOLS = "TLSv1.1,TLSv1.2,TLSv1.3";
	public static final String HTTP_SSL_SERVER_CIPHERS = "TLS_AES_128_GCM_SHA256,TLS_AES_256_GCM_SHA384,TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384,TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,TLS_DHE_RSA_WITH_AES_256_GCM_SHA384,TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256,TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256,TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384,TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,TLS_RSA_WITH_AES_256_CBC_SHA256,TLS_RSA_WITH_AES_128_CBC_SHA256,TLS_RSA_WITH_AES_256_GCM_SHA384,TLS_RSA_WITH_AES_128_GCM_SHA256,TLS_DHE_RSA_WITH_AES_256_CBC_SHA256,TLS_DHE_RSA_WITH_AES_128_CBC_SHA256,TLS_DHE_DSS_WITH_AES_128_CBC_SHA,TLS_DHE_DSS_WITH_AES_128_CBC_SHA256,TLS_DHE_DSS_WITH_AES_128_GCM_SHA256,TLS_DHE_DSS_WITH_AES_256_CBC_SHA,TLS_DHE_DSS_WITH_AES_256_CBC_SHA256,TLS_DHE_DSS_WITH_AES_256_GCM_SHA384";
	
	public static String[] validCodeExtensions = {
		  CommonIndexUtils.RULE_EXTENSION,
		  CommonIndexUtils.RULEFUNCTION_EXTENSION,
		  CommonIndexUtils.EVENT_EXTENSION,
		  CommonIndexUtils.STATEMACHINE_EXTENSION,
		  CommonIndexUtils.PROCESS_EXTENSION,
		  CommonIndexUtils.JAVA_EXTENSION
		  };
	private static String defaultCompiler;
	private static String sourceJavaVersion;
	private static String targetJavaVersion;
	
	
	public static String[] getCodeExtensions() {
		return validCodeExtensions;
	}
	
	
	
	/**
	 * Returns the list of global ignores.
	 */
	public synchronized static IIgnoreInfo[] getAllIgnores() {
		// The ignores are cached and when the preferences change the
		// cache is cleared. This makes it faster to lookup without having
		// to re-parse the preferences.
		initializeIgnores();
		IIgnoreInfo[] result = getIgnoreInfo(globalIgnore);
		return result;
	}
	
	public static IIgnoreInfo[] getDefaultIgnores() {
		SortedMap gIgnore = new TreeMap();
		SortedMap pIgnore = new TreeMap();
		initializePluginIgnores(pIgnore, gIgnore);
		return getIgnoreInfo(gIgnore);
	}

	private static void initializeIgnores() {
		if (globalIgnore == null) {
			globalIgnore = new TreeMap();
			pluginIgnore = new TreeMap();
			ignoreMatchers = null;
			try {
				readIgnoreState();
			} catch (Exception e) {
				StudioCorePlugin.log(Messages.getString("pref.initialize.error"), e); //$NON-NLS-1$
			}
			initializePluginIgnores(pluginIgnore, globalIgnore);
		}
	}

	private static IIgnoreInfo[] getIgnoreInfo(Map gIgnore) {
		IIgnoreInfo[] result = new IIgnoreInfo[gIgnore.size()];
		Iterator e = gIgnore.entrySet().iterator();
		int i = 0;
		while (e.hasNext() ) {
			Map.Entry entry = (Entry) e.next();
			final String pattern = (String) entry.getKey();
			final boolean enabled = ((Boolean)entry.getValue()).booleanValue();
			result[i++] = new IIgnoreInfo() {
				private String p = pattern;
				private boolean e1 = enabled;
				public String getPattern() {
					return p;
				}
				public boolean getEnabled() {
					return e1;
				}
			};
		}
		return result;
	}

	private synchronized static StringPatternMatcher[] getStringMatchers() {
		if (ignoreMatchers==null) {
			IIgnoreInfo[] ignorePatterns = getAllIgnores();
			ArrayList matchers = new ArrayList(ignorePatterns.length);
			for (int i = 0; i < ignorePatterns.length; i++) {
				if (ignorePatterns[i].getEnabled()) {
					matchers.add(new StringPatternMatcher(ignorePatterns[i].getPattern(), true, false));
				}
			}
			ignoreMatchers = new StringPatternMatcher[matchers.size()];
			ignoreMatchers = (StringPatternMatcher[]) matchers.toArray(ignoreMatchers);
		}
		return ignoreMatchers;
	}
	
	private static boolean matchesEnabledIgnore(IResource resource) {
		StringPatternMatcher[] matchers = getStringMatchers();
		for (int i = 0; i < matchers.length; i++) {
			String resourceName = resource.getName();
			if(matchers[i].isPathPattern()) {
				resourceName = resource.getFullPath().toString();
			}
			if (matchers[i].match(resourceName)) return true;
		}
		return false;
	}
	/**
	 * Returns whether the given file or folder with its content should be ignored.
	 * 
	 * This method answers true if the file matches one of the global ignore
	 * patterns, or if the file is marked as derived.
	 * 
	 * @param resource the file or folder
	 * @return whether the file should be ignored
	 */
	public static boolean isIgnoredHint(File file) {
		StringPatternMatcher[] matchers = getStringMatchers();
		for (int i = 0; i < matchers.length; i++) {
			String resourceName = file.getName();
			if(matchers[i].isPathPattern()) {
				resourceName = file.getPath();
			}
			if (matchers[i].match(resourceName)) return true;
		}
		return false;
	}
	
	/**
	 * Returns whether the given file or folder with its content should be ignored.
	 * 
	 * This method answers true if the file matches one of the global ignore
	 * patterns, or if the file is marked as derived.
	 * 
	 * @param resource the file or folder
	 * @return whether the file should be ignored
	 */
	public static boolean isIgnoredHint(IResource resource) {
		if (resource.isDerived()) return true;
		return matchesEnabledIgnore(resource);
	}
	
	/**
	 * Add patterns to the list of global ignores.
	 * 
	 * @param patterns Array of patterns to set
	 * @param enabled Array of booleans indicating if given pattern is enabled 
	 */
	public static void setAllIgnores(String[] patterns, boolean[] enabled) {
		initializeIgnores();
		globalIgnore = new TreeMap();
		ignoreMatchers = null;
		for (int i = 0; i < patterns.length; i++) {
			globalIgnore.put(patterns[i], Boolean.valueOf(enabled[i]));
		}
		// Now set into preferences
		StringBuffer buf = new StringBuffer();
		Iterator e = globalIgnore.entrySet().iterator();
		while (e.hasNext()) {
			Map.Entry entry = (Entry) e.next();
			String pattern = (String) entry.getKey();
			Boolean value = (Boolean) entry.getValue();
			boolean isCustom = (!pluginIgnore.containsKey(pattern)) ||
				!((Boolean)pluginIgnore.get(pattern)).equals(value);
			if (isCustom) {
				buf.append(pattern);
				buf.append(StudioCorePreferenceConstants.PREF_PATTERN_SEPARATOR);
				boolean en = value.booleanValue();
				buf.append(en);
				buf.append(StudioCorePreferenceConstants.PREF_PATTERN_SEPARATOR);
			}
			
		}
		StudioCorePlugin.getDefault().getPluginPreferences().setValue(StudioCorePreferenceConstants.PREF_CODEGEN_EXCLUDED_FILE_EXTENSIONS, buf.toString());
	}
	
	
	public static String getDefaultCompiler() {
		Preferences pref = StudioCorePlugin.getDefault().getPluginPreferences();
		pref.addPropertyChangeListener(new Preferences.IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				// when a property is changed, invalidate our cache so that
				// properties will be recalculated.
				if(event.getProperty().equals(StudioCorePreferenceConstants.PREF_CODEGEN_COMPILATION_MODE)) {
					defaultCompiler = (String) event.getNewValue();
				}
			}
		});
		if(!pref.contains(StudioCorePreferenceConstants.PREF_CODEGEN_COMPILATION_MODE)) {
			defaultCompiler =  DefaultRuntimeClassesPackager.DEFAULT_COMPILER;
		} else {
			defaultCompiler = pref.getString(StudioCorePreferenceConstants.PREF_CODEGEN_COMPILATION_MODE);
		}
		return defaultCompiler;
	}
	
	public static String getSourceJavaVersion() {
		Preferences pref = StudioCorePlugin.getDefault().getPluginPreferences();
		pref.addPropertyChangeListener(new Preferences.IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				// when a property is changed, invalidate our cache so that
				// properties will be recalculated.
				if(event.getProperty().equals(StudioCorePreferenceConstants.PREF_CODEGEN_SOURCE_JAVA_VERSION)) {
					sourceJavaVersion = (String) event.getNewValue();
				}
			}
		});
		if(!pref.contains(StudioCorePreferenceConstants.PREF_CODEGEN_SOURCE_JAVA_VERSION)) {
			sourceJavaVersion =  CodeGenConstants.JAVA_TARGET_VERSION;
		} else {
			sourceJavaVersion = pref.getString(StudioCorePreferenceConstants.PREF_CODEGEN_SOURCE_JAVA_VERSION);
		}
		return sourceJavaVersion;
	}
	
	public static String getTargetJavaVersion() {
		Preferences pref = StudioCorePlugin.getDefault().getPluginPreferences();
		pref.addPropertyChangeListener(new Preferences.IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				// when a property is changed, invalidate our cache so that
				// properties will be recalculated.
				if(event.getProperty().equals(StudioCorePreferenceConstants.PREF_CODEGEN_TARGET_JAVA_VERSION)) {
					targetJavaVersion = (String) event.getNewValue();
				}
			}
		});
		if(!pref.contains(StudioCorePreferenceConstants.PREF_CODEGEN_TARGET_JAVA_VERSION)) {
			targetJavaVersion =  CodeGenConstants.JAVA_TARGET_VERSION;
		} else {
			targetJavaVersion = pref.getString(StudioCorePreferenceConstants.PREF_CODEGEN_TARGET_JAVA_VERSION);
		}
		return targetJavaVersion;
	}
	
	/*
	 * IGNORE
	 * 
	 * Reads global ignore preferences and populates globalIgnore
	 */
	private static void readIgnoreState() throws Exception {
		Preferences pref = StudioCorePlugin.getDefault().getPluginPreferences();
		if (!pref.contains(StudioCorePreferenceConstants.PREF_CODEGEN_EXCLUDED_FILE_EXTENSIONS)) 
			return;
		pref.addPropertyChangeListener(new Preferences.IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				// when a property is changed, invalidate our cache so that
				// properties will be recalculated.
				if(event.getProperty().equals(StudioCorePreferenceConstants.PREF_CODEGEN_EXCLUDED_FILE_EXTENSIONS))
					globalIgnore = null;
			}
		});
		String prefIgnores = pref.getString(StudioCorePreferenceConstants.PREF_CODEGEN_EXCLUDED_FILE_EXTENSIONS);
		StringTokenizer tok = new StringTokenizer(prefIgnores, StudioCorePreferenceConstants.PREF_PATTERN_SEPARATOR);
		String pattern, enabled;
		try {
			while (true) {
				pattern = tok.nextToken();
				if (pattern.length()==0) return;
				enabled = tok.nextToken();
				globalIgnore.put(pattern, Boolean.valueOf(enabled));
			} 
		} catch (NoSuchElementException e) {
			return;
		}
	}
	
	
	private static void initializePluginIgnores(SortedMap<String,Boolean> pIgnore, SortedMap<String,Boolean> gIgnore) {
		StudioCorePlugin plugin = StudioCorePlugin.getDefault();
		if (plugin != null) {
			IExtensionPoint extension = Platform.getExtensionRegistry().getExtensionPoint(StudioCorePlugin.PLUGIN_ID, StudioCorePlugin.IGNORE_SHARED_ARCHIVE_RESOURCE_PATTERNS_EXTENSION);
			if (extension != null) {
				IExtension[] extensions =  extension.getExtensions();
				for (int i = 0; i < extensions.length; i++) {
					IConfigurationElement [] configElements = extensions[i].getConfigurationElements();
					for (int j = 0; j < configElements.length; j++) {
						String pattern = configElements[j].getAttribute("pattern"); //$NON-NLS-1$
						if (pattern != null) {
							String selected = configElements[j].getAttribute("enabled"); //$NON-NLS-1$
							boolean enabled = selected != null && selected.equalsIgnoreCase("true"); //$NON-NLS-1$
							pIgnore.put(pattern, Boolean.valueOf(enabled)); 
							if (!gIgnore.containsKey(pattern)){
								gIgnore.put(pattern, Boolean.valueOf(enabled));
							}
						}
					}
				}
			}		
		}
	}
	
	
	public static Collection<String> getValidLibraryExtensions(String [] types) {
		Set<String> fileExts = new HashSet<String>();
		StudioCorePlugin plugin = StudioCorePlugin.getDefault();
		if (plugin != null) {
			IExtensionPoint extension = Platform.getExtensionRegistry().getExtensionPoint(STUDIO_COMMON_PLUGIN_ID, "ProjLibExportExtensions");
			if (extension != null) {
				IExtension[] extensions =  extension.getExtensions();
				for (int i = 0; i < extensions.length; i++) {
					IConfigurationElement [] configElements = extensions[i].getConfigurationElements();
					for (int j = 0; j < configElements.length; j++) {
						if(Arrays.asList(types).contains(configElements[j].getName())) {
							String ext = configElements[j].getAttribute("extension"); //$NON-NLS-1$
							if (ext != null) {
								fileExts.add(ext);
							}
						}
					}
				}
			}		
		}
		return fileExts;
	}
	
	/**
	 * Shut down the registry, persisting its state.
	 * 
	 * This method is called by the plug-in upon shutdown, clients should not call this method
	 */	
	public static void shutdown() {
		StudioCorePlugin.getDefault().savePluginPreferences();
	}
	
	
	/**
	 * Resolve a variable path (helper method).
	 *
	 * @param variablePath the given variable path
	 * @return the resolved variable path or <code>null</code> if none
	 */
	public static IPath getResolvedVariablePath(IPath variablePath) {
		return StudioProjectConfigurationManager.getInstance().getResolvedVariablePath(variablePath, false/*don't use previous session value*/);
	}
	
	public static String[] getPathVariableNames() {
		return StudioProjectConfigurationManager.getInstance().variableNames();
	}
	
	public static PathVariableInitializer getPathVariableInitializer(String variable) {
		Plugin studioCorePlugin = StudioCorePlugin.getDefault();
		if (studioCorePlugin == null) return null;

		IExtensionPoint extension = Platform.getExtensionRegistry().getExtensionPoint(StudioCorePlugin.PLUGIN_ID, StudioProjectConfigurationManager.PATHVARIABLE_INITIALIZER_EXTPOINT_ID);
		if (extension != null) {
			IExtension[] extensions =  extension.getExtensions();
			for(int i = 0; i < extensions.length; i++){
				IConfigurationElement [] configElements = extensions[i].getConfigurationElements();
				for(int j = 0; j < configElements.length; j++){
					IConfigurationElement configElement = configElements[j];
					try {
						String varAttribute = configElement.getAttribute("variable"); //$NON-NLS-1$
						if (variable.equals(varAttribute)) {
							if (StudioProjectConfigurationManager.PATH_RESOLVE_VERBOSE_ADVANCED)
								verbose_found_variable_initializer(variable, configElement);
							Object execExt = configElement.createExecutableExtension("class"); //$NON-NLS-1$
							if (execExt instanceof PathVariableInitializer){
								PathVariableInitializer initializer = (PathVariableInitializer)execExt;
								String deprecatedAttribute = configElement.getAttribute("deprecated"); //$NON-NLS-1$
								if (deprecatedAttribute != null) {
									StudioProjectConfigurationManager.getInstance().deprecatedVariables.put(variable, deprecatedAttribute);
								}
								String readOnlyAttribute = configElement.getAttribute("readOnly"); //$NON-NLS-1$
								if (StudioProjectConfigurationManager.TRUE.equals(readOnlyAttribute)) {
									StudioProjectConfigurationManager.getInstance().readOnlyVariables.add(variable);
								}else
									initializer = null;// only read only variables to be shown
								return initializer;
							}
						}
					} catch(CoreException e){
						// executable extension could not be created: ignore this initializer
						if (StudioProjectConfigurationManager.PATH_RESOLVE_VERBOSE || StudioProjectConfigurationManager.PATH_RESOLVE_VERBOSE_FAILURE) {
							verbose_failed_to_instanciate_variable_initializer(variable, configElement);
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns whether a given classpath variable is read-only or not.
	 *
	 * @param variableName
	 * @return <code>true</code> if the classpath variable is read-only,
	 * 	<code>false</code> otherwise.
	 * @since 3.3
	 */
	public static boolean isClasspathVariableReadOnly(String variableName) {
	    return StudioProjectConfigurationManager.getInstance().readOnlyVariables.contains(variableName);
	}
	
	public static String getClasspathVariableDeprecationMessage(String variableName) {
	    StudioProjectConfigurationManager manager = StudioProjectConfigurationManager.getInstance();

		// Returns the stored deprecation message
		String message = (String) manager.deprecatedVariables.get(variableName);
		if (message != null) {
		    return message;
		}
	    
	    // If the variable has been already initialized, then there's no deprecation message
		IPath variablePath = manager.variableGet(variableName);
		if (variablePath != null && variablePath != StudioProjectConfigurationManager.VARIABLE_INITIALIZATION_IN_PROGRESS) {
			return null;
		}

		// Search for extension point to get the possible deprecation message
		Plugin jdtCorePlugin = StudioCorePlugin.getDefault();
		if (jdtCorePlugin == null) return null;

		IExtensionPoint extension = Platform.getExtensionRegistry().getExtensionPoint(StudioCorePlugin.PLUGIN_ID, StudioProjectConfigurationManager.PATHVARIABLE_INITIALIZER_EXTPOINT_ID);
		if (extension != null) {
			IExtension[] extensions =  extension.getExtensions();
			for(int i = 0; i < extensions.length; i++){
				IConfigurationElement [] configElements = extensions[i].getConfigurationElements();
				for(int j = 0; j < configElements.length; j++){
					IConfigurationElement configElement = configElements[j];
					String varAttribute = configElement.getAttribute("variable"); //$NON-NLS-1$
					if (variableName.equals(varAttribute)) {
						String deprecatedAttribute = configElement.getAttribute("deprecated"); //$NON-NLS-1$
						if (deprecatedAttribute != null) {
							return deprecatedAttribute;
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns the path held in the given classpath variable.
	 * Returns <code>null</code> if unable to bind.
	 * <p>
	 * Classpath variable values are persisted locally to the workspace, and
	 * are preserved from session to session.
	 * <p>
	 * Note that classpath variables can be contributed registered initializers for,
	 * using the extension point "org.eclipse.jdt.core.classpathVariableInitializer".
	 * If an initializer is registered for a variable, its persisted value will be ignored:
	 * its initializer will thus get the opportunity to rebind the variable differently on
	 * each session.
	 *
	 * @param variableName the name of the classpath variable
	 * @return the path, or <code>null</code> if none
	 * @see #setClasspathVariable(String, IPath)
	 */
	public static IPath getClasspathVariable(final String variableName) {

	    StudioProjectConfigurationManager manager = StudioProjectConfigurationManager.getInstance();
		IPath variablePath = manager.variableGet(variableName);
		if (variablePath == StudioProjectConfigurationManager.VARIABLE_INITIALIZATION_IN_PROGRESS){
		    return manager.getPreviousSessionVariable(variableName);
		}

		if (variablePath != null) {
			if (variablePath == StudioProjectConfigurationManager.PATH_ENTRY_IGNORE_PATH)
				return null;
			return variablePath;
		}

		// even if persisted value exists, initializer is given priority, only if no initializer is found the persisted value is reused
		final PathVariableInitializer initializer = StudioCore.getPathVariableInitializer(variableName);
		if (initializer != null){
			if (StudioProjectConfigurationManager.PATH_RESOLVE_VERBOSE)
				verbose_triggering_variable_initialization(variableName, initializer);
			if (StudioProjectConfigurationManager.PATH_RESOLVE_VERBOSE_ADVANCED)
				verbose_triggering_variable_initialization_invocation_trace();
			manager.variablePut(variableName, StudioProjectConfigurationManager.VARIABLE_INITIALIZATION_IN_PROGRESS); // avoid initialization cycles
			boolean ok = false;
			try {
				// let OperationCanceledException go through
				// (see https://bugs.eclipse.org/bugs/show_bug.cgi?id=59363)
				initializer.initialize(variableName);

				variablePath = manager.variableGet(variableName); // initializer should have performed side-effect
				if (variablePath == StudioProjectConfigurationManager.VARIABLE_INITIALIZATION_IN_PROGRESS) return null; // break cycle (initializer did not init or reentering call)
				if (StudioProjectConfigurationManager.PATH_RESOLVE_VERBOSE_ADVANCED)
					verbose_variable_value_after_initialization(variableName, variablePath);
				manager.variablesWithInitializer.add(variableName);
				ok = true;
			} catch (RuntimeException e) {
				if (StudioProjectConfigurationManager.PATH_RESOLVE_VERBOSE || StudioProjectConfigurationManager.PATH_RESOLVE_VERBOSE_FAILURE)
					e.printStackTrace();
				throw e;
			} catch (Error e) {
				if (StudioProjectConfigurationManager.PATH_RESOLVE_VERBOSE || StudioProjectConfigurationManager.PATH_RESOLVE_VERBOSE_FAILURE)
					e.printStackTrace();
				throw e;
			} finally {
				if (!ok) StudioProjectConfigurationManager.getInstance().variablePut(variableName, null); // flush cache
			}
		} else {
			if (StudioProjectConfigurationManager.PATH_RESOLVE_VERBOSE_ADVANCED || StudioProjectConfigurationManager.PATH_RESOLVE_VERBOSE_FAILURE)
				verbose_no_variable_initializer_found(variableName);
		}
		return variablePath;
	}
	
	/**
	 * Sets the value of the given classpath variable.
	 * The path must not be null.
	 * <p>
	 * This functionality cannot be used while the resource tree is locked.
	 * <p>
	 * Classpath variable values are persisted locally to the workspace, and
	 * are preserved from session to session.
	 * <p>
	 * Updating a variable with the same value has no effect.
	 *
	 * @param variableName the name of the classpath variable
	 * @param path the path
	 * @param monitor a monitor to report progress
	 * @throws Exception
	 * @see #getClasspathVariable(String)
	 */
	public static void setClasspathVariable(
		String variableName,
		IPath path,
		IProgressMonitor monitor) {

		if (path == null) Assert.isTrue(false, "Variable path cannot be null"); //$NON-NLS-1$
		setPathVariables(new String[]{variableName}, new IPath[]{ path }, monitor);
	}

	/**
	 * Sets the values of all the given classpath variables at once.
	 * Null paths can be used to request corresponding variable removal.
	 * <p>
	 * A combined Java element delta will be notified to describe the corresponding
	 * classpath changes resulting from the variables update. This operation is batched,
	 * and automatically eliminates unnecessary updates (new variable is same as old one).
	 * This operation acquires a lock on the workspace's root.
	 * <p>
	 * This functionality cannot be used while the workspace is locked, since
	 * it may create/remove some resource markers.
	 * <p>
	 * Classpath variable values are persisted locally to the workspace, and
	 * are preserved from session to session.
	 * <p>
	 * Updating a variable with the same value has no effect.
	 *
	 * @param variableNames an array of names for the updated classpath variables
	 * @param paths an array of path updates for the modified classpath variables (null
	 *       meaning that the corresponding value will be removed
	 * @param monitor a monitor to report progress
	 * @throws Exception
	 * @see #getClasspathVariable(String)
	 * @since 2.0
	 */
	public static void setPathVariables(
		String[] variableNames,
		IPath[] paths,
		IProgressMonitor monitor){

		if (variableNames.length != paths.length)	Assert.isTrue(false, "Variable names and paths collections should have the same size"); //$NON-NLS-1$
		for (int i = 0; i < paths.length; i++) {
			StudioProjectConfigurationManager.getInstance().variablePut(variableNames[i], paths[i]);
			StudioProjectConfigurationManager.getInstance().variablePreferencesPut(variableNames[i], paths[i]);
		}
		
	}
	
	private static void verbose_triggering_variable_initialization(String variableName, PathVariableInitializer initializer) {
		StudioCorePlugin.debug(
			"PathVariable INIT - triggering initialization\n" + //$NON-NLS-1$
			"	variable: " + variableName + '\n' + //$NON-NLS-1$
			"	initializer: " + initializer); //$NON-NLS-1$
	}
	
	
	private static void verbose_no_variable_initializer_found(String variableName) {
		StudioCorePlugin.debug(
			"PathVariable INIT - no initializer found\n" + //$NON-NLS-1$
			"	variable: " + variableName); //$NON-NLS-1$
	}
	
	private static void verbose_variable_value_after_initialization(String variableName, IPath variablePath) {
		StudioCorePlugin.debug(
			"PathVariable INIT - after initialization\n" + //$NON-NLS-1$
			"	variable: " + variableName +'\n' + //$NON-NLS-1$
			"	variable path: " + variablePath); //$NON-NLS-1$
	}
	
	private static void verbose_triggering_variable_initialization_invocation_trace() {
		StudioCorePlugin.debug(
			"PathVariable INIT - triggering initialization\n" + //$NON-NLS-1$
			"	invocation trace:"); //$NON-NLS-1$
		new Exception("<Fake exception>").printStackTrace(System.out); //$NON-NLS-1$
	}
	
	/**
	 * @param variable
	 * @param configElement
	 */
	private static void verbose_failed_to_instanciate_variable_initializer(String variable, IConfigurationElement configElement) {
		StudioCorePlugin.debug(
			"PathContainer INIT - failed to instanciate initializer\n" + //$NON-NLS-1$
			"	variable: " + variable + '\n' + //$NON-NLS-1$
			"	class: " + configElement.getAttribute("class"), //$NON-NLS-1$ //$NON-NLS-2$
			System.err);
	}
	/**
	 * @param variable
	 * @param configElement
	 */
	private static void verbose_found_variable_initializer(String variable, IConfigurationElement configElement) {
		StudioCorePlugin.debug(
			"PathVariable INIT - found initializer\n" + //$NON-NLS-1$
			"	variable: " + variable + '\n' + //$NON-NLS-1$
			"	class: " + configElement.getAttribute("class")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	

}
