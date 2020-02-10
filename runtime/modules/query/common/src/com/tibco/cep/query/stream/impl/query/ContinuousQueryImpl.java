package com.tibco.cep.query.stream.impl.query;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.monitor.CustomDaemonThread;
import com.tibco.cep.query.stream.monitor.CustomThreadGroup;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.Logger.LogLevel;
import com.tibco.cep.query.stream.monitor.QueryWatcher;
import com.tibco.cep.query.stream.monitor.QueryWatcher.Run;
import com.tibco.cep.query.stream.monitor.QueryWatcher.Step;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.monitor.ThreadCentral;
import com.tibco.cep.query.stream.query.continuous.AbstractContinuousQuery;

/*
 * Author: Ashwin Jayaprakash Date: Nov 14, 2007 Time: 10:48:23 AM
 */

public abstract class ContinuousQueryImpl<I> extends AbstractContinuousQuery<I> {
    protected final ConcurrentLinkedQueue<I> queuedInput;

    private final Worker syncWorker;

    private final AsyncProcessor asyncProcessor;

    /**
     * @param regionName
     * @param resourceId
     * @param sources
     * @param sink
     * @param listener   If <code>null</code>, then runs in synchronous mode. Otherwise, async.
     */
    public ContinuousQueryImpl(String regionName, ResourceId resourceId, Source[] sources,
                               Sink sink, AsyncProcessListener listener) {
        super(regionName, resourceId, sources, sink);

        this.queuedInput = new ConcurrentLinkedQueue<I>();

        if (listener == null) {
            this.syncWorker = new Worker(true);
            this.asyncProcessor = null;
        }
        else {
            this.syncWorker = null;

            ThreadCentral threadCentral = Registry.getInstance().getComponent(ThreadCentral.class);
            ResourceId threadGroupId = resourceId.getParent();
            if (threadGroupId == null) {
                threadGroupId = resourceId;
            }
            CustomThreadGroup threadGroup = threadCentral.createOrGetThreadGroup(threadGroupId);
            String s = ContinuousQueryImpl.class.getSimpleName() + ":" + resourceId.getId();
            ResourceId threadId = new ResourceId(s);
            this.asyncProcessor = new AsyncProcessor(threadGroup, threadId, listener);
        }
    }

    @Override
    public void start() throws Exception {
        if (asyncProcessor != null) {
            asyncProcessor.start();
        }
    }

    public boolean isSynchronous() {
        return syncWorker != null;
    }

    /**
     * Works only if the {@link #ContinuousQueryImpl(String, com.tibco.cep.query.stream.monitor.ResourceId,
     * com.tibco.cep.query.stream.core.Source[], com.tibco.cep.query.stream.core.Sink,
     * com.tibco.cep.query.stream.impl.query.ContinuousQueryImpl.AsyncProcessListener)} was
     * constructed with "sync" mode.
     *
     * @see #isSynchronous()
     */
    public void performSyncWork() {
        if (syncWorker != null) {
            syncWorker.performWork();
        }
    }

    @Override
    public ConcurrentLinkedQueue<I> getQueuedInput() {
        return queuedInput;
    }

    @Override
    public void stop() throws Exception {
        if (asyncProcessor != null) {
            asyncProcessor.signalStop(60 * 1000);
        }
        else {
            syncWorker.signalStop();
        }

        super.stop();
    }

    // ------------

    /**
     * Thread-safe!
     *
     * @param input Anything except <code>null</code>. These objects will be processed one at a time
     *              by a Thread asynchronously, when it invokes the {@link
     *              #handleQueuedInput(Object)} where it should be extracted and the Events sent to
     *              the appropriate Sources. Items can be entire collections where the entire batch
     *              can be processed at once.
     */
    @Override
    public void enqueueInput(I input) throws Exception {
        queuedInput.offer(input);

        if (tryAcquireQueryLock()) {
            try {
                notifyQueryLockWaiter();
            }
            finally {
                relinquishQueryLock();
            }
        }
    }

    /**
     * Lock should be acquired first!
     *
     * @param object
     * @throws Throwable
     */
    private void processQueuedInput(I object) throws Throwable {
        Throwable throwable = null;
        boolean started = false;

        try {
            started = attemptCycleStart(CycleType.TRIGGERED);

            if (started) {
                handleQueuedInput(object);
            }
        }
        catch (Throwable t) {
            throwable = t;
            throw t;
        }
        finally {
            if (started) {
                cycleEnd(CycleType.TRIGGERED, throwable);
            }
        }
    }

    protected abstract void handleQueuedInput(I input) throws Exception;

