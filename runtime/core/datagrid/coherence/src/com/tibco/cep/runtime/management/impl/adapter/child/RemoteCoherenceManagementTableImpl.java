package com.tibco.cep.runtime.management.impl.adapter.child;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.LinkedList;

import com.tibco.cep.runtime.management.Domain;
import com.tibco.cep.runtime.management.impl.adapter.RemoteCoherenceManagementTable;
import com.tibco.cep.runtime.management.impl.cluster.CoherenceManagementTable;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Mar 17, 2009 Time: 11:58:21 PM
*/
public class RemoteCoherenceManagementTableImpl implements RemoteCoherenceManagementTable {
    protected CoherenceManagementTable target;

    protected ExceptionCollector exceptionCollector;

    public void init(String clusterName, ExceptionCollector exceptionCollector) {
        this.target = new CoherenceManagementTable();
        this.target.init(clusterName,null);

        this.exceptionCollector = exceptionCollector;
    }

    public void putOrRenewDomain(Domain domain, long leaseDurationMillis) throws RemoteException {
        target.putOrRenewDomain(domain, leaseDurationMillis);
    }

    public Collection<Domain> getDomains() throws RemoteException {
        return new LinkedList<Domain>(target.getDomains());
    }

    public Domain getDomain(FQName key) throws RemoteException {
        return target.getDomain(key);
    }

    public Domain removeDomain(FQName key) throws RemoteException {
        return target.removeDomain(key);
    }

    public void discard() {
        target.discard();
    }
}
