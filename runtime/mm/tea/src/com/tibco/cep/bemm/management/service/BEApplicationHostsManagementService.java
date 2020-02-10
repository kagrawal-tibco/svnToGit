package com.tibco.cep.bemm.management.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataSource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.cep.bemm.common.exception.MBeanOperationFailException;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.service.LockManager;
import com.tibco.cep.bemm.common.service.MBeanService;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.ValidationService;
import com.tibco.cep.bemm.management.exception.BEDownloadLogException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceDeleteException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceDeployException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceKillException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceStartException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceStopException;
import com.tibco.cep.bemm.management.exception.BEValidationException;
import com.tibco.cep.bemm.management.exception.CopyInstanceFailException;
import com.tibco.cep.bemm.management.exception.DeploymentFailException;
import com.tibco.cep.bemm.management.exception.DeploymentVariableStoreFailException;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.Summary;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.rta.common.service.StartStopService;
import com.tibco.tea.agent.be.ui.model.GroupDeploymentVariable;

/**
 * Service defines the operations to be performed on applictaion host
 * 
 * @author dijadhav
 *
 */
public interface BEApplicationHostsManagementService extends StartStopService {
	/**
	 * Initialize the service with given properties
	 * 
	 * @param configuration
	 * @throws ObjectCreationException
	 */
	void init(Properties configuration) throws ServiceInitializationException, ObjectCreationException;

	/**
	 * Deploy the passed instances on the host.
	 * 
	 * @param host
	 *            - Host on which we need to deploy the service instance.
	 * @param instances
	 *            - List of the service instance which we need to deploy
	 * @param loggedInUser
	 *            TODO
	 * @throws BEServiceInstanceDeployException
	 */
	String deploy(Host host, List<String> instances, String loggedInUser) throws BEServiceInstanceDeployException;

	/**
	 * Undeploy the passed instances on the host.
	 * 
	 * @param host
	 *            - Host on which we need to undeploy the service instance.
	 * @param instances
	 *            - List of the service instance which we need to undeploy
	 * @throws BEServiceInstanceDeployException
	 */
	String undeploy(Host host, List<String> instances, String loggedInUser) throws BEServiceInstanceDeployException;

	/**
	 * Start the passed instances on the host
	 * 
	 * @param host
	 *            - Host on which we need to start the service instance.
	 * @param instances
	 *            - List of the service instance which we need to start.
	 * @throws DeploymentFailException
	 */
	String start(Host host, List<String> instances, String loggedInUser) throws BEServiceInstanceStartException;

	/**
	 * Stop the passed instances on the host
	 * 
	 * @param host
	 *            - Host on which we need to stop the service instance.
	 * @param instances
	 *            - List of the service instance which we need to stop.
	 * @throws DeploymentFailException
	 */
	String stop(Host host, List<String> instances, String loggedInUser) throws BEServiceInstanceStopException;

	/**
	 * @param host
	 * @return
	 */
	Summary getHostSummary(Host host);

	/**
	 * @param earArchive
	 * @param loggedInUser
	 * @param application
	 * @return
	 * @throws DeploymentFailException
	 * @throws BEServiceInstanceDeployException
	 */
	String hotdeploy(Host host, DataSource earArchive, List<String> instancesList, String loggedInUser)
			throws BEServiceInstanceDeployException;

	/**
	 * @param host
	 * @param globalVariables
	 * @param instancesKey
	 * @return
	 */
	List<GroupDeploymentVariable> loadDeploymentVariable(Host host, DeploymentVariableType globalVariables,
			List<String> instancesKey);

	/**
	 * @param host
	 * @param systemVariables
	 * @param groupDeploymentVariables
	 * @param loggedInUser
	 * @return
	 * @throws DeploymentVariableStoreFailException
	 */
	String saveDeploymentVariable(Host host, DeploymentVariableType systemVariables,
			List<GroupDeploymentVariable> groupDeploymentVariables, String loggedInUser)
			throws DeploymentVariableStoreFailException;

	@JsonIgnore
	BEApplicationsManagementDataStoreService<?> getDataStoreService();

	@JsonIgnore
	void setDataStoreService(BEApplicationsManagementDataStoreService<?> dataStoreService);

	@JsonIgnore
	BEServiceInstancesManagementService getInstanceService();

	@JsonIgnore
	void setInstanceService(BEServiceInstancesManagementService instanceService);

	/**
	 * @return the messageService
	 */
	@JsonIgnore
	MessageService getMessageService();

	/**
	 * @param messageService
	 *            the messageService to set
	 */
	@JsonIgnore
	void setMessageService(MessageService messageService);

	/**
	 * @return the validationService
	 */
	@JsonIgnore
	ValidationService getValidationService();

