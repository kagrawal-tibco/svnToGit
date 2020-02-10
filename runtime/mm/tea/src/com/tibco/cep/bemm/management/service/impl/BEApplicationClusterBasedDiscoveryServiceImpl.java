package com.tibco.cep.bemm.management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.handler.ApplicationDataFeedHandler;
import com.tibco.cep.bemm.common.model.CPUUsage;
import com.tibco.cep.bemm.common.model.impl.ProcessMemoryUsageImpl;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.OMEnum;
import com.tibco.cep.bemm.management.service.BEApplicationDiscoveryService;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.monitoring.exception.RemoteMetricsCollectorServiceException;
import com.tibco.cep.bemm.monitoring.service.RemoteMetricsCollectorService;
import com.tibco.cep.bemm.monitoring.service.impl.JMXCPUMetricCollectorServiceImpl;
import com.tibco.cep.runtime.service.management.exception.JMXConnClientException;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnClient;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

/**
 * @author vdhumal Discovery Service - To discover/update statuses of instances
 *         from the ManagementTables space from the application Cluster.
 */
public class BEApplicationClusterBasedDiscoveryServiceImpl implements BEApplicationDiscoveryService {

	/**
	 * Logger Object
	 */
	private static final Logger LOGGER = LogManagerFactory.getLogManager()
			.getLogger(BEApplicationDiscoveryService.class);

	public static final long DEFAULT_START_TIME_THRESHOLD = 90 * 1000;

	private BEApplicationsManagementService beApplicationsManagementService = null;

	public BEApplicationClusterBasedDiscoveryServiceImpl() throws ObjectCreationException {
		this.beApplicationsManagementService = BEMMServiceProviderManager.getInstance()
				.getBEApplicationsManagementService();
	}

	@Override
	public void init(Properties properties) {

	}

	@Override
	public boolean discover(Application application) {
		boolean isAppRunning = false;
		try {
			ApplicationDataFeedHandler<Application> applicationDataFeedHandler = application
					.getApplicationDataFeedHandler(OMEnum.TIBCO);
			if (applicationDataFeedHandler != null) {
				Application rtApplication = applicationDataFeedHandler.getTopologyData(application);
				List<ServiceInstance> allSTServiceInstances = getAllServiceInstances(application);

				for (ServiceInstance serviceInstance : allSTServiceInstances) {
					if (!BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(serviceInstance.getStatus())) {
						ServiceInstance rtServiceInstance = matchInstance(serviceInstance, rtApplication);
						if (rtServiceInstance != null) {
							isAppRunning = true;
							markInstanceAsRunning(serviceInstance, rtServiceInstance);
						} else {
							markInstanceAsStopped(serviceInstance);
						}
					}
				}
				// Set the application status
				ManagementUtil.setRunningStatus(application);
			}
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		}
		return isAppRunning;
	}

	/**
	 * @param stServiceInstance
	 * @param rtApplication
	 * @return
	 */
	private ServiceInstance matchInstance(ServiceInstance stServiceInstance, Application rtApplication) {
		ServiceInstance matchedRTServiceInstance = null;
		if (rtApplication != null) {
			List<ServiceInstance> allRTServiceInstances = getAllServiceInstances(rtApplication);
			for (ServiceInstance rtServiceInstance : allRTServiceInstances) {
				if (stServiceInstance.getName().equals(rtServiceInstance.getName())
						&& stServiceInstance.getHost().getHostIp().equals(rtServiceInstance.getHost().getHostIp())
						&& isInstanceAgentsMatched(stServiceInstance, rtServiceInstance)) {
					matchedRTServiceInstance = rtServiceInstance;
					break;
				}
			}
		}
		return matchedRTServiceInstance;
	}

	/**
	 * @param stServiceInstance
	 * @param rtServiceInstance
	 * @return
	 */
	private boolean isInstanceAgentsMatched(ServiceInstance stServiceInstance, ServiceInstance rtServiceInstance) {
		List<Agent> stInstanceAgents = stServiceInstance.getAgents();
		List<Agent> rtInstanceAgents = rtServiceInstance.getAgents();
		boolean isMatched = false;
		if (null != stInstanceAgents && null != rtInstanceAgents
				&& stInstanceAgents.size() == rtInstanceAgents.size()) {
			for (Agent agent : stInstanceAgents) {
				if (null != agent) {
					Agent matchedRTInstanceAgent = matchInstanceAgent(agent, rtInstanceAgents);
					if (matchedRTInstanceAgent != null) {
						isMatched = true;
					} else {
						isMatched = false;
						break;
					}
				}
			}
		}
		return isMatched;
	}

	/**
	 * @param stInstanceAgent
	 * @param rtInstanceAgents
	 * @return
	 */
	private Agent matchInstanceAgent(Agent stInstanceAgent, List<Agent> rtInstanceAgents) {
		Agent matchedRTInstanceAgent = null;
		for (Agent rtInstanceAgent : rtInstanceAgents) {
			if (null != rtInstanceAgent) {
				if (stInstanceAgent.getAgentName().equals(rtInstanceAgent.getAgentName())
						&& stInstanceAgent.getAgentType().equals(rtInstanceAgent.getAgentType())) {
					matchedRTInstanceAgent = rtInstanceAgent;
					break;
				}
			}
		}
		return matchedRTInstanceAgent;
	}

