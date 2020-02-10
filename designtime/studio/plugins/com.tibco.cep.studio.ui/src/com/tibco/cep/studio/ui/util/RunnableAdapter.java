package com.tibco.cep.studio.ui.util;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.operation.IThreadListener;

import com.tibco.cep.studio.ui.views.StatusInfo;

public class RunnableAdapter implements IRunnableWithProgress, IThreadListener {
	
	private boolean transfer= false;
	private IWorkspaceRunnable workspaceRunnable;
	private ISchedulingRule rule;

	/**
	 * Runs a workspace runnable with the workspace lock.
	 * @param runnable the runnable
	 */
	public RunnableAdapter(IWorkspaceRunnable runnable) {
		this(runnable, ResourcesPlugin.getWorkspace().getRoot());
	}
	
	/**
	 * Runs a workspace runnable with the given lock or <code>null</code> to run with no lock at all.
	 * @param runnable the runnable
	 * @param rule the scheduling rule
	 */
	public RunnableAdapter(IWorkspaceRunnable runnable, ISchedulingRule rule) {
		this.workspaceRunnable= runnable;
		this.rule= rule;
	}
	
	/**
	 * Runs a workspace runnable with the given lock or <code>null</code> to run with no lock at all.
	 * @param runnable the runnable
	 * @param rule the scheduling rule
	 * @param transfer <code>true</code> if the rule is to be transfered 
	 *  to the model context thread. Otherwise <code>false</code>
	 */
	public RunnableAdapter(IWorkspaceRunnable runnable, ISchedulingRule rule, boolean transfer) {
		this.workspaceRunnable= runnable;
		this.rule= rule;
		this.transfer= transfer;
	}
	
	public ISchedulingRule getSchedulingRule() {
		return rule;
	}

	public void threadChange(Thread thread) {
		if (transfer)
			Job.getJobManager().transferRule(rule, thread);
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		try {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			workspaceRunnable.run(monitor);
		} catch (OperationCanceledException e) {
			throw new InterruptedException(e.getMessage());
		} catch (CoreException e) {
			throw new InvocationTargetException(e);
		}
	}
	
	public void runAsUserJob(String name, final Object jobFamiliy) {
		Job job = new Job(name){ 

			protected IStatus run(IProgressMonitor monitor) {
				try {
					RunnableAdapter.this.run(monitor);
				} catch (InvocationTargetException e) {
					Throwable cause= e.getCause();
					if (cause instanceof CoreException) {
						return ((CoreException) cause).getStatus();
					} else {
						return new StatusInfo(cause.getMessage(),IStatus.ERROR);
					}
				} catch (InterruptedException e) {
					return Status.CANCEL_STATUS;
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}
			public boolean belongsTo(Object family) {
				return jobFamiliy == family;
			}
		};
		job.setRule(rule);
		job.setUser(true); 
		job.schedule();
		
	}

}
