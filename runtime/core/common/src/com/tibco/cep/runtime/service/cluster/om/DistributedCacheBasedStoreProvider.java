package com.tibco.cep.runtime.service.cluster.om;

import java.lang.reflect.Constructor;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.runtime.service.cluster.CacheAgent;

/*
* Author: Suresh Subramani / Date: 11/28/11 / Time: 4:24 PM
*/
public class DistributedCacheBasedStoreProvider {

    static DistributedCacheBasedStoreProvider singleton = new DistributedCacheBasedStoreProvider();

    private DistributedCacheBasedStoreProvider() {
    }

    public static DistributedCacheBasedStoreProvider getInstance() {
        return singleton;
    }

    public DistributedCacheBasedStore newDistributedCacheBasedStore(CacheAgent agent, BEProperties beProperties) throws Exception {
        Class clazz = DefaultDistributedCacheBasedStore.class;

        String propName = DistributedCacheStoreLite.PROPERTY_BULK_MODE.toLowerCase();
        boolean bulkModeOn = beProperties.getBoolean(propName, false);

        boolean directCacheConnectModeOn = beProperties.getBoolean(DistributedCacheStoreUltraLite.PROPERTY_DIRECT_CACHE_CONNECT_MODE, false);

        if (bulkModeOn) {
            if (DistributedCacheStoreLite.class.isAssignableFrom(clazz) == false) {
                clazz = DistributedCacheStoreLite.class;
            }
        }
        else if (directCacheConnectModeOn) {
            if (DistributedCacheStoreUltraLite.class.isAssignableFrom(clazz) == false) {
                clazz = DistributedCacheStoreUltraLite.class;
            }
        }

        Constructor<DistributedCacheBasedStore> cons =  clazz.getConstructor(new Class[]{CacheAgent.class});

        return cons.newInstance(agent);

    }
}
