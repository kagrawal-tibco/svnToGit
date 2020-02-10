package com.tibco.cep.query.stream.query.continuous;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.ContextRepository;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.misc.Clock;
import com.tibco.cep.query.stream.monitor.QueryConfig;
import com.tibco.cep.query.stream.monitor.QueryWatcher;
import com.tibco.cep.query.stream.monitor.QueryWatcher.Run;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.util.LazyContainerTreeMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 12:50:47 PM
 */

public abstract class AbstractContinuousQuery<I> implements ContinuousQuery<I> {
    private DefaultQueryContext qc;

    private LocalContext lc;

    private LazyContainerTreeMap<Long, Stream, LinkedHashSet<Stream>> timeTable;

    private QueryConfig qCon;

    private QueryWatcher qw;

    protected final ReentrantLock lock;

    protected final LocalContext nullLocalContext;

    protected Context context;

    /**
     * Can be <code>null</code>.
     */
    protected Run currentRun;

    private int tracingEnabledCheckCount;

    private boolean tracingIsOn;

    protected final Condition notifyCondition;

    protected final Condition pauseCondition;

    protected final AtomicBoolean paused;

    protected final AtomicBoolean stopped;

    protected final String regionName;

    protected final ResourceId resourceId;

    protected final Source[] sources;

    protected final Sink sink;

    public AbstractContinuousQuery(String regionName, ResourceId resourceId, Source[] sources, Sink sink) {
        this.regionName = regionName;
        this.resourceId = resourceId;
        this.sources = sources;
        this.sink = sink;

        this.lock = new ReentrantLock();
        this.notifyCondition = this.lock.newCondition();
        this.pauseCondition = this.lock.newCondition();
        this.paused = new AtomicBoolean(false);
        this.stopped = new AtomicBoolean(false);
        this.nullLocalContext = new LocalContext(true);
    }

    @Override
    public ResourceId getResourceId() {
        return resourceId;
    }

    @Override
    public String getRegionName() {
        return regionName;
    }

    @Override
    public String getName() {
        return resourceId.getId();
    }

    @Override
    public Sink getSink() {
        return sink;
    }

    @Override
    public Source[] getSources() {
        return sources;
    }

    /**
     * Must be inited first - {@link #init(Map)}.
     *
     * @return Context
     */
    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public boolean hasStopped() {
        return stopped.get();
    }

    // --------

    /**
     * @param externalData Can be <code>null</code>.
     * @throws Exception
     */
    public void init(Map<String, Object> externalData) throws Exception {
        ContextRepository repository = Registry.getInstance().getComponent(ContextRepository.class);

        DefaultGlobalContext gc = repository.getGlobalContext();

        this.qc = new DefaultQueryContext(regionName, getName(), externalData);
        this.context = new Context(gc, this.qc);

        this.lc = this.context.getLocalContext();
        this.timeTable = this.qc.getTimeScheduledStreams();
        this.qw = this.qc.getQueryWatcher();
        this.qCon = this.qc.getQueryConfig();

        repository.registerContext(qc);
    }

    @Override
    public void start() throws Exception {

    }
    
    /**
     * Stops in 2 phases. First signals the waiters. Then re-acquires the lock and stops all the
     * Sources.
     *
     * @throws Exception
     */
    public void stop() throws Exception {
        acquireQueryLock();
        try {
            pauseCondition.signal();

            notifyCondition.signal();
        }
        finally {
            relinquishQueryLock();
        }

        //--------

        acquireQueryLock();
        try {
            for (Source source : sources) {
                source.stop();
            }
        }
        finally {
            relinquishQueryLock();
        }

        stopped.set(true);
    }

    @Override
    public void discard() {
        ContextRepository repository = Registry.getInstance().getComponent(ContextRepository.class);
        repository.unregisterContext(regionName, getName());

        context = null;
    }

    // ----------

    public final boolean isPaused() {
        return paused.get();
    }

    @Override
    public void pause() {
        paused.set(true);
    }

    @Override
    public void resume() {
        paused.set(false);

        acquireQueryLock();
        try {
            pauseCondition.signal();
        }
        finally {
            relinquishQueryLock();
        }
    }

    /**
     * Trys to wake the Query up.
     */
    @Override
    public void ping() {
        if (lock.tryLock()) {
            try {
                notifyCondition.signal();
            }
            finally {
                lock.unlock();
            }
        }

        if (lock.tryLock()) {
            try {
                pauseCondition.signal();
            }
            finally {
                lock.unlock();
            }
        }
    }

    public final void acquireQueryLock() {
        if (lock.isHeldByCurrentThread() == false) {
            lock.lock();
        }
    }

    /**
     * @return <code>true</code> if lock acquisition succeeded.
     */
    public final boolean tryAcquireQueryLock() {
        return lock.isHeldByCurrentThread() || lock.tryLock();
    }

