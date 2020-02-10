package com.tibco.cep.bpmn.runtime.agent;

import java.util.Collection;

import com.tibco.cep.runtime.service.cluster.agent.tasks.AgentAction;
import com.tibco.cep.runtime.service.cluster.agent.tasks.AgentActionManager;
import com.tibco.cep.runtime.service.cluster.agent.tasks.IAgentActionManager;

public class ProcessAgentActionManager extends AgentActionManager
{
	/**
	 * @param action
	 */
	public void addAction(AgentAction action) {
		IAgentActionManager agentActionManager = (IAgentActionManager) JobImpl.getCurrentJob();
		if(agentActionManager != null) {
			agentActionManager.addAction(action);
		}
	}
	
	/**
	 * @return
	 */
	public Collection<AgentAction> removeAll() {
		IAgentActionManager proc = (IAgentActionManager) JobImpl.getCurrentJob();
		if(proc != null) {
			return proc.removeAll();
		}
		return null;
	}
}
