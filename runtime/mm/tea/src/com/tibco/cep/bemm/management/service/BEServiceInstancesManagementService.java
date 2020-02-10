package com.tibco.cep.bemm.management.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataSource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jcraft.jsch.Session;
import com.tibco.cep.bemm.common.audit.AuditRecord;
import com.tibco.cep.bemm.common.audit.AuditRecords;
import com.tibco.cep.bemm.common.exception.MBeanOperationFailException;
import com.tibco.cep.bemm.common.model.CPUUsage;
import com.tibco.cep.bemm.common.model.MemoryUsage;
import com.tibco.cep.bemm.common.model.ThreadDetail;
import com.tibco.cep.bemm.common.model.impl.LogDetailImpl;
import com.tibco.cep.bemm.common.model.impl.ProcessMemoryUsageImpl;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.service.LockManager;
import com.tibco.cep.bemm.common.service.MBeanService;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.ValidationService;
import com.tibco.cep.bemm.management.exception.BEDownloadLogException;
import com.tibco.cep.bemm.management.exception.BEProcessingUnitStartFailException;
import com.tibco.cep.bemm.management.exception.BEProcessingUnitStopFailException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceAddException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceDeleteException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceEditException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceKillException;
import com.tibco.cep.bemm.management.exception.BEUploadFileException;
import com.tibco.cep.bemm.management.exception.BEValidationException;
import com.tibco.cep.bemm.management.exception.CopyInstanceFailException;
import com.tibco.cep.bemm.management.exception.DeploymentFailException;
import com.tibco.cep.bemm.management.exception.DeploymentVariableStoreFailException;
import com.tibco.cep.bemm.management.exception.JschAuthenticationException;
import com.tibco.cep.bemm.management.exception.JschCommandFailException;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.BEMMModelFactory;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.rta.common.service.StartStopService;
import com.tibco.tea.agent.be.ui.model.GroupDeploymentVariable;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

/**
 * This interface is used to defined the methods used to perform operations on
 * instance.
 * 
 * @author dijadhav
 *
 */
public interface BEServiceInstancesManagementService extends StartStopService {
	/**
	 * Initialize the configuration
	 * 
	 * @param configuration
	 *            - Properties
	 * @throws ServiceInitializationException
	 *             - Thrown when service initialization failed
	 */
	void init(Properties configuration) throws ServiceInitializationException;

	/**
	 * Create the service instance
	 * 
	 * @param application
	 *            - Application
	 * @param name
	 *            - Name of the service center
	 * @param processingUnit
	 *            - name of the processing unit
	 * @param hostName
	 *            - Name of the host
	 * @param jmxPort
	 *            -JMX Port
	 * @param deploymentPath
	 *            - Deployment path
	 * @param isPredefined
	 *            - The instance is predefined or not.
	 * @param jmxUserName
	 *            - JMX Password
	 * @param jmxPassword
	 *            - JMX User Name
	 * @param beId TODO
	 * @return Success message
	 * @throws BEServiceInstanceAddException
	 *             -Thrown when add service instance fails.
	 * @throws BEValidationException
	 *             - Thrown if there are in failure in data validation.
	 */
	String createServiceInstance(Application application, String name, String processingUnit, String hostName,
			int jmxPort, String deploymentPath, boolean isPredefined, String loggedInUser, String jmxUserName,
			String jmxPassword, String beId) throws BEServiceInstanceAddException, BEValidationException;

	/**
	 * Delete the service instance
	 * 
	 * @param application
	 *            - Application instance.
	 * @param name
	 *            - Name of the Instance
	 * @return Success message
	 * @throws BEServiceInstanceDeleteException
	 *             - Thrown when delete service instance fails.
	 * @throws BEValidationException
	 *             - Thrown if there are in failure in data validation.
	 */
	String deleteServiceInstance(Application application, String name)
			throws BEServiceInstanceDeleteException, BEValidationException;

	/**
	 * Deploy the service instance
	 * 
	 * @param instance
	 *            - Processing unit instance
	 * @param loggedInUser
	 *            - Logged in user name
	 * @return Success message
	 * @throws DeploymentFailException
	 *             -Thrown if deployment of service instance fails
	 * @throws BEValidationException
	 *             - Thrown if there are in failure in data validation.
	 */
	String deploy(ServiceInstance serviceInstance, String loggedInUser)
			throws DeploymentFailException, BEValidationException;

