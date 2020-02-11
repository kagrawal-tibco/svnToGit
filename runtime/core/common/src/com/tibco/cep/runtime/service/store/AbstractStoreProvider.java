package com.tibco.cep.runtime.service.store;

import com.tibco.cep.runtime.service.cluster.CacheProvider;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;

public abstract class AbstractStoreProvider implements StoreProvider {
	
	private GenericBackingStore cacheAsideBackingStore;
	
	private CacheProvider cacheProvider;
	
	private StoreSerializer storeSerializer;
}
