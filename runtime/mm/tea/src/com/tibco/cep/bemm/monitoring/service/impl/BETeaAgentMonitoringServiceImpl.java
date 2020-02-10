package com.tibco.cep.bemm.monitoring.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.service.BETeaAgentMonitoringService;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorable;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorableImpl;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.tea.agent.internal.MachineUtil;

/*
 * @author : vasharma
 */

public class BETeaAgentMonitoringServiceImpl extends AbstractStartStopServiceImpl implements BETeaAgentMonitoringService{

	int jmxPort=-1;
	boolean sslEnabled=false;
	boolean authEnabled=false;
	private Map<Monitorable, Object> monitorableEntitiesRegistry = null;
	private BeTeaAgentMonitorable agent=new BeTeaAgentMonitorableImpl();

	@Override
	public void init(Properties properties) throws Exception {
		
		this.monitorableEntitiesRegistry=new ConcurrentHashMap<>();
		
		String port=(String) ConfigProperty.BE_TEA_AGENT_JMX_PORT.getValue(properties);
		jmxPort=Integer.parseInt(port);
		
		String hostIp =MachineUtil.getHostAddress(); 
		agent.setName("BusinessEvents");
		agent.setJmxPort(jmxPort);
		agent.setStatus("running");
		agent.setHostIp(hostIp);
		agent.setStartingTime(System.currentTimeMillis());
		
		startMetricsCollection(agent);
	}
	
	@Override
	public void startMetricsCollection(BeTeaAgentMonitorable agent) throws Exception {
		
		register(agent,agent.getJmxPort());
	}

	@Override
	public void stopMetricsCollection(BeTeaAgentMonitorable agent) throws Exception {
		unregister(agent);
		//Removing the connection associated to the URI post instance stop
		BEMMServiceProviderManager.getInstance().getJmxConnectionPool().closeConnection(MonitoringUtils.getJmxServiceUrl(agent.getHostIp(), agent.getJmxPort()));
	}	
	
	public final synchronized void register(Monitorable monitorableEntity, Object value) throws IOException {

		if (!monitorableEntitiesRegistry.containsKey(monitorableEntity)) {
			monitorableEntitiesRegistry.put(monitorableEntity, value);
		}
	}
	
	public final synchronized void unregister(Monitorable monitorableEntity) throws IOException {
		if (monitorableEntitiesRegistry.containsKey(monitorableEntity)) {
			monitorableEntitiesRegistry.remove(monitorableEntity);
		}
	}

	@Override
	public Map<Monitorable, Object> getMonitorableRegistry() {
		return monitorableEntitiesRegistry;
	}
	
	@Override
	public BeTeaAgentMonitorable getAgent() {
		return agent;
	}

	@Override
	public void setAgent(BeTeaAgentMonitorable agent) {
		this.agent = agent;
	}

}
