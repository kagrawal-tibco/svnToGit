package com.tibco.cep.query.stream.impl.rete.service;

import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.core.ControllableResource;
import com.tibco.cep.query.stream.impl.rete.ReteEntityClassHierarchyHelper;
import com.tibco.cep.query.stream.impl.rete.integ.QueryDataProvider;

/*
* Author: Ashwin Jayaprakash Date: Jul 10, 2008 Time: 3:40:35 PM
*/
public interface RegionManager extends ControllableResource {
    AgentService getAgentService();

    Cache getPrimaryCache();

    CacheScout getCacheScout();

    QueryDataProvider getQueryDataProvider();

    ReteEntityClassHierarchyHelper getHierarchyHelper();

    ReteEntityDispatcher getDispatcher();

    SharedObjectSourceRepository getSOSRepository();

    String getRegionName();

    WorkingMemory getWorkingMemory();

    SnapshotQueryManager getSSQueryManager();
}
