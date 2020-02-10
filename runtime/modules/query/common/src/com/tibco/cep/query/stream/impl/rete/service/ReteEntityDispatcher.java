package com.tibco.cep.query.stream.impl.rete.service;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.cache.SharedPointer;
import com.tibco.cep.query.stream.cache.SharedPointerImpl;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.rete.ReteEntityBatchHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandleType;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.query.Sender;
import com.tibco.cep.query.stream.misc.Clock;
import com.tibco.cep.query.stream.monitor.CustomDaemonThread;
import com.tibco.cep.query.stream.monitor.CustomThreadGroup;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.Logger.LogLevel;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.monitor.ThreadCentral;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.CopyOnWriteArrayHolder;
import com.tibco.cep.query.stream.util.ReusableIterator;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 4:27:18 PM
 */

public class ReteEntityDispatcher extends ReteEntityCollector {
    public static final int MAX_RECENT_RUNS = 50;

    protected static final int MAX_ITEMS_COALESCE = 1024;

    protected HashMap<Class, SharedObjectSource> cachedSources;

    protected DispatcherThread dispatcher;

    protected ArrayBlockingQueue<Run> recentRuns;

    public ReteEntityDispatcher(ResourceId parentId, SharedObjectSourceRepository repo) {
        super(parentId, repo);

        this.recentRuns = new ArrayBlockingQueue<Run>(MAX_RECENT_RUNS);
    }

    public void start() throws Exception {
        cachedSources = new HashMap<Class, SharedObjectSource>(16, 0.50f);

        ThreadCentral threadCentral = Registry.getInstance().getComponent(ThreadCentral.class);
        ResourceId threadGroupId =
                (resourceId.getParent() == null) ? resourceId : resourceId.getParent();
        CustomThreadGroup threadGroup = threadCentral.createOrGetThreadGroup(threadGroupId);
        dispatcher = new DispatcherThread(threadGroup, getClass().getSimpleName());

        dispatcher.start();
    }

    @Override
    public void stop() throws Exception {
        dispatcher.signalStop();
        dispatcher.join(60 * 1000);
        dispatcher = null;

        super.stop();

        cachedSources.clear();
        cachedSources = null;

        recentRuns.clear();
        recentRuns = null;
    }

    /**
     * A maximum of {@link #MAX_RECENT_RUNS} will be stored. If it crosses the limit, the oldest one
     * will be replaced.
     *
     * @return The oldest run, if at least 1 is present. <code>null</code> otherwise. This operation
     *         does not remove the run from the internal queue.
     */
    public Run peekOldestRun() {
        return recentRuns.peek();
    }

    /**
     * A maximum of {@link #MAX_RECENT_RUNS} will be stored. If it crosses the limit, the oldest one
     * will be replaced.
     *
     * @return The oldest run, if at least 1 is present. <code>null</code> otherwise. This operation
     *         removes the run from the internal queue.
     */
    public Run pollOldestRun() {
        return recentRuns.poll();
    }

    // ----------

    private void dispatch(ReteEntityHandle handle, Run run) throws Exception {
        Class klass = handle.getReteClass();
        Long reteId = handle.getReteId();
        ReteEntityHandleType type = handle.getType();

        SharedObjectSource source = cachedSources.get(klass);
        if (source == null) {
            source = sosRepo.getSource(klass.getName());
            cachedSources.put(klass, source);
        }

        switch (type) {
            case MODIFICATION:
                run.modifiedEntities++;

                break;

            case DELETION:
                run.deletedEntities++;

                break;

            case NEW:

                run.newEntities++;

                break;

            default:
        }

        SourceAndListenerQueries sourceAndListenerQueries = sourceAndListenerMap.get(klass);
        CopyOnWriteArrayHolder<ReteQuery> listeners = sourceAndListenerQueries.getListeners();
        Object[] reteQueries = null;

        // Could've been unregistered by now.
        if (listeners != null && (reteQueries = listeners.getInternalArray()) != null) {
            //Direct to query will create its own shared-pointer. So, check for that.
            if (type == ReteEntityHandleType.NEW && handle.getSharedPointer() == null) {
                SharedPointer sharedPointer = new SharedPointerImpl(reteId, source);

                //Share the same handle among all queries.
                handle.setSharedPointer(sharedPointer);
            }

            for (Object query : reteQueries) {
                ((ReteQuery) query).enqueueInput(handle, Sender.CURRENT);
            }
        }
    }

