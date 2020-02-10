package com.tibco.cep.runtime.management.impl.adapter;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Mar 18, 2009 Time: 1:27:25 PM
*/
public interface RemoteCoherenceMetricDataListener extends Remote {
    String getName() throws RemoteException;

    void onNew(FQName key, Data data) throws RemoteException;
}
