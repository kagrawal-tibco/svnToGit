package com.tibco.cep.runtime.service.om.impl.datastore;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Jul 26, 2004
 * Time: 9:20:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class LRUCache extends LinkedHashMap {

    private final static float DEFAULT_LOAD_FACTOR = 1.0F;
    private final int maxEntries;
    private CacheCallBack callback;

    LRUCache(int maxsize) {
        super(maxsize + 1, DEFAULT_LOAD_FACTOR, true);
        maxEntries = maxsize;
        callback = null;
    }

    LRUCache(int maxsize, CacheCallBack callback ) {
        this(maxsize);
        this.callback = callback;
    }

    protected boolean removeEldestEntry(Map.Entry e) {
        if(size() > maxEntries) {

            if(callback != null)
                return callback.EntryFallOff(e.getKey(), e.getValue());
            else
                return true;
        }
        else
            return false;
    }

}
