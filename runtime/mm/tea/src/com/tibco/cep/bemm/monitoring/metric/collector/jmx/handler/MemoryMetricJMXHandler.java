package com.tibco.cep.bemm.monitoring.metric.collector.jmx.handler;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.common.jmx.JMXConnectionContext;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.metric.MetricType;
import com.tibco.cep.bemm.monitoring.metric.collector.MetricsContext;

public class MemoryMetricJMXHandler extends MetricTypeJMXHandler {

	private static final int ONE_MB = 1048576;
		
	public MemoryMetricJMXHandler() {
		super(MetricType.MEMORY_STATS.value());
	}	

	@Override
	public MetricsContext initMetricsContext(JMXConnectionContext connContext) throws IOException {
		return null;
	}

	@Override
	public Collection<Map<String, Object>> getMetrics(JMXConnectionContext connContext, MetricsContext metricsContext) throws IOException {
		MemoryMXBean memoryMXBean = connContext.getMXBeanProxy(ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
		
		MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
		List <Map<String, Object>> metricsList = new ArrayList<>();
		Map<String, Object> metricMap = new HashMap<>();
		metricMap.put(MetricAttribute.MEMORY_INITIAL_SIZE, new Double(heapMemoryUsage.getInit()/ONE_MB));
		metricMap.put(MetricAttribute.MEMORY_USED_SIZE, new Double(heapMemoryUsage.getUsed()/ONE_MB));
		metricMap.put(MetricAttribute.MEMORY_COMMITTED_SIZE, new Double(heapMemoryUsage.getCommitted()/ONE_MB));
		metricMap.put(MetricAttribute.MEMORY_MAX_SIZE, new Double(heapMemoryUsage.getMax()/ONE_MB));
		metricsList.add(metricMap);
		return metricsList;
	}

}
