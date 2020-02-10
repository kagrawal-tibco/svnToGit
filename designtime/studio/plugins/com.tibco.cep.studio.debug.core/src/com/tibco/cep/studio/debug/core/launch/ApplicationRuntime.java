package com.tibco.cep.studio.debug.core.launch;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;

import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;

public class ApplicationRuntime {
	/**
	 * Preference key for launch/connect timeout. VM Runners should honor this timeout
	 * value when attempting to launch and connect to a debuggable VM. The value is
	 * an int, indicating a number of milliseconds.
	 * 
	 */
	public static final String PREF_CONNECT_TIMEOUT = StudioDebugCorePlugin.getUniqueIdentifier() + ".PREF_CONNECT_TIMEOUT"; //$NON-NLS-1$
	
	
	/**
	 * Default launch/connect timeout (ms).
	 * 
	 */
	public static final int DEF_CONNECT_TIMEOUT = 20000;

	public static ISourceContainer[] computeSourceContainers(
			ILaunchConfiguration configuration, IProgressMonitor monitor) throws CoreException {
		String projectName = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, (String)null);

		IProject project  = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		
		List<ISourceContainer> sourceContainers = new ArrayList<ISourceContainer>();
		
		sourceContainers.add(new StudioProjectSourceContainer(project));
		
		return sourceContainers.toArray(new ISourceContainer[sourceContainers.size()]);
		
	}
	
	
	/**
	 * Returns the preference store for the launching plug-in.
	 * 
	 * @return the preference store for the launching plug-in
	 */
	public static IEclipsePreferences getPreferences() {
		return InstanceScope.INSTANCE.getNode(StudioDebugCorePlugin.PLUGIN_ID);
		//return StudioDebugCorePlugin.getDefault().getPluginPreferences();
	}
	
	/**
	 * Saves the preferences for the launching plug-in.
	 * 
	 */
	public static void savePreferences() {
		StudioDebugCorePlugin.getDefault().savePluginPreferences();
	}


}
