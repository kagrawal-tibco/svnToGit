/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.query.stream.impl.rete.service;

import com.tibco.cep.query.stream.core.ControllableResource;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.runtime.service.cluster.events.notification.ClusterEntityListener;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.RuleSession;

/*
* Author: Ashwin Jayaprakash Date: Apr 13, 2009 Time: 6:34:28 PM
*/
public class StandAloneAgentService implements AgentService, ControllableResource {
    protected final String name;

    protected final BEClassLoader beClassLoader;

    protected final RuleSession ruleSession;

    protected final ResourceId resourceId;

    public StandAloneAgentService(String name, BEClassLoader beClassLoader,
                                  RuleSession ruleSession) {
        this.name = name;
        this.beClassLoader = beClassLoader;
        this.ruleSession = ruleSession;

        String s = StandAloneAgentService.class.getName() + ":" + ruleSession.getName();
        this.resourceId = new ResourceId(s);
    }

    public void listenerReady(ClusterEntityListener listener) {
    }

    public String getName() {
        return name;
    }

    public BEClassLoader getEntityClassLoader() {
        return beClassLoader;
    }

    @Override
    public RuleSession getRuleSession() {
        return ruleSession;
    }

    //-------------

    public void start() throws Exception {
    }

    public void stop() throws Exception {
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    //-------------

    public EntityDao getEntityCache(Class entityClass) {
        throw new UnsupportedOperationException();
    }

    public String getEntityCacheName(Class entityClass) {
        throw new UnsupportedOperationException();
    }

    public MetadataCache getMetadataCache() {
        throw new UnsupportedOperationException();
    }

    public ObjectTable getObjectTableCache() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean useObjectTable() {
    	throw new UnsupportedOperationException();
    }

    public int getTypeId(Class entityClz) throws Exception {
        throw new UnsupportedOperationException();
    }

    public Class getClass(int typeId) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return name;
    }
}
