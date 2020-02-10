package com.tibco.cep.bemm.management.service;

import java.util.List;
import java.util.Properties;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.service.StartStopService;
import com.tibco.cep.bemm.management.exception.JschAuthenticationException;
import com.tibco.cep.bemm.management.exception.JschConnectionException;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.model.BE;

/**
 * This interface is used to discover the BE homes
 * 
 * @author dijadhav
 *
 */
public interface BEHomeDiscoveryService extends StartStopService{
	/**
	 * This method is used to get the BE home details
	 * 
	 * @param ipAddress
	 *            - IP address of the machine
	 * @param hostOs
	 *            - Host operating system
	 * @param userName
	 *            - SSH user name
	 * @param password
	 *            - SSH password
	 * @param sshPort
	 *            - SSH port
	 * @param hostName-
	 *            name of the host
	 * @return List of BE homes on the given machine .
	 * @throws JschAuthenticationException 
	 * @throws JschConnectionException 
	 */
	List<BE> getBEHomes(String ipAddress, String hostOs, String userName, String password, int sshPort,
			String hostName) throws JschAuthenticationException, JschConnectionException;

	/**
	 * Initialize the service
	 * 
	 * @param configuration
	 *            - Configuration details
	 * @throws ServiceInitializationException
	 * @throws ObjectCreationException
	 */
	void init(Properties configuration) throws ServiceInitializationException, ObjectCreationException;

}
