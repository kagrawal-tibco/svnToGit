/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.query.stream.impl.rete.service;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.query.service.QueryProperty;
import com.tibco.cep.query.stream.core.ControllableResource;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.monitor.*;
import com.tibco.cep.query.stream.monitor.Logger.LogLevel;
import com.tibco.cep.query.stream.util.BQLHelper;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.AbstractCacheAgent;
import com.tibco.cep.runtime.service.cluster.agent.AgentConfiguration;
import com.tibco.cep.runtime.service.cluster.events.notification.ClusterEntityListener;
import com.tibco.cep.runtime.service.cluster.events.notification.ClusterEntityMediator;
import com.tibco.cep.runtime.service.cluster.events.notification.EntityMediator;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.management.agent.AgentMBeansManager;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.HotDeployListener;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.util.SystemProperty;

import java.util.Collection;
import java.util.LinkedList;

/*
* Author: Ashwin Jayaprakash Date: Apr 28, 2008 Time: 12:01:49 PM
*/

/**
 * Not to be created at all if {@link com.tibco.cep.runtime.service.cluster.om.DefaultDistributedCacheBasedStore#isCacheServer()} is
 * <code>true</code>.
 */
public class QueryAgent extends AbstractCacheAgent implements ControllableResource, AgentService, HotDeployListener {
    private Cluster cacheCluster;

    private RuleSession ruleSession;

    private EntityMediator entityMediator;

    protected ResourceId resourceId;

    private volatile ClusterEntityListener listener;

    private boolean restQueryEnabled = false;
    //private QueryService restQueryService;

    /**
     * @param agentConfig
     * @param ruleSession
     * @throws Exception
     */
    public QueryAgent(AgentConfiguration agentConfig, RuleSession ruleSession) throws Exception {
        super(agentConfig, CacheAgent.Type.QUERY);

        String s = QueryAgent.class.getName() + ":" + agentConfig.getAgentName();
        this.resourceId = new ResourceId(s);

        this.ruleSession = ruleSession;

        this.restQueryEnabled = Boolean.valueOf(System.getProperty(SystemProperty.QUERY_RESTSERVICE_ENABLED.getPropertyName(),
                SystemProperty.QUERY_RESTSERVICE_ENABLED.getValidValues()[0].toString()));

        /*
        if (this.restQueryEnabled) {
            this.restQueryService = new QueryService();
            this.restQueryService.start();
        }
        */
    }


