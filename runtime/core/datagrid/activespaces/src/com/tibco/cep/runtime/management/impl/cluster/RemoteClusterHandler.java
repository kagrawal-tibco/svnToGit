/**
 * 
 */
package com.tibco.cep.runtime.management.impl.cluster;

import com.tibco.as.space.MemberDef;


/**
 * @author Nick
 *
 */
public class RemoteClusterHandler{

	private String clusterName;
	private MemberDef cd;
	private InternalManagementTable managementTable;
	private InternalMetricTable metricTable;
	private InternalCacheTable cacheTable;
	
	public RemoteClusterHandler(String cluster, MemberDef cd){
		this.clusterName = cluster;
		this.cd = cd;
		this.init();
	}
	
	private void init(){
		managementTable = new ASManagementTable();
		metricTable = new ASMetricTable();
		cacheTable = new ASCacheTable();
		((ASManagementTable)managementTable).setConnectionDef(cd);
		((ASMetricTable)metricTable).setConnectionDef(cd);
		managementTable.init(clusterName,"leech");
		metricTable.init(clusterName,"leech");
		cacheTable.init(clusterName);
	}

	public InternalManagementTable getManagementTable() {
		return managementTable;
	}

	public InternalMetricTable getMetricTable() {
		return metricTable;
	}

	public InternalCacheTable getCacheTable() {
		return cacheTable;
	}

	public String getClusterName() {
		return clusterName;
	}
	
	public void discard(String listenerName){
		this.metricTable.unregisterListener(listenerName, null);
		this.managementTable.discard();
		this.cacheTable.discard();
		this.metricTable.discard();
	}
}
