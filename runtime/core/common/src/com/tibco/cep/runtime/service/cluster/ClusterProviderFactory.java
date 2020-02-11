package com.tibco.cep.runtime.service.cluster;

import java.util.Properties;

import com.tibco.be.util.config.BEClusterConfiguration;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.cluster.ClusterDriverPojo;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * @author ssinghal
 *
 */
public class ClusterProviderFactory extends ServiceProviderFactory{
	
	private final static Logger logger = LogManagerFactory.getLogManager().getLogger(ClusterProviderFactory.class.getSimpleName());
	private Cluster cluster;
	private ClusterProvider clusterProvider;
	
	
	public static ClusterProviderFactory INSTANCE = new ClusterProviderFactory();
	
	private ClusterProviderFactory() {}

	@Override
	public Object getProvider() {
		return clusterProvider;
	}
	
	public void configure(Cluster cluster, Properties properties) throws Exception {
		
		super.configure(cluster, properties);
		
		String clusterProviderClz;
		BEClusterConfiguration beClusterConfig = (BEClusterConfiguration)properties.get(SystemProperty.VM_CLUSTER_CONFIG.getPropertyName());
		if (beClusterConfig == null) {
			clusterProviderClz = "com.tibco.be.noop.services.NoOpClusterProvider";
		} else {
			ClusterDriverPojo clustProviderPojo = (ClusterDriverPojo) beClusterConfig.getDp();
			clusterProviderClz = clustProviderPojo.getClassName();
			beClusterConfig.setProperty("clusterName", this.cluster.getClusterName());
		}
		clusterProvider = (ClusterProvider) Class.forName(clusterProviderClz).getConstructor(BEClusterConfiguration.class).newInstance(beClusterConfig);
		
	}

	

}
