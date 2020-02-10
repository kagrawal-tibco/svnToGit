package com.tibco.cep.runtime.management.impl.adapter;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.EnumMap;

/*
* Author: Ashwin Jayaprakash Date: Mar 25, 2009 Time: 1:51:12 PM
*/
public interface RemoteProcessManager extends Remote {
    /**
     * {@value}.
     */
    long PING_DELAY_MILLIS = 30 * 1000;

    /**
     * @return <code>null</code> if not {@link #init() inited} successfully.
     * @throws java.rmi.RemoteException
     */
    EnumMap<RemoteClusterSpecialPropKeys, String> getPropsIfAlreadyInited() throws RemoteException;

    /**
     * Call {@link #getPropsIfAlreadyInited()} to check first.
     *
     * @throws RemoteException
     */
    void init() throws RemoteException;

    void ping() throws RemoteException;

    Collection<Throwable> fetchRecentExceptions() throws RemoteException;

    void shutdown(int status) throws RemoteException;
}
