package com.tibco.cep.bemm.monitoring.metric.collector;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import com.tibco.cep.bemm.common.ConnectionContext;
import com.tibco.cep.bemm.model.Monitorable;

public class MonitorableEntityWrapper<C extends ConnectionContext<?>> {

	private Monitorable monitorableEntity = null;
	private C connectionContext = null;
	private Map<String, MetricsContext> metricsContextRegistry = null;
	protected Set<String> metricsRegistry = null;

	public MonitorableEntityWrapper(Monitorable monitorableEntity) {
		this.monitorableEntity = monitorableEntity;
		this.metricsContextRegistry = new ConcurrentHashMap<>();
		this.metricsRegistry = new CopyOnWriteArraySet<>(); 
	}
	
	public Monitorable getMonitorableEntity() {
		return monitorableEntity;
	}

	public C getConnectionContext() {
		return connectionContext;
	}

	public void setConnectionContext(C connectionContext) {
		this.connectionContext = connectionContext;
	}

	public void setMetricsContext(String metricType, MetricsContext metricsContext) {
		if (metricType != null && metricsContext != null)
			this.metricsContextRegistry.put(metricType, metricsContext);
	}

	public MetricsContext getMetricsContext(String metricType) {
		return this.metricsContextRegistry.get(metricType);
	}
	
	public void addMetricType(String metricType) {
		this.metricsRegistry.add(metricType);
	}

	public boolean removeMetricType(String metricType) {
		return this.metricsRegistry.remove(metricType);
	}

	public void removeAllMetricTypes() {
		this.metricsRegistry.clear();
	}

	public Set<String> getMetricTypes() {
		return Collections.unmodifiableSet(metricsRegistry);
	}
		
}
