/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 26/8/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.cluster;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.EntityDao;

/*
* Author: Ashwin Jayaprakash Date: Jan 7, 2009 Time: 3:56:15 PM
*/

public class BEEntityCacheProvider {
    /**
     * {@value}.
     */
    public static final String KEY_CACHE_PROVIDER_CLASS_NAME = "be.om.entityCache.className";

    public static EntityDao create(BEProperties properties, Logger logger,
                                   Cluster cluster,
                                   Class entityClz, String cacheName,
                                   boolean hasBackingStore) {
        String className =
                properties.getString(KEY_CACHE_PROVIDER_CLASS_NAME, BEEntityCache.class.getName());

        EntityDao entityCache = null;
        try {
            Class<? extends EntityDao> clazz =
                    (Class<? extends EntityDao>) Class.forName(className);

            entityCache = clazz.newInstance();

//            entityCache.init(cluster, entityClz, cacheName);
//            entityCache.setHasBackingStore(hasBackingStore);
            logger.log(Level.INFO, "Initialized " + EntityDao.class.getSimpleName() + " [" +
                    entityCache.getClass().getName() + ", Clz=" + entityClz.getName() +
                    ", BackingStore=" + hasBackingStore + "]");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return entityCache;
    }
}
