package com.tibco.rta.query.filter.eval;

import com.tibco.rta.runtime.model.MetricNode;

public interface FilterEvaluator {
	
	boolean eval(MetricNode node) throws Exception;

}
