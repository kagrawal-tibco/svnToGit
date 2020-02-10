package com.tibco.be.bemm.functions;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.event.SimpleEvent;

class JMXMemoryMetricHandler extends JMXMetricTypeHandler {

	private static final int ONE_MB = 1048576;
	
	private MemoryMXBean memoryMXBean;
	
	JMXMemoryMetricHandler() {
		super();
	}	

	protected void init() throws IOException {
        if (serverConnection!=null)
		    memoryMXBean = ManagementFactory.newPlatformMXBeanProxy(serverConnection, ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
	}

	protected SimpleEvent[] populate(EventCreator eventCreator) throws IOException {
		SimpleEvent event = eventCreator.create();
		if (event == null){
			return null;
		}
		MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
		String name = null;
		try {
			name = "initSize";
			event.setProperty(name, new Double(heapMemoryUsage.getInit()/ONE_MB));
			name = "usedSize";
			event.setProperty(name, new Double(heapMemoryUsage.getUsed()/ONE_MB));
			name = "committedSize";
			event.setProperty(name, new Double(heapMemoryUsage.getCommitted()/ONE_MB));
			name = "maxSize";
			event.setProperty(name, new Double(heapMemoryUsage.getMax()/ONE_MB));
			return new SimpleEvent[]{event};
		} catch (NoSuchFieldException e) {
			logger.log(Level.WARN, "could not find property named "+name+" in "+event.getExpandedName());
			return null;
		} catch (Exception e) {
			if (logger.isEnabledFor(Level.DEBUG)) {
				logger.log(Level.WARN, "could not set value for property named " + name + " in " + event.getExpandedName(), e);
			}
			else {
				logger.log(Level.WARN, "could not set value for property named " + name + " in " + event.getExpandedName());
			}			
			return null;
		}
	}

}
