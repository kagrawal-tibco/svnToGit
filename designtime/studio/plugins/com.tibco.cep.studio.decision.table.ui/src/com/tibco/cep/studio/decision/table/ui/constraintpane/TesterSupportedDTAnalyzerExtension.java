package com.tibco.cep.studio.decision.table.ui.constraintpane;

import org.eclipse.jface.action.IAction;

import com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerView;
import com.tibco.cep.studio.decision.table.constraintpane.SimpleDecisionTableAnalyzerExtension;

public class TesterSupportedDTAnalyzerExtension extends SimpleDecisionTableAnalyzerExtension {

	/**
	 * @param decisionTableAnalyzerView
	 */
	public TesterSupportedDTAnalyzerExtension() {
	}

	@Override
	public boolean canAnalyze() {
		return true;
	}

	@Override
	public boolean canGenerateTestData() {
		return true;
	}

	@Override
	public boolean canShowCoverage() {
		return true;
	}

	@Override
	public boolean canShowTestDataCoverage() {
		return true;
	}
	
	@Override
	public IAction getGenerateTestDataAction(DecisionTableAnalyzerView view) {
		return new GenerateTestDataAction(view);
	}
	
	@Override
	public IAction getShowTestDataCoverageAction(DecisionTableAnalyzerView view) {
		return new CheckTestDataCoverageAction(view);
	}
}