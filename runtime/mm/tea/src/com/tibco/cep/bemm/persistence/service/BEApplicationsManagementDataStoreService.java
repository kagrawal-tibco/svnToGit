package com.tibco.cep.bemm.persistence.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.cep.bemm.common.audit.AuditRecords;
import com.tibco.cep.bemm.common.tra.config.model.HostTraConfigs;
import com.tibco.cep.bemm.management.exception.BEApplicationSaveException;
import com.tibco.cep.bemm.management.exception.BEMasterHostSaveException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceSaveException;
import com.tibco.cep.bemm.management.exception.FileSaveException;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.persistence.service.exception.BEApplicationProfileNotExistException;
import com.tibco.cep.bemm.persistence.service.exception.BEApplicationProfileSaveException;
import com.tibco.cep.bemm.persistence.service.exception.BEHostTRASaveException;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.rta.common.service.StartStopService;

public interface BEApplicationsManagementDataStoreService<T> extends StartStopService {

	/**
	 * @param configuration
	 */
	public void init(Properties configuration) throws ServiceInitializationException;

	/**
	 * @return
	 */
	public T getApplicationManagementDataStore();

	/**
	 * @return
	 * @throws Exception
	 */
	public Collection<Application> fetchAllApplicationTopologies() throws Exception;

	/**
	 * @param applicationName
	 * @return
	 */
	public Application fetchApplicationTopology(String applicationName);

	/**
	 * @param application
	 */
	public void storeApplicationTopology(Application application) throws BEApplicationSaveException;

	/**
	 * @param applicationName
	 * @return
	 * @throws Exception
	 */
	public ClusterConfig fetchApplicationCDD(String applicationName) throws Exception;

	/**
	 * @param applicationName
	 * @param applicationCDD
	 * @throws FileSaveException
	 */
	public void storeApplicationCDD(String applicationName, byte[] applicationCDD) throws BEApplicationSaveException;

	/**
	 * @param applicationName
	 * @return
	 * @throws Exception 
	 */
	public Collection<GlobalVariableDescriptor> fetchApplicationArchive(String applicationName) throws Exception;

	/**
	 * @param applicationName
	 * @param applicationArchive
	 * @throws FileSaveException
	 */
	public void storeApplicationArchive(String applicationName, byte[] applicationArchive)
			throws BEApplicationSaveException;

	/**
	 * @param application
	 * @param serviceInstance
	 * @param deploymentVariables
	 * @throws BEServiceInstanceSaveException
	 */
	public void storeDeploymentVariables(String applicationName, String serviceInstanceName,
			DeploymentVariables deploymentVariables) throws BEServiceInstanceSaveException;

	/**
	 * @param applicationName
	 * @param serviceInstanceName
	 * @param variableType
	 * @return
	 * @throws Exception
	 */
	public DeploymentVariables fetchDeploymentVaribles(String applicationName, ServiceInstance serviceInstance,
			DeploymentVariableType variableType) throws Exception;

	/**
	 * @param applicationName
	 * @return
	 * @throws Exception
	 */
	public String loadApplicationCDD(String applicationName) throws Exception;

	/**
	 * @param applicationName
	 * @return
	 * @throws Exception
	 */
	public DeploymentVariables fetchApplicationConfig(String applicationName,
			DeploymentVariableType deploymentVariableType) throws Exception;

	/**
	 * @param deploymentVariables
	 * @return
	 * @throws Exception
	 */
	public void storeApplicationConfig(String applicationName, DeploymentVariables deploymentVariables)
			throws Exception;

	void deleteConfigData(String entityPath) throws FileNotFoundException;

	void storeAuditRecords(String applicationName, String serviceInstanceName, AuditRecords auditRecords)
			throws Exception;

	AuditRecords fetchAuditRecords(String applicationName, String serviceInstanceName) throws Exception;

	public void storeMasterHostTopology(Map<String, MasterHost> masterHotsMap) throws BEMasterHostSaveException;

	public Map<String, MasterHost> fetchAllMasterHost();

	void storeApplicationTraConfigs(String applicationName, HostTraConfigs hostTraConfigs) throws Exception;

	HostTraConfigs loadpplicationTraConfigs(String applicationName) throws Exception;

	Set<String> getExcludedProperties();

	public InputStream fetchHostTraFile(String applicationName, String hostId);

	void storeHostTra(String applicationName, String hostId, InputStream traFileStream) throws BEHostTRASaveException;

	File getTempFileLocation();

	public String createApplicationXML(com.tibco.cep.bemm.management.export.model.Application exportedApplication)
			throws Exception;

	public com.tibco.cep.bemm.management.export.model.Application createApplicationFromXML(String name)
			throws Exception;

	/**
	 * Store the application profiles
	 * 
	 * @param beProperties
	 * @param systemProperties
	 */
	public void storeApplicationProfile(Application application, String profileName, Map<String, String> globalVariables,
			Map<String, String> systemProperties, Map<String, String> beProperties)
			throws BEApplicationProfileSaveException;

	Set<String> loadProfiles(String name);

	Map<String, Map<String, String>> loadProfile(String applicationName, String profileName) throws BEApplicationProfileNotExistException;
	
	/**
	 * Rollback application if there is exception while create/import
	 * applictaion
	 * 
	 * @param name
	 */
	public void rollback(String name);
	
	public void copyApplicationProfile(Application application, String newProfileName, String oldProfileName);

}
