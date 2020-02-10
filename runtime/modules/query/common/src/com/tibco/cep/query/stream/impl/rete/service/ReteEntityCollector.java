package com.tibco.cep.query.stream.impl.rete.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.core.ControllableResource;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandleType;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.util.CopyOnWriteArrayHolder;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 1:38:01 PM
 */

public abstract class ReteEntityCollector
        implements ReteEntityChangeListener, ControllableResource {
    protected final LinkedBlockingQueue<ReteEntityHandle> queuedInputHandles;

    /**
     * Entries are added but never removed from here.
     */
    protected final ConcurrentHashMap<Class, SourceAndListenerQueries> sourceAndListenerMap;

    protected final ConcurrentHashMap<String, ReteQuery> registeredQueries;

    protected final SharedObjectSourceRepository sosRepo;

    protected final ReentrantLock lock;

    protected final ResourceId resourceId;

    public ReteEntityCollector(ResourceId parentId, SharedObjectSourceRepository repo) {
        this.resourceId = new ResourceId(parentId, getClass().getName());
        this.sosRepo = repo;
        this.sourceAndListenerMap = new ConcurrentHashMap<Class, SourceAndListenerQueries>();
        this.registeredQueries = new ConcurrentHashMap<String, ReteQuery>();
        this.queuedInputHandles = new LinkedBlockingQueue<ReteEntityHandle>();
        this.lock = new ReentrantLock();
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public void stop() throws Exception {
        queuedInputHandles.clear();

        ArrayList<ReteQuery> registeredQueries =
                new ArrayList<ReteQuery>(this.registeredQueries.values());
        for (ReteQuery registeredQuery : registeredQueries) {
            unregisterAndStopQuery(registeredQuery);
        }
        this.registeredQueries.clear();

        resourceId.discard();
    }

    /**
     * Thread-safe!
     *
     * @param query
     * @return <code>true</code> if the registration succeeded.
     */
    public boolean registerQuery(ReteQuery query) {
        if (registeredQueries.putIfAbsent(query.getName(), query) == null) {
            lock.lock();
            try {
                Collection<Class> classes = query.getReteEntityClasses();
                for (Class clazz : classes) {
                    SourceAndListenerQueries sourceAndListenerQueries =
                            sourceAndListenerMap.get(clazz);

                    if (sourceAndListenerQueries == null) {
                        sourceAndListenerQueries = createOrGetExistingSLQ(clazz);
                    }

                    CopyOnWriteArrayHolder<ReteQuery> holder =
                            sourceAndListenerQueries.getListeners();
                    holder.add(query);
                }
            }
            finally {
                lock.unlock();
            }

            return true;
        }

        return false;
    }

    private SourceAndListenerQueries createOrGetExistingSLQ(Class clazz) {
        SharedObjectSource source = sosRepo.getSource(clazz.getName());
        SourceAndListenerQueries sourceAndListenerQueries = new SourceAndListenerQueries(source);

        SourceAndListenerQueries temp =
                sourceAndListenerMap.putIfAbsent(clazz, sourceAndListenerQueries);
        if (temp != null) {
            sourceAndListenerQueries = temp;
        }

        return sourceAndListenerQueries;
    }

    /**
     * Expensive operation!
     *
     * @return Copy of the internal data structure.
     */
    public Collection<ReteQuery> getRegisteredQueries() {
        LinkedList<ReteQuery> linkedList = new LinkedList<ReteQuery>(registeredQueries.values());

        return linkedList;
    }

    /**
     * Thread-safe!
     *
     * @param query
     */
    public void unregisterAndStopQuery(ReteQuery query) throws Exception {
        lock.lock();
        try {
            String key = query.getName();

            if (registeredQueries.containsKey(key)) {
                Collection<Class> entityClassNames = query.getReteEntityClasses();

                for (Class klass : entityClassNames) {
                    SourceAndListenerQueries sourceAndListenerQueries =
                            sourceAndListenerMap.get(klass);

                    //Remove just the query from the holder. SLQ itself is never removed. 
                    CopyOnWriteArrayHolder<ReteQuery> holder =
                            sourceAndListenerQueries.getListeners();
                    holder.remove(query);
                }

                registeredQueries.remove(key);
            }
        }
        finally {
            lock.unlock();
        }

        if (query.hasStopped() == false) {
            query.stop();
            query.discard();
        }
    }

    public boolean hasListeners(Class entityClass) {
        SourceAndListenerQueries sourceAndListenerQueries = sourceAndListenerMap.get(entityClass);

        if (sourceAndListenerQueries == null) {
            return false;
        }

        return sourceAndListenerQueries.hasListeners();
    }

    public final void onNew(Class entityClass, Long id, int version) {
        SourceAndListenerQueries sourceAndListenerQueries = sourceAndListenerMap.get(entityClass);
        if (sourceAndListenerQueries == null) {
            sourceAndListenerQueries = createOrGetExistingSLQ(entityClass);
        }

        //-----------

        // Collect Handle only if there are listeners.
        if (sourceAndListenerQueries.hasListeners()) {
            ReteEntityHandle handle =
                    new ReteEntityHandle(entityClass, id, ReteEntityHandleType.NEW);
            queuedInputHandles.add(handle);
        }
    }

    public final void onModify(Class entityClass, Long id, int version) {
        SourceAndListenerQueries sourceAndListenerQueries = sourceAndListenerMap.get(entityClass);
        if (sourceAndListenerQueries == null) {
            sourceAndListenerQueries = createOrGetExistingSLQ(entityClass);
        }

        SharedObjectSource source = sourceAndListenerQueries.getSource();
        source.softPurge(id);

        //-----------

        // Collect Handle only if there are listeners.
        if (sourceAndListenerQueries.hasListeners()) {
            ReteEntityHandle handle =
                    new ReteEntityHandle(entityClass, id, ReteEntityHandleType.MODIFICATION);
            queuedInputHandles.add(handle);
        }
    }

    public final void onDelete(Class entityClass, Long id, int version) {
        SourceAndListenerQueries sourceAndListenerQueries = sourceAndListenerMap.get(entityClass);
        if (sourceAndListenerQueries == null) {
            sourceAndListenerQueries = createOrGetExistingSLQ(entityClass);
        }

        SharedObjectSource source = sourceAndListenerQueries.getSource();
        source.softPurge(id);

        //-----------

        // Collect Handle only if there are listeners.
        if (sourceAndListenerQueries.hasListeners()) {
            ReteEntityHandle handle =
                    new ReteEntityHandle(entityClass, id, ReteEntityHandleType.DELETION);

            queuedInputHandles.add(handle);
        }
    }

    public void onEntity(ReteEntityHandle entityHandle) {
        Class entityClass = entityHandle.getReteClass();

        SourceAndListenerQueries sourceAndListenerQueries = sourceAndListenerMap.get(entityClass);
        if (sourceAndListenerQueries == null) {
            sourceAndListenerQueries = createOrGetExistingSLQ(entityClass);
        }

        //-----------

        // Collect Handle only if there are listeners.
        if (sourceAndListenerQueries.hasListeners()) {
            queuedInputHandles.add(entityHandle);
        }
    }

    //-----------

    protected static class SourceAndListenerQueries {
        protected final SharedObjectSource source;

        protected final CopyOnWriteArrayHolder<ReteQuery> listeners;

        public SourceAndListenerQueries(SharedObjectSource source) {
            this.source = source;
            this.listeners = new CopyOnWriteArrayHolder<ReteQuery>();
        }

        public SharedObjectSource getSource() {
            return source;
        }

        public CopyOnWriteArrayHolder<ReteQuery> getListeners() {
            return listeners;
        }

        public boolean hasListeners() {
            return listeners.getLength() > 0;
        }
    }
}