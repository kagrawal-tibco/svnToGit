package com.tibco.cep.bemm.monitoring.service;

import java.util.Map;

import javax.management.MBeanServerConnection;

import com.tibco.cep.bemm.monitoring.exception.RemoteMetricsCollectorServiceException;

/**
 * This interface is used to provide the functionality to collect the metrics
 * data from remote machine
 * 
 * @author dijadhav
 *
 */
public interface RemoteMetricsCollectorService {
	/**
	 * Initialize the service
	 * 
	 * @throws RemoteMetricsCollectorServiceException
	 */
	void init() throws RemoteMetricsCollectorServiceException;

	/**
	 * Populate the data
	 * 
	 * @return Key-Value Pair
	 */
	Map<String, String> populate() throws RemoteMetricsCollectorServiceException;

	/**
	 * This method set the server connection
	 */
	void setMBeanServerConnection(MBeanServerConnection serverConnection);
}
