package com.tibco.cep.runtime.service.om.impl.coherence.cluster.backingstore;

import java.util.Collection;
import java.util.Map;

import com.tangosol.net.cache.CacheStore;
import com.tibco.be.oracle.OracleStore;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

public class BECoherenceOracleStore extends OracleStore implements CacheStore {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(BECoherenceOracleStore.class);

    /**
     * @param cacheName
     */
    public BECoherenceOracleStore(String cacheName) {
        super(cacheName);
        logger.log(Level.INFO, "Constructing " + cacheName + " BECoherenceJdbcStore");
    }

    public BECoherenceOracleStore(String cacheName, Integer readOnly) {
        super(cacheName, readOnly);
        logger.log(Level.INFO, "Constructing " + cacheName + " read-only=" + ((readOnly == 0) ? "false":"true"));
    }

    public BECoherenceOracleStore(String cacheName, boolean forRecovery) {
        super(cacheName, forRecovery);
        logger.log(Level.INFO, "Constructing " + cacheName + " for-recovery=" + forRecovery);
    }

    public BECoherenceOracleStore(String cacheName, String schedulerCacheName) {
        super(cacheName, schedulerCacheName);
        logger.log(Level.INFO, "Constructing " + cacheName + " with scheduler cache " + schedulerCacheName);
    }

    public BECoherenceOracleStore(String cacheName, String schedulerCacheName, Integer readOnly) {
        super(cacheName, schedulerCacheName, readOnly);
        logger.log(Level.INFO, "Constructing " + cacheName  + " with scheduler cache " + schedulerCacheName + " with read-only=" + readOnly);
    }

    public BECoherenceOracleStore() {
        super();
        logger.log(Level.INFO, "Constructing generic backing store");
    }

    public void store(Object key, Object value) {
    	super.store(key, value, null);
    }

    public void storeAll(Map map) {
    	super.storeAll(map);
    }

    public void erase(Object object) {
        super.erase(object);
    }

    public void eraseAll(Collection collection) {
        super.eraseAll(collection);
    }

    public Object load(Object object) {
        return super.load(object);
    }

    public Map loadAll(Collection collection) {
        return super.loadAll(collection);
    }
}
