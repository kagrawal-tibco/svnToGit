package com.tibco.cep.studio.ui.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.StudioProjectMigrationUtils;

public class MigrateStudioProjectsOperation extends WorkspaceModifyOperation {

	private Object[] fProjectsToConvert;
	private boolean fUseDefaults;

	/**
	 * @param newProj
	 * @param targetLocation
	 * @param useDefaults
	 */
	public MigrateStudioProjectsOperation(Object[] projectsToConvert, 
			                              boolean useDefaults) {
		super();
		this.fProjectsToConvert = projectsToConvert;
		this.fUseDefaults = useDefaults;
	}
	
	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
		monitor.beginTask("Converting old projects -- ", fProjectsToConvert.length*2+1);
		monitor.worked(1);
		for (Object obj: fProjectsToConvert) {
			IProject proj = (IProject) obj;
			monitor.subTask("Converting "+proj.getName());
			if (!checkStudioProjectNature(proj))  {
				continue;
			}
			StudioProjectMigrationUtils.convertStudioProject(proj.getLocation().toFile(), null, null, new SubProgressMonitor(monitor, 1));
			proj.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			monitor.worked(1);
		}
	}

    private boolean checkStudioProjectNature(IProject project) throws CoreException{
    	return project.isOpen() && CommonUtil.isStudioProject(project);
    }
}
