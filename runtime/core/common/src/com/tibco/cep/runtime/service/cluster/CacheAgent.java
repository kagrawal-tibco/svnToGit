/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster;

import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.runtime.service.cluster.agent.AgentConfiguration;
import com.tibco.cep.runtime.service.cluster.agent.AgentManager;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.impl.HotDeployListener;


public interface CacheAgent extends HotDeployListener {
    Cluster getCluster();

    AgentConfiguration getAgentConfig();
    
    AgentState getAgentState();

    UID getNodeId();


    String getAgentName();


    CacheAgent.Type getAgentType();

    void init(AgentManager agentManager) throws Exception;

    //boolean activate() throws Exception;

    boolean suspend() throws Exception;

    boolean resume() throws Exception;

    boolean isSuspended();

    void shutdown() throws Exception;

    //void deactivate() throws Exception;

    int getMaxActive();

    int getPriority();

    int getAgentId();

    //void registerAgent() throws Exception;

    void start(AgentState mode) throws Exception;

    void stop() throws Exception;

    String getTransactionCacheName();

    /**
     * This is same as isSeeder
     * @return
     */
    boolean isCacheServer();
    
    /**
     * Convience method
     * @param entityURI
     * @return
     * @throws Exception
     */
    public EntityDao getEntityDao(String entityURI) throws Exception;

    enum AgentState {

        ERROR("Error"),

        UNINITIALIZED("Uninitialized"),

        INITIALIZED("Initialized"),

        REGISTERED("Registered"),

        PREPARETOACTIVATE("Prepare to Activate"),

        ACTIVATED("Activated"),

        PREPARETODEACTIVATE("Prepare to Deactivate"),

        DEACTIVATED("Deactivated"),

        SHUTDOWN("Shutdown"),

        CLEANUP("Cleanup In Progress");


        String stateName;

        AgentState(String stateName) {
            this.stateName = stateName;
        }

        String getStateName() {
            return this.stateName;
        }
    }

    enum Type {
        UNDEFINED("Undefined"),
        INFERENCE("Inference"),
        CACHESERVER("DataCache"),
        CHANNEL("Channel"),
        MASTER("Master"),
        QUERY("Query"),
        DASHBOARD("Dashboard"),
        PROCESS("Process"),
        LIVEVIEW("LiveView");

        String name;

        Type(String name) {
            this.name = name;
        }

        String getName() {
            return this.name;
        }
    }

}
