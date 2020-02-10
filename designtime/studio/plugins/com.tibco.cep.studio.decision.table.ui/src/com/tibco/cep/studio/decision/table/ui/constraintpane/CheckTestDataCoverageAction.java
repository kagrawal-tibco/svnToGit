package com.tibco.cep.studio.decision.table.ui.constraintpane;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerView;
import com.tibco.cep.studio.decision.table.constraintpane.IDecisionTableTestDataCoverageView;
import com.tibco.cep.studio.decision.table.constraintpane.Messages;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

/**
 * @author vdhumal
 *
 */
public class CheckTestDataCoverageAction extends Action {

	private DecisionTableAnalyzerView analyzerView;
	
	/**
	 * @param analyzerView
	 */
	public CheckTestDataCoverageAction(DecisionTableAnalyzerView analyzerView) {

		setText(Messages.getString("DecisionTableAnalyzerView.CheckTestDataCoverage"));
		setToolTipText(Messages.getString("DecisionTableAnalyzerView.CheckTestDataCoverage"));
		setImageDescriptor(DecisionTableUIPlugin.getImageDescriptor("icons/testdata_coverage.png"));
		this.analyzerView = analyzerView;
	}
	
	public void run() {
		try {
			Table tableEModel = analyzerView.getCurrentEditor().getTable();
			String projectName = analyzerView.getCurrentEditor().getProject().getName();
			DecisionTableTestDataSelector decisionTableTestDataSelector = new DecisionTableTestDataSelector(
																							Display.getDefault().getActiveShell(),
																							analyzerView.getCurrentEditor().getProject().getName(),
																							tableEModel);
			if (decisionTableTestDataSelector.open() == Window.OK) {
				Map<String, List<String>> selectedTestDataFiles = decisionTableTestDataSelector.getSelectedTestDataFiles();
				Set<Entry<String, List<String>>>  testDataFilesSet = selectedTestDataFiles.entrySet();
				Iterator<Entry<String, List<String>>> itr = testDataFilesSet.iterator();
				while (itr.hasNext()) {
					Map.Entry<String, List<String>> testDataFileEntry = itr.next();
					String entityPath = testDataFileEntry.getKey();
					List<String> testDataFiles = testDataFileEntry.getValue();
					for (int i = 0; i < testDataFiles.size(); i++) {
						CheckTestDataCoverageTask checkTestDataCoverageTask = new CheckTestDataCoverageTask(projectName, entityPath, testDataFiles.get(i), analyzerView);
						checkTestDataCoverageTask.run();
						DecisionTableTestDataCoverageView testDataResultsView = null;
						testDataResultsView = (DecisionTableTestDataCoverageView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(
																																	IDecisionTableTestDataCoverageView.TESTDATA_COVERAGE_RESULTS_VIEW_ID);
						if (testDataResultsView != null) {
							testDataResultsView.refreshResultsView();
						} else {
							testDataResultsView = (DecisionTableTestDataCoverageView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(
																																		IDecisionTableTestDataCoverageView.TESTDATA_COVERAGE_RESULTS_VIEW_ID);	
							//PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(testDataResultsView);
						}					
					}	
				}
			}
		}
		catch(Exception e) {
			DecisionTableUIPlugin.log(e);
		}
	}	
}
