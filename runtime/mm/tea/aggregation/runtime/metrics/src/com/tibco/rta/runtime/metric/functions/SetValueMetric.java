package com.tibco.rta.runtime.metric.functions;

import com.tibco.rta.Fact;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.SingleValueMetricFunction;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

/**
 * Computes the set value at each node
 * 
 */

public class SetValueMetric extends SingleValueMetricFunction {
	
	Object value;
	
	public static final String PARAM1 = "PARAM1";
	
	@Override
	public Object compute(MetricNode metricNode, SingleValueMetric metric, RtaNodeContext context) {
		if (value == null) {
			value = metric.getValue();
		}		
		return value;
	}

	@Override
	public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
		super.init(fn, measurement, startNode, dh);		
		String attrName = measurement.getFunctionParamBinding(measurement.getMetricFunctionDescriptor().getFunctionParams().iterator().next().getName());
		value = fn.getAttribute(attrName);
	}
}
