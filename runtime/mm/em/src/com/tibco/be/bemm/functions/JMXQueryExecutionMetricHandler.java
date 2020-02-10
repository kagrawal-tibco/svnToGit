package com.tibco.be.bemm.functions;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.event.SimpleEvent;

public class JMXQueryExecutionMetricHandler extends JMXMetricTypeHandler {
	
	private static String QUERY_CONTINUOUS_SERVICE_MXBEAN_NAME_PATTERN = "com.tibco.be:type=Query,partition={0},service=Queries - Continuous";
	
	private static String QUERY_SNAPSHOT_SERVICE_MXBEAN_NAME_PATTERN = "com.tibco.be:type=Query,partition={0},service=Queries - Snapshot";
	
	private static String[] SERVICE_STAT_NAMES = new String[]{"accumulatedEntityCountDuringSS","pendingEntityCount"};
	
	private ObjectName continuousQueryBaseBeanName;
	
	private ObjectName snapshotQueryBaseBeanName;
	
	protected String partitionName;
	
	JMXQueryExecutionMetricHandler() {
		super();
	}

	@Override
	void setMonitoredEntityName(String monitoredEntityName) {
		super.setMonitoredEntityName(monitoredEntityName);
		partitionName = monitoredEntityName.split("#")[0];
	}
	
	@Override
	protected void init() throws IOException {
		if (type.equals("cluster/machine/process/query/cqestats") == true){
			try {
				String searchQuery = MessageFormat.format(QUERY_CONTINUOUS_SERVICE_MXBEAN_NAME_PATTERN,partitionName);
				ObjectName name = new ObjectName(searchQuery);
				Set mbeanNames = serverConnection.queryNames(name, null);
				if (mbeanNames != null && mbeanNames.size() == 1) {
					continuousQueryBaseBeanName = (ObjectName) mbeanNames.iterator().next();
					refresh(continuousQueryBaseBeanName);
				}
			} catch (MalformedObjectNameException e) {
				throw new RuntimeException("cannot query for continuous query service mbean",e);
			}
		}
		else if (type.equals("cluster/machine/process/query/ssqestats") == true){
			try {
				String searchQuery = MessageFormat.format(QUERY_SNAPSHOT_SERVICE_MXBEAN_NAME_PATTERN,partitionName);
				ObjectName name = new ObjectName(searchQuery);
				Set mbeanNames = serverConnection.queryNames(name, null);
				if (mbeanNames != null && mbeanNames.size() == 1) {
					snapshotQueryBaseBeanName = (ObjectName) mbeanNames.iterator().next();
					refresh(snapshotQueryBaseBeanName);
				}
			} catch (MalformedObjectNameException e) {
				throw new RuntimeException("cannot query for snapshot query service mbean",e);
			}
		}
	}

	@Override
	protected SimpleEvent[] populate(EventCreator eventCreator) throws IOException {
		LinkedList<SimpleEvent> events = new LinkedList<SimpleEvent>();
		if (continuousQueryBaseBeanName != null) {
			readQueryExecutionStats(continuousQueryBaseBeanName,eventCreator,events);
			refresh(continuousQueryBaseBeanName);
		}
		if (snapshotQueryBaseBeanName != null) {
			readQueryExecutionStats(snapshotQueryBaseBeanName,eventCreator,events);
			refresh(snapshotQueryBaseBeanName);
		}
		if (events.isEmpty() == true){
			return null;
		}
		return events.toArray(new SimpleEvent[events.size()]);
	}
	
	protected void readQueryExecutionStats(ObjectName basePattern, EventCreator eventCreator, List<SimpleEvent> events) throws IOException {
		String searchPattern = basePattern+",*";
		try {
			Set mbeanNames = serverConnection.queryNames(new ObjectName(searchPattern), null);
			if (mbeanNames != null) {
				Iterator iterator = mbeanNames.iterator();
				while (iterator.hasNext()) {
					ObjectName objName = (ObjectName) iterator.next();
					if (objName.equals(basePattern) == false) {
						SimpleEvent event = eventCreator.create();
						if (event != null) {
							String propertyName = null;
							try {
								propertyName = "queryName";
								String queryName = objName.getKeyProperty("query");
								event.setProperty(propertyName, queryName.substring(partitionName.length()+1));
								for (int i = 0; i < SERVICE_STAT_NAMES.length; i++) {
									propertyName = SERVICE_STAT_NAMES[i];
									Object value = serverConnection.getAttribute(objName, propertyName);
									event.setProperty(propertyName, value);
								}
								events.add(event);
							} catch (NullPointerException e){
								logger.log(Level.WARN, "could not extract 'query' property from "+objName, e);
							} catch (NoSuchFieldException e) {
								logger.log(Level.WARN, "could not find property named " + propertyName + " in " + event.getExpandedName());
							} catch (IOException e) {
								throw e;	
							} catch (UndeclaredThrowableException e){
								throw e;								
							} catch (Exception e) {
								if (logger.isEnabledFor(Level.DEBUG)) {
									logger.log(Level.DEBUG, "could not set value for property named " + propertyName + " in " + event.getExpandedName(), e);
								}
								else {
									logger.log(Level.WARN, "could not set value for property named " + propertyName + " in " + event.getExpandedName());
								}								
							}							
						}
					}
				}
			}
		} catch (MalformedObjectNameException e){
			if (logger.isEnabledFor(Level.DEBUG)) {
				logger.log(Level.DEBUG, "could not create object name pattern ["+searchPattern+"]",e);
			}
			else {
				logger.log(Level.WARN, "could not create object name pattern ["+searchPattern+"]");
			}
		} 
	}
	
	protected void refresh(ObjectName name) {
		try {
			serverConnection.invoke(name, "refresh", null, null);
		} catch (Exception e) {
			logger.log(Level.WARN, "could not invoke 'refresh' on "+name, e);
		}
	}

}