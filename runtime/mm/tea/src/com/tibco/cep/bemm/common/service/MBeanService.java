package com.tibco.cep.bemm.common.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.management.MBeanServerConnection;

import com.tibco.cep.bemm.common.exception.MBeanOperationFailException;
import com.tibco.cep.bemm.common.model.CPUUsage;
import com.tibco.cep.bemm.common.model.MemoryUsage;
import com.tibco.cep.bemm.common.model.ThreadDetail;
import com.tibco.cep.bemm.common.model.impl.LogDetailImpl;
import com.tibco.cep.bemm.common.model.impl.ProcessMemoryUsageImpl;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.management.exception.JmxConnectionException;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorable;
import com.tibco.cep.runtime.management.Domain;
import com.tibco.cep.runtime.service.management.exception.JMXConnClientException;
import com.tibco.rta.common.service.StartStopService;

/**
 * Define the functionality which will be implemented using MBean
 * 
 * @author dijadhav
 *
 */
public interface MBeanService extends StartStopService {
	/**
	 * @param methodGroup
	 *            - Group Name of the method
	 * @param methodName
	 *            - Name of the method which needs to invoke
	 * @param params
	 *            - Map of parameters
	 * @param username
	 *            - Name of the user
	 * @param password
	 *            - Encoded password
	 * @param hostIp
	 *            - Host Ip Address
	 * @param jmxPort
	 *            - Jmx port
	 * @param agentId
	 *            - Id of the Agent
	 * @param instanceKey
	 *            - Instance key
	 * @param cluster
	 *            - Name Name of the cluster
	 * @param entityName
	 *            of the entity which methods we want to invoke.
	 * @return OperationResult Instance
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */

	OperationResult invoke(String entityType, String methodGroup, String methodName, Map<String, String> params,
			String username, String password, String hostIp, int jmxPort, int agentId, String instanceKey,
			String clusterName) throws MBeanOperationFailException;

	/**
	 * Get Service instance start time
	 * 
	 * @param instance
	 * @return Start time
	 */
	long getProcessStartTime(ServiceInstance instance);

	/**
	 * Get the details of memory used by java process
	 * 
	 * @param instance
	 *            - Service Instance
	 * @return List of ProcessMemoryUsage
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	List<ProcessMemoryUsageImpl> getMemoryUsage(ServiceInstance instance) throws MBeanOperationFailException;

	/**
	 * Get Logger details for given service instance
	 * 
	 * @param instance
	 *            - Service Instance
	 * @return Logger details
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	List<LogDetailImpl> getLoggerDetails(ServiceInstance instance) throws MBeanOperationFailException;

	/**
	 * Get domain details
	 * 
	 * @param instance
	 *            - Service Instance
	 * @return Domain Details
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	Collection<Domain> getDomains(ServiceInstance instance) throws MBeanOperationFailException;

	/**
	 * Get Garbage collection details
	 * 
	 * @param instance
	 *            - Service Instance
	 * @return Garbage collection result
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	OperationResult getGarbageCollectionDetails(ServiceInstance instance) throws MBeanOperationFailException;

	/**
	 * Get thread details of given service instance
	 * 
	 * @param instance
	 *            - Service Instance
	 * @return Thread details
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	ThreadDetail getThreadDetails(ServiceInstance instance) throws MBeanOperationFailException;

	/**
	 * Get Memory pools
	 * 
	 * @return List of memory pools
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	List<String> getMemoryPools() throws MBeanOperationFailException;

	/**
	 * Get the Memory Usage of the running instance for given pool.
	 * 
	 * @param instance
	 *            -Running instance
	 * @return CPU usage of running instance.
	 */
	MemoryUsage getMemoryByPoolName(String poolName, ServiceInstance instance) throws MBeanOperationFailException;

	/**
	 * Get the CPU of the running instance.
	 * 
	 * @param instance
	 *            -Running instance
	 * @return CPU usage of running instance.
	 */
	CPUUsage getCPUUsage(ServiceInstance instance) throws MBeanOperationFailException;

