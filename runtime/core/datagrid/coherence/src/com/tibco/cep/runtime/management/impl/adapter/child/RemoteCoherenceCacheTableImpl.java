package com.tibco.cep.runtime.management.impl.adapter.child;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.LinkedList;

import com.tibco.cep.runtime.management.CacheInfo;
import com.tibco.cep.runtime.management.impl.adapter.RemoteCoherenceCacheTable;
import com.tibco.cep.runtime.management.impl.cluster.CoherenceCacheTable;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Apr 20, 2009 Time: 7:05:14 PM
*/
public class RemoteCoherenceCacheTableImpl implements RemoteCoherenceCacheTable {
    protected CoherenceCacheTable target;

    protected ExceptionCollector exceptionCollector;

    public void init(String clusterName, ExceptionCollector exceptionCollector) {
        this.target = new CoherenceCacheTable();
        this.target.init(clusterName);

        this.exceptionCollector = exceptionCollector;
    }

    public Collection<CacheInfo> getCacheInfos() throws RemoteException {
        return new LinkedList<CacheInfo>(target.getCacheInfos());
    }

    public CacheInfo getCacheInfo(FQName name) throws RemoteException {
        return target.getCacheInfo(name);
    }

    public void discard() {
        target.discard();
    }
}
