package com.tibco.rta.bemm.functions;

import com.tibco.rta.Fact;
import com.tibco.rta.Metric;
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
public class AgentTypePercentCriticalHealthMetric extends SingleValueMetricFunction<Long> {

	String agentType;

	public static final String PARAM1 = "PARAM1";

	@Override
	public Long compute(MetricNode metricNode, SingleValueMetric<Long> metric, RtaNodeContext context) {

		MetricKey metricKey = (MetricKey) metricNode.getKey();
		/*if(metricKey.getDimensionLevelName().equals("app")) {

			SingleValueMetric<Object> agentCountMetric = (SingleValueMetric<Object>) metricNode.getMetric("agentcount");
			Long total=getAttributeValue(agentCountMetric.getValue());
			int agentTypeCount=0;
			for(String key : context.getTupleNames())
			{
				if(agentType.equals(key))
				{
					agentTypeCount++;
				}
			}
			if(total==0)
				return 0l;

			return (agentTypeCount*100l)/total;
		}
		else if(metricKey.getDimensionLevelName().equals("agent")) {
			int agentCount=0;
			SingleValueMetric<Object> agentHealthMetric = (SingleValueMetric<Object>) metricNode.getMetric("agenthealth");
			String agentHealth=agentHealthMetric.getValue().toString();
			if(agentHealth.equals("critical"))
			{	
				if(context.getTupleValue(agentType)!=null)
					agentCount=(int) context.getTupleValue(agentType);
				context.setTuple(agentType, agentCount+1);
			}
			return  -1l;
		}
		else
			return -1l;*/
		return 0l;
	}

	@Override
	public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
		super.init(fn, measurement, startNode, dh);

		String type = measurement.getFunctionParamBinding(PARAM1);

		agentType=fn.getAttribute(type).toString();
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
