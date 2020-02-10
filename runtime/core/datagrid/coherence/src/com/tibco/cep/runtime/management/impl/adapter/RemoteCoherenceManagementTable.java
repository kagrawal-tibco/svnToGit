package com.tibco.cep.runtime.management.impl.adapter;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import com.tibco.cep.runtime.management.Domain;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Mar 17, 2009 Time: 11:44:34 PM
*/
public interface RemoteCoherenceManagementTable extends Remote {
    void putOrRenewDomain(Domain domain, long leaseDurationMillis) throws RemoteException;

    Collection<Domain> getDomains() throws RemoteException;

    Domain getDomain(FQName key) throws RemoteException;

    Domain removeDomain(FQName key) throws RemoteException;
}
