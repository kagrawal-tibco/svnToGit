package com.tibco.rta.runtime.metric.functions;

import com.tibco.rta.Fact;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.SingleValueMetricFunction;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

/**
 * Computes the sum value at each node
 * 
 */

public class SumMetricLong extends SingleValueMetricFunction<Long> {
	
	Long sum;
	
	public static final String PARAM1 = "PARAM1";
	
	@Override
	public Long compute(MetricNode metricNode, SingleValueMetric<Long> metric, RtaNodeContext context1) {
		Long d = metric.getValue();
		if (d == null) {
			return sum;
		} else {
			return sum + d;
		}
	}

	@Override
	public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
		super.init(fn, measurement, startNode, dh);
		String attrName = measurement.getFunctionParamBinding(measurement.getMetricFunctionDescriptor()
				.getFunctionParams().iterator().next().getName());
		
		if (fn.getAttribute(attrName) instanceof Number) {
			Number number = (Number) fn.getAttribute(attrName);
			if (number instanceof Long) {
				sum = (Long)number;
			} else if (number instanceof Float) {
				sum = number.longValue();
			} else if (number instanceof Double) {
				sum = number.longValue();
			} else if (number instanceof Integer) {
				sum = number.longValue();
			} else if (number instanceof Short) {
				sum = number.longValue();
			} else if (number instanceof Byte) {
				sum = number.longValue();
			} else {
				sum = 0l;
			}
		} else {
			sum = 0l;
		}
	}
}
