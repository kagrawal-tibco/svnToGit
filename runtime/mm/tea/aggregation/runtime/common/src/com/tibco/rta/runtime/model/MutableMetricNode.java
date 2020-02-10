package com.tibco.rta.runtime.model;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.Metric;
//import com.tibco.rta.service.persistence.PersistenceService;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.RtaNodeContext;

/**
 * A wrapper over a {@link Metric} instance. It provides convenience methods to
 * iterate over its associated facts. This might by required by the metric computation function.
 */
public interface MutableMetricNode extends MetricNode {
	
	/**
	 * Sets this nodes metric value for the given metric name.
	 * @param name Metric name as defined in the descriptor.
	 * @param metric Value of the metric.
	 */
	<N> void setMetric (String name, Metric<N> metric);
	
	/**
	 * 
	 * @param fact The fact node that contributed to this metric.
	 * @throws Exception 
	 * 
	 */
	void addFact (Fact fact) throws Exception;

	void setContext(String metricName, RtaNodeContext context);
	
	void setKey(Key key);
	
	void setParentKey (Key parentKey);
	
	void setIsNew(boolean isNew);
	
	void setDeleted(boolean isDeleted);
	
	void setCreatedTime(long createdTime);
	
	void setLastModifiedTime(long lastModifiedTime);

	void setProcessed(boolean isProcessed);

}