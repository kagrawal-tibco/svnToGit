package com.tibco.cep.bemm.management.service;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.cep.bemm.common.service.StartStopService;

/**
 * The service used to cache the applications cdd details
 * 
 * @author dijadhav
 *
 */
public interface BEApplicationCDDCacheService<T> extends StartStopService {
	/**
	 * Get the cluster cluster config details
	 * 
	 * @param applicationName
	 *            - Name of the application
	 * @param applicationDataStoreLocation
	 *            - Application store location
	 * @return ClusterConfig
	 */
	ClusterConfig fetchClusterConfigDetails(String applicationName, String applicationDataStoreLocation);

	/**
	 * Cache the application cluster config
	 * 
	 * @param applicationName
	 *            - Name of the application
	 * @param cddPath
	 *            - Path of cdd
	 */
	void cacheClusterConfigDetails(String applicationName, String cddPath);

	/**
	 * On delete of application remove the configuration
	 * 
	 * @param applicationName
	 * @throws Exception
	 */
	void removeConfiguration(String applicationName) throws Exception;

	/**
	 * get appVersion
	 * 
	 * @return
	 */
	String getApplicationVersion(String applicationName);
}
