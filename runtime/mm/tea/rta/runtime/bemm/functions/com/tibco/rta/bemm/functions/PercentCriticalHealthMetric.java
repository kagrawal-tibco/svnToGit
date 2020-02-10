package com.tibco.rta.bemm.functions;

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
import com.tibco.tea.agent.be.util.BEEntityHealthStatus;
import com.tibco.tea.agent.be.util.BETeaAgentProps;

/**
 * This metric will return the percentage critical value as  follows:
 * -----AGENT DIM LEVEL    : return -1 : not applicable at this level
 * -----INSTANCE DIM LEVEL : return -1 : not applicable at this level 
 * -----APP DIM LEVEL      : This is a generic method to evaluate %critical value for both agent and instance
 * 							 It differentiates based on the attribute name passed at to the metric.
 * 							 Further it evaluates the value by getting the health of all applicable entities from 
 * 							 the context and evaluating over the total entity count 
 * 
 * @author mjinia,vasharma
 *
 */
public class PercentCriticalHealthMetric extends SingleValueMetricFunction<Long> {

	String entityType;

	public static final String PARAM1 = "PARAM1";


	@Override
	public Long compute(MetricNode metricNode, SingleValueMetric<Long> metric, RtaNodeContext context) {

		MetricKey metricKey = (MetricKey) metricNode.getKey();
		if(metricKey.getDimensionLevelName().equals(MetricAttribute.DIM_APP)) {
			
			if(entityType.equals(MetricAttribute.PU_INSTANCE_HEALTH)){	
				int num = 0;
				RtaNodeContext rawHealthContext = metricNode.getContext(MetricAttribute.PU_INSTANCE_HEALTH);
				for(String instanceName : rawHealthContext.getTupleNames()) {
					String healthValue = (String) rawHealthContext.getTupleValue(instanceName);
					if(BEEntityHealthStatus.critical.getHealthStatus().equals(healthValue)) {
						num++;
					}
				}

				SingleValueMetric<Object> instanceCountMetric = (SingleValueMetric<Object>) metricNode.getMetric(MetricAttribute.PU_INSTANCE_ISACTIVE);
				Long total=getAttributeValue(instanceCountMetric.getValue());
				return (total==0)?0:((num*100l)/total);
			}
			else if(entityType.equals(MetricAttribute.AGENT_HEALTH)){
				int num = 0;
				RtaNodeContext rawHealthContext = metricNode.getContext(MetricAttribute.AGENT_HEALTH);
				for(String instanceName : rawHealthContext.getTupleNames()) {
					Map<String,String> agentHealthMap = (Map<String,String>) rawHealthContext.getTupleValue(instanceName);
					for(Map.Entry<String, String> entry : agentHealthMap.entrySet() ){
						if(BEEntityHealthStatus.critical.getHealthStatus().equals(entry.getValue())) {
							num++;
						}
					}
				}
				SingleValueMetric<Object> agentCountMetric = (SingleValueMetric<Object>) metricNode.getMetric(MetricAttribute.AGENT_COUNT);
				Long total=getAttributeValue(agentCountMetric.getValue());
				return (total==0)?0:((num*100l)/total);
			}
			else
				return -1l;
		}
		else
			return -1l;
	}

	@Override
	public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
		super.init(fn, measurement, startNode, dh);
		entityType = measurement.getFunctionParamBinding(PARAM1);
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
