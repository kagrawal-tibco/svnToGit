package com.tibco.cep.decision.table.language;

import com.tibco.be.parser.RuleError;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.util.logger.problems.ProblemEventManager;

public class ImmediateProblemsViewRuleErrorRecorder extends ProblemsViewRuleErrorRecorder {
	public void reportError(RuleError rulErr) {
		reportError(rulErr, null, null, null, null);
	}
	public void reportError(RuleError rulErr, Table table, TableRuleSet trs, TableRule row, TableRuleVariable col) {
		ProblemEventManager.getInstance().postProblem(makeProblemEvent(rulErr, table, trs, row, col));
	}
	
}
