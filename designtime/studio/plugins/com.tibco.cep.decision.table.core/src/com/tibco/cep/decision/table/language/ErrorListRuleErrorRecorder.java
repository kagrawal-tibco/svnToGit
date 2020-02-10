package com.tibco.cep.decision.table.language;

import java.util.ArrayList;
import java.util.List;

import com.tibco.be.parser.RuleError;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.core.validation.dt.DecisionTableErrorRecorder;
import com.tibco.cep.studio.core.validation.dt.DecisionTableSyntaxProblemParticipant;

public class ErrorListRuleErrorRecorder implements DecisionTableErrorRecorder {
	protected List<RuleError> errorList = null;
	
	public ErrorListRuleErrorRecorder() {}
	public ErrorListRuleErrorRecorder(List<RuleError> errorList) {
		this.errorList = errorList;
	}
	
	public void reportError(RuleError rulErr) {
		reportError(rulErr, null, null, null, null);
	}
	
	public void reportError(RuleError rulErr, Table table, TableRuleSet trs, TableRule row, TableRuleVariable trv) {
		if(rulErr != null) {
			rulErr.setSource(row);
			if (trv != null) rulErr.setColumnId(trv.getColId());
			if (row != null) rulErr.setRowId(row.getId());
			if(errorList == null) errorList = new ArrayList<RuleError>();
			errorList.add(rulErr);
		}
	}
	
	public List<RuleError> getErrorList() {
		return errorList;
	}
	
	public int numErrors() {
		if(errorList == null) return 0;
		return errorList.size();
	}
	public boolean hasErrors() {
		return numErrors() > 0;
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.dt.RuleErrorRecorder#clear()
	 */
	
	public void clear() {
		errorList.clear();
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.dt.RuleErrorRecorder#reportAllErrors()
	 */
	
	public void reportAllErrors() {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.dt.RuleErrorRecorder#clearError(com.tibco.cep.studio.core.validation.dt.DecisionTableSyntaxProblemParticipant)
	 */
	
	public void clearError(
			DecisionTableSyntaxProblemParticipant problemParticipant) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.dt.DecisionTableErrorRecorder#clearError(com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable, com.tibco.cep.decision.table.model.dtmodel.TableRule)
	 */
	public void clearError(TableRuleVariable problemTableRuleVar,
			TableRule containingRule) {
		// TODO Auto-generated method stub
		
	}
	
	
}
