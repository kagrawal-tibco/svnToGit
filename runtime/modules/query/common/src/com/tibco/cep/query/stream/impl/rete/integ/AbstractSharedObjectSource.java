package com.tibco.cep.query.stream.impl.rete.integ;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.runtime.service.om.api.EntityDao;

/*
* Author: Ashwin Jayaprakash Date: Mar 24, 2008 Time: 5:18:32 PM
*/

/**
 * Works between Cluster cache and the Local cache.
 */
public abstract class AbstractSharedObjectSource implements SharedObjectSource {
    protected ClassLoader entityClassLoader;

    private boolean primarySupportsInternalEntryGets;

    protected Cache primaryCache;

    protected Cache deadPoolCache;

    protected EntityDao source;

    protected String cacheName;

    protected AbstractSharedObjectSource(EntityDao source, String cacheName,
                                         Cache primaryCache, Cache deadPoolCache,
                                         ClassLoader entityClassLoader) {
        this.source = source;
        this.primaryCache = primaryCache;
        this.deadPoolCache = deadPoolCache;
        this.entityClassLoader = entityClassLoader;
        this.cacheName = cacheName;

        this.primarySupportsInternalEntryGets = primaryCache.isGetInternalEntrySupported();
    }

    private Entity fetchFromSource(Object key) {
        Entity entity = null;

        /*
         * Set context-classloader: needed for TestNG tests and other hosted
         * environments.
         */
        final Thread currThread = Thread.currentThread();
        final ClassLoader oldCL = currThread.getContextClassLoader();

        try {
            currThread.setContextClassLoader(entityClassLoader);

            Object o = source.get(key);

            if (o != null) {
                Long id = (Long) key;

                entity = handleDownloadedEntity(o, id);
            }
        }
        catch (Throwable e) {
            String msg = "Error occurred while retrieving from the Cache: "
                    + cacheName + ", Key: " + key;

            throw new RuntimeException(msg, e);
        }
        finally {
            currThread.setContextClassLoader(oldCL);
        }

        return entity;
    }

    public abstract Entity handleDownloadedEntity(Object o, Long id);

    public final Entity fetch(Object id, boolean refreshFromSource) {
        Object obj = refreshFromSource ? null : primaryCache.get(id);

        if (obj == null) {
            obj = fetchFromSource(id);

            if (obj != null) {
                primaryCache.put(id, obj);
            }
            // See if it is there in the dead-pool.
            else {
                obj = deadPoolCache.get(id);
            }
        }

        //----------

        if (obj != null) {
            return (Entity) obj;
        }

        String msg = "The entity with Id: " + id + " does not exist in the Cache: " + cacheName;
        throw new RuntimeException(msg);
    }

    public final Object fetchValueOrInternalEntryRef(Object id, boolean refreshFromSource) {
        if (primarySupportsInternalEntryGets == false) {
            return fetch(id, refreshFromSource);
        }

        //----------

        Object obj = refreshFromSource ? null : primaryCache.getInternalEntry(id);

        if (obj == null) {
            obj = fetchFromSource(id);

            if (obj != null) {
                primaryCache.put(id, obj);

                Object internalEntry = primaryCache.getInternalEntry(id);
                /*
                We just put it in there. So, it we get a null here, then either the cache size is 0,
                or it's being evicted very quickly. In either case, just use the actual object.
                */
                if (internalEntry == null) {
                    return obj;
                }

                obj = internalEntry;
            }
            // See if it is there in the dead-pool.
            else {
                obj = deadPoolCache.get(id);
            }
        }

        //----------

        if (obj != null) {
            return obj;
        }

        String msg = "The entity with Id: " + id + " does not exist in the Cache: " + cacheName;
        throw new RuntimeException(msg);
    }

    public final void purge(Object key) {
        primaryCache.remove(key);
    }

    public final void softPurge(Object key) {
        Object value = primaryCache.remove(key);

        // No point placing null values.
        if (value != null) {
            deadPoolCache.put(key, value);
        }
    }

    public final EntityDao getInternalSource() {
        return source;
    }

    public final Cache getPrimaryCache() {
        return primaryCache;
    }

    public final Cache getDeadPoolCache() {
        return deadPoolCache;
    }

    public void discard() {
        source = null;
        primaryCache = null;
        deadPoolCache = null;
        entityClassLoader = null;
        cacheName = null;
    }
}
