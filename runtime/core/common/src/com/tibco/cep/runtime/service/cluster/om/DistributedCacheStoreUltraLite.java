/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.om.DirectDistributedCacheConnect;

/*
* Author: Ashwin Jayaprakash Date: Oct 29, 2008 Time: 11:22:43 AM
*/

class DistributedCacheStoreUltraLite extends DefaultDistributedCacheBasedStore
        implements DirectDistributedCacheConnect {
    /**
     * {@value}
     */
    public static final String PROPERTY_DIRECT_CACHE_CONNECT_MODE =
            "be.engine.mode.directCacheConnect";

    protected DirectDistributedCacheConnect dcUser;

    /**
     * @param agent

     */

    DistributedCacheStoreUltraLite(CacheAgent agent)
    {
        super(agent);
    }

    //------------------

    public void init(MetadataCache metadataCache, ObjectTable objectTable) {
        dcUser = new DirectDistributedCacheConnectImpl();
        dcUser.init(metadataCache, objectTable);
    }

    public Entity loadEntity(String extId) {
        return dcUser.loadEntity(extId);
    }

    public Entity loadEntity(long id) {
        return dcUser.loadEntity(id);
    }

    //------------------

    @Override
    public Entity getElementFromStore(String extId) {
        Entity e = dcUser.loadEntity(extId);

        //Fallback.
        if (e == null) {
            e = super.getElementFromStore(extId);
        }
        if (e != null)
            e.setLoadedFromCache();
        return e;
    }


    @Override
    protected Concept getConceptFromCache(CacheConceptHandle handle) {
        long id = handle.getElementId();

        Concept c = (Concept) dcUser.loadEntity(id);
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

        Concept c = (Concept) dcUser.loadEntity(id);
        if (c != null) {
            c.setLoadedFromCache();
            return c;
        }

        //Fallback.
        return super.getConceptFromCache(handle);
    }

    @Override
    public Element getElementFromStore(long id) {
        Element e = (Element) dcUser.loadEntity(id);

        //Fallback.
        if (e == null) {
            e = super.getElementFromStore(id);
        }

        if (e != null) {
            e.setLoadedFromCache();
        }
        return e;
    }
}
