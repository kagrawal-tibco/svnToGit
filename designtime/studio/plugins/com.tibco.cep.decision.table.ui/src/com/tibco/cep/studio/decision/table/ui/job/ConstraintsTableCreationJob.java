/**
 * 
 */
package com.tibco.cep.studio.decision.table.ui.job;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerView;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;

/**
 * @author aathalye
 *
 */
public class ConstraintsTableCreationJob extends Job {
	
	private DecisionTableAnalyzerView analyzerView;
	
	private IDecisionTableEditor decisionTableEditor;
	
	/**
	 * @param name
	 */
	public ConstraintsTableCreationJob(String name, 
			                           DecisionTableAnalyzerView analyzerView,
			                           IDecisionTableEditor decisionTableEditor) {
		super(name);
		this.analyzerView = analyzerView;
		this.decisionTableEditor = decisionTableEditor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		// is all this really necessary? 
		try {
//			if (decisionTableEditor.lock.tryLock()) {
			//	if (decisionTableEditor.getTable().getDecisionTable() ==  null) {
					SubMonitor mainMonitor = SubMonitor.convert(monitor);
					analyzerView.setDecisionTable(decisionTableEditor, mainMonitor);
					if (mainMonitor.isCanceled()) {
						return Status.CANCEL_STATUS;
					}
					analyzerView.refillComponents(decisionTableEditor, decisionTableEditor.getDecisionTable());
					mainMonitor.done();
			//	}
//			}
			return Status.OK_STATUS;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
//			if(decisionTableEditor.lock.isLocked()){
//				try {
//					decisionTableEditor.lock.unlock();
//				} catch (IllegalMonitorStateException e) {
//					Thread.currentThread().interrupt();
//				}
//			}
			//while (decisionTableEditor.lock.isHeldByCurrentThread()) {
//			try {
//				decisionTableEditor.lock.lockInterruptibly();
//				  try {
//				    // do locked work here
//				  } finally {
//					  decisionTableEditor.lock.unlock();
//				  }
//				} catch( InterruptedException e ) {
//				  Thread.currentThread().interrupt();
//				}
			//}
			monitor.done();
		}
	}
}
