package com.tibco.cep.query.stream.framework;

import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.monitor.ResourceId;

import java.lang.ref.Reference;

/*
 * Author: Ashwin Jayaprakash Date: Dec 19, 2007 Time: 5:13:33 PM
 */

public class TrackedCache implements Cache {
    protected final ResourceId resourceId;

    protected static TestSession testSession;

    public TrackedCache() {
        this.resourceId = new ResourceId(TrackedCache.class.getName());
    }

    public static TestSession getTestSession() {
        return testSession;
    }

    public static void setTestSession(TestSession testSession) {
        TrackedCache.testSession = testSession;
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public int size() {
        return testSession.getCacheData().size();
    }

    public Object get(Object key) {
        return testSession.getCacheData().get(key);
    }

    public boolean isGetInternalEntrySupported() {
        return false;
    }

    /**
     * @param key
     * @return <code>null</code>
     */
    public Reference<InternalEntry> getInternalEntry(Object key) {
        return null;
    }

    public Object put(Object key, Object value) {
        return testSession.getCacheData().put(key, value);
    }

    public Object remove(Object key) {
        return testSession.getCacheData().remove(key);
    }

    public Object replace(Object key, Object newValue) {
        return testSession.getCacheData().put(key, newValue);
    }
}
