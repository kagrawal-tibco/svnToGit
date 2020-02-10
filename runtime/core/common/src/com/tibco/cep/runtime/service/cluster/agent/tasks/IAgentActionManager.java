package com.tibco.cep.runtime.service.cluster.agent.tasks;

import java.util.Collection;

public interface IAgentActionManager {

    void addAction(AgentAction action);

    Collection<AgentAction> removeAll();
}
