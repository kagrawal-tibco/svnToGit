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
 * This metric will return the instance count as follows:
 * -----AGENT DIM LEVEL    : will return 0 , also instance count at instance level will not be applicable
 * -----INSTANCE DIM LEVEL : will return the value as is
 * -----APP DIM LEVEL      : will put the instance name in context and return the context size as total instance count
 * 
 * @author vasharma
 *
 */
public class InstanceCountMetric extends SingleValueMetricFunction<Integer> {

	Integer instanceCount;
	String instanceName;
	
    public static final String PARAM1 = "PARAM1";
    public static final String PARAM2 = "PARAM2";

    @Override
    public Integer compute(MetricNode metricNode, SingleValueMetric<Integer> metric, RtaNodeContext context) {

    	MetricKey metricKey = (MetricKey) metricNode.getKey();
    	if(metricKey.getDimensionLevelName().equals(MetricAttribute.DIM_INSTANCE)) {
    		return instanceCount;
    	} else if(metricKey.getDimensionLevelName().equals(MetricAttribute.DIM_APP)) {
    		int count=0;
    		context.setTuple(instanceName, instanceCount);
    		for(String name:context.getTupleNames()){
    			if((int)context.getTupleValue(name)==1){
    				count++;
    			}
    		}
    		return count;
    		}
    	return 0;
    }


    @Override
    public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
        super.init(fn, measurement, startNode, dh);
        String attrInstanceName = measurement.getFunctionParamBinding(PARAM1);
        instanceName = fn.getAttribute(attrInstanceName).toString();
        String attrInstanceCount = measurement.getFunctionParamBinding(PARAM2);
        instanceCount = getAttributeValue(fn.getAttribute(attrInstanceCount));
    }
    
    private Integer getAttributeValue(Object attribute) {
		if (attribute instanceof Long) {
			return ((Long) attribute).intValue();
		} else if (attribute instanceof Integer) {
			return ((Integer) attribute).intValue();
		} else if (attribute instanceof Short) {
			return ((Short) attribute).intValue();
		} else if (attribute instanceof Byte) {
			return ((Byte) attribute).intValue();
		} else {
			return 0;
		}
	}
}