	/**
	 * UnDeploy the service instance
	 * 
	 * @param instance
	 *            - Processing unit instance
	 * @return Success message
	 * @throws DeploymentFailException
	 *             -Thrown if un-deployment of service instance fails
	 * @throws BEValidationException
	 *             - Thrown if there are in failure in data validation.
	 */
	String undeploy(ServiceInstance serviceInstance, String loggedInUser)
			throws DeploymentFailException, BEValidationException;

	/**
	 * Hot deploy of EAR for service instance
	 * 
	 * @param serviceInstance
	 *            - Service instance on which EAR should be deployed
	 * @param earArchive
	 *            - Application archive file
	 * @param loggedInUser
	 *            - Logged in user
	 * @return Success message
	 * @throws DeploymentFailException
	 *             - Thrown if hot deployment of EAR fails -
	 * @throws BEValidationException
	 *             - Thrown if there are in failure in data validation.
	 */
	String hotdeploy(ServiceInstance serviceInstance, DataSource earArchive, String loggedInUser)
			throws DeploymentFailException, BEValidationException;

	/**
	 * Start the service instance
	 * 
	 * @param instance
	 *            - Processing unit instance
	 * @param loggedInUser
	 *            - Logged in user
	 * @return Success message
	 * @throws BEProcessingUnitStartFailException
	 *             - Exception is thrown when failed to start the instance.
	 * @throws BEValidationException
	 *             - Thrown if there are in failure in data validation.
	 */
	String start(ServiceInstance serviceInstance, String loggedInUser)
			throws BEProcessingUnitStartFailException, BEValidationException, JschAuthenticationException;

	/**
	 * Stop the service instance
	 * 
	 * @param instance
	 *            - Processing unit instance
	 * @param loggedInUser
	 *            - Logged in user
	 * @return Success message
	 * @throws BEProcessingUnitStopFailException
	 *             - Exception is thrown when failed to stop the instance. -
	 * @throws BEValidationException
	 *             - Thrown if there are in failure in data validation.
	 */
	String stop(ServiceInstance instance, String loggedInUser)
			throws BEProcessingUnitStopFailException, BEValidationException;

	/**
	 * Edit the instance
	 * 
	 * @param instance
	 *            -Processing Unit Instance
	 * @param processingUnit
	 *            - Processing Unit name.
	 * @param hostName
	 *            -Name of the Host
	 * @param jmxPort
	 *            - JMX port
	 * @param deploymentPath
	 *            - Deployment Path
	 * @param loggedInUser
	 *            - Logged in user
	 * @param jmxUserName
	 *            - JMX User Name
	 * @param jmxPassword
	 *            - JMX Password
	 * @param beId
	 *            - BE Home Details Id
	 * @return Success message
	 * @throws BEServiceInstanceEditException
	 *             - Exception is thrown when editing the service instance is
	 *             failed.
	 * @throws BEValidationException
	 *             - Thrown if there are in failure in data validation.
	 */
	String editServiceInstance(ServiceInstance instance, String processingUnit, String hostName, int jmxPort,
			String deploymentPath, String loggedInUser, String jmxUserName, String jmxPassword,String beId)
			throws BEServiceInstanceEditException, BEValidationException;

	/**
	 * Store the deployment variables of given type
	 * 
	 * @param serviceInstance
	 *            -Processing Unit Instance
	 * @param deploymentVariables
	 *            - Instance of Deployment variables
	 * @param isIntermediateAction
	 *            - Whether this operation is externally performed by user or
	 *            intermediate
	 * @return Success message
	 * @throws DeploymentVariableStoreFailException
	 *             - Exception is thrown when storing of deployment variable
	 *             fails
	 * @throws BEValidationException
	 *             - Thrown if there are in failure in data validation.
	 */
	String storeDeploymentVariables(ServiceInstance serviceInstance, DeploymentVariables deploymentVariables,
			String loggedInUser, boolean isIntermediateAction)
			throws DeploymentVariableStoreFailException, BEValidationException;

