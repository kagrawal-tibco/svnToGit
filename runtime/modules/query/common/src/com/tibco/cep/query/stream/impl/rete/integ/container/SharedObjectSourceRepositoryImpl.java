package com.tibco.cep.query.stream.impl.rete.integ.container;

import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.impl.rete.integ.AbstractSharedObjectSourceRepository;
import com.tibco.cep.query.stream.impl.rete.service.AgentService;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.runtime.service.om.api.EntityDao;

/*
* Author: Ashwin Jayaprakash Date: Mar 24, 2008 Time: 2:35:08 PM
*/

public class SharedObjectSourceRepositoryImpl extends AbstractSharedObjectSourceRepository {
    protected BaseObjectManager realOM;

    protected AgentService agentService;

    protected String regionName;

    public SharedObjectSourceRepositoryImpl(ResourceId parentId, String regionName,
                                            BaseObjectManager realOM, Cache deadPoolCache,
                                            AgentService agentService) {
        super(parentId, agentService.getEntityClassLoader(),
                new PrimaryCacheOMDelegator(parentId, realOM), deadPoolCache);

        this.agentService = agentService;
        this.realOM = realOM;
        this.regionName = regionName;
    }

    @Override
    protected SharedObjectSourceImpl createSharedObjectSource(Class clazz,
                                                              ClassLoader entityClassLoader,
                                                              Cache primaryCache,
                                                              Cache deadPoolCache) {
        String cacheName = agentService.getEntityCacheName(clazz);
        EntityDao entityDao = agentService.getEntityCache(clazz);

        return new SharedObjectSourceImpl(entityDao, cacheName, primaryCache, deadPoolCache,
                entityClassLoader);
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        agentService = null;
        realOM = null;
        regionName = null;
    }
}
