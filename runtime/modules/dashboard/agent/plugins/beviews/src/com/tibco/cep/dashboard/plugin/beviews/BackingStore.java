package com.tibco.cep.dashboard.plugin.beviews;

import java.util.Properties;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.cep.dashboard.management.ServiceContext;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.util.SystemProperty;

public class BackingStore {

	private static BackingStore instance;

	public static final synchronized BackingStore getInstance() {
		if (instance == null) {
			instance = new BackingStore();
		}
		return instance;
	}

	private boolean enabled;

	private String databaseType;

	private BackingStore() {
		enabled = false;
	}

	void init(Properties properties, Logger logger, ServiceContext serviceContext) {
		ClusterConfig clusterConfig = (ClusterConfig) serviceContext.getRuleServiceProvider().getProperties().get(SystemProperty.CLUSTER_CONFIG.getPropertyName());
		BackingStoreConfig backingStoreConfig = clusterConfig.getObjectManagement().getCacheManager().getBackingStore();
		String persistenceOption = CddTools.getValueFromMixed(backingStoreConfig.getPersistenceOption());
		if (persistenceOption != null) {
			if(persistenceOption.equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL) == true) {
	            enabled = true;
	            databaseType = CddTools.getValueFromMixed(backingStoreConfig.getType());
	        }
		}
	}

	public static final boolean isEnabled(){
		return getInstance().enabled;
	}

	public static final boolean isJDBC(){
		String type = getInstance().databaseType;
		if (type == null) {
			return false;
		}
		return BackingStoreConfig.TYPE_BDB.equals(type) == false;
	}

}