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

package com.tibco.cep.runtime.service.cluster.deploy;


import java.util.Set;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;

/**
 * Created by IntelliJ IDEA. User: aathalye Date: Nov 11, 2008 Time: 5:01:34 PM A cache which should
 * not allow more than one entry in it at any time
 */
public class SingleKeyCache {
    private ControlDao<String, String> singleKeyCache;

    public SingleKeyCache(Cluster cluster) throws Exception {
        singleKeyCache = cluster.getDaoProvider().createControlDao(String.class, String.class, ControlDaoType.DeletedExternalEntities);
        singleKeyCache.start();
    }

    protected boolean put(String entityURI, String className) throws CacheNotEmptyException {
    	try {
			if (singleKeyCache.lock("ID", -1)) {
				int size = singleKeyCache.size();
				if (size > 0) {
					throw new CacheNotEmptyException("Cannot put more than one key in this cache");
				}
				singleKeyCache.put(entityURI, className);
				return true;
			}
			return false;
		} finally {
        	singleKeyCache.unlock("ID");
        }
    }

    protected String remove(String entityURI) {
    	try {
			if (singleKeyCache.lock("ID", -1)) {
				String key = singleKeyCache.remove(entityURI);
				return key;
			}
			return null;
		} finally {
        	singleKeyCache.unlock("ID");
        }
    }

    public Set<String> keySet() {
        return singleKeyCache.keySet();
    }

    public int size() {
        return singleKeyCache.size();
    }

    public void clear() {
        singleKeyCache.clear();
    }

    public void shutdown() {
        singleKeyCache.discard();
    }
}
