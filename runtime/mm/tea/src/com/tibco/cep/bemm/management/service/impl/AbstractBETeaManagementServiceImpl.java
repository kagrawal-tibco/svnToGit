
package com.tibco.cep.bemm.management.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataSource;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.job.BETeaDeleteDeploymentJob;
import com.tibco.cep.bemm.common.job.BETeaDeployInstanceJob;
import com.tibco.cep.bemm.common.job.BETeaDownloadThreadDump;
import com.tibco.cep.bemm.common.job.BETeaEarHotDeployJob;
import com.tibco.cep.bemm.common.job.BETeaGroupMMOperationJob;
import com.tibco.cep.bemm.common.job.BETeaKillInstanceJob;
import com.tibco.cep.bemm.common.job.BETeaStartInstanceJob;
import com.tibco.cep.bemm.common.job.BETeaStopInstanceJob;
import com.tibco.cep.bemm.common.job.BETeaTailCommandJob;
import com.tibco.cep.bemm.common.job.BETeaUndeployInstanceJob;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.model.BETeaOperationResult;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.GroupJobExecutorService;
import com.tibco.cep.bemm.common.pool.context.BETeaMMOpertaionJobContext;
import com.tibco.cep.bemm.common.pool.jsch.JSchGroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.jsch.SshConfig;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceDeleteException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceDeployException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceKillException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceStartException;
import com.tibco.cep.bemm.management.exception.BEServiceInstanceStopException;
import com.tibco.cep.bemm.management.exception.BEValidationException;
import com.tibco.cep.bemm.management.service.BEApplicationGVCacheService;
import com.tibco.cep.bemm.management.service.BEMasterHostManagementService;
import com.tibco.cep.bemm.management.service.BEServiceInstancesManagementService;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.management.util.BETeaOperationEnum;
import com.tibco.cep.bemm.management.util.FetchCddDataUtil;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.AgentConfig;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.BE;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;

/**
 * @author dijadhav
 *
 */
public abstract class AbstractBETeaManagementServiceImpl extends AbstractStartStopServiceImpl {

	private MessageService messageService;
	public BEApplicationsManagementDataStoreService<?> dataStoreService;
	/**
	 * Logger instance
	 */
	protected static Logger LOGGER = LogManagerFactory.getLogManager()
			.getLogger(AbstractBETeaManagementServiceImpl.class);

	protected GroupJobExecutorService poolService;

	AbstractBETeaManagementServiceImpl() {
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init(Properties configuration) throws ServiceInitializationException, ObjectCreationException {
		try{
			dataStoreService = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementDataStoreService();
		}catch (ObjectCreationException e) {
			throw new ServiceInitializationException(
					messageService.getMessage(MessageKey.INSTANCE_INITIALIZE_INSTANCE_ERROR_MESSAGE));
		}
	}
	
	

	/**
	 * Deploy service instances
	 * 
	 * @param application
	 * @param instances
	 * @param loggedInUser
	 * @param serviceInstances
	 * @param applicationGVCacheService
	 * @param instanceService
	 * @param dataStoreService
	 * @return
	 * @throws BEServiceInstanceDeployException
	 */
	protected String deployServiceInstances(Application application, List<String> instances, String loggedInUser,
			List<ServiceInstance> serviceInstances, BEApplicationGVCacheService<?> applicationGVCacheService,
			BEServiceInstancesManagementService instanceService,
			BEApplicationsManagementDataStoreService<?> dataStoreService) throws BEServiceInstanceDeployException {

		// Get filtered service instances
		List<ServiceInstance> filteredServiceInstances = ManagementUtil.getFilteredServiceInstances(instances,
				serviceInstances, BETeaOperationEnum.DEPLOY);

		if (filteredServiceInstances == null || filteredServiceInstances.isEmpty()) {
			throw new BEServiceInstanceDeployException(
					messageService.getMessage(MessageKey.SELECTED_SERVICE_INSTANCE_ALREADY_DEPLOYED));
		}
		List<ServiceInstance> list = new ArrayList<>();
		for (ServiceInstance serviceInstance : filteredServiceInstances) {
			if (serviceInstance.getHost().getMasterHost().isAuthenticated()) {
				list.add(serviceInstance);
			}
		}
		if (list == null || list.isEmpty()) {
			throw new BEServiceInstanceDeployException(
					messageService.getMessage(MessageKey.APPLICATION_AUTHENTICATION_ERROR_MESSAGE));
		}

		// Add service instances in job execution context
		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
		for (ServiceInstance serviceInstance : list) {
			SshConfig sshConfig = getSshConfig(serviceInstance);
			jobExecutionContexts.add(new JSchGroupJobExecutionContext(serviceInstance, sshConfig));
		}
		// Get the global variables of application
		Collection<GlobalVariableDescriptor> globalVariableDescriptors = null;
		try {
			globalVariableDescriptors = applicationGVCacheService.getServiceSettableGlobalVariables(
					application.getName(),
					((File) dataStoreService.getApplicationManagementDataStore()).getAbsolutePath());
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.GET_GLOBAL_VARIABLES_ERROR));
		}

