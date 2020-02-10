package com.tibco.cep.bemm.management.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataSource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.cep.bemm.common.exception.MBeanOperationFailException;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.model.MemoryUsage;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.service.LockManager;
import com.tibco.cep.bemm.management.exception.AddApplicationProfileException;
import com.tibco.cep.bemm.management.exception.BEApplicationCreateException;
import com.tibco.cep.bemm.management.exception.BEApplicationDeleteException;
import com.tibco.cep.bemm.management.exception.BEApplicationEditException;
import com.tibco.cep.bemm.management.exception.BEApplicationExportException;
import com.tibco.cep.bemm.management.exception.BEApplicationImportFailException;
import com.tibco.cep.bemm.management.exception.BEApplicationLoadException;
import com.tibco.cep.bemm.management.exception.BEApplicationNotFoundException;
import com.tibco.cep.bemm.management.exception.BEApplicationTraUploadException;
import com.tibco.cep.bemm.management.exception.BEDownloadLogException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceAddException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceDeleteException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceDeployException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceKillException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceStartException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceStopException;
import com.tibco.cep.bemm.management.exception.BEValidationException;
import com.tibco.cep.bemm.management.exception.CopyInstanceFailException;
import com.tibco.cep.bemm.management.exception.DeleteApplicationProfileException;
import com.tibco.cep.bemm.management.exception.DeploymentFailException;
import com.tibco.cep.bemm.management.exception.DeploymentVariableStoreFailException;
import com.tibco.cep.bemm.management.exception.EditApplicationProfileException;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.model.AgentConfig;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.Monitorable.ENTITY_TYPE;
import com.tibco.cep.bemm.model.ProcessingUnit;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.Summary;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.model.listener.StatusChangeListener;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorable;
import com.tibco.cep.bemm.persistence.service.exception.BEApplicationProfileNotExistException;
import com.tibco.rta.common.service.StartStopService;
import com.tibco.tea.agent.be.ui.model.GroupDeploymentVariable;

/**
 * This interface defines the methods to manage the Business Events application.
 * 
 * @author dijadhav
 *
 */
public interface BEApplicationsManagementService extends StartStopService {

	/**
	 * Initialize the service using given
	 * 
	 * @param configuration
	 * @throws ObjectCreationException
	 * @throws BEApplicationLoadException
	 */
	void init(Properties configuration) throws ServiceInitializationException;

	/**
	 * @param entityType
	 * @param statusChangeListener
	 */
	void registerStatusChangeListener(ENTITY_TYPE entityType, StatusChangeListener statusChangeListener);

	/**
	 * Get the applications
	 * 
	 * @return Application collection
	 */
	Map<String, Application> getApplications();

	/**
	 * @param applicationName
	 * @return
	 */
	Application getApplicationByName(String applicationName);

	/**
	 * @param name
	 * @param loggedInUser
	 *            TODO
	 * @param application
	 * @param cddContents
	 * @param earContents
	 * @throws BEApplicationCreateException
	 */
	Application createApplication(String name, DataSource cddDataSource, DataSource earDataSource, String loggedInUser,
			StringBuilder successMessage) throws BEApplicationCreateException;

	/**
	 * @param name
	 *            TODO
	 * @param stfileDataSource
	 * @param cddDataSource
	 * @param earDataSource
	 * @param loggedInUser
	 *            TODO
	 * @return
	 */
	Application importApplication(String name, DataSource stfileDataSource, DataSource cddDataSource,
			DataSource earDataSource, String loggedInUser, StringBuilder successMessage)
			throws BEApplicationImportFailException;

	/**
	 * @param application
	 * @param name
	 * @param processingUnit
	 * @param jmxPort
	 * @param deploymentPath
	 * @param jmxUserName
	 *            TODO
	 * @param jmxPassword
	 *            TODO
	 * @param beId
	 *            TODO
	 * @param hostId
	 * @param isUseAsEngineName
	 * @throws BEServiceInstanceAddException
	 */
	String createServiceInstance(Application application, String name, String processingUnit, String hostName,
			int jmxPort, String deploymentPath, boolean isPredefined, String loggedInUser, String jmxUserName,
			String jmxPassword, String beId) throws BEServiceInstanceAddException;

