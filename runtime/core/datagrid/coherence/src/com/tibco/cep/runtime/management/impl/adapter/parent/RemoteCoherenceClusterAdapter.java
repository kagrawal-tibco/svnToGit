package com.tibco.cep.runtime.management.impl.adapter.parent;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.EnumMap;

import com.tibco.cep.runtime.management.impl.adapter.RemoteClusterSpecialPropKeys;
import com.tibco.cep.runtime.management.impl.adapter.RemoteCoherenceCacheTable;
import com.tibco.cep.runtime.management.impl.adapter.RemoteCoherenceManagementTable;
import com.tibco.cep.runtime.management.impl.adapter.RemoteCoherenceMetricTable;
import com.tibco.cep.runtime.management.impl.adapter.RemoteProcessManager;

/*
* Author: Ashwin Jayaprakash Date: Mar 2, 2009 Time: 2:12:19 PM
*/
public class RemoteCoherenceClusterAdapter {
    protected RemoteProcessManager processManager;

    protected RemoteCoherenceManagementTable managementTable;

    protected RemoteCoherenceMetricTable metricDataTable;

    protected RemoteCoherenceCacheTable cacheTable;

    protected EnumMap<RemoteClusterSpecialPropKeys, String> initedProps;

    protected boolean initedBySelf;

    /**
     * If the remote services are already available and they are inited, then it just connects to
     * them and retrieves their properties.
     *
     * @param port
     * @return
     * @throws RemoteException
     * @throws NotBoundException
     */
    public EnumMap<RemoteClusterSpecialPropKeys, String> initIfRequired(int port)
            throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(port);

        processManager = (RemoteProcessManager) registry
                .lookup(RemoteProcessManager.class.getName());

        managementTable = (RemoteCoherenceManagementTable) registry
                .lookup(RemoteCoherenceManagementTable.class.getName());

        metricDataTable = (RemoteCoherenceMetricTable) registry
                .lookup(RemoteCoherenceMetricTable.class.getName());

        cacheTable = (RemoteCoherenceCacheTable) registry
                .lookup(RemoteCoherenceCacheTable.class.getName());

        initedProps = processManager.getPropsIfAlreadyInited();

        if (initedProps == null) {
            processManager.init();
            initedProps = processManager.getPropsIfAlreadyInited();

            initedBySelf = true;
        }

        return initedProps;
    }

    /**
     * From {@link com.tibco.cep.runtime.management.impl.adapter.RemoteProcessManager#getPropsIfAlreadyInited()}.
     *
     * @return
     */
    public EnumMap<RemoteClusterSpecialPropKeys, String> getInitedProps() {
        return initedProps;
    }

    public boolean wasInitedBySelf() {
        return initedBySelf;
    }

    public RemoteProcessManager getProcessManager() {
        return processManager;
    }

    public RemoteCoherenceManagementTable getManagementTable() {
        return managementTable;
    }

    public RemoteCoherenceMetricTable getMetricDataTable() {
        return metricDataTable;
    }

    public RemoteCoherenceCacheTable getCacheTable() {
        return cacheTable;
    }

    public void discard() throws RemoteException {
        if (initedBySelf && processManager != null) {
            processManager.shutdown(0);
        }

        processManager = null;
        cacheTable = null;
        metricDataTable = null;
        managementTable = null;

        initedProps = null;
    }
}