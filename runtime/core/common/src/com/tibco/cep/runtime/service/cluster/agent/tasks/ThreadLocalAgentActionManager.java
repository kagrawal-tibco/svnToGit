package com.tibco.cep.runtime.service.cluster.agent.tasks;

import java.util.Collection;
import java.util.LinkedList;

public class ThreadLocalAgentActionManager extends AgentActionManager 
{
    private ThreadLocal<LinkedList<AgentAction>> m_currentActions =
            new ThreadLocal<LinkedList<AgentAction>>();

    /**
     * @param action
     */
    public void addAction(AgentAction action) {
        LinkedList<AgentAction> m_actions = m_currentActions.get();
        if (m_actions == null) {
            m_actions = new LinkedList<AgentAction>();
            m_currentActions.set(m_actions);
        }

        m_actions.add(action);
    }

    /**
     * @return
     */
    public Collection<AgentAction> removeAll() {
        LinkedList<AgentAction> m_actions = m_currentActions.get();
        if (m_actions != null) {
            m_currentActions.remove();

            return m_actions;
        }

        return null;
    }
}
