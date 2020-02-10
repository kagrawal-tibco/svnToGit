package com.tibco.rta;

import com.tibco.rta.impl.MetricKeyImpl;

/**
 * 
 * A factory class for generating a Metric's MetricKey
 *
 */

public class KeyFactory {
	
	public static MetricKey newMetricKey (String schemaName, String cubeName, String hierarchyName, String dimensionLevelName) {
		return new MetricKeyImpl (schemaName, cubeName, hierarchyName, dimensionLevelName);
	}
}
