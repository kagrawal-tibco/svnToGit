package com.tibco.cep.bpmn.core.index.jobs;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.designtime.model.IndexCache.ProjectCacheLock;
import com.tibco.cep.studio.core.index.jobs.IndexJob;
import com.tibco.cep.studio.core.index.jobs.IndexJobResult;


public class BpmnProjectCacheLock implements ProjectCacheLock<EObject> {
	IndexJob<EObject> job;
	
	
	/**
	 * @param job
	 */
	public BpmnProjectCacheLock(IndexJob<EObject> job) {
		super();
		this.job = job;
	}


	public IndexJob<EObject> getJob() {
		return job;
	}

	@SuppressWarnings("unchecked")
	public EObject waitForCompletion() {
		if(Job.getJobManager().currentJob() == null) {
			try {
				job.join();
				IStatus result = job.getResult();
				if(result.isOK()) {
					if(result instanceof IndexJobResult<?>) {
						return ((IndexJobResult<EObject>)result).getJobData();
					} else {
						return null;
					}
				}
			} catch (InterruptedException e) {
				BpmnCorePlugin.log(e);
			}
		}
		if(job.equals(Job.getJobManager().currentJob())) {
			return job.waitForCompletion();
		} else {
			try {
				job.join();
				IStatus result = job.getResult();
				if(result.isOK()) {
					if(result instanceof IndexJobResult<?>) {
						return ((IndexJobResult<EObject>)result).getJobData();
					}
				}
			} catch (InterruptedException e) {
				BpmnCorePlugin.log(e);
			}
		}
		return null;
	}
	
	public boolean isComplete() {
		return job.getResult() != null;
	}

}
