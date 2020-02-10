package com.tibco.rta.query.filter.eval;

import com.tibco.rta.query.filter.OrFilter;
import com.tibco.rta.runtime.model.MetricNode;

class OrEval extends LogicalEval {

	public OrEval(OrFilter filter) {
		super(filter);
	}
	
	@Override
	public boolean eval(MetricNode node) throws Exception {
		for (FilterEvaluator f : filterEvalsList) {
			if (f.eval(node)) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		String s =  "ORE: ";
		for (FilterEvaluator f : filterEvalsList) {
			s += f;
		}
		return s;
	}
}