package com.tibco.rta.runtime.metric;

import com.tibco.rta.Fact;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.model.MetricNode;

/**
 * 
 * An abstract metric function. Metric function implementations will indirectly extend this 
 * as a result of extending either SingleValue of MultiValueMetricFunction.
 *
 * @param <N> The data type of the metric computation.
 */
public abstract class AbstractMetricFunction<N> implements MetricFunction<N> {
	
	protected Measurement measurement;
	
	protected MetricNode startNode;
	
	protected DimensionHierarchy dh;

    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_FUNCTIONS.getCategory());
	
	public AbstractMetricFunction() {
	}
	
	@Override
	public void init(Fact fact, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception {
		this.measurement = measurement;
		this.startNode = startNode;
		this.dh = dh;
	}
	
}