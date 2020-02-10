package com.tibco.cep.bemm.management.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.bemm.common.job.BETeaStatusUpdateJob;
import com.tibco.cep.bemm.common.model.BETeaOperationResult;
import com.tibco.cep.bemm.common.pool.GroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.GroupJobExecutorService;
import com.tibco.cep.bemm.common.pool.jsch.JSchGroupJobExecutionContext;
import com.tibco.cep.bemm.common.pool.jsch.SshConfig;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.service.BEApplicationDiscoveryService;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

/**
 * To discover/update statuses of instances from the JMX MBeans
 * 
 * @author vdhumal
 */
public class BEApplicationInstancesBasedDiscoveryServiceImpl implements BEApplicationDiscoveryService {

	/**
	 * Logger Object
	 */
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEApplicationDiscoveryService.class);

	private BEApplicationsManagementService beApplicationsManagementService;
	protected GroupJobExecutorService poolService;

	public BEApplicationInstancesBasedDiscoveryServiceImpl() throws Exception {
		this.beApplicationsManagementService = BEMMServiceProviderManager.getInstance()
				.getBEApplicationsManagementService();
		this.poolService = BEMMServiceProviderManager.getInstance().getPollerExecutorService();
	}

	@Override
	public void init(Properties properties) throws Exception {

	}

	@Override
	public boolean discover(Application application) {
		boolean isAppRunning = false;
		try {
			List<ServiceInstance> startingServiceInstances = new ArrayList<>();
			List<ServiceInstance> otherServiceInstances = new ArrayList<>();

			// Segregate the Starting & rest of the service instances
			segregateStartingAndOtherInstances(application, startingServiceInstances, otherServiceInstances);

			// Poll the starting service instances first
			if (startingServiceInstances != null && !startingServiceInstances.isEmpty()) {
				boolean isRunning = pollServiceInstances(startingServiceInstances);
				if (isRunning)
					isAppRunning = isRunning;
			}

			// Poll the rest service instances
			if (otherServiceInstances != null && !otherServiceInstances.isEmpty()) {
				boolean isRunning = pollServiceInstances(otherServiceInstances);
				if (isRunning)
					isAppRunning = isRunning;
			}
			//Set the deployment status of application in case of monitoring applications
			if (application.isMonitorableOnly()) {
				boolean isAllInstancesUndeployed = false;
				for (ServiceInstance serviceInstance : startingServiceInstances) {
					if (BETeaAgentStatus.UNDEPLOYED.getStatus().equals(serviceInstance.getStatus())) {
						isAllInstancesUndeployed = true;
					} else {
						isAllInstancesUndeployed = false;
						break;
					}
				}
				if (!isAllInstancesUndeployed) {
					for (ServiceInstance serviceInstance : otherServiceInstances) {
						if (BETeaAgentStatus.UNDEPLOYED.getStatus().equals(serviceInstance.getStatus())) {
							isAllInstancesUndeployed = true;
						} else {
							isAllInstancesUndeployed = false;
							break;
						}
					}
				}
				if (isAllInstancesUndeployed) {
					application.setDeploymentStatus(BETeaAgentStatus.UNDEPLOYED.getStatus());
				}
			}
			// Set the application status
			ManagementUtil.setRunningStatus(application);
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
		}
		return isAppRunning;
	}

	/**
	 * @param application
	 * @param appStartingInstances
	 * @param appOtherInstances
	 */
	private void segregateStartingAndOtherInstances(Application application, List<ServiceInstance> appStartingInstances,
			List<ServiceInstance> appOtherInstances) {
		// Get all instances of application
		Collection<Host> hosts = application.getHosts();
		for (Host host : hosts) {
			if (null != host) {
				for (ServiceInstance serviceInstance : host.getInstances()) {
					if (null != serviceInstance) {
						if (BETeaAgentStatus.STARTING.getStatus().equals(serviceInstance.getStatus())) {
							appStartingInstances.add(serviceInstance);
						} else {
							appOtherInstances.add(serviceInstance);
						}
					}
				}
			}
		}
	}

	/**
	 * @param serviceInstances
	 * @return
	 * @throws Exception
	 */
	private boolean pollServiceInstances(List<ServiceInstance> serviceInstances) throws Exception {
		if (null != serviceInstances) {
			List<GroupJobExecutionContext> jobExecutionContexts = new ArrayList<>();
			for (ServiceInstance serviceInstance : serviceInstances) {
				if (serviceInstance.getHost().getApplication().isMonitorableOnly()
						|| !BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(serviceInstance.getStatus())) {
					SshConfig sshConfig = getSshConfig(serviceInstance);
					jobExecutionContexts.add(new JSchGroupJobExecutionContext(serviceInstance, sshConfig));
				}
			}
			// Submit the job
			List<Object> results = poolService.submitJobs(new BETeaStatusUpdateJob(beApplicationsManagementService),
					jobExecutionContexts);
			int errorCnt = 0;
			for (Object object : results) {
				BETeaOperationResult operationResult = (BETeaOperationResult) object;
				Object result = operationResult.getResult();
				if (result instanceof Exception) {
					errorCnt++;
				}
			}
			if (errorCnt != serviceInstances.size())
				return true;
		}

		// if all are false return false
		return false;
	}

	@Override
	public void stop() throws Exception {

	}

	/**
	 * Get SSH config
	 * 
	 * @param serviceInstance
	 * @return
	 */
	public SshConfig getSshConfig(ServiceInstance serviceInstance) {
		SshConfig sshConfig = new SshConfig();
		Host host = serviceInstance.getHost();
		sshConfig.setHostIp(host.getHostIp());
		sshConfig.setPassword(host.getPassword());
		sshConfig.setPort(host.getSshPort());
		sshConfig.setUserName(host.getUserName());
		return sshConfig;
	}

}
