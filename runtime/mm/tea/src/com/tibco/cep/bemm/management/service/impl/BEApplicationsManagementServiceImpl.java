package com.tibco.cep.bemm.management.service.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;

import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.AgentsConfig;
import com.tibco.be.util.config.cdd.CacheAgentClassConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.InferenceAgentClassConfig;
import com.tibco.be.util.config.cdd.ProcessAgentClassConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitsConfig;
import com.tibco.be.util.config.cdd.QueryAgentClassConfig;
import com.tibco.be.util.config.factories.ClusterConfigFactory;
import com.tibco.cep.bemm.common.exception.MBeanOperationFailException;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.job.BETeaDeploymentVariableLoadJob;
import com.tibco.cep.bemm.common.job.BETeaDownloadRemoteFileJob;
import com.tibco.cep.bemm.common.job.BETeaUploadFileJob;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.model.MemoryUsage;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.jsch.JSchGroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.jsch.SshConfig;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.LockManager;
import com.tibco.cep.bemm.common.service.MBeanService;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.ValidationService;
import com.tibco.cep.bemm.common.service.impl.BEMMServiceFactoryImpl;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.tra.config.model.HostTraConfig;
import com.tibco.cep.bemm.common.tra.config.model.HostTraConfigs;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.exception.AddApplicationProfileException;
import com.tibco.cep.bemm.management.exception.BEApplicationCreateException;
import com.tibco.cep.bemm.management.exception.BEApplicationDeleteException;
import com.tibco.cep.bemm.management.exception.BEApplicationEditException;
import com.tibco.cep.bemm.management.exception.BEApplicationExportException;
import com.tibco.cep.bemm.management.exception.BEApplicationImportFailException;
import com.tibco.cep.bemm.management.exception.BEApplicationLoadException;
import com.tibco.cep.bemm.management.exception.BEApplicationNotFoundException;
import com.tibco.cep.bemm.management.exception.BEApplicationSaveException;
import com.tibco.cep.bemm.management.exception.BEApplicationTraUploadException;
import com.tibco.cep.bemm.management.exception.BEDeleteRemoteFileException;
import com.tibco.cep.bemm.management.exception.BEDownloadLogException;
import com.tibco.cep.bemm.management.exception.BEMasterHostAddException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceAddException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceDeleteException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceDeployException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceKillException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceSaveException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceStartException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceStopException;
import com.tibco.cep.bemm.management.exception.BEValidationException;
import com.tibco.cep.bemm.management.exception.CopyInstanceFailException;
import com.tibco.cep.bemm.management.exception.DeleteApplicationProfileException;
import com.tibco.cep.bemm.management.exception.DeploymentVariableStoreFailException;
import com.tibco.cep.bemm.management.exception.EditApplicationProfileException;
import com.tibco.cep.bemm.management.export.model.Agents;
import com.tibco.cep.bemm.management.export.model.Hosts;
import com.tibco.cep.bemm.management.export.model.Instance;
import com.tibco.cep.bemm.management.export.model.Instances;
import com.tibco.cep.bemm.management.export.model.ObjectFactory;
import com.tibco.cep.bemm.management.export.model.Profile;
import com.tibco.cep.bemm.management.service.BEApplicationCDDCacheService;
import com.tibco.cep.bemm.management.service.BEApplicationGVCacheService;
import com.tibco.cep.bemm.management.service.BEApplicationHostsManagementService;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.service.BEMasterHostManagementService;
import com.tibco.cep.bemm.management.service.BEServiceInstancesManagementService;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.management.util.FetchCddDataUtil;
import com.tibco.cep.bemm.management.util.GroupOperationUtil;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.management.util.MasterHostConvertor;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.AgentConfig;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.BE;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.Monitorable.ENTITY_TYPE;
import com.tibco.cep.bemm.model.ProcessingUnit;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.Summary;
import com.tibco.cep.bemm.model.impl.AbstractMonitorableEntity;
import com.tibco.cep.bemm.model.impl.AgentType;
import com.tibco.cep.bemm.model.impl.ApplicationBuilderImpl;
import com.tibco.cep.bemm.model.impl.BEMMModelFactoryImpl;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.model.impl.NameValuePair;
import com.tibco.cep.bemm.model.impl.NameValuePairs;
import com.tibco.cep.bemm.model.listener.StatusChangeListener;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorable;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.cep.bemm.persistence.service.exception.BEApplicationProfileNotExistException;
import com.tibco.cep.bemm.persistence.service.exception.BEApplicationProfileSaveException;
import com.tibco.cep.bemm.persistence.service.exception.BEHostTRASaveException;
import com.tibco.cep.bemm.persistence.topology.model.Site;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.runtime.management.Domain;
import com.tibco.cep.runtime.management.DomainKey;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.tea.agent.be.BETeaAgentManager;
import com.tibco.tea.agent.be.ui.model.GroupDeploymentVariable;
import com.tibco.tea.agent.be.util.BEAgentUtil;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

/**
 * This service is used for application level functionality.
 * 
 * @author dijadhav
 *
 */