	/**
	 * @param validationService
	 *            the validationService to set
	 */
	@JsonIgnore
	void setValidationService(ValidationService validationService);

	@JsonIgnore
	MBeanService getMbeanService();

	@JsonIgnore
	void setMbeanService(MBeanService mbeanService);

	/**
	 * Refresh host status
	 * 
	 * @param host
	 */
	void refreshHostStatus(Host host);

	/**
	 * Copy the instance
	 * 
	 * @param instanceName
	 * @param processingUnit
	 * @param hostId
	 * @param deploymentPath
	 * @param jmxPort
	 * @param instance
	 * @param loggedInUser
	 * @param jmxUserName
	 *            TODO
	 * @param jmxPassword
	 *            TODO
	 * @return
	 * @throws CopyInstanceFailException
	 * @throws BEValidationException
	 */
	String copyInstance(String instanceName, String processingUnit, String hostId, String deploymentPath, int jmxPort,
			ServiceInstance instance, String loggedInUser, String jmxUserName, String jmxPassword,String beId)
			throws CopyInstanceFailException, BEValidationException;

	/**
	 * Group thread dump
	 * 
	 * @param instances
	 * @param host
	 * @return
	 * @throws BEValidationException
	 */
	Map<String, String> groupThreadDump(List<String> instances, Host host) throws BEValidationException;

	Set<String> getInstanceAgents(List<String> instances, Host host);

	/**
	 * Apply log patterns
	 * 
	 * @param host
	 *            - Host where service instances are deployed
	 * @param instances
	 *            - Instances to apply pattern
	 * @param logPatternsAndLevel
	 *            - Map of logger and level
	 * @param loggedInUser
	 *            - Logged in user
	 * @param isIntermediateAction
	 *            - Weather this is intermetiate or performed by user
	 * @return Success message
	 * @throws BEValidationException
	 */
	String applyLogPatterns(Host host, List<String> instances, Map<String, String> logPatternsAndLevel,
			String loggedInUser, boolean isIntermediateAction) throws BEValidationException;

	/**
	 * Get runtime log levels of passed instances
	 * 
	 * @param host
	 *            - Host where service instances are deployed
	 * @param instances
	 *            - Instance whose runtime log levels are fetched
	 * @return Logger and level map
	 * @throws BEValidationException
	 */
	Map<String, String> getGroupRuntimeLoggerLevels(Host host, List<String> instances) throws BEValidationException;

	/**
	 * Download the log files
	 * 
	 * @param instances
	 *            - Instances from where log files to be downloaded
	 * @param host
	 *            - Host of service instances
	 * @param isASLog
	 *            - Download AS logs or not
	 * @return Zip of log files from passed instances
	 */
	DataSource downloadLogs(List<String> instances, Host host, boolean isASLog) throws BEDownloadLogException;

	/**
	 * Group Kill passed service instance
	 * 
	 * @param instances
	 *            - Service instances to be killed
	 * @param host
	 *            - Host from where it is killed
	 * @param loggedInUser
	 *            - Logged in user
	 * @return Success message
	 * @throws BEServiceInstanceKillException
	 * @throws BEValidationException
	 */
	String groupKill(List<String> instances, Host host, String loggedInUser)
			throws BEServiceInstanceKillException, BEValidationException;

	/**
	 * Invoke MM opertaion for group of instances
	 *
	 * @param entityName
	 * @param methodGroup
	 * @param methodName
	 * @param params
	 * @param instances
	 * @param host
	 * @param loggedInUser
	 * @return
	 * @throws MBeanOperationFailException
	 */
	Map<String, OperationResult> invokeGroupOperation(String entityName, String methodGroup, String methodName,
			Map<String, String> params, List<String> instances, Host host, String loggedInUser)
			throws MBeanOperationFailException;

	/**
	 * Group delete from host
	 * 
	 * @param host
	 *            - Host from which passed instances need to be deleted
	 * @param instances
	 *            - Instances to be deleted
	 * @param loggedInUser
	 *            - Logged in user
	 * @param deletedInstance
	 *            - List of deleted instance
	 * @return - Success message
	 * @throws BEServiceInstanceDeleteException
	 */
	String groupDelete(Host host, List<String> instances, String loggedInUser, List<String> deletedInstances)
			throws BEServiceInstanceDeleteException;

	/**
	 * @param lockManager
	 */
	@JsonIgnore
	void setLockManager(LockManager lockManager);

	/**
	 * @return LockManager
	 */
	@JsonIgnore
	LockManager getLockManager();

	/**
	 * Fire tail command
	 * @param numberofLines
	 * @param isASLog
	 * @param instance
	 * @return
	 */
	String fireTailCommand(String numberofLines, boolean isASLog, ServiceInstance instance);
}
