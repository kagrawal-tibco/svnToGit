package com.tibco.cep.bemm.monitoring.service;

import java.util.Map;
import java.util.Properties;

import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.rta.common.service.StartStopService;

/**
 * @author vdhumal
 *
 */
public interface BEEntityMonitoringService  extends StartStopService {

	/**
	 * @param properties
	 * @throws Exception
	 */
	void init(Properties properties) throws Exception;
			
	/**
	 * @param serviceInstance
	 * @throws Exception
	 */
	void startMetricsCollection(ServiceInstance serviceInstance) throws Exception;

	/**
	 * @param serviceInstance
	 * @param newStatus 
	 * @param oldStatus 
	 * @throws Exception
	 */
	void stopMetricsCollection(ServiceInstance serviceInstance, String oldStatus, String newStatus) throws Exception;
		
	/**
	 * Publish initial facts for each application in the BeTea scope with entity info
	 * @throws Exception
	 */
	public void sendApplicationsBootstrapFacts() throws Exception;
	
	/**
	 * Publish facts for each agent at Service instance undeploy
	 * @throws Exception
	 */
	void performUndeployOperation(ServiceInstance serviceInstance, String oldStatus, String newStatus);
	
	
	/**
	 * Returns the registry map
	 * @throws Exception
	 */
	Map<Monitorable, Object> getMonitorableRegistry();
	
	/**
	 * Performs monitoring related bootstrap operations
	 * @throws Exception
	 */
	void performMonitoringBootstrapOperations() throws Exception;
		
}
