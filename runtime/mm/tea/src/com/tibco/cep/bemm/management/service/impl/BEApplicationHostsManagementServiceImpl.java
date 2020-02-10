package com.tibco.cep.bemm.management.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataSource;
import javax.activation.FileDataSource;

import com.tibco.cep.bemm.common.exception.MBeanOperationFailException;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.model.impl.LogDetailImpl;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.LockManager;
import com.tibco.cep.bemm.common.service.MBeanService;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.ValidationService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.exception.BEApplicationLoadException;
import com.tibco.cep.bemm.management.exception.BEDownloadLogException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceDeleteException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceDeployException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceKillException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceStartException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceStopException;
import com.tibco.cep.bemm.management.exception.BEValidationException;
import com.tibco.cep.bemm.management.exception.CopyInstanceFailException;
import com.tibco.cep.bemm.management.exception.DeploymentVariableStoreFailException;
import com.tibco.cep.bemm.management.service.BEApplicationGVCacheService;
import com.tibco.cep.bemm.management.service.BEApplicationHostsManagementService;
import com.tibco.cep.bemm.management.service.BEServiceInstancesManagementService;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.management.util.FetchCddDataUtil;
import com.tibco.cep.bemm.management.util.GroupOperationUtil;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.BEMMModelFactory;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.Summary;
import com.tibco.cep.bemm.model.impl.BEMMModelFactoryImpl;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.model.impl.NameValuePair;
import com.tibco.cep.bemm.model.impl.NameValuePairs;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.tea.agent.be.ui.model.GroupDeploymentVariable;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