    private void dispatchBatchAddsOrDeletes(ReusableIterator<ReteEntityHandle> handles,
                                            int numHandles, Run run) throws Exception {
        handles.reset();

        ReteEntityHandle tempHandle = handles.next();
        final Class klass = tempHandle.getReteClass();
        final ReteEntityHandleType type = tempHandle.getType();
        tempHandle = null;

        //-----------

        SharedObjectSource source = cachedSources.get(klass);
        if (source == null) {
            source = sosRepo.getSource(klass.getName());
            cachedSources.put(klass, source);
        }

        //-----------

        SourceAndListenerQueries sourceAndListenerQueries = sourceAndListenerMap.get(klass);
        CopyOnWriteArrayHolder<ReteQuery> listeners = sourceAndListenerQueries.getListeners();
        Object[] reteQueries = null;

        // Could've been unregistered by now.
        if (listeners == null || (reteQueries = listeners.getInternalArray()) == null) {
            return;
        }

        //-----------

        ReteEntityBatchHandle batchHandle = null;

        if (type == ReteEntityHandleType.NEW) {
            //Share the same handle among all queries.
            SharedPointer[] pointers = new SharedPointer[numHandles];

            handles.reset();
            for (int i = 0; handles.hasNext(); i++) {
                ReteEntityHandle h = handles.next();

                Long reteId = h.getReteId();
                SharedPointer sharedPointer = h.getSharedPointer();

                pointers[i] = (sharedPointer == null) ? new SharedPointerImpl(reteId, source) :
                        sharedPointer;
            }

            batchHandle = ReteEntityBatchHandle.createAsTypeNew(klass, pointers);

            run.newEntities = run.newEntities + numHandles;
        }
        else {
            Long[] deletedReteIds = new Long[numHandles];

            handles.reset();
            for (int i = 0; handles.hasNext(); i++) {
                ReteEntityHandle h = handles.next();

                deletedReteIds[i] = h.getReteId();
            }

            batchHandle = ReteEntityBatchHandle.createAsTypeDeletion(klass, deletedReteIds);

            run.deletedEntities = run.deletedEntities + numHandles;
        }

        for (Object query : reteQueries) {
            ((ReteQuery) query).enqueueInput(batchHandle, Sender.CURRENT);
        }
    }

    // ----------

    protected class DispatcherThread extends CustomDaemonThread {
        private AppendOnlyQueue<ReteEntityHandle> drainToQ;

        private ReusableIterator<ReteEntityHandle> drainToQIter;

        public DispatcherThread(CustomThreadGroup threadGroup, String name) {
            super(threadGroup, new ResourceId(name));

            setPriority(Thread.MAX_PRIORITY);

            //----------

            this.drainToQ = new AppendOnlyQueue<ReteEntityHandle>(256);
            this.drainToQIter = this.drainToQ.iterator();
        }

        @Override
        public void run() {
            final LinkedBlockingQueue<ReteEntityHandle> q =
                    ReteEntityDispatcher.this.queuedInputHandles;
            final int maxQuickLoop = 1024;
            final long inputWaitTimeMillis = 5000;

            final ArrayBlockingQueue<Run> cachedRecentRuns = recentRuns;
            final AppendOnlyQueue<ReteEntityHandle> coalescedHandles =
                    new AppendOnlyQueue<ReteEntityHandle>(256);
            final ReusableIterator<ReteEntityHandle> coalescedHandlesIter =
                    coalescedHandles.iterator();

            final AppendOnlyQueue<ReteEntityHandle> cachedDrainToQ = drainToQ;
            final ReusableIterator<ReteEntityHandle> cachedDrainToQIter = drainToQIter;

            long lastRunStartTimeMillis = Clock.getCurrentTimeMillis();

            while (stopFlag.get() == false) {
                Run run = new Run();
                run.setStartTimeMillis(Clock.getCurrentTimeMillis());

                for (int c = 1; c <= maxQuickLoop; c++) {
                    try {
                        cachedDrainToQ.clear();

                        if (q.drainTo(cachedDrainToQ, MAX_ITEMS_COALESCE) == 0) {
                            //Direct drain received nothing. Now, do a blocking wait.
                            ReteEntityHandle handle =
                                    q.poll(inputWaitTimeMillis, TimeUnit.MILLISECONDS);

                            //Even poll(..) returned nothing.
                            if (handle == null) {
                                continue;
                            }

                            cachedDrainToQ.add(handle);
                        }

                        //------------

                        for (cachedDrainToQIter.reset(); cachedDrainToQIter.hasNext();) {
                            ReteEntityHandle handle = cachedDrainToQIter.next();

                            ReteEntityHandleType type = handle.getType();

                            //Cannot coalesce Mods.
                            if (type == ReteEntityHandleType.MODIFICATION) {
                                ReteEntityDispatcher.this.dispatch(handle, run);

                                continue;
                            }

                            //Try to coalesce events of the same Type & Class.
                            coalesceAndDispatchFromDrainQ(handle, coalescedHandles,
                                    coalescedHandlesIter, run);
                        }
                    }
                    catch (InterruptedException e) {
                        break;
                    }
                    catch (Throwable t) {
                        Logger logger = Registry.getInstance().getComponent(Logger.class);
                        logger.log(LogLevel.ERROR, t);

                        run.setError(t);

                        break;
                    }

                    cachedDrainToQ.clear();

                    if ((c & 15) == 0) {
                        long endTime = Clock.getCurrentTimeMillis();

                        //Only 16 iterations and already 5 seconds have passed. Low traffic.
                        if ((endTime - run.getStartTimeMillis()) >= inputWaitTimeMillis) {
                            run.setEndTimeMillis(endTime);

                            //Update run-stats now.
                            break;
                        }
                    }
                }

                //------------

                if (run.getEndTimeMillis() == 0) {
                    run.setEndTimeMillis(Clock.getCurrentTimeMillis());
                }

                //More stats.
                long e = run.getEndTimeMillis();
                long s = run.getStartTimeMillis();
                int total =
                        run.getNewEntities() + run.getModifiedEntities() + run.getDeletedEntities();
                long diff = e - s;
                double tps = (total * 1000) / (double) diff;
                long timeBetweenRunsMillis = s - lastRunStartTimeMillis;
                lastRunStartTimeMillis = s;

                run.setTps(tps);
                run.setTimeBetweenRunsSeconds(timeBetweenRunsMillis / 1000);

                if (cachedRecentRuns.offer(run) == false) {
                    //Remove the oldest element and make room.
                    cachedRecentRuns.poll();

                    //Try again.
                    cachedRecentRuns.offer(run);
                }
            }

            coalescedHandles.discard();

            runCompleted();
        }

