package com.tibco.rta.query.filter.eval;

import com.tibco.rta.query.filter.LtFilter;
import com.tibco.rta.runtime.model.MetricNode;

class LtEval extends RelationalEval {
	
	public LtEval(LtFilter filter) {
		super(filter);
	}
	
	@Override
	public boolean eval(MetricNode node) throws Exception {
		return super.evaluate(node, RelationalOperator.LESSTHAN, filter.getKeyQualifier(),
				filter.getKey(), filter.getValue());

	}


	
	public String toString() {
		return "LTE: ";
	}
}