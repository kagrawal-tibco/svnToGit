package com.tibco.cep.bemm.management.service;

import java.util.Properties;

import com.tibco.cep.bemm.model.Application;

public interface BEApplicationDiscoveryService {

	/**
	 * @param properties
	 * @throws Exception 
	 */
	void init(Properties properties) throws Exception;
	
	/**
	 * @param application
	 * @return
	 */
	boolean discover(Application application);
	/**
	 * Stop the service
	 * 
	 * @throws Exception
	 */
	void stop() throws Exception;
}
