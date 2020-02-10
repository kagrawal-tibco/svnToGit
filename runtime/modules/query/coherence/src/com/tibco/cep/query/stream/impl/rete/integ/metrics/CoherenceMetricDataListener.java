/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.query.stream.impl.rete.integ.metrics;


import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.query.service.QueryLogger;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.core.ControllableResource;
import com.tibco.cep.query.stream.impl.rete.integ.QueryDataProvider;
import com.tibco.cep.query.stream.impl.rete.service.AgentService;
import com.tibco.cep.query.stream.impl.rete.service.CacheScout;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityChangeListener;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityDispatcher;
import com.tibco.cep.query.stream.impl.rete.service.SnapshotQueryManager;
import com.tibco.cep.query.stream.impl.util.DelegatedLogger;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.runtime.management.MetricTable;
import com.tibco.cep.runtime.management.impl.adapter.parent.RemoteProcessInfo;
import com.tibco.cep.runtime.management.impl.adapter.parent.RemoteProcessMaster;
import com.tibco.cep.runtime.management.impl.adapter.parent.RemoteProcessesManager;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.cluster.events.notification.ClusterEntityListener;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.ProcessInfo;

/*
* Author: Ashwin Jayaprakash Date: Mar 3, 2009 Time: 1:08:05 PM
*/
public class CoherenceMetricDataListener implements MetricTable.DataListener, ControllableResource,
        QueryDataProvider {
    /**
     * {@value}.
     */
    public static final String KEY_METRIC_OBJECT_XFORMER_CLASSNAME =
            "be.agent.query.querydataprovider.metricobjecttransformer.classname";

    protected ResourceId resourceId;

    protected ReteEntityChangeListener changeListener;

    protected Cache primaryCache;

    protected RemoteProcessInfo remoteCoherenceInfo;

    protected Properties properties;

    protected BEClassLoader classLoader;

    protected Logger logger;

    protected AgentService agentService;

    protected ResourceId parentId;
    
    protected HashMap<String, MetricObjectTransformer> clustNameToMetricObjTransformer;
    
    protected RemoteProcessesManager remProcsMngr;

    public CoherenceMetricDataListener() {
    }

    public void setParentId(ResourceId parentId) {
        this.parentId = parentId;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setBEClassLoader(BEClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void setWorkingMemory(WorkingMemory workingMemory) {
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setAgentService(AgentService agentService) {
        this.agentService = agentService;

        String suffix = ProcessInfo.getProcessIdentifier() + ":" + getClass().getSimpleName() +
                ":" + System.identityHashCode(this);
        this.resourceId = new ResourceId(parentId, suffix);
    }

    public void setPrimaryCache(Cache cache) {
        this.primaryCache = cache;
    }

    public void setDeadpoolCache(Cache cache) {
    }

    public void setSharedObjectSourceRepository(SharedObjectSourceRepository sourceRepository) {
    }

    public void setReteEntityDispatcher(ReteEntityDispatcher entityDispatcher) {
        this.changeListener = entityDispatcher;
    }

    public void setSnapshotQueryManager(SnapshotQueryManager queryManager) {
    }

    public void setCacheScout(CacheScout cacheScout) {
    }

    //-------------

    public ResourceId getResourceId() {
        return resourceId;
    }

    public void start() throws Exception {
        String xformerClassName = properties.getProperty(KEY_METRIC_OBJECT_XFORMER_CLASSNAME);
        if (xformerClassName == null) {
            throw new IllegalArgumentException("Required property [" +
                    KEY_METRIC_OBJECT_XFORMER_CLASSNAME + "] has not been specified.");
        }

        Class clazz = Class.forName(xformerClassName, true, classLoader);
        if (!MetricObjectTransformer.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(
                    "The implementation class specified by the property [" +
                            KEY_METRIC_OBJECT_XFORMER_CLASSNAME + "] must implement [" +
                            MetricObjectTransformer.class.getName() + "].");
        }

        //-------------

        com.tibco.cep.kernel.service.logging.Logger kernelLogger = null;
        if (logger instanceof DelegatedLogger) {
            DelegatedLogger delegatedLogger = (DelegatedLogger) logger;
            kernelLogger = delegatedLogger.getDelegate();
        }
        else {
            kernelLogger = new QueryLogger(this.getName());
        }

        //-------------

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        remProcsMngr = registry.getService(RemoteProcessesManager.class);


        if (remProcsMngr == null) {
            remProcsMngr = new RemoteProcessesManager(properties, kernelLogger);
            remProcsMngr.init(registry.getConfiguration());
            registry.registerService(RemoteProcessesManager.class, remProcsMngr);

            remProcsMngr.start();
        }

        Collection<RemoteProcessMaster> remProcsMaster = remProcsMngr.connectAll();

        if (remProcsMaster == null || remProcsMaster.isEmpty()) {
            logger.log(Logger.LogLevel.INFO, "No Remote (broker) Processes started. " +
                       "No monitored clusters info will be available to BEMM.");
            return;
        }

        //-------------

        clustNameToMetricObjTransformer = new HashMap<String, MetricObjectTransformer>();

        for (RemoteProcessMaster remProc : remProcsMaster) {
            final String CLUST_NAME = remProc.getClusterName();
            
            if(remProc.getCurrentRemoteProcessInfo() == null){
                logger.log(Logger.LogLevel.WARNING, String.format("Could NOT find Remote (broker) " +
                        "Process info for cluster '%s'. Proceeding to the next cluster.", CLUST_NAME));
                continue;
            }

            final MetricObjectTransformer objectTransformer = (MetricObjectTransformer) clazz.newInstance();

            logger.log(Logger.LogLevel.INFO,
                    String.format("Initializing [%s] with [%s] implemented by [%s] for cluster [%s]",
                            getClass().getSimpleName(), MetricObjectTransformer.class.getSimpleName(),
                            objectTransformer.getClass().getName(), CLUST_NAME));


            remProc.createProxyTables();

            objectTransformer.init(properties, classLoader, remProc.getProxyCoherenceManagementTable(),
                    remProc.getProxyCoherenceMetricTable(), remProc.getProxyCoherenceCacheTable());

            remProc.getProxyCoherenceMetricTable().registerListener(this, null);
            
            clustNameToMetricObjTransformer.put(CLUST_NAME, objectTransformer);
        }
        //Don't need these anymore.
        classLoader = null;
        properties = null;

        agentService.listenerReady(new FakeListener());
    }

    public void stop() throws Exception {
        for (String cName : clustNameToMetricObjTransformer.keySet()) {
            MetricObjectTransformer objTrans = clustNameToMetricObjTransformer.get(cName);
            objTrans.discard();

            //discards and unregisters listener
            remProcsMngr.getRemoteProcess(cName).discardProxyTables(getName());
        }
    }

    //-------------

    public String getName() {
        return resourceId.toString();
    }

    public void onNew(FQName key, Data data) {
        String[] componenets = key.getComponentNames();
        MetricObjectTransformer objectTransformer = null;
        if(componenets.length>0){
            objectTransformer = clustNameToMetricObjTransformer.get(componenets[0]);
        } else
            return;

        if(objectTransformer == null) {
            return;
        }

        Entity entity;
        try{
            entity = objectTransformer.transform(key, data);
        } catch(Exception e){
            if(logger != null)
                logger.log(Logger.LogLevel.DEBUG, "Could NOT transform to event the data with key: "+key);
            return;
        }

        if (entity == null) {
            return;
        }

        Class entityClass = entity.getClass();
        if (changeListener.hasListeners(entityClass)) {
            Long id = entity.getId();

            primaryCache.put(id, entity);

            changeListener.onNew(entity.getClass(), id, 0);
        }
    }

    //-------------

    protected static class FakeListener implements ClusterEntityListener {
        public boolean requireAsyncInvocation() {
            return false;
        }

        public void onEntity(Object obj) {
        }

        public Filter getEntityFilter() {
            return null;
        }

        public String getListenerName() {
            return getClass().getName();
        }
        
        @Override
        public void entitiesAdded() {}
        
        @Override
        public void entitiesChanged(Collection<Class<Entity>> changedClasses) {}
    }
}
