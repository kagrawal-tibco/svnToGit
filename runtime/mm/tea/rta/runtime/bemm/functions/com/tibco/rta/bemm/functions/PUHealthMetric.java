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
public class PUHealthMetric extends SingleValueMetricFunction<String> {

	String puHealthValue;
	String puName;

    public static final String PARAM1 = "PARAM1";

    @Override
    public String compute(MetricNode metricNode, SingleValueMetric<String> metric, RtaNodeContext context) {
    	MetricKey metricKey = (MetricKey) metricNode.getKey();
    	if(metricKey.getDimensionLevelName().equals("pu")) {
    		//pu level, just set the raw health
    		return puHealthValue;
    	} else {
    		//cluster level, set the count in the context
    		//to be used for the percent metrics
    		RtaNodeContext puHealthContext = metricNode.getContext("puhealth");
    		puHealthContext.setTuple(puName, puHealthValue);
    		return "na";
    	}
    }

    @Override
    public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
        super.init(fn, measurement, startNode, dh);

        String attrName = measurement.getFunctionParamBinding(measurement.getMetricFunctionDescriptor()
                .getFunctionParams().iterator().next().getName());

        puHealthValue = fn.getAttribute(attrName).toString();
        puName = fn.getAttribute("pu").toString();

    }
}