	/**
	 * Create the clone of the service instance using given details.
	 * 
	 * @param instanceName
	 *            - Name of the instance
	 * @param processingUnit
	 *            - Processing Unit mapped with service instance
	 * @param hostId
	 *            - Host id on which we need to deploy the the instance
	 * @param deploymentPath
	 *            - Path where we need to deploy the resource of instance
	 * @param jmxPort
	 *            - JMX port
	 * @param jmxUserName
	 *            - JMX user name
	 * @param jmxPassword
	 *            - JMX password
	 * @param beId 
	 * @param instance
	 *            - Processing Unit instance which needs to be cloned.
	 * @return Success message
	 * @throws CopyInstanceFailException
	 *             - This class is used to throw exception when copy of instance
	 *             fails.
	 * @throws BEValidationException
	 *             - Thrown if there are in failure in data validation.
	 */
	String copyInstance(String instanceName, String processingUnit, String hostId, String deploymentPath, int jmxPort,
			String jmxUserName, String jmxPassword, String beId, ServiceInstance instance, String loggedInUser)
			throws CopyInstanceFailException, BEValidationException;

	/**
	 * Download the instance logs
	 * 
	 * @param instance
	 *            - Processing Unit Instance
	 * @param isASLog TODO
	 * @return DataSource for logs zip file.
	 * @throws BEDownloadLogException
	 *             Thrown if download of log file fails
	 * @throws BEValidationException
	 *             - Thrown if there are in failure in data validation.
	 */
	DataSource downloadLog(ServiceInstance instance, boolean isASLog) throws BEValidationException, BEDownloadLogException;

	/**
	 * This method is used to load the audit record
	 * 
	 * @param instance
	 *            - Processing unit instance.
	 * @return AuditRecords - Audit records of the instance.
	 * @throws BEValidationException
	 *             - Thrown if there are in failure in data validation.
	 * @throws Exception
	 *             - Exception to load audit
	 */

	AuditRecords loadAuditRecords(ServiceInstance instance) throws BEValidationException, Exception;

	/**
	 * This method is used to store the audit record of Processing Unit
	 * Instance.
	 * 
	 * @param auditRecord
	 *            - Audit record
	 * @throws BEValidationException
	 *             - Thrown if there are in failure in data validation.
	 * @throws Exception
	 *             - Exception to store audit records
	 */

	void storeAuditRecords(AuditRecord auditRecord, ServiceInstance instance) throws BEValidationException, Exception;

	/**
	 * Get CPU Usage of Service instance
	 * 
	 * @param instance
	 *            - Service instance
	 * @return CPUUsage object
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */

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

	CPUUsage getCPUUsage(ServiceInstance instance) throws MBeanOperationFailException;

	/**
	 * Get The memory usage of service instance for given pool
	 * 
	 * @param poolName
	 *            - Name of pool
	 * @param instance
	 *            - Service instance
	 * @return MemoryUsage object
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	MemoryUsage getMemoryByPoolName(String poolName, ServiceInstance instance) throws MBeanOperationFailException;

	/**
	 * Get the list of memory pools
	 * 
	 * @return List of memory pools
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	List<String> getMemoryPools() throws MBeanOperationFailException;

	/**
	 * Get memory usage of service instance
	 * 
	 * @param serviceInstance
	 *            - Service instance
	 * @return List of memory used by service instance
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	List<ProcessMemoryUsageImpl> getMemoryUsage(ServiceInstance serviceInstance) throws MBeanOperationFailException;

	/**
	 * Details of thread used of service instance
	 * 
	 * @param instance
	 *            - Service instance
	 * @return Thread details
	 * @throws MBeanOperationFailException-
	 *             Exception is throws if MBean operation fails.
	 */
	ThreadDetail getThreadDetails(ServiceInstance instance) throws MBeanOperationFailException;

	/**
	 * Get Logger levels
	 * 
	 * @param instance
	 *            - Service instance
	 * @return List of logger details
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	List<LogDetailImpl> getRuntimeLoggerLevels(ServiceInstance instance) throws MBeanOperationFailException;

	/**
	 * Invoke the MM operation
	 * 
	 * @param entityName
	 *            - Name of the entity
	 * @param methodGroup
	 *            - Method group
	 * @param methodName
	 *            - Name of method
	 * @param params
	 *            - Parameters of method
	 * @param username
	 *            - JMX User name
	 * @param password
	 *            - JMX Password
	 * @param hostIp
	 *            - Host on which instance is running
	 * @param jmxPort
	 *            - JMX port
	 * @param agentId
	 *            - BE agent id
	 * @param serviceInstance
	 *            - Service instance on which we need to call method
	 * @return Operation result
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	OperationResult invoke(String entityName, String methodGroup, String methodName, Map<String, String> params,
			String username, String password, String hostIp, int jmxPort, int i, ServiceInstance serviceInstance)
			throws MBeanOperationFailException;

	/**
	 * Get Service instance garbage collection details
	 * 
	 * @param instance
	 *            - Service instance
	 * @return Operation result
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	OperationResult getGarbageCollectionDetails(ServiceInstance instance) throws MBeanOperationFailException;

	/**
	 * Add new deployment variable details for instance
	 * 
	 * @param deploymentVariableType
	 *            - Type of deployment variable
	 * @param serviceInstance
	 *            - Service instance details
	 * @param groupDeploymentVariable
	 *            - Group of deployment variable
	 */
	void addDeploymentVariables(DeploymentVariableType deploymentVariableType, ServiceInstance serviceInstance,
			GroupDeploymentVariable groupDeploymentVariable);