    /**
     * Lock should already be held using one of these - {@link #acquireQueryLock()} or {@link
     * #tryAcquireQueryLock()}.
     */
    public final void notifyQueryLockWaiter() {
        notifyCondition.signal();
    }

//    public final void awaitQueryLockNotification() throws InterruptedException {
//        notifyCondition.await();
//    }
//
//    /**
//     * @param time
//     * @param unit
//     * @return <code>true</code> if the wait time did not elapse completely.
//     * @throws InterruptedException
//     */
//    public final boolean awaitQueryLockNotification(long time, TimeUnit unit)
//            throws InterruptedException {
//        return notifyCondition.await(time, unit);
//    }
//
//    public final void awaitQueryResumeNotification() throws InterruptedException {
//        pauseCondition.await();
//    }

    public final void relinquishQueryLock() {
        lock.unlock();
    }

    // ----------

    /**
     * <p/> Lock should be acquired first! </p> <p/> Updates the {@link
     * com.tibco.cep.query.stream.context.DefaultQueryContext#getCurrentCycleSchedule()} to reflect
     * the latest Streams, including any elapsed time-scheduled Streams. </p>
     *
     * @param cycleType
     * @return <code>true</code> if the cycle-type is {@link CycleType#SCHEDULED} and there is
     *         pending work. Or, cycle-type is {@link CycleType#TRIGGERED}. So, if the value is
     *         <code>true</code>, then it means that the cycle was indeed started. Otherwise, it did
     *         not start.
     */
    protected final boolean attemptCycleStart(CycleType cycleType) {
        qc.transferNextToCurrentCycleSchedule();

        // --------

        final long cycleTime = Clock.getCurrentTimeMillis();
        final LinkedHashSet<Stream> currSch = qc.getCurrentCycleSchedule();
        final LazyContainerTreeMap<Long, Stream, LinkedHashSet<Stream>> tt = timeTable;

        while (tt.isEmpty() == false) {
            Long timestamp = tt.firstKey();

            if (timestamp <= cycleTime) {
                Object streamOrStreams = tt.remove(timestamp);

                // Force them into the current schedule.
                if (streamOrStreams instanceof Collection) {
                    currSch.addAll((Collection<Stream>) streamOrStreams);
                }
                else {
                    currSch.add((Stream) streamOrStreams);
                }
            }
            else {
                break;
            }
        }

        // --------

        if (cycleType == CycleType.TRIGGERED
                || (cycleType == CycleType.SCHEDULED && currSch.isEmpty() == false)) {
            qc.setCurrentCycleTimestamp(cycleTime);

            startTrace();

            return true;
        }

        return false;
    }

    private void startTrace() {
        if (tracingEnabledCheckCount == 0) {
            tracingIsOn = qCon.isTracingEnabled();
        }

        currentRun = null;
        if (tracingIsOn) {
            currentRun = qw.initNewRun();
        }

        //Check only once in few cycles. Here: once in 32 cycles.
        tracingEnabledCheckCount = (tracingEnabledCheckCount + 1) & (32 - 1);
    }

    /**
     * Lock should be acquired first! Should be invoked only if 
     * returned <code>true</code>.
     *
     * @param cycleType
     * @param error     <code>null</code> if there was no error.
     */
    protected final void cycleEnd(CycleType cycleType, Throwable error) {
        if (currentRun != null) {
            currentRun.recordEnd(error);
        }

        currentRun = null;
    }

    // ----------

    /**
     * Lock should be acquired first!
     *
     * @return <code>true</code> if there was any work to do.
     * @throws Throwable
     */
    protected final boolean performPendingWork() throws Throwable {
        Stream candidateStream = null;

        boolean started = attemptCycleStart(CycleType.SCHEDULED);

        if (started) {
            context.setLocalContext(nullLocalContext);

            Throwable throwable = null;

            try {
                LinkedHashSet<Stream> schedule = qc.getCurrentCycleSchedule();
                if (schedule.isEmpty() == false) {
                    //todo Avoid creating this iterator everytime.

                    // Just one item at a time.                    
                    candidateStream = schedule.iterator().next();

                    candidateStream.process(context);
                }
            }
            catch (Throwable t) {
                throwable = t;
                throw t;
            }
            finally {
                cycleEnd(CycleType.SCHEDULED, throwable);

                // Restore context.
                context.setLocalContext(lc);
            }
        }

        return candidateStream != null;
    }

    /**
     * Lock should be acquired first!
     *
     * @return long <code>0</code> or greater.
     */
    protected final long calculateWaitTimeMillis() {
        long waitTime = Long.MAX_VALUE;

        if (context != null) {
            if (timeTable.isEmpty() == false) {
                waitTime = timeTable.firstKey() - Clock.getCurrentTimeMillis();
            }

            if (waitTime > 0
                    && (qc.getCurrentCycleSchedule().isEmpty() == false || qc
                    .getNextCycleSchedule().isEmpty() == false)) {
                // Immediately.
                waitTime = 0;
            }
        }

        return waitTime < 0 ? 0 : waitTime;
    }
}
