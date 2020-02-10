package com.tibco.cep.bemm.monitoring.metric.collector.jmx.handler;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.sun.management.OperatingSystemMXBean;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.metric.MetricType;
import com.tibco.cep.bemm.monitoring.metric.collector.MetricsContext;
import com.tibco.cep.bemm.monitoring.metric.collector.impl.MetricsContextImpl;
import com.tibco.cep.bemm.common.jmx.JMXConnectionContext;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;

@SuppressWarnings("restriction")
public class CPUMetricJMXHandler extends MetricTypeJMXHandler {
	
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(CPUMetricJMXHandler.class);
	
	private static final String IS_PROCESS_CPU_TIME_SUPPORTED = "IsProcessCPUTimeSupported";
	private static final String NO_OF_CPUS = "NoOfCPUs";
	private static final String PREVIOUS_PROCESS_CPU_TIME = "PrevProcessCPUTime";
	private static final String PREVIOUS_UP_TIME = "PrevUPTime";
	
	public CPUMetricJMXHandler() {
		super(MetricType.CPU_STATS.value());
	}

	@Override
	public MetricsContext initMetricsContext(JMXConnectionContext connContext) throws IOException {		 
		boolean isProcessCPUTimeSupported;
		try {
			isProcessCPUTimeSupported = connContext.getConnection().isInstanceOf(new ObjectName(ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME), "com.sun.management.OperatingSystemMXBean");
		} catch (InstanceNotFoundException ex) {
			LOGGER.log(Level.WARN, "could not create an instance of "+ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, ex);
			isProcessCPUTimeSupported = false;
		} catch (MalformedObjectNameException ex) {
			LOGGER.log(Level.WARN, "could not create an instance of "+ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, ex);
			isProcessCPUTimeSupported = false;
		} 

		MetricsContext metricsContext = new MetricsContextImpl();
		metricsContext.setTuple(IS_PROCESS_CPU_TIME_SUPPORTED, isProcessCPUTimeSupported);
		if (isProcessCPUTimeSupported) {
			OperatingSystemMXBean operatingSystemMXBean = connContext.getMXBeanProxy(ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
			RuntimeMXBean runtimeMXBean = connContext.getMXBeanProxy(ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);	
			metricsContext.setTuple(NO_OF_CPUS, operatingSystemMXBean.getAvailableProcessors());
			metricsContext.setTuple(PREVIOUS_PROCESS_CPU_TIME, operatingSystemMXBean.getProcessCpuTime());
			metricsContext.setTuple(PREVIOUS_UP_TIME, runtimeMXBean.getUptime());
		}
		return metricsContext;
	}

	@Override
	public Collection<Map<String, Object>> getMetrics(JMXConnectionContext connContext, MetricsContext metricsContext) throws IOException {		
		List <Map<String, Object>> metricsList = new ArrayList<>();
		boolean isProcessCPUTimeSupported = (boolean) metricsContext.getTupleValue(IS_PROCESS_CPU_TIME_SUPPORTED);
		if (isProcessCPUTimeSupported) {
			OperatingSystemMXBean operatingSystemMXBean = connContext.getMXBeanProxy(ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
			RuntimeMXBean runtimeMXBean = connContext.getMXBeanProxy(ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
		
			int noOfCPUs = (int) metricsContext.getTupleValue(NO_OF_CPUS);
			long prevCPUTime = (long) metricsContext.getTupleValue(PREVIOUS_PROCESS_CPU_TIME);
			long prevUpTime = (long) metricsContext.getTupleValue(PREVIOUS_UP_TIME);
	
			long currUpTime = runtimeMXBean.getUptime();
			long currCPUTime = operatingSystemMXBean.getProcessCpuTime();
				
			if (prevUpTime > 0L && currUpTime > prevUpTime) {
				Map<String, Object> metricMap = new HashMap<>();
				
				// elapsedCpu is in nanos and elapsedTime is in millis.
				long elapsedCpu = currCPUTime - prevCPUTime;
				long elapsedTime = currUpTime - prevUpTime;
				// cpuUsage could go higher than 100% because elapsedTime
				// and elapsedCpu are not fetched simultaneously. Limit to
				// 99% to avoid Plotter showing a scale from 0% to 200%.
				float cpuUsageInPercent =  Math.min(99F,elapsedCpu / (elapsedTime * 10000F * noOfCPUs));
				metricMap.put(MetricAttribute.CPU_COUNT, noOfCPUs);
				metricMap.put(MetricAttribute.CPU_TIME, currCPUTime);
				metricMap.put(MetricAttribute.JVM_UPTIME, currUpTime);
				metricMap.put(MetricAttribute.CPU_USAGE, limit(cpuUsageInPercent,2));
				metricsList.add(metricMap);
			}
			metricsContext.setTuple(PREVIOUS_PROCESS_CPU_TIME, currCPUTime);
			metricsContext.setTuple(PREVIOUS_UP_TIME, currUpTime);
		}	
		return metricsList;
	}

}
