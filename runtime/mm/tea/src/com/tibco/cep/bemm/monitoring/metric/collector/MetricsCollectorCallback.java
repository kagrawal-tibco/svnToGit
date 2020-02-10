package com.tibco.cep.bemm.monitoring.metric.collector;

import java.util.Map;

public interface MetricsCollectorCallback {

	/**
	 * @param metrics
	 */
	void publish(Map<String, Object> metrics);
}
