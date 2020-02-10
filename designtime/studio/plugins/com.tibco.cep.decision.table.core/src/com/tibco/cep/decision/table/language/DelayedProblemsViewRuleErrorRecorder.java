package com.tibco.cep.decision.table.language;

import java.util.ArrayList;

import com.tibco.be.parser.RuleError;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.studio.util.logger.problems.ProblemEventManager;

public class DelayedProblemsViewRuleErrorRecorder extends ProblemsViewRuleErrorRecorder {
	
	protected ArrayList<ProblemEvent> problemEvents = null;
	
	public void reportError(RuleError rulErr) {
		reportError(rulErr, null, null, null, null);
	}
	
	public void reportError(RuleError rulErr, Table table, TableRuleSet trs, TableRule row, TableRuleVariable col) {
		if(rulErr == null) return;
		if(problemEvents == null) problemEvents = new ArrayList<ProblemEvent>();
		problemEvents.add(makeProblemEvent(rulErr, table, trs, row, col));
	}
	
	public void postAllProblems() {
		if(problemEvents == null) return;
		ProblemEventManager pem = ProblemEventManager.getInstance();
		if(pem == null) return;
		for(ProblemEvent pe : problemEvents) {
			pem.postProblem(pe);
		}
	}
	
	public void clear() {
		if(problemEvents != null) {
			problemEvents.clear();
		}
	}
	
	public boolean hasErrors() {
		return numErrors() > 0;
	}
	
	public int numErrors() {
		if(problemEvents == null) return 0;
		else return problemEvents.size();
	}
}
