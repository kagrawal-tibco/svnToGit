/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent;

import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.RuleSession;


public abstract class AbstractCacheAgent implements CacheAgent {

    public final static String CACHESERVER_SUFFIX = "$CacheServer";

    protected AgentState agentState;
    protected Cluster cluster;
    protected RuleSession ruleSession;
    protected UID nodeId;
    protected AgentConfiguration agentConfig;
    protected String name;
    protected AtomicBoolean suspended;
    protected CacheAgent.Type type;
    protected final Logger logger = LogManagerFactory.getLogManager().getLogger(this.getClass());
    private AgentManager agentManager;
    private int agentId;

    public AbstractCacheAgent(AgentConfiguration agentConfig, CacheAgent.Type type) {
        this.agentConfig = agentConfig;
        this.agentState = AgentState.UNINITIALIZED;
        this.name = agentConfig.getAgentName();
        this.type = type;
        this.suspended = new AtomicBoolean();
    }

    public AgentState getAgentState() {
        return agentState;
    }

    public Cluster getCluster() {
        return cluster;
    }

    /**
     * NodeId is same as Member Id.
     * @return
     */
    public UID getNodeId() {
        if (cluster == null) {
        	return null;
        }
        if (nodeId == null) {
            nodeId = cluster.getGroupMembershipService().getLocalMember().getMemberId();
        }
        return nodeId;
    }

    public EnumMap<AgentCapability, Object> getAgentCapabilities() {
        DefaultAgentConfiguration dac = (DefaultAgentConfiguration) agentConfig;
        return dac.agentCapabilities;
    }

    public AgentConfiguration getAgentConfig() {
        return this.agentConfig;
    }

    public String getName() {
        return name;
    }

    public String getAgentName() {
        return name;
    }

    public CacheAgent.Type getAgentType() {
        return type;
    }

    public int getMaxActive() {
        return (Integer)AgentCapability.MAXACTIVE.getDefaultValue();
    }

    public int getPriority() {
        return (Integer)AgentCapability.PRIORITY.getDefaultValue();
    }

    public int getAgentId() {
        return agentId;
    }

    @Override
    public String getTransactionCacheName() {
        return null;
    }

    public void init(AgentManager agentManager) throws Exception {
        this.agentManager = agentManager;
        this.cluster = agentManager.getCluster();
        if (agentState == AgentState.UNINITIALIZED) {
            agentId = this.agentManager.getNextAgentId();
            onInit();
            agentState = AgentState.INITIALIZED;
            logger.log(Level.INFO, "Agent Initialized: %s", this);
        }
    }

    protected abstract void onInit() throws Exception;

    /**
     * @throws Exception
     */
    private final void prepareToActivate(boolean reactivate) throws Exception {
        if ((agentState != AgentState.PREPARETOACTIVATE) && (agentState != AgentState.ACTIVATED)) {
            onPrepareToActivate(reactivate);
            agentState = AgentState.PREPARETOACTIVATE;

            this.agentManager.updateAgent(this);
        }
    }

    protected abstract void onPrepareToActivate(boolean reactivate) throws Exception;

    private final void register()  throws Exception{
        onRegister();
        this.agentManager.registerAgent(this);
    }

    protected abstract void onRegister() throws Exception;


    protected abstract void onPrepareToDeActivate() throws Exception;

    /**
     *
     */
    private void activate(boolean reactivate) throws Exception {
        if (agentState == AgentState.PREPARETOACTIVATE) {
            onActivate(reactivate);
            agentState = AgentState.ACTIVATED;
            this.agentManager.updateAgent(this);
        }
    }

    @Override
    public void start(AgentState mode) throws Exception {
        if (mode == AgentState.REGISTERED) {
            register();
        }
        else if (mode == AgentState.ACTIVATED) {
        	boolean reactivate = getAgentState() == AgentState.DEACTIVATED;
            prepareToActivate(reactivate);
            activate(reactivate);
        }
        else {
            throw new Exception("Invalid start mode specified for the Agent");
        }
    }

    protected abstract void onActivate(boolean reactivate) throws Exception;

    public boolean suspend() throws Exception {
        if (!suspended.get()) {
            onSuspend();
            suspended.compareAndSet(false, true);
            this.agentManager.updateAgent(this);
            return true;
        }
        else {
            return false; // Ignored!
        }
    }

    /**
     * Deactivate an external class loaded into the classloader of every
     * inference agent
     *
     * @throws Exception
     */
    protected abstract void deActivateExternalClass() throws Exception;

    protected abstract void onSuspend() throws Exception;

    public boolean resume() throws Exception {
        if (suspended.get()) {
            onResume();
            suspended.compareAndSet(true, false);
            this.agentManager.updateAgent(this);
            return true;
        }
        else {
            return false; // Ignored!
        }
    }

