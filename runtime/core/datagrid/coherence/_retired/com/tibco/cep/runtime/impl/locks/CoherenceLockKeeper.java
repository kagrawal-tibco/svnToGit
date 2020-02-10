/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.session.impl.locks;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.session.locks.LockManager;

import java.util.Collection;

/*
* Author: Ashwin Jayaprakash Date: Jul 22, 2008 Time: 7:04:26 PM
*/

public class CoherenceLockKeeper<K> extends DefaultLockKeeper<K, Collection<Long>> {
    /**
     * {@value}.
     */
    public static final String CACHE_NAME = "$$_ClusterLocks_$$";

    public CoherenceLockKeeper(Cluster cluster) throws Exception {
        super(cluster.getDaoProvider().createControlDao(Object.class, Collection.class, ControlDaoType.ClusterLocks, null));
    }

    @Override
    public void put(LockManager.LockLevel lockLevel,
                    LockManager.LockData<K, Collection<Long>> lockData,
                    Collection<Long> affectedIds) {
        if (lockLevel == LockManager.LockLevel.LEVEL2) {
            if (affectedIds != null && affectedIds.size() > 0) {
                //todo Skip this step for now.
                // lockCache.put(lockData.getKey(), affectedIds);
            }
        }
    }

    @Override
    public Collection<Long> remove(LockManager.LockLevel lockLevel, K key) {
        if (lockLevel == LockManager.LockLevel.LEVEL2) {
            return null;

            //todo Skip this step for now.
            // return (Collection<Long>) lockCache.remove(key);
        }

        return null;
    }
}
