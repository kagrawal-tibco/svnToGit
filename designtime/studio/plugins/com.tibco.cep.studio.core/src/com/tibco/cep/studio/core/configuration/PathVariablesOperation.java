package com.tibco.cep.studio.core.configuration;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.StudioCoreWorkspaceOperation;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.Utils;

public class PathVariablesOperation extends StudioCoreWorkspaceOperation {

	private String[] variableNames;
	private IPath[] variablePaths;
	private boolean updatePreferences;
	protected boolean canChangeResources;
	/**
	 * The progress monitor passed into this operation
	 */
	public IProgressMonitor progressMonitor= null;

	public PathVariablesOperation(String[] variableNames, IPath[] variablePaths, boolean updatePreferences) {
		this.variableNames = variableNames;
		this.variablePaths = variablePaths;
		this.updatePreferences = updatePreferences;
		this.canChangeResources = !ResourcesPlugin.getWorkspace().isTreeLocked();
	}

	@Override
	protected void executeOperation() throws CoreException {
		checkCanceled();
		
		try {
			beginTask("", 1); //$NON-NLS-1$
			if(StudioProjectConfigurationManager.PATH_RESOLVE_VERBOSE){
				verbose_set_variables();
			}
			StudioProjectConfigurationManager manager = StudioProjectConfigurationManager.getInstance();
			if (manager.variablePutIfInitializingWithSameValue(this.variableNames, this.variablePaths))
				return;
	
			int varLength = this.variableNames.length;
			
			// gather classpath information for updating
			final HashMap<IProject,Object> affectedProjectClasspaths = new HashMap<IProject,Object>(5);
			
			// filter out unmodified variables
			int discardCount = 0;
			for (int i = 0; i < varLength; i++){
				String variableName = this.variableNames[i];
				IPath oldPath = manager.variableGet(variableName); // if reentering will provide previous session value 
				if (oldPath == StudioProjectConfigurationManager.VARIABLE_INITIALIZATION_IN_PROGRESS) {
					oldPath = null;  //33695 - cannot filter out restored variable, must update affected project to reset cached CP
				}
				if (oldPath != null && oldPath.equals(this.variablePaths[i])){
					this.variableNames[i] = null;
					discardCount++;
				}
			}
			if (discardCount > 0){
				if (discardCount == varLength) return;
				int changedLength = varLength - discardCount;
				String[] changedVariableNames = new String[changedLength];
				IPath[] changedVariablePaths = new IPath[changedLength];
				for (int i = 0, index = 0; i < varLength; i++){
					if (this.variableNames[i] != null){
						changedVariableNames[index] = this.variableNames[i];
						changedVariablePaths[index] = this.variablePaths[i];
						index++;
					}
				}
				this.variableNames = changedVariableNames;
				this.variablePaths = changedVariablePaths;
				varLength = changedLength;
			}
			
			if (isCanceled()) 
				return;
			
			IProject[] studioProjects = CommonUtil.getAllStudioProjects();
			nextProject : for (int i = 0, projectLength = studioProjects.length; i < projectLength; i++){
				// check to see if any of the modified variables is present on the classpath
				StudioProjectConfiguration config = manager.getProjectConfiguration(studioProjects[i].getName());
				EList<ProjectLibraryEntry> projLibs = config.getProjectLibEntries();
				EList<ThirdPartyLibEntry> tpLibs = config.getThirdpartyLibEntries();
				EList<CustomFunctionLibEntry> cfLibs = config.getCustomFunctionLibEntries();
				for (int k = 0; k < varLength; k++) {

					String variableName = this.variableNames[k];

					for (ProjectLibraryEntry var : projLibs) {
						IPath vPath = new Path(var.getPath());
						if (var.isVar()
								&& variableName.equals(vPath.segment(0))) {
							affectedProjectClasspaths.put(studioProjects[i],
									new Object());
						}
					}
					for (ThirdPartyLibEntry var : tpLibs) {
						IPath vPath = new Path(var.getPath());
						if (var.isVar()
								&& variableName.equals(vPath.segment(0))) {
							affectedProjectClasspaths.put(studioProjects[i],
									new Object());
						}
					}
					for (CustomFunctionLibEntry var : cfLibs) {
						IPath vPath = new Path(var.getPath());
						if (var.isVar()
								&& variableName.equals(vPath.segment(0))) {
							affectedProjectClasspaths.put(studioProjects[i],
									new Object());
						}
					}
				
				} // end vars
			} // end project
			
			// update variables
			for (int i = 0; i < varLength; i++){
				manager.variablePut(this.variableNames[i], this.variablePaths[i]);
				if (this.updatePreferences)
					manager.variablePreferencesPut(this.variableNames[i], this.variablePaths[i]);
			}
			// update affected project classpaths
			if (!affectedProjectClasspaths.isEmpty()) {
				String[] dbgVariableNames = this.variableNames;
				// propagate classpath change
				Iterator<IProject> projectsToUpdate = affectedProjectClasspaths.keySet().iterator();
				while (projectsToUpdate.hasNext()) {
					if (this.progressMonitor != null && this.progressMonitor.isCanceled()) return;
					IProject affectedProject = (IProject) projectsToUpdate.next();
					
					// force resolved classpath to be recomputed
					if (StudioProjectConfigurationManager.PATH_RESOLVE_VERBOSE_ADVANCED)
						verbose_update_project(dbgVariableNames, affectedProject);
					if (this.canChangeResources) {
						// touch project to force a build if needed
						affectedProject.getProject().touch(this.progressMonitor);
					}
				}
			}
			
		} finally {
			done();
		}

	}
	
	private void verbose_failure(String[] dbgVariableNames) {
		StudioCorePlugin.debug(
			"PathVariable SET  - FAILED DUE TO EXCEPTION\n" + //$NON-NLS-1$
			"	variables: " + Utils.toString(dbgVariableNames), //$NON-NLS-1$
			System.err);
	}

	private void verbose_update_project(String[] dbgVariableNames,
			IProject affectedProject) {
		StudioCorePlugin.debug(
			"PathVariable SET  - updating affected project due to setting variables\n" + //$NON-NLS-1$
			"	project: " + affectedProject.getName() + '\n' + //$NON-NLS-1$
			"	variables: " + Utils.toString(dbgVariableNames)); //$NON-NLS-1$
	}

	private void verbose_set_variables() {
		StudioCorePlugin.debug(
			"PathVariable SET  - setting variables\n" + //$NON-NLS-1$
			"	variables: " + Utils.toString(this.variableNames) + '\n' +//$NON-NLS-1$
			"	values: " + Utils.toString(this.variablePaths)); //$NON-NLS-1$
	}

}
