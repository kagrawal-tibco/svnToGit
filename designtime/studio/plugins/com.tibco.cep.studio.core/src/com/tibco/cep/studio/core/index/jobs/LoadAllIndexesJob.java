package com.tibco.cep.studio.core.index.jobs;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.StudioModelManager;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.util.CommonUtil;

public class LoadAllIndexesJob extends IndexJob<DesignerProject> {

	public LoadAllIndexesJob() {
		super("Loading all indexes");
	}

	@Override
	public IStatus runJob(IProgressMonitor monitor) {
		IProject[] projects = CommonUtil.getAllStudioProjects();
		monitor.beginTask("Loading all indexes", projects.length);
//		try {
//			Thread.sleep(15000);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
		for (IProject project : projects) {
			if (monitor.isCanceled()) {
				break;
			}
			try {
				if (project.isOpen() && CommonUtil.isStudioProject(project)) {
					// load the index, and create if necessary
					monitor.setTaskName("Loading index for "+project.getName());
					StudioCorePlugin.getDesignerModelManager().loadIndex(project, true);
				}
				monitor.worked(1);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		monitor.done();
		return Status.OK_STATUS;
	}

	@Override
	public boolean belongsTo(Object family) {
		return StudioModelManager.LOAD_ALL_INDEX_JOB.equals(family);
	}

}