    /**
     * @return <code>-1</code> if all processing <b>appears</b> to have been completed. Anything
     *         greater (in <b>milliseconds</b>) means that the Query is still running - but this
     *         value must not be taken literally.
     */
    public long calcEstimatedFinishTime() {
        long estimatedWaitTime = 0;

        acquireQueryLock();
        try {
            estimatedWaitTime = calculateWaitTimeMillis();

            if (queuedInput.isEmpty() && estimatedWaitTime == Long.MAX_VALUE) {
                estimatedWaitTime = -1;
            }
        }
        finally {
            relinquishQueryLock();
        }

        return estimatedWaitTime;
    }

    // ------------

    protected class Worker {
        protected final boolean synchronousRun;

        protected final AtomicBoolean stopFlag;

        protected Worker(boolean synchronousRun) {
            this.synchronousRun = synchronousRun;
            this.stopFlag = new AtomicBoolean(false);
        }

        protected void signalStop() {
            this.stopFlag.set(true);
        }

        /**
         * If {@link #synchronousRun} is <code>true</code>, then this method returns after a few
         * cycles. Otherwise, the method does not return until {@link #signalStop()} is invoked.
         */
        protected void performWork() {
            final ConcurrentLinkedQueue<I> q = ContinuousQueryImpl.this.queuedInput;
            final ReentrantLock l = ContinuousQueryImpl.this.lock;
            final Condition nc = ContinuousQueryImpl.this.notifyCondition;
            final Condition pc = ContinuousQueryImpl.this.pauseCondition;
            final AtomicBoolean qPaused = ContinuousQueryImpl.this.paused;
            final int maxQuickLoop = 25;
            final boolean cachedSyncRunFlag = synchronousRun;

            while (stopFlag.get() == false) {
                l.lock();
                try {
                    while (qPaused.get()) {
                        pc.await();
                    }

                    for (int i = 0; i < maxQuickLoop; i++) {
                        // Do pending tasks from previous cycle.
                        boolean didWork = ContinuousQueryImpl.this.performPendingWork();

                        // Consume input.
                        I objectFromQ = q.poll();
                        if (objectFromQ != null) {
                            ContinuousQueryImpl.this.processQueuedInput(objectFromQ);
                        }

                        // ---------

                        if (didWork == false && objectFromQ == null) {
                            long waitTime = ContinuousQueryImpl.this.calculateWaitTimeMillis();
                            if (waitTime > 0) {
                                if (cachedSyncRunFlag) {
                                    break;
                                }

                                I quickPeek = q.peek();
                                if (quickPeek != null) {
                                    continue;
                                }

                                boolean interrupted = nc.await(waitTime, TimeUnit.MILLISECONDS);
                                if (interrupted) {
                                    break;
                                }
                            }
                        }
                    }
                }
                catch (InterruptedException e) {
                    // Do nothing.
                }
                catch (Throwable t) {
                    logError(t);
                }
                finally {
                    l.unlock();
                }

                if (cachedSyncRunFlag) {
                    /*
                    Run the loop only once in Sync mode. The caller should keep calling this method
                    repeatedly.
                    */
                    break;
                }
            }
        }

        protected void logError(Throwable t) {
            Logger logger = Registry.getInstance().getComponent(Logger.class);
            String s = sanitizeMessage(t);
            logger.log(LogLevel.ERROR, s, t);

            QueryWatcher watcher = context.getQueryContext().getQueryWatcher();
            Run latestRun = watcher.getCurrentRun();
            if (latestRun != null) {
                logger.log(LogLevel.ERROR, "---- Additional Info ----");

                String message = "Most recent run: " + latestRun.getStatus() + ", "
                        + latestRun.getStartTime() + " to " + latestRun.getEndTime();
                logger.log(LogLevel.ERROR, message);

                Step step = latestRun.getFirstStep();
                int i = 1;
                while (step != null) {
                    message = "  " + i + ")" + step.getResourceId();
                    logger.log(LogLevel.ERROR, message);

                    i++;

                    step = step.getNextStep();
                }

                Throwable error = latestRun.getError();
                if (error != null && error != t) {
                    s = sanitizeMessage(error);
                    logger.log(LogLevel.ERROR, s, error);
                }
            }
        }
    }

    protected String sanitizeMessage(Throwable t) {
        return t.getMessage();
    }

    protected class AsyncProcessor extends CustomDaemonThread {
        protected Worker worker;

        protected AsyncProcessListener listener;

        protected AsyncProcessor(CustomThreadGroup group, ResourceId resourceId,
                                 AsyncProcessListener listener) {
            super(group, resourceId);

            this.worker = new Worker(false);
            this.listener = listener;
        }

        @Override
        public void signalStop() {
            worker.signalStop();

            super.signalStop();
        }

        @Override
        public void signalStop(long joinMillis) {
            worker.signalStop();

            super.signalStop(joinMillis);
        }

        @Override
        public void run() {
            listener.beforeStart();

            try {
                worker.performWork();

                runCompleted();
            }
            finally {
                listener.afterEnd();
            }
        }
    }

    public interface AsyncProcessListener {
        void beforeStart();

        void afterEnd();
    }
}