	/**
	 * @param name
	 * @param cddContents
	 * @param earContents
	 * @param loggedInUser
	 *            TODO
	 * @param isEARMentioned
	 * @param isCDDMentioned
	 * @param instanceAgentstoBeRemoved 
	 * @param deploymentVariables
	 * @throws BEApplicationEditException
	 */
	String editApplication(String name, DataSource cddContents, DataSource earContents, String loggedInUser,
			boolean isCDDMentioned, boolean isEARMentioned,List<String> removedPU, List<String> removedInstanceAgent, List<String> instanceAgentstoBeRemoved) throws BEApplicationEditException;

	/**
	 * @param name
	 * @throws BEApplicationDeleteException
	 */
	String deleteApplication(String name) throws BEApplicationDeleteException;

	/**
	 * @param application
	 * @param instances
	 * @param loggedInUser
	 *            TODO
	 * @return
	 * @throws DeploymentFailException
	 */
	String deploy(Application application, List<String> instances, String loggedInUser)
			throws BEServiceInstanceDeployException;

	/**
	 * @param application
	 * @param instances
	 * @throws DeploymentFailException
	 */
	String undeploy(Application application, List<String> instances, String loggedInUser)
			throws BEServiceInstanceDeployException;

	/**
	 * @param application
	 * @param instances
	 * @throws DeploymentFailException
	 */
	String start(Application application, List<String> instances, String loggedInUser)
			throws BEServiceInstanceStartException;

	/**
	 * @param application
	 * @param instances
	 * @throws DeploymentFailException
	 */
	String stop(Application application, List<String> instances, String loggedInUser)
			throws BEServiceInstanceStopException;

	/**
	 * @param applicationName
	 * @param procUnit
	 * @return
	 */
	Collection<ServiceInstance> getServiceInstancesByPU(String applicationName, ProcessingUnit procUnit);

	/**
	 * @param name
	 * @param procUnit
	 * @return
	 */
	Collection<Host> getHostsForPU(String name, ProcessingUnit procUnit);

	/**
	 * @param applicationName
	 * @return
	 */
	Collection<AgentConfig> getAgents(String applicationName);

	/**
	 * @param name
	 * @param puId
	 * @return
	 */
	Summary getApplicationSummaryByPUId(String name, String puId);

	/**
	 * @param appName
	 * @return
	 */
	Summary getApplicationSummary(String appName);

	/**
	 * @param applicationName
	 */
	String updateInMemoryServiceInstance(String applicationName);

	/**
	 * This method is used to load the deployment variables for a group.
	 * 
	 * @param name
	 *            -Name of the application
	 * @param deploymentVariableType
	 *            - Type of the Deployment variable
	 * @param instancesKey
	 *            - List of instance key
	 * @return GroupDeploymentVariable
	 */
	List<GroupDeploymentVariable> loadDeploymentVariable(String name, DeploymentVariableType deploymentVariableType,
			List<String> instancesKey);

	/**
	 * This method is used to load the deployment variables for a group.
	 * 
	 * @param name
	 *            - Name of the application
	 * @param deploymentVariableType
	 *            - Type of the Deployment variable
	 * @param GroupDeploymentVariables
	 *            - Group deployment variable
	 */
	String storeGroupDeploymentVariable(String name, DeploymentVariableType deploymentVariableType,
			List<GroupDeploymentVariable> groupDeploymentVariables, String loggedInUser);

	/**
	 * This method is used to load the application CDD.
	 * 
	 * @param name
	 *            - Name of the application
	 * @return
	 */
	String getBEApplicationsCDD(String name);

	/**
	 * @return
	 */
	long getInstanceStartThreshold();

	/**
	 * @param appName
	 * @param hostAddress
	 * @return
	 * @throws BEValidationException
	 * @throws BEApplicationNotFoundException
	 */
	Host getHostByHostIpAddress(String appName, String hostAddress)
			throws BEValidationException, BEApplicationNotFoundException;

	/**
	 * @param application
	 * @param earArchive
	 * @param loggedInUser
	 * @param instances
	 * @return
	 * @throws BEServiceInstanceDeployException
	 */

	String hotdeploy(Application application, DataSource earArchive, String loggedInUser, List<String> instances)
			throws BEServiceInstanceDeployException;

