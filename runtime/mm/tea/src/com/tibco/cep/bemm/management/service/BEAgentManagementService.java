package com.tibco.cep.bemm.management.service;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.service.MBeanService;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.ValidationService;
import com.tibco.cep.bemm.management.exception.BEAgentManagementServiceException;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.AgentConfig;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Summary;
import com.tibco.rta.common.service.StartStopService;

/**
 * This interface is used to perform the operations on BEAgent
 * 
 * @author dijadhav
 *
 */
public interface BEAgentManagementService extends StartStopService {
	/**
	 * This method is used to suspend the agent
	 * 
	 * @param agent
	 *            - Business Events Agent
	 * @return
	 */
	String suspend(Agent agent, String loggedInUser) throws BEAgentManagementServiceException;

	/**
	 * This method is used to resume the agent
	 * 
	 * @param agent
	 *            - Business Events Agent
	 */
	String resume(Agent agent, String loggedInUser) throws BEAgentManagementServiceException;

	/**
	 * This method is used to get the Summary of agent
	 * 
	 * @param agent
	 * @return
	 */
	Summary getAgentSummary(Agent agent) throws BEAgentManagementServiceException;

	/**
	 * Invoke the different methods on agent
	 * 
	 * @param entityName
	 * @param methodGroup
	 * @param methodName
	 * @param params
	 * @param agent
	 * @return
	 * @throws BEAgentManagementServiceException
	 */
	OperationResult invoke(String entityName, String methodGroup, String methodName, Map<String, String> params,
			Agent agent) throws BEAgentManagementServiceException;

	/**
	 * @return the messageService
	 */
	@JsonIgnore
	public MessageService getMessageService();

	/**
	 * @param messageService
	 *            the messageService to set
	 */
	@JsonIgnore
	public void setMessageService(MessageService messageService);

	/**
	 * @return the validationService
	 */
	@JsonIgnore
	public ValidationService getValidationService();

	/**
	 * @param validationService
	 *            the validationService to set
	 */
	@JsonIgnore
	public void setValidationService(ValidationService validationService);

	/**
	 * Get the MBeanService instance
	 * 
	 * @return MBeanService instance
	 */
	@JsonIgnore
	MBeanService getMbeanService();

	/**
	 * Set the MBeanService instance
	 * 
	 * @return MBeanService instance
	 */
	@JsonIgnore
	void setMbeanService(MBeanService mbeanService);

	Summary getApplicationAgentSummary(AgentConfig agentConfig, Application application)
			throws BEAgentManagementServiceException;
}
