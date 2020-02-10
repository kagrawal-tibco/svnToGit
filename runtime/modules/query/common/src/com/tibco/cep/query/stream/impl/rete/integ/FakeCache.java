package com.tibco.cep.query.stream.impl.rete.integ;

import java.lang.ref.Reference;

import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
* Author: Ashwin Jayaprakash / Date: Jan 8, 2010 / Time: 1:26:53 PM
*/
public class FakeCache implements Cache {
    protected ResourceId resourceId;

    public FakeCache(ResourceId resourceId) {
        this.resourceId = resourceId;
    }

    public Object put(Object key, Object value) {
        return null;
    }

    public Object get(Object key) {
        return null;
    }

    public Object remove(Object key) {
        return null;
    }

    public boolean isGetInternalEntrySupported() {
        return false;
    }

    public Reference<InternalEntry> getInternalEntry(Object key) {
        return null;
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
    }

    @Override
    public int size() {
        return 0;
    }

    public ResourceId getResourceId() {
        return resourceId;
    }
}
