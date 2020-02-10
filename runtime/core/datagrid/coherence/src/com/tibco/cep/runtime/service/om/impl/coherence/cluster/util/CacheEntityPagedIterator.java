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
import com.tangosol.util.filter.LimitFilter;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 14, 2008
 * Time: 4:48:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheEntityPagedIterator implements java.util.Iterator {
    java.util.Iterator cur;
    int pageNumber = -1;
    LimitFilter limitFilter;
    NamedCache cache;

    public CacheEntityPagedIterator(NamedCache cache, int limit) {
        this.cache = cache;
        limitFilter = new LimitFilter(new AlwaysFilter(), limit);
        nextPage();
    }

    public CacheEntityPagedIterator(NamedCache cache, Filter filter, int limit) {
        this.cache = cache;
        if (filter != null)
            limitFilter = new LimitFilter(filter, limit);
        else
            limitFilter = new LimitFilter(new AlwaysFilter(), limit);
        nextPage();
    }

    void nextPage() {
        ++pageNumber;
        limitFilter.setPage(pageNumber);
        cur = cache.entrySet(limitFilter).iterator();
    }

    public boolean hasNext() {
        if (!cur.hasNext()) {
            nextPage();
            return cur.hasNext();
        } else {
            return true;
        }
    }

    public Object next() {
        return cur.next();
    }

    public void remove() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
