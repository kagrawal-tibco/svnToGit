/**
 * 
 */
package com.tibco.cep.decision.table.ui.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerView;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;

/**
 * @author aathalye
 *
 */
public class ConstraintsTableCreationJob extends Job {
	
	private DecisionTableAnalyzerView analyzerView;
	
	private DecisionTableEditor decisionTableEditor;
	
	/**
	 * @param name
	 */
	public ConstraintsTableCreationJob(String name, 
			                           DecisionTableAnalyzerView analyzerView,
			                           DecisionTableEditor decisionTableEditor) {
		super(name);
		this.analyzerView = analyzerView;
		this.decisionTableEditor = decisionTableEditor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			if (decisionTableEditor.lock.tryLock()) {
				if (decisionTableEditor.getDecisionTable() ==  null) {
					SubMonitor mainMonitor = SubMonitor.convert(monitor);
					analyzerView.setDecisionTable(decisionTableEditor, mainMonitor);
					analyzerView.refillComponents(decisionTableEditor, decisionTableEditor.getDecisionTable());
					mainMonitor.done();
				}
			}
			return Status.OK_STATUS;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			//while (decisionTableEditor.lock.isHeldByCurrentThread()) {
				decisionTableEditor.lock.unlock();
			//}
			monitor.done();
		}
	}
}
