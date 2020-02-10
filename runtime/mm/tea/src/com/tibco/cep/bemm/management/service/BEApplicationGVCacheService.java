package com.tibco.cep.bemm.management.service;

import java.util.List;

import com.tibco.cep.bemm.common.service.StartStopService;
import com.tibco.cep.repo.GlobalVariableDescriptor;

/**
 * The service used to cache the applications global variables from EAR file
 * 
 * @author dijadhav
 *
 */
public interface BEApplicationGVCacheService<T> extends StartStopService {

	/**
	 * Cache the application global variables
	 * 
	 * @param applicationName
	 *            - Name of the application
	 * @param earPath
	 *            - Path of EAR
	 * @throws Exception
	 */
	void cacheGlobalDescriptorDetails(String applicationName, String earPath) throws Exception;

	/**
	 * Get service settable global variables
	 * 
	 * @param applicationName
	 *            - Name of the application
	 * @param applicationDataStoreLocation
	 *            - Path of repo
	 * @return Collection of GlobalDescriptor
	 * @throws Exception
	 */
	List<GlobalVariableDescriptor> getServiceSettableGlobalVariables(String applicationName,
			String applicationDataStoreLocation) throws Exception;

	/**
	 * On delete of application remove the configuration
	 * 
	 * @param applicationName
	 * @throws Exception
	 */
	void removeConfiguration(String applicationName) throws Exception;

}
