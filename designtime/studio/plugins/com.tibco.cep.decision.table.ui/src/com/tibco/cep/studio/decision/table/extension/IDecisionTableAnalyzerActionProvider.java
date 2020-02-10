package com.tibco.cep.studio.decision.table.extension;

import org.eclipse.jface.action.IAction;

import com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerView;

/**
 * Provides the IActions for
 * the Decision Table Analyzer's supported actions
 * 
 * @author rhollom
 *
 */
public interface IDecisionTableAnalyzerActionProvider {

	/**
	 * Return whether the table analyzer should support the 'analyze' action.
	 * Returning false will cause the table analyzer view to hide this action
	 * from the view
	 * @param view 
	 * @return
	 */
	public IAction getAnalyzeAction(DecisionTableAnalyzerView view);
	
	/**
	 * Return whether the table analyzer should support the 'show coverage' action.
	 * Returning false will cause the table analyzer view to hide this action
	 * from the view
	 * @return
	 */
	public IAction getShowCoverageAction(DecisionTableAnalyzerView view);
	
	/**
	 * Return whether the table analyzer should support the 'generate test data' action.
	 * Returning false will cause the table analyzer view to hide this action
	 * from the view
	 * @return
	 */
	public IAction getGenerateTestDataAction(DecisionTableAnalyzerView view);
	
	/**
	 * Return whether the table analyzer should support the 'show test data coverage' action.
	 * Returning false will cause the table analyzer view to hide this action
	 * from the view
	 * @return
	 */
	public IAction getShowTestDataCoverageAction(DecisionTableAnalyzerView view);

}
