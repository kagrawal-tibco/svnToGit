package com.tibco.cep.runtime.service.cluster;

import java.util.Properties;

import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

public abstract class ServiceProviderFactory {
	
	private final static Logger logger = LogManagerFactory.getLogManager().getLogger(ServiceProviderFactory.class.getSimpleName());
	
	private Cluster cluster;
	private ClusterProvider clusterProvider;
	//private BEStore defaultStore;

	public ClusterProvider getClusterProvider() {
		return clusterProvider;
	}

	/*
	public BEStore getStore() {
		return defaultStore;
	}
	*/

	public void configure(Cluster cluster, Properties properties) throws Exception {
		this.cluster = cluster;
	}
	
	
}
