package com.tibco.cep.runtime.management.impl.adapter;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import com.tibco.cep.runtime.management.MetricDef;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Mar 17, 2009 Time: 11:52:40 PM
*/
public interface RemoteCoherenceMetricTable extends Remote {
    Collection<FQName> getMetricDefNames() throws RemoteException;

    void addMetricDef(MetricDef metricDef) throws RemoteException;

    MetricDef getMetricDef(FQName fqn) throws RemoteException;

    MetricDef removeMetricDef(FQName fqn) throws RemoteException;

    Collection<FQName> getMetricNames() throws RemoteException;

    void addMetricData(FQName fqn, Data data) throws RemoteException;

    Data getMetricData(FQName fqn) throws RemoteException;

    Data removeMetricData(FQName fqn) throws RemoteException;

    void addRemoteListener(RemoteCoherenceMetricDataListener remoteListener) throws RemoteException;

    /**
     * @param listenerName  Same as {@link RemoteCoherenceMetricDataListener#getName()}.
     * @param fqnToListenTo
     * @throws RemoteException
     */
    void startListening(String listenerName, FQName fqnToListenTo) throws RemoteException;

    /**
     * @param listenerName  Same as {@link RemoteCoherenceMetricDataListener#getName()}.
     * @param fqnToListenTo
     * @throws RemoteException
     */
    void stopListening(String listenerName, FQName fqnToListenTo) throws RemoteException;

    /**
     * @param remoteListenerName Same as {@link RemoteCoherenceMetricDataListener#getName()}.
     * @throws RemoteException
     */
    void removeRemoteListener(String remoteListenerName) throws RemoteException;
}