	/**
	 * @param serviceInstance
	 * @param rtServiceInstance
	 */
	private void markInstanceAsRunning(ServiceInstance serviceInstance, ServiceInstance rtServiceInstance) {
		if (BETeaAgentStatus.STARTING.getStatus().equals(serviceInstance.getStatus())
				|| BETeaAgentStatus.STOPPED.getStatus().equals(serviceInstance.getStatus())) {
			serviceInstance.setProcessId(rtServiceInstance.getProcessId());
			// Update Start time
			updateServiceInstanceStartTime(serviceInstance);
			// Update Status
			serviceInstance.setStatus(BETeaAgentStatus.RUNNING.getStatus());
			// beApplicationsManagementService.getInstanceService().updateLogLevels(serviceInstance);
			if (serviceInstance.isPredefined()) {
				if (null == serviceInstance.getRemoteMetricsCollectorService()) {
					initMetricsCollectorService(serviceInstance.getHost(), serviceInstance);
				}
			}
		}
		updateInstanceAgentsInfo(serviceInstance, rtServiceInstance);
		updateMemoryUsage(serviceInstance);
		updateCPUUsage(serviceInstance);
	}

	/**
	 * @param serviceInstance
	 */
	private void markInstanceAsStopped(ServiceInstance serviceInstance) {
		if (isStartInProgress(serviceInstance) == false) {
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
			LOGGER.log(Level.DEBUG, "Instance state is in %s mode since last %s secs", serviceInstance.getStatus(),
					elapsedTimeInSecs);
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

	private void updateInstanceAgentsInfo(ServiceInstance serviceInstance, ServiceInstance rtServiceInstance) {
		for (Agent instanceAgent : serviceInstance.getAgents()) {
			Agent rtInstanceAgent = matchInstanceAgent(instanceAgent, rtServiceInstance.getAgents());
			instanceAgent.setAgentId(rtInstanceAgent.getAgentId());
			instanceAgent.setStatus(BETeaAgentStatus.RUNNING.getStatus());
		}
	}

	private void updateServiceInstanceStartTime(ServiceInstance serviceInstance) {
		long jvmUptime;
		try {
			jvmUptime = BEMMServiceProviderManager.getInstance().getBEMBeanService()
					.getProcessStartTime(serviceInstance);
			serviceInstance.setUpTime(jvmUptime);
		} catch (ObjectCreationException ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		}
	}

	/**
	 * @param serviceInstance
	 */
	private void updateMemoryUsage(ServiceInstance serviceInstance) {
		try {
			List<ProcessMemoryUsageImpl> result = BEMMServiceProviderManager.getInstance().getBEMBeanService()
					.getMemoryUsage(serviceInstance);
			if (result != null && !result.isEmpty())
				serviceInstance.getMemoryUsage().setPercentUsed(result.get(0).getPercentUsed());
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		}
	}

	/**
	 * @param serviceInstance
	 */
	private void updateCPUUsage(ServiceInstance serviceInstance) {
		try {
			CPUUsage cpuUsage = BEMMServiceProviderManager.getInstance().getBEMBeanService()
					.getCPUUsage(serviceInstance);
			if (cpuUsage != null)
				serviceInstance.getCpuUsage().setCpuUsageInPercent(cpuUsage.getCpuUsageInPercent());
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		}
	}

	/**
	 * @param application
	 * @return All the service instances
	 */
	private List<ServiceInstance> getAllServiceInstances(Application application) {
		List<ServiceInstance> allServiceInstances = new ArrayList<>();
		for (Host host : application.getHosts()) {
			for (ServiceInstance serviceInstance : host.getInstances()) {
				allServiceInstances.add(serviceInstance);
			}
		}
		return allServiceInstances;
	}

	/**
	 * @param stHost
	 * @param stHostInstance
	 */
	private void initMetricsCollectorService(Host stHost, ServiceInstance stHostInstance) {
		String password = stHostInstance.getJmxPassword();
		String username = stHostInstance.getJmxUserName();
		String decodedPassword = ManagementUtil.getDecodedPwd(password);
		if (stHostInstance.getJmxPort() > 0) {
			try {

				JMXConnClient client = new JMXConnClient(stHost.getHostIp(), stHostInstance.getJmxPort(), username,
						decodedPassword, true);

				RemoteMetricsCollectorService remoteMetricsCollectorService = new JMXCPUMetricCollectorServiceImpl();
				remoteMetricsCollectorService.setMBeanServerConnection(client.getMBeanServerConnection());
				remoteMetricsCollectorService.init();
				stHostInstance.setRemoteMetricsCollectorService(remoteMetricsCollectorService);
			} catch (JMXConnClientException | RemoteMetricsCollectorServiceException ex) {
				LOGGER.log(Level.ERROR, ex, ex.getMessage());
			}
		}
	}

	@Override
	public void stop() throws Exception {

	}
}
