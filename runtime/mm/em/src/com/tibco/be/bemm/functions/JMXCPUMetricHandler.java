package com.tibco.be.bemm.functions;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.sun.management.OperatingSystemMXBean;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.event.SimpleEvent;

class JMXCPUMetricHandler extends JMXMetricTypeHandler {
	
	private OperatingSystemMXBean operatingSystemMXBean;
	
	private RuntimeMXBean runtimeMXBean;
	
	private long prevCPUTime = 0L;
	private long prevUpTime = 0L;
	private int noOfCPUs = 0;

	JMXCPUMetricHandler() {
		super();
	}

	@Override
	protected void init() throws IOException {
		boolean cpuProcessTimeSupported;
		try {
			cpuProcessTimeSupported = serverConnection.isInstanceOf(new ObjectName(ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME), "com.sun.management.OperatingSystemMXBean");
		} catch (InstanceNotFoundException e) {
			logger.log(Level.WARN, "could not create an instance of "+ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, e);
			cpuProcessTimeSupported = false;
		} catch (MalformedObjectNameException e) {
			logger.log(Level.WARN, "could not create an instance of "+ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, e);
			cpuProcessTimeSupported = false;
		} 
		if (cpuProcessTimeSupported == true){
			operatingSystemMXBean = ManagementFactory.newPlatformMXBeanProxy(serverConnection, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
			runtimeMXBean = ManagementFactory.newPlatformMXBeanProxy(serverConnection, ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
	
			noOfCPUs = operatingSystemMXBean.getAvailableProcessors();
			prevUpTime = runtimeMXBean.getUptime();
			prevCPUTime = operatingSystemMXBean.getProcessCpuTime();
		}
	}

	@Override
	protected SimpleEvent[] populate(EventCreator eventCreator) throws IOException {
		if (operatingSystemMXBean == null || runtimeMXBean == null){
			return null;
		}
		
		SimpleEvent event = null;
		
		long currUpTime = runtimeMXBean.getUptime();
		long currCPUTime = operatingSystemMXBean.getProcessCpuTime();

		if (prevUpTime > 0L && currUpTime > prevUpTime) {
			// elapsedCpu is in nanos and elapsedTime is in millis.
			long elapsedCpu = currCPUTime - prevCPUTime;
			long elapsedTime = currUpTime - prevUpTime;
			// cpuUsage could go higher than 100% because elapsedTime
			// and elapsedCpu are not fetched simultaneously. Limit to
			// 99% to avoid Plotter showing a scale from 0% to 200%.
			float cpuUsageInPercent =  Math.min(99F,elapsedCpu / (elapsedTime * 10000F * noOfCPUs));
			
			event = eventCreator.create();
			if (event != null){
				String name = null;
				try {
					name = "cpucnt";
					event.setProperty(name, noOfCPUs);
					name = "cputime";
					event.setProperty(name, currCPUTime);
					name = "uptime";
					event.setProperty(name, currUpTime);
					name = "usage";
					event.setProperty(name, limit(cpuUsageInPercent,2));
				} catch (NoSuchFieldException e) {
					logger.log(Level.WARN, "could not find property named "+name+" in "+event.getExpandedName());
					event = null;
				} catch (Exception e) {
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "could not set value for property named " + name + " in " + event.getExpandedName(), e);
					}
					else {
						logger.log(Level.WARN, "could not set value for property named " + name + " in " + event.getExpandedName());
					}
					event = null;
				}
			}
		}
	    this.prevUpTime = currUpTime;
	    this.prevCPUTime = currCPUTime;
	    if (event == null){
	    	return null;
	    }
		return new SimpleEvent[]{event};
	}

}
