/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.query.stream.impl.rete.integ;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.query.stream.impl.rete.integ.standalone.QueryWorkingMemory;
import com.tibco.cep.query.stream.impl.rete.service.AgentService;
import com.tibco.cep.query.stream.monitor.CustomRuntimeException;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.events.notification.ClusterEntityListener;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.util.SimpleThreadFactory;

/*
* Author: Ashwin Jayaprakash / Date: Jan 8, 2010 / Time: 1:01:56 PM
*/

public class CacheServerAgentService implements AgentService {
    protected static CacheServerAgentService SINGLETON;

    protected Cluster cacheCluster;

    protected String name;

    protected QueryWorkingMemory queryWorkingMemory;

    protected ResourceId resourceId;

    protected ThreadPoolExecutor threadPoolExecutor;

    public static void init(String name, Cluster cacheCluster, int numThreads)
            throws Exception {
        SINGLETON = new CacheServerAgentService(name, cacheCluster, numThreads);
    }

    public static CacheServerAgentService getCacheServerAgentService() {
        return SINGLETON;
    }

    protected CacheServerAgentService(String name, Cluster cacheCluster, int numThreads)
            throws Exception {
        this.cacheCluster = cacheCluster;
        this.name = name;
        this.resourceId = new ResourceId(name);

        this.threadPoolExecutor = new ThreadPoolExecutor(1, numThreads,
                (3 * 60), TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(), new SimpleThreadFactory("SpecialOM"));

        FakeCache primaryCache = new FakeCache(new ResourceId(this.resourceId, "PrimaryCache"));
        FakeCache deadPoolCache = new FakeCache(new ResourceId(this.resourceId, "DeadPoolCache"));

        SeparateThreadedQueryOM qom =
                new SeparateThreadedQueryOM(this.resourceId, name, this, primaryCache,
                        deadPoolCache, this.threadPoolExecutor);

        this.queryWorkingMemory =
                new QueryWorkingMemory(name, qom, LogManagerFactory.getLogManager());
        this.queryWorkingMemory.start();
    }

    public QueryWorkingMemory getQueryWorkingMemory() {
        return queryWorkingMemory;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    //------------

    public void listenerReady(ClusterEntityListener listener) {
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    @Override
    public RuleSession getRuleSession() {
        return null;
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
            //Don't cache this. These fields get initialized lazily.
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
        return (BEClassLoader) cacheCluster.getRuleServiceProvider().getTypeManager();
    }

    public int getTypeId(Class entityClz) throws Exception {
        return cacheCluster.getMetadataCache().getTypeId(entityClz);
    }

    public Class getClass(int typeId) throws Exception {
        return cacheCluster.getMetadataCache().getClass(typeId);
    }
}
