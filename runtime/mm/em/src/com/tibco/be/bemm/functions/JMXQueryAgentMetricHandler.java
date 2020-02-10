package com.tibco.be.bemm.functions;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.text.MessageFormat;
import java.util.Set;

import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.query.stream.impl.monitor.model.AgentInfoMBean;
import com.tibco.cep.query.stream.impl.monitor.model.AgentTraceInfoMBean;
import com.tibco.cep.runtime.model.event.SimpleEvent;

public class JMXQueryAgentMetricHandler extends JMXMetricTypeHandler {
	
	private static String QUERY_AGENT_INFO_MXBEAN_NAME_PATTERN = "com.tibco.be:type=Query,partition={0},service=AgentInfo";
	
	private static String QUERY_AGENT_TRACE_INFO_MXBEAN_NAME_PATTERN = "com.tibco.be:type=Query,partition={0},service=AgentTraceInfo";
	
	private AgentInfoMBean agentInfoBean;
	
	private AgentTraceInfoMBean agentTraceInfoBean;
	
	protected String partitionName;

	JMXQueryAgentMetricHandler() {
		super();
	}
	
	@Override
	void setMonitoredEntityName(String monitoredEntityName) {
		super.setMonitoredEntityName(monitoredEntityName);
		partitionName = monitoredEntityName.split("#")[0];
	}



	@Override
	protected void init() throws IOException {
		String searchPattern = MessageFormat.format(QUERY_AGENT_INFO_MXBEAN_NAME_PATTERN,partitionName);
		try {
			ObjectName name = new ObjectName(searchPattern);
			Set mbeanNames = serverConnection.queryNames(name, null);
			if (mbeanNames != null && mbeanNames.size() == 1) {
				ObjectName objName = (ObjectName) mbeanNames.iterator().next();
				agentInfoBean = (AgentInfoMBean) MBeanServerInvocationHandler.newProxyInstance(serverConnection, objName, AgentInfoMBean.class, false);			
			}
		} catch (MalformedObjectNameException e) {
			logger.log(Level.WARN, "could not query ["+searchPattern+"]",e);
		} 
		searchPattern = MessageFormat.format(QUERY_AGENT_TRACE_INFO_MXBEAN_NAME_PATTERN,partitionName);
		try {
			ObjectName name = new ObjectName(searchPattern);
			Set mbeanNames = serverConnection.queryNames(name, null);
			if (mbeanNames != null && mbeanNames.size() == 1) {
				ObjectName objName = (ObjectName) mbeanNames.iterator().next();
				agentTraceInfoBean = (AgentTraceInfoMBean) MBeanServerInvocationHandler.newProxyInstance(serverConnection, objName, AgentTraceInfoMBean.class, false);			
			}
		} catch (MalformedObjectNameException e) {
			logger.log(Level.WARN, "could not query ["+searchPattern+"]",e);
		}
	}

	@Override
	protected SimpleEvent[] populate(EventCreator eventCreator) throws IOException {
		if (agentInfoBean != null && agentTraceInfoBean != null){
			SimpleEvent event = eventCreator.create();
			if (event != null){
				String propertyName = null;
				try {
					propertyName = "localCacheCurrentElements";
					event.setProperty(propertyName, agentInfoBean.getLocalCacheCurrentElements());
					propertyName = "newEntities";
					event.setProperty(propertyName, agentTraceInfoBean.getNewEntities());
					propertyName = "tps";
					event.setProperty(propertyName, agentTraceInfoBean.getTps());
					return new SimpleEvent[]{event};
				} catch (NoSuchFieldException e) {
					logger.log(Level.WARN, "could not find property named "+propertyName+" in "+event.getExpandedName());
					return null;
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
					return null;
				}
			}
		}
		return null;
	}

}