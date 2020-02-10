package com.tibco.cep.bemm.management.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.bemm.common.exception.MBeanOperationFailException;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MBeanService;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.ValidationService;
import com.tibco.cep.bemm.management.exception.BEAgentManagementServiceException;
import com.tibco.cep.bemm.management.service.BEAgentManagementService;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.AgentConfig;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.BEMMModelFactory;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.Summary;
import com.tibco.cep.bemm.model.impl.BEMMModelFactoryImpl;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

/**
 * Implements the methods defined in BEAgentManagementService interface
 * 
 * @author dijadhav
 *
 */
public class BEAgentManagementServiceImpl extends AbstractStartStopServiceImpl implements BEAgentManagementService {
	/**
	 * BE TEA agent model factory instance.
	 */
	private BEMMModelFactory agentModelFactory;// = BEMMModelFactoryImpl.getInstance();
	private MessageService messageService;
	private ValidationService validationService;
	private MBeanService mbeanService;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.services.BEAgentManagementService#suspend(com.
	 * tibco.tea.agent.be.model.Agent)
	 */
	@Override
	public String suspend(Agent agent, String loggeInUser) throws BEAgentManagementServiceException {
		try {

			String clusterName=agent
					.getInstance().getHost().getApplication().getClusterName();
			mbeanService.invoke("Inference", "Agent", "Suspend", null, agent.getInstance().getHost().getUserName(),
					agent.getInstance().getHost().getHostName(), agent.getInstance().getHost().getHostIp(), agent
							.getInstance().getJmxPort(), agent.getAgentId(), agent
							.getInstance().getKey(),  clusterName);
		} catch (MBeanOperationFailException e) {
			throw new BEAgentManagementServiceException(e.getMessage(), e);
		}
		agent.setStatus(BETeaAgentStatus.SUSPENDED.getStatus());
		return agent.getAgentName() + " is suspended successfully";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.services.BEAgentManagementService#resume(com.tibco
	 * .tea.agent.be.model.Agent)
	 */
	@Override
	public String resume(Agent agent, String loggeInUser) throws BEAgentManagementServiceException {
		try {
			String clusterName=agent
					.getInstance().getHost().getApplication().getClusterName();
			mbeanService.invoke("Inference", "Agent", "Resume", null, agent.getInstance().getHost().getUserName(),
					agent.getInstance().getHost().getHostName(), agent.getInstance().getHost().getHostIp(), agent
							.getInstance().getJmxPort(), agent.getAgentId(), agent
							.getInstance().getKey(), clusterName);
		} catch (MBeanOperationFailException e) {
			throw new BEAgentManagementServiceException(e.getMessage(), e);
		}
		agent.setStatus(BETeaAgentStatus.RUNNING.getStatus());
		return agent.getAgentName() + " is resumed successfully";
	}

	@Override
	public Summary getAgentSummary(Agent agent) throws BEAgentManagementServiceException {
		try {

			Summary appSummary = null;
			try {
				appSummary = agentModelFactory.getSummary();
				int upInstanceCount = 0;
				int downInstanceCount = 0;
				int intermediateStateInstances = 0;
				Application application = agent.getInstance().getHost().getApplication();
				Collection<Host> hosts = application.getHosts();

				for (Host applicationHost : hosts) {
					try {
						for (ServiceInstance serviceInstance : applicationHost.getInstances()) {
							for (Agent instanceAgent : serviceInstance.getAgents()) {
								if (instanceAgent.getAgentName().endsWith(agent.getAgentName())) {
									if (BETeaAgentStatus.RUNNING.getStatus().equalsIgnoreCase(
											serviceInstance.getStatus()))
										upInstanceCount++;
									else if (BETeaAgentStatus.STARTING.getStatus().equalsIgnoreCase(
											serviceInstance.getStatus())
											|| BETeaAgentStatus.STOPPING.getStatus().equalsIgnoreCase(
													serviceInstance.getStatus())
											|| BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equalsIgnoreCase(
													serviceInstance.getStatus()))
										intermediateStateInstances++;
									else
										downInstanceCount++;
								}
							}

						}
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
				appSummary.setUpInstances(upInstanceCount);
				appSummary.setDownInstances(downInstanceCount);
				appSummary.setIntermediateStateInstances(intermediateStateInstances);
			} catch (ObjectCreationException e) {
				throw new BEAgentManagementServiceException(e.getMessage(), e);
			}
			return appSummary;

		} catch (Exception e) {
			throw new BEAgentManagementServiceException(e.getMessage(), e);
		}
	}

	@Override
	public OperationResult invoke(String entityName, String methodGroup, String methodName, Map<String, String> params,
			Agent agent) throws BEAgentManagementServiceException {
		try {
			String clusterName=agent
					.getInstance().getHost().getApplication().getClusterName();
			OperationResult operationResult = new OperationResult();
			operationResult = mbeanService.invoke(entityName, methodGroup, methodName, params, agent.getInstance()
					.getJmxUserName(), agent.getInstance().getJmxPassword(), agent.getInstance().getHost().getHostIp(),
					agent.getInstance().getJmxPort(), agent.getAgentId(), agent
					.getInstance().getKey(),  clusterName);
			return operationResult;
		} catch (MBeanOperationFailException e) {
			throw new BEAgentManagementServiceException(e.getMessage(), e);
		}
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
	public Summary getApplicationAgentSummary(AgentConfig agentConfig, Application application)
			throws BEAgentManagementServiceException {

		if (null != application) {

			for (Host host : application.getHosts()) {
				for (ServiceInstance serviceInstance : host.getInstances()) {
					for (Agent instanceAgent : serviceInstance.getAgents()) {
						if (instanceAgent.getAgentType().equals(agentConfig.getAgentType())
								&& instanceAgent.getAgentName().equals(agentConfig.getAgentName())) {
							return getAgentSummary(instanceAgent);
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public void init(Properties configuration) throws Exception {
		agentModelFactory = BEMMModelFactoryImpl.getInstance();
		messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		mbeanService = BEMMServiceProviderManager.getInstance().getBEMBeanService();
		validationService = BEMMServiceProviderManager.getInstance().getValidationService();
	}

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suspend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isStarted() {
		// TODO Auto-generated method stub
		return false;
	}

}
