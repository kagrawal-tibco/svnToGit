package com.tibco.be.bemm.functions;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.event.SimpleEvent;

class JMXGarbageCollectorMetricHandler extends JMXMetricTypeHandler {
	
	private static Object[][] DURATIONS = new Object[][]{
		new Object[]{365*24*60*60*1000L,"y"}, //a year
		new Object[]{30*24*60*60*1000L,"m"}, //a month
		new Object[]{24*60*60*1000L,"d"}, //a day
		new Object[]{60*60*1000L,"h"}, //a hour
		new Object[]{60*1000L,"m"}, //a minute
		new Object[]{1000L,"s"}, //a second
		new Object[]{1L,"ms"}, //a millisecond
	};
	
	private RuntimeMXBean runTimeMXBean;
	private List<GarbageCollectorMXBean> gcMBeans;
	

	JMXGarbageCollectorMetricHandler() {
		super();
	}

	protected void init() throws IOException {
		try {
			runTimeMXBean = ManagementFactory.newPlatformMXBeanProxy(serverConnection, ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
			ObjectName gcName = new ObjectName(ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",*");
			Set mbeanNames = serverConnection.queryNames(gcName, null);
			if (mbeanNames != null) {
				gcMBeans = new ArrayList<GarbageCollectorMXBean>();
				Iterator iterator = mbeanNames.iterator();
				while (iterator.hasNext()) {
					ObjectName objName = (ObjectName) iterator.next();
					GarbageCollectorMXBean proxy = ManagementFactory.newPlatformMXBeanProxy(serverConnection, objName.getCanonicalName(), GarbageCollectorMXBean.class);
					gcMBeans.add(proxy);
				}
			}
		} catch (MalformedObjectNameException e) {
			throw new RuntimeException("could not query garbage collection pool names",e);
		} 
	}

	protected SimpleEvent[] populate(EventCreator eventCreator) throws IOException {
		if (runTimeMXBean == null || gcMBeans == null || gcMBeans.isEmpty() == true) {
			return null;
		}
		SimpleEvent[] events = new SimpleEvent[gcMBeans.size()];
		int i = 0;
		for (GarbageCollectorMXBean gcMBean : gcMBeans) {
			events[i] = eventCreator.create();
			if (events[i] == null){
				return null;
			}
			String name = null;
			try {
				name = "gcPoolName";
				events[i].setProperty(name, gcMBean.getName());
				name = "gcPoolCollectionCount";
				events[i].setProperty(name, gcMBean.getCollectionCount());
				name = "gcPoolCollectionTime";
				long collectionTime = gcMBean.getCollectionTime();
				events[i].setProperty(name, formatTime(collectionTime));
				name = "rawGCPoolCollectionTime";
				events[i].setProperty(name, collectionTime);
				name = "upTime";
				long uptime = runTimeMXBean.getUptime();
				events[i].setProperty(name, formatTime(uptime));
				name = "rawUPTime";
				events[i].setProperty(name, uptime);				
				i++;
			} catch (NoSuchFieldException e) {
				logger.log(Level.WARN, "could not find property named "+name+" in "+events[i].getExpandedName());
				return null;
			} catch (IOException e) {
				throw e;
			} catch (UndeclaredThrowableException e){
				throw e;				
			} catch (Exception e) {
				if ( logger.isEnabledFor(Level.DEBUG) ) {
					logger.log(Level.DEBUG, "could not set value for property named " + name + " in " + events[i].getExpandedName(), e);
				}
				else {
					logger.log(Level.WARN, "could not set value for property named " + name + " in " + events[i].getExpandedName());
				}
				return null;
			}
		}
		return events;
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
	
	
	public static void main(String[] args) {
		JMXGarbageCollectorMetricHandler h = new JMXGarbageCollectorMetricHandler();
		//System.out.println(h.formatTime(10));
		System.out.println(h.formatTime(1));
	}

}