package com.tibco.cep.runtime.service.cluster;

import java.util.Properties;

import com.tibco.be.util.config.BECacheConfiguration;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.cluster.CacheDriverPojo;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * @author ssinghal
 *
 */
public class CacheProviderFactory extends ServiceProviderFactory{
	
	private final static Logger logger = LogManagerFactory.getLogManager().getLogger(ClusterProviderFactory.class.getSimpleName());
	private Cluster cluster;
	private BECacheProvider cacheProvider;
	
	
	public static CacheProviderFactory INSTANCE = new CacheProviderFactory();
	
	private CacheProviderFactory() {
	}

	@Override
	public Object getProvider() {
		return cacheProvider;
	}
	
	public void configure(Cluster cluster, Properties properties) throws Exception {
		
		super.configure(cluster, properties);
		
		String cacheProviderClz;
		BECacheConfiguration beClusterConfig = (BECacheConfiguration)properties.get(SystemProperty.VM_CACHE_CONFIG.getPropertyName());
		if (beClusterConfig == null) {
			cacheProviderClz = "com.tibco.be.noop.services.NoOpClusterProvider";
		} else {
			CacheDriverPojo cacheProviderPojo = (CacheDriverPojo) beClusterConfig.getDp();
			cacheProviderClz = cacheProviderPojo.getClassName();
			beClusterConfig.setProperty("clusterName", this.cluster.getClusterName());
		}
		cacheProvider = (BECacheProvider) Class.forName(cacheProviderClz).getConstructor(BECacheConfiguration.class).newInstance(beClusterConfig);
		
	}

}
