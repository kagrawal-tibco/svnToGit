package com.tibco.cep.runtime.metrics.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.metrics.CountingStopWatch;
import com.tibco.cep.runtime.metrics.StopWatch;
import com.tibco.cep.runtime.metrics.StopWatchManager;
import com.tibco.cep.runtime.metrics.StopWatchOwner;
import com.tibco.cep.runtime.metrics.StopWatchOwnerLifecycleListener;
import com.tibco.cep.runtime.metrics.StopWatchOwnerOption;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 22, 2009 Time: 2:37:00 PM
*/

public class DefaultStopWatchManager implements InternalStopWatchManager {
    protected volatile ConcurrentHashMap<FQName, InternalStopWatchOwner> watchOwners;

    protected ScheduledExecutorService executorService;

    /**
     * Values are dummy.
     */
    protected ConcurrentHashMap<StopWatchOwnerLifecycleListener, Object> listeners;

    public String getId() {
        return DefaultStopWatchManager.class.getName();
    }

    public void init(Configuration configuration, Object... otherArgs) throws Exception {
        watchOwners = new ConcurrentHashMap<FQName, InternalStopWatchOwner>();

        StopWatchManagerThreadFactory threadFactory =
                new StopWatchManagerThreadFactory();
        executorService = Executors.newScheduledThreadPool(1, threadFactory);

        listeners = new ConcurrentHashMap<StopWatchOwnerLifecycleListener, Object>();
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
        executorService.shutdown();

        for (FQName name : watchOwners.keySet()) {
            InternalStopWatchOwner owner = watchOwners.remove(name);

            if (owner != null) {
                owner.discard();

                for (StopWatchOwnerLifecycleListener listener : listeners.keySet()) {
                    listener.onDiscard(owner);
                }
            }
        }

        watchOwners.clear();

        listeners.clear();
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return executorService;
    }

    //------------

    public Collection<? extends StopWatchOwner> fetchOwners() {
        return watchOwners.values();
    }

    public <W extends StopWatch> StopWatchOwner<W> fetchOwner(FQName metricDefName,
                                                              FQName metricName,
                                                              Class<W> type,
                                                              StopWatchOwnerOption... options) {
        InternalStopWatchOwner<W, ?> owner = watchOwners.get(metricName);
        if (owner != null) {
            Class watchOwnerClass = owner.getStopWatchType();

            if (type.isAssignableFrom(watchOwnerClass)) {
                return owner;
            }

            throw new RuntimeException("An owner already exists with the same name [" +
                    metricName +
                    "] but of a different type [" + watchOwnerClass.getName() + "]" +
                    " with options [" + Arrays.asList(options) + "].");
        }

        return createOwner(metricDefName, metricName, type, options);
    }

    protected synchronized <W extends StopWatch> StopWatchOwner<W> createOwner(FQName metricDefName,
                                                                               FQName metricName,
                                                                               Class<W> type,
                                                                               StopWatchOwnerOption... options) {
        boolean optMeasureLatency = false;
        boolean optAccumulateCount = false;

        for (StopWatchOwnerOption option : options) {
            switch (option) {
                case OPTION_ACCUMULATE_COUNT:
                    optAccumulateCount = true;
                    break;

                case OPTION_MEASURE_LATENCY:
                    optMeasureLatency = true;
                    break;

                default:
            }
        }

        //------------

        //Check again.
        InternalStopWatchOwner<W, ? extends Slab> owner = watchOwners.get(metricName);

        if (owner == null) {
            StopWatchCreator<W> creator = makeCreator(type, optMeasureLatency, optAccumulateCount);

            SlabWorker slabWorker = null;
            if (optMeasureLatency) {
                slabWorker = new TxnSlabWorker();
            }
            else {
                slabWorker = new DefaultSlabWorker();
            }

            owner = new SlabProcessor<W, Slab>(metricDefName, metricName);
            owner.init(creator, slabWorker, options);

            watchOwners.put(metricName, owner);

            for (StopWatchOwnerLifecycleListener listener : listeners.keySet()) {
                listener.onNew(owner);
            }
        }

        return owner;
    }

    protected <W extends StopWatch> StopWatchCreator<W> makeCreator(Class<W> type,
                                                                    boolean optMeasureLatency,
                                                                    boolean optAccumulateCount) {
        if (CountingStopWatch.class.isAssignableFrom(type)) {
            if (optAccumulateCount) {
                if (optMeasureLatency) {
                    return (StopWatchCreator<W>) new AccumulatingLatencyStopWatchCreator();
                }

                return (StopWatchCreator<W>) new AccumulatingStopWatchCreator();
            }
            else {
                if (optMeasureLatency) {
                    return (StopWatchCreator<W>) new CountingLatencyStopWatchCreator();
                }

                return (StopWatchCreator<W>) new CountingStopWatchCreator();
            }
        }
        else if (StopWatch.class.isAssignableFrom(type)) {
            return (StopWatchCreator<W>) new SimpleStopWatchCreator();
        }

        throw new IllegalArgumentException(
                "The type [" + type.getName() + "] is not supported.");
    }

    //-------------

    public void addListener(StopWatchOwnerLifecycleListener listener) {
        listeners.put(listener, listener);
    }

    public void removeListener(StopWatchOwnerLifecycleListener listener) {
        listeners.remove(listener);
    }

    //-------------

    protected static class StopWatchManagerThreadFactory implements ThreadFactory {
        protected ThreadGroup threadGroup;

        protected AtomicInteger counter;

        public StopWatchManagerThreadFactory() {
            this.threadGroup = new ThreadGroup(DefaultStopWatchManager.class.getSimpleName());
            this.counter = new AtomicInteger();
        }

        public Thread newThread(Runnable r) {
            String name = StopWatchManager.class.getSimpleName()
                    + ":" + ScheduledExecutorService.class.getSimpleName()
                    + ":" + counter.incrementAndGet();

            Thread t = new Thread(threadGroup, r, name);
            t.setDaemon(true);
            t.setPriority(Thread.MIN_PRIORITY);

            return t;
        }
    }
}
