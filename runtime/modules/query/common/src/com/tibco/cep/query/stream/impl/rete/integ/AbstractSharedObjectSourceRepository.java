package com.tibco.cep.query.stream.impl.rete.integ;

import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.monitor.CustomRuntimeException;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
* Author: Ashwin Jayaprakash Date: Mar 24, 2008 Time: 5:53:40 PM
*/

/**
 * Holds references to all the source-names and their Sources.
 */
public abstract class AbstractSharedObjectSourceRepository implements SharedObjectSourceRepository {
    /**
     * {@value}
     */
    public static final String NAME_DUMMY_SOS = FakeSharedObjectSource.class.getName();

    protected ConcurrentHashMap<String, SharedObjectSource> nameAndSources;

    protected ClassLoader entityClassLoader;

    protected Cache primaryCache;

    protected Cache deadPoolCache;

    protected ResourceId resourceId;

    protected AbstractSharedObjectSourceRepository(ResourceId parentId,
                                                   ClassLoader entityClassLoader,
                                                   Cache primaryCache, Cache deadPoolCache) {
        this.resourceId = new ResourceId(parentId, getClass().getName());
        this.nameAndSources = new ConcurrentHashMap<String, SharedObjectSource>();
        this.entityClassLoader = entityClassLoader;
        this.primaryCache = primaryCache;
        this.deadPoolCache = deadPoolCache;

        this.nameAndSources
                .put(NAME_DUMMY_SOS, new FakeSharedObjectSource(primaryCache, deadPoolCache));
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public Cache getDeadPoolCache() {
        return deadPoolCache;
    }

    public Cache getPrimaryCache() {
        return primaryCache;
    }

    public void start() throws Exception {
        primaryCache.start();
        deadPoolCache.start();
    }

    public void stop() throws Exception {
        primaryCache.stop();
        deadPoolCache.stop();

        for (SharedObjectSource sharedObjectSource : nameAndSources.values()) {
            sharedObjectSource.discard();
        }
        nameAndSources.clear();
        nameAndSources = null;

        entityClassLoader = null;
        primaryCache = null;
        deadPoolCache = null;

        resourceId.discard();
        resourceId = null;
    }

    //---------

    /**
     * @return Calls {@link #getSource(String)} using {@link #NAME_DUMMY_SOS}.
     */
    public SharedObjectSource getDefaultSource() {
        return getSource(NAME_DUMMY_SOS);
    }

    public SharedObjectSource getSource(String className) {
        SharedObjectSource sos = nameAndSources.get(className);

        if (sos != null) {
            return sos;
        }

        // ----------

        Class clazz = null;

        try {
            clazz = Class.forName(className, true, entityClassLoader);
        }
        catch (ClassNotFoundException e) {
            throw new CustomRuntimeException(resourceId, e);
        }

        sos = createSharedObjectSource(clazz, entityClassLoader, primaryCache, deadPoolCache);

        /*
        * Atomic operation. Might or might not succeed, if another Thread is
        * doing the same thing.
        */
        SharedObjectSource temp = nameAndSources.putIfAbsent(className, sos);

        if (temp != null) {
            // This thread was beaten by another thread. So, drop our instance.
            sos.discard();

            sos = temp;
        }

        return sos;
    }

    /**
     * @param clazz             The class whose instances are going to managed/retrieved.
     * @param entityClassLoader
     * @param primaryCache
     * @param deadPoolCache
     * @return
     */
    protected abstract SharedObjectSource createSharedObjectSource(Class clazz,
                                                                   ClassLoader entityClassLoader,
                                                                   Cache primaryCache,
                                                                   Cache deadPoolCache);

    public final void purgeObject(Object longId, String className) {
        SharedObjectSource sos = getSource(className);

        sos.purge(longId);
    }

    public final void softPurgeObject(Object longId, String className) {
        SharedObjectSource sos = getSource(className);

        sos.softPurge(longId);
    }
}
