/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.cluster.util;

import com.tangosol.net.cache.CacheLoader;
import com.tangosol.net.cache.LocalCache;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jan 21, 2009
 * Time: 7:18:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class BELocalCache extends LocalCache {
    public BELocalCache() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BELocalCache(int i) {
        super(i);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BELocalCache(int i, int i1) {
        super(i, i1);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BELocalCache(int i, int i1, CacheLoader cacheLoader) {
        super(i, i1, cacheLoader);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public Object remove(Object o) {
        super.remove(o);    //To change body of overridden methods use File | Settings | File Templates.
        return null;
    }


}
