package com.tibco.be.jdbcstore.impl;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.Map;

/**
 * Provide a synchronized access and MRU based cache manager.
 * The iteration order is based on the last access operation (get or put).
 * The oldest object is dropped is the size of the cache exceeds the limit.
 * Close the statement if not used.
 */
@SuppressWarnings("serial")
public class StatementCacheManager extends CacheManager {
    
    private int     mMaxCacheLimit = 100;

    protected StatementCacheManager(int maxCacheLimit) {
        // Set initial capacity to be about the limit.
        // Maintain iterator order to be access order.
        super(maxCacheLimit);
        mMaxCacheLimit = maxCacheLimit;
    }

    public static Map newInstance(int maxCacheLimit) {
        if (maxCacheLimit < 1)
            throw new IllegalArgumentException();

        StatementCacheManager cacheMgr = new StatementCacheManager(maxCacheLimit);
        return Collections.synchronizedMap(cacheMgr);
    }

    // Override baseclass's method to delete the eldest entry.
    protected boolean removeEldestEntry(Map.Entry eldest) {
        // Remove the eldest entry when size reaches limit.
        if (super.size() > mMaxCacheLimit) {
            Object e = eldest.getValue();
            StatementCacheEntry sce = (StatementCacheEntry) e;
            if (sce.getUseCount() <= 0) {  // close if not in use; let GC close otherwise
                PreparedStatement stmt = sce.getPreparedStatement();
                try {
                    stmt.close();
                } catch (Exception ee) {

                }
                return true;
            }
            return true;
        } else {
            return false;
        }
    }
}