public class BEApplicationsManagementServiceImpl extends AbstractBETeaManagementServiceImpl
		implements BEApplicationsManagementService {

	private static final String CLUSTER_TOPOLOGY_JAXB_PACKAGE = "com.tibco.cep.bemm.persistence.topology.model";
	private static final int BUFFER_SIZE = 4096;
	private Properties configuration;
	private Map<String, Application> applications;
	private BEServiceInstancesManagementService instanceService;
	// private BEApplicationsManagementDataStoreService<?> dataStoreService;
	private BEApplicationHostsManagementService hostService;
	private BEMasterHostManagementService masterHostService;
	private MessageService messageService;
	private ValidationService validationService;
	private MBeanService mbeanService;
	private LockManager lockManager;
	private BEApplicationGVCacheService<?> applicationGVCacheService;
	private BEApplicationCDDCacheService<?> applicationCddCacheService;

	private long instanceStartTimeThresold = 0;

	@Override
	public void init(Properties configuration) throws ServiceInitializationException {
		LOGGER.log(Level.DEBUG, "Initializing instance of BEApplicationsManagementService");
		try {
			// dataStoreService =
			// BEMMServiceProviderManager.getInstance().getBEApplicationsManagementDataStoreService();
			super.init(configuration);
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
			mbeanService = BEMMServiceProviderManager.getInstance().getBEMBeanService();
			validationService = BEMMServiceProviderManager.getInstance().getValidationService();
			instanceService = BEMMServiceProviderManager.getInstance().getBEServiceInstancesManagementService();
			hostService = BEMMServiceProviderManager.getInstance().getBEApplicationHostsManagementService();
			masterHostService = BEMMServiceProviderManager.getInstance().getMasterHostManagementService();
			applicationGVCacheService = BEMMServiceProviderManager.getInstance().getBEApplicationGVCacheService();
			applicationCddCacheService = BEMMServiceProviderManager.getInstance().getBEApplicationCDDCacheService();
			this.poolService = BEMMServiceProviderManager.getInstance().getGroupOpExecutorService();
			this.configuration = configuration;
			applications = new ConcurrentHashMap<String, Application>();
			loadAll();
			String instanceStartTimeThresoldStr = ConfigProperty.BE_TEA_AGENT_INSTANCE_START_TIME_THRESHOLD
					.getValue(configuration).toString();
			instanceStartTimeThresold = Long.parseLong(instanceStartTimeThresoldStr);
		} catch (NumberFormatException | ObjectCreationException | BEApplicationLoadException e) {
			throw new ServiceInitializationException(
					messageService.getMessage(MessageKey.INSTANCE_INITIALZED_BEAPPLICATIONSMANAGEMENTSERVICE_ERROR));
		}
		LOGGER.log(Level.DEBUG, "Initialized instance of BEApplicationsManagementService");
	}

	@Override
	public void registerStatusChangeListener(ENTITY_TYPE entityType, StatusChangeListener statusChangeListener) {
		AbstractMonitorableEntity.addStatusChangeListener(entityType, statusChangeListener);
	}

	@Override
	public Application getApplicationByName(String applicationName) {
		return applications.get(applicationName);
	}

	@Override
	public Application createApplication(String name, DataSource cddDataSource, DataSource earDataSource,
			String loggedInUser, StringBuilder successMessage) throws BEApplicationCreateException {

		Set<String> applicationList = new HashSet<String>();
		for (String applicationName : applications.keySet())
			applicationList.add(applicationName.toLowerCase());
		if (applicationList.contains(name.toLowerCase())) {
			throw new BEApplicationCreateException(
					messageService.getMessage(MessageKey.APPLICATION_DUPLICATE_ERROR_MESSAGE, name));
		}
		Application application = null;
		try {
			boolean isMonitorable = Boolean.valueOf(
					configuration.getProperty(ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getPropertyName(),
							ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getDefaultValue()));
			ApplicationBuilderImpl applicationBuilder = new ApplicationBuilderImpl();
			application = applicationBuilder.newApplication();
			application.setName(name);
			application.setDescription(name);
			application.setMonitorableOnly(isMonitorable);
			dataStoreService.storeApplicationTopology(application);
			ClusterConfig clusterConfig = null;
			if (!isMonitorable) {
				byte[] cddFileByteArray = ManagementUtil.getByteArrayFromStream(cddDataSource.getInputStream());
				dataStoreService.storeApplicationCDD(application.getName(), cddFileByteArray);

				byte[] earFileByteArray = ManagementUtil.getByteArrayFromStream(earDataSource.getInputStream());
				dataStoreService.storeApplicationArchive(application.getName(), earFileByteArray);

				clusterConfig = dataStoreService.fetchApplicationCDD(application.getName());
				if (null == clusterConfig) {
					dataStoreService.rollback(application.getName());
					throw new BEApplicationCreateException(
							messageService.getMessage(MessageKey.APPLICATION_CREATE_INCOMPATIBLE_CDD_ERROR));
				}
				application.setAppVersion(applicationCddCacheService.getApplicationVersion(application.getName()));


			}


			applicationBuilder.enrichClusterConfigData(application, clusterConfig);

			if (application != null)
				applications.put(application.getName(), application);

			DeploymentVariables deploymentVariables = getApplicationDeploymentConfig(0, application, isMonitorable,
					loggedInUser);
			dataStoreService.storeApplicationConfig(name, deploymentVariables);
		} catch (BEApplicationCreateException e) {
			throw e;
		} catch (Exception ex) {
			dataStoreService.rollback(application.getName());
			throw new BEApplicationCreateException(
					messageService.getMessage(MessageKey.APPLICATION_CREATE_ERROR_MESSAGE, name), ex);
		}
		successMessage.append(messageService.getMessage(MessageKey.APPLICATION_CREATE_SUCCESS_MESSAGE, name));
		return application;
	}

	@Override
	public Application importApplication(String name, DataSource stfileDataSource, DataSource cddDataSource,
			DataSource earDataSource, String loggedInUser, StringBuilder successMessage)
			throws BEApplicationImportFailException {

		Application application = null;
		try {

			byte[] stFileByteArray = ManagementUtil.getByteArrayFromStream(stfileDataSource.getInputStream());
			Site site = this.parseTopologyFile(stFileByteArray);

			if (null != name && !name.trim().isEmpty())
				site.setName(name);

			name = site.getName();
			Set<String> applicationList = new HashSet<String>();

			for (String applicationName : applications.keySet())
				applicationList.add(applicationName.toLowerCase());

			if (applicationList.contains(name.toLowerCase())) {
				throw new BEApplicationImportFailException(
						messageService.getMessage(MessageKey.APPLICATION_DUPLICATE_ERROR_MESSAGE, name));
			}
			ApplicationBuilderImpl applicationBuilder = new ApplicationBuilderImpl();
			application = applicationBuilder.newApplication();
			application.setName(name);

			byte[] cddFileByteArray = ManagementUtil.getByteArrayFromStream(cddDataSource.getInputStream());
			dataStoreService.storeApplicationCDD(application.getName(), cddFileByteArray);

			ClusterConfig clusterConfig = dataStoreService.fetchApplicationCDD(application.getName());
			if (null == clusterConfig) {
				dataStoreService.rollback(application.getName());
				throw new BEApplicationImportFailException(
						messageService.getMessage(MessageKey.APPLICATION_CREATE_INCOMPATIBLE_CDD_ERROR));
			}
			application.setAppVersion(applicationCddCacheService.getApplicationVersion(application.getName()));

			applicationBuilder.enrichTopologyAndClusterConfigData(application, site, clusterConfig, loggedInUser, true);
			Map<String, MasterHost> masterHosts = masterHostService.getAllMasterHost();
			dataStoreService.storeApplicationTopology(application);
			dataStoreService.storeMasterHostTopology(masterHosts);
			byte[] earFileByteArray = ManagementUtil.getByteArrayFromStream(earDataSource.getInputStream());
			dataStoreService.storeApplicationArchive(application.getName(), earFileByteArray);

			if (application != null)
				applications.put(application.getName(), application);

			DeploymentVariables deploymentVariables = getApplicationDeploymentConfig(1, application, false,
					loggedInUser);
			dataStoreService.storeApplicationConfig(application.getName(), deploymentVariables);
		} catch (BEApplicationImportFailException ex) {
			throw ex;
		} catch (Exception ex) {

			dataStoreService.rollback(application.getName());
			String message = messageService.getMessage(MessageKey.APPLICATION_IMPORT_ERROR_MESSAGE, name);
			if (ex instanceof BEApplicationImportFailException) {
				message = ex.getMessage();
			} else if (ex instanceof BEServiceInstanceSaveException) {
				message = message + " " + ex.getMessage();
			} else if (ex instanceof UnmarshalException) {
				message = "Failed to import the application. Reason:Unexpected xml contents. ";
			}
			throw new BEApplicationImportFailException(message, ex);
		}
		successMessage.append(messageService.getMessage(MessageKey.APPLICATION_IMPORT_SUCCESS_MESSAGE, name));
		return application;
	}

	@Override
	public String deleteApplication(String name) throws BEApplicationDeleteException {
		Application application = applications.get(name);
		if (null != application) {
			List<String> errorMessages = new ArrayList<String>();
			for (Host host : application.getHosts()) {
				for (ServiceInstance serviceInstance : host.getInstances()) {
					if (null != serviceInstance) {
						if (!BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus()
								.equals(serviceInstance.getDeploymentStatus())) {
							throw new BEApplicationDeleteException(messageService
									.getMessage(MessageKey.APPLICATION_DELETE_ALL_INSTANCE_MUST_UNDEPLOYED));
						}
						if (BETeaAgentStatus.STOPPING.getStatus().equals(serviceInstance.getStatus())
								|| BETeaAgentStatus.STARTING.getStatus().equals(serviceInstance.getStatus())
								|| BETeaAgentStatus.RUNNING.getStatus().equals(serviceInstance.getStatus())) {
							throw new BEApplicationDeleteException(
									messageService.getMessage(MessageKey.APPLICATION_DELETE_ALL_INSTANCE_MUST_STOPPED));
						}

					}
				}
			}

			if (errorMessages.isEmpty()) {
				applications.remove(name);
				// Suppressed errors because user might delete data from back
				// end.
				try {
					dataStoreService.deleteConfigData(name);
					applicationGVCacheService.removeConfiguration(name);
					applicationCddCacheService.removeConfiguration(name);
				} catch (FileNotFoundException e) {
					LOGGER.log(Level.DEBUG, e.getMessage());
				} catch (Exception e) {
					LOGGER.log(Level.DEBUG, e.getMessage());
				}
				try {
					if (BEMMServiceProviderManager.getInstance().getMetricRuleService() != null)
						BEMMServiceProviderManager.getInstance().getMetricRuleService().deleteAppRuleConifg(name);
				} catch (Exception e) {
					LOGGER.log(Level.DEBUG, e.getMessage());
				}

			}

		} else {
			throw new BEApplicationDeleteException(
					messageService.getMessage(MessageKey.APPLICATION_DOES_NOT_EXIST_ERROR_MESSAGE, name));
		}
		return messageService.getMessage(MessageKey.APPLICATION_DELETE_SUCCESS_MESSAGE, name);
	}

	@Override
	public String storeDeploymentVariables(Application application, DeploymentVariables runtimeVariables,
			String loggedInUser) throws DeploymentVariableStoreFailException, BEValidationException {
		boolean hasError = false;
		if (null == application) {
			throw new BEValidationException(messageService.getMessage(MessageKey.APPLICATION_NULL_ERROR_MESSAGE));
		}
		if (null == runtimeVariables) {
			throw new BEValidationException(
					messageService.getMessage(MessageKey.DEPLOYMENT_VARIABLES_NULL_ERROR_MESSAGE));
		}
		DeploymentVariableType type = runtimeVariables.getType();
		if (null == type) {
			throw new BEValidationException(
					messageService.getMessage(MessageKey.DEPLOYMENT_VARIABLES_NULL_ERROR_MESSAGE));
		}

		for (Host host : application.getHosts()) {
			for (ServiceInstance instance : host.getInstances()) {
				try {
					instanceService.storeDeploymentVariables(instance, runtimeVariables, loggedInUser, false);
					hasError = true;
				} catch (Exception e) {
					LOGGER.log(Level.ERROR, e, e.getMessage());
				}
			}
		}
		String message = null;
		if (!hasError) {
			switch (type) {
			case GLOBAL_VARIABLES:
				message = messageService.getMessage(MessageKey.APPLICATION_INSTANCE_SAVE_GV_SUCCESS_MESSAGE,
						application.getName());
				break;
			case SYSTEM_VARIABLES:
				message = messageService.getMessage(MessageKey.APPLICATION_INSTANCE_SAVE_SV_SUCCESS_MESSAGE,
						application.getName());
				break;
			case JVM_PROPERTIES:
				message = messageService.getMessage(MessageKey.APPLICATION_INSTANCE_SAVE_JP_SUCCESS_MESSAGE,
						application.getName());
				break;
			case APPLICATION_CONFIG:
				message = messageService.getMessage(MessageKey.APPLICATION_CONFIG_SAVE_SUCCESS_MESSAGE,
						application.getName());
				break;
			default:
				break;
			}

		} else {
			switch (type) {
			case GLOBAL_VARIABLES:
				message = messageService.getMessage(MessageKey.APPLICATION_INSTANCE_SAVE_GV_ERROR_MESSAGE,
						application.getName());
				break;
			case SYSTEM_VARIABLES:
				message = messageService.getMessage(MessageKey.APPLICATION_INSTANCE_SAVE_SV_ERROR_MESSAGE,
						application.getName());
				break;
			case JVM_PROPERTIES:
				message = messageService.getMessage(MessageKey.APPLICATION_INSTANCE_SAVE_JP_ERROR_MESSAGE,
						application.getName());
				break;
			case APPLICATION_CONFIG:
				message = messageService.getMessage(MessageKey.APPLICATION_CONFIG_SAVE_ERROR_MESSAGE,
						application.getName());
				break;
			default:
				break;
			}

		}
		return message;
	}

	@Override
	public String deploy(Application application, List<String> instances, String loggedInUser)
			throws BEServiceInstanceDeployException {
		LOGGER.log(Level.DEBUG, "Deploing the service instances");

		// Get Application service instances
		List<ServiceInstance> serviceInstances = getApplicationServiceInstances(application);

		// Deploy service instance
		return deployServiceInstances(application, instances, loggedInUser, serviceInstances, applicationGVCacheService,
				instanceService, dataStoreService);
	}

	@Override
	public String hotdeploy(Application application, DataSource earArchive, String loggedInUser, List<String> instances)
			throws BEServiceInstanceDeployException {
		List<ServiceInstance> applServiceInstances = getApplicationServiceInstances(application);
		if (null == applServiceInstances || applServiceInstances.isEmpty())
			throw new BEServiceInstanceDeployException(
					messageService.getMessage(MessageKey.APPLICATION_DOES_NOT_HAVE_INSTANCES));
		return hotDepoloy(earArchive, instances, loggedInUser, applServiceInstances, instanceService,
				dataStoreService.getTempFileLocation());
	}

	@Override
	public String hotDeployAll(Application application, DataSource earArchive, String loggedInUser)
			throws BEServiceInstanceDeployException {
		List<ServiceInstance> applServiceInstances = getApplicationServiceInstances(application);
		List<String> instances = new ArrayList<String>();
		if (null == applServiceInstances || applServiceInstances.isEmpty())
			throw new BEServiceInstanceDeployException("Application does not have instances");
		for (ServiceInstance instance : applServiceInstances) {
			instances.add(instance.getKey());
		}
		return hotDepoloy(earArchive, instances, loggedInUser, applServiceInstances, instanceService,
				dataStoreService.getTempFileLocation());
	}

	@Override
	public String undeploy(Application application, List<String> instances, String loggedInUser)
			throws BEServiceInstanceDeployException {
		// Get Application service instances
		List<ServiceInstance> serviceInstances = getApplicationServiceInstances(application);

		// Undeploy service instances
		return undeployServiceInstances(application, instances, loggedInUser, serviceInstances,
				applicationGVCacheService, instanceService, dataStoreService);
	}

	@Override
	public String start(Application application, List<String> instances, String loggedInUser)
			throws BEServiceInstanceStartException {

		List<ServiceInstance> serviceInstances = getApplicationServiceInstances(application);
		// Start the service instance
		return startServiceInstances(instances, loggedInUser, serviceInstances, instanceService);
	}

	@Override
	public String stop(Application application, List<String> instances, String loggedInUser)
			throws BEServiceInstanceStopException {
		List<ServiceInstance> serviceInstances = getApplicationServiceInstances(application);
		// Stop the service instance
		return stopServiceInstances(instances, loggedInUser, serviceInstances, instanceService);
	}

	@Override
	public Collection<ServiceInstance> getServiceInstancesByPU(String applicationName, ProcessingUnit procUnit) {
		Set<ServiceInstance> serviceInstances = new HashSet<ServiceInstance>();
		Application application = applications.get(applicationName);
		for (Host host : application.getHosts()) {
			for (ServiceInstance instance : host.getInstances()) {
				if (instance.getPuId().equals(procUnit.getPuId())) {
					serviceInstances.add(instance);
				}
			}
		}
		return serviceInstances;
	}

	@Override
	public Collection<Host> getHostsForPU(String applicationName, ProcessingUnit procUnit) {
		Set<Host> applicationHosts = new HashSet<Host>();
		Application application = applications.get(applicationName);
		for (Host host : application.getHosts()) {
			for (ServiceInstance instance : host.getInstances()) {
				if (instance.getPuId().equals(procUnit.getPuId())) {
					applicationHosts.add(host);
				}
			}
		}
		return applicationHosts;
	}

	@Override
	public Summary getApplicationSummaryByPUId(String name, String puId) {
		Summary appSummary = null;
		try {
			appSummary = BEMMModelFactoryImpl.getInstance().getSummary();
			int upInstances = 0;
			int downInstances = 0;
			int intermediateStateInstances = 0;
			Application application = applications.get(name);
			Collection<Host> hosts = application.getHosts();
			for (Host applicationHost : hosts) {
				try {
					for (ServiceInstance serviceInstance : applicationHost.getInstances()) {
						if (serviceInstance.getPuId().equals(puId)) {
							if (BETeaAgentStatus.RUNNING.getStatus().equalsIgnoreCase(serviceInstance.getStatus()))
								upInstances++;
							else if (BETeaAgentStatus.STARTING.getStatus().equalsIgnoreCase(serviceInstance.getStatus())
									|| BETeaAgentStatus.STOPPING.getStatus()
											.equalsIgnoreCase(serviceInstance.getStatus())
									|| BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus()
											.equalsIgnoreCase(serviceInstance.getStatus()))
								intermediateStateInstances++;
							else
								downInstances++;
						}
					}
				} catch (Exception e) {
					LOGGER.log(Level.DEBUG, e, e.getMessage());
				}
			}
			appSummary.setUpInstances(upInstances);
			appSummary.setDownInstances(downInstances);
			appSummary.setIntermediateStateInstances(intermediateStateInstances);
		} catch (ObjectCreationException ocex) {
			LOGGER.log(Level.ERROR, ocex, ocex.getMessage());
		}
		return appSummary;
	}

	@Override
	public String editApplication(String name, DataSource cddContents, DataSource earContents, String loggedInUser,
			boolean isCDDMentioned, boolean isEARMentioned, List<String> removedPU, List<String> removedInstanceAgent,
			List<String> instanceAgentstoBeRemoved) throws BEApplicationEditException {
		if (null == cddContents) {
			isCDDMentioned = false;
		}
		if (null == earContents) {
			isEARMentioned = false;
		}

		if (!isCDDMentioned && !isEARMentioned) {
			throw new BEApplicationEditException(messageService.getMessage(MessageKey.INVALID_CDD_EAR_ERROR_MESSAGE));

		}
		Application application = applications.get(name);
		try {

			// Validate CDD if it is mentioned
			if (isCDDMentioned) {
				if (null != cddContents) {
					byte[] cddFileByteArray = ManagementUtil.getByteArrayFromStream(cddContents.getInputStream());
					if (cddFileByteArray.length > 0) {
						if (isValidCDD(cddContents, application, removedPU, removedInstanceAgent,
								instanceAgentstoBeRemoved)) {
							dataStoreService.storeApplicationCDD(name, cddFileByteArray);
						}
					}
				}
			}
			if (isEARMentioned) {
				if (null != earContents) {
					byte[] earFileByteArray = ManagementUtil.getByteArrayFromStream(earContents.getInputStream());
					if (earFileByteArray.length > 0) {
						dataStoreService.storeApplicationArchive(name, earFileByteArray);
					}
				}
			}
			if (isCDDMentioned || isEARMentioned) {
				DeploymentVariables deploymentVariables = getApplicationDeploymentConfig(2, application, false,
						loggedInUser);
				dataStoreService.storeApplicationConfig(name, deploymentVariables);
				ClusterConfig clusterConfig = dataStoreService.fetchApplicationCDD(name);
				if (null == clusterConfig) {
					dataStoreService.rollback(application.getName());
					throw new BEApplicationEditException(
							messageService.getMessage(MessageKey.APPLICATION_EDIT_INCOMPATIBLE_CDD_ERROR));
				}
				application.setAppVersion(applicationCddCacheService.getApplicationVersion(application.getName()));
				ApplicationBuilderImpl applicationBuilder = new ApplicationBuilderImpl();
				applicationBuilder.enrichClusterConfigData(application, clusterConfig);
				updateInstanceStatus(application, loggedInUser,
						messageService.getMessage(MessageKey.APPLICATION_EDITED_MESSAGE), null, false);

				this.loadDeploymentVariables(application, true);
			}

		} catch (BEApplicationEditException e) {
			throw e;
		} catch (Exception e) {
			throw new BEApplicationEditException(
					messageService.getMessage(MessageKey.APPLICATION_EDIT_ERROR_MESSAGE, application.getName()), e);
		}
		application.incrementVersion();
		return messageService.getMessage(MessageKey.APPLICATION_EDIT_SUCCESS_MESSAGE, application.getName());

	}

	@Override
	public String createServiceInstance(Application application, String name, String processingUnit, String hostName,
			int jmxPort, String deploymentPath, boolean isPredefined, String loggedInUser, String jmxUserName,
			String jmxPassword, String beId) throws BEServiceInstanceAddException {
		try {
			MasterHost masterHost = masterHostService.getMasterHostByName(hostName);
			if (!masterHost.isAuthenticated()) {
				throw new BEServiceInstanceAddException("Host " + masterHost.getHostName() + " is not authenticated");
			}
			boolean isMonitorable = Boolean
					.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getPropertyName(),
							ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getDefaultValue()));
			LOGGER.log(Level.INFO, "Instances are monitorable only :"+isMonitorable);
			if (!isMonitorable)
				validateHost(hostName, beId, application.getAppVersion());

		
			addApplicationHost(application, hostName);
			String message = instanceService.createServiceInstance(application, name, processingUnit, hostName, jmxPort,
					deploymentPath, true, loggedInUser, jmxUserName, jmxPassword, beId);
			dataStoreService.storeApplicationTopology(application);
			BETeaAgentManager.INSTANCE.createAndRegisterTEAObjects(application);
			return message;
		} catch (BEValidationException | BEApplicationSaveException | ObjectCreationException e) {
			throw new BEServiceInstanceAddException(e.getMessage());
		}
	}

	@Override
	public Host addApplicationHost(Application application, String hostName) {
		Host host = null;
		try {
			host = getHostByHostName(application.getName(), hostName);
			if (null == host) {
				MasterHost masterHost = masterHostService.getMasterHostByName(hostName);
				if (null != masterHost) {
					host = BEMMModelFactoryImpl.getInstance().getHost();
					try {
						boolean isReachable = InetAddress.getByName(masterHost.getHostIp()).isReachable(3000);
						if (isReachable) {
							masterHost.setStatus(BETeaAgentStatus.RUNNING.getStatus());
						}
					} catch (IOException e) {
					}
					host.setMasterHost(masterHost);
					host.setApplication(application);
					application.addHost(host);
				}
			}
		} catch (BEApplicationNotFoundException | BEValidationException | ObjectCreationException e) {
			LOGGER.log(Level.DEBUG, e, e.getMessage());
		}
		return host;
	}

	@Override
	public Summary getApplicationSummary(String appName) {
		Summary appSummary = null;
		try {
			appSummary = BEMMModelFactoryImpl.getInstance().getSummary();
			int upInstances = 0;
			int downInstances = 0;
			int intermediateStateInstances = 0;
			Application application = applications.get(appName);
			Collection<Host> hosts = application.getHosts();
			for (Host applicationHost : hosts) {
				try {
					for (ServiceInstance serviceInstance : applicationHost.getInstances()) {
						if (BETeaAgentStatus.RUNNING.getStatus().equalsIgnoreCase(serviceInstance.getStatus()))
							upInstances++;
						else if (BETeaAgentStatus.STARTING.getStatus().equalsIgnoreCase(serviceInstance.getStatus())
								|| BETeaAgentStatus.STOPPING.getStatus().equalsIgnoreCase(serviceInstance.getStatus())
								|| BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus()
										.equalsIgnoreCase(serviceInstance.getStatus()))
							intermediateStateInstances++;
						else
							downInstances++;
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
			appSummary.setUpInstances(upInstances);
			appSummary.setDownInstances(downInstances);
			appSummary.setIntermediateStateInstances(intermediateStateInstances);
		} catch (ObjectCreationException ocex) {
			LOGGER.log(Level.ERROR, ocex, ocex.getMessage());
		}
		return appSummary;
	}

	@Override
	public String updateInMemoryServiceInstance(String applicationName) {
		Application application = applications.get(applicationName);
		Collection<Host> hosts = application.getHosts();
		if (null != hosts && !hosts.isEmpty()) {
			/*
			 * Check host IP is matching or not.
			 */
			for (Host host : hosts) {

				Collection<ServiceInstance> serviceInstances = host.getInstances();
				if (null != serviceInstances && !serviceInstances.isEmpty()) {
					for (ServiceInstance instance : serviceInstances) {
						if (null != host && null != instance) {
							try {
								Collection<Domain> domains = mbeanService.getDomains(instance);
								if (null != domains && !domains.isEmpty()) {
									Domain domain = (Domain) domains.toArray()[0];

									if (domain.safeGet(DomainKey.DESCRIPTION_CSV) == null)
										domain = (Domain) domains.toArray()[1];

									if (null != domain) {
										String processId = domain.safeGet(DomainKey.HOST_PROCESS_ID);
										instance.setStatus(BETeaAgentStatus.RUNNING.getStatus());
										application.setStatus(BETeaAgentStatus.RUNNING.getStatus());
										instance.setProcessId(processId.split("@")[0].trim());
										host.getMasterHost().setMachineName(processId.split("@")[1].trim());
										String cacheMode = domain.safeGet(DomainKey.DESCRIPTION_CSV);
										cacheMode = cacheMode.split("=")[1].toLowerCase();
										cacheMode = cacheMode.startsWith("cache") ? "cache" : cacheMode;

										final String[] compNames = ((FQName) domain.safeGet(DomainKey.FQ_NAME))
												.getComponentNames();

										String agentName = compNames[compNames.length - 2]; // AGENT_NAME
										String agentId = compNames[compNames.length - 1]; // AGENT_ID

										for (Agent agent : instance.getAgents()) {
											if (agent.getAgentName().equals(agentName)) {
												agent.setAgentId(Integer.parseInt(agentId));
											}
										}
									}

								}
							} catch (Exception ignore) {
								/*
								 * if an error occurred, it's handled below
								 */
							}
						}
					}
				}
			}
		}
		return "";
	}

	@Override
	public Map<String, Application> getApplications() {
		return applications;
	}

	@Override
	public Collection<AgentConfig> getAgents(String applicationName) {

		Set<AgentConfig> beAgents = new HashSet<AgentConfig>();
		Application application = applications.get(applicationName);
		for (ProcessingUnit processingUnit : application.getProcessingUnits()) {
			for (AgentConfig agent : processingUnit.getAgents()) {
				beAgents.add(agent);
			}
		}
		return beAgents;
	}

	@Override
	public List<GroupDeploymentVariable> loadDeploymentVariable(String name,
			DeploymentVariableType deploymentVariableType, List<String> instancesKey) {

		List<GroupDeploymentVariable> deploymentVariables = new ArrayList<GroupDeploymentVariable>();
		Application application = applications.get(name);
		if (null != application) {
			loadGroupDeploymentVariables(application, deploymentVariables, deploymentVariableType, instancesKey);
		}
		return deploymentVariables;
	}

	@Override
	public String storeGroupDeploymentVariable(String name, DeploymentVariableType deploymentVariableType,
			List<GroupDeploymentVariable> groupDeploymentVariables, String loggedInUser) {
		Application application = applications.get(name);
		String message = "";
		if (null != application) {
			message = saveGroupDeploymentVariable(deploymentVariableType, groupDeploymentVariables, loggedInUser,
					application, message);
		}
		return message;
	}

	/**
	 * @param deploymentVariableType
	 * @param groupDeploymentVariables
	 * @param loggedInUser
	 * @param application
	 * @param message
	 * @return
	 */
	private String saveGroupDeploymentVariable(DeploymentVariableType deploymentVariableType,
			List<GroupDeploymentVariable> groupDeploymentVariables, String loggedInUser, Application application,
			String message) {
		for (GroupDeploymentVariable groupDeploymentVariable : groupDeploymentVariables) {
			for (String instanceKey : groupDeploymentVariable.getSelectedInstances()) {
				ServiceInstance serviceInstance = getServiceInstanceByKey(instanceKey, application);
				if (null != serviceInstance) {
					if (DeploymentVariableType.GLOBAL_VARIABLES.equals(deploymentVariableType)) {
						NameValuePair nameValuePair = getInstanceMatchedNameValuePair(
								serviceInstance.getGlobalVariables(), groupDeploymentVariable.getName());
						upsertDeploymentVariable(groupDeploymentVariable, serviceInstance, nameValuePair,
								DeploymentVariableType.GLOBAL_VARIABLES);

					} else if (DeploymentVariableType.SYSTEM_VARIABLES.equals(deploymentVariableType)) {

						NameValuePair nameValuePair = getInstanceMatchedNameValuePair(
								serviceInstance.getSystemVariables(), groupDeploymentVariable.getName());
						upsertDeploymentVariable(groupDeploymentVariable, serviceInstance, nameValuePair,
								DeploymentVariableType.SYSTEM_VARIABLES);

					} else if (DeploymentVariableType.JVM_PROPERTIES.equals(deploymentVariableType)) {

						NameValuePair nameValuePair = getInstanceMatchedNameValuePair(
								serviceInstance.getJVMProperties(), groupDeploymentVariable.getName());
						upsertDeploymentVariable(groupDeploymentVariable, serviceInstance, nameValuePair,
								DeploymentVariableType.JVM_PROPERTIES);
					} else if (DeploymentVariableType.LOG_PATTERNS.equals(deploymentVariableType)) {

						NameValuePair nameValuePair = getInstanceMatchedNameValuePair(
								serviceInstance.getLoggerPatternAndLogLevel(), groupDeploymentVariable.getName());
						upsertDeploymentVariable(groupDeploymentVariable, serviceInstance, nameValuePair,
								DeploymentVariableType.LOG_PATTERNS);
					} else if (DeploymentVariableType.BE_PROPERTIES.equals(deploymentVariableType)) {

						NameValuePair nameValuePair = getInstanceMatchedNameValuePair(serviceInstance.getBEProperties(),
								groupDeploymentVariable.getName());
						upsertDeploymentVariable(groupDeploymentVariable, serviceInstance, nameValuePair,
								DeploymentVariableType.BE_PROPERTIES);
					}
				}
			}
		}
		boolean hasError = false;
		for (GroupDeploymentVariable groupDeploymentVariable : groupDeploymentVariables) {
			for (String instanceKey : groupDeploymentVariable.getSelectedInstances()) {
				ServiceInstance serviceInstance = getServiceInstanceByKey(instanceKey, application);
				if (null != serviceInstance) {
					if (DeploymentVariableType.GLOBAL_VARIABLES.equals(deploymentVariableType)) {
						try {
							instanceService.storeDeploymentVariables(serviceInstance,
									serviceInstance.getGlobalVariables(), loggedInUser, false);
						} catch (DeploymentVariableStoreFailException | BEValidationException e) {
							hasError = false;
							LOGGER.log(Level.ERROR, e, e.getMessage());
						}
					} else if (DeploymentVariableType.SYSTEM_VARIABLES.equals(deploymentVariableType)) {
						try {
							instanceService.storeDeploymentVariables(serviceInstance,
									serviceInstance.getSystemVariables(), loggedInUser, false);
						} catch (DeploymentVariableStoreFailException | BEValidationException e) {
							hasError = false;
							LOGGER.log(Level.ERROR, e, e.getMessage());
						}
					} else if (DeploymentVariableType.JVM_PROPERTIES.equals(deploymentVariableType)) {
						try {
							instanceService.storeDeploymentVariables(serviceInstance,
									serviceInstance.getJVMProperties(), loggedInUser, false);
						} catch (DeploymentVariableStoreFailException | BEValidationException e) {
							hasError = false;
							LOGGER.log(Level.ERROR, e, e.getMessage());
						}
					} else if (DeploymentVariableType.LOG_PATTERNS.equals(deploymentVariableType)) {
						try {
							instanceService.storeDeploymentVariables(serviceInstance,
									serviceInstance.getLoggerPatternAndLogLevel(), loggedInUser, false);
						} catch (DeploymentVariableStoreFailException | BEValidationException e) {
							hasError = false;
							LOGGER.log(Level.ERROR, e, e.getMessage());
						}
					} else if (DeploymentVariableType.BE_PROPERTIES.equals(deploymentVariableType)) {
						try {
							instanceService.storeDeploymentVariables(serviceInstance, serviceInstance.getBEProperties(),
									loggedInUser, false);
						} catch (DeploymentVariableStoreFailException | BEValidationException e) {
							hasError = false;
							LOGGER.log(Level.ERROR, e, e.getMessage());
						}
					}
				}
			}
		}
		if (hasError) {
			if (DeploymentVariableType.GLOBAL_VARIABLES.equals(deploymentVariableType)) {
				message = messageService.getMessage(MessageKey.APPLICATION_INSTANCE_SAVE_GV_ERROR_MESSAGE,
						application.getName());
			} else if (DeploymentVariableType.SYSTEM_VARIABLES.equals(deploymentVariableType)) {
				message = messageService.getMessage(MessageKey.APPLICATION_INSTANCE_SAVE_SV_ERROR_MESSAGE,
						application.getName());
			} else if (DeploymentVariableType.JVM_PROPERTIES.equals(deploymentVariableType)) {
				message = messageService.getMessage(MessageKey.APPLICATION_INSTANCE_SAVE_JP_ERROR_MESSAGE,
						application.getName());
			} else if (DeploymentVariableType.LOG_PATTERNS.equals(deploymentVariableType)) {
				message = messageService.getMessage(MessageKey.APPLICATION_LOG_PATTERN_LEVEL_SAVE_ERROR_MESSAGE,
						application.getName());
			} else if (DeploymentVariableType.BE_PROPERTIES.equals(deploymentVariableType)) {
				message = messageService.getMessage(MessageKey.APPLICATION_BUSINESSEVENTS_PROPERTIES_SAVE_ERROR_MESSAGE,
						application.getName());
			}
		} else {
			if (DeploymentVariableType.GLOBAL_VARIABLES.equals(deploymentVariableType)) {
				message = messageService.getMessage(MessageKey.APPLICATION_INSTANCE_SAVE_GV_SUCCESS_MESSAGE,
						application.getName());
			} else if (DeploymentVariableType.SYSTEM_VARIABLES.equals(deploymentVariableType)) {
				message = messageService.getMessage(MessageKey.APPLICATION_INSTANCE_SAVE_SV_SUCCESS_MESSAGE,
						application.getName());
			} else if (DeploymentVariableType.JVM_PROPERTIES.equals(deploymentVariableType)) {
				message = messageService.getMessage(MessageKey.APPLICATION_INSTANCE_SAVE_JP_SUCCESS_MESSAGE,
						application.getName());
			} else if (DeploymentVariableType.LOG_PATTERNS.equals(deploymentVariableType)) {
				message = messageService.getMessage(MessageKey.APPLICATION_LOG_PATTERN_LEVEL_SAVE_SUCCESS_MESSAGE,
						application.getName());
			} else if (DeploymentVariableType.BE_PROPERTIES.equals(deploymentVariableType)) {
				message = messageService.getMessage(
						MessageKey.APPLICATION_BUSINESSEVENTS_PROPERTIES_SAVE_SUCCESS_MESSAGE, application.getName());
			}
		}
		return message;
	}

	@Override
	public String getBEApplicationsCDD(String name) {
		try {
			return dataStoreService.loadApplicationCDD(name);
		} catch (ObjectCreationException e) {
			LOGGER.log(Level.ERROR, e, e.getMessage());
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e, e.getMessage());
		}
		return null;
	}

	@Override
	public Host getHostByHostIpAddress(String appName, String hostAddress)
			throws BEValidationException, BEApplicationNotFoundException {
		Host host = null;
		if (null == appName || appName.trim().isEmpty()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.INVALID_APPLICATION_NAME));
		} else if (null == hostAddress || hostAddress.trim().isEmpty()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.INVALID_HOST_IP_ADDRESS));
		}
		Application application = applications.get(appName);
		if (null == application) {
			throw new BEApplicationNotFoundException(messageService.getMessage(MessageKey.APPLICATION_NOT_FOUND_ERROR));
		}
		List<Host> applicationHosts = application.getHosts();
		if (null != applicationHosts && !applicationHosts.isEmpty()) {
			for (Host applicationHost : applicationHosts) {
				if ("localhost".equals(applicationHost.getHostIp())) {
					String ipAddress;
					try {
						ipAddress = InetAddress.getLocalHost().getHostAddress();
						if (null != applicationHost && ipAddress.equals(hostAddress)) {
							host = applicationHost;
							break;
						}
					} catch (UnknownHostException e) {
						LOGGER.log(Level.ERROR, e, e.getMessage());
					}

				}

			}
		}

		return host;
	}

	@Override
	public Host getHostByHostName(String name, String hostName)
			throws BEApplicationNotFoundException, BEValidationException {

		Host host = null;
		if (null == name || name.trim().isEmpty()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.INVALID_APPLICATION_NAME));
		} else if (null == hostName || hostName.trim().isEmpty()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.INVALID_HOST_IP_ADDRESS));
		}
		Application application = applications.get(name);
		if (null == application) {
			throw new BEApplicationNotFoundException(messageService.getMessage(MessageKey.APPLICATION_NOT_FOUND_ERROR));
		}
		List<Host> applicationHosts = application.getHosts();
		if (null != applicationHosts && !applicationHosts.isEmpty()) {
			for (Host applicationHost : applicationHosts) {
				if (null != applicationHost && hostName.equals(applicationHost.getHostName())) {
					host = applicationHost;
					break;
				}
			}
		}

		return host;

	}

	@Override
	public Map<String, OperationResult> invokeGroupOperation(String entityName, String methodGroup, String methodName,
			Map<String, String> params, List<String> instances, Application application, String loggedInUser,
			AgentConfig agentConfig) throws MBeanOperationFailException {

		List<ServiceInstance> serviceInstances = getApplicationServiceInstances(application);
		return invokeBEMMOperation(entityName, methodGroup, methodName, params, instances, serviceInstances,
				instanceService, loggedInUser, agentConfig);
	}

	private ServiceInstance getServiceInstanceByKey(String instanceKey, Application application) {
		for (Host host : application.getHosts()) {
			for (ServiceInstance serviceInstance : host.getInstances()) {
				if (null != serviceInstance && serviceInstance.getKey().equals(instanceKey)) {
					return serviceInstance;
				}
			}
		}
		return null;
	}

	/**
	 * Load group variables for given type.
	 * 
	 * @param application
	 * @param deploymentVariables
	 * @param deploymentVariableType
	 * @param instancesKey
	 */
	private void loadGroupDeploymentVariables(Application application,
			List<GroupDeploymentVariable> deploymentVariables, DeploymentVariableType deploymentVariableType,
			List<String> instancesKey) {

		GroupOperationUtil.perform(application, deploymentVariables, deploymentVariableType, instancesKey,
				instanceService);
		Collections.sort(deploymentVariables, new Comparator<GroupDeploymentVariable>() {

			@Override
			public int compare(GroupDeploymentVariable o1, GroupDeploymentVariable o2) {
				if (o1 == null && o2 != null)
					return -1;
				if (o1 != null && o2 == null)
					return 1;
				if (o1 == null && o2 == null)
					return 0;
				String name = o1.getName();
				String name2 = o2.getName();
				if (name == null && name2 != null)
					return -1;
				if (name != null && name2 == null)
					return 1;
				if (name == null && name2 == null)
					return 0;
				return name.compareToIgnoreCase(name2);
			}

		});
	}

	/**
	 * Get name value pare from given instance matching with given name value
	 * pair
	 * 
	 * @param deploymentVariables
	 * @param key
	 * @return
	 */
	private NameValuePair getInstanceMatchedNameValuePair(DeploymentVariables deploymentVariables, String name) {
		NameValuePair valuePair = null;
		if (null != deploymentVariables) {
			NameValuePairs nameValuePairs = deploymentVariables.getNameValuePairs();

			if (null != nameValuePairs) {
				List<NameValuePair> nameValuePair = nameValuePairs.getNameValuePair();
				if (null != nameValuePair && !nameValuePair.isEmpty()) {
					for (NameValuePair nameValue : nameValuePair) {
						if (nameValue.getName().trim().equals(name.trim())) {
							valuePair = nameValue;
							break;
						}
					}
				}
			}
		}
		return valuePair;
	}

	private DeploymentVariables getApplicationDeploymentConfig(int operation, Application application,
			boolean isMonitorable, String loggedInUser) {
		application.setDeploymentStatus(BETeaAgentStatus.NEED_DEPLOYMENT_OR_REDEPLOYMENT.getStatus());

		DeploymentVariables deploymentVariables = new DeploymentVariables();
		deploymentVariables.setName(application.getName());
		deploymentVariables.setType(DeploymentVariableType.APPLICATION_CONFIG);
		NameValuePairs nameValuePairs = new NameValuePairs();

		NameValuePair deploymentStatus = new NameValuePair();
		deploymentStatus.setName(Constants.DEPLOYMENT_STATUS);
		deploymentStatus.setValue(application.getDeploymentStatus());
		nameValuePairs.getNameValuePair().add(deploymentStatus);

		NameValuePair connectToCluster = new NameValuePair();
		connectToCluster.setName(Constants.CONNECT_TO_CLUSTER);
		connectToCluster.setValue(String.valueOf(application.connectToCluster()));
		nameValuePairs.getNameValuePair().add(connectToCluster);

		NameValuePair clusterListenURL = new NameValuePair();
		clusterListenURL.setName(Constants.TEA_AGENT_APP_CLUSTER_LISTEN_URL);
		if (application.getListenURL() != null)
			clusterListenURL.setValue(String.valueOf(application.getListenURL()));
		nameValuePairs.getNameValuePair().add(clusterListenURL);

		NameValuePair isMonitorableNameValue = new NameValuePair();
		connectToCluster.setName(Constants.IS_MONITORABLE_ONLY);
		connectToCluster.setValue(String.valueOf(isMonitorable));
		nameValuePairs.getNameValuePair().add(isMonitorableNameValue);
		if (null == loggedInUser || loggedInUser.trim().isEmpty()) {
			loggedInUser = application.getAuthor();
		}
		long time = new Date().getTime();
		switch (operation) {
		case 0: {
			NameValuePair description = new NameValuePair();
			description.setName(Constants.DESCRIPTION);
			description.setValue(messageService.getMessage(MessageKey.APPLICATION_CREATED_MESSAGE));
			nameValuePairs.getNameValuePair().add(description);

			NameValuePair creationTime = new NameValuePair();
			creationTime.setName(Constants.CREATION_TIME);
			creationTime.setValue(Long.toString(time));
			nameValuePairs.getNameValuePair().add(creationTime);

			deploymentVariables.setNameValuePairs(nameValuePairs);
			application.setCreationTime(time);
			application.setDeploymentDescription(messageService.getMessage(MessageKey.APPLICATION_CREATED_MESSAGE));
		}
			break;

		case 1: {
			NameValuePair description = new NameValuePair();
			description.setName(Constants.DESCRIPTION);
			description.setValue(messageService.getMessage(MessageKey.APPLICATION_IMPORTED_MESSAGE));
			nameValuePairs.getNameValuePair().add(description);

			NameValuePair creationTime = new NameValuePair();
			creationTime.setName(Constants.CREATION_TIME);
			creationTime.setValue(Long.toString(time));
			nameValuePairs.getNameValuePair().add(creationTime);
			application.setCreationTime(time);
			deploymentVariables.setNameValuePairs(nameValuePairs);

			application.setDeploymentDescription(messageService.getMessage(MessageKey.APPLICATION_IMPORTED_MESSAGE));

		}
			break;

		case 2:
			NameValuePair description = new NameValuePair();
			description.setName(Constants.DESCRIPTION);
			description.setValue(messageService.getMessage(MessageKey.APPLICATION_EDITED_MESSAGE));
			nameValuePairs.getNameValuePair().add(description);

			deploymentVariables.setNameValuePairs(nameValuePairs);
			application.setDeploymentDescription(messageService.getMessage(MessageKey.APPLICATION_EDITED_MESSAGE));
			break;

		default:
			break;
		}
		return deploymentVariables;
	}

	private boolean isValidCDD(DataSource cddContents, Application application, List<String> removedPU,
			List<String> removedPUAgent, List<String> instanceAgentstoBeRemoved)
			throws IOException, BEApplicationEditException {
		LOGGER.log(Level.DEBUG, "Validating CDD");
		boolean isValid = false;
		if (null != cddContents) {
			File temFile = File.createTempFile(application.getName() + "_" + System.currentTimeMillis(), ".cdd",
					dataStoreService.getTempFileLocation());
			ManagementUtil.streamCopy(cddContents.getInputStream(), temFile);
			ClusterConfigFactory factory = new ClusterConfigFactory();
			ClusterConfig clusterConfig = factory.newConfig(temFile.getAbsolutePath());
			Collection<ProcessingUnit> processingUnits = application.getProcessingUnits();
			List<ProcessingUnitConfig> processingUnitConfigs = null;
			if (null != clusterConfig) {
				ProcessingUnitsConfig processingUnitsConfig = clusterConfig.getProcessingUnits();
				processingUnitConfigs = processingUnitsConfig.getProcessingUnit();
			}
			if (null != processingUnits && !processingUnits.isEmpty()) {
				Iterator<ProcessingUnit> iterator = processingUnits.iterator();
				while (iterator.hasNext()) {
					ProcessingUnit processingUnit = iterator.next();
					if (null != processingUnit) {
						if (isPUMappeedToInstance(processingUnit, application)) {
							if (isPUExist(processingUnit, processingUnitConfigs, instanceAgentstoBeRemoved,
									application.getName(), true))
								isValid = true;
							else {
								throw new BEApplicationEditException(messageService
										.getMessage(MessageKey.PU_DELETE_FROM_CDD, processingUnit.getPuId()));
							}
						} else {
							if (!isPUExist(processingUnit, processingUnitConfigs, null, application.getName(), false)) {
								iterator.remove();
								removedPU.add(processingUnit.getKey());
								for (AgentConfig agentConfig : processingUnit.getAgents()) {
									if (!isAgentLinked(processingUnits, agentConfig.getTeaObjectKey(),
											processingUnit.getPuId()))
										removedPUAgent.add(agentConfig.getTeaObjectKey());
								}
							}

							isValid = true;
						}
					}
				}
			} else {
				isValid = true;
			}

		}
		return isValid;
	}

	private boolean isAgentLinked(Collection<ProcessingUnit> processingUnits, String teaObjectKey, String puId) {
		boolean isLinked = false;
		for (ProcessingUnit processingUnit : processingUnits) {
			if (null != processingUnit && !processingUnit.getPuId().equals(puId)) {
				for (AgentConfig agentConfig : processingUnit.getAgents()) {
					if (agentConfig.getTeaObjectKey().equals(teaObjectKey)) {
						isLinked = true;
						break;
					}
				}
			}
		}
		return isLinked;
	}

	private boolean isPUMappeedToInstance(ProcessingUnit processingUnit, Application application) {
		boolean isMapped = false;
		for (Host applicationHost : application.getHosts()) {
			if (null != applicationHost) {
				List<ServiceInstance> hostInstances = applicationHost.getInstances();
				if (null != hostInstances) {
					for (ServiceInstance serviceInstance : hostInstances) {
						if (null != serviceInstance && serviceInstance.getPuId().equals(processingUnit.getPuId())) {
							isMapped = true;
						}
					}
				}
			}
		}
		return isMapped;
	}

	private boolean isPUExist(ProcessingUnit processingUnit, List<ProcessingUnitConfig> processingUnitConfigs,
			List<String> instanceAgentstoBeRemoved, String applicationName, boolean checkServiceInstanceAgent)
			throws BEApplicationEditException {
		boolean isExist = false;
		LOGGER.log(Level.DEBUG,
				"Checking processing unit " + processingUnit.getPuId() + " is exist in uploaded CDD or not.");

		if (null != processingUnitConfigs) {
			for (ProcessingUnitConfig processingUnitConfig : processingUnitConfigs) {
				if (null != processingUnitConfig) {
					if (processingUnitConfig.getId().equals(processingUnit.getPuId())) {
						if (checkServiceInstanceAgent) {
							Collection<ServiceInstance> serviceInstances = getServiceInstancesByPU(applicationName,
									processingUnit);
							if (null != serviceInstances && !serviceInstances.isEmpty()) {
								for (ServiceInstance serviceInstance : serviceInstances) {
									List<Agent> agentsToBERemoved = new ArrayList<>();
									Iterator<Agent> iterator = serviceInstance.getAgents().iterator();
									while (iterator.hasNext()) {
										Agent agent = iterator.next();
										if (!isAgentExist(agent, processingUnitConfig)) {
											agentsToBERemoved.add(agent);
											instanceAgentstoBeRemoved.add(agent.getKey());
										}
									}
									serviceInstance.getAgents().removeAll(agentsToBERemoved);
								}
							}
						}
						isExist = true;
						break;
					}
				}
			}
		}
		LOGGER.log(Level.DEBUG,
				"Processing unit " + processingUnit.getPuId() + " is exist in uploaded CDD :" + isExist);

		return isExist;
	}

	private boolean isAgentExist(Agent agent, ProcessingUnitConfig processingUnitConfig) {
		LOGGER.log(Level.DEBUG, "Checking agent " + agent.getAgentName() + " from " + agent.getInstance().getName()
				+ " instance is exist in uploaded CDD or not.");
		boolean isExist = false;
		AgentsConfig agentsConfig = processingUnitConfig.getAgents();
		if (null != agentsConfig) {
			for (com.tibco.be.util.config.cdd.AgentConfig agentConfig : agentsConfig.getAgent()) {
				AgentType type = null;
				String agentName = null;
				if (null != agentConfig) {

					AgentClassConfig typeClass = agentConfig.getRef();
					agentName = typeClass.getId();
					if (typeClass instanceof InferenceAgentClassConfig) {
						type = AgentType.INFERENCE;
					} else if (typeClass instanceof QueryAgentClassConfig) {
						type = AgentType.QUERY;
					} else if (typeClass instanceof CacheAgentClassConfig) {
						type = AgentType.CACHE;
					} else if (typeClass instanceof ProcessAgentClassConfig) {
						type = AgentType.PROCESS;
					} else {
						type = AgentType.DASHBOARD;
					}
				}
				if (agent.getAgentName().equals(agentName) && agent.getAgentType().equals(type)) {
					isExist = true;
					break;
				}
			}
		}
		LOGGER.log(Level.DEBUG, "Agent " + agent.getAgentName() + " from " + agent.getInstance().getName()
				+ " instance is exist in uploaded CDD:" + isExist);

		return isExist;
	}

	private void loadAll() throws BEApplicationLoadException {
		Collection<Application> applicationTopologies = null;
		try {
			applicationTopologies = dataStoreService.fetchAllApplicationTopologies();
			for (Application applicationTopology : applicationTopologies) {
				boolean isMonitorable = Boolean.valueOf(
						configuration.getProperty(ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getPropertyName(),
								ConfigProperty.BE_TEA_AGENT_MONITORING_APPLICATION.getDefaultValue()));
				applicationTopology.setMonitorableOnly(isMonitorable);
				if (!applicationTopology.isMonitorableOnly()) {
					applicationTopology.setAppVersion(
							applicationCddCacheService.getApplicationVersion(applicationTopology.getName()));

					HostTraConfigs hostTraConfigs = dataStoreService
							.loadpplicationTraConfigs(applicationTopology.getName());
					if (null != hostTraConfigs) {
						applicationTopology.setHostTraConfigs(hostTraConfigs);
					}
					loadAllDeploymentVariable(applicationTopology);
				}

				applications.put(applicationTopology.getName(), applicationTopology);
			}
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, "Eception while LoadAll()", ex);
			throw new BEApplicationLoadException(ex);

		}
	}

	/**
	 * Get the topology from passed byte array
	 * 
	 * @param applicationTopology
	 *            - Byte array
	 * @return Site Topology
	 * @throws BEApplicationLoadException
	 */
	private Site parseTopologyFile(byte[] applicationTopology) throws BEApplicationLoadException {
		Site site = null;
		InputStream topologyFileInStream = new ByteArrayInputStream(applicationTopology);
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(CLUSTER_TOPOLOGY_JAXB_PACKAGE, this.getClass().getClassLoader());
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			site = (Site) unmarshaller.unmarshal(topologyFileInStream);
		} catch (JAXBException jbex) {
			LOGGER.log(Level.ERROR, jbex, jbex.getMessage());
		}
		return site;
	}

	@Override
	public long getInstanceStartThreshold() {
		return instanceStartTimeThresold;
	}

	@Override
	public String copyInstance(String instanceName, String processingUnit, String hostId, String deploymentPath,
			int jmxPort, ServiceInstance instance, String loggedInUser, String jmxUserName, String jmxPassword,
			String beId) throws CopyInstanceFailException, BEValidationException {
		return instanceService.copyInstance(instanceName, processingUnit, hostId, deploymentPath, jmxPort, jmxUserName,
				jmxPassword, beId, instance, loggedInUser);
	}

	/**
	 * Update the status of application instances
	 * 
	 * @param application
	 * @param loggedInUser
	 * @param description
	 * @param profileName
	 * @param isDelete
	 *            TODO
	 */
	private void updateInstanceStatus(Application application, String loggedInUser, String description,
			String profileName, boolean isDelete) {
		if (null != application) {
			DeploymentVariables instanceConfig = new DeploymentVariables();
			instanceConfig.setType(DeploymentVariableType.INSTANCE_CONFIG);
			for (Host host : application.getHosts()) {
				for (ServiceInstance instance : host.getInstances()) {

					// Set External classes directory and RTI directory is
					// mentioned on edit of application
					boolean hasInferneceAgent = false;
					boolean updateStatus = true;

					ProcessingUnit processingUnit = application.getProcessingUnit(instance.getPuId());
					if (null != processingUnit) {

						if (null != instance.getAgents()) {
							for (AgentConfig agentConfig : processingUnit.getAgents()) {
								if (null != agentConfig) {
									if (!isAgentExist(agentConfig, instance.getAgents())) {

										try {
											Agent agent = BEMMModelFactoryImpl.getInstance().getAgent();
											agent.setAgentName(agentConfig.getAgentName());
											agent.setAgentType(agentConfig.getAgentType());
											agent.setInstance(instance);
											instance.addAgent(agent);
										} catch (ObjectCreationException e) {
										}

									}
								}
							}

						}
					}
					for (Agent agentConfig : instance.getAgents()) {
						if (!hasInferneceAgent && AgentType.INFERENCE.getType()
								.equalsIgnoreCase(agentConfig.getAgentType().getType())) {
							hasInferneceAgent = true;
						}
					}
					if (null != application.getRuleTemplateDeployDir()
							&& !application.getRuleTemplateDeployDir().trim().isEmpty()) {
						instance.setRuleTemplateDeployDir(application.getRuleTemplateDeployDir().trim());
					}

					
					if (null != processingUnit) {
						Map<String, String> properties = processingUnit.getProperties();
						if (null != properties && !properties.isEmpty()) {
							Object ruleTempleDeployDir = properties
									.get(SystemProperty.CLUSTER_RULETEMPLATE_INSTANCE_DEPLOYER_DIR.getPropertyName());
							if (null != ruleTempleDeployDir && !ruleTempleDeployDir.toString().isEmpty()) {
								instance.setRuleTemplateDeployDir(ruleTempleDeployDir.toString());
							}
						}
					}

					DeploymentVariables beProps = instance.getBEProperties();
					if (beProps != null) {
						NameValuePairs bePropsPairs = beProps.getNameValuePairs();
						List<NameValuePair> bePropsNvList = bePropsPairs.getNameValuePair();
						for (NameValuePair bePropsNvPair : bePropsNvList) {
							String effectiveValue = null;
							if (bePropsNvPair.getName().equals(
									SystemProperty.CLUSTER_RULETEMPLATE_INSTANCE_DEPLOYER_DIR.getPropertyName())) {
								effectiveValue = GroupOperationUtil.getEffectiveValue(bePropsNvPair);
								if (null != effectiveValue && !effectiveValue.toString().isEmpty()) {
									instance.setRuleTemplateDeployDir(effectiveValue.toString());
								}
							} else if (bePropsNvPair.getName()
									.equals(SystemProperty.CLUSTER_EXTERNAL_CLASSES_PATH.getPropertyName())) {
								effectiveValue = GroupOperationUtil.getEffectiveValue(bePropsNvPair);
								if (null != effectiveValue && !effectiveValue.toString().isEmpty()) {
									instance.setExternalClassesPath(effectiveValue.toString());
								}
							}
						}
					}

					if (hasInferneceAgent) {
						if (null != instance.getRuleTemplateDeployDir()
								&& !instance.getRuleTemplateDeployDir().trim().isEmpty()) {
							instance.setRuleTemplateDeploy(true);
						}

						if (null != instance.getExternalClassesPath()
								&& !instance.getExternalClassesPath().trim().isEmpty()) {
							instance.setDeployClasses(true);
						}
					}

					if (null != profileName && !profileName.trim().isEmpty()) {
						if (null != instance.getDefaultProfile() && !instance.getDefaultProfile().equals(profileName))
							instance.setDefaultProfile(profileName);
						if (null != instance.getDefaultProfile() && instance.getDefaultProfile().equals(profileName)) {
							if (isDelete) {
								instance.setDefaultProfile(null);
							}
							description = messageService
									.getMessage(MessageKey.INSTANCE_DEFAULT_PROFILE_UPDATED_DESCRIPTION);
						} else if (null == instance.getDefaultProfile()
								|| instance.getDefaultProfile().trim().isEmpty()) {
							instance.setDefaultProfile(profileName);
							description = messageService
									.getMessage(MessageKey.INSTANCE_DEFAULT_PROFILE_UPDATED_DESCRIPTION);
						} else {
							updateStatus = false;
						}
					}
					if (updateStatus) {
						instance.setDeploymentDescription(description);
						if (!BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(instance.getStatus())) {
							instance.setDeploymentStatus(BETeaAgentStatus.NEEDREDEPLOYMENT.getStatus());
						} else {
							instance.setDeploymentStatus(BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus());
						}
						try {
							instanceService.storeDeploymentVariables(instance, instanceConfig, loggedInUser, true);
						} catch (DeploymentVariableStoreFailException | BEValidationException e) {
							LOGGER.log(Level.ERROR, e, e.getMessage());
						}
					}
				}
			}
		}
	}

	private boolean isAgentExist(AgentConfig agentConfig, List<Agent> agents) {
		boolean isExist = false;
		if (!agents.isEmpty()) {
			for (Agent agent : agents) {
				if (null != agent) {
					if (agent.getAgentName().equals(agentConfig.getAgentName())
							&& agent.getAgentType().equals(agentConfig.getAgentType())) {
						isExist = true;
						break;
					}
				}
			}
		}
		return isExist;
	}

	@Override
	public void setMasterHostService(BEMasterHostManagementService masterHostManagementService) {
		this.masterHostService = masterHostManagementService;
	}

	@Override
	public String groupKill(List<String> instances, Application application, String loggedInUser)
			throws BEServiceInstanceKillException, BEValidationException {
		if (null == instances || instances.isEmpty()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.PASSED_INSTANCE_LIST_EMPTY_MESSAGE));
		}
		if (null == application) {
			throw new BEValidationException(messageService.getMessage(MessageKey.PASSED_INSTANCE_LIST_EMPTY_MESSAGE));
		}

		LOGGER.log(Level.DEBUG, "Kill the service instances");

		// Get Application service instances
		List<ServiceInstance> serviceInstances = getApplicationServiceInstances(application);

		// Deploy service instance
		return killServiceInstances(application, instances, serviceInstances, loggedInUser, dataStoreService,
				instanceService);
	}

	@Override
	public Map<String, String> groupThreadDump(List<String> instances, Application application)
			throws BEValidationException {
		if (null == instances || instances.isEmpty()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.PASSED_INSTANCE_LIST_EMPTY_MESSAGE));
		}
		if (null == application) {
			throw new BEValidationException(messageService.getMessage(MessageKey.PASSED_INSTANCE_LIST_EMPTY_MESSAGE));
		}
		// Get Application service instances
		List<ServiceInstance> serviceInstances = getApplicationServiceInstances(application);
		// Get thread dump service instance
		return downloadThreadDumpServiceInstances(application, instances, serviceInstances, dataStoreService,
				instanceService);

	}

	@Override
	public DataSource groupThreadDumpZip(List<String> instances, Application application) throws BEValidationException {
		if (null == instances || instances.isEmpty()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.PASSED_INSTANCE_LIST_EMPTY_MESSAGE));
		}
		if (null == application) {
			throw new BEValidationException(messageService.getMessage(MessageKey.PASSED_INSTANCE_LIST_EMPTY_MESSAGE));
		}

		// Get Application service instances
		List<ServiceInstance> serviceInstances = getApplicationServiceInstances(application);
		// Get thread dump service instance
		Map<String, String> threaddumps = downloadThreadDumpServiceInstances(application, instances, serviceInstances,
				dataStoreService, instanceService);
		for (Host host : application.getHosts()) {
			if (null != host) {
				try {
					threaddumps.putAll(hostService.groupThreadDump(instances, host));
				} catch (BEValidationException e) {
					LOGGER.log(Level.ERROR, e, e.getMessage());
				}
			}
		}
		if (!threaddumps.isEmpty())
			return ManagementUtil.createZip(threaddumps, application.getName());
		return null;
	}

	@Override
	public Set<String> getInstanceAgents(List<String> instances, Application application) throws BEValidationException {

		if (null == instances || instances.isEmpty()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.PASSED_INSTANCE_LIST_EMPTY_MESSAGE));
		}
		if (null == application) {
			throw new BEValidationException(messageService.getMessage(MessageKey.PASSED_INSTANCE_LIST_EMPTY_MESSAGE));
		}
		Set<String> instanceAgentNames = new HashSet<String>();

		for (Host host : application.getHosts()) {
			if (null != host) {
				instanceAgentNames.addAll(hostService.getInstanceAgents(instances, host));
			}
		}
		return instanceAgentNames;
	}

	@Override
	public String applyLogPatterns(Application application, List<String> instances,
			Map<String, String> logPatternsAndLevel, String loggedInUser, boolean isIntermediateAction)
			throws BEValidationException {

		if (null == instances || instances.isEmpty()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.PASSED_INSTANCE_LIST_EMPTY_MESSAGE));
		}
		if (null == application) {
			throw new BEValidationException(messageService.getMessage(MessageKey.PASSED_INSTANCE_LIST_EMPTY_MESSAGE));
		}

		List<String> errors = new ArrayList<String>();

		for (Host host : application.getHosts()) {
			if (null != host) {
				hostService.applyLogPatterns(host, instances, logPatternsAndLevel, loggedInUser, false);
			}
		}
		return "Log Patterns are applied to all instances";

	}

	@Override
	public Map<String, String> getGroupRuntimeLoggerLevels(Application application, List<String> instances) {
		Map<String, String> groupLoggerAndLevel = new HashMap<String, String>();
		if (null != instances && !instances.isEmpty()) {
			for (Host host : application.getHosts()) {
				if (null != host) {
					try {
						Map<String, String> loggerAndLevel = hostService.getGroupRuntimeLoggerLevels(host, instances);
						if (null != loggerAndLevel && !loggerAndLevel.isEmpty()) {
							for (Entry<String, String> entry : loggerAndLevel.entrySet()) {
								if (null != entry) {
									String logger = entry.getKey();
									String level = entry.getValue();
									if (groupLoggerAndLevel.containsKey(logger)) {
										String logLevel = groupLoggerAndLevel.get(logger);
										if (!level.trim().equalsIgnoreCase(logLevel)) {
											groupLoggerAndLevel.remove(logger);
										}
									} else {
										groupLoggerAndLevel.put(logger, level);
									}

								}
							}
						}
					} catch (BEValidationException e) {
						LOGGER.log(Level.ERROR, e.getMessage());
					}
				}
			}
		}
		return null;
	}

	@Override
	public String uploadHostTra(String hostId, boolean uploadFile, String deploymentPath, DataSource uploadTRAfile,
			String traFilePath, Application application, boolean isDeleted, String loggedInUser)
			throws BEApplicationTraUploadException {

		HostTraConfigs hostTraConfigs = application.getHostTraConfigs();
		if (null == hostTraConfigs) {
			hostTraConfigs = new HostTraConfigs();
		}

		if (isDeleted) {
			HostTraConfig hostTraConfig = getHostTraConfig(hostTraConfigs.getHostTraConfig(), hostId);
			if (null != hostTraConfig) {
				hostTraConfigs.getHostTraConfig().remove(hostTraConfig);
				MasterHost host = masterHostService.getMasterHostByHostId(hostId);
				if (null != host) {
					try {
						removeHostTraFile(hostId, traFilePath, application, loggedInUser, host);
					} catch (BEDeleteRemoteFileException e) {
						LOGGER.log(Level.DEBUG, e.getMessage());
					}
				}

			}
		} else {
			uploadHostTraFile(hostId, deploymentPath, uploadTRAfile, application, loggedInUser, hostTraConfigs,
					uploadFile, traFilePath);

			if (!uploadFile) {
				HostTraConfig hostTraConfig = getHostTraConfig(hostTraConfigs.getHostTraConfig(), hostId);
				if (null != hostTraConfig) {
					hostTraConfig.setHostId(hostId);
					hostTraConfig.setTraPath(traFilePath);
				} else {
					hostTraConfig = new HostTraConfig();
					hostTraConfig.setHostId(hostId);
					hostTraConfig.setTraPath(traFilePath);
					hostTraConfigs.getHostTraConfig().add(hostTraConfig);
				}

			} else {
				HostTraConfig hostTraConfig = getHostTraConfig(hostTraConfigs.getHostTraConfig(), hostId);
				if (null == hostTraConfig) {
					hostTraConfig = new HostTraConfig();
					hostTraConfig.setHostId(hostId);
					if (!(deploymentPath.endsWith("\\") || deploymentPath.endsWith("/"))) {
						deploymentPath += "/";
					}
					hostTraConfig.setTraPath(deploymentPath + uploadTRAfile.getName());
					hostTraConfigs.getHostTraConfig().add(hostTraConfig);
				} else {
					hostTraConfig.setHostId(hostId);
					if (!(deploymentPath.endsWith("\\") || deploymentPath.endsWith("/"))) {
						deploymentPath += "/";
					}
					hostTraConfig.setTraPath(deploymentPath + uploadTRAfile.getName());
				}

			}

			application.setHostTraConfigs(hostTraConfigs);

			// Update Instance deployment status
			try {
				updateHostInstanceDeploymentStatus(hostId, application, loggedInUser);
			} catch (DeploymentVariableStoreFailException | BEValidationException e) {
				LOGGER.log(Level.ERROR, e.getMessage());
			}
		}
		return "Master Application TRA file configurtaion is updated successfully";
	}

	/**
	 * @param hostId
	 * @param deploymentPath
	 * @param uploadTRAfile
	 * @param application
	 * @param loggedInUser
	 * @param hostTraConfigs
	 * @param uploadFile
	 * @throws BEApplicationTraUploadException
	 */
	private void uploadHostTraFile(String hostId, String deploymentPath, DataSource uploadTRAfile,
			Application application, String loggedInUser, HostTraConfigs hostTraConfigs, boolean uploadFile,
			String traFilePath) throws BEApplicationTraUploadException {
		MasterHost host = masterHostService.getMasterHostByHostId(hostId);
		if (null != host) {
			if (host.isAuthenticated()) {
				try {

					// Create the JSCH client.

					String startPuMethod = BEAgentUtil.determineMethod(host.getOs());
					File repoLocation = (File) dataStoreService.getApplicationManagementDataStore();
					File hostTRAFile = new File(repoLocation + "/" + application.getName(), hostId + ".tra");
					SshConfig sshConfig = getHostSshConfig(host);
					if (uploadFile) {
						if (null == deploymentPath || deploymentPath.trim().isEmpty()) {
							throw new BEApplicationTraUploadException(
									messageService.getMessage(MessageKey.INVALID_DEPLOYMENT_PATH));
						}
						if (null == uploadTRAfile || uploadTRAfile.getInputStream().available() == 0) {
							throw new BEApplicationTraUploadException(
									messageService.getMessage(MessageKey.INVALID_TRA_FILE));
						}
						try {
							dataStoreService.storeHostTra(application.getName(), hostId,
									uploadTRAfile.getInputStream());
						} catch (BEHostTRASaveException e) {
							LOGGER.log(Level.DEBUG, e.getMessage());
						}

						List<GroupJobExecutionContext> groupJobExecutionContexts = new ArrayList<GroupJobExecutionContext>();

						groupJobExecutionContexts.add(new JSchGroupJobExecutionContext(null, sshConfig));
						String remoteFile = deploymentPath + "/" + uploadTRAfile.getName();
						if (startPuMethod.startsWith("windows")) {
							remoteFile = BEAgentUtil.getWinSshPath(remoteFile);
						}
						List<Object> results = poolService.submitJobs(new BETeaUploadFileJob(remoteFile,
								hostTRAFile.getAbsolutePath(), host.getOs(), timeout()), groupJobExecutionContexts);
						int errorCount = 0;
						if (null != results) {
							for (Object object : results) {
								if (!(object instanceof Boolean))

									errorCount++;
								else {
									boolean result = (Boolean) object;
									if (!result)
										errorCount++;
								}

							}
						}

						if (errorCount != 0)
							throw new BEApplicationTraUploadException(messageService
									.getMessage(MessageKey.HOST_UPLOAD_TRA_ERROR_MESSAGE, host.getHostName()));
					} else {
						if (!(traFilePath.endsWith(".tra") || traFilePath.endsWith(".TRA"))) {
							throw new BEApplicationTraUploadException(
									messageService.getMessage(MessageKey.NOT_TRA_FILE, traFilePath));
						}
						if (startPuMethod.startsWith("windows")) {
							traFilePath = BEAgentUtil.getWinSshPath(traFilePath);
						}

						List<GroupJobExecutionContext> groupJobExecutionContexts = new ArrayList<GroupJobExecutionContext>();

						groupJobExecutionContexts.add(new JSchGroupJobExecutionContext(null, sshConfig));
						String remoteFile = traFilePath;

						List<Object> results = poolService.submitJobs(new BETeaDownloadRemoteFileJob(remoteFile,
								hostTRAFile.getAbsolutePath(), host.getOs(), timeout()), groupJobExecutionContexts);
						int errorCount = 0;
						if (null != results) {
							for (Object object : results) {
								if (!(object instanceof Boolean))
									errorCount++;
								else {
									boolean result = (Boolean) object;
									if (!result)
										errorCount++;
								}

							}
						}
						if (errorCount != 0)
							throw new BEApplicationTraUploadException(
									messageService.getMessage(MessageKey.HOST_GET_TRA_FILE_ERROR, host.getHostName()));
					}

				} catch (IOException e) {
					LOGGER.log(Level.ERROR, e.getMessage());
					throw new BEApplicationTraUploadException(e);
				}
			} else
				LOGGER.log(Level.ERROR,
						messageService.getMessage(MessageKey.HOST_AUTHENTICATION_ERROR_MESSAGE, host.getHostName()));
		}

	}

	/**
	 * @param hostId
	 * @param deploymentPath
	 * @param application
	 * @param loggedInUser
	 * @param host
	 * @throws BEApplicationTraUploadException
	 */
	private void removeHostTraFile(String hostId, String deploymentPath, Application application, String loggedInUser,
			MasterHost host) throws BEDeleteRemoteFileException {
		LOGGER.log(Level.DEBUG, "Deleting the master tra configuration application %s for %s host",
				application.getName(), host.getHostName());

		try {
			updateHostInstanceDeploymentStatus(hostId, application, loggedInUser);
		} catch (DeploymentVariableStoreFailException | BEValidationException e) {
			LOGGER.log(Level.DEBUG, e.getMessage());
		}
		File repoLocation = (File) dataStoreService.getApplicationManagementDataStore();
		File applicationDir = new File(repoLocation, application.getName());
		File hostDir = new File(applicationDir, hostId + ".tra");
		try {
			ManagementUtil.delete(hostDir);
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.DEBUG, e.getMessage());
		}

		loadAllDeploymentVariable(application);
		LOGGER.log(Level.DEBUG, "Deleting the master tra configuration application %s for %s host",
				application.getName(), host.getHostName());
	}

	/**
	 * @param application
	 * @param hostTraConfigs
	 * @return
	 * @throws BEApplicationTraUploadException
	 */
	@Override
	public String saveHostTraConfig(Application application) throws BEApplicationTraUploadException {
		try {

			dataStoreService.storeApplicationTraConfigs(application.getName(), application.getHostTraConfigs());
			return "Master Application TRA configuration saved succefully for " + application.getName()
					+ " application";
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e.getMessage(), e);
			throw new BEApplicationTraUploadException(e);
		}
	}

	private HostTraConfig getHostTraConfig(List<HostTraConfig> hostTraConfigs, String hostId) {
		if (null != hostTraConfigs) {
			for (HostTraConfig hostTraConfig : hostTraConfigs) {
				if (null != hostTraConfig && hostTraConfig.getHostId().equals(hostId)) {
					return hostTraConfig;
				}
			}
		}
		return null;
	}

	@Override
	public void loadApplicationTraConfig(Application application) throws Exception {
		HostTraConfigs hostTraConfigs = dataStoreService.loadpplicationTraConfigs(application.getName());
		if (null != hostTraConfigs) {
			application.setHostTraConfigs(hostTraConfigs);
		}
	}

	@Override
	public DataSource downloadLogs(List<String> instances, Application application, boolean isASLog)
			throws BEDownloadLogException {
		List<ServiceInstance> filteredInstance = new ArrayList<ServiceInstance>();
		List<Host> applicationHosts = application.getHosts();
		if (null != applicationHosts && !applicationHosts.isEmpty()) {
			for (Host applicationHost : applicationHosts) {
				if (null != applicationHost) {
					List<ServiceInstance> hostInstances = applicationHost.getInstances();
					if (null != hostInstances) {
						for (ServiceInstance serviceInstance : hostInstances) {
							if (null != serviceInstance && instances.contains(serviceInstance.getKey())
									&& serviceInstance.getDeployed()) {
								filteredInstance.add(serviceInstance);
							}
						}
					}
				}
			}
		}

		String tempFile = null;
		String currentTimeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		final String fileName = application.getName() + "_" + currentTimeStamp;
		if (!filteredInstance.isEmpty()) {
			List<ServiceInstance> list = new ArrayList<>();
			Map<String, String> instanceLogLocation = new HashMap<String, String>();
			for (ServiceInstance serviceInstance : filteredInstance) {
				if (serviceInstance.getHost().getMasterHost().isAuthenticated()) {
					list.add(serviceInstance);
					Map<String, String> traProps = loadTRAProperties(serviceInstance.getHost().getApplication(),
							serviceInstance.getHost(), serviceInstance);
					instanceLogLocation = FetchCddDataUtil.getLogLocationMap(instanceLogLocation, serviceInstance,
							application.getName(), dataStoreService, traProps);

				}
			}
			if (list == null || list.isEmpty()) {
				throw new BEDownloadLogException(
						messageService.getMessage(MessageKey.APPLICATION_AUTHENTICATION_ERROR_MESSAGE));
			}

			try {
				tempFile = File.createTempFile(fileName, "_logs.zip", dataStoreService.getTempFileLocation())
						.getAbsolutePath();

			} catch (IOException e) {
				throw new BEDownloadLogException(e.getMessage(), e);
			}
			ManagementUtil.createLogZip(list, instanceLogLocation, tempFile, poolService,
					dataStoreService.getTempFileLocation(), timeout(), isASLog);
		} else {
			throw new BEDownloadLogException(
					messageService.getMessage(MessageKey.APPLICATION_AUTHENTICATION_ERROR_MESSAGE) + " or "
							+ messageService.getMessage(MessageKey.SELECTED_INSTANCE_NOT_DEPLOYED_MESSAGE));
		}

		return new FileDataSource(tempFile) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see javax.activation.FileDataSource#getName()
			 */
			@Override
			public String getName() {
				return fileName + "_logs.zip";
			}

		};
	}

	/**
	 * Load deployment variables
	 * 
	 * @param application
	 *            - Application instance
	 * @param isEdit
	 *            TODO
	 */
	private void loadDeploymentVariables(Application application, boolean isEdit) {
		Collection<GlobalVariableDescriptor> globalVariableDescriptors = null;
		try {
			globalVariableDescriptors = applicationGVCacheService.getServiceSettableGlobalVariables(
					application.getName(),
					((File) dataStoreService.getApplicationManagementDataStore()).getAbsolutePath());
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, "Failed to get global variables =");
		}
		Map<Object, Object> cddProperties = new HashMap<Object, Object>();
		ManagementUtil.loadCDDProps(application, cddProperties);

		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();

		Map<String, Map<String, String>> traPropertyMap = new HashMap<>();
		for (Host host : application.getHosts()) {
			for (BE be : host.getBE()) {
				Map<String, String> traProperties = loadTRAProperties(application, host, be.getId());
				traPropertyMap.put(host.getKey() + "_" + be.getId(), traProperties);
			}
			for (ServiceInstance instance : host.getInstances()) {

				jobExecutionContexts.add(new JSchGroupJobExecutionContext(instance, getSshConfig(instance)));
			}
		}
		List<Object> results = poolService.submitJobs(new BETeaDeploymentVariableLoadJob(globalVariableDescriptors,
				cddProperties, traPropertyMap, dataStoreService, isEdit), jobExecutionContexts);

	}

	/**
	 * Add new property or update existing
	 * 
	 * @param groupDeploymentVariable
	 *            - Group Deployment Variable
	 * @param serviceInstance
	 *            - Service instance object
	 * @param nameValuePair
	 *            -Deployment Variable
	 * @param deploymentVariableType
	 *            - Type of deployment
	 */
	private void upsertDeploymentVariable(GroupDeploymentVariable groupDeploymentVariable,
			ServiceInstance serviceInstance, NameValuePair nameValuePair,
			DeploymentVariableType deploymentVariableType) {
		if (null == nameValuePair) {
			// Add If property not exist
			instanceService.addDeploymentVariables(deploymentVariableType, serviceInstance, groupDeploymentVariable);
		} else {

			if (!groupDeploymentVariable.getDeleted() && groupDeploymentVariable.getValue() != null
					&& !groupDeploymentVariable.getValue().trim().isEmpty())
				nameValuePair.setValue(groupDeploymentVariable.getValue());

			nameValuePair.setIsDeleted(groupDeploymentVariable.getDeleted());
		}
	}

	@Override
	public void loadAllDeploymentVariable(Application application) {
		loadDeploymentVariables(application, false);
	}

	/**
	 * Load TRA properties
	 * 
	 * @param application
	 * @param matchedHost
	 * @param beId
	 * @return
	 */
	public Map<String, String> loadTRAProperties(Application application, Host matchedHost, String beId) {
		// Load TRA Properties
		InputStream hostTraStream = null;

		Map<String, String> traProperties = new HashMap<String, String>();

		if (ManagementUtil.isHostTraConfigExist(matchedHost.getHostId(), application))
			hostTraStream = dataStoreService.fetchHostTraFile(application.getName(), matchedHost.getHostId());
		else
			hostTraStream = dataStoreService.fetchHostTraFile(null, matchedHost.getHostId() + "_" + beId);

		ManagementUtil.loadTraProperties(traProperties, hostTraStream);
		return traProperties;
	}

	/**
	 * Get service instances of application
	 * 
	 * @param application
	 *            - Application object
	 * @return List of Application Service Instances
	 */
	public List<ServiceInstance> getApplicationServiceInstances(Application application) {
		List<ServiceInstance> serviceInstances = new ArrayList<>();
		Collection<Host> hosts = application.getHosts();
		if (null != hosts && !hosts.isEmpty()) {
			for (Host host : hosts) {
				if (null != host) {
					List<ServiceInstance> hostInstances = host.getInstances();
					if (null != hostInstances && !hostInstances.isEmpty())
						serviceInstances.addAll(hostInstances);
				}
			}
		}
		return serviceInstances;
	}

	@Override
	public String groupDelete(Application application, List<String> instances, String loggedInUser,
			List<String> deletedInstance) throws BEServiceInstanceDeleteException {
		List<ServiceInstance> applicationInstances = getApplicationServiceInstances(application);
		return deleteServiceInstances(applicationInstances, instances, loggedInUser, instanceService, deletedInstance);
	}

	/**
	 * Get host SSH configuration
	 * 
	 * @param host
	 * @return
	 */
	private SshConfig getHostSshConfig(MasterHost host) {
		SshConfig sshConfig = new SshConfig();
		sshConfig.setHostIp(host.getHostIp());
		sshConfig.setPassword(host.getPassword());
		sshConfig.setPort(host.getSshPort());
		sshConfig.setUserName(host.getUserName());
		return sshConfig;
	}

	/**
	 * Update instance deployment status
	 */

	private void updateHostInstanceDeploymentStatus(String hostId, Application application, String loggedInUser)
			throws DeploymentVariableStoreFailException, BEValidationException {
		LOGGER.log(Level.DEBUG, "Updating instance status");
		List<ServiceInstance> instances = getApplicationServiceInstances(application);
		if (null != instances) {
			for (ServiceInstance serviceInstance : instances) {
				if (null != serviceInstance) {
					instanceService.updateDeploymentStatus(serviceInstance,
							BETeaAgentStatus.NEED_DEPLOYMENT_OR_REDEPLOYMENT, loggedInUser);
				}
			}
		}
		LOGGER.log(Level.DEBUG, "Updated instance status");
	}

	/**
	 * @return
	 */
	private Integer timeout() {
		return Integer.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_JSCH_TIMEOUT.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_JSCH_TIMEOUT.getDefaultValue()));
	}

	/**
	 * @return
	 */
	private Integer maxRetry() {
		return Integer.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_SSH_MAX_RETRY.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_SSH_MAX_RETRY.getDefaultValue()));
	}

	/**
	 * @return
	 */
	private Integer threadSleepTime() {
		return Integer.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_SSH_SLEEP_TIME.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_SSH_SLEEP_TIME.getDefaultValue()));
	}

	@Override
	public void setLockManager(LockManager lockManager) {
		this.lockManager = lockManager;
	}

	@Override
	public LockManager getLockManager() {
		return lockManager;
	}

	@Override
	public DataSource export(Application application) throws BEApplicationExportException {
		LOGGER.log(Level.DEBUG, "Exporting the application");
		File tempFile = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;

		String currentTimeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		final String applicationExportFileName = application.getName() + "_" + currentTimeStamp;
		try {
			tempFile = File.createTempFile(applicationExportFileName, "_Export.zip",
					dataStoreService.getTempFileLocation());
			fos = new FileOutputStream(tempFile);
			zos = new ZipOutputStream(fos);
			String applicationXML = convertApplicationToExportApplicationModel(application);
			if (null != application.getProfiles()) {
				for (String profile : application.getProfiles()) {
					String profileFile = dataStoreService.getApplicationManagementDataStore() + "/"
							+ application.getName() + "/" + profile + ".properties";
					ZipEntry zipEntry = new ZipEntry(profile + ".properties");
					InputStream profileIn = new FileInputStream(profileFile);
					byte[] byteArr = ManagementUtil.getByteArrayFromStream(profileIn);
					zos.putNextEntry(zipEntry);
					zos.write(byteArr);
					zos.closeEntry();
					profileIn.close();
				}

			}
			if (null != application.getHostTraConfigs() && null != application.getHostTraConfigs().getHostTraConfig()) {

				for (HostTraConfig config : application.getHostTraConfigs().getHostTraConfig()) {
					if (null != config) {
						// Load TRA Properties
						InputStream hostTraFileStream = null;

						hostTraFileStream = dataStoreService.fetchHostTraFile(application.getName(),
								config.getHostId());

						ZipEntry zipEntry = new ZipEntry(config.getHostId() + ".tra");
						byte[] byteArr = ManagementUtil.getByteArrayFromStream(hostTraFileStream);
						zos.putNextEntry(zipEntry);
						zos.write(byteArr);
						zos.closeEntry();
						hostTraFileStream.close();
					}
				}
			}
			ZipEntry zipEntry = new ZipEntry(application.getName() + ".xml");
			InputStream applicationis = new FileInputStream(applicationXML);
			byte[] applicationXMLFileByteArray = ManagementUtil.getByteArrayFromStream(applicationis);
			zos.putNextEntry(zipEntry);
			zos.write(applicationXMLFileByteArray);
			zos.closeEntry();
			applicationis.close();
			String cdd = dataStoreService.getApplicationManagementDataStore() + "/" + application.getName() + "/"
					+ application.getName() + ".cdd";
			zipEntry = new ZipEntry(application.getName() + ".cdd");
			InputStream cddis = new FileInputStream(cdd);
			byte[] byteArr = ManagementUtil.getByteArrayFromStream(cddis);
			zos.putNextEntry(zipEntry);
			zos.write(byteArr);
			zos.closeEntry();
			cddis.close();
			String ear = dataStoreService.getApplicationManagementDataStore() + "/" + application.getName() + "/"
					+ application.getName() + ".ear";
			zipEntry = new ZipEntry(application.getName() + ".ear");
			InputStream earis = new FileInputStream(ear);
			byteArr = ManagementUtil.getByteArrayFromStream(earis);
			zos.putNextEntry(zipEntry);
			zos.write(byteArr);
			zos.closeEntry();
			earis.close();
		} catch (Exception e1) {
			LOGGER.log(Level.DEBUG, e1.getMessage());
			throw new BEApplicationExportException(
					messageService.getMessage(MessageKey.APPLICATION_EXPORT_ERROR_MESSAGE, e1.getMessage()));
		} finally {

			if (null != zos) {
				try {
					zos.close();
				} catch (IOException e) {
					LOGGER.log(Level.ERROR, e.getMessage());
				}
			}
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					LOGGER.log(Level.ERROR, e.getMessage());
				}
			}
		}

		if (null != tempFile) {
			LOGGER.log(Level.DEBUG, "Exported the application successfully");
			return new FileDataSource(tempFile) {

				/*
				 * (non-Javadoc)
				 * 
				 * @see javax.activation.FileDataSource#getName()
				 */
				@Override
				public String getName() {
					return applicationExportFileName + "_Export.zip";
				}

			};
		}
		return null;
	}

	private String convertApplicationToExportApplicationModel(Application application) throws Exception {
		ObjectFactory objectFactory = new ObjectFactory();

		com.tibco.cep.bemm.management.export.model.Application exportedApplication = objectFactory.createApplication();
		exportedApplication.setAuthor(application.getAuthor());
		exportedApplication.setCddversion(application.getCddVersion());
		exportedApplication.setClustername(application.getClusterName());
		exportedApplication.setName(application.getName());
		exportedApplication.setDefaultProfile(application.getDefaultProfile());
		if (null != application.getProfiles() && !application.getProfiles().isEmpty()) {
			com.tibco.cep.bemm.management.export.model.Profiles profiles = objectFactory.createProfiles();
			for (String profile : application.getProfiles()) {
				com.tibco.cep.bemm.management.export.model.Profile exportedProfile = objectFactory.createProfile();
				exportedProfile.setName(profile);
				profiles.getProfile().add(exportedProfile);
			}
			exportedApplication.setProfiles(profiles);
		}
		if (null != application.getHostTraConfigs() && null != application.getHostTraConfigs().getHostTraConfig()
				&& !application.getHostTraConfigs().getHostTraConfig().isEmpty()) {
			com.tibco.cep.bemm.management.export.model.HostTraConfigs hostTraConfigs = objectFactory
					.createHostTraConfigs();
			for (HostTraConfig hostTraConfig : application.getHostTraConfigs().getHostTraConfig()) {
				com.tibco.cep.bemm.management.export.model.HostTraConfig config = objectFactory.createHostTraConfig();
				config.setHostId(hostTraConfig.getHostId());
				config.setTraPath(hostTraConfig.getTraPath());
				hostTraConfigs.getHostTraConfig().add(config);
			}
			exportedApplication.setHostTraConfigs(hostTraConfigs);
		}

		List<Host> hosts = application.getHosts();
		if (null != hosts) {
			Hosts exportedHosts = objectFactory.createHosts();
			for (Host host : hosts) {
				if (null != host) {
					com.tibco.cep.bemm.management.export.model.Host exportedHost = objectFactory.createHost();
					exportedHost.getBe()
							.addAll(MasterHostConvertor.convertBEServiceModelExportImportModel(host.getBE()));
					exportedHost.setDeploymentpath(host.getMasterHost().getDeploymentPath());
					exportedHost.setIpaddress(host.getHostIp());
					exportedHost.setName(host.getHostName());
					exportedHost.setSshpassword(host.getPassword());
					exportedHost.setSshport(String.valueOf(host.getSshPort()));
					exportedHost.setSshusername(host.getUserName());
					exportedHost.setOs(host.getOs());
					List<ServiceInstance> instances = host.getInstances();
					Instances exportedInstances = objectFactory.createInstances();
					for (ServiceInstance serviceInstance : instances) {
						if (null != serviceInstance) {
							Instance instance = objectFactory.createInstance();
							instance.setDeploymentpath(serviceInstance.getDeploymentPath());
							instance.setJmxpassword(serviceInstance.getJmxPassword());
							instance.setJmxport(String.valueOf(serviceInstance.getJmxPort()));
							instance.setJmxusername(serviceInstance.getJmxUserName());
							instance.setName(serviceInstance.getName());
							instance.setBeId(serviceInstance.getBeId());
							instance.setPu(serviceInstance.getPuId());
							List<Agent> agents = serviceInstance.getAgents();
							Agents exportedAgents = objectFactory.createAgents();
							if (null != agents) {
								for (Agent agent : agents) {
									if (null != agent) {
										com.tibco.cep.bemm.management.export.model.Agent exportedAgent = objectFactory
												.createAgent();
										exportedAgent.setName(agent.getAgentName());
										exportedAgent.setType(agent.getAgentType().getType());
										exportedAgents.getAgent().add(exportedAgent);
									}
								}
							}
							instance.setAgents(exportedAgents);
							populateProperties(objectFactory, serviceInstance, instance,
									DeploymentVariableType.GLOBAL_VARIABLES);
							populateProperties(objectFactory, serviceInstance, instance,
									DeploymentVariableType.SYSTEM_VARIABLES);
							populateProperties(objectFactory, serviceInstance, instance,
									DeploymentVariableType.BE_PROPERTIES);
							populateProperties(objectFactory, serviceInstance, instance,
									DeploymentVariableType.JVM_PROPERTIES);
							populateProperties(objectFactory, serviceInstance, instance,
									DeploymentVariableType.LOG_PATTERNS);
							exportedInstances.getInstance().add(instance);
						}
					}
					exportedHost.setInstances(exportedInstances);
					exportedHosts.getHost().add(exportedHost);
				}
			}
			exportedApplication.setHosts(exportedHosts);
		}
		return dataStoreService.createApplicationXML(exportedApplication);

	}

	private void populateProperties(ObjectFactory objectFactory, ServiceInstance serviceInstance, Instance instance,
			DeploymentVariableType type) {
		DeploymentVariables deploymentVariables = null;
		switch (type) {
		case GLOBAL_VARIABLES:
			deploymentVariables = serviceInstance.getGlobalVariables();
			break;
		case SYSTEM_VARIABLES:
			deploymentVariables = serviceInstance.getSystemVariables();
			break;

		case BE_PROPERTIES:
			deploymentVariables = serviceInstance.getBEProperties();
			break;

		case JVM_PROPERTIES:
			deploymentVariables = serviceInstance.getJVMProperties();
			break;
		case LOG_PATTERNS:
			deploymentVariables = serviceInstance.getLoggerPatternAndLogLevel();
			break;

		default:
			break;
		}

		if (null != deploymentVariables) {
			com.tibco.cep.bemm.management.export.model.DeploymentVariables exportedDeploymentVariables = objectFactory
					.createDeploymentVariables();
			exportedDeploymentVariables.setType(deploymentVariables.getType().name());
			NameValuePairs nameValuePairs = deploymentVariables.getNameValuePairs();
			if (null != nameValuePairs) {
				com.tibco.cep.bemm.management.export.model.NameValuePairs pairs = objectFactory.createNameValuePairs();
				List<NameValuePair> valuePairs = nameValuePairs.getNameValuePair();
				if (null != valuePairs) {
					for (NameValuePair nameValuePair : valuePairs) {
						if (null != nameValuePair && !nameValuePair.isIsDeleted()) {
							com.tibco.cep.bemm.management.export.model.NameValuePair valuePair = objectFactory
									.createNameValuePair();
							valuePair.setName(nameValuePair.getName());
							valuePair.setDescription(nameValuePair.getDescription());
							valuePair.setDefaultValue(nameValuePair.getDefaultValue());
							valuePair.setValue(nameValuePair.getValue());
							pairs.getNameValuePair().add(valuePair);
						}
					}
				}
				exportedDeploymentVariables.setNameValuePairs(pairs);
			}
			instance.getDeploymentVariables().add(exportedDeploymentVariables);
		}
	}

	@Override
	public Application importExportedApplication(DataSource file, String loggedInUser, StringBuilder successMessage)
			throws BEApplicationImportFailException {
		Application application = null;

		try {
			byte[] zipFileByteArray = ManagementUtil.getByteArrayFromStream(file.getInputStream());
			storeZip(zipFileByteArray, file.getName());
			unzip(dataStoreService.getTempFileLocation() + "/" + file.getName(),
					dataStoreService.getTempFileLocation() + "/" + file.getName().replaceAll(".zip", "").trim());
			com.tibco.cep.bemm.management.export.model.Application importedApplication = dataStoreService
					.createApplicationFromXML(file.getName());
			if (null == importedApplication) {
				throw new BEApplicationImportFailException(
						messageService.getMessage(MessageKey.APPLICATION_IMPORT_ERROR_DESCRIPTION));
			}

			if (applications.containsKey(importedApplication.getName().trim())) {
				throw new BEApplicationImportFailException(
						"Application with " + importedApplication.getName() + " name is already exist");
			}
			application = convertImportedApplicationTopology(importedApplication, loggedInUser, file.getName());
			applications.put(application.getName(), application);
		} catch (Exception e) {

			if (e instanceof UnmarshalException) {
				throw new BEApplicationImportFailException(
						"Failed to import the application. Reason:Unexpected xml contents", e);
			}
			throw new BEApplicationImportFailException(e);
		}

		successMessage.append(messageService.getMessage(MessageKey.APPLICATION_IMPORT_SUCCESS_MESSAGE, application.getName()));
		return application;
	}

	private Application convertImportedApplicationTopology(
			com.tibco.cep.bemm.management.export.model.Application importedApplication, String loggedInUser,
			String fileName) throws Exception {
		Application application = null;
		ApplicationBuilderImpl applicationBuilder = new ApplicationBuilderImpl();
		application = applicationBuilder.newApplication();
		application.setName(importedApplication.getName());
		application.setAuthor(importedApplication.getAuthor());
		application.setCddVersion(importedApplication.getCddversion());
		application.setClusterName(importedApplication.getClustername());
		application.setDefaultProfile(importedApplication.getDefaultProfile());

		if (null != importedApplication.getProfiles() && null != importedApplication.getProfiles().getProfile()
				&& !importedApplication.getProfiles().getProfile().isEmpty()) {

			for (Profile profile : importedApplication.getProfiles().getProfile()) {
				if (null != profile && null != profile.getName() && !profile.getName().trim().isEmpty()) {
					application.getProfiles().add(profile.getName());
				}
			}
			copyProfileFiles(application.getProfiles(), fileName, application.getName());
		}
		if (null != importedApplication.getProfiles() && null != importedApplication.getProfiles().getProfile()
				&& !importedApplication.getProfiles().getProfile().isEmpty()) {

			for (Profile profile : importedApplication.getProfiles().getProfile()) {
				if (null != profile && null != profile.getName() && !profile.getName().trim().isEmpty()) {
					application.getProfiles().add(profile.getName());
				}
			}
			copyProfileFiles(application.getProfiles(), fileName, application.getName());
		}
		if (null != importedApplication.getHostTraConfigs()
				&& null != importedApplication.getHostTraConfigs().getHostTraConfig()
				&& !importedApplication.getHostTraConfigs().getHostTraConfig().isEmpty()) {
			HostTraConfigs configs = new HostTraConfigs();
			for (com.tibco.cep.bemm.management.export.model.HostTraConfig hostTraConfig : importedApplication
					.getHostTraConfigs().getHostTraConfig()) {
				if (null != hostTraConfig) {
					HostTraConfig config = new HostTraConfig();
					config.setHostId(hostTraConfig.getHostId());
					config.setTraPath(hostTraConfig.getTraPath());
					configs.getHostTraConfig().add(config);
				}
			}
			copyHostCongigTraFiles(importedApplication.getHostTraConfigs().getHostTraConfig(), fileName,
					application.getName());
			application.setHostTraConfigs(configs);
		}
		InputStream cddDataSource = new FileInputStream(getCddPath(fileName));
		byte[] cddFileByteArray = ManagementUtil.getByteArrayFromStream(cddDataSource);

		dataStoreService.storeApplicationCDD(application.getName(), cddFileByteArray);

		ClusterConfig clusterConfig = dataStoreService.fetchApplicationCDD(application.getName());
		if (null == clusterConfig) {
			dataStoreService.rollback(application.getName());
			throw new Exception(messageService.getMessage(MessageKey.INCOMPATIBLE_CDD_VERSION));
		}
		application.setAppVersion(applicationCddCacheService.getApplicationVersion(application.getName()));

		applicationBuilder.enrichTopologyAndClusterConfigData(application, null, clusterConfig, loggedInUser, false);
		InputStream earDataSource = new FileInputStream(getEARPath(fileName));

		byte[] earFileByteArray = ManagementUtil.getByteArrayFromStream(earDataSource);
		dataStoreService.storeApplicationArchive(application.getName(), earFileByteArray);

		if (application != null)
			applications.put(application.getName(), application);

		DeploymentVariables deploymentVariables = getApplicationDeploymentConfig(1, application, false, loggedInUser);
		dataStoreService.storeApplicationConfig(application.getName(), deploymentVariables);

		Hosts hosts = importedApplication.getHosts();
		if (null != hosts) {
			for (com.tibco.cep.bemm.management.export.model.Host importedHost : hosts.getHost()) {
				if (null != importedHost) {
					try {
						MasterHost masterHost = BEMMServiceFactoryImpl.getInstance().getMasterHostManagementService()
								.getMasterHostByName(importedHost.getName());
						if (null == masterHost) {
							BEMMServiceFactoryImpl.getInstance().getMasterHostManagementService()
									.addMasterHost(importedHost.getName(), importedHost.getIpaddress(),
											importedHost.getOs(),
											MasterHostConvertor.convertBEExportIportModelToBEServiceModel(
													importedHost.getBe()),
											importedHost.getSshusername(), importedHost.getSshpassword(),
											Integer.valueOf(importedHost.getSshport()),
											importedHost.getDeploymentpath(), loggedInUser);
						}
						Instances instances = importedHost.getInstances();
						if (null != instances) {
							for (Instance importedInstance : instances.getInstance()) {
								if (null != importedInstance) {
									try {
										createServiceInstance(application, importedInstance.getName(),
												importedInstance.getPu(), importedHost.getName(),
												Integer.parseInt(importedInstance.getJmxport()),
												importedInstance.getDeploymentpath(), true, loggedInUser,
												importedInstance.getJmxusername(), importedInstance.getJmxpassword(),
												importedInstance.getBeId());
										ServiceInstance serviceInstance = getServiceInstanceByKey(
												application.getKey() + "/ServiceInstance/" + importedInstance.getName(),
												application);
										BEMMServiceFactoryImpl.getInstance().getServiceInstancesManagementService()
												.storeDeploymentVariables(serviceInstance,
														getDeploymentVariables(
																importedInstance.getDeploymentVariables(),
																DeploymentVariableType.GLOBAL_VARIABLES,
																serviceInstance),
														loggedInUser, true);
										BEMMServiceFactoryImpl.getInstance().getServiceInstancesManagementService()
												.storeDeploymentVariables(serviceInstance,
														getDeploymentVariables(
																importedInstance.getDeploymentVariables(),
																DeploymentVariableType.SYSTEM_VARIABLES,
																serviceInstance),
														loggedInUser, true);
										BEMMServiceFactoryImpl.getInstance().getServiceInstancesManagementService()
												.storeDeploymentVariables(serviceInstance,
														getDeploymentVariables(
																importedInstance.getDeploymentVariables(),
																DeploymentVariableType.BE_PROPERTIES, serviceInstance),
														loggedInUser, true);
										BEMMServiceFactoryImpl.getInstance().getServiceInstancesManagementService()
												.storeDeploymentVariables(serviceInstance,
														getDeploymentVariables(
																importedInstance.getDeploymentVariables(),
																DeploymentVariableType.JVM_PROPERTIES, serviceInstance),
														loggedInUser, true);
										BEMMServiceFactoryImpl.getInstance().getServiceInstancesManagementService()
												.storeDeploymentVariables(serviceInstance,
														getDeploymentVariables(
																importedInstance.getDeploymentVariables(),
																DeploymentVariableType.LOG_PATTERNS, serviceInstance),
														loggedInUser, true);

									} catch (BEServiceInstanceAddException e) {
									}
								}
							}
						}
					} catch (NumberFormatException | BEMasterHostAddException | ObjectCreationException e) {
						throw new BEApplicationImportFailException(e);
					}
				}
			}
		}
		return application;
	}

	private void copyHostCongigTraFiles(List<com.tibco.cep.bemm.management.export.model.HostTraConfig> list,
			String name, String applicationName) {

		File applicationFolder = new File(dataStoreService.getTempFileLocation(), name.replace(".zip", "").trim());
		File[] files = applicationFolder.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith("tra");
			}
		});
		if (null != files && files.length > 0) {
			for (File file : files) {
				String fileName = file.getName().replace(".tra", "");
				if (isHostConfigExist(list, fileName)) {
					FileInputStream fin = null;
					FileOutputStream fout = null;
					try {
						File appDir = new File((File) dataStoreService.getApplicationManagementDataStore(),
								applicationName);
						if (!appDir.exists()) {
							Files.createDirectory(appDir.toPath(), new FileAttribute[0]);
						}

						File traFile = new File(appDir, file.getName());
						fin = new FileInputStream(file);
						fout = new FileOutputStream(traFile);
						int i = 0;
						while ((i = fin.read()) != -1) {
							fout.write((byte) i);
						}
						fin.close();
						fout.close();

					} catch (IOException e) {
						LOGGER.log(Level.ERROR, "Failed to copy %s file.Reason %s", file.getName(), e.getMessage());
					} finally {

						if (null != fin)
							try {
								fin.close();
							} catch (IOException e) {
							}

						if (null != fout)
							try {
								fout.flush();
								fout.close();
							} catch (IOException e) {
							}
					}
				}
			}
		}

	}

	private boolean isHostConfigExist(List<com.tibco.cep.bemm.management.export.model.HostTraConfig> list,
			String fileName) {
		boolean isExist = false;
		for (com.tibco.cep.bemm.management.export.model.HostTraConfig hostTraConfig : list) {
			if (null != hostTraConfig && fileName.equals(hostTraConfig.getHostId())) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}

	private void copyProfileFiles(Set<String> profiles, String name, String applicationName) {
		File applicationFolder = new File(dataStoreService.getTempFileLocation(), name.replace(".zip", "").trim());
		File[] files = applicationFolder.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith("properties");
			}
		});
		if (null != files && files.length > 0) {
			for (File file : files) {
				String fileName = file.getName().replace(".properties", "");
				if (profiles.contains(fileName)) {
					BufferedReader bufferedReader = null;
					BufferedWriter bufferedWriter = null;
					try {
						bufferedReader = new BufferedReader(new FileReader(file));
						File appDir = new File((File) dataStoreService.getApplicationManagementDataStore(),
								applicationName);
						if (!appDir.exists()) {
							Files.createDirectory(appDir.toPath(), new FileAttribute[0]);
						}
						File profileFile = new File(appDir, file.getName());
						profileFile.createNewFile();

						bufferedWriter = new BufferedWriter(new FileWriter(profileFile));
						String line = null;
						while (null != (line = bufferedReader.readLine()))
							bufferedWriter.write(line);
					} catch (IOException e) {
						LOGGER.log(Level.ERROR, "Failed to copy %s file.Reason %s", file.getName(), e.getMessage());
					} finally {
						if (null != bufferedReader)
							try {
								bufferedReader.close();
							} catch (IOException e) {
							}

						if (null != bufferedWriter)
							try {
								bufferedWriter.flush();
								bufferedWriter.close();
							} catch (IOException e) {
							}
					}
				}
			}
		}
	}

	private DeploymentVariables getDeploymentVariables(
			List<com.tibco.cep.bemm.management.export.model.DeploymentVariables> deploymentVariables,
			DeploymentVariableType type, ServiceInstance serviceInstance) {
		DeploymentVariables variables = null;
		com.tibco.cep.bemm.model.impl.ObjectFactory objectFactory = new com.tibco.cep.bemm.model.impl.ObjectFactory();
		if (null != deploymentVariables) {
			switch (type) {
			case GLOBAL_VARIABLES:
				variables = objectFactory.createDeploymentVariables();

				break;
			case SYSTEM_VARIABLES:
				variables = objectFactory.createDeploymentVariables();
				break;

			case BE_PROPERTIES:
				variables = objectFactory.createDeploymentVariables();
				break;

			case JVM_PROPERTIES:
				variables = objectFactory.createDeploymentVariables();
				break;
			case LOG_PATTERNS:
				variables = objectFactory.createDeploymentVariables();
				break;

			default:
				break;
			}
			variables.setType(type);
			variables.setName(serviceInstance.getName());
			variables.setKey(serviceInstance.getKey() + "/" + type.name());
			for (com.tibco.cep.bemm.management.export.model.DeploymentVariables importedDeploymentVariables : deploymentVariables) {
				if (null != importedDeploymentVariables) {
					if (importedDeploymentVariables.getType().equals(type.name())) {

						com.tibco.cep.bemm.management.export.model.NameValuePairs nameValuePairs = importedDeploymentVariables
								.getNameValuePairs();
						if (null != nameValuePairs) {
							NameValuePairs pairs = objectFactory.createNameValuePairs();
							List<com.tibco.cep.bemm.management.export.model.NameValuePair> valuePairs = nameValuePairs
									.getNameValuePair();
							if (null != valuePairs) {
								for (com.tibco.cep.bemm.management.export.model.NameValuePair nameValuePair : valuePairs) {
									if (null != nameValuePair) {
										NameValuePair valuePair = objectFactory.createNameValuePair();
										valuePair.setName(nameValuePair.getName());
										valuePair.setDescription(nameValuePair.getDescription());
										valuePair.setDefaultValue(nameValuePair.getDefaultValue());
										if (null != nameValuePair.getDefaultValue()
												&& nameValuePair.getDefaultValue().trim().isEmpty()) {
											valuePair.setHasDefaultValue(true);
										}
										valuePair.setIsDeleted(false);
										valuePair.setValue(nameValuePair.getValue());
										pairs.getNameValuePair().add(valuePair);
									}
								}
							}
							variables.setNameValuePairs(pairs);
						}
					}

				}
			}

		}

		return variables;
	}

	private String getCddPath(String name) {
		File applicationFolder = new File(dataStoreService.getTempFileLocation(), name.replace(".zip", "").trim());
		File[] files = applicationFolder.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith("cdd");
			}
		});
		if (null != files && files.length > 0) {
			return files[0].getAbsolutePath();
		}
		return null;
	}

	private String getEARPath(String name) {
		File applicationFolder = new File(dataStoreService.getTempFileLocation(), name.replace(".zip", "").trim());
		File[] files = applicationFolder.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith("ear");
			}
		});
		if (null != files && files.length > 0) {
			return files[0].getAbsolutePath();
		}
		return null;
	}

	public void unzip(String zipFilePath, String destDirectory) throws IOException {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = zipIn.getNextEntry();

		while (entry != null) {
			String filePath = destDirectory + File.separator + entry.getName();
			extractFile(zipIn, filePath);
			entry = zipIn.getNextEntry();
		}

		zipIn.close();
	}

	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}

	public void storeZip(byte[] applicationArchive, String zipName) throws BEApplicationSaveException {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(applicationArchive);
			File file = new File(dataStoreService.getTempFileLocation(), zipName);
			ManagementUtil.streamCopy(bais, file);
		} catch (IOException ex) {
			throw new BEApplicationSaveException(ex);
		} finally {

			if (null != bais) {
				try {
					bais.close();
				} catch (IOException ex) {
					// LOGGER.log(Level.ERROR, ex, ex.getMessage());
				}
			}

		}
	}

	@Override
	public String fireTailCommand(String numberofLines, boolean isASLog, ServiceInstance instance) {
		return super.fireTailCommand(numberofLines, isASLog, instance, this.instanceService);
	}

	@Override
	public String addProfile(String profileName, Map<String, String> globalVariables,
			Map<String, String> systemProperties, Map<String, String> beProperties, Application application)
			throws AddApplicationProfileException {
		if (null == profileName || profileName.trim().isEmpty()) {
			throw new AddApplicationProfileException(
					messageService.getMessage(MessageKey.INVALID_PROFILE_NAME_MESSAGE));
		}
		Set<String> profileList = new HashSet<String>();
		for (String profile : application.getProfiles()) {
			profileList.add(profile.toLowerCase());
		}
		if (profileList.contains(profileName.toLowerCase())) {
			throw new AddApplicationProfileException(messageService.getMessage(MessageKey.PROFILE_ALREADY_EXIST));
		}

		try {
			dataStoreService.storeApplicationProfile(application, profileName, globalVariables, systemProperties,
					beProperties);
			application.getProfiles().add(profileName);
			return "Application profile is added successfully";
		} catch (BEApplicationProfileSaveException e) {
			throw new AddApplicationProfileException(
					messageService.getMessage(MessageKey.APPLICATION_SAVE_PROFILE_ERROR));
		}

	}

	@Override
	public String setDefaultProfile(String profileName, Application application) throws Exception {

		DeploymentVariables deploymentVariables = getApplicationDeploymentConfig(1, application, false, null);
		NameValuePair defaultProfile = new NameValuePair();
		defaultProfile.setName("defaultProfile");
		defaultProfile.setValue(profileName);
		deploymentVariables.getNameValuePairs().getNameValuePair().add(defaultProfile);
		dataStoreService.storeApplicationConfig(application.getName(), deploymentVariables);
		updateInstanceStatus(application, "", messageService.getMessage(MessageKey.APPLICATION_DEFAULT_PROFILE_UPDATED),
				profileName, false);
		application.setDefaultProfile(profileName);
		return "Application Default profile is updated successfully";

	}

	@Override
	public String editProfile(String profileName, Map<String, String> globalVariables,
			Map<String, String> systemProperties, Map<String, String> beProperties, Application application)
			throws EditApplicationProfileException {
		if (null == profileName || profileName.trim().isEmpty()) {
			throw new EditApplicationProfileException(
					messageService.getMessage(MessageKey.INVALID_PROFILE_NAME_MESSAGE));
		}

		try {
			dataStoreService.storeApplicationProfile(application, profileName, globalVariables, systemProperties,
					beProperties);
			updateInstanceStatus(application, "", messageService.getMessage(MessageKey.APPLICATION_PROFILE_UPDATED),
					profileName, false);
			return "Application profile is updated successfully";
		} catch (BEApplicationProfileSaveException e) {
			throw new EditApplicationProfileException(
					messageService.getMessage(MessageKey.APPLICATION_SAVE_PROFILE_ERROR));
		}
	}

	@Override
	public Map<String, Map<String, String>> getApplicationProfileDetails(String profileName, Application application)
			throws BEApplicationProfileNotExistException {
		return dataStoreService.loadProfile(application.getName(), profileName);
	}

	@Override
	public String deleteApplicationProfile(String profileName, Application application)
			throws DeleteApplicationProfileException {
		if (null == profileName || profileName.trim().isEmpty()) {
			throw new DeleteApplicationProfileException(
					messageService.getMessage(MessageKey.INVALID_PROFILE_NAME_MESSAGE));
		}
		File profileFile = new File((File) dataStoreService.getApplicationManagementDataStore(),
				application.getName() + "/" + profileName + ".properties");
		if (profileFile.exists())
			profileFile.delete();

		if (null != application.getDefaultProfile() && application.getDefaultProfile().equals(profileName.trim())) {
			DeploymentVariables deploymentVariables = getApplicationDeploymentConfig(1, application, false, "");
			NameValuePair defaultProfile = new NameValuePair();
			defaultProfile.setName("defaultProfile");
			defaultProfile.setValue("");
			deploymentVariables.getNameValuePairs().getNameValuePair().add(defaultProfile);
			try {
				dataStoreService.storeApplicationConfig(application.getName(), deploymentVariables);
				application.setDefaultProfile("");
				application.setBEProperties(null);
				application.setGlobalVariables(null);
				application.setSystemProperties(null);
				application.getProfiles().remove(profileName);
			} catch (Exception e) {
			}
			updateInstanceStatus(application, "", messageService.getMessage(MessageKey.APPLICATION_PROFILE_DELETED),
					profileName, true);
		}
		application.getProfiles().remove(profileName);
		return "Application profiles deleted successfully";
	}

	@Override
	public OperationResult getGarbageCollectionDetails(BeTeaAgentMonitorable beagent)
			throws MBeanOperationFailException {
		return mbeanService.getBeAgentGarbageCollectionDetails(beagent);
	}

	@Override
	public List<String> getMemoryPools() throws MBeanOperationFailException {
		return mbeanService.getBeAgentMemoryPools();
	}

	@Override
	public MemoryUsage getMemoryByPoolName(String poolName, BeTeaAgentMonitorable agent)
			throws MBeanOperationFailException {
		return mbeanService.getBeAgentMemoryByPoolName(poolName, agent);
	}

	@Override
	public Map<String, String> getApplicationGVNameAndType(Application application) {
		Map<String, String> appGVTypeMap = new HashMap<>();
		Collection<GlobalVariableDescriptor> globalVariableDescriptors = null;
		try {
			globalVariableDescriptors = applicationGVCacheService.getServiceSettableGlobalVariables(
					application.getName(),
					((File) dataStoreService.getApplicationManagementDataStore()).getAbsolutePath());
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, "Failed to get global variables =");
		}
		if (null != globalVariableDescriptors) {
			for (GlobalVariableDescriptor globalVariableDescriptor : globalVariableDescriptors) {
				if (null != globalVariableDescriptor) {
					appGVTypeMap.put(globalVariableDescriptor.getName(), globalVariableDescriptor.getType());
				}
			}
		}
		return appGVTypeMap;
	}

	@Override
	public String copyProfile(String newProfileName, String oldProfileName, Application application)
			throws AddApplicationProfileException {

		if (null == newProfileName || newProfileName.trim().isEmpty()) {
			throw new AddApplicationProfileException(
					messageService.getMessage(MessageKey.INVALID_PROFILE_NAME_MESSAGE));
		}
		Set<String> profileList = new HashSet<String>();
		for (String profile : application.getProfiles()) {
			profileList.add(profile.toLowerCase());
		}
		if (profileList.contains(newProfileName.toLowerCase())) {
			throw new AddApplicationProfileException(messageService.getMessage(MessageKey.PROFILE_ALREADY_EXIST));
		}

		dataStoreService.copyApplicationProfile(application, newProfileName, oldProfileName);
		application.getProfiles().add(newProfileName);
		return "Application profile is added successfully";

	}
}
