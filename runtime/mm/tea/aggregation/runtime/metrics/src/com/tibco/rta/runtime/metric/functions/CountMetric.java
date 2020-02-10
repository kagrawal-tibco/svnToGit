package com.tibco.rta.runtime.metric.functions;

import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.runtime.metric.SingleValueMetricFunction;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;


/**
 * 
 * @author bgokhale
 * 
 * Computes the count
 * 
 */
public class CountMetric extends SingleValueMetricFunction<Long> {

	@Override
	public Long compute(MetricNode metricNode, SingleValueMetric<Long> metric, RtaNodeContext context) {
		if (metric.getValue() == null) {
			return 1L;
		} else {
			return metric.getValue() + 1;
		}
	}
	
}
