package com.tibco.cep.query.stream.impl.cache;

import java.lang.ref.Reference;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.collections4.map.LRUMap;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
 * Author: Ashwin Jayaprakash Date: Dec 18, 2007 Time: 12:00:20 PM
 */

public class SimpleLocalCache implements Cache {
    protected Map lruMap;

    protected ResourceId resourceId;

    public SimpleLocalCache(ResourceId parent, String name, int maxItems, long expiryTimeMillis) {
        this.lruMap = Collections.synchronizedMap(new LRUMap(Math.max(1, maxItems)));

        this.resourceId = new ResourceId(parent, name);
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
        lruMap.clear();

        resourceId.discard();
        resourceId = null;
    }

    //----------

    @Override
    public Object put(Object o, Object o1) {
        return lruMap.put(o, o1);
    }

    @Override
    public Object get(Object key) {
        return lruMap.get(key);
    }

    @Override
    public Object remove(Object key) {
        return lruMap.remove(key);
    }

    @Override
    public int size() {
        return lruMap.size();
    }

    public boolean isGetInternalEntrySupported() {
        return false;
    }

    /**
     * Not implemented!
     *
     * @param key
     * @return <code>null</code>.
     */
    public Reference<InternalEntry> getInternalEntry(Object key) {
        return null;
    }
}
