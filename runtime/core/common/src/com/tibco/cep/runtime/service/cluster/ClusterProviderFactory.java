package com.tibco.cep.runtime.service.cluster;

import java.lang.reflect.InvocationTargetException;

import com.tibco.be.util.config.ClusterProviderConfiguration;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.cluster.ClusterDriverPojo;

/**
 * @author ssinghal
 *
 */
public class ClusterProviderFactory{
	
	private final static Logger logger = LogManagerFactory.getLogManager().getLogger(ClusterProviderFactory.class.getSimpleName());
	private static ClusterProvider clusterProvider;
	
	public static ClusterProvider getClusterProvider(Cluster cluster, ClusterProviderConfiguration clusterConfig) 
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		
		String clusterProviderClz;
		ClusterProviderConfiguration beClusterConfig = clusterConfig;
		if (beClusterConfig == null) {
			clusterProviderClz = "com.tibco.be.noop.services.NoOpClusterProvider";
		} else {
			ClusterDriverPojo clustProviderPojo = (ClusterDriverPojo) beClusterConfig.getDp();
			clusterProviderClz = clustProviderPojo.getClassName();
			beClusterConfig.setProperty("clusterName", cluster.getClusterName());
		}
		clusterProvider = (ClusterProvider) Class.forName(clusterProviderClz).getConstructor(ClusterProviderConfiguration.class).newInstance(beClusterConfig);
		return clusterProvider;
		
	}
	
}
