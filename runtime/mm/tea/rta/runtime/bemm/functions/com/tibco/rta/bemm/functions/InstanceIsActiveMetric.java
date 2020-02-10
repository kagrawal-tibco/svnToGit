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
 * This metric will return Active instance count:
 * -----AGENT DIM LEVEL    : will return 0 , also this metric at instance level will not be applicable
 * -----INSTANCE DIM LEVEL : will return the value as is 
 * -----APP DIM LEVEL      : will put the instance name in context and then iterate over the context and evaluate the active counts
 * 
 * @author mjinia,vasharma
 *
 */
public class InstanceIsActiveMetric extends SingleValueMetricFunction<Integer> {

	Integer isActiveValue;
	String instanceName;

	public static final String PARAM1 = "PARAM1";
	public static final String PARAM2 = "PARAM2";

	@Override
	public Integer compute(MetricNode metricNode, SingleValueMetric<Integer> metric, RtaNodeContext context) {
		MetricKey metricKey = (MetricKey) metricNode.getKey();
		if(metricKey.getDimensionLevelName().equals(MetricAttribute.DIM_APP)) {
			int activeCount=0;
			context.setTuple(instanceName, isActiveValue);
			for(String key : context.getTupleNames()) {
    			int  active= (Integer) context.getTupleValue(key);
    			if(active==1) {
    				activeCount++;
    			}
    		}
			return activeCount;
		}
		else
			return isActiveValue;
	}
	@Override
	public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
		super.init(fn, measurement, startNode, dh);
		String attrInstanceName = measurement.getFunctionParamBinding(PARAM1);
		String attrInstanceActive = measurement.getFunctionParamBinding(PARAM2);
		instanceName = fn.getAttribute(attrInstanceName).toString();
		isActiveValue = getAttributeValue(fn.getAttribute(attrInstanceActive).toString());
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
		} else if (attribute instanceof String) {
			return Integer.parseInt((String) attribute);
		}else {
			return 0;
		}
	}


}