	/**
	 * Get the thread dump of service instance
	 * 
	 * @param instance
	 *            - Service instance
	 * @return Thread dump of service instance
	 * @throws BEValidationException
	 *             - Thrown if there is validation failure in data
	 */
	String getThreadDump(ServiceInstance instance) throws BEValidationException;

	/**
	 * Apply log patterns
	 * 
	 * @param instance-
	 *            Service instance
	 * @param logPatternsAndLevel
	 * @param loggedInUser
	 *            - Logged in user
	 * @param isIntermediateAction
	 *            - Whether action is performed by user of intermediate or not
	 * @return Success message
	 * @throws DeploymentVariableStoreFailException
	 *             - Exception is thrown when storing of deployment variable
	 *             fails.
	 */
	String applyLogPatterns(ServiceInstance instance, Map<String, String> logPatternsAndLevel, String loggedInUser,
			boolean isIntermediateAction) throws DeploymentVariableStoreFailException;

	/**
	 * Upload the the class files
	 * 
	 * @param classZipFile-
	 *            ZIP/Jar of class files
	 * @param instance
	 *            - Service instance on which we need to deploy the class files
	 * @return Success message
	 * @throws BEValidationException
	 *             - Thrown if there is validation failure in data
	 * @throws DeploymentFailException
	 *             - Thrown if upload of class files fails
	 */
	String uploadClassFiles(DataSource classZipFile, ServiceInstance instance)
			throws BEValidationException, DeploymentFailException;

	String loadAndDeploy(String vrfURI, String implName, ServiceInstance instance) throws Exception;

	/**
	 * Used to update the deployment status of service instance
	 * 
	 * @param serviceInstance
	 *            - Service instance
	 * @param deploymentStatus
	 *            - Deployment status
	 * @param loggedInUser
	 *            - Logged in user
	 * @throws DeploymentVariableStoreFailException
	 *             - Fails if update of service instance deployment status
	 *             fails.
	 * @throws BEValidationException
	 *             - Thrown if there is validation failure in data
	 */
	void updateDeploymentStatus(ServiceInstance serviceInstance, BETeaAgentStatus deploymentStatus, String loggedInUser)
			throws DeploymentVariableStoreFailException, BEValidationException;

	/**
	 * Upload Rule template files
	 * 
	 * @param ruleTemplateZipFile
	 *            - Zip file of rule template files
	 * @param instance
	 *            - Service instance
	 * @return Success message
	 * @throws BEValidationException
	 *             - Thrown if there is validation failure in data
	 * @throws DeploymentFailException
	 *             - Thrown if upload of class files fails
	 */
	String uploadRuleTemplates(DataSource ruleTemplateZipFile, ServiceInstance instance)
			throws BEValidationException, DeploymentFailException;

	String loadAndDeployRuleTemplates(String agentName, String projectName, String ruleTemplateInstanceFQN,
			ServiceInstance instance) throws Exception;

	/**
	 * Deploy the service instance
	 * 
	 * @param serviceInstance
	 *            - Service Instance
	 * @param loggedInUser
	 *            - Logged in User
	 * @param session
	 *            - JSCH Session
	 * @param globalVariableDescriptors
	 *            - Collection of GlobalVariableDescript
	 * @return Result of operation
	 * @throws BEValidationException
	 *             - Thrown if there is validation failure in data
	 * @throws DeploymentFailException
	 *             - Thrown if upload of class files fails
	 * @throws JschCommandFailException
	 *             - Thrown if JSCH command fails
	 */
	Object deploy(ServiceInstance serviceInstance, String loggedInUser, Session session,
			Collection<GlobalVariableDescriptor> globalVariableDescriptors)
			throws BEValidationException, DeploymentFailException, JschCommandFailException;

