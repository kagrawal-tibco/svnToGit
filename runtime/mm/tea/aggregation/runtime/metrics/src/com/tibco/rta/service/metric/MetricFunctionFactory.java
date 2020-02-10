package com.tibco.rta.service.metric;

import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.runtime.metric.MetricFunction;

/**
 * 
 * A factory class, based on the aggregation function, returns aggregation implementations accordingly.
 * 
 */

public class MetricFunctionFactory {
	
	static MetricFunctionFactory instance;
	
	public static MetricFunctionFactory getInstance() {
		if (instance == null) {
			instance = new MetricFunctionFactory();
		}
		return instance;
	}
	

	public MetricFunction newMetricFunction(MetricFunctionDescriptor metricDescriptor) throws Exception {
		String clzNm = metricDescriptor.getImplClass();
		Class clz = Class.forName(clzNm);
		MetricFunction aggregator = (MetricFunction) clz.newInstance();
		return aggregator;
	}
	
}
