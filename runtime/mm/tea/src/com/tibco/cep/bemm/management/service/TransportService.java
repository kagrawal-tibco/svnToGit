package com.tibco.cep.bemm.management.service;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.model.ServiceInstance;

/**
 * Define the operations used by different transport.
 * 
 * @author dijadhav
 *
 */
public interface TransportService {
	/**
	 * Deploy the application.
	 * 
	 * @param serviceInstance
	 *            - Service instance which needs to be deploy
	 * @param loggedInUser
	 *            - Logged in user name
	 * @return Returns success message
	 */
	String deploy(ServiceInstance serviceInstance, String loggedInUser);

	/**
	 * UnDeploy the application.
	 * 
	 * @param serviceInstance
	 *            - Service instance which needs to be undeploy
	 * @param loggedInUser
	 *            - Logged in user name
	 * @return Returns success message
	 */
	String undeploy(ServiceInstance serviceInstance, String loggedInUser);

	/**
	 * Start the service instance
	 * 
	 * @param serviceInstance
	 *            - Service instance which needs to be started
	 * @param loggedInUser
	 *            - Logged in user name
	 * @return Returns success message
	 */
	String start(ServiceInstance serviceInstance, String loggedInUser);
	
	/**
	 * Stop the service instance
	 * 
	 * @param serviceInstance
	 *            - Service instance which needs to be stopped
	 * @param loggedInUser
	 *            - Logged in user name
	 * @return Returns success message
	 * @throws ObjectCreationException 
	 */
	String stop(ServiceInstance instance, String loggedInUser) throws ObjectCreationException;
	
	
}