    public void onTimeEvent(Object key) {
        throw new RuntimeException("onTimeEvent() not supported by this agent");
    }

    protected abstract void onResume() throws Exception;

    public boolean isSuspended() {
        return suspended.get();
    }

    public void shutdown() throws Exception {
        if (agentState != AgentState.SHUTDOWN) {
            onShutdown();
            agentState = AgentState.SHUTDOWN;
            this.agentManager.deregisterAgent(this);
        }
    }

    protected abstract void onShutdown() throws Exception;

    public void stop() throws Exception{
        prepareToDeactivate();
        deactivate();
    }

    public void prepareToDeactivate() throws Exception {
        if ((agentState != AgentState.PREPARETODEACTIVATE) && (agentState != AgentState.DEACTIVATED)) {
            onPrepareToDeActivate();
            agentState = AgentState.PREPARETODEACTIVATE;
            this.agentManager.updateAgent(this);
        }
    }

    private final synchronized void deactivate() throws Exception {
		if (agentState == AgentState.PREPARETODEACTIVATE) {
            onDeactivate();
            agentState = AgentState.DEACTIVATED;
            this.agentManager.updateAgent(this);
        }
    }

    protected abstract void onDeactivate() throws Exception;

    @Override
    public boolean isCacheServer() {
        return type == Type.CACHESERVER;
    }

    @Override
    public EntityDao getEntityDao(String entityURI) throws Exception {
        TypeManager.TypeDescriptor td = cluster.getRuleServiceProvider().getTypeManager().getTypeDescriptor(entityURI);
        if (td != null) {
            return cluster.getDaoProvider().getEntityDao(td.getImplClass());
        }
        return null;
    }

    protected void printInitializedInfo() { // TODO: Implement so it can be made avail in base class
//        if (logger.isEnabledFor(Level.INFO)) {
//            logger.log(Level.INFO, "---------------------------------Agent: " + this.getAgentName() + " Initialization Info-----------------------------------");
//            logger.log(Level.INFO, "--Name=" + this.getAgentName());
//            logger.log(Level.INFO, "--Cluster Name=" + this.getCluster().getClusterName());
//            logger.log(Level.INFO, "--CACHESERVER Mode=" + objectStore.isCacheServer());
//            logger.log(Level.INFO, "--ID=" + this.getAgentId());
//            logger.log(Level.INFO, "--2XMode=" + this.use2xMode);
//        
//            if (!objectStore.isCacheServer()) {
//                logger.log(Level.INFO, "--MaxActive=" + this.getMaxActive());
//                logger.log(Level.INFO, "--Priority=" + this.getPriority());
//                logger.log(Level.INFO, "--ConcurrentRETE Enabled=" + this.getAgentConfig().isConcurrent());
//                logger.log(Level.INFO, "--ParallelWrites Enabled=" + this.getAgentConfig().isEnableParallelOps());
//                logger.log(Level.INFO, "--DuplicateCheck Enabled=" + this.getAgentConfig().isDuplicateCheckOn());
//                logger.log(Level.INFO, "--L1CacheSize=" + this.getAgentConfig().getL1CacheSize());
//                logger.log(Level.INFO, "--Cache ThreadPool=" + this.getAgentConfig().getThreadCount());
//                logger.log(Level.INFO, "--Cache QueueSize=" + this.getAgentConfig().getCacheOpsQueueSize());
//        
//                if (cluster.getClusterConfig().isCacheAside()) {
//                    logger.log(Level.INFO, "--DB ThreadPool=" + this.getAgentConfig().getDBThreadCount());
//                    logger.log(Level.INFO, "--DB QueueSize=" + this.getAgentConfig().getDBOpsQueueSize());
//                }
//
//                logger.log(Level.INFO, "--System Subscriptions [Used to keep agents in sync]");
//                for (int i = 0; i < this.deleteSubscriptions.length; i++) {
//                    int type_id = i + MetadataCache.BE_TYPE_START;
//                    Class entityClz = cluster.getMetadataCache().getClass(type_id);
//                    if (deleteSubscriptions[i])
//                        logger.log(Level.INFO, "  -- " + entityClz);
//                }
//
//                logger.log(Level.INFO, "--User Subscriptions [Used to keep agents in sync]");
//                for (int i = 0; i < this.changeSubscriptions.length; i++) {
//                    int type_id = i + MetadataCache.BE_TYPE_START;
//                    Class entityClz = cluster.getMetadataCache().getClass(type_id);
//                    if (changeSubscriptions[i])
//                        logger.log(Level.INFO, "  -- " + entityClz + " : Preprocessor=" + this.getEntityConfig(entityClz).getRuleFunctionUri());
//                }
//            }
//        }
    }
}
