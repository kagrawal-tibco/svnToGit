/**
 *
 */
package com.tibco.be.bemm.functions;

import com.tibco.cep.runtime.management.CacheTable;
import com.tibco.cep.runtime.management.ManagementTable;
import com.tibco.cep.runtime.management.MetricTable;
import com.tibco.cep.runtime.management.impl.adapter.parent.RemoteProcessMaster;
import com.tibco.cep.runtime.management.impl.adapter.parent.RemoteProcessesManager;
import com.tibco.cep.runtime.management.impl.cluster.RemoteClusterMaster;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;

import java.util.Collection;

/**
 * @author anpatil
 */
final class ClusterInfoProvider {

	private static ClusterInfoProvider instance;

	private boolean hasAsDataGrid;

    private RemoteClusterMaster clusterMaster;

    private RemoteProcessesManager remProcsMngr;

    static synchronized ClusterInfoProvider getInstance() throws RuntimeException {
        if (instance == null) {
            instance = new ClusterInfoProvider();
        }
        return instance;
    }

    private ClusterInfoProvider() throws RuntimeException {
		try {
			RuleServiceProvider currRuleServiceProvider =
                    RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();

			ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
			remProcsMngr = registry.getService(RemoteProcessesManager.class);

            //For Coherence we register RemoteProcessesManager, for AS we register RemoteClusterMaster
			if(remProcsMngr == null) {
				hasAsDataGrid = true;
				clusterMaster = registry.getService(RemoteClusterMaster.class);
			} else {
				Collection<RemoteProcessMaster>  remProcsMaster = remProcsMngr.getRemoteProcesses();
                if (remProcsMaster == null || remProcsMaster.isEmpty()) {
                    remProcsMaster = remProcsMngr.connectAll(); //might be redundant, but just in case
                }
			}
		} catch (Exception e) {
			throw new RuntimeException("could not connect to cache", e);
		}
	}

	ManagementTable getManagementTable(String clusterName) {
		if(hasAsDataGrid){
            return (clusterName == null) ? null : clusterMaster.getHandler(clusterName).getManagementTable();
		}
        //coherence datagrid
        return remProcsMngr.getRemoteProcess(clusterName).getProxyCoherenceManagementTable();
	}

	MetricTable getMetricTable(String clusterName) {
        if(hasAsDataGrid){
            return (clusterName == null) ? null : clusterMaster.getHandler(clusterName).getMetricTable();
        }
        //coherence datagrid
        return remProcsMngr.getRemoteProcess(clusterName).getProxyCoherenceMetricTable();
	}

	CacheTable getCacheTable(String clusterName) {
        if(hasAsDataGrid){
            return (clusterName == null) ? null : clusterMaster.getHandler(clusterName).getCacheTable() ;
        }
        //coherence datagrid
        return remProcsMngr.getRemoteProcess(clusterName).getProxyCoherenceCacheTable();
	}
}