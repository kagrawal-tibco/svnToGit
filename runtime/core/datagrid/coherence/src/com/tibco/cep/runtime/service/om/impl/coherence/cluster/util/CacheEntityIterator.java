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

import com.tangosol.net.NamedCache;
import com.tangosol.util.Filter;
import com.tangosol.util.filter.AlwaysFilter;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 14, 2008
 * Time: 4:47:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheEntityIterator implements java.util.Iterator {

    java.util.Iterator cur;

    public CacheEntityIterator(NamedCache cache) {
        cur = cache.entrySet(new AlwaysFilter()).iterator();
    }

    public CacheEntityIterator(NamedCache cache, Filter filter) {
        if (filter != null) {
            cur = cache.entrySet(filter).iterator();
        } else {
            cur = cache.entrySet(new AlwaysFilter()).iterator();
        }
    }

    public boolean hasNext() {
        return cur.hasNext();
    }

    public Object next() {
        return cur.next();
    }

    public void remove() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
