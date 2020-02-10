package com.tibco.cep.bemm.monitoring.metric.collector.jmx.handler;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.common.jmx.JMXConnectionContext;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.metric.MetricType;
import com.tibco.cep.bemm.monitoring.metric.collector.MetricsContext;

public class ThreadMetricJMXHandler extends MetricTypeJMXHandler {

	public ThreadMetricJMXHandler() {
		super(MetricType.THREAD_STATS.value());
	}

	@Override
	public MetricsContext initMetricsContext(JMXConnectionContext connContext) throws IOException {
		return null;
	}

	@Override
	public Collection<Map<String, Object>> getMetrics(JMXConnectionContext connContext, MetricsContext metricsContext) throws IOException {
		ThreadMXBean threadMXBean = connContext.getMXBeanProxy(ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
		
		List <Map<String, Object>> metricsList = new ArrayList<>();
		Map<String, Object> metricMap = new HashMap<>();
		metricMap.put(MetricAttribute.THREAD_COUNT, threadMXBean.getThreadCount());
		long[] deadlockedThreads = threadMXBean.findMonitorDeadlockedThreads();
		if (deadlockedThreads == null) {
			metricMap.put(MetricAttribute.THREAD_DEADLOCKED_COUNT, 0);
		} else {
			metricMap.put(MetricAttribute.THREAD_DEADLOCKED_COUNT, deadlockedThreads.length);
		}
		metricsList.add(metricMap);
		return metricsList;
	}

}