/**
 * 
 */
package com.tibco.be.bemm.functions;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.UndeclaredThrowableException;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.event.SimpleEvent;

/**
 * @author anpatil
 * 
 */
class JMXThreadMetricHandler extends JMXMetricTypeHandler {

	private ThreadMXBean threadMXBean;

	JMXThreadMetricHandler() {
		super();
	}

	protected void init() throws IOException {
		threadMXBean = ManagementFactory.newPlatformMXBeanProxy(serverConnection, ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
	}

	protected SimpleEvent[] populate(EventCreator eventCreator) throws IOException {
		SimpleEvent event = eventCreator.create();
		if (event == null) {
			return null;
		}
		String name = null;
		try {
			name = "threadCount";
			event.setProperty(name, threadMXBean.getThreadCount());
			long[] deadlockedThreads = threadMXBean.findMonitorDeadlockedThreads();
			name = "deadlockedThreadCount";
			if (deadlockedThreads == null) {
				event.setProperty(name, 0);
			} else {
				event.setProperty(name, deadlockedThreads.length);
			}
			return new SimpleEvent[] { event };
		} catch (NoSuchFieldException e) {
			logger.log(Level.WARN, "could not find property named " + name + " in " + event.getExpandedName());
			return null;
		} catch (IOException e) {
			throw e;
		} catch (UndeclaredThrowableException e){
			throw e;			
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