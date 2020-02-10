package com.tibco.rta.runtime.metric;

import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

/**
 * Providers of metric computations should implement this interface. The metric computation engine  
 * will call into this interface's compute method in order to acquire its metric value.
 *
 */
public abstract class SingleValueMetricFunction<N> extends AbstractMetricFunction<N> {
	
	/**
	 * Implementation classes will provide the algorithm to compute the metric values via this method
	 * It will be given a {@code MetricNode} to for which the metric value needs to be computed
	 * @param metricNode The metric node for which the metric value needs to be computed
	 * @return true if the metric computation should propagate or rollup to its parent, false otherwise
	 * @throws Exception if any problem was encountered during metric computation depending on the implementation
	 */
	abstract public N compute(MetricNode metricNode, SingleValueMetric<N> metric, RtaNodeContext context) throws Exception;

}