/**
 * 
 */
package com.tibco.cep.studio.core.validation.dt;

import com.tibco.be.parser.RuleError;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;


public interface RuleErrorRecorder {
	
	void reportError(RuleError rulErr, Table table, TableRuleSet trs, TableRule row, TableRuleVariable col);
	
	void reportError(RuleError rulErr);
	
	void clear();
	
	/**
	 * Batch reporting of all errors
	 */
	void reportAllErrors();
}
