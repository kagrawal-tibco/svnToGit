package com.tibco.cep.query.stream.impl.rete.integ.standalone;

import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.impl.rete.integ.AbstractSharedObjectSourceRepository;
import com.tibco.cep.query.stream.impl.rete.service.AgentService;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.runtime.service.om.api.EntityDao;

/*
 * Author: Ashwin Jayaprakash Date: Mar 7, 2008 Time: 2:55:25 PM
 */

public class DefaultDelegateRepository extends AbstractSharedObjectSourceRepository
        implements DelegateRepository {
    protected AgentService agentService;

    protected QueryWorkingMemory workingMemory;

    protected DefaultDelegateRepository(ResourceId parentId, AgentService agentService,
                                        Cache primaryCache, Cache deadPoolCache) {
        super(parentId, agentService.getEntityClassLoader(), primaryCache, deadPoolCache);

        this.agentService = agentService;
    }

    public void init(QueryWorkingMemory workingMemory) {
        this.workingMemory = workingMemory;
    }

    @Override
    protected QueryTypeInfo createSharedObjectSource(Class clazz,
                                                     ClassLoader entityClassLoader,
                                                     Cache primaryCache,
                                                     Cache deadPoolCache) {
        String cacheName = agentService.getEntityCacheName(clazz);
        EntityDao entityDao = agentService.getEntityCache(clazz);

        DefaultQueryTypeInfo typeInfo = new DefaultQueryTypeInfo(entityDao, cacheName,
                primaryCache, deadPoolCache,
                entityClassLoader, clazz, workingMemory);

        typeInfo.init();

        return typeInfo;
    }

    @Override
    public QueryTypeInfo getSource(String className) {
        return (QueryTypeInfo) super.getSource(className);
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        agentService = null;
        workingMemory = null;
    }
}
