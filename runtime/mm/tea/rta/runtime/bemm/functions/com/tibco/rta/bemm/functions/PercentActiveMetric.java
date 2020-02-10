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
 * This metric will return the percentage active instances as follows:
 * -----AGENT DIM LEVEL    : return -1 : not applicable at this level
 * -----INSTANCE DIM LEVEL : return -1 : not applicable at this level 
 * -----APP DIM LEVEL      : will get total instance count and total active instance count and evaluate the percentage
 * 
 * @author mjinia,vasharma
 *
 */
public class PercentActiveMetric extends SingleValueMetricFunction<Long> {

	Long total;

	public static final String PARAM1 = "PARAM1";

	@Override
	public Long compute(MetricNode metricNode, SingleValueMetric<Long> metric, RtaNodeContext context) {

		MetricKey metricKey = (MetricKey) metricNode.getKey();
		if(metricKey.getDimensionLevelName().equals(MetricAttribute.DIM_APP)) {
			
			SingleValueMetric<Object> instanceCountMetric = (SingleValueMetric<Object>) metricNode.getMetric(MetricAttribute.PU_INSTANCE_COUNT);
			if(instanceCountMetric==null)
				return 0l;
    		Long totalInstances=getAttributeValue(instanceCountMetric.getValue());
    		
    		SingleValueMetric<Object> activeInstancesCountMetric = (SingleValueMetric<Object>) metricNode.getMetric(MetricAttribute.PU_INSTANCE_ISACTIVE);
    		if(activeInstancesCountMetric==null)
    			return 0l;
    		Long totalActiveInstances=getAttributeValue(activeInstancesCountMetric.getValue());
			if(totalInstances<=0)
				return 0l;
			return (totalActiveInstances*100l)/totalInstances;
		}
		else
		{
			return -1l;
		}
	}
	@Override
	public void init(Fact fn, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
		super.init(fn, measurement, startNode, dh);

		String attrName = measurement.getFunctionParamBinding(PARAM1);

		total = getAttributeValue(fn.getAttribute(attrName));

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
