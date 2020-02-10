package com.tibco.cep.bemm.common.service;

import java.util.Map;

import com.tibco.cep.bemm.model.ServiceInstance;

/**
 * This interface is used to connect the data provider service as per the mode,
 * provider used in cdd file
 * 
 * @author dijadhav
 *
 */
public interface ApplicationDataProviderService extends StartStopService{
	/**
	 * 
	 * @param clusterConfig
	 * @param application
	 */
	Map<String, Object> getTopologyData(ServiceInstance serviceInstance);
	
	/**
	 * @param serviceInstance
	 * @return
	 */
	Map<String, String> getInstanceAgentData(ServiceInstance serviceInstance);

}