    public void stop() throws Exception {
        super.stop();
        listener = null;

        entityMediator = null;

        resourceId.discard();
        resourceId = null;

        /*
        if (this.restQueryEnabled) {
            this.restQueryService.stop();
        }
        */
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    //-----------

    public int getAgentPartitionID() {
        return 0;
    }

    /**
     * By the time this method gets invoked, {@link #listenerReady(com.tibco.cep.runtime.service.cluster.events.notification.ClusterEntityListener)}
     * should've been invoked.
     *
     * @throws Exception if the {@link #listener} is <code>null</code>.
     */
    protected void onInit() throws Exception {
        if (listener == null) {
            throw new CustomException(resourceId,
                    ClusterEntityListener.class.getName() + " is null/not ready.");
        }

        ((RuleSessionImpl)this.ruleSession).init(false);

        cacheCluster = ruleSession.getRuleServiceProvider().getCluster();
    }

    @Override
    protected void onRegister() throws Exception {
        // Registers all of the Agent MBeans for this QueryAgent. These Agent MBeans expose the methods exposed by Hawk
        new AgentMBeansManager(this).registerAgentMBeans();
    }

    protected void onPrepareToActivate(boolean reactivate) throws Exception {
        try {
            final String agentNameStr = "Agent " + this.getAgentName() + "-" + this.getAgentId();

            logger.log(Level.INFO, agentNameStr + ": Prepare To Activate");

            final RuleServiceProviderImpl provider =
                    (RuleServiceProviderImpl) cacheCluster.getRuleServiceProvider();

            //-----------------

            Logger logger = Registry.getInstance().getComponent(Logger.class);

            if (!reactivate) {
	            String allowCQ = provider.getProperties()
	                    .getProperty(QueryProperty.ALLOW_CONTINUOUS_QUERY.getPropName(), Boolean.FALSE.toString());
	
	            String allowCQLogMsg = "Continuous query feature (" + QueryProperty.ALLOW_CONTINUOUS_QUERY.getPropName() + ") is ";
	            
	            if (Boolean.parseBoolean(allowCQ)) {
	                LinkedList<Integer> allConceptsAndEventTypeIds = new LinkedList<Integer>();
	                allConceptsAndEventTypeIds
	                        .addAll(cacheCluster.getMetadataCache().getRegisteredConceptTypes());
	                allConceptsAndEventTypeIds
	                        .addAll(cacheCluster.getMetadataCache().getRegisteredSimpleEventTypes());
	
	                cacheCluster.getTopicRegistry()
	                        .register(this, allConceptsAndEventTypeIds, allConceptsAndEventTypeIds);
	
	                logger.log(LogLevel.INFO, allowCQLogMsg + "enabled");
	            }
	            else {
	                logger.log(LogLevel.INFO, allowCQLogMsg + "disabled");
	            }
            }
            
            //-----------------

            //entityMediator = cluster.addClusterEntityListener(listener, false);
            entityMediator = new ClusterEntityMediator(cluster, listener, agentConfig.isPublishTxn());
            entityMediator.startMediator(this.getAgentId());

            //-----------------

            if (!reactivate) this.ruleSession.start(true);
            else ((RuleSessionImpl)ruleSession).resume();
            
            provider.bind(ruleSession);

            if (BQLHelper.isBQLConsoleEnabled(provider.getProperties(), getAgentName()) == false) {
                provider.startChannels(ruleSession, ChannelManager.ACTIVE_MODE);
            }
            else {
                logger.log(Logger.LogLevel.WARNING, "Channel activation disabled for Agent:" + getAgentName() +
                        " Id:" + getAgentId() + " Name:" + getName());
            }
            
            if (reactivate) {
            	((RuleSessionImpl)ruleSession).resume();
            	AgentServiceHelper.resumeQueriesInRegion(getAgentName());
            }
        }
        catch (Throwable t) {
            doPrepareToDeActivate(false);

            if (t instanceof Exception) {
                throw (Exception) t;
            }

            throw new Exception(t);
        }
    }

    protected void onActivate(boolean reactivate) throws Exception {
    }

    protected void doPrepareToDeActivate(boolean logErrors) throws CustomMultiSourceException {
        final CustomMultiSourceException errorRecorder =
                logErrors ? new CustomMultiSourceException(resourceId) : null;

        AgentServiceHelper.pauseQueriesInRegion(resourceId, getAgentName());

        RuleServiceProviderImpl provider =
                (RuleServiceProviderImpl) cacheCluster.getRuleServiceProvider();

        try {
            provider.unbindRuleSession(ruleSession);
        }
        catch (Exception e) {
            if (logErrors) {
                errorRecorder.addSource(new CustomMultiSourceException.Source(e));
            }
        }

        ((RuleSessionImpl) ruleSession).suspend();

        if (logErrors && errorRecorder.hasSources()) {
            throw errorRecorder;
        }
    }

    protected void onPrepareToDeActivate() throws Exception {
        doPrepareToDeActivate(true);
    }

    protected void onDeactivate() throws Exception {
    }

    protected void doSuspend(boolean logErrors) throws CustomMultiSourceException {
        final CustomMultiSourceException errorRecorder =
                logErrors ? new CustomMultiSourceException(resourceId) : null;

        try {
            ruleSession.getTaskController().suspend();
        }
        catch (Exception e) {
            if (logErrors) {
                errorRecorder.addSource(new CustomMultiSourceException.Source(e));
            }
        }

        try {
            entityMediator.suspendMediation();
        }
        catch (Exception e) {
            if (logErrors) {
                errorRecorder.addSource(new CustomMultiSourceException.Source(e));
            }
        }

        //------------

        Registry registry = Registry.getInstance();
        Logger logger = registry.getComponent(Logger.class);

        int waitCycles = 0;
        for (; ;) {
            boolean someStillRunning = false;

            someStillRunning = ruleSession.getTaskController().isRunning();

            someStillRunning = someStillRunning || entityMediator.isMediationRunning();


            if (someStillRunning == false) {
                break;
            }
            else if (waitCycles == 10 && logger.isAllowed(Logger.LogLevel.ERROR)) {
                logger.log(Logger.LogLevel.ERROR, resourceId,
                        "Some modules are still running." +
                                " Giving up further attempts to suspend this set." +
                                " Resuming other work.");

                break;
            }

            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                //Ignore.
            }

            waitCycles++;
        }

        //------------

        if (logErrors && errorRecorder.hasSources()) {
            throw errorRecorder;
        }
    }

