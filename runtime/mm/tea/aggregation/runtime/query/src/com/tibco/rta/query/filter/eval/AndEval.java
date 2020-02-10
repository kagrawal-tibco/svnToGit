package com.tibco.rta.query.filter.eval;

import com.tibco.rta.query.filter.AndFilter;
import com.tibco.rta.runtime.model.MetricNode;

class AndEval extends LogicalEval {

	public AndEval(AndFilter filter) {
		super(filter);
	}
	
	@Override
	public boolean eval(MetricNode node) throws Exception {
		for (FilterEvaluator f : filterEvalsList) {
			if (!f.eval(node)) {
				return false;
			}
		}
		return true;
	}
	
	public String toString() {
		String s =  "ANDE: ";
		for (FilterEvaluator f : filterEvalsList) {
			s += f;
		}
		return s;
	}
	
}