		// Submit the job
		List<Object> results = poolService.submitJobs(
				new BETeaDeployInstanceJob(instanceService, loggedInUser, globalVariableDescriptors),
				jobExecutionContexts);
		List<String> failedInstances = new ArrayList<String>();
		int errorCnt = 0;
		for (Object object : results) {
			BETeaOperationResult operationResult = (BETeaOperationResult) object;
			Object result = operationResult.getResult();
			if (result instanceof Exception) {
				failedInstances.add(operationResult.getName());
				errorCnt++;
			}
		}
		// If error count is same as count of
		if (errorCnt != 0 && errorCnt == filteredServiceInstances.size())
			throw new BEServiceInstanceDeployException(
					messageService.getMessage(MessageKey.INSTANCE_SELECTED_DEPLOY_ERROR));
		if (!failedInstances.isEmpty()) {
			return (messageService.getMessage(MessageKey.SOME_SERVICE_INSTANCE_NOT_DEPLOYED));
		}

		return (messageService.getMessage(MessageKey.SELECTED_SERVICE_INSTANCE_DEPLOYED_SUCCESS));
	}

	/**
	 * Undeploy service instances
	 * 
	 * @param application
	 * @param instances
	 * @param loggedInUser
	 * @param serviceInstances
	 * @param dataStoreService
	 * @return
	 * @throws BEServiceInstanceDeployException
	 */
	protected String undeployServiceInstances(Application application, List<String> instances, String loggedInUser,
			List<ServiceInstance> serviceInstances, BEApplicationGVCacheService<?> applicationGVCacheService,
			BEServiceInstancesManagementService instanceService,
			BEApplicationsManagementDataStoreService<?> dataStoreService) throws BEServiceInstanceDeployException {
		List<ServiceInstance> filteredServiceInstances = ManagementUtil.getFilteredServiceInstances(instances,
				serviceInstances, BETeaOperationEnum.UNDEPLOY);

		if (filteredServiceInstances == null || filteredServiceInstances.isEmpty()) {
			
			for (ServiceInstance serviceInstance : serviceInstances) {
				if (null != serviceInstance && instances.contains(serviceInstance.getKey())
						&& (serviceInstance.isRunning() || serviceInstance.isStarting() || serviceInstance.isStopping())){
					throw new BEServiceInstanceDeployException(
							messageService.getMessage(MessageKey.SELECTED_SERVICE_INSTANCE_CANNOT_UNDEPLOYED));
				}
			}
			throw new BEServiceInstanceDeployException(
					messageService.getMessage(MessageKey.SELECTED_SERVICE_INSTANCE_ALREADY_UNDEPLOYED));
		}
		List<ServiceInstance> list = new ArrayList<>();
		for (ServiceInstance serviceInstance : filteredServiceInstances) {
			if (serviceInstance.getHost().getMasterHost().isAuthenticated()) {
				list.add(serviceInstance);
			}
		}
		if (list == null || list.isEmpty()) {
			throw new BEServiceInstanceDeployException(
					messageService.getMessage(MessageKey.APPLICATION_AUTHENTICATION_ERROR_MESSAGE));
		}
		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
		for (ServiceInstance serviceInstance : filteredServiceInstances) {
			jobExecutionContexts.add(new JSchGroupJobExecutionContext(serviceInstance, getSshConfig(serviceInstance)));
		}
		Collection<GlobalVariableDescriptor> globalVariableDescriptors = null;
		try {
			globalVariableDescriptors = applicationGVCacheService.getServiceSettableGlobalVariables(
					application.getName(),
					((File) dataStoreService.getApplicationManagementDataStore()).getAbsolutePath());
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.GET_GLOBAL_VARIABLES_ERROR));

		}
		List<Object> results = poolService.submitJobs(
				new BETeaUndeployInstanceJob(instanceService, loggedInUser, globalVariableDescriptors),
				jobExecutionContexts);
		int errorCnt = 0;
		List<String> failedInstances = new ArrayList<String>();
		for (Object object : results) {
			BETeaOperationResult operationResult = (BETeaOperationResult) object;
			Object result = operationResult.getResult();
			if (result instanceof Exception) {
				failedInstances.add(operationResult.getName());
				errorCnt++;
			}

		}
		// If error count is same as count of
		if (errorCnt != 0 && errorCnt == filteredServiceInstances.size())
			throw new BEServiceInstanceDeployException(
					messageService.getMessage(MessageKey.INSTANCE_SELECTED_UNDEPLOY_ERROR));
		if (!failedInstances.isEmpty()) {
			return (messageService.getMessage(MessageKey.SOME_INSTANCE_NOT_UNDEPLOYED));
		}
		return (messageService.getMessage(MessageKey.SELECTED_SERVICE_INSTANCE_UNDEPLOY_SUCCESS));
	}

	/**
	 * Start the service instance
	 * 
	 * @param instances
	 * @param loggedInUser
	 * @param serviceInstances
	 * @param instanceService
	 * @return
	 * @throws BEServiceInstanceStartException
	 */
	protected String startServiceInstances(List<String> instances, String loggedInUser,
			List<ServiceInstance> serviceInstances, BEServiceInstancesManagementService instanceService)
			throws BEServiceInstanceStartException {
		List<ServiceInstance> filteredServiceInstances = ManagementUtil.getFilteredServiceInstances(instances,
				serviceInstances, BETeaOperationEnum.START);

		if (filteredServiceInstances != null && filteredServiceInstances.size() == 0) {
			return (messageService.getMessage(MessageKey.SELECTED_SERVICE_INSTANCE_ALREADY_STARTED_OR_UNDEPLOYED));
		}

		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
		for (ServiceInstance serviceInstance : filteredServiceInstances) {
			jobExecutionContexts.add(new JSchGroupJobExecutionContext(serviceInstance, getSshConfig(serviceInstance)));
		}

		List<Object> results = poolService.submitJobs(new BETeaStartInstanceJob(instanceService, loggedInUser),
				jobExecutionContexts);
		List<String> failedInstances = new ArrayList<String>();
		int errorCnt = 0;
		for (Object object : results) {
			BETeaOperationResult operationResult = (BETeaOperationResult) object;
			Object result = operationResult.getResult();
			if (result instanceof Exception) {
				failedInstances.add(operationResult.getName());
				errorCnt++;
			}

		}
		// If error count is same as count of
		if (errorCnt != 0 && errorCnt == filteredServiceInstances.size())
			throw new BEServiceInstanceStartException(
					messageService.getMessage(MessageKey.INSTANCE_SELECTED_START_ERROR));

		return (messageService.getMessage(MessageKey.SELECTED_SERVICE_INSTANCE_STARTED_SUCCESS));
	}

	protected String stopServiceInstances(List<String> instances, String loggedInUser,
			List<ServiceInstance> serviceInstances, BEServiceInstancesManagementService instanceService)
			throws BEServiceInstanceStopException {
		List<ServiceInstance> filteredServiceInstances = ManagementUtil.getFilteredServiceInstances(instances,
				serviceInstances, BETeaOperationEnum.STOP);

		if (filteredServiceInstances != null && filteredServiceInstances.size() == 0) {
			return (messageService.getMessage(MessageKey.SELECTED_SERVICE_INSTANCE_ALREADY_STOPPED));
		}

		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
		for (ServiceInstance serviceInstance : filteredServiceInstances) {
			jobExecutionContexts.add(new JSchGroupJobExecutionContext(serviceInstance, getSshConfig(serviceInstance)));
		}

		List<Object> results = poolService.submitJobs(new BETeaStopInstanceJob(instanceService, loggedInUser),
				jobExecutionContexts);
		List<String> failedInstances = new ArrayList<String>();
		int errorCnt = 0;
		for (Object object : results) {
			BETeaOperationResult operationResult = (BETeaOperationResult) object;
			Object result = operationResult.getResult();
			if (result instanceof Exception) {
				failedInstances.add(operationResult.getName());
				errorCnt++;
			}

		}
		// If error count is same as count of
		if (errorCnt != 0 && errorCnt == filteredServiceInstances.size())
			throw new BEServiceInstanceStopException(
					messageService.getMessage(MessageKey.INSTANCE_SELECTED_STOP_ERROR));

		return (messageService.getMessage(MessageKey.SELECTED_SERVICE_INSTANCE_STOPPED_SUCCESS));
	}

	protected String killServiceInstances(Application application, List<String> instances,
			List<ServiceInstance> serviceInstances, String loggedInUser,
			BEApplicationsManagementDataStoreService<?> dataStoreService2,
			BEServiceInstancesManagementService instanceService) throws BEServiceInstanceKillException {
		List<ServiceInstance> filteredServiceInstances = ManagementUtil.getFilteredServiceInstances(instances,
				serviceInstances, BETeaOperationEnum.KILL);
		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
		for (ServiceInstance serviceInstance : filteredServiceInstances) {
			jobExecutionContexts.add(new JSchGroupJobExecutionContext(serviceInstance, getSshConfig(serviceInstance)));
		}

		List<Object> results = poolService.submitJobs(new BETeaKillInstanceJob(instanceService, loggedInUser),
				jobExecutionContexts);
		List<String> failedInstances = new ArrayList<String>();
		int errorCnt = 0;
		for (Object object : results) {
			BETeaOperationResult operationResult = (BETeaOperationResult) object;
			Object result = operationResult.getResult();
			if (result instanceof Exception) {
				failedInstances.add(operationResult.getName());
				errorCnt++;
			}

		}
		// If error count is same as count of
		if (errorCnt != 0 && errorCnt == filteredServiceInstances.size())
			throw new BEServiceInstanceKillException(
					messageService.getMessage(MessageKey.INSTANCE_SELECTED_KILL_ERROR));

		return (messageService.getMessage(MessageKey.SELECTED_SERVICE_INSTANCE_KILLED_SUCCESS));
	}

	protected Map<String, String> downloadThreadDumpServiceInstances(Application application, List<String> instances,
			List<ServiceInstance> serviceInstances, BEApplicationsManagementDataStoreService<?> dataStoreService,
			BEServiceInstancesManagementService instanceService) {
		List<ServiceInstance> filteredServiceInstances = ManagementUtil.getFilteredServiceInstances(instances,
				serviceInstances, BETeaOperationEnum.THREAD_DUMP);
		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
		for (ServiceInstance serviceInstance : filteredServiceInstances) {
			jobExecutionContexts.add(new JSchGroupJobExecutionContext(serviceInstance, getSshConfig(serviceInstance)));
		}

		List<Object> results = poolService.submitJobs(new BETeaDownloadThreadDump(instanceService),
				jobExecutionContexts);
		List<String> failedInstances = new ArrayList<String>();
		Map<String, String> threaddumps = new HashMap<String, String>();

		for (Object object : results) {
			BETeaOperationResult operationResult = (BETeaOperationResult) object;
			Object result = operationResult.getResult();
			if (result instanceof Exception) {
				failedInstances.add(operationResult.getName());
			} else if (result instanceof String) {
				threaddumps.put(operationResult.getName(), operationResult.getResult().toString());
			}

		}
		return threaddumps;
	}

	/**
	 * Get SSH config
	 * 
	 * @param serviceInstance
	 * @return
	 */
	protected SshConfig getSshConfig(ServiceInstance serviceInstance) {
		SshConfig sshConfig = new SshConfig();
		Host host = serviceInstance.getHost();
		sshConfig.setHostIp(host.getHostIp());
		sshConfig.setPassword(host.getPassword());
		sshConfig.setPort(host.getSshPort());
		sshConfig.setUserName(host.getUserName());
		return sshConfig;
	}

	/**
	 * Delete the passed service instances
	 * 
	 * @param instanceList
	 *            - List of instance from which passed instances to be deleted.
	 * @param instances
	 *            - List of instances to be deleted
	 * @param loggedInUser
	 *            - Logged in user
	 * @param instanceService
	 *            -Instance Management Service
	 * @return Success or error message
	 * @throws BEServiceInstanceDeleteException
	 */
	protected String deleteServiceInstances(List<ServiceInstance> instanceList, List<String> instances,
			String loggedInUser, BEServiceInstancesManagementService instanceService, List<String> deletedInstances)
			throws BEServiceInstanceDeleteException {
		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
		for (ServiceInstance serviceInstance : instanceList) {
			if (instances.contains(serviceInstance.getKey()))
				jobExecutionContexts
						.add(new JSchGroupJobExecutionContext(serviceInstance, getSshConfig(serviceInstance)));
		}

		List<Object> results = poolService.submitJobs(new BETeaDeleteDeploymentJob(instanceService),
				jobExecutionContexts);
		int errorCount = 0;
		for (Object object : results) {
			BETeaOperationResult operationResult = (BETeaOperationResult) object;
			Object result = operationResult.getResult();
			if (!(result instanceof String)) {
				errorCount++;

			} else {
				if (null != result) {
					deletedInstances.add(operationResult.getKey());
				}
			}
		}
		if (0 != errorCount)
			if (errorCount == instances.size())
				throw new BEServiceInstanceDeleteException(
						messageService.getMessage(MessageKey.SERVICE_INSTANCE_DELETE_ERROR));
			else
				return (messageService.getMessage(MessageKey.SELECTED_INSTANCES_NOT_DELETED));

		return (messageService.getMessage(MessageKey.SELECTED_INSTANCES_DELETE_SUCCESS));
	}

	/**
	 * Hot deployment of ear
	 * 
	 * @param earArchive
	 *            - Application ear
	 * @param instancesList
	 *            - List of service instance key
	 * @param loggedInUser
	 *            - Logged in user
	 * @param instances
	 *            - List of service instance
	 * @param beTeaTempFileLocation
	 * @return
	 * @throws BEServiceInstanceDeployException
	 */
	protected String hotDepoloy(DataSource earArchive, List<String> instancesList, String loggedInUser,
			List<ServiceInstance> instances, BEServiceInstancesManagementService instanceService,
			File beTeaTempFileLocation) throws BEServiceInstanceDeployException {
		List<ServiceInstance> serviceInstances = new ArrayList<>();
		for (ServiceInstance serviceInstance : instances) {
			if (null != serviceInstance && instancesList.contains(serviceInstance.getKey())) {
				serviceInstances.add(serviceInstance);
			}
		}
		if (serviceInstances.isEmpty())
			throw new BEServiceInstanceDeployException(
					messageService.getMessage(MessageKey.APPLICATION_AUTHENTICATION_ERROR_MESSAGE));
		File tempEarFile;
		try {
			tempEarFile = File.createTempFile(earArchive.getName() + "_" + System.currentTimeMillis(), ".ear",
					beTeaTempFileLocation);
			OutputStream out = new FileOutputStream(tempEarFile);
			byte[] b = ManagementUtil.getByteArrayFromStream(earArchive.getInputStream());
			out.write(b);
			out.close();
			List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
			for (ServiceInstance serviceInstance : serviceInstances) {
				jobExecutionContexts
						.add(new JSchGroupJobExecutionContext(serviceInstance, getSshConfig(serviceInstance)));
			}

			List<Object> results = poolService.submitJobs(
					new BETeaEarHotDeployJob(tempEarFile.getAbsolutePath(), instanceService, loggedInUser),
					jobExecutionContexts);
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
			if (null != tempEarFile)
				try {
					ManagementUtil.delete(tempEarFile);
				} catch (FileNotFoundException e) {
					LOGGER.log(Level.DEBUG, e.getMessage());
				}
			if (0 != errorCount) {
				if (errorCount == instances.size())
					throw new BEServiceInstanceDeployException(
							messageService.getMessage(MessageKey.SERVICE_INSTANCE_HOT_DEPLOY_ERROR));
				else
					return (messageService.getMessage(MessageKey.HOT_DEPLOY_FAILED));
			}

		} catch (IOException e) {
			LOGGER.log(Level.DEBUG, e.getMessage());
		}

		return (messageService.getMessage(MessageKey.SELECTED_SERVICE_INSTANCE_HOT_DEPLOYED));
	}

	protected Map<String, OperationResult> invokeBEMMOperation(String entityName, String methodGroup, String methodName,
			Map<String, String> params, List<String> instances, List<ServiceInstance> serviceInstances,
			BEServiceInstancesManagementService instanceService, String loggedInUser, AgentConfig agentConfig) {
		Collection<ServiceInstance> appFiltered = ManagementUtil.getFilteredServiceInstances(instances,
				serviceInstances, BETeaOperationEnum.INVOKE_MM_OP);
		if (null == appFiltered || appFiltered.isEmpty())
			return null;
		List<ServiceInstance> filteredInstances = new ArrayList<>();

		int agentId = -1;
		if (null != agentConfig) {
			if (params.containsKey("Session")) {
				params.put("Session", agentConfig.getAgentName());
			} else if (params.containsKey("sessionName")) {
				params.put("sessionName", agentConfig.getAgentName());
			} else if (params.containsKey("Name")) {
				if (!"GetHostInformation".equals(methodName))
					params.put("Name", agentConfig.getAgentName());
			}

			for (ServiceInstance serviceInstance : appFiltered) {
				List<Agent> agents = serviceInstance.getAgents();
				if (null != agents) {
					for (Agent agent : agents) {
						if (null != agent && agentConfig.getAgentName().equals(agent.getAgentName())
								&& agentConfig.getAgentType().equals(agent.getAgentType())) {
							filteredInstances.add(serviceInstance);
						}
					}
				}
			}

		} else
			filteredInstances.addAll(appFiltered);

		List<GroupJobExecutionContext> contexts = new ArrayList<GroupJobExecutionContext>();
		for (ServiceInstance instance : filteredInstances) {
			contexts.add(new BETeaMMOpertaionJobContext(instance, agentId));
		}
		List<Object> objects = poolService.submitJobs(new BETeaGroupMMOperationJob(instanceService, loggedInUser,
				methodGroup, entityName, params, methodName), contexts);

		Map<String, OperationResult> returnDataMap = new HashMap<>();
		if (null != objects) {
			for (Object object : objects) {
				if (object instanceof BETeaOperationResult) {
					BETeaOperationResult result = (BETeaOperationResult) object;
					if (result.getResult() instanceof OperationResult)
						returnDataMap.put(result.getName(), (OperationResult) result.getResult());
				}

			}
		}

		return returnDataMap;
	}

	public String fireTailCommand(String numberofLines, boolean isASLog, ServiceInstance instance,
			BEServiceInstancesManagementService instanceService) {
		// Create A Job and submit it
		List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();

		jobExecutionContexts.add(new JSchGroupJobExecutionContext(instance, getSshConfig(instance)));
		
		Map<String, String> traProps = loadTRAProperties(instance.getHost().getApplication(), instance.getHost(), instance);
		Map<String, String> instanceLogLocation =  new HashMap<String, String>();
		instanceLogLocation = FetchCddDataUtil.getLogLocationMap(instanceLogLocation, instance, instance.getHost().getApplication().getName(), dataStoreService, traProps);


		List<Object> results = poolService.submitJobs(new BETeaTailCommandJob(instanceService, numberofLines, isASLog, instanceLogLocation),
				jobExecutionContexts);
		for (Object object : results) {
			if (null != object) {
				if (object instanceof Exception) {
					LOGGER.log(Level.DEBUG, ((Exception) object).getMessage());
				} else if (object instanceof BETeaOperationResult) {
					String logs = ((BETeaOperationResult) object).getResult().toString();
					return logs;
				}
			}
		}
		return null;
	}
	
	/**
	 * Load TRA properties
	 * 
	 * @param application
	 *            - Application object
	 * @param matchedHost
	 *            - Matched Host from application
	 * @return
	 */
	public Map<String, String> loadTRAProperties(Application application, Host matchedHost, ServiceInstance config) {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.LOAD_TRA_PROPERTIES, application.getName(),
				matchedHost.getName()));
		InputStream hostTraStream = null;
		// Load TRA Properties
		if (ManagementUtil.isHostTraConfigExist(config.getHost().getHostId(), config.getHost().getApplication())) {
			hostTraStream = dataStoreService.fetchHostTraFile(config.getHost().getApplication().getName(),
					config.getHost().getHostId());
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.OVERRIDDEN_TRA_EXIST_FOR_APPLICATION));
		} else {
			hostTraStream = dataStoreService.fetchHostTraFile(null,
					config.getHost().getHostId() + "_" + config.getBeId());
		}
		Map<String, String> traProperties = new HashMap<String, String>();

		ManagementUtil.loadTraProperties(traProperties, hostTraStream);
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.LOAD_TRA_PROPERTIES, application.getName(),
				matchedHost.getName()));

		return traProperties;
	}
	// Validate host details
	protected void validateHost(String hostName, String beId, String appVersion) throws BEValidationException, ObjectCreationException {
		BEMasterHostManagementService masterHostService = BEMMServiceProviderManager.getInstance()
				.getMasterHostManagementService();
		MasterHost masterHost = masterHostService.getMasterHostByName(hostName);
		if (null != masterHost && !masterHost.isAuthenticated()) {
			throw new BEValidationException(messageService.getMessage(MessageKey.BE_VERSION_NOT_MENTIONED_ERROR_MESSAGE,masterHost.getHostName()));
		}
		if (beId == null) {
			if (masterHost.getBE().size() > 1) {
				throw new BEValidationException(messageService.getMessage(MessageKey.SPECIFY_BE_HOME_ERROR_MESSAGE));
			}
		} else {
			BE beHome = masterHostService.getBEHomeById(masterHost, beId);
			if (null == beHome.getVersion() || beHome.getVersion().trim().isEmpty()) {
				throw new BEValidationException(messageService.getMessage(MessageKey.BE_VERSION_NOT_MENTIONED_ERROR_MESSAGE));
			} else {
				if (!beHome.getVersion().contains(appVersion)) {
					throw new BEValidationException(messageService.getMessage(MessageKey.NOT_MATCHING_VERSION_ERROR_MESSAGE, appVersion, beHome.getVersion()));
				}
			}
		}
	}
}
