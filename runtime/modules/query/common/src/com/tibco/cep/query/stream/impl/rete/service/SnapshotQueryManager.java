package com.tibco.cep.query.stream.impl.rete.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.core.ControllableResource;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.monitor.CustomDaemonThreadFactory;
import com.tibco.cep.query.stream.monitor.CustomThreadGroup;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.Logger.LogLevel;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.monitor.ThreadCentral;
import com.tibco.cep.query.stream.query.snapshot.Bridge;

/*
 * Author: Ashwin Jayaprakash Date: Mar 17, 2008 Time: 2:28:52 PM
 */

public class SnapshotQueryManager implements ControllableResource,
        SnapshotQueryCompletionListener {
    protected final HashMap<String, SnapshotQueryFeeder> queries;

    protected final ReentrantLock lock;

    protected final SharedObjectSourceRepository sosRepo;

    protected final ExecutorService executorService;

    protected final CustomDaemonThreadFactory daemonThreadFactory;

    protected final ResourceId resourceId;

    public SnapshotQueryManager(ResourceId parentId, SharedObjectSourceRepository repository) {
        this.resourceId = new ResourceId(parentId, getClass().getName());
        this.queries = new HashMap<String, SnapshotQueryFeeder>();
        this.lock = new ReentrantLock();

        this.sosRepo = repository;

        ThreadCentral threadCentral = Registry.getInstance().getComponent(ThreadCentral.class);
        CustomThreadGroup threadGroup = threadCentral.createOrGetThreadGroup(this.resourceId);
        this.daemonThreadFactory = new CustomDaemonThreadFactory(threadGroup,
                SnapshotQueryManager.class.getSimpleName());
        this.executorService = Executors.newCachedThreadPool(daemonThreadFactory);
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
        //Make a snapshot because the onCompletionListener(..) gets invoked async.
        ArrayList<SnapshotQueryFeeder> feeders =
                new ArrayList<SnapshotQueryFeeder>(queries.values());

        for (SnapshotQueryFeeder feeder : feeders) {
            ReteQuery q = feeder.getQuery();
            stopFeedingAndUnregister(q);
        }
        queries.clear();

        executorService.shutdown();

        resourceId.discard();
    }


    public ExecutorService getExecutorService() {
        return executorService;
    }

    /**
     * Thread-safe!
     *
     * @param query  Should already have started.
     * @param bridge
     * @return <code>true</code> if the registration succeeded.
     * @throws Exception
     */
    public boolean registerAndStartFeeding(ReteQuery query, Bridge bridge) throws Exception {
        lock.lock();
        try {
            String key = query.getName();

            if (queries.containsKey(key) == false) {
                SnapshotQueryFeeder feeder =
                        new SnapshotQueryFeeder(query, bridge, this, sosRepo, executorService);

                queries.put(key, feeder);

                feeder.start();

                return true;
            }
        }
        finally {
            lock.unlock();
        }

        return false;
    }

    /**
     * Expensive operation!
     *
     * @return Copy of the internal data structure.
     */
    public Collection<ReteQuery> getRegisteredQueries() {
        LinkedList<ReteQuery> linkedList = new LinkedList<ReteQuery>();

        lock.lock();
        try {
            for (SnapshotQueryFeeder feeder : queries.values()) {
                ReteQuery rq = feeder.getQuery();

                if (rq != null) {
                    linkedList.add(rq);
                }
            }
        }
        finally {
            lock.unlock();
        }

        return linkedList;
    }

    /**
     * <p> Thread-safe! </p> {@inheritDoc}
     */
    public void onComplete(SnapshotQueryFeeder feeder) {
        lock.lock();
        try {
            ReteQuery query = feeder.getQuery();
            queries.remove(query.getName());

            try {
                if (query.hasStopped() == false) {
                    query.stop();
                    query.discard();
                }
            }
            catch (Exception e) {
                Logger logger = Registry.getInstance().getComponent(Logger.class);
                logger.log(LogLevel.ERROR, e);
            }
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Thread-safe!
     *
     * @param query
     */
    public void stopFeedingAndUnregister(ReteQuery query) {
        SnapshotQueryFeeder queryFeeder = null;

        lock.lock();
        try {
            queryFeeder = queries.remove(query.getName());
        }
        finally {
            lock.unlock();
        }

        if (queryFeeder != null) {
            try {
                queryFeeder.stop();
            }
            catch (Exception e) {
                Logger logger = Registry.getInstance().getComponent(Logger.class);
                logger.log(LogLevel.ERROR, e);
            }
        }
    }
}