	/**
	 * @param name
	 * @param hostName
	 * @return
	 * @throws BEApplicationNotFoundException
	 * @throws BEValidationException
	 */
	Host getHostByHostName(String name, String hostName) throws BEApplicationNotFoundException, BEValidationException;

	/**
	 * @param application
	 * @param runtimeVariables
	 * @param loggedInUser
	 * @return
	 * @throws DeploymentVariableStoreFailException
	 * @throws BEValidationException
	 */
	String storeDeploymentVariables(Application application, DeploymentVariables runtimeVariables, String loggedInUser)
			throws DeploymentVariableStoreFailException, BEValidationException;

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
	 * @param beId
	 * @return
	 * @throws CopyInstanceFailException
	 * @throws BEValidationException
	 */
	String copyInstance(String instanceName, String processingUnit, String hostId, String deploymentPath, int jmxPort,
			ServiceInstance instance, String loggedInUser, String jmxUserName, String jmxPassword, String beId)
			throws CopyInstanceFailException, BEValidationException;

	@JsonIgnore
	void setMasterHostService(BEMasterHostManagementService masterHostManagementService);

	/**
	 * Add new host in application if host exit in application
	 * 
	 * @return TODO
	 */
	Host addApplicationHost(Application application, String hostName);

	String groupKill(List<String> instances, Application application, String loggedInUser)
			throws BEServiceInstanceKillException, BEValidationException;

	/**
	 * Group thread dump at application level
	 * 
	 * @param instances
	 * @param application
	 * @return
	 * @throws BEValidationException
	 */
	Map<String, String> groupThreadDump(List<String> instances, Application application) throws BEValidationException;

	DataSource groupThreadDumpZip(List<String> instances, Application application) throws BEValidationException;

	Set<String> getInstanceAgents(List<String> instances, Application application) throws BEValidationException;

	String applyLogPatterns(Application application, List<String> instances, Map<String, String> logPatternsAndLevel,
			String loggedInUser, boolean isIntermediateAction) throws BEValidationException;

	Map<String, String> getGroupRuntimeLoggerLevels(Application application, List<String> instances);

	/**
	 * Load existing configuration of Host TRA
	 * 
	 * @param application
	 * @throws Exception
	 */
	void loadApplicationTraConfig(Application application) throws Exception;

	/**
	 * 
	 * @param application
	 * @return
	 * @throws BEApplicationTraUploadException
	 */
	String saveHostTraConfig(Application application) throws BEApplicationTraUploadException;

	/**
	 * Upload the host TRA file
	 * 
	 * @param hostId
	 *            - HOst Id
	 * @param uploadFile
	 *            - Upload new file or give existing TRA path
	 * @param deploymentPath
	 *            - Path where TRA should be uploaded
	 * @param uploadTRAfile
	 *            - New uploaded TRA file
	 * @param traFilePath
	 *            - Path of TRA from HOST
	 * @param application
	 *            - Application object
	 * @param isDeleted
	 *            - Host TRA file deleted or not
	 * @param loggedInUser
	 *            - Logged in User
	 * @return Success message
	 * @throws BEApplicationTraUploadException
	 *             - This exception is thrown when TRA file upload fails
	 */
	String uploadHostTra(String hostId, boolean uploadFile, String deploymentPath, DataSource uploadTRAfile,
			String traFilePath, Application application, boolean isDeleted, String loggedInUser)
			throws BEApplicationTraUploadException;

	/**
	 * Download log files of given instances
	 * 
	 * @param instances
	 *            - Instances whose log file needs to be deleted
	 * @param application
	 *            - Application object
	 * @param isASLog
	 *            TODO
	 * @return Zip file of log files
	 * @throws BEDownloadLogException
	 *             - This class is used to throw the exception when download of
	 *             instance log fails.
	 * 
	 */
	DataSource downloadLogs(List<String> instances, Application application, boolean isASLog)
			throws BEDownloadLogException;

	/**
	 * Load All deployment variables
	 * 
	 * @param application
	 *            - Application instance
	 */
	void loadAllDeploymentVariable(Application application);

	/**
	 * Hot deployment of all service instances
	 * 
	 * @param application
	 * @param earpath
	 * @param loggedInUser
	 * @return
	 * @throws BEServiceInstanceDeployException
	 */
	String hotDeployAll(Application application, DataSource earpath, String loggedInUser)
			throws BEServiceInstanceDeployException;

