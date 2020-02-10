package com.tibco.cep.runtime.service.cluster.om.mm;

import com.tibco.cep.runtime.service.cluster.mm.CacheClusterMBean;

public interface CoherenceClusterMBean extends CacheClusterMBean {

	//public void startCluster() throws Exception;

	String getSiteName();

	String getRackName();

	String getMachineName();

	String getProcessName();

	void flushAll() throws Exception;//

	void flushCache();

	//public String getClusterState();

	public long getSiteId();
}
