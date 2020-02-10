package com.tibco.cep.bemm.management.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataSource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jcraft.jsch.Session;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.service.LockManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.ValidationService;
import com.tibco.cep.bemm.management.exception.BEMasterHostAddException;
import com.tibco.cep.bemm.management.exception.BEMasterHostDeleteException;
import com.tibco.cep.bemm.management.exception.BEMasterHostEditException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceKillException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceStartException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceStopException;
import com.tibco.cep.bemm.management.exception.BEUploadFileException;
import com.tibco.cep.bemm.management.exception.InvalidParameterException;
import com.tibco.cep.bemm.management.exception.JschAuthenticationException;
import com.tibco.cep.bemm.management.exception.JschConnectionException;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.model.BE;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.cep.bemm.persistence.service.exception.BEHostTRASaveException;
import com.tibco.cep.bemm.persistence.topology.model.HostResource;
import com.tibco.rta.common.service.StartStopService;

/**
 * Service defines the operations to be performed on master host
 * 
 * @author dijadhav
 *
 */
public interface BEMasterHostManagementService extends StartStopService {
	/**
	 * Initialize the service with given properties
	 * 
	 * @param configuration
	 * @throws ObjectCreationException
	 */
	void init(Properties configuration) throws ServiceInitializationException, ObjectCreationException;

	/**
	 * Set the data store service
	 * 
	 * @param dataStoreService
	 *            - Data store service
	 */
	@JsonIgnore
	void setDataStoreService(BEApplicationsManagementDataStoreService<?> dataStoreService);

	/**
	 * Set Message Service
	 * 
	 * @param messageService
	 *            -Message Service
	 */
	@JsonIgnore
	void setMessageService(MessageService messageService);

	/**
	 * Set validation service
	 * 
	 * @param validationService
	 *            Validation service
	 */
	@JsonIgnore
	void setValidationService(ValidationService validationService);

	/**
	 * Delete the host from the application *
	 * 
	 * @param host
	 *            - Master Host instance
	 * @return Host deletion message
	 * @throws BEMasterHostDeleteException
	 */
	String deleteMasterHost(MasterHost host, String loggedInUser) throws BEMasterHostDeleteException;

	/**
	 * Get map of master host
	 * 
	 * @return Map of master host
	 */
	Map<String, MasterHost> getAllMasterHost();

	/**
	 * Add Master Host
	 * 
	 * @param hostName
	 *            - Host Name
	 * @param ipAddress
	 *            - IP address
	 * @param hostOS
	 *            - Operation system of host
	 * @param userName
	 *            - User name
	 * @param password
	 *            - Password of the user
	 * @param sshPort
	 *            - SSH port
	 * @param deploymentPath
	 *            - Deployment path of host
	 * @param beHome
	 *            - Home of Business Events
	 * @param beTra
	 *            - TRA of business
	 * @param b
	 * @param isTEAgentHost
	 *            TODO
	 * @param isPredefined
	 *            - Whether the host is predefined or not
	 * @param machineName
	 *            - Name of the machine
	 * @return Success message
	 * @throws BEMasterHostAddException
	 *             -This class is used to throw exception when adding the host
	 *             in Business Event application fails.
	 */
	String addMasterHost(String hostName, String ipAddress, String hostOS,List<BE> beServiceModels, String userName,
			String password, int sshPort, String deploymentPath, String loggedInUser)
			throws BEMasterHostAddException;

	/**
	 * Edit the master host details.
	 * 
	 * @param masterHost
	 *            - MasterHost object
	 * @param ipAddress
	 *            - Host IP Address
	 * @param beDetails
	 *            - TRA file path of Business Events home
	 * @param userName
	 *            - Authenticated user name
	 * @param password
	 *            - Password of user
	 * @param sshPort
	 *            - SSH port
	 * @param deploymentPath
	 *            Deployment path of host
	 * @throws BEMasterHostEditException
	 *             - This class is used to throw exception when adding the host
	 *             in Business Event application fails.
	 */

	String editMasterHost(MasterHost masterHost, String hostName, String ipAddress, String hostOs, List<BE> beDetails,
			String userName, String password, int sshPort, String deploymentPath, String loggedInUser)
			throws BEMasterHostEditException;

	/**
	 * Set the application management service
	 * 
	 * @param applicationsManagementService
	 */
	@JsonIgnore
	void setApplicationsManagementService(BEApplicationsManagementService applicationsManagementService);

	/**
	 * Get master host by name
	 * 
	 * @param hostName
	 *            - Name of the host
	 * @return Master host for given name
	 */
	MasterHost getMasterHostByName(String hostName);

	/**
	 * Get matched master host
	 * 
	 * @param hostResource
	 *            - Site topology host resource
	 * @param isImportApplication
	 * @return Matched master host
	 */
	MasterHost getMasterHostByHostResource(HostResource hostResource);

