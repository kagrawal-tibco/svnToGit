package com.tibco.cep.bemm.common.service;

import java.util.Map;

import com.tibco.cep.bemm.common.operations.model.Method;
import com.tibco.cep.bemm.common.operations.model.Methods;

/**
 * This service is used to get the process and agent level method details.
 * 
 * @author dijadhav
 *
 */
public interface EntityMethodsDescriptorService {
	/**
	 * This method is get the process and agent level methods
	 * 
	 * @return Process and agent level methods
	 */
	Map<String, Methods> getEnityMethods();

	/**
	 * Get the method of give name from given entity type and method group
	 * 
	 * @param entityType
	 *            - Type of the entity
	 * @param methodGroup
	 *            -Method group
	 * @param methodName
	 *            - Name of the Method
	 * @return Method object
	 */
	Method getEnityMethod(String entityType, String methodGroup, String methodName);
}
