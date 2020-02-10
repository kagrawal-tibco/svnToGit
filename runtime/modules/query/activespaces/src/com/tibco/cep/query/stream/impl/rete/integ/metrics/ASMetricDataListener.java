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
import com.tibco.cep.query.stream.monitor.Logger.LogLevel;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.runtime.management.MetricTable;
import com.tibco.cep.runtime.management.impl.cluster.RemoteClusterHandler;
import com.tibco.cep.runtime.management.impl.cluster.RemoteClusterMaster;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.cluster.events.notification.ClusterEntityListener;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.ProcessInfo;

/*
* Author: Nick Xu
*/
public class ASMetricDataListener implements MetricTable.DataListener, ControllableResource,
        QueryDataProvider {
    /**
     * {@value}.
     */
    public static final String KEY_METRIC_OBJECT_XFORMER_CLASSNAME =
            "be.agent.query.querydataprovider.metricobjecttransformer.classname";


    protected ResourceId resourceId;

    protected RemoteClusterMaster clusterMaster;
    
    protected HashMap<String, MetricObjectTransformer> objectTransformerMap;

    protected ReteEntityChangeListener changeListener;

    protected Cache primaryCache;

    protected Properties properties;

    protected BEClassLoader classLoader;

    protected Logger logger;

    protected AgentService agentService;

    protected ResourceId parentId;

    public ASMetricDataListener() {
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
        if (MetricObjectTransformer.class.isAssignableFrom(clazz) == false) {
            throw new IllegalArgumentException(
                    "The implementation class specified by the property [" +
                            KEY_METRIC_OBJECT_XFORMER_CLASSNAME + "] must implement [" +
                            MetricObjectTransformer.class.getName() + "].");
        }

        com.tibco.cep.kernel.service.logging.Logger kernelLogger = null;
        if (logger instanceof DelegatedLogger) {
            DelegatedLogger delegatedLogger = (DelegatedLogger) logger;
            kernelLogger = delegatedLogger.getDelegate();
        }
        else {
            kernelLogger = new QueryLogger(this.getName());
        }
        
        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        clusterMaster = registry.getService(RemoteClusterMaster.class);

        if(clusterMaster == null){
            clusterMaster = new RemoteClusterMaster();
            clusterMaster.init(registry.getConfiguration());
            registry.registerService(RemoteClusterMaster.class, clusterMaster);

            clusterMaster.start();
        }

        HashMap<String, RemoteClusterHandler> clusterUrlToRemClustHand = clusterMaster.connect(properties, kernelLogger);

        objectTransformerMap = new HashMap<String, MetricObjectTransformer>();

        //Multicluster support for AS is implemented in this loop
        for (String cname : clusterUrlToRemClustHand.keySet()) {
            RemoteClusterHandler rch = clusterUrlToRemClustHand.get(cname);
            MetricObjectTransformer objectTransformer = (MetricObjectTransformer) clazz.newInstance();

            logger.log(LogLevel.INFO,
                            String.format("Initializing [%s] with [%s] implemented by [%s]",
                            getClass().getSimpleName(), MetricObjectTransformer.class.getSimpleName(),
                            objectTransformer.getClass().getName()));

            objectTransformer.init(properties, classLoader, rch.getManagementTable(),
                    rch.getMetricTable(), rch.getCacheTable());

            rch.getMetricTable().registerListener(this, null);
            objectTransformerMap.put(rch.getClusterName(), objectTransformer);
        }

        //Don't need these anymore.
        classLoader = null;
        properties = null;

        agentService.listenerReady(new FakeListener());
    }

    public void stop() throws Exception {
        for (String cname : objectTransformerMap.keySet()) {
            MetricObjectTransformer trans = objectTransformerMap.get(cname);
            trans.discard();
            RemoteClusterHandler rch = clusterMaster.getHandler(cname);
            if (rch != null) rch.discard(getName());
        }
    }


    //-------------

    public String getName() {
        return resourceId.toString();
    }

    public void onNew(FQName key, Data data) {
        if (key == null)        //TODO: This is a temporary fix for BE-14445. Try removing these two
            return;             //TODO lines of code after syncing and see if the NPE persists.

        String[] splits = key.getComponentNames();
        MetricObjectTransformer objectTransformer = null;
        if(splits.length>0){
            objectTransformer = objectTransformerMap.get(splits[0]);
        }
        else return;
        if(objectTransformer == null) {
            //logger.log(LogLevel.ERROR, "Could not find MetricTransformer for cluster: "+splits[0]);
            return;
        }
        Entity entity;
        try{
        	entity = objectTransformer.transform(key, data);
        }
        catch(Exception e){
        	if(logger != null)
        	logger.log(LogLevel.DEBUG, "Could NOT transform to event the data with key: "+key);
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
