/**
 * 
 */
package com.tibco.cep.studio.core.validation.dt;

import com.tibco.be.parser.RuleError;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

/**
 * @author aathalye
 *
 */
public class DecisionTableSyntaxProblemParticipant implements IMarkableEntity {
	
	private TableRuleVariable tableRuleVariable;
	
	/**
	 * The containing {@link Table}
	 */
	private Table tableEModel;
	
	private TableRuleSet trs;
	
	private TableRule containingRule;
	
	/**
	 * The rule error on this participant
	 */
	private RuleError ruleError;
	
	public DecisionTableSyntaxProblemParticipant(final TableRuleVariable tableRuleVariable) {
		this.tableRuleVariable = tableRuleVariable;
	}
	
	public String getId() {
		return ((tableRuleVariable != null)?tableRuleVariable.getId() : null);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof DecisionTableSyntaxProblemParticipant)) {
			return false;
		}
		DecisionTableSyntaxProblemParticipant other = (DecisionTableSyntaxProblemParticipant)obj;
		if (this.getId() != null && this.getId().equals(other.getId())) {
			return true;
		}
		return false;
	}

	/**
	 * @return the tableEModel
	 */
	public final Table getTableEModel() {
		return tableEModel;
	}

	/**
	 * @param tableEModel the tableEModel to set
	 */
	public final void setTableEModel(Table tableEModel) {
		this.tableEModel = tableEModel;
	}

	/**
	 * @return the trs
	 */
	public final TableRuleSet getTrs() {
		return trs;
	}
	
	

	/**
	 * @return the tableRuleVariable
	 */
	public final TableRuleVariable getTableRuleVariable() {
		return tableRuleVariable;
	}

	/**
	 * @param trs the trs to set
	 */
	public final void setTrs(TableRuleSet trs) {
		this.trs = trs;
	}

	/**
	 * @return the containingRule
	 */
	public final TableRule getContainingRule() {
		return containingRule;
	}

	/**
	 * @param containingRule the containingRule to set
	 */
	public final void setContainingRule(TableRule containingRule) {
		this.containingRule = containingRule;
	}

	/**
	 * @return the ruleError
	 */
	public final RuleError getRuleError() {
		return ruleError;
	}

	/**
	 * @param ruleError the ruleError to set
	 */
	public final void setRuleError(RuleError ruleError) {
		this.ruleError = ruleError;
	}
	
	
}
