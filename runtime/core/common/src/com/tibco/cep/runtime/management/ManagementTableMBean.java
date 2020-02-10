package com.tibco.cep.runtime.management;

import java.util.Map;

/**
 * @author vdhumal
 * MBean interface for the ManagementTable instance
 */
public interface ManagementTableMBean {

	/**
	 * @return Info of the running instance 
	 */
	Map<String, Object> getInstanceInfo();
	
	/**
	 * @return Agents info
	 */
	Map<String, String> getInstanceAgentsInfo();
	
	/**
	 * Stop the Engine
	 */
	void stopInstance();

	Map<String, Object> getInstanceDetails();

	Map<String, String> getInstanceAgentsDetails();

	Map<String, Object> getGlobalVariables();

	Map<String, Object> getSystemProperties();

	Map<String, Object> getBEProperties();

	Map<String, Object> getJVMProperties();
}