	/**
	 * Create master host for host resource from site topology
	 * 
	 * @param hostResource
	 *            - Host resource from site topology
	 * @return Newly created Master host
	 * @throws JschConnectionException 
	 * @throws JschAuthenticationException 
	 */
	MasterHost createMasterHost(HostResource hostResource) throws JschAuthenticationException, JschConnectionException;

	/**
	 * Clone the master host details.
	 * 
	 * @param masterHost
	 *            - MasterHost object
	 * @param ipAddress
	 *            - Host IP Address
	 * @param beHome
	 *            - Business Events home
	 * @param beTra
	 *            - TRA file path of Business Events home
	 * @param userName
	 *            - Authenticated user name
	 * @param password
	 *            - Password of user
	 * @param sshPort
	 *            - SSH port
	 * @param deploymentPath
	 *            Deployment path of host
	 * @throws BEMasterHostEditException
	 *             - This class is used to throw exception when adding the host
	 *             in Business Event application fails.
	 */

	String cloneMasterHost(MasterHost masterHost, String hostName, String ipAddress, String hostOs, String beHome,
			String beTra, String userName, String password, int sshPort, String deploymentPath, String loggedInUser)
			throws BEMasterHostEditException;

	/**
	 * Get the matched host by host id
	 * 
	 * @param hostResource
	 *            - Site topology host file
	 * @return masterHost - Matched Master host
	 */
	MasterHost getMasterHostByHostId(String masterHostId);

	/**
	 * Refresh status of all master host
	 * 
	 */
	void refereshAllMasterHostStatus();

	/**
	 * Refresh status of master host
	 * 
	 * @param masterHost
	 *            - Master host
	 */
	void refreshMasterHostStatus(MasterHost masterHost);

	/**
	 * Check any instances are deployed on this host or not
	 * 
	 * @param masterHost
	 * @return
	 */
	boolean isInstancesDeployed(MasterHost masterHost);

	/**
	 * Get application host which will be deleted.
	 * 
	 * @return
	 */
	List<String> getApplicationHostToBeDeleted(MasterHost masterHost);

	/**
	 * Group delete of master host
	 * 
	 * @param masterHostsToBeDeleted
	 *            - List of master host to be deleted
	 * @param loggedInUser
	 *            - Logged in user
	 * @return Success message
	 * @throws BEMasterHostDeleteException
	 */
	String groupDelete(List<String> masterHostsToBeDeleted, String loggedInUser) throws BEMasterHostDeleteException;

	/**
	 * Update the status host
	 */
	void updateMasterhostStatus(MasterHost masterHost);

	/**
	 * Get the passed host service instances
	 * 
	 * @param host
	 *            - Master host instance
	 * @return List of service instances
	 */
	List<ServiceInstance> getHostServiceInstance(MasterHost host);

	/**
	 * Start the passed service instance
	 * 
	 * @param masterHost
	 *            - Master host
	 * @param instances
	 *            - Service instances to be started
	 * @param loggedInUser
	 *            - Logged in user
	 * @return Success message
	 * @throws BEServiceInstanceStartException
	 */
	String start(MasterHost masterHost, List<String> instances, String loggedInUser)
			throws BEServiceInstanceStartException;

	/**
	 * Stop the passed service instance
	 * 
	 * @param masterHost
	 *            - Master host
	 * @param instances
	 *            - Service instances to be stopped
	 * @param loggedInUser
	 *            - Logged in user
	 * @return Success message
	 * @throws BEServiceInstanceStopException
	 */
	String stop(MasterHost masterHost, List<String> instances, String loggedInUser)
			throws BEServiceInstanceStopException;

	/**
	 * Kill the passed service instance
	 * 
	 * @param masterHost
	 *            - Master host
	 * @param instances
	 *            - Service instances to be killed
	 * @param loggedInUser
	 *            - Logged in user
	 * @return Success message
	 * @throws BEServiceInstanceKillException
	 */
	String kill(MasterHost masterHost, List<String> instances, String loggedInUser)
			throws BEServiceInstanceKillException;

	/**
	 * @param lockManager
	 */
	@JsonIgnore
	void setLockManager(LockManager lockManager);
	@JsonIgnore
	LockManager getLockManager();	
	List<BE> getBeHomes(String hostName, String ipAddress, String hostOs, String userName, String password, int sshPort) throws Exception;

	String uploadExternalJars(MasterHost masterHost, DataSource jarFiles, String beId) throws InvalidParameterException, BEUploadFileException;

	String uploadExternalJars(MasterHost masterHost, DataSource jarFiles, String beId, Session session)
			throws BEUploadFileException;

	MasterHost discoverBEHome(List<String> selectedHosts) throws Exception;

	void storeTRA(MasterHost masterHost) throws BEHostTRASaveException, JschAuthenticationException, JschConnectionException;

	BE getBEHomeById(MasterHost masterHost, String beId);
}
