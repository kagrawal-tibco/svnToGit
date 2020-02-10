package com.tibco.cep.studio.core.index.jobs;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.cep.designtime.model.IndexCache.ProjectCacheLock;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerProject;

public class StudioProjectCacheLock implements ProjectCacheLock<DesignerProject> {
	IndexJob<DesignerProject> job;
	
	
	/**
	 * @param job
	 */
	public StudioProjectCacheLock(IndexJob<DesignerProject> job) {
		super();
		this.job = job;
	}


	public DesignerProject waitForCompletion() {
		if(Job.getJobManager().currentJob() == null) {
			try {
				job.join();
				IStatus result = job.getResult();
				if(result.isOK()) {
					if(result instanceof IndexJobResult) {
						return (DesignerProject) ((IndexJobResult)result).getJobData();
					} else {
						return null;
					}
				}
			} catch (InterruptedException e) {
				StudioCorePlugin.log(e);
			}
		}
		if(Job.getJobManager().currentJob().equals(job)) {
			return job.waitForCompletion();
		} else {
			try {
				job.join();
				IStatus result = job.getResult();
				if(result.isOK()) {
					if(result instanceof IndexJobResult) {
						return (DesignerProject) ((IndexJobResult)result).getJobData();
					}
				}
			} catch (InterruptedException e) {
				StudioCorePlugin.log(e);
			}
		}
		return null;
	}
	
	@Override
	public boolean isComplete() {
		return job.getResult() != null;
	}

}
