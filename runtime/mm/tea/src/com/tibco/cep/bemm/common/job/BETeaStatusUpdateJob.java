package com.tibco.cep.bemm.common.job;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.handler.ApplicationDataFeedHandler;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.model.BETeaOperationResult;
import com.tibco.cep.bemm.common.pool.GroupJob;
import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.jsch.JSchGroupJobExecutionContext;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.OMEnum;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.AgentConfig;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ProcessingUnit;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.monitoring.exception.RemoteMetricsCollectorServiceException;
import com.tibco.cep.bemm.monitoring.service.RemoteMetricsCollectorService;
import com.tibco.cep.bemm.monitoring.service.impl.JMXCPUMetricCollectorServiceImpl;
import com.tibco.cep.runtime.service.management.exception.JMXConnClientException;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnClient;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.tea.agent.be.BETeaAgentManager;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

public class BETeaStatusUpdateJob implements GroupJob<Object> {
	private MessageService messageService;

	public static final long DEFAULT_START_TIME_THRESHOLD = 45 * 1000;
	/**
	 * Logger Object
	 */
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BETeaStatusUpdateJob.class);
	/**
	 * Application management service
	 */
	private BEApplicationsManagementService beApplicationsManagementService = null;

	/**
	 * Constructor to set field value
	 * 
	 * @param serviceInstance
	 *            - Service Instance
	 * @param beApplicationsManagementService
	 *            - Application management service
	 */
	public BETeaStatusUpdateJob(BEApplicationsManagementService beApplicationsManagementService) {
		this.beApplicationsManagementService = beApplicationsManagementService;
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
	}

	private Boolean invokeMbeanAndMarkStatus(ServiceInstance serviceInstance) {
		try {
			boolean isAppRunning = false;

			Application application = serviceInstance.getHost().getApplication();
			if (application.isMonitorableOnly() && serviceInstance.isOldInstance()) {
				return false;
			}
			ApplicationDataFeedHandler<ServiceInstance> applicationDataFeedHandler = application
					.getApplicationDataFeedHandler(OMEnum.JMX);
			if (applicationDataFeedHandler != null
					/*
					 * Need not check status for an undeployed instance
					 */
					&& (application.isMonitorableOnly()
							|| !BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(serviceInstance.getStatus()))) {
				try {

					ServiceInstance rtServiceInstance = applicationDataFeedHandler.getTopologyData(serviceInstance);
					if (match(serviceInstance, rtServiceInstance)) {
						if (rtServiceInstance.getEngineStatus() == RuleServiceProviderImpl.STATE_RUNNING) {
							isAppRunning = true;
							markInstanceAsRunning(serviceInstance, rtServiceInstance);
						}
					} else {
						markInstanceAsStopped(serviceInstance);
					}
				} catch (Exception e) {
					markInstanceAsStopped(serviceInstance);
					LOGGER.log(Level.DEBUG, e.getMessage());
				}
			}
			return isAppRunning;
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INVOKING_MBAEN_ERROR));
		}
		return false;
	}

	/**
	 * @param stServiceInstance
	 * @param rtServiceInstance
	 * @return
	 */
	private boolean match(ServiceInstance stServiceInstance, ServiceInstance rtServiceInstance) {
		boolean isMatching = false;
		if (rtServiceInstance != null) {
			Application stApplication = stServiceInstance.getHost().getApplication();
			String rtClusterName = rtServiceInstance.getHost().getApplication().getClusterName();
			String rtInstanceName = rtServiceInstance.getName();
			String rtHostProcessId = rtServiceInstance.getProcessId();
			if (!stApplication.isMonitorableOnly()) {
				if (stApplication.getClusterName().equals(rtClusterName)
						&& stServiceInstance.getName().equals(rtInstanceName) && rtHostProcessId != null) {
					isMatching = true;
				}
			} else {
				if (rtHostProcessId != null)
					stApplication.setClusterName(rtClusterName);
				isMatching = true;
			}
		}
		return isMatching;
	}

	/**
	 * @param serviceInstance
	 * @param rtServiceInstance
	 */
	private void markInstanceAsRunning(ServiceInstance serviceInstance, ServiceInstance rtServiceInstance) {
		if (serviceInstance.getHost().getApplication().isMonitorableOnly()
				|| BETeaAgentStatus.STARTING.getStatus().equals(serviceInstance.getStatus())
				|| BETeaAgentStatus.STOPPED.getStatus().equals(serviceInstance.getStatus())) {
			// Get the Process Id
			String rtHostProcessId = rtServiceInstance.getProcessId();
			String[] parts = rtHostProcessId.split("@");
			String processId = parts[0];
			serviceInstance.setProcessId(processId);
			// Update Start time
			updateServiceInstanceStartTime(serviceInstance);

			if (null == serviceInstance.getGlobalVariables())
				serviceInstance.setGlobalVariables(rtServiceInstance.getGlobalVariables());
			if (null == serviceInstance.getSystemVariables())
				serviceInstance.setSystemVariables(rtServiceInstance.getSystemVariables());
			if (null == serviceInstance.getBEProperties())
				serviceInstance.setBEProperties(rtServiceInstance.getBEProperties());
			if (null == serviceInstance.getJVMProperties())
				serviceInstance.setJVMProperties(rtServiceInstance.getJVMProperties());

			// beApplicationsManagementService.getInstanceService().updateLogLevels(serviceInstance);
			if (serviceInstance.isPredefined()) {
				if (null == serviceInstance.getRemoteMetricsCollectorService()) {
					initMetricsCollectorService(serviceInstance.getHost(), serviceInstance);
				}
			}
		}
		if (serviceInstance.getHost().getApplication().isMonitorableOnly()) {
			serviceInstance.setPuId(rtServiceInstance.getPuId());
			updateProcessingUnitInfo(serviceInstance.getHost().getApplication(),
					rtServiceInstance.getHost().getApplication());
		}
		updateInstanceAgentsInfo(serviceInstance, rtServiceInstance);
		updateMemoryUsage(serviceInstance, rtServiceInstance);
		updateCPUUsage(serviceInstance, rtServiceInstance);

		if (serviceInstance.getHost().getApplication().isMonitorableOnly()) {
			BETeaAgentManager.INSTANCE.createAndRegisterTEAObjects(serviceInstance.getHost().getApplication());
		}

		// Update Status
		if (!serviceInstance.getStatus().equals(BETeaAgentStatus.RUNNING.getStatus()))
			serviceInstance.setStatus(BETeaAgentStatus.RUNNING.getStatus());
		else
			serviceInstance.setDeploymentStatus(BETeaAgentStatus.DEPLOYED.getStatus());
		
	}

	/**
	 * @param serviceInstance
	 * @param rtServiceInstance
	 */
	private void updateInstanceAgentsInfo(ServiceInstance serviceInstance, ServiceInstance rtServiceInstance) {
		if (null != serviceInstance.getAgents() && !serviceInstance.getAgents().isEmpty()) {
			for (Agent stAgent : serviceInstance.getAgents()) {
				for (Agent rtAgent : rtServiceInstance.getAgents()) {
					if (stAgent.getAgentName().equals(rtAgent.getAgentName())) {
						stAgent.setAgentId(Integer.valueOf(rtAgent.getAgentId()));
					}
				}
				stAgent.setStatus(BETeaAgentStatus.RUNNING.getStatus());
			}
		} else {
			for (Agent rtAgent : rtServiceInstance.getAgents()) {
				rtAgent.setInstance(serviceInstance);
				rtAgent.setStatus(BETeaAgentStatus.RUNNING.getStatus());
				serviceInstance.addAgent(rtAgent);
			}
		}
	}

	/**
	 * @param serviceInstance
	 */
	private void markInstanceAsStopped(ServiceInstance serviceInstance) {
		/*
		 * Should not mark an undeployed instance as Stopped
		 */
		if (serviceInstance.getHost().getApplication().isMonitorableOnly()
				|| (isStartInProgress(serviceInstance) == false
						&& !BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(serviceInstance.getStatus()))) {
			serviceInstance.setUpTime(-1L);
			serviceInstance.setProcessId("");

			serviceInstance.setStatus(BETeaAgentStatus.STOPPED.getStatus());
			for (Agent stAgent : serviceInstance.getAgents()) {
				stAgent.setAgentId(-1);
				stAgent.setStatus(BETeaAgentStatus.STOPPED.getStatus());
			}

			if (serviceInstance.isPredefined()) {
				if (null != serviceInstance.getRemoteMetricsCollectorService()) {
					serviceInstance.setRemoteMetricsCollectorService(null);
				}
			}
			// Reset CPU/Memory usage to 0
			serviceInstance.getMemoryUsage().setPercentUsed(0.0D);
			serviceInstance.getCpuUsage().setCpuUsageInPercent(0.0D);
			if (serviceInstance.getHost().getApplication().isMonitorableOnly()) {
				serviceInstance.setDeployed(false);
				serviceInstance.setDeploymentStatus(BETeaAgentStatus.UNDEPLOYED.getStatus());
			}
		}
	}

	/**
	 * @param serviceInstance
	 * @return
	 */
	private boolean isStartInProgress(ServiceInstance serviceInstance) {
		boolean isStartInProgress = false;
		if (serviceInstance.isStarting()) {
			long elapsedTimeInMillis = System.currentTimeMillis() - serviceInstance.getStartingTime();
			long elapsedTimeInSecs = elapsedTimeInMillis / 1000;
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INSTANCE_STATE_MODE,
					serviceInstance.getStatus(), elapsedTimeInSecs));
			long startTimeThreshold = beApplicationsManagementService.getInstanceStartThreshold();
			if (startTimeThreshold < DEFAULT_START_TIME_THRESHOLD) {
				startTimeThreshold = DEFAULT_START_TIME_THRESHOLD;
			}
			if (elapsedTimeInMillis <= startTimeThreshold) {
				// The instance is still starting
				isStartInProgress = true;
			}
		}
		return isStartInProgress;
	}

	/**
	 * Initialize the metric service
	 * 
	 * @param stHost
	 *            - Host from Site topology
	 * @param stHostInstance
	 *            - Site topology host instance
	 */
	private void initMetricsCollectorService(Host stHost, ServiceInstance stHostInstance) {
		String password = stHostInstance.getJmxPassword();
		String decodedPassword = ManagementUtil.getDecodedPwd(password);
		if (stHostInstance.getJmxPort() > 0) {
			try {
				JMXConnClient client = new JMXConnClient(stHost.getHostIp(), stHostInstance.getJmxPort(),
						stHostInstance.getJmxUserName(), decodedPassword, true);

				RemoteMetricsCollectorService remoteMetricsCollectorService = new JMXCPUMetricCollectorServiceImpl();
				remoteMetricsCollectorService.setMBeanServerConnection(client.getMBeanServerConnection());
				remoteMetricsCollectorService.init();
				stHostInstance.setRemoteMetricsCollectorService(remoteMetricsCollectorService);
			} catch (JMXConnClientException | RemoteMetricsCollectorServiceException ex) {
				LOGGER.log(Level.ERROR, ex, ex.getMessage());
			}
		}
	}

	/**
	 * Update the Service instance uptime
	 * 
	 * @param serviceInstance
	 *            - Service Instance
	 */
	private void updateServiceInstanceStartTime(ServiceInstance serviceInstance) {
		long jvmUptime;
		try {
			jvmUptime = BEMMServiceProviderManager.getInstance().getBEMBeanService()
					.getProcessStartTime(serviceInstance);
			serviceInstance.setUpTime(jvmUptime);
			if (serviceInstance.getHost().getApplication().isMonitorableOnly()) {
				Calendar calendar = Calendar.getInstance();
				long time = calendar.getTimeInMillis() - jvmUptime;
				serviceInstance.setStartingTime(new Date(time).getTime());
			}
		} catch (ObjectCreationException e) {
			LOGGER.log(Level.DEBUG, e.getMessage());
		}

	}

	/**
	 * Update memory usage
	 * 
	 * @param serviceInstance
	 * @param rtServiceInstance
	 */
	private void updateMemoryUsage(ServiceInstance serviceInstance, ServiceInstance rtServiceInstance) {
		serviceInstance.getMemoryUsage().setPercentUsed(rtServiceInstance.getMemoryUsage().getPercentUsed());
	}

	/**
	 * Update CPU Usage of the Service
	 * 
	 * @param serviceInstance
	 *            -
	 * @param rtServiceInstance
	 */
	private void updateCPUUsage(ServiceInstance serviceInstance, ServiceInstance rtServiceInstance) {
		serviceInstance.getCpuUsage().setCpuUsageInPercent(rtServiceInstance.getCpuUsage().getCpuUsageInPercent());
	}

	@Override
	public Object callUsingExecutionContext(GroupJobExecutionContext executionContext) throws Exception {
		if (!(executionContext instanceof JSchGroupJobExecutionContext)) {
			throw new Exception(messageService.getMessage(MessageKey.UNSUPPORTED_EXECUTION_CONTEXT_MESSAGE));
		}
		JSchGroupJobExecutionContext context = (JSchGroupJobExecutionContext) executionContext;

		ServiceInstance serviceInstance = context.getServiceInstance();

		LOGGER.log(Level.DEBUG,
				messageService.getMessage(MessageKey.JOB_STARTED_UPDATE_STATUS_INSTANCE, serviceInstance.getName()));

		BETeaOperationResult operationResult = new BETeaOperationResult();
		operationResult.setName(serviceInstance.getName());
		try {
			Boolean result = invokeMbeanAndMarkStatus(serviceInstance);
			operationResult.setResult(result);
		} catch (Exception e) {
			LOGGER.log(Level.DEBUG,
					messageService.getMessage(MessageKey.INSTANCE_STATUS_UPDATE_ERROR, serviceInstance.getName()));
			operationResult.setResult(e);
		}

		LOGGER.log(Level.DEBUG,
				messageService.getMessage(MessageKey.JOB_COMPLETED_INSTANCE_STATUS_UPDATE, serviceInstance.getName()));

		return operationResult;
	}

	private void updateProcessingUnitInfo(Application stApplication, Application rtApplication) {
		Collection<ProcessingUnit> rtPus = rtApplication.getProcessingUnits();
		if (null != rtPus && !rtPus.isEmpty()) {
			for (ProcessingUnit processingUnit : rtPus) {
				if (null != processingUnit) {
					if (null == stApplication.getProcessingUnit(processingUnit.getPuId())) {
						Collection<AgentConfig> agents = processingUnit.getAgents();
						if (null != agents) {
							for (AgentConfig agentConfig : agents) {
								if (null != agentConfig) {
									String key = stApplication.getName() + "/Agents/" + agentConfig.getAgentName();
									agentConfig.setTeaObjectKey(key);
								}
							}
						}
						processingUnit.setApplicationConfig(stApplication);
						stApplication.addProcessingUnit(processingUnit);
					}
				}
			}
		}
	}
}