package com.tibco.cep.decision.table.language;

import com.tibco.be.parser.RuleError;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.core.validation.dt.RuleErrorRecorder;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.studio.util.logger.problems.ProblemEventManager;

public abstract class ProblemsViewRuleErrorRecorder implements RuleErrorRecorder {
	
	public static final String PROBLEM_CODE_SYNTAX_ERROR = "Syntax Error";
	public static final String PROBLEM_CODE_SEMANTIC_ERROR = "Language Validation";
	public static final String PROBLEM_CODE_INTERNAL_ERROR = "Language Validation Internal Error";
	
	private static final String COLUMN_DATA = "COLUMN_DATA";
	private static final String DECISION_TABLE = "DT";
	private static final String TABLE_TYPE = "TABLE_TYPE";
	private static final String EXCEPTION_TABLE = "ET";
	
	protected static ProblemEvent makeProblemEvent(RuleError rulErr) {
		return makeProblemEvent(rulErr, null, null, null, null);
	}
	
	protected static ProblemEvent makeProblemEvent(RuleError rulErr, Table table, TableRuleSet trs, TableRule row, TableRuleVariable col) {
		String problemCode = null;
		if(rulErr.isInternalError()) problemCode = PROBLEM_CODE_INTERNAL_ERROR;
		if(rulErr.isSyntaxError()) problemCode = PROBLEM_CODE_SYNTAX_ERROR;
		//don't think we produce any warnings currently
		if(rulErr.isSemanticError() || rulErr.isWarning()) problemCode = PROBLEM_CODE_SEMANTIC_ERROR;
		
		int severity = ProblemEvent.ERROR;
		if(rulErr.isWarning()) severity = ProblemEvent.WARNING;
		
		String tablePath = null;
		int rowId = -1;
		if(table != null) {
			tablePath = table.getFolder();
			if(tablePath != null && !tablePath.endsWith("/")) {
				tablePath += "/";
			}
			tablePath += table.getName();
		}
			
		if(row != null) {
			try {
				if(row.getId() != null) {
					rowId = Integer.parseInt(row.getId());
				}
			} catch (NumberFormatException nfe) {}
		}
		ProblemEvent event = ProblemEventManager.makeProblemEvent(null, problemCode, rulErr.getMessage(), tablePath, rowId, severity);
		if(col != null) event.setData(COLUMN_DATA, col);
		if (table != null && trs != null) {
			// Don't set the data to the TableRuleSet itself.
			// It is not needed, and we don't want to hang
			// on to it so it can be garbage collected
			if (trs.equals(table.getDecisionTable())) {
				event.setData(TABLE_TYPE, DECISION_TABLE);
			} else if (trs.equals(table.getExceptionTable())) {
				event.setData(TABLE_TYPE, EXCEPTION_TABLE);
			}
		}
		return event;
	}
	
	public void clear() {
		ProblemEventManager pem = ProblemEventManager.getInstance();
		pem.clearAllProblems();
	}

	public static void clearRuleErrorProblems() {
		ProblemEventManager pem = ProblemEventManager.getInstance();
		if(pem != null) {
			pem.clearProblems(PROBLEM_CODE_SYNTAX_ERROR);
			pem.clearProblems(PROBLEM_CODE_SEMANTIC_ERROR);
			pem.clearProblems(PROBLEM_CODE_INTERNAL_ERROR);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.dt.RuleErrorRecorder#reportAllErrors()
	 */
	
	public void reportAllErrors() {
		// TODO Auto-generated method stub
		
	}
	
	
}
