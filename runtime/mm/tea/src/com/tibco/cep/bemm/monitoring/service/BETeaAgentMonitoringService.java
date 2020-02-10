package com.tibco.cep.bemm.monitoring.service;

import java.util.Map;
import java.util.Properties;

import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorable;
import com.tibco.rta.common.service.StartStopService;

/*
 * @author : vasharma
 */

public interface BETeaAgentMonitoringService extends StartStopService {

	/**
	 * @param properties
	 * @throws Exception
	 */
	void init(Properties properties) throws Exception;

	void stopMetricsCollection(BeTeaAgentMonitorable agent) throws Exception;

	void startMetricsCollection(BeTeaAgentMonitorable agent) throws Exception;

	Map<Monitorable, Object> getMonitorableRegistry();
	
	public BeTeaAgentMonitorable getAgent();
	
	public void setAgent(BeTeaAgentMonitorable agent);
	
}
