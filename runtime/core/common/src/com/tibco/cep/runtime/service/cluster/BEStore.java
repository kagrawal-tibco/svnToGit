package com.tibco.cep.runtime.service.cluster;

import java.util.Iterator;
import java.util.Map;

import com.tibco.be.util.config.BEStoreConfiguration;
import com.tibco.be.util.config.StoreConfigManager.StoreProvider;
import com.tibco.cep.runtime.service.cluster.backingstore.CacheAsideBackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.RecoveryManager;

/**
 * 
 * @author bala
 * 
 * An abstraction that combines cache and storage.
 * 
 * Store is one of these types:
 * imdb cache+bs (cache-aside) (rdbms + as2.x)
 * imdb cache+bs (write-behind) (rdbms + as2.x) or S/N
 * imdb cache-only-no-storage (AS2.0)
 * nosql like (store-and-cache) (AS-3.0, Cassandra)
 * 
 *
 * 
 * 
 */
public interface BEStore extends CacheProvider, CacheAsideBackingStore {

	/**
	 * Each store implementation provides its own recovery manager to the framework
	 * 
	 * @return
	 */
	RecoveryManager getRecoveryManager();

	String DEFAULTSTORE_NAME = "default";
	
	public String getStoreName();
	
	public StoreProvider getStoreProvider();
	
	public Map createOrGetStoreControlDao(Class keyClass, Class valueClass, String daoName, Object... additionalProps) throws Exception;

	public BEStoreConfiguration getBEStoreConfiguration();

	Iterator query(String query, Object[] queryParameters, Object queryOptions, String entityPath) throws Exception;
	
	public void commit() throws Exception;
	
	public void rollback() throws Exception;
	
	/*boolean lock(String key, long timeoutMillis);
	boolean unlock(String key);*/
	
}
