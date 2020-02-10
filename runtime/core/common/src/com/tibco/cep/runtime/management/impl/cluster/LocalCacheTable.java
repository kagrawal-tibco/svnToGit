package com.tibco.cep.runtime.management.impl.cluster;

import java.util.Collection;
import java.util.LinkedList;

import com.tibco.cep.runtime.management.CacheInfo;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Apr 16, 2009 Time: 1:50:01 PM
*/
public class LocalCacheTable implements InternalCacheTable {
    public void init(String clusterURL) {
    }

    public void discard() {
    }

    public Collection<CacheInfo> getCacheInfos() {
        return new LinkedList<CacheInfo>();
    }

    public CacheInfo getCacheInfo(FQName name) {
        return null;
    }
}
