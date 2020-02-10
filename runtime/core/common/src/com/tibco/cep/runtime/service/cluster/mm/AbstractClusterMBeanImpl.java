/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Bala
 * Date  : 4 Jan 2011
 * 
 * Implements CacheClusterMBean.
 * 
 */

package com.tibco.cep.runtime.service.cluster.mm;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;


public abstract class AbstractClusterMBeanImpl implements CacheClusterMBean {

	protected Cluster cluster;
	
	public AbstractClusterMBeanImpl(Cluster cluster) {
		this.cluster = cluster;
		registerMBean();
	}
	
	@Override
	public String getCacheType() {
		 return cluster.getClusterConfig().getCacheType();
	}

	@Override
	public String getClusterName() {
		return cluster.getClusterName();
	}
	
	@Override
    public String getPersistenceOption() {
    	return cluster.getClusterConfig().getPersistenceOption();
    }

	@Override
	public String[] getLoadInfo() {
	    if (cluster.getExternalClassesCache() != null) {
            return cluster.getExternalClassesCache().getLoadInfo();
        } else {
            return new String[0];
        }
	}

    @Override
    public String[] getRecoveryInfo() {
        if (cluster.getRecoveryManager() != null) {
            return cluster.getRecoveryManager().getRecoveryStatus();
        } else {
            return new String[0];
        }
    }

    @Override
    public String[] getPreloadInfo() {
        if (cluster.getRecoveryManager() != null) {
            return cluster.getRecoveryManager().getPreloadStatus();
        } else {
            return new String[0];
        }
    }

	@Override
	public int getMemberCount() {
		return cluster.getGroupMembershipService().getMembers().size();
	}

	@Override
	public boolean isAutoStartup() {
		return cluster.getClusterConfig().isAutoStartup();
	}

	@Override
	public boolean isBackingStoreEnabled() {
		return cluster.getClusterConfig().isHasBackingStore();
	}

	@Override
	public boolean isCacheAside() {
		return cluster.getClusterConfig().isCacheAside();
	}

	@Override
	public boolean isSerializationOptimized() {
		return cluster.getClusterConfig().isNewSerializationEnabled();
	}

	@Override
	public boolean isStorageEnabled() {
		return cluster.getClusterConfig().isStorageEnabled();
	}
	
	@Override
	public String getNodeId() {
		return cluster.getGroupMembershipService().getLocalMember().getMemberId().getAsString();
	}
	
	@Override
	public void loadAndDeploy() {
    	Logger logger = cluster.getRuleServiceProvider().getLogger(CacheClusterMBean.class);
        if (cluster.getClusterConfig().isClassLoader()) {
            try {
                logger.log(Level.INFO, "Cluster %s : Hot Deployment Request, Loading Classes From File System", this.getClusterName());
                if (cluster.getClusterConfig().getExternalClassesDir() != null) {
                	cluster.getExternalClassesCache().loadClasses(cluster.getClusterConfig().getExternalClassesDir());
                }

                logger.log(Level.INFO, "Cluster %s : Hot Deployment Request, Issue Reload to All Nodes", this.getClusterName());

                cluster.getHotDeployer().insert();
            } catch (Exception ex) {
                logger.log(Level.WARN, ex, "Loading/deploying Cache Cluster failed");
            }
        }		
	}

	@Override
	public void loadAndDeploy(String vrfURI, String implName) {
		Logger logger = cluster.getRuleServiceProvider().getLogger(CacheClusterMBean.class);
		if (cluster.getClusterConfig().isClassLoader()) {
            try {
                logger.log(Level.INFO, "Cluster %s : Hot Deployment Request, Loading Class From File System", this.getClusterName());
                if (cluster.getClusterConfig().getExternalClassesDir() != null) {
                	cluster.getExternalClassesCache().loadClass(cluster.getClusterConfig().getExternalClassesDir(), vrfURI, implName);
                }

                logger.log(Level.INFO, "Cluster %s : Hot Deployment Request, Issue Reload to All Nodes", this.getClusterName());
                cluster.getHotDeployer().insert();
            } catch (Exception ex) {
                logger.log(Level.WARN, ex, "Loading/deploying Cache Cluster failed");
            }
	    }		
	}
	
    public void SetLogLevel(String level) throws Exception {
        LogManager logManager = LogManagerFactory.getLogManager();
        logManager.getLogger("*").setLevel(Level.valueOf(level));
	}
	
	@Override
	public void resumeNode() {
		((RuleServiceProviderImpl)cluster.getRuleServiceProvider()).resumeRSP();	
	}

	@Override
	public void suspendNode() {
		((RuleServiceProviderImpl)cluster.getRuleServiceProvider()).suspendRSP(RuleServiceProviderImpl.SUSPEND_MODE_CLUSTER);
	}

	@Override
	public void unloadClass(String vrfURI, String implName) throws Exception {
		cluster.getHotDeployer().unloadClass(vrfURI, implName);
	}

	public abstract void registerMBean();

	/*private void registerMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("com.tibco.be:service=Cluster,name=" + cluster.getClusterName() + "$cluster");
            StandardMBean mBean = new StandardMBean(this, CacheClusterMBean.class);
            mbs.registerMBean(mBean, name);
        } catch (Exception ex) {
        }
    }*/

	/*@Override
	public long getSiteId() {
		return cluster.getClusterConfig().getSiteId();
	}
	
	@Override
	public String getSiteName() {
		DaoProvider daoProvider = cluster.getDaoProvider();
		if (daoProvider.getClass().getName().equals(SystemProperty.VM_DAOPROVIDER_CLASSNAME.getValidValues()[1].toString())) {
			return CacheFactory.getCluster().getLocalMember().getSiteName();
		}
		return null;
	}

	@Override
	public String getRackName() {
		DaoProvider daoProvider = cluster.getDaoProvider();
		if (daoProvider.getClass().getName().equals(SystemProperty.VM_DAOPROVIDER_CLASSNAME.getValidValues()[1].toString())) {
			return CacheFactory.getCluster().getLocalMember().getRackName();
		}
		return null;
	}
	
	 
    // Could be common
    //@see com.tibco.cep.runtime.service.cluster.mm.CacheClusterMBean#getMachineName()
	@Override
	public String getMachineName() {
		DaoProvider daoProvider = cluster.getDaoProvider();
		if (daoProvider.getClass().getName().equals(SystemProperty.VM_DAOPROVIDER_CLASSNAME.getValidValues()[1].toString())) {
			return CacheFactory.getCluster().getLocalMember().getMachineName();
		}
		return null;
	}

	@Override
	public String getProcessName() {
		DaoProvider daoProvider = cluster.getDaoProvider();
		if (daoProvider.getClass().getName().equals(SystemProperty.VM_DAOPROVIDER_CLASSNAME.getValidValues()[1].toString())) {
			return CacheFactory.getCluster().getLocalMember().getProcessName();
		}
		return null;
	}

	@Override
	public String getClusterState() {
		MasterCache masterCache = new MasterCache(this.cluster);
        MasterCache.ClusterState st = masterCache.getClusterState();
        return STATES[st.clusterState];
	}*/
}
