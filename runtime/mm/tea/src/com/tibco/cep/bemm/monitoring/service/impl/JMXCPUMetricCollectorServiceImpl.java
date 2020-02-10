package com.tibco.cep.bemm.monitoring.service.impl;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.sun.management.OperatingSystemMXBean;
import com.tibco.cep.bemm.monitoring.exception.RemoteMetricsCollectorServiceException;
import com.tibco.cep.bemm.monitoring.service.RemoteMetricsCollectorService;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.tea.agent.be.BEApplication;

/**
 * This service implementation is used to get the get metrics data for CPU usage
 * 
 * @author dijadhav
 *
 */
public class JMXCPUMetricCollectorServiceImpl implements RemoteMetricsCollectorService {

	private OperatingSystemMXBean operatingSystemMXBean;

	private RuntimeMXBean runtimeMXBean;

	private long prevCPUTime = 0L;
	private long prevUpTime = 0L;
	private int noOfCPUs = 0;
	private Logger logger = LogManagerFactory.getLogManager().getLogger(BEApplication.class);
	private MBeanServerConnection serverConnection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.monitoring.RemoteMetricsCollectorService#init()
	 */
	@Override
	public void init() throws RemoteMetricsCollectorServiceException {
		if (null != serverConnection) {
			boolean cpuProcessTimeSupported;
			try {
				cpuProcessTimeSupported = serverConnection.isInstanceOf(new ObjectName(
						ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME), "com.sun.management.OperatingSystemMXBean");
				if (cpuProcessTimeSupported == true) {
					operatingSystemMXBean = ManagementFactory.newPlatformMXBeanProxy(serverConnection,
							ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
					runtimeMXBean = ManagementFactory.newPlatformMXBeanProxy(serverConnection,
							ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);

					noOfCPUs = operatingSystemMXBean.getAvailableProcessors();
					prevUpTime = runtimeMXBean.getUptime();
					prevCPUTime = operatingSystemMXBean.getProcessCpuTime();
				}
			} catch (InstanceNotFoundException e) {
				try {
					logger.log(Level.WARN, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.CAN_NOT_CREATE_INSTANCE,
						ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME), e);
				} catch (ObjectCreationException e1) {
					e1.printStackTrace();
				}
				cpuProcessTimeSupported = false;

			} catch (MalformedObjectNameException e) {
				try {
					logger.log(Level.WARN, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.CAN_NOT_CREATE_INSTANCE,
							 ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME), e);
				} catch (ObjectCreationException e1) {
					e1.printStackTrace();
				}
				cpuProcessTimeSupported = false;
			} catch (IOException e) {
				throw new RemoteMetricsCollectorServiceException(e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.monitoring.RemoteMetricsCollectorService#populate
	 * ()
	 */
	@Override
	public Map<String, String> populate() throws RemoteMetricsCollectorServiceException {

		if (operatingSystemMXBean == null || runtimeMXBean == null) {
			return null;
		}

		Map<String, String> data = null;

		long currUpTime = runtimeMXBean.getUptime();
		long currCPUTime = operatingSystemMXBean.getProcessCpuTime();

		if (prevUpTime > 0L && currUpTime > prevUpTime) {
			data = new HashMap<String, String>();
			// elapsedCpu is in nanos and elapsedTime is in millis.
			long elapsedCpu = currCPUTime - prevCPUTime;
			long elapsedTime = currUpTime - prevUpTime;
			// cpuUsage could go higher than 100% because elapsedTime
			// and elapsedCpu are not fetched simultaneously. Limit to
			// 99% to avoid Plotter showing a scale from 0% to 200%.
			float cpuUsageInPercent = Math.min(99F, elapsedCpu / (elapsedTime * 10000F * noOfCPUs));

			String name = null;
			name = "cpucnt";
			data.put(name, String.valueOf(noOfCPUs));
			name = "cputime";
			data.put(name, String.valueOf(currCPUTime));
			name = "uptime";
			data.put(name, String.valueOf(currUpTime));
			name = "usage";
			data.put(name, String.valueOf(limit(cpuUsageInPercent, 2)));
		}
		this.prevUpTime = currUpTime;
		this.prevCPUTime = currCPUTime;
		if (data == null) {
			return null;
		}
		return data;
	}

	@Override
	public void setMBeanServerConnection(MBeanServerConnection serverConnection) {
		this.serverConnection = serverConnection;
	}

	protected double limit(double d, int decimalPlaces) {
		double i = Math.pow(10, decimalPlaces);
		return Math.round(d * i) / i;
	}

}
