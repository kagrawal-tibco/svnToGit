/**
 * 
 */
package com.tibco.cep.studio.decision.table.ui.job;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.cep.studio.decision.table.constraintpane.AnalyzeTableRunnable;

/**
 * Run Table analysis as a job
 * 
 *
 */
public class AnalyzeTableJob extends Job {
	
	private AnalyzeTableRunnable analysisExecution;
	

	/**
	 * @param name
	 * @param analysisExecution
	 */
	public AnalyzeTableJob(String name, AnalyzeTableRunnable analysisExecution) {
		super(name);
		this.analysisExecution = analysisExecution;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		SubMonitor mainMonitor = SubMonitor.convert(monitor);
		try {
//			Thread.sleep(200);
			analysisExecution.analyze(mainMonitor);
			mainMonitor.done();
			return Status.OK_STATUS;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			monitor.done();
		}
	}
}
