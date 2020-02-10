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

public class SumMetric extends SingleValueMetricFunction<Double> {
	
	double sum;
	
	public static final String PARAM1 = "PARAM1";
	
	@Override
	public Double compute(MetricNode metricNode, SingleValueMetric<Double> metric, RtaNodeContext context1) {
		Double d = metric.getValue();
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
			if (number instanceof Double) {
				sum = (Double)number;
			} else if (number instanceof Float) {
				sum = number.doubleValue();
			} else if (number instanceof Long) {
				sum = number.doubleValue();
			} else if (number instanceof Integer) {
				sum = number.doubleValue();
			} else if (number instanceof Short) {
				sum = number.doubleValue();
			} else if (number instanceof Byte) {
				sum = number.doubleValue();
			} else {
				sum = 0.0;
			}
		} else {
			sum = 0.0;
		}
	}
}
