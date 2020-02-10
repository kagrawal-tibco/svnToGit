package com.tibco.rta.bemm.functions;

import java.util.HashMap;
import java.util.Map;

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
 * This metric will return the agent Health as follows:
 * -----AGENT DIM LEVEL    : will return the health as is
 * -----INSTANCE DIM LEVEL : will return na , also agent health at instance level will not be applicable
 * -----APP DIM LEVEL      : will put the agent health /agent name in the context and return na
 * 
 * @author vasharma
 *
 */
public class AgentHealthMetric extends SingleValueMetricFunction<String> {

	String agentHealthValue;
	String agentName;
	String instanceName;
	Integer agentDeployed=0;

    public static final String PARAM1 = "PARAM1";
    public static final String PARAM2 = "PARAM2";
    public static final String PARAM3 = "PARAM3";
    public static final String PARAM4 = "PARAM4";

    @Override
    public String compute(MetricNode metricNode, SingleValueMetric<String> metric, RtaNodeContext context) {
    	MetricKey metricKey = (MetricKey) metricNode.getKey();
    	if(metricKey.getDimensionLevelName().equals(MetricAttribute.DIM_APP)) {
    		RtaNodeContext appHealthContext = metricNode.getContext(MetricAttribute.AGENT_HEALTH);
    		
    		Map<String,String> agentHealthMap=(Map<String, String>) appHealthContext.getTupleValue(instanceName);
    		if(agentHealthMap!=null) {
    			agentHealthMap.put(agentName, agentHealthValue);
    		}
    		else {
    			agentHealthMap=new HashMap<String,String>();
        		agentHealthMap.put(agentName, agentHealthValue);
    		}
    		appHealthContext.setTuple(instanceName, agentHealthMap);
    		return "na";
    	} else if(metricKey.getDimensionLevelName().equals(MetricAttribute.DIM_AGENT)) {
    		return agentHealthValue;
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
        String attrName4= measurement.getFunctionParamBinding(PARAM4);
        instanceName = fn.getAttribute(attrName1).toString();
        agentName = fn.getAttribute(attrName2).toString();
        agentHealthValue = fn.getAttribute(attrName3).toString();
        agentDeployed = getIntegerAttributeValue(fn.getAttribute(attrName4));

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