public class BEApplicationHostsManagementServiceImpl extends AbstractBETeaManagementServiceImpl
		implements BEApplicationHostsManagementService {

	private BEMMModelFactory agentModelFactory = BEMMModelFactoryImpl.getInstance();
	private Properties configuration;
	//private BEApplicationsManagementDataStoreService<?> dataStoreService;
	private BEServiceInstancesManagementService instanceService;
	private MessageService messageService;
	private ValidationService validationService;
	private MBeanService mbeanService;
	private LockManager lockManager;
	private BEApplicationGVCacheService<?> applicationGVCacheService;

	/**
	 * @return the messageService
	 */
	@Override
	public MessageService getMessageService() {
		return messageService;
	}

	/**
	 * @param messageService
	 *            the messageService to set
	 */
	@Override
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * @return the validationService
	 */
	@Override
	public ValidationService getValidationService() {
		return validationService;
	}

	/**
	 * @param validationService
	 *            the validationService to set
	 */
	@Override
	public void setValidationService(ValidationService validationService) {
		this.validationService = validationService;
	}

	/**
	 * @return the dataStoreService
	 */
	@Override
	public BEApplicationsManagementDataStoreService<?> getDataStoreService() {
		return dataStoreService;
	}

	/**
	 * @param dataStoreService
	 *            the dataStoreService to set
	 */
	@Override
	public void setDataStoreService(BEApplicationsManagementDataStoreService<?> dataStoreService) {
		this.dataStoreService = dataStoreService;
	}

	/**
	 * @return the instanceService
	 */
	@Override
	public BEServiceInstancesManagementService getInstanceService() {
		return instanceService;
	}

	/**
	 * @param instanceService
	 *            the instanceService to set
	 */
	@Override
	public void setInstanceService(BEServiceInstancesManagementService instanceService) {
		this.instanceService = instanceService;
	}

	/**
	 * @return the mbeanService
	 */
	@Override
	public MBeanService getMbeanService() {
		return mbeanService;
	}

	/**
	 * @param mbeanService
	 *            the mbeanService to set
	 */
	@Override
	public void setMbeanService(MBeanService mbeanService) {
		this.mbeanService = mbeanService;
	}

	@Override
	public void init(Properties configuration) throws ServiceInitializationException {
		
		try{
			super.init(configuration);
			this.configuration = configuration;
	
			dataStoreService = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementDataStoreService();
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
			mbeanService = BEMMServiceProviderManager.getInstance().getBEMBeanService();
			validationService = BEMMServiceProviderManager.getInstance().getValidationService();
			instanceService = BEMMServiceProviderManager.getInstance().getBEServiceInstancesManagementService();
			this.poolService = BEMMServiceProviderManager.getInstance().getGroupOpExecutorService();
			applicationGVCacheService = BEMMServiceProviderManager.getInstance().getBEApplicationGVCacheService();
		}catch (NumberFormatException | ObjectCreationException e) {
			throw new ServiceInitializationException(
					messageService.getMessage(MessageKey.INSTANCE_INITIALZED_BEAPPLICATIONSMANAGEMENTSERVICE_ERROR));
		}
	}

	@Override
	public String deploy(Host host, List<String> serviceInstances, String loggedInUser)
			throws BEServiceInstanceDeployException {

		if (!host.getMasterHost().isAuthenticated()) {
			throw new BEServiceInstanceDeployException(
					messageService.getMessage(MessageKey.HOST_AUTHENTICATION_ERROR_MESSAGE, host.getHostName()));
		}
		List<ServiceInstance> instances = host.getInstances();

		// Deploy service instance
		return deployServiceInstances(host.getApplication(), serviceInstances, loggedInUser, instances,
				applicationGVCacheService, instanceService, dataStoreService);
	}

	@Override
	public String undeploy(Host host, List<String> instances, String loggedInUser)
			throws BEServiceInstanceDeployException {
		if (!host.getMasterHost().isAuthenticated()) {
			throw new BEServiceInstanceDeployException(
					messageService.getMessage(MessageKey.HOST_AUTHENTICATION_ERROR_MESSAGE, host.getHostName()));
		}
		List<ServiceInstance> serviceInstances = host.getInstances();
		// Undeploy service instances
		return undeployServiceInstances(host.getApplication(), instances, loggedInUser, serviceInstances,
				applicationGVCacheService, instanceService, dataStoreService);
	}

	@Override
	public String start(Host host, List<String> instances, String loggedInUser) throws BEServiceInstanceStartException {
		List<ServiceInstance> serviceInstances = host.getInstances();
		// Start the service instance
		return startServiceInstances(instances, loggedInUser, serviceInstances, instanceService);
	}

	@Override
	public String stop(Host host, List<String> instances, String loggedInUser) throws BEServiceInstanceStopException {
		List<ServiceInstance> serviceInstances = host.getInstances();
		// Stop the service instance
		return stopServiceInstances(instances, loggedInUser, serviceInstances, instanceService);
	}

	@Override
	public Summary getHostSummary(Host host) {
		Summary hostSummary = null;
		try {
			hostSummary = agentModelFactory.getSummary();
			int upInstances = 0;
			int downInstances = 0;
			int intermediateStateInstances = 0;
			Application application = host.getApplication();
			Collection<Host> hosts = application.getHosts();

			for (Host applicationHost : hosts) {
				if (applicationHost.getHostId().equals(host.getHostId())) {
					try {
						for (ServiceInstance serviceInstance : applicationHost.getInstances()) {
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
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
			}
			hostSummary.setUpInstances(upInstances);
			hostSummary.setDownInstances(downInstances);
			hostSummary.setIntermediateStateInstances(intermediateStateInstances);
		} catch (ObjectCreationException e) {
			LOGGER.log(Level.ERROR, e, e.getMessage());
		}
		return hostSummary;

	}

	@Override
	public String hotdeploy(Host host, DataSource earArchive, List<String> instancesList, String loggedInUser)
			throws BEServiceInstanceDeployException {
		if (host.getMasterHost().isAuthenticated()) {
			throw new BEServiceInstanceDeployException(
					messageService.getMessage(MessageKey.HOST_AUTHENTICATION_ERROR_MESSAGE, host.getHostName()));
		}

		return hotDepoloy(earArchive, instancesList, loggedInUser, host.getInstances(), instanceService,
				dataStoreService.getTempFileLocation());
	}

	@Override
	public List<GroupDeploymentVariable> loadDeploymentVariable(Host host,
			DeploymentVariableType deploymentVariableType, List<String> instancesKey) {
		List<GroupDeploymentVariable> deploymentVariables = new ArrayList<GroupDeploymentVariable>();
		if (null != host) {
			loadGroupDeploymentVariables(host, deploymentVariables, deploymentVariableType, instancesKey);
		}

		return deploymentVariables;
	}

	@Override
	public String saveDeploymentVariable(Host host, DeploymentVariableType deploymentVariableType,
			List<GroupDeploymentVariable> groupDeploymentVariables, String logedInUser)
			throws DeploymentVariableStoreFailException {

		for (GroupDeploymentVariable groupDeploymentVariable : groupDeploymentVariables) {
			for (String instanceKey : groupDeploymentVariable.getSelectedInstances()) {
				ServiceInstance serviceInstance = getserviceInstanceByKey(instanceKey, host);
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
				ServiceInstance serviceInstance = getserviceInstanceByKey(instanceKey, host);
				if (null != serviceInstance) {
					if (DeploymentVariableType.GLOBAL_VARIABLES.equals(deploymentVariableType)) {
						try {
							instanceService.storeDeploymentVariables(serviceInstance,
									serviceInstance.getGlobalVariables(), logedInUser, false);
						} catch (DeploymentVariableStoreFailException | BEValidationException e) {
							hasError = true;
							LOGGER.log(Level.ERROR, e, e.getMessage());
						}
					} else if (DeploymentVariableType.SYSTEM_VARIABLES.equals(deploymentVariableType)) {
						try {
							instanceService.storeDeploymentVariables(serviceInstance,
									serviceInstance.getSystemVariables(), logedInUser, false);
						} catch (DeploymentVariableStoreFailException | BEValidationException e) {
							hasError = true;
							LOGGER.log(Level.ERROR, e, e.getMessage());
						}
					} else if (DeploymentVariableType.JVM_PROPERTIES.equals(deploymentVariableType)) {
						try {
							instanceService.storeDeploymentVariables(serviceInstance,
									serviceInstance.getJVMProperties(), logedInUser, false);
						} catch (DeploymentVariableStoreFailException | BEValidationException e) {
							hasError = true;
							LOGGER.log(Level.ERROR, e, e.getMessage());
						}
					} else if (DeploymentVariableType.LOG_PATTERNS.equals(deploymentVariableType)) {
						try {
							instanceService.storeDeploymentVariables(serviceInstance,
									serviceInstance.getLoggerPatternAndLogLevel(), logedInUser, false);
						} catch (DeploymentVariableStoreFailException | BEValidationException e) {
							hasError = true;
							LOGGER.log(Level.ERROR, e, e.getMessage());
						}
					} else if (DeploymentVariableType.BE_PROPERTIES.equals(deploymentVariableType)) {
						try {
							instanceService.storeDeploymentVariables(serviceInstance, serviceInstance.getBEProperties(),
									logedInUser, false);
						} catch (DeploymentVariableStoreFailException | BEValidationException e) {
							hasError = true;
							LOGGER.log(Level.ERROR, e, e.getMessage());
						}
					}
				}
			}
		}
		String message = "";
		if (hasError) {
			if (DeploymentVariableType.GLOBAL_VARIABLES.equals(deploymentVariableType)) {
				throw new DeploymentVariableStoreFailException(
						messageService.getMessage(MessageKey.HOST_INSTANCE_SAVE_GV_ERROR_MESSAGE, host.getHostName()));
			} else if (DeploymentVariableType.SYSTEM_VARIABLES.equals(deploymentVariableType)) {
				throw new DeploymentVariableStoreFailException(
						messageService.getMessage(MessageKey.HOST_INSTANCE_SAVE_SV_ERROR_MESSAGE, host.getHostName()));
			} else if (DeploymentVariableType.JVM_PROPERTIES.equals(deploymentVariableType)) {
				throw new DeploymentVariableStoreFailException(
						messageService.getMessage(MessageKey.HOST_INSTANCE_SAVE_JP_ERROR_MESSAGE, host.getHostName()));
			} else if (DeploymentVariableType.LOG_PATTERNS.equals(deploymentVariableType)) {
				throw new DeploymentVariableStoreFailException(messageService
						.getMessage(MessageKey.HOST_LOG_PATTERN_LEVEL_SAVE_SUCCESS_MESSAGE, host.getHostName()));
			} else if (DeploymentVariableType.BE_PROPERTIES.equals(deploymentVariableType)) {
				throw new DeploymentVariableStoreFailException(messageService.getMessage(
						MessageKey.HOST_BUSINESSEVENTS_PROPERTIES_SAVE_SUCCESS_MESSAGE, host.getHostName()));
			}
		} else {
			if (DeploymentVariableType.GLOBAL_VARIABLES.equals(deploymentVariableType)) {
				message = messageService.getMessage(MessageKey.HOST_INSTANCE_SAVE_GV_SUCCESS_MESSAGE,
						host.getHostName());
			} else if (DeploymentVariableType.SYSTEM_VARIABLES.equals(deploymentVariableType)) {
				message = messageService.getMessage(MessageKey.HOST_INSTANCE_SAVE_SV_SUCCESS_MESSAGE,
						host.getHostName());
			} else if (DeploymentVariableType.JVM_PROPERTIES.equals(deploymentVariableType)) {
				message = messageService.getMessage(MessageKey.HOST_INSTANCE_SAVE_JP_SUCCESS_MESSAGE,
						host.getHostName());
			} else if (DeploymentVariableType.LOG_PATTERNS.equals(deploymentVariableType)) {
				message = messageService.getMessage(MessageKey.HOST_LOG_PATTERN_LEVEL_SAVE_ERROR_MESSAGE,
						host.getHostName());
			} else if (DeploymentVariableType.BE_PROPERTIES.equals(deploymentVariableType)) {
				message = messageService.getMessage(MessageKey.HOST_BUSINESSEVENTS_PROPERTIES_SAVE_ERROR_MESSAGE,
						host.getHostName());
			}
		}
		return message;

	}

	@Override
	public Map<String, OperationResult> invokeGroupOperation(String entityName, String methodGroup, String methodName,
			Map<String, String> params, List<String> instances, Host host, String loggedInUser)
			throws MBeanOperationFailException {
		return invokeBEMMOperation(entityName, methodGroup, methodName, params, instances, host.getInstances(),
				instanceService, loggedInUser, null);

	}

	private Set<ServiceInstance> getServiceIntances(Host host) {
		Set<ServiceInstance> serviceInstances = new HashSet<ServiceInstance>();
		for (ServiceInstance instance : host.getInstances()) {
			if (null != instance && BETeaAgentStatus.RUNNING.getStatus().equals(instance.getStatus())) {
				serviceInstances.add(instance);
			}
		}
		return serviceInstances;
	}

	private void loadGroupDeploymentVariables(Host host, List<GroupDeploymentVariable> deploymentVariables,
			DeploymentVariableType deploymentVariableType, List<String> instancesKey) {

		Map<NameValuePair, List<ServiceInstance>> groupDeploymentVariableMap = new HashMap<NameValuePair, List<ServiceInstance>>();
		GroupOperationUtil.perform(host, deploymentVariables, deploymentVariableType, instancesKey,
				groupDeploymentVariableMap, instanceService, instanceService);
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
		NameValuePairs nameValuePairs = deploymentVariables.getNameValuePairs();
		NameValuePair valuePair = null;
		if (null != nameValuePairs) {
			List<NameValuePair> nameValuePair = nameValuePairs.getNameValuePair();
			if (null != nameValuePair && !nameValuePair.isEmpty()) {
				for (NameValuePair nameValue : nameValuePair) {
					if (nameValue.getName().equals(name)) {
						valuePair = nameValue;
						break;
					}
				}
			}
		}
		return valuePair;
	}

	private ServiceInstance getserviceInstanceByKey(String instanceKey, Host host) {
		for (ServiceInstance serviceInstance : host.getInstances()) {
			if (null != serviceInstance && serviceInstance.getKey().equals(instanceKey)) {
				return serviceInstance;
			}
		}
		return null;
	}

	/**
	 * Set the status of host
	 * 
	 * 
	 * @param host
	 *            - Host instance
	 */
	@Override
	public void refreshHostStatus(Host host) {
		try {
			BEMMServiceProviderManager.getInstance().getMasterHostManagementService()
					.refreshMasterHostStatus(host.getMasterHost());
		} catch (ObjectCreationException ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		}
	}

	@Override
	public String copyInstance(String instanceName, String processingUnit, String hostId, String deploymentPath,
			int jmxPort, ServiceInstance instance, String loggedInUser, String jmxUserName, String jmxPassword,
			String beId) throws CopyInstanceFailException, BEValidationException {
		return instanceService.copyInstance(instanceName, processingUnit, hostId, deploymentPath, jmxPort, jmxUserName,
				jmxPassword, beId, instance, loggedInUser);
	}

	@Override
	public String groupKill(List<String> instances, Host host, String loggedInUser)
			throws BEServiceInstanceKillException, BEValidationException {
		if (null == instances || instances.isEmpty()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.PASSED_INSTANCE_LIST_EMPTY_MESSAGE));
		}

		// Deploy service instance
		return killServiceInstances(host.getApplication(), instances, host.getInstances(), loggedInUser,
				dataStoreService, instanceService);
	}

	@Override
	public Map<String, String> groupThreadDump(List<String> instances, Host host) throws BEValidationException {
		if (null == instances || instances.isEmpty()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.PASSED_INSTANCE_LIST_EMPTY_MESSAGE));
		}

		// Deploy service instance
		return downloadThreadDumpServiceInstances(host.getApplication(), instances, host.getInstances(),
				dataStoreService, instanceService);
	}

	public DataSource groupThreadDumpZip(List<String> instances, Host host) throws BEValidationException {

		if (null == instances || instances.isEmpty()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.PASSED_INSTANCE_LIST_EMPTY_MESSAGE));
		}

		Map<String, String> threaddumps = downloadThreadDumpServiceInstances(host.getApplication(), instances,
				host.getInstances(), dataStoreService, instanceService);

		return ManagementUtil.createZip(threaddumps, host.getHostName());

	}

	@Override
	public Set<String> getInstanceAgents(List<String> instances, Host host) {
		Set<String> instanceAgentNames = new HashSet<String>();
		if (null != host) {
			List<ServiceInstance> serviceInstances = host.getInstances();
			if (null != serviceInstances) {
				for (ServiceInstance serviceInstance : serviceInstances) {
					if (null != serviceInstance && instances.contains(serviceInstance.getKey())) {
						List<Agent> agents = serviceInstance.getAgents();
						if (null != agents) {
							for (Agent agent : agents) {
								if (null != agent) {
									instanceAgentNames.add(agent.getAgentName());
								}
							}
						}
					}
				}
			}
		}
		return instanceAgentNames;
	}

	@Override
	public String applyLogPatterns(Host host, List<String> instances, Map<String, String> logPatternsAndLevel,
			String loggedInUser, boolean isIntermediateAction) throws BEValidationException {

		if (null == instances || instances.isEmpty()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.PASSED_INSTANCE_LIST_EMPTY_MESSAGE));
		}

		Collection<ServiceInstance> runningInstance = getServiceIntances(host);
		if (null != runningInstance && !runningInstance.isEmpty()) {

			for (ServiceInstance instance : runningInstance) {
				if (instances.contains(instance.getKey())) {
					try {
						instanceService.applyLogPatterns(instance, logPatternsAndLevel, loggedInUser,
								isIntermediateAction);
					} catch (DeploymentVariableStoreFailException e) {
						LOGGER.log(Level.DEBUG, e.getMessage());
					}
				}
			}
		}

		return null;
	}

	@Override
	public Map<String, String> getGroupRuntimeLoggerLevels(Host host, List<String> instances)
			throws BEValidationException {
		if (null == instances || instances.isEmpty()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.PASSED_INSTANCE_LIST_EMPTY_MESSAGE));
		}
		Map<String, String> loggerAndLevel = new HashMap<String, String>();
		Collection<ServiceInstance> runningInstance = getServiceIntances(host);
		if (null != runningInstance && !runningInstance.isEmpty()) {

			for (ServiceInstance instance : runningInstance) {
				if (instances.contains(instance.getKey())) {

					try {
						List<LogDetailImpl> logDetails = instanceService.getRuntimeLoggerLevels(instance);
						if (null != logDetails && !logDetails.isEmpty()) {
							for (LogDetailImpl logDetailImpl : logDetails) {
								if (null != logDetailImpl) {
									if (loggerAndLevel.containsKey(logDetailImpl.getLoggerName())) {
										String level = loggerAndLevel.get(logDetailImpl.getLoggerName());
										if (!level.trim().equalsIgnoreCase(logDetailImpl.getLogLevel())) {
											loggerAndLevel.remove(logDetailImpl.getLogLevel());
										}
									} else {
										loggerAndLevel.put(logDetailImpl.getLoggerName(), logDetailImpl.getLogLevel());
									}
								}
							}
						}
					} catch (MBeanOperationFailException e) {
						LOGGER.log(Level.DEBUG, e.getMessage());
					}

				}
			}
		}
		return loggerAndLevel;
	}

	@Override
	public DataSource downloadLogs(List<String> instances, final Host host, boolean isASLog)
			throws BEDownloadLogException {
		if (!host.getMasterHost().isAuthenticated()) {
			throw new BEDownloadLogException(
					messageService.getMessage(MessageKey.HOST_AUTHENTICATION_ERROR_MESSAGE, host.getHostName()));
		}
		List<ServiceInstance> filteredInstance = ManagementUtil.getFilteredServiceInstances(instances,
				host.getInstances(), null);
		File tempFile = null;
		String currentTimeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		final String fileName = host.getName() + "_" + currentTimeStamp;
		if (!filteredInstance.isEmpty()) {
			List<ServiceInstance> list = new ArrayList<>();
			Map<String, String> instanceLogLocation =  new HashMap<String, String>();
			for (ServiceInstance serviceInstance : filteredInstance) {
				if (serviceInstance.getHost().getMasterHost().isAuthenticated()) {
					list.add(serviceInstance);
					Map<String, String> traProps = loadTRAProperties(serviceInstance.getHost().getApplication(), serviceInstance.getHost(), serviceInstance);
					instanceLogLocation = FetchCddDataUtil.getLogLocationMap(instanceLogLocation, serviceInstance, host.getApplication().getName(), dataStoreService, traProps);
					
				}
			}
			if (list == null || list.isEmpty()) {
				throw new BEDownloadLogException(
						messageService.getMessage(MessageKey.HOST_AUTHENTICATION_ERROR_MESSAGE, host.getHostName()));
			}

			try {
				tempFile = File.createTempFile(fileName, "_logs.zip", dataStoreService.getTempFileLocation());
			} catch (IOException e) {
				throw new BEDownloadLogException(e.getMessage(), e);
			}
			ManagementUtil.createLogZip(filteredInstance, instanceLogLocation, tempFile.getAbsolutePath(), poolService,
					dataStoreService.getTempFileLocation(), timeout(), isASLog);
		} else {
			throw new BEDownloadLogException(
					messageService.getMessage(MessageKey.SELECTED_INSTANCE_NOT_DEPLOYED_MESSAGE));
		}
		FileDataSource dataSource = new FileDataSource(tempFile) {

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
		try {
			ManagementUtil.delete(tempFile);
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.DEBUG, e.getMessage());
		}
		return dataSource;
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
			if (!nameValuePair.isIsDeleted()) {

				if (!groupDeploymentVariable.getDeleted() && groupDeploymentVariable.getValue() != null
						&& !groupDeploymentVariable.getValue().trim().isEmpty())
					nameValuePair.setValue(groupDeploymentVariable.getValue());

				nameValuePair.setIsDeleted(groupDeploymentVariable.getDeleted());

			} else {
				if (groupDeploymentVariable.getIsNew()) {
					if (groupDeploymentVariable.getValue() != null
							&& !groupDeploymentVariable.getValue().trim().isEmpty())
						nameValuePair.setValue(groupDeploymentVariable.getValue());

					nameValuePair.setIsDeleted(groupDeploymentVariable.getDeleted());

				}
			}
		}
	}

	@Override
	public String groupDelete(Host host, List<String> instances, String loggedInUser, List<String> deletedInstances)
			throws BEServiceInstanceDeleteException {
		return deleteServiceInstances(host.getInstances(), instances, loggedInUser, instanceService, deletedInstances);
	}

	/**
	 * @return
	 */
	private Integer timeout() {
		return Integer.valueOf(configuration.getProperty(ConfigProperty.BE_TEA_AGENT_JSCH_TIMEOUT.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_JSCH_TIMEOUT.getDefaultValue()));
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
	public String fireTailCommand(String numberofLines, boolean isASLog, ServiceInstance instance) {
		return super.fireTailCommand(numberofLines, isASLog, instance, this.instanceService);
	}
}
