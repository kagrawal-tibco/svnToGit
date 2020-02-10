package com.tibco.cep.bemm.monitoring.metric.collector.jmx.handler;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.ObjectName;

import com.tibco.cep.bemm.common.jmx.JMXConnectionContext;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.metric.MetricType;
import com.tibco.cep.bemm.monitoring.metric.collector.MetricsContext;

public class GarbageCollectorMetricJMXHandler extends MetricTypeJMXHandler {
	
	private static Object[][] DURATIONS = new Object[][]{
		new Object[]{365*24*60*60*1000L,"y"}, //a year
		new Object[]{30*24*60*60*1000L,"m"}, //a month
		new Object[]{24*60*60*1000L,"d"}, //a day
		new Object[]{60*60*1000L,"h"}, //a hour
		new Object[]{60*1000L,"m"}, //a minute
		new Object[]{1000L,"s"}, //a second
		new Object[]{1L,"ms"}, //a millisecond
	};
		
	public GarbageCollectorMetricJMXHandler() {
		super(MetricType.GC_STATS.value());
	}

	@Override
	public MetricsContext initMetricsContext(JMXConnectionContext connContext) throws IOException {
		return null;
	}

	@Override
	public Collection<Map<String, Object>> getMetrics(JMXConnectionContext connContext, MetricsContext metricsContext) throws IOException {
		RuntimeMXBean runtimeMXBean = connContext.getMXBeanProxy(ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
		Set<ObjectName> gcMbeanNames = connContext.getGCMBeanNames();	
		if (runtimeMXBean == null || gcMbeanNames == null || gcMbeanNames.isEmpty() == true) {
			return null;
		}
		List <Map<String, Object>> metricsList = new ArrayList<>();
		for (ObjectName gcMbeanName : gcMbeanNames) {
			GarbageCollectorMXBean gcMBean = connContext.getMXBeanProxy(gcMbeanName.getCanonicalName(), GarbageCollectorMXBean.class);
			Map<String, Object> metricMap = new HashMap<>();
			metricMap.put(MetricAttribute.GC_POOL_NAME, gcMBean.getName());
			metricMap.put(MetricAttribute.GC_POOL_COLLECTION_COUNT, gcMBean.getCollectionCount());
			long collectionTime = gcMBean.getCollectionTime();
			metricMap.put(MetricAttribute.GC_POOL_COLLECTION_TIME, collectionTime);
			metricMap.put(MetricAttribute.GC_FORMATTED_POOL_COLLECTION_TIME, formatTime(collectionTime));
			long uptime = runtimeMXBean.getUptime();
			metricMap.put(MetricAttribute.JVM_UPTIME, uptime);
			metricMap.put(MetricAttribute.JVM_FORMATTED_UPTIME, formatTime(uptime));			
			metricsList.add(metricMap);
		}
		return metricsList;
	}

	private String formatTime(long time) {
		if (time == 0){
			return "0ms";
		}
		StringBuilder sb = new StringBuilder();
		int limit = 2;
		for (int i = 0; i < DURATIONS.length; i++) {
			long durationMSecs = (Long) DURATIONS[i][0];
			int t = (int) (time/durationMSecs);
			if (t > 0){
				time = time % durationMSecs;
				if (sb.length() > 0){
					sb.append(",");
				}
				sb.append(t+""+DURATIONS[i][1]);
				limit--;
				if (limit == 0){
					break;
				}
			}
		}	
		return sb.toString();
	}		
}