	/**
	 * Set log level
	 * 
	 * @param instance
	 *            -Running instance
	 * @param logDetails
	 *            -Log level details
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */

	void setLoggerDetails(ServiceInstance instance, DeploymentVariables deploymentVariables)
			throws MBeanOperationFailException;

	/**
	 * Get thread dump of running instance
	 * 
	 * @param instance
	 *            - Service Instance
	 * @return Thread of instance
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	String getThreadDump(ServiceInstance instance) throws MBeanOperationFailException;

	/**
	 * 
	 * @param instance
	 *            - Service instance
	 * @param logPatternsAndLevel
	 *            - Log patterns and level
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	void applyLogPatterns(ServiceInstance instance, Map<String, String> logPatternsAndLevel)
			throws MBeanOperationFailException;

	/**
	 * Load and deploy classes
	 * 
	 * @param vrfURI
	 *            - Virtual Rule Function Name
	 * @param implName
	 *            - Virtual Rule Function implementation name
	 * @param instance
	 *            - Service instance
	 * @throws MBeanOperationFailException
	 *             -Exception is throws if MBean operation fails.
	 */
	void loadAndDeploy(String vrfURI, String implName, ServiceInstance instance) throws MBeanOperationFailException;

	/**
	 * Load and deploy rule templates
	 * 
	 * @param agentName
	 *            - Name of the agent(required)
	 * @param projectName
	 *            - Project name
	 * @param ruleTemplateInstanceFQN
	 *            - Rule template instance FQN
	 * @param instance
	 *            - Service instance
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	void loadAndDeployRuleTemplates(String agentName, String projectName, String ruleTemplateInstanceFQN,
			ServiceInstance instance) throws MBeanOperationFailException;

	/**
	 * This method is used to get the MBeanServerConnection.
	 * 
	 * @param hostIp
	 *            - Host IP Address
	 * @param jmxPort
	 *            - JMX Port
	 * @param instanceKey
	 *            - Instance key
	 * @param username
	 *            - JMX user name
	 * @param decodedPassword
	 *            - Decoded JMX Password
	 * @return MBeanServerConnection - Exception is throws if MBean operation
	 *         fails.
	 * @throws JMXConnClientException
	 */
	MBeanServerConnection getMbeanConnection(String hostIp, int jmxPort, String instanceKey, String username,
			String decodedPassword) throws JMXConnClientException;

	/**
	 * Get the topology data for passed instance
	 * 
	 * @param serviceInstance
	 *            - Service instance
	 * @return Service instance updated with topology data
	 * @throws JmxConnectionException
	 */
	ServiceInstance getServiceInstanceTopologyData(ServiceInstance serviceInstance) throws JmxConnectionException;

	/**
	 * Remove existing JMX connection
	 * 
	 * @param key
	 */
	void removeJMXConnection(String key);

	/**
	 * Stop service instance
	 * 
	 * @param serviceInstance
	 * @throws MBeanOperationFailException 
	 */
	void stopInstance(ServiceInstance serviceInstance) throws MBeanOperationFailException;
	
	/**
	 * Get Garbage collection details for Be Tea Agent
	 * @param poolName 
	 * 
	 * @param instance
	 *            - BeTeaAgentMonitorable
	 * @return Garbage collection result
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	OperationResult getBeAgentGarbageCollectionDetails(BeTeaAgentMonitorable instance) throws MBeanOperationFailException;
	
	/**
	 * Get Memory Pool details for Be Tea Agent
	 * @param poolName 
	 * 			  - String
	 * @param instance
	 *            - BeTeaAgentMonitorable
	 * @return Memory Pool details
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	MemoryUsage getBeAgentMemoryByPoolName(String poolName, BeTeaAgentMonitorable instance) throws MBeanOperationFailException;
	
	/**
	 * Get Memory pools
	 * 
	 * @return List of memory pools for BE Agent
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	List<String> getBeAgentMemoryPools() throws MBeanOperationFailException;

}
