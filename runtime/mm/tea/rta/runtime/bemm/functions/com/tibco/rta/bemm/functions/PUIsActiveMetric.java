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
 * @author mjinia
 *
 */
public class PUIsActiveMetric extends SingleValueMetricFunction<Boolean> {

	boolean isActiveValue;
	String puName;

    public static final String PARAM1 = "PARAM1";

    @Override
    public Boolean compute(MetricNode metricNode, SingleValueMetric<Boolean> metric, RtaNodeContext context) {
    	MetricKey metricKey = (MetricKey) metricNode.getKey();
    	if(metricKey.getDimensionLevelName().equals("pu")) {
    		//pu level, just set the isActive value
    		return isActiveValue;
    	} else {
    		//cluster level, set the count in the context
    		//to be used for the percent metrics
    		RtaNodeContext puIsActiveContext = metricNode.getContext("puisactive");
    		puIsActiveContext.setTuple(puName, isActiveValue);
    		return false; //na for cluster
    	}
    }

    @Override
    public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
        super.init(fn, measurement, startNode, dh);

        String attrName = measurement.getFunctionParamBinding(measurement.getMetricFunctionDescriptor()
                .getFunctionParams().iterator().next().getName());

        isActiveValue = Boolean.parseBoolean(fn.getAttribute(attrName).toString());
        puName = fn.getAttribute("pu").toString();

    }
}
