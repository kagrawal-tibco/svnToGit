package com.tibco.cep.bpmn.core.index.jobs;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.BpmnModelManager;
import com.tibco.cep.bpmn.core.nature.BpmnProjectNatureManager;
import com.tibco.cep.studio.core.index.jobs.IndexJob;

public class LoadAllBpmnIndexesJob extends IndexJob<EObject> {

	public LoadAllBpmnIndexesJob() {
		super("Loading all indexes");
	}

	@Override
	public IStatus runJob(IProgressMonitor monitor) {
		IProject[] projects = BpmnProjectNatureManager.getInstance().getAllBpmnProjects();
		monitor.beginTask("Loading all indexes", projects.length);

		for (IProject project : projects) {
			if (monitor.isCanceled()) {
				break;
			}
			try {
				if (project.isOpen()) {
					// load the index, and create if necessary
					monitor.setTaskName("Loading index for "+project.getName());
					BpmnCorePlugin.getDefault().getBpmnModelManager().loadIndex(project);
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
		return BpmnModelManager.LOAD_ALL_BPMN_INDEX_JOB.equals(family);
	}

}
