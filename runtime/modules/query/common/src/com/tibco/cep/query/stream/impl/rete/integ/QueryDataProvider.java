package com.tibco.cep.query.stream.impl.rete.integ;

import java.util.Properties;

import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.core.ControllableResource;
import com.tibco.cep.query.stream.impl.rete.service.AgentService;
import com.tibco.cep.query.stream.impl.rete.service.CacheScout;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityDispatcher;
import com.tibco.cep.query.stream.impl.rete.service.SnapshotQueryManager;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.runtime.service.loader.BEClassLoader;

/*
* Author: Ashwin Jayaprakash Date: Mar 3, 2009 Time: 2:06:43 PM
*/
public interface QueryDataProvider extends ControllableResource {
    void setParentId(ResourceId parentId);

    void setProperties(Properties properties);

    void setBEClassLoader(BEClassLoader classLoader);

    void setWorkingMemory(WorkingMemory workingMemory);

    void setLogger(Logger logger);

    void setAgentService(AgentService agentService);

    void setPrimaryCache(Cache cache);

    void setDeadpoolCache(Cache cache);

    void setSharedObjectSourceRepository(SharedObjectSourceRepository sourceRepository);

    void setReteEntityDispatcher(ReteEntityDispatcher entityDispatcher);

    void setSnapshotQueryManager(SnapshotQueryManager queryManager);

    void setCacheScout(CacheScout cacheScout);
}