    /**
     * TODO Not sure if query agent also needs to do this
     *
     * @throws Exception
     */
    protected void deActivateExternalClass() throws Exception {
        throw new UnsupportedOperationException(
                "This operation is only supported on Inference agent nodes");
    }

    protected void onSuspend() throws Exception {
        doSuspend(true);
    }

    protected void onResume() throws Exception {
        try {
            ruleSession.getTaskController().resume();

            entityMediator.resumeMediation();
        }
        catch (Throwable t) {
            doSuspend(false);

            if (t instanceof Exception) {
                throw (Exception) t;
            }

            throw new Exception(t);
        }
    }

    protected void onShutdown() throws Exception {
        entityMediator.shutdownMediation();
        entityMediator = null;

        ruleSession.stopAndShutdown();

        stop();
    }

    @Override
    public int getMaxActive() {
        return this.agentConfig.getMaxActive();
    }

    @Override
    public int getPriority() {
        return this.agentConfig.getPriority();
    }

    //------------

    public void listenerReady(ClusterEntityListener listener) {
        this.listener = listener;
    }

    /**
     * {@link #getAgentName()}.
     *
     * @return
     */
    public String getName() {
        return getAgentName();
    }

    /**
     * @return current Query rule session
     */
    public RuleSession getRuleSession() {
        return ruleSession;
    }

    public MetadataCache getMetadataCache() {
        try {
            //Don't cache this. These fields get initialized lazily.
            return cacheCluster.getMetadataCache();
        }
        catch (Exception e) {
            throw new CustomRuntimeException(resourceId, e);
        }
    }

    public ObjectTable getObjectTableCache() {
        try {
            // Don't cache this. These fields get initialized lazily.
            return cacheCluster.getObjectTableCache();
        }
        catch (Exception e) {
            throw new CustomRuntimeException(resourceId, e);
        }
    }
    
    @Override
    public boolean useObjectTable() {
    	return cacheCluster.getClusterConfig().useObjectTable();
    }

    public EntityDao getEntityCache(Class entityClass) {
        DaoProvider daoProvider = cacheCluster.getDaoProvider();

        return daoProvider.getEntityDao(entityClass);
    }

    /**
     * @param entityClass
     * @return
     * @throw com.tibco.cep.query.stream.monitor.CustomRuntimeException
     */
    public String getEntityCacheName(Class entityClass) {
        EntityDao provider = null;

        try {
            provider = cacheCluster.getMetadataCache().getEntityDao(entityClass);
        }
        catch (Exception e) {
            throw new CustomRuntimeException(resourceId, e);
        }

        return provider.getName();
    }

    public BEClassLoader getEntityClassLoader() {
        return (BEClassLoader) ruleSession.getRuleServiceProvider().getTypeManager();
    }

    public int getTypeId(Class entityClz) throws Exception {
        return cacheCluster.getMetadataCache().getTypeId(entityClz);
    }

    public Class getClass(int typeId) throws Exception {
        return cacheCluster.getMetadataCache().getClass(typeId);
    }

    @Override
    public void start() throws Exception {
        throw new Exception("Start call should not be invoked directly...");
    }
    
    @Override
    public void entitiesAdded() {}
    
    @Override
    public void entitiesChanged(Collection<Class<com.tibco.cep.kernel.model.entity.Entity>> changedClasses) {}
}
