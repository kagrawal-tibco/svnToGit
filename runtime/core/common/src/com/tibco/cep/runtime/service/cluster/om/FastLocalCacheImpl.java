/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import java.util.Collection;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.service.om.FastLocalCache;
import com.tibco.cep.runtime.service.om.impl.invm.DefaultLocalCache;

/*
* Author: Ashwin Jayaprakash Date: Oct 16, 2008 Time: 11:08:31 AM
*/
public class FastLocalCacheImpl extends DefaultLocalCache implements FastLocalCache {
    public FastLocalCacheImpl(int maxItems, long expiryTimeMillis) {
        super();

        actualInit(maxItems, expiryTimeMillis);
    }

    //---------------

    public synchronized void putEntity(Long id, Entity entity) {
        lruMap.put(entity.getId(), entity);
    }

    public Entity getEntity(Long id) {
        return get(id);
    }

    public void removeEntity(Long id) {
        remove(id);
    }

    public void removeEntities(Collection<Long> ids) {
        for (Long id : ids) {
            remove(id);
        }
    }
}
