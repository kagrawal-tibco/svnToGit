package com.tibco.cep.runtime.service.cluster.om.mm;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import com.tangosol.net.CacheFactory;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.mm.AbstractClusterMBeanImpl;
import com.tibco.cep.runtime.service.cluster.mm.CacheClusterMBean;
import com.tibco.cep.runtime.service.om.api.InvocationService;

public class CoherenceClusterMBeanImpl extends AbstractClusterMBeanImpl
		implements CoherenceClusterMBean {

	public CoherenceClusterMBeanImpl(Cluster cluster){
		super(cluster);
	}
	
	@Override
	public void registerMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("com.tibco.be:service=Cluster,name=" + this.cluster.getClusterName() + "$cluster");
            StandardMBean mBean = new StandardMBean(this, CoherenceClusterMBean.class);
            mbs.registerMBean(mBean, name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	@Override
    public void flushAll() throws Exception {
		try {
			if (this.isBackingStoreEnabled() && !this.isCacheAside()) {
			    if (cluster != null) {
			        InvocationService invoker=this.cluster.getDaoProvider().getInvocationService();
			        if(invoker != null) {
			        	invoker.invoke(new CacheFlush(), this.cluster.getGroupMembershipService().getStorageEnabledMembers());
			        }
			    }
			}
		} catch (Exception e) {
			this.cluster.getRuleServiceProvider().getLogger(CacheClusterMBean.class).log(Level.ERROR,e, e.getMessage());
		}
    }
	
    @Override
    public void flushCache() {
    	CacheFlush.flushCache(this.cluster);
    }
    
	@Override
	public String getMachineName() {
		return CacheFactory.getCluster().getLocalMember().getMachineName();
	}

	@Override
	public String getProcessName() {
        return CacheFactory.getCluster().getLocalMember().getProcessName();
    }

	@Override
	public String getRackName() {
        return CacheFactory.getCluster().getLocalMember().getRackName();
	}

	@Override
	public long getSiteId() {
        return cluster.getClusterConfig().getSiteId();
    }

	@Override
	public String getSiteName() {
        return CacheFactory.getCluster().getLocalMember().getSiteName();
	}
	
}
