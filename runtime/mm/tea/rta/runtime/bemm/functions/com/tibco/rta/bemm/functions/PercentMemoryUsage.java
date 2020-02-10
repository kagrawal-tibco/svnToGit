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
public class PercentMemoryUsage extends SingleValueMetricFunction<Double> {

	Double memUsage=0D;
	Double maxMem=0D;

    public static final String PARAM1 = "PARAM1";
    public static final String PARAM2 = "PARAM2";

    @Override
    public Double compute(MetricNode metricNode, SingleValueMetric<Double> metric, RtaNodeContext context) {
    		//Two precision points for percentage Memory
    		if(maxMem==0D)
    			return 0D;
    		Double percent=(memUsage/maxMem)*100;
    		return (((double)((int)(percent*100)))/100);        
    }
    @Override
    public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
        super.init(fn, measurement, startNode, dh);

        String  attrMemUsage= measurement.getFunctionParamBinding(PARAM1);
        String  attrMaxMem= measurement.getFunctionParamBinding(PARAM2);

        memUsage = getAttributeValue(fn.getAttribute(attrMemUsage));
        maxMem = getAttributeValue(fn.getAttribute(attrMaxMem));
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
