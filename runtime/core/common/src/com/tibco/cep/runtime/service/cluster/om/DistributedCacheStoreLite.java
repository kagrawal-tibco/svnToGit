/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import java.util.Collection;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.om.FastLocalCache;

/*
* Author: Ashwin Jayaprakash Date: Oct 15, 2008 Time: 10:18:42 PM
*/
class DistributedCacheStoreLite extends DefaultDistributedCacheBasedStore implements FastLocalCache {
    /**
     * {@value}
     */
    public static final String PROPERTY_BULK_MODE = "be.engine.mode.bulkCacheRead";

    /**
     * {@value}
     */
    public static final String PROPERTY_LITE_CACHE_SIZE = "be.om.liteCache.size";

    /**
     * {@value}
     */
    public static final String PROPERTY_LITE_CACHE_EXPIRY_MILLIS =
            "be.om.liteCache.expiryMillis";

    /**
     * {@value}
     */
    public static final int DEFAULT_LITE_CACHE_SIZE = 1024;

    /**
     * {@value}
     */
    public static final long DEFAULT_LITE_CACHE_EXPIRY_MILLIS = 3 * 60 * 1000;

    protected FastLocalCacheImpl localCacheDelegate;

    /**

     * @param agent CacheAgent
     * Expects {@link #PROPERTY_LITE_CACHE_SIZE} and {@link
     *                     #PROPERTY_LITE_CACHE_EXPIRY_MILLIS} to be present.

     */

    public DistributedCacheStoreLite(CacheAgent agent) {
        super(agent);
    }

    @Override
    public void init() throws Exception {


        BEProperties beProperties = (BEProperties) mCacheAgent.getCluster().getRuleServiceProvider().getProperties();
        int cacheSize = beProperties
                .getInt(PROPERTY_LITE_CACHE_SIZE, DEFAULT_LITE_CACHE_SIZE);

        long cacheExpiryMillis = beProperties.getLong(
                PROPERTY_LITE_CACHE_EXPIRY_MILLIS,
                DEFAULT_LITE_CACHE_EXPIRY_MILLIS);

        this.localCacheDelegate = new FastLocalCacheImpl(cacheSize, cacheExpiryMillis);

    }


    @Override
    protected Concept getConceptFromCache(CacheConceptHandle handle) {
        long id = handle.getElementId();

        Concept c = (Concept) localCacheDelegate.getEntity(id);
        if (c != null) {
            c.setLoadedFromCache();
            return c;
        }

        //Fallback.
        return super.getConceptFromCache(handle);
    }

    @Override
    protected Concept getConceptFromCache(CacheConceptExHandle handle) {
        long id = handle.getElementId();

        Concept c = (Concept) localCacheDelegate.getEntity(id);
        if (c != null) {
            c.setLoadedFromCache();
            return c;
        }

        //Fallback.
        return super.getConceptFromCache(handle);
    }

    @Override
    public Element getElementFromStore(long id) {
        Element e = (Element) localCacheDelegate.getEntity(id);

        //Fallback.
        if (e == null) {
            e = super.getElementFromStore(id);
            e.setLoadedFromCache();
        }

        return e;
    }

    //-------------

    public void putEntity(Long id, Entity entity) {
        localCacheDelegate.putEntity(id, entity);
    }

    public Entity getEntity(Long id) {
        return localCacheDelegate.getEntity(id);
    }

    public void removeEntity(Long id) {
        localCacheDelegate.removeEntity(id);
    }

    public void removeEntities(Collection<Long> ids) {
        localCacheDelegate.removeEntities(ids);
    }
}
