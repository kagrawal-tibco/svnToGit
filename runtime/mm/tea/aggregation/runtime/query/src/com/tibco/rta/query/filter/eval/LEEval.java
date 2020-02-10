package com.tibco.rta.query.filter.eval;

import com.tibco.rta.query.filter.LEFilter;
import com.tibco.rta.runtime.model.MetricNode;

class LEEval extends RelationalEval {
	
	public LEEval(LEFilter filter) {
		super(filter);
	}
	
	@Override
	public boolean eval(MetricNode node) throws Exception {
		return super.evaluate(node, RelationalOperator.LESSTHAN_OR_EQUAL, filter.getKeyQualifier(),
				filter.getKey(), filter.getValue());

	}
	
	public String toString() {
		return "LEE: ";
	}
}