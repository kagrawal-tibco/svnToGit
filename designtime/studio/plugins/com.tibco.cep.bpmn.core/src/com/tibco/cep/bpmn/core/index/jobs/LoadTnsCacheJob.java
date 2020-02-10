package com.tibco.cep.bpmn.core.index.jobs;

import java.util.Queue;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.update.BpmnTnsCacheUpdater;
import com.tibco.cep.studio.core.index.jobs.IndexJob;

public class LoadTnsCacheJob  extends IndexJob<EObject> {

	private IProject project;
	private BpmnTnsCacheUpdater cacheUpdator;

	public LoadTnsCacheJob(IProject project, BpmnTnsCacheUpdater updator) {
		super("Loading BPMN TnsCache resources for "+project.getName());
		this.project = project;
		this.cacheUpdator = updator;
	}
	
	public IProject getProject() {
		return project;
	}


	@Override
	public IStatus runJob(IProgressMonitor monitor) {
		try {
			project.accept(this.cacheUpdator);
		} catch (CoreException e) {
			return new Status(IStatus.ERROR, BpmnCorePlugin.PLUGIN_ID, "Bpmn Cache update failed for project:"+project.getName(),e);
		}catch (Exception e) {
			BpmnCorePlugin.log(e);
		}
		Queue<Throwable> q = cacheUpdator.getErrors();
		Throwable t = null;
		do {
			// drain the error queue
			t = q.poll();
			if(t != null)
				BpmnCorePlugin.log(t);
		} while (t != null);
		return Status.OK_STATUS;
	}

}
