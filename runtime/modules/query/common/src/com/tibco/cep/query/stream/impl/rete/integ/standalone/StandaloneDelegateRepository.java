package com.tibco.cep.query.stream.impl.rete.integ.standalone;

import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.impl.rete.integ.AbstractSharedObjectSourceRepository;
import com.tibco.cep.query.stream.impl.rete.service.AgentService;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
 * Author: Ashwin Jayaprakash Date: Mar 7, 2008 Time: 2:55:25 PM
 */

public class StandaloneDelegateRepository extends AbstractSharedObjectSourceRepository
        implements DelegateRepository {
    protected AgentService agentService;

    protected QueryWorkingMemory workingMemory;

    protected StandaloneDelegateRepository(ResourceId parentId, AgentService agentService,
                                           Cache primaryCache, Cache deadPoolCache) {
        super(parentId, agentService.getEntityClassLoader(), primaryCache, deadPoolCache);

        this.agentService = agentService;
    }

    public void init(QueryWorkingMemory workingMemory) {
        this.workingMemory = workingMemory;
    }

    protected SharedObjectSource createSharedObjectSource(Class clazz,
                                                          ClassLoader entityClassLoader,
                                                          Cache primaryCache, Cache deadPoolCache) {
        return getDefaultSource();
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
