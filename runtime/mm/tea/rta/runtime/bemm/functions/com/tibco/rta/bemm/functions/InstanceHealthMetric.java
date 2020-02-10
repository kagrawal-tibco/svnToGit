package com.tibco.rta.bemm.functions;

import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.rta.Fact;
import com.tibco.rta.MetricKey;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.SingleValueMetricFunction;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

/**
 * This metric will return the instance health value as  follows:
 * -----AGENT DIM LEVEL    : return na : not applicable at this level
 * -----INSTANCE DIM LEVEL : return the health value as is
 * -----APP DIM LEVEL      : set instance name/health value in the context and return na 
 * 
 * @author mjinia,vasharma
 *
 */
public class InstanceHealthMetric extends SingleValueMetricFunction<String> {

	String puHealthValue;
	String instanceName;
	Integer instanceDeployed=0;

    public static final String PARAM1 = "PARAM1";
    public static final String PARAM2 = "PARAM2";
    public static final String PARAM3 = "PARAM3";

    @Override
    public String compute(MetricNode metricNode, SingleValueMetric<String> metric, RtaNodeContext context) {
    	MetricKey metricKey = (MetricKey) metricNode.getKey();
    	
    	if(metricKey.getDimensionLevelName().equals(MetricAttribute.DIM_APP)) {
    		RtaNodeContext puHealthContext = metricNode.getContext(MetricAttribute.PU_INSTANCE_HEALTH);
    		if(instanceDeployed==1)
    			puHealthContext.setTuple(instanceName, puHealthValue);
    		return "na";
    	} else if(metricKey.getDimensionLevelName().equals(MetricAttribute.DIM_INSTANCE)) {
    		return puHealthValue;
    	}
    	else
    		return "na";
    }

    @Override
    public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
        super.init(fn, measurement, startNode, dh);

        String attrName1= measurement.getFunctionParamBinding(PARAM1);
        String attrName2= measurement.getFunctionParamBinding(PARAM2);
        String attrName3= measurement.getFunctionParamBinding(PARAM3);
        instanceName = fn.getAttribute(attrName1).toString();
        puHealthValue = fn.getAttribute(attrName2).toString();
        instanceDeployed = getIntegerAttributeValue(fn.getAttribute(attrName3));

    }
    
    private Integer getIntegerAttributeValue(Object attribute) {
		if (attribute instanceof Integer) {
			return ((Integer) attribute).intValue();
		} else if (attribute instanceof Integer) {
			return ((Long) attribute).intValue();
		} else if (attribute instanceof Short) {
			return ((Short) attribute).intValue();
		} else if (attribute instanceof Byte) {
			return ((Byte) attribute).intValue();
		} else {
			return 0;
		}
	}
}