        private void coalesceAndDispatchFromDrainQ(ReteEntityHandle firstInBatch,
                                                   AppendOnlyQueue<ReteEntityHandle> coalescedHandles,
                                                   ReusableIterator<ReteEntityHandle> coalescedHandlesIter,
                                                   Run run) throws Exception {
            ReteEntityHandleType type = firstInBatch.getType();
            final Class klass = firstInBatch.getReteClass();
            //Don't reset this.
            final ReusableIterator<ReteEntityHandle> cachedDrainToQIter = drainToQIter;

            int coalesceCount = 0;

            ReteEntityHandle batchBreaker = null;
            for (coalescedHandles.clear();
                 coalesceCount < MAX_ITEMS_COALESCE && cachedDrainToQIter.hasNext();) {
                final ReteEntityHandle nextInBatch = cachedDrainToQIter.next();

                /*
                Sequence of same type+class of events break here. Purge everything we've accumulated
                 so far.
                */
                if (nextInBatch.getReteClass() != klass || nextInBatch.getType() != type) {
                    //Store this so that it can be dispatched separately.
                    batchBreaker = nextInBatch;

                    break;
                }

                if (coalesceCount == 0) {
                    coalescedHandles.add(firstInBatch);
                    coalesceCount++;
                }

                coalescedHandles.add(nextInBatch);
                coalesceCount++;
            }

            //-----------

            if (coalesceCount == 0) {
                ReteEntityDispatcher.this.dispatch(firstInBatch, run);
            }
            else {
                ReteEntityDispatcher.this.dispatchBatchAddsOrDeletes(coalescedHandlesIter,
                        /* firstInBatch + remaining. */ coalesceCount, run);

                coalescedHandles.clear();
            }

            if (batchBreaker != null) {
                ReteEntityDispatcher.this.dispatch(batchBreaker, run);
            }
        }
    }

    //-----------

    public static class Run {
        protected long startTimeMillis;

        protected long endTimeMillis;

        protected int newEntities;

        protected int modifiedEntities;

        protected int deletedEntities;

        protected double tps;

        protected long timeBetweenRunsMillis;

        protected Throwable error;

        public long getStartTimeMillis() {
            return startTimeMillis;
        }

        public long getEndTimeMillis() {
            return endTimeMillis;
        }

        public int getNewEntities() {
            return newEntities;
        }

        public int getModifiedEntities() {
            return modifiedEntities;
        }

        public int getDeletedEntities() {
            return deletedEntities;
        }

        public Throwable getError() {
            return error;
        }

        public double getTps() {
            return tps;
        }

        public long getTimeBetweenRunsMillis() {
            return timeBetweenRunsMillis;
        }

        protected void setStartTimeMillis(long startTimeMillis) {
            this.startTimeMillis = startTimeMillis;
        }

        protected void setEndTimeMillis(long endTimeMillis) {
            this.endTimeMillis = endTimeMillis;
        }

        protected void setNewEntities(int newEntities) {
            this.newEntities = newEntities;
        }

        protected void setModifiedEntities(int modifiedEntities) {
            this.modifiedEntities = modifiedEntities;
        }

        protected void setDeletedEntities(int deletedEntities) {
            this.deletedEntities = deletedEntities;
        }

        protected void setError(Throwable error) {
            this.error = error;
        }

        public void setTps(double tps) {
            this.tps = tps;
        }

        public void setTimeBetweenRunsSeconds(long timeBetweenRunsMillis) {
            this.timeBetweenRunsMillis = timeBetweenRunsMillis;
        }
    }
}
