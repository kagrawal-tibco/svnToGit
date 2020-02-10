package com.tibco.cep.runtime.management.impl.adapter.util;

import java.util.Collection;

import com.tibco.cep.runtime.management.CacheInfo;
import com.tibco.cep.runtime.management.CacheTable;
import com.tibco.cep.runtime.management.impl.adapter.RemoteCoherenceCacheTable;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Apr 20, 2009 Time: 7:08:05 PM
*/
public class ProxyCoherenceCacheTable implements CacheTable {
    protected volatile RemoteCoherenceCacheTable target;

    public ProxyCoherenceCacheTable(RemoteCoherenceCacheTable target) {
        this.target = target;
    }

    public RemoteCoherenceCacheTable getTarget() {
        return target;
    }

    public void resetTarget(RemoteCoherenceCacheTable target) {
        this.target = target;
    }

    public Collection<CacheInfo> getCacheInfos() {
        try {
            return target.getCacheInfos();
        }
        catch (Exception e) {
            throw new RuntimeException("Error from remote process.", e);
        }
    }

    public CacheInfo getCacheInfo(FQName name) {
        try {
            return target.getCacheInfo(name);
        }
        catch (Exception e) {
            throw new RuntimeException("Error from remote process.", e);
        }
    }

    public void discard() {
        
    }
}
