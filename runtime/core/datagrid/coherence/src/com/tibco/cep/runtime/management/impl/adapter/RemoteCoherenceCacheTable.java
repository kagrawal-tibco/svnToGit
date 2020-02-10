package com.tibco.cep.runtime.management.impl.adapter;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import com.tibco.cep.runtime.management.CacheInfo;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Apr 20, 2009 Time: 7:04:33 PM
*/
public interface RemoteCoherenceCacheTable extends Remote {
    Collection<CacheInfo> getCacheInfos() throws RemoteException;

    CacheInfo getCacheInfo(FQName name) throws RemoteException;
}
