package com.tibco.rta.bemm.functions;

import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;
import com.tibco.rta.Fact;
import com.tibco.rta.MetricKey;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.SingleValueMetricFunction;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MutableMetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

/**
 * This metric will return the instance health value as  follows:
 * -----AGENT DIM LEVEL    : delete dummy agent node and return na
 * -----INSTANCE DIM LEVEL : delete dummy instance node and return na
 * -----APP DIM LEVEL      : delete dummy app node and return na 
 * 
 * @author vasharma
 *
 */
public class DummyHealthMetric extends SingleValueMetricFunction<String> {

	String healthValue;

	public static final String PARAM1 = "PARAM1";

	@Override
	public String compute(MetricNode metricNode, SingleValueMetric<String> metric, RtaNodeContext context) throws Exception {
		MetricKey metricKey = (MetricKey) metricNode.getKey();

		if(metricKey.getDimensionLevelName().equals(MetricAttribute.DIM_APP)) {
			if(MetricAttribute.DUMMY_ATTR.equals(metricKey.getDimensionValue(MetricAttribute.DIM_APP))){

				MutableMetricNode node=MonitoringUtils.getMetricNode(metricKey);
				node.setDeleted(true);
			}
			return "na";
		} else if(metricKey.getDimensionLevelName().equals(MetricAttribute.DIM_INSTANCE)) {
			if(MetricAttribute.DUMMY_ATTR.equals(metricKey.getDimensionValue(MetricAttribute.DIM_INSTANCE))){
				MutableMetricNode node=MonitoringUtils.getMetricNode(metricKey);
				node.setDeleted(true);
			}
			return "na";
		}
		else if(metricKey.getDimensionLevelName().equals(MetricAttribute.DIM_AGENT)) {
			if(MetricAttribute.DUMMY_ATTR.equals(metricKey.getDimensionValue(MetricAttribute.DIM_AGENT))){
				MutableMetricNode node=MonitoringUtils.getMetricNode(metricKey);
				node.setDeleted(true);
			}
			return "na";
		}
		else
			return "na";
	}

	@Override
	public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
		super.init(fn, measurement, startNode, dh);

		String attrName1= measurement.getFunctionParamBinding(PARAM1);
		healthValue = fn.getAttribute(attrName1).toString();

	}
}
