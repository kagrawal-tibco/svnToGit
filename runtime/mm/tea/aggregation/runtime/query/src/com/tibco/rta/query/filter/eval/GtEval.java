package com.tibco.rta.query.filter.eval;

import com.tibco.rta.query.filter.GtFilter;
import com.tibco.rta.runtime.model.MetricNode;

class GtEval extends RelationalEval {
	
	public GtEval(GtFilter filter) {
		super(filter);
	}
	
	@Override
	public boolean eval(MetricNode node) throws Exception {		
		return super.evaluate(node, RelationalOperator.GREATERTHAN, filter.getKeyQualifier(),
			filter.getKey(), filter.getValue());
	}
	
	public String toString() {
		return "GTE: ";
	}
}