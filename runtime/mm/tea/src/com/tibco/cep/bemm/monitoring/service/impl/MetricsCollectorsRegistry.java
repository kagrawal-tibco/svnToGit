package com.tibco.cep.bemm.monitoring.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.monitoring.metric.collector.MetricsCollector;

public class MetricsCollectorsRegistry {

	private static MetricsCollectorsRegistry metricsCollectorRegistry = null;
	
	private Map<String, Class<?>> metricsCollectorConfig = null;
	private Map<Application, Map<String, MetricsCollector<?, ?>>> metricsCollectors = null;
	private Properties properties = null;
	
	private MetricsCollectorsRegistry() {
		this.metricsCollectors = new HashMap<>(); 
		this.metricsCollectorConfig = new HashMap<>();
	}
	
	synchronized static MetricsCollectorsRegistry getInstance(String configFile) throws Exception {
		if (metricsCollectorRegistry == null) {
			metricsCollectorRegistry = new MetricsCollectorsRegistry();
			metricsCollectorRegistry.loadMetricCollectorsConfig(configFile);
		}
		return metricsCollectorRegistry;
	}
	
	synchronized static MetricsCollectorsRegistry getInstance() {
		return metricsCollectorRegistry;
	}
	
	protected void init(Properties properties) throws Exception {
		this.properties = properties;
	}
	
	synchronized MetricsCollector<?, ?> getMetricsCollector(Application application, String collectorType) throws Exception {
		Map<String, MetricsCollector<?, ?>> applicationMetricsCollectors = metricsCollectors.get(application);
		if (applicationMetricsCollectors == null) {
			applicationMetricsCollectors = new HashMap<>();
			metricsCollectors.put(application, applicationMetricsCollectors);
		}
		MetricsCollector<?, ?> metricCollector = applicationMetricsCollectors.get(collectorType);
		if (metricCollector == null) {
			metricCollector = createMetricsCollectorInstance(collectorType);
			metricCollector.init(this.properties);
			applicationMetricsCollectors.put(collectorType, metricCollector);
		}
		return metricCollector;
	}
	
	private void loadMetricCollectorsConfig(String configFile) throws Exception {
		try {
			Class<?> clazz = Class.forName("com.tibco.cep.bemm.monitoring.metric.collector.jmx.JMXMetricsCollector");
			this.metricsCollectorConfig.put("JMX", clazz);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		}
	}
	
	private MetricsCollector<?, ?> createMetricsCollectorInstance(String collectorType) throws Exception {
		MetricsCollector<?, ?> metricsCollector = null;
		Class<?> clazz = metricsCollectorConfig.get(collectorType);
		try {
			if (clazz != null) {
				metricsCollector = (MetricsCollector<?, ?>) clazz.newInstance();
			} else {
				//throw no metric collector defined
			}
		} catch (IllegalAccessException | InstantiationException ex) {
			throw new Exception(ex);
		}
		return metricsCollector;
	}
		
}
