package com.tibco.rta.bemm.functions;

import com.tibco.rta.Fact;
import com.tibco.rta.MetricKey;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.SingleValueMetricFunction;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

/**
 * 
 * @author vasharma
 *
 */
public class ActivePUCountMetric extends SingleValueMetricFunction<Long> {

	Long totalPUs;

    public static final String PARAM1 = "PARAM1";

    @Override
    public Long compute(MetricNode metricNode, SingleValueMetric<Long> metric, RtaNodeContext context) {

    	MetricKey metricKey = (MetricKey) metricNode.getKey();
    	if(metricKey.getDimensionLevelName().equals("pu")) {
    		//pu level, return -1, NA
    		return -1l;
    	} else {
    		//cluster level, update percentage
    		int numActive = 0;
    		
    		RtaNodeContext puIsActiveContext = metricNode.getContext("puisactive");
    		for(String puName : puIsActiveContext.getTupleNames()) {
    			if("Y".equalsIgnoreCase((String)puIsActiveContext.getTupleValue(puName)))
    				numActive++;
    			}
    		return (numActive*100l)/totalPUs;
    		
    		}    		
    }


    @Override
    public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
        super.init(fn, measurement, startNode, dh);

        String attrName = measurement.getFunctionParamBinding(measurement.getMetricFunctionDescriptor()
                .getFunctionParams().iterator().next().getName());

        totalPUs = getAttributeValue(fn.getAttribute(attrName));

    }
    
    private Long getAttributeValue(Object attribute) {
		if (attribute instanceof Long) {
			return ((Long) attribute).longValue();
		} else if (attribute instanceof Integer) {
			return ((Integer) attribute).longValue();
		} else if (attribute instanceof Short) {
			return ((Short) attribute).longValue();
		} else if (attribute instanceof Byte) {
			return ((Byte) attribute).longValue();
		} else {
			return 0L;
		}
	}
}
