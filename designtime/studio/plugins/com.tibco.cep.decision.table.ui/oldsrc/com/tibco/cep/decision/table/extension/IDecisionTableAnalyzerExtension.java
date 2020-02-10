package com.tibco.cep.decision.table.extension;

import org.eclipse.core.resources.IFile;

import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;

/**
 * Extension interface to modify/augment the behavior of
 * the Decision Table Analyzer
 * 
 * @author rhollom
 *
 */
public interface IDecisionTableAnalyzerExtension {

	/**
	 * Return whether the table analyzer should support the 'analyze' action.
	 * Returning false will cause the table analyzer view to hide this action
	 * from the view
	 * @return
	 */
	public boolean canAnalyze();
	
	/**
	 * Return whether the table analyzer should support the 'show coverage' action.
	 * Returning false will cause the table analyzer view to hide this action
	 * from the view
	 * @return
	 */
	public boolean canShowCoverage();
	
	/**
	 * Return whether the table analyzer should support the 'generate test data' action.
	 * Returning false will cause the table analyzer view to hide this action
	 * from the view
	 * @return
	 */
	public boolean canGenerateTestData();
	
	/**
	 * Processes the <code>ProblemEvent</code> detected while analyzing the 
	 * given table
	 * @param event
	 * @param table
	 * @throws Exception
	 */
	public void processAnalyzerProblemEvent(ProblemEvent event, Table table) throws Exception;
	
	/**
	 * Set the current decision table editor based on the analyzer context
	 * @param editor
	 */
	public void setCurrentDecisionTable(DecisionTableEditor editor);

	/**
	 * Get the underlying file against which the analyzer will report problems
	 */
	public IFile getUnderlyingFile();
	
}
