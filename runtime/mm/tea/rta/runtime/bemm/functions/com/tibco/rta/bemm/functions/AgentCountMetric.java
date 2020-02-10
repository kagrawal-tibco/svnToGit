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
 * This metric will return the agent count as follows:
 * -----AGENT DIM LEVEL    : will return the count as is
 * -----INSTANCE DIM LEVEL : will return 0 , also agent count at instance level will not be applicable
 * -----APP DIM LEVEL      : will return the aggregated value for total agent count
 * 
 * @author vasharma
 *
 */
public class AgentCountMetric extends SingleValueMetricFunction<Integer> {

	Integer agentCount;
	String  agentName;
	String  instanceName;

	public static final String PARAM1 = "PARAM1";
	public static final String PARAM2 = "PARAM2";
	public static final String PARAM3 = "PARAM3";

	@Override
	public Integer compute(MetricNode metricNode, SingleValueMetric<Integer> metric, RtaNodeContext context) {

		MetricKey metricKey = (MetricKey) metricNode.getKey();
		if(metricKey.getDimensionLevelName().equals(MetricAttribute.DIM_AGENT)) {
			return agentCount;
		} else if(metricKey.getDimensionLevelName().equals(MetricAttribute.DIM_APP)) {
			context.setTuple(instanceName+"-"+agentName, agentCount);
			return context.getTupleNames().length;
		}
		return 0;
	}

	@Override
	public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
		super.init(fn, measurement, startNode, dh);
		
		String attrInstanceName = measurement.getFunctionParamBinding(PARAM1);
		instanceName = fn.getAttribute(attrInstanceName).toString();
		String attrAgentName = measurement.getFunctionParamBinding(PARAM2);
		agentName = fn.getAttribute(attrAgentName).toString();
		String attrAgentCount = measurement.getFunctionParamBinding(PARAM3);
		agentCount = getAttributeValue(fn.getAttribute(attrAgentCount));
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
