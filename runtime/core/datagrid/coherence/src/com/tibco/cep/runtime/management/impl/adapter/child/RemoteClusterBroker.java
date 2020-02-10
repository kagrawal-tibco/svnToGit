package com.tibco.cep.runtime.management.impl.adapter.child;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.DistributedCacheService;
import com.tibco.cep.runtime.management.impl.adapter.RemoteCoherenceCacheTable;
import com.tibco.cep.runtime.management.impl.adapter.RemoteCoherenceManagementTable;
import com.tibco.cep.runtime.management.impl.adapter.RemoteCoherenceMetricTable;
import com.tibco.cep.runtime.management.impl.adapter.RemoteProcessManager;
import com.tibco.cep.runtime.util.ProcessInfo;

/*
* Author: Ashwin Jayaprakash Date: Mar 17, 2009 Time: 11:41:12 PM
*/
public class RemoteClusterBroker {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }

    /**
     * {@value}.
     */
    public static final int DEFAULT_PORT = 11200;

    protected RemoteProcessManagerImpl processManager;
    protected DistributedCacheService dcs;
    protected ClusterMemberEventListener mListener;

    public void start(int port) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(port);

        //-------------

        processManager = new RemoteProcessManagerImpl();

        //-------------

        RemoteCoherenceManagementTableImpl managementTable =
                processManager.getManagementTableLocal();
        RemoteCoherenceManagementTable managementTableStub =
                (RemoteCoherenceManagementTable) UnicastRemoteObject
                        .exportObject(managementTable,0);
        registry.rebind(RemoteCoherenceManagementTable.class.getName(), managementTableStub);

        //-------------

        RemoteCoherenceMetricTableImpl metricTable = processManager.getMetricTableLocal();
        RemoteCoherenceMetricTable metricTableStub =
                (RemoteCoherenceMetricTable) UnicastRemoteObject
                        .exportObject(metricTable,0);
        registry.rebind(RemoteCoherenceMetricTable.class.getName(), metricTableStub);

        //-------------

        RemoteCoherenceCacheTableImpl cacheTable = processManager.getCacheTableLocal();
        RemoteCoherenceCacheTable cacheTableStub =
                (RemoteCoherenceCacheTable) UnicastRemoteObject
                        .exportObject(cacheTable,0);
        registry.rebind(RemoteCoherenceCacheTable.class.getName(), cacheTableStub);

        //-------------

        //Register this in the end.
        RemoteProcessManager processManagerStub = (RemoteProcessManager) UnicastRemoteObject
                .exportObject(processManager,0);
        registry.rebind(RemoteProcessManager.class.getName(), processManagerStub);

        //-------------
        
        String s = String.format("Successfully created remote services in process [%s]." +
                "%nServices listening on port [%s]:" +
                "%n   [%s]%n   [%s]%n   [%s]%n   [%s]",
                ProcessInfo.getProcessIdentifier(),
                port + "",
                RemoteProcessManager.class.getName(),
                RemoteCoherenceManagementTable.class.getName(),
                RemoteCoherenceMetricTable.class.getName(),
                RemoteCoherenceCacheTable.class.getName());

        System.out.println(s);
        System.out.println();
    }
    
    protected void registerMemberListener(){
        mListener = new ClusterMemberEventListener(processManager);
        dcs = (DistributedCacheService) CacheFactory.getService("DistributedCache");
        dcs.addMemberListener(mListener);    	
    }
    
    protected void removeMemberListener(){
    	if(mListener != null && dcs != null){
    		dcs.removeMemberListener(mListener);
    	}
    }
    

    protected void waitForVMExit() {
        for (; ;) {
            try {
                Thread.sleep(Integer.MAX_VALUE);
            }
            catch (InterruptedException e) {
                //Ignore.
            }
        }
    }

    protected void shutdown(int status) throws RemoteException {
        processManager.shutdown(status);
        removeMemberListener();
    }

    public static void main(String[] args) {
        try {
            int port = DEFAULT_PORT;

            if (args == null || args.length == 0) {
                System.out.println("Attempting to use default port: " + DEFAULT_PORT);
            }
            else {
                port = Integer.parseInt(args[0]);
                System.out.println("Attempting to use port: " + port);
            }

            System.out.println("Starting with the following properties: ");
            System.out.println(System.getProperties());
            System.out.println();

            RemoteClusterBroker broker = new RemoteClusterBroker();
            broker.start(port);
            
            broker.registerMemberListener();

            broker.waitForVMExit();

            broker.shutdown(0);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }

        System.err.println("Shutting down.");
        System.exit(1);
    }
}
