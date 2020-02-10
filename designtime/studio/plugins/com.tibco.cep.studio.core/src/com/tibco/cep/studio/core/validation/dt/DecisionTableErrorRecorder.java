/**
 * 
 */
package com.tibco.cep.studio.core.validation.dt;

import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

/**
 * Interface to be implemented for recording errors in decision table
 * @author aathalye
 *
 */
public interface DecisionTableErrorRecorder extends RuleErrorRecorder {
	
	void clearError(DecisionTableSyntaxProblemParticipant problemParticipant);
	
	void clearError(TableRuleVariable problemTableRuleVar, TableRule containingRule);
}
