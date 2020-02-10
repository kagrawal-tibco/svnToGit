package com.tibco.cep.decision.table.constraintpane;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.ui.jobs.AnalyzeTableJob;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;


public class AnalyzeAction extends Action {

	private DecisionTableAnalyzerView fAnalyzerView;

	public AnalyzeAction(DecisionTableAnalyzerView view) {
		setText(Messages.getString("DecisionTableAnalyzerView.Analyze"));
		setToolTipText(Messages.getString("DecisionTableAnalyzerView.Analyze"));
		setImageDescriptor(DecisionTableUIPlugin.getImageDescriptor("icons/analyze_table16x16.gif"));
		
		this.fAnalyzerView = view;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {

		//Check if domain model should be used for table completeness
		boolean useDomainModel = 
			DecisionTableUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.ANALYZER_USE_DOMAINMODEL);
		AnalyzeTableRunnable runnable = new AnalyzeTableRunnable(fAnalyzerView.getCurrentOptimizedTable(), 
				getTable(),
				useDomainModel,
				fAnalyzerView.getAnalyzerExtension());
		AnalyzeTableJob analysisJob = new AnalyzeTableJob("Analyze Table", runnable);
		IProgressMonitor monitor = new NullProgressMonitor();
		SubMonitor subMonitor = SubMonitor.convert(monitor);
		analysisJob.setPriority(Job.SHORT);
		analysisJob.setProgressGroup(subMonitor, 100);
		analysisJob.setUser(true);
		analysisJob.addJobChangeListener(new AnalyzeTableJobChangeListener(runnable));
		analysisJob.schedule();
	}
	
	private Table getTable() {
		DecisionTableEditor editor = fAnalyzerView.getCurrentEditor();
		if (editor != null) {
			return editor.getDecisionTableModelManager().getTabelEModel();
		}
		return null;
	}

	private class AnalyzeTableJobChangeListener extends JobChangeAdapter {
		
		private AnalyzeTableRunnable analysisExecutioner;
		
		
		/**
		 * @param analysisExecutioner
		 */
		AnalyzeTableJobChangeListener(AnalyzeTableRunnable analysisExecutioner) {
			this.analysisExecutioner = analysisExecutioner;
		}


		/* (non-Javadoc)
		 * @see org.eclipse.core.runtime.jobs.JobChangeAdapter#done(org.eclipse.core.runtime.jobs.IJobChangeEvent)
		 */
		@Override
		public void done(IJobChangeEvent event) {
			Job job = event.getJob();
			
			if (job instanceof AnalyzeTableJob) {
				IStatus result = event.getResult();
				if (result.getSeverity() == Status.OK) {
					List<ProblemEvent> problems = analysisExecutioner.getProblems();
					if (problems.size() > 0) {
						showMessage("Analyze Table", "There were " + problems.size() + " issues found.  See the Problems view for more information", MessageType.WARNING);
					} else {
						showMessage("Analyze Table", "There were no issues found with the selected table.", MessageType.INFO);
					}
				}
			}
		}
	}
	
	private enum MessageType {
		WARNING, ERROR, INFO;
	}
	
	private void showMessage(final String title, final String message, MessageType messageType) {
		switch (messageType) {
		case WARNING:
			StudioUIUtils.invokeOnDisplayThread(new Runnable() {
				public void run() {
					Shell activeShell = fAnalyzerView.getSite().getShell();
					MessageDialog.openWarning(activeShell, title, message);
				}
			}, false);
			break;
		case INFO:
			StudioUIUtils.invokeOnDisplayThread(new Runnable() {
				public void run() {
					Shell activeShell = fAnalyzerView.getSite().getShell();
					MessageDialog.openInformation(activeShell, title, message);
				}
			}, false);
			break;
		}
	}
}