	/**
	 * Start the Service Instance
	 * 
	 * @param serviceInstance
	 *            - Service Instance
	 * @param loggedInUser
	 *            - Logged in User
	 * @param session
	 *            - JSCH Session
	 * @return Result of operation
	 * @throws BEValidationException
	 *             - Thrown if there is validation failure in data
	 * @throws BEProcessingUnitStartFailException
	 *             - Thrown if start of service fails
	 */
	Object start(ServiceInstance serviceInstance, String loggedInUser, Session session)
			throws BEValidationException, BEProcessingUnitStartFailException;

	/**
	 * Undeploy the Service Instance
	 * 
	 * @param serviceInstance
	 *            - Service Instance
	 * @param loggedInUser
	 *            - Logged in User
	 * @param session
	 *            - JSCH Session
	 * @return Result of operation
	 * @throws DeploymentFailException
	 *             -Thrown if upload of class files fails
	 * @throws BEValidationException
	 *             - Thrown if there is validation failure in data
	 */
	Object undeploy(ServiceInstance serviceInstance, String loggedInUser, Session session,
			Collection<GlobalVariableDescriptor> globalVariableDescriptors)
			throws DeploymentFailException, BEValidationException;

	/**
	 * Kill Service instance
	 * 
	 * @param serviceInstance
	 *            - Service Instance
	 * @param loggedInUser
	 *            - Logged in User
	 * @param session
	 *            - JSCH Session
	 * @return Result of operation
	 * @throws BEValidationException
	 *             - Thrown if validation of data fails
	 * @throws BEServiceInstanceKillException
	 *             - Thrown if kill to service instance fails
	 */
	String kill(ServiceInstance serviceInstance, String loggedInUser, Session session)
			throws BEValidationException, BEServiceInstanceKillException;

	/**
	 * Kill Service instance from instance page
	 * 
	 * @param serviceInstance
	 *            - Service Instance
	 * @param loggedInUser
	 *            - Logged in User
	 * @return Success message
	 * @throws BEServiceInstanceKillException
	 *             - Thrown if kill to service instance fails
	 * @throws BEValidationException
	 *             - Thrown if validation of data fails
	 */
	String killServiceServiceInstance(ServiceInstance serviceInstance, String loggedInUser)
			throws BEServiceInstanceKillException, BEValidationException;

	/**
	 * Delete the service instance
	 * 
	 * @param application
	 *            - Application object
	 * @param name
	 *            - Name of the service instance which will be deleted
	 * @return Success message
	 * @throws BEServiceInstanceDeleteException
	 *             - Thrown if delete of service instance fails
	 */
	String delele(Application application, String name) throws BEServiceInstanceDeleteException;

	/**
	 * Host deployment of EAR file
	 * 
	 * @param serviceInstance
	 *            - Service instance
	 * @param localEarFile
	 *            - Application EAR file
	 * @param loggedInUser
	 *            - Logged in user
	 * @param session
	 *            - JSCh session
	 * @return Success message
	 * @throws DeploymentFailException
	 *             - Thrown if deployment of EAR fails
	 * @throws BEValidationException
	 *             - Thrown if validation of data fails
	 */
	String hotdeploy(ServiceInstance serviceInstance, String localEarFile, String loggedInUser, Session session)
			throws DeploymentFailException, BEValidationException;

	/**
	 * Upload a data source to remote directory
	 * 
	 * @param serviceInstance
	 *            - Service instance
	 * @param dataSource
	 *            - Data source
	 * @param session
	 *            - JSCh session
	 * @param remoteUploadDir
	 *            - Remote directory where we need to upload the code
	 * @throws BEUploadFileException
	 *             - thrown if upload fails
	 */
	void upload(ServiceInstance serviceInstance, DataSource dataSource, Session session, String remoteUploadDir)
			throws BEUploadFileException;

	/**
	 * Fire tail command to fetch logs
	 * 
	 * @param numberofLines
	 *            - Number of lines to retrieve
	 * @param isASLog - Tail for AS logs or not
	 * @param serviceInstance
	 *            - Instance object
	 * @return Result of tail command
	 */

	String fireTailCommand(String numberofLines, boolean isASLog, ServiceInstance serviceInstance);

	/**
	 * 
	 * @param command
	 * @param session
	 * @return
	 * @throws JschCommandFailException
	 */
	String fireTailCommand(String command, Session session) throws JschCommandFailException;

	String setDefaultProfile(String profileName, ServiceInstance instance) throws Exception;

}
