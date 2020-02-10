package com.tibco.rta.bemm.functions;

import com.tibco.rta.Fact;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.SingleValueMetricFunction;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

/**
 * Converts memory usage to percentage and returns the value with 3 precision points
 * 
 * @author vasharma
 *
 */
public class CpuUsagePercentMetric extends SingleValueMetricFunction<Double> {

	Double cpuLoad;

    public static final String PARAM1 = "PARAM1";

    @Override
    public Double compute(MetricNode metricNode, SingleValueMetric<Double> metric, RtaNodeContext context) {
    		//Three precision points for percentage
    		return (((double)((int)(cpuLoad*100000)))/1000);        
    }
    @Override
    public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
        super.init(fn, measurement, startNode, dh);

        String attrName = measurement.getFunctionParamBinding(measurement.getMetricFunctionDescriptor()
                .getFunctionParams().iterator().next().getName());

        cpuLoad = getAttributeValue(fn.getAttribute(attrName));
        if(cpuLoad<0)
        	cpuLoad=0.000;
    }
    
    private Double getAttributeValue(Object attribute) {
		if (attribute instanceof Long) {
			return ((Long) attribute).doubleValue();
		} else if (attribute instanceof Double) {
			return ((Double) attribute).doubleValue();
		} else if (attribute instanceof Integer) {
			return ((Integer) attribute).doubleValue();
		} else if (attribute instanceof Short) {
			return ((Short) attribute).doubleValue();
		} else if (attribute instanceof Byte) {
			return ((Byte) attribute).doubleValue();
		} else {
			return 0D;
		}
	}
}
