/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent;

import java.util.Collection;
import java.util.Map;

import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.om.RuleFunctionService;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/*
* Author: Suresh Subramani / Date: Nov 4, 2010 / Time: 4:58:35 PM
*/

public interface AgentManager {

    /**
     * Initialise the AgentManager with new status
     *
     * @param cluster
     * @throws Exception
     */
    void init(String clusterName, RuleServiceProvider rsp, Cluster cluster) throws Exception;

    /**
     * Start the AgentManager
     *
     * @throws Exception
     */
    void start() throws Exception;

    Map<String, AgentTuple> getAgentTable();

    CacheAgent[] getLocalAgents();

    /**
     * Register the Agent to the Datagrid
     *
     * @param agent
     */
    void registerAgent(CacheAgent agent);

    /**
     * Get a agent identifier
     *
     * @return
     */
    int getNextAgentId() throws Exception;

    /**
     * Update the AgentStatus
     *
     * @param agent
     */
    void updateAgent(CacheAgent agent);

    /**
     * Get the Agent tuple info from the AgentTable Cache
     *
     * @param agentId
     * @return
     */
    AgentTuple getAgentAsTuple(int agentId);

    /**
     * Return registered agents
     *
     * @return
     */
    Collection<AgentTuple> getAgents();

    /**
     * Get the Cluster for which this provides Agent management
     *
     * @return
     */
    Cluster getCluster();

    /**
     * deregister the agent
     *
     * @param cacheAgent
     */
    void deregisterAgent(CacheAgent cacheAgent);

    /**
     * Add a agentListener
     *
     * @param listener
     */
    void addAgentListener(AgentListener listener);

    /**
     * remove a agentListener
     *
     * @param listener
     */
    void removeAgentListener(AgentListener listener);

    /**
     * Listener interface for Agents
     */
    interface AgentListener {

        void onNew(AgentTuple agent);

        void onChange(AgentTuple oldagent, AgentTuple newagent);

        void onExit(AgentTuple agent);
    }

    /**
     * This interface marks a serializable entity of the Agent that is running in a JVM.
     */

    interface AgentTuple {

        int getAgentId();

        String getAgentName();

        int getPriority();

        UID getMemberId();

        CacheAgent.Type getType();

        CacheAgent.AgentState getState();

        String getTransactionCacheName();
    }

    public void suspendAgents() throws Exception;

    public void resumeAgents() throws Exception;

    public RuleFunctionService getRuleFunctionService();
}
