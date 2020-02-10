/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.query.stream.impl.rete.service;

import com.tibco.cep.runtime.service.cluster.events.notification.ClusterEntityListener;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.RuleSession;

/*
* Author: Ashwin Jayaprakash Date: Apr 28, 2008 Time: 4:47:20 PM
*/

public interface AgentService {
    public void listenerReady(ClusterEntityListener listener);

    public String getName();

    /**
     * @param entityClass
     * @return No reference to any specific implementation.
     */
    public EntityDao getEntityCache(Class entityClass);

    public String getEntityCacheName(Class entityClass);

    public BEClassLoader getEntityClassLoader();

    public MetadataCache getMetadataCache();

    public ObjectTable getObjectTableCache();
    
    public boolean useObjectTable();

    public RuleSession getRuleSession();

    //----------

    //Shortcut methods mostly used by Test stubs.

    public int getTypeId(Class entityClz) throws Exception;

    public Class getClass(int typeId) throws Exception;
}
