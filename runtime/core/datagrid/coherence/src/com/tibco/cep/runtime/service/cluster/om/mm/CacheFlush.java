package com.tibco.cep.runtime.service.cluster.om.mm;

import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.DefaultConfigurableCacheFactory;
import com.tangosol.net.DistributedCacheService;
import com.tangosol.net.Service;
import com.tangosol.net.cache.ReadWriteBackingMap;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.mm.CacheClusterMBean;
import com.tibco.cep.runtime.service.om.api.Invocable;

public class CacheFlush implements Invocable {

	private static final long serialVersionUID = 2709735676718205651L;

	public CacheFlush(){
		super();
	}
	
    public static void flushCache(Cluster cluster) {
        if (cluster.getClusterConfig().isHasBackingStore() && !cluster.getClusterConfig().isCacheAside()) {
            try {
                Enumeration<?> allServices = CacheFactory.getCluster().getServiceNames();
                while (allServices.hasMoreElements()) {
                    String s_svc = (String) allServices.nextElement();
                    Service c_svc = CacheFactory.getCluster().getService(s_svc);
                    if (c_svc instanceof DistributedCacheService) {
                        DistributedCacheService d_svc = (DistributedCacheService) c_svc;
                        DefaultConfigurableCacheFactory.Manager bmm = (DefaultConfigurableCacheFactory.Manager) d_svc.getBackingMapManager();
                        if (bmm != null) {
                            Enumeration<?> allCaches = d_svc.getCacheNames();
                            while (allCaches.hasMoreElements()) {
                                String s_cn = (String) allCaches.nextElement();
                                Map<?, ?> rwm = bmm.getBackingMap(s_cn);
                                if (rwm instanceof ReadWriteBackingMap) {
                                    ReadWriteBackingMap map = (ReadWriteBackingMap) rwm;
                                    if (map != null) {
                                        cluster.getRuleServiceProvider().getLogger(CacheClusterMBean.class).log(Level.DEBUG,"Flushing Write Behind Queue for Cache=" + s_cn);
                                        map.flush();
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {
            	cluster.getRuleServiceProvider().getLogger(CacheClusterMBean.class).log(Level.WARN, ex, "Flushing Cache failed");
            }
        }
    }

	@Override
	public Object invoke(Entry entry) throws Exception {
        try {
        	Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (cluster != null) {
                Thread.currentThread().setContextClassLoader(cluster.getRuleServiceProvider().getClassLoader());
                CacheFlush.flushCache(cluster);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
	}
}