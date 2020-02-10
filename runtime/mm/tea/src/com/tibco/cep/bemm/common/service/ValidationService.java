package com.tibco.cep.bemm.common.service;

import com.tibco.cep.bemm.management.exception.BEValidationException;
import com.tibco.cep.bemm.model.ServiceInstance;

/**
 * Defines the methods to validate the data.
 * 
 * @author dijadhav
 *
 */
public interface ValidationService extends StartStopService {

	/**
	 * This method is used to check whether the ip address is valid or not.
	 * 
	 * @param ipAddress
	 *            -IP Address of the machine
	 * @return Whether the file of given path exist on remote machine.
	 */
	boolean isIPAddressValid(String ipAddress);

	/**
	 * String is not null or empty
	 * 
	 * @param str
	 *            - String object
	 * @return True/False
	 */
	boolean isNotNullAndEmpty(String str);

	/**
	 * Validate service instance
	 * 
	 * @param serviceInstance
	 *            - Instance to be validated
	 */
	void vaidateServiceInstance(ServiceInstance serviceInstance) throws BEValidationException;
}
