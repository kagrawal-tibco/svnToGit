package com.tibco.rta.bemm.functions;

import com.tibco.rta.Fact;
import com.tibco.rta.MetricKey;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.SingleValueMetricFunction;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

public class InferenceAgentsPercentActiveMetric extends SingleValueMetricFunction<Long> {

	Long totalAgents;

    public static final String PARAM1 = "PARAM1";

    @Override
    public Long compute(MetricNode metricNode, SingleValueMetric<Long> metric, RtaNodeContext context) {

    	MetricKey metricKey = (MetricKey) metricNode.getKey();
    	if(metricKey.getDimensionLevelName().equals("pu")) {
    		
    		metricKey.getDimensionValue("pu");
    		
    		//pu level, return 100%, NA
    		return 100l;
    	} else if(metricKey.getDimensionLevelName().equals("clust")) {
    		//cluster level, update percentage
    		int numActive = 0;
    		
    		RtaNodeContext activeAgentsContext = metricNode.getContext("activeinferenceagents");
    		for(String puName : activeAgentsContext.getTupleNames()) {
    			numActive=numActive+Integer.parseInt(activeAgentsContext.getTupleValue(puName).toString());
    		}
    		return (numActive*100l)/totalAgents;
    	}
    	else
    	{
    		return -1l;
    	}
    }
    
    @Override
    public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
        super.init(fn, measurement, startNode, dh);

        String attrName = measurement.getFunctionParamBinding(measurement.getMetricFunctionDescriptor()
                .getFunctionParams().iterator().next().getName());

        totalAgents = getAttributeValue(fn.getAttribute(attrName));

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
