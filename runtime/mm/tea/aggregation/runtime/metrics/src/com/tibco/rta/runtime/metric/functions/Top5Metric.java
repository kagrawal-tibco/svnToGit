package com.tibco.rta.runtime.metric.functions;

import java.util.ArrayList;
import java.util.List;

import com.tibco.rta.Fact;
import com.tibco.rta.MultiValueMetric;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.metric.MultiValueMetricFunction;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

/**
 * @author bgokhale
 *         <p/>
 *         <p/>
 *         An example to demonstrate a multivalued metric
 *         This metric holds the "top5" entries at each level.
 */

public class Top5Metric extends MultiValueMetricFunction<Long> {

    Long value;

    @Override
    public List<Long> compute(MetricNode metricNode, MultiValueMetric<Long> metric, RtaNodeContext context) {
//		MultiValueMetric<N> mvm = (MultiValueMetric<N>) metric;
        List<Long> l = new ArrayList<Long>((List<Long>) metric.getValues());

        if (l.size() < 5) {
            l.add(value);
            return l;
        }

        for (int i = 0; i < l.size(); i++) {
            if (value > l.get(i)) {
                l.set(i, value);
                return l;
            }
        }
        //if nothing changes at this level, do not propogate upward..
//		return false;
        return l;
    }

	@Override
	public void init(Fact fact, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
		super.init(fact, measurement, startNode, dh);
		String attrName = measurement.getFunctionParamBinding(measurement.getMetricFunctionDescriptor()
				.getFunctionParams().iterator().next().getName());
		value = getAttributeValue(fact.getAttribute(attrName));
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