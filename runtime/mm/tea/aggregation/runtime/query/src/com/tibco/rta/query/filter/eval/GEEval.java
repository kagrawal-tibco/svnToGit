package com.tibco.rta.query.filter.eval;

import com.tibco.rta.query.filter.GEFilter;
import com.tibco.rta.runtime.model.MetricNode;

class GEEval extends RelationalEval {

	public GEEval(GEFilter filter) {
		super(filter);
	}

	@Override
	public boolean eval(MetricNode node) throws Exception {
		return super.evaluate(node, RelationalOperator.GREATERTHAN_OR_EQUAL, filter.getKeyQualifier(),
				filter.getKey(), filter.getValue());
	}
	
	public String toString() {
		return "GEE: ";
	}
}