	/**
	 * Invoke group level BEMM operation
	 * 
	 * @param entityName
	 * @param methodGroup
	 * @param methodName
	 * @param params
	 * @param instances
	 * @param application
	 * @param loggedInUser
	 * @param agentConfig
	 * @return
	 * @throws MBeanOperationFailException
	 */
	Map<String, OperationResult> invokeGroupOperation(String entityName, String methodGroup, String methodName,
			Map<String, String> params, List<String> instances, Application application, String loggedInUser,
			AgentConfig agentConfig) throws MBeanOperationFailException;

	/**
	 * Group delete of passed service instances
	 * 
	 * @param application
	 *            - Application object
	 * @param instances
	 *            - Service instances needs to be deleted
	 * @param loggedInUser
	 *            - Logged in user
	 * @param deletedInstance
	 *            - List of deleted instance
	 * @return
	 * @throws BEServiceInstanceDeleteException
	 */
	String groupDelete(Application application, List<String> instances, String loggedInUser,
			List<String> deletedInstance) throws BEServiceInstanceDeleteException;

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
	 * Export the application
	 * 
	 * @param application
	 * @return Zip file with details of application
	 * @throws BEApplicationExportException
	 */
	DataSource export(Application application) throws BEApplicationExportException;

	/**
	 * Import the exported application
	 * 
	 * @param file
	 * @param loggedInUser
	 * @param successMessage
	 * @return
	 * @throws BEApplicationImportFailException
	 */
	Application importExportedApplication(DataSource file, String loggedInUser, StringBuilder successMessage)
			throws BEApplicationImportFailException;

	/**
	 * Fire tail command on logs of given instance
	 * 
	 * @param numberofLines
	 * @param isASLog
	 * @param instance
	 * @return
	 */
	String fireTailCommand(String numberofLines, boolean isASLog, ServiceInstance instance);

	/**
	 * Add application profile
	 * 
	 * @param profileName
	 * @param globalVariables
	 * @param beProperties
	 * @param systemProperties
	 * @param application
	 * @return
	 * @throws AddApplicationProfileException
	 */
	String addProfile(String profileName, Map<String, String> globalVariables, Map<String, String> systemProperties,
			Map<String, String> beProperties, Application application) throws AddApplicationProfileException;

	/**
	 * Set Default profile of application
	 * 
	 * @param profileName
	 * @param application
	 * @return
	 * @throws Exception
	 */
	String setDefaultProfile(String profileName, Application application) throws Exception;

	/**
	 * 
	 * @param profileName
	 * @param globalVariables
	 * @param systemProperties
	 * @param beProperties
	 * @param application
	 * @return
	 * @throws EditApplicationProfileException
	 */
	String editProfile(String profileName, Map<String, String> globalVariables, Map<String, String> systemProperties,
			Map<String, String> beProperties, Application application) throws EditApplicationProfileException;

	/**
	 * 
	 * @param profileName
	 * @param application
	 * @return
	 * @throws BEApplicationProfileNotExistException 
	 */
	Map<String,Map<String, String>> getApplicationProfileDetails(String profileName, Application application) throws BEApplicationProfileNotExistException;
	/**
	 * 
	 * @param profileName
	 * @param application
	 * @return
	 * @throws DeleteApplicationProfileException 
	 */
	String deleteApplicationProfile(String profileName, Application application) throws DeleteApplicationProfileException;
	
	/**
	 * 
	 * @param beagent
	 * @return
	 * @throws MBeanOperationFailException 
	 */
	public OperationResult getGarbageCollectionDetails(BeTeaAgentMonitorable beagent) throws MBeanOperationFailException;
	
	/**
	 * Get memory pool list
	 * 
	 * @return List<String>
	 * @throws MBeanOperationFailException 
	 * 
	 */
	public List<String> getMemoryPools() throws MBeanOperationFailException;
	
	/**
	 * Get memory pool details by name
	 * @param pool name
	 * @return MemoryUsage
	 * @throws MBeanOperationFailException 
	 * 
	 */
	public MemoryUsage getMemoryByPoolName(String poolName, BeTeaAgentMonitorable agent) throws MBeanOperationFailException;

	Map<String, String> getApplicationGVNameAndType(Application application);
	
	String copyProfile(String newProfileName, String oldProfileName, Application application) throws AddApplicationProfileException;
	
}
