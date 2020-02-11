package com.tibco.cep.runtime.service.store;

import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.RecoveryManager;

public interface StoreProvider {

	RecoveryManager getRecoveryManager();
	
	Map createOrGetStoreControlDao(Class keyClass, Class valueClass, String daoName);
	
	Iterator query(String query, Object[] queryParameters, Object queryOptions, String entityPath);
	
	void commit();
	
	void rollback();
	
	public CacheProvider getCacheProvider();
	
	public BackingStore getBackingStore();
}
  