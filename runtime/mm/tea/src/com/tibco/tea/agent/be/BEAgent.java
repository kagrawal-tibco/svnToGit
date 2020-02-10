package com.tibco.tea.agent.be;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.service.BEAgentManagementService;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Summary;
import com.tibco.cep.bemm.monitoring.metric.view.ViewData;
import com.tibco.tea.agent.annotations.Customize;
import com.tibco.tea.agent.annotations.TeaOperation;
import com.tibco.tea.agent.annotations.TeaParam;
import com.tibco.tea.agent.annotations.TeaReference;
import com.tibco.tea.agent.annotations.TeaRequires;
import com.tibco.tea.agent.api.MethodType;
import com.tibco.tea.agent.api.TeaObject;
import com.tibco.tea.agent.api.TeaPrincipal;
import com.tibco.tea.agent.api.WithConfig;
import com.tibco.tea.agent.api.WithStatus;
import com.tibco.tea.agent.be.permission.Permission;
import com.tibco.tea.agent.be.provider.BEAgentProvider;
import com.tibco.tea.agent.be.provider.ObjectCacheProvider;
import com.tibco.tea.agent.internal.types.ClientType;
import com.tibco.tea.agent.support.TeaException;
import com.tibco.tea.agent.types.AgentObjectStatus;

/**
 * This class represents the BusinessEvents Agent in TEA object hierarchy
 * 
 * @author dijadhav
 *
 */

public class BEAgent implements TeaObject,WithStatus , WithConfig<Agent> {
	
	
	@Override
	public Agent getConfig() {
		return this.getAgent();
	}
	
	
	/**
	 * Logger instance
	 */
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEAgent.class);

	/**
	 * Object holds the details of the Business Events agent details.
	 */
	private Agent agent;
	@JsonIgnore
	private BEAgentManagementService agentService;

	/**
	 * Parameterized constructor
	 * 
	 * @param agent
	 *            - Agent object
	 */
	public BEAgent(Agent agent) {
		this.agent = agent;
	}

	/**
	 * Get type of Agent
	 * 
	 * @return Type of agent
	 */
	public String getType() {
		return agent.getAgentType().name();
	}

	/**
	 * Description of the agent
	 */
	@Override
	public String getDescription() {
		return null;
	}

	/**
	 * Name of the agent
	 */
	@Override
	public String getName() {
		return agent.getAgentName();
	}

	/**
	 * Key of the agent
	 */

	@Override
	public String getKey() {
		return agent.getKey();
	}

	/**
	 * @return the agent
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * @param agent
	 *            the agent to set
	 */
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@TeaOperation(name = "getAgentInfo", description = "gets the agent info", methodType = MethodType.READ)
	public Agent getAgentInfo() {
		return this.agent;
	}

	@Customize(value = "label:Suspend;if:state=Running")
	@TeaOperation(name = "suspend", description = "Suspend the agent class", methodType = MethodType.UPDATE)
	@TeaRequires(value=Permission.SUSPEND_AGENT_PERMISSION)
	public String pause(TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			return agentService.suspend(getAgent(), loggedInUser);
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
			throw new TeaException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	@Customize(value = "label:Resume;if:state=Suspended")
	@TeaOperation(name = "resume", description = "Resume the agent class", methodType = MethodType.UPDATE)
	@TeaRequires(value=Permission.RESUME_AGENT_PERMISSION)
	public void resume(TeaPrincipal teaPrincipal) {
		try {
			String loggedInUser = teaPrincipal.getName();
			agentService.resume(getAgent(), loggedInUser);
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
			throw new TeaException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	@TeaReference(name = "ServiceInstance")
	public BEServiceInstance getServiceInstance() {
		BEServiceInstance beServiceInstance = (BEServiceInstance) ObjectCacheProvider.getInstance()
				.getProvider(Constants.BE_SERVICE_INSTANCE).getInstance(agent.getInstance().getKey());
		return beServiceInstance;
	}

	public void registerWithObjectProvider() {
		BEAgentProvider agentProvider = (BEAgentProvider) ObjectCacheProvider.getInstance().getProvider(
				Constants.BE_AGENT);
		if (null == agentProvider.getInstance(this.getKey())) {
			agentProvider.add(this.getKey(), this);
		}
	}

	@Customize(value = "label:Get Summary")
	@TeaOperation(name = "getSummary", description = "Resume the agent class", methodType = MethodType.READ)
	public Summary getSummary() {
		try {
			return agentService
					.getAgentSummary(this.getAgent());
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
			throw new TeaException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	@Customize(value = "label:Invoke")
	@TeaOperation(name = "invoke", description = "Invoke the passed method on given entity ", methodType = MethodType.READ, hideFromClients = ClientType.PYTHON)
	public OperationResult invoke(@TeaParam(name = "entityName", alias = "entityName") String entityName,
			@TeaParam(name = "methodGroup", alias = "methodGroup") String methodGroup,
			@TeaParam(name = "methodName", alias = "methodName") String methodName,
			@TeaParam(name = "params", alias = "params", defaultValue = "null") Map<String, String> params) {
		try {
			return agentService
					.invoke(entityName, methodGroup, methodName, params, agent);
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
			throw new TeaException(ex.getMessage(), ex.getMessage(), ex);
		}
	}


	/**
	 * @return the agentService
	 */
	@JsonIgnore
	public BEAgentManagementService getAgentService() {
		return agentService;
	}

	/**
	 * @param agentService
	 *            the agentService to set
	 */
	@JsonIgnore
	public void setAgentService(BEAgentManagementService agentService) {
		this.agentService = agentService;
	}
	
	@Customize(value = "label:Get Chart data")
	@TeaOperation(name = "getChartData", description = "", methodType = MethodType.READ)
	public ViewData getChartData(
			@TeaParam(name = "sectionId", alias = "sectionId") int sectionId,
			@TeaParam(name = "chartId", alias = "chartId") int chartId,
			@TeaParam(name = "dimLevel", alias = "dimLevel") String dimLevel,
			@TeaParam(name = "threshold", alias = "threshold") Long threshold,
			@TeaParam(name = "appName", alias = "appName") String appName) {
		try {
			return BEMMServiceProviderManager.getInstance().getMetricVisulizationService().getChart(this.getAgent(),sectionId,chartId,dimLevel,threshold,appName);
			
		} catch (Exception ex) {
			LOGGER.log(Level.ERROR, ex, ex.getMessage());
			throw new TeaException(ex.getMessage(), ex.getMessage(), ex);
		}
	}
	@Override
	public AgentObjectStatus getStatus() {
		AgentObjectStatus status = new AgentObjectStatus();
		status.setState(agent.getStatus());
		return status;
	}
}
