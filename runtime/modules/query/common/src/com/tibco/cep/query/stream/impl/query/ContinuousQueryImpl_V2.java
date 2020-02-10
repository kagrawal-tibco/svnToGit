package com.tibco.cep.query.stream.impl.query;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.service.QueryLogger;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.monitor.QueryWatcher;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.query.continuous.AbstractContinuousQuery;
import com.tibco.cep.runtime.util.scheduler.Id;
import com.tibco.cep.runtime.util.scheduler.Job;
import com.tibco.cep.runtime.util.scheduler.JobContext;
import com.tibco.cep.runtime.util.scheduler.Scheduler;
import com.tibco.cep.runtime.util.scheduler.SchedulerException;

/*
* Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
*/
public abstract class ContinuousQueryImpl_V2<I> extends AbstractContinuousQuery<I> implements Job {
    // logger
    protected static final Logger logger = new QueryLogger(ContinuousQueryImpl_V2.class.getName());
    protected final ConcurrentLinkedQueue<I> queuedInput;
    protected final Scheduler scheduler;
    protected Id jobId;
    protected int maxEventsToProcess = 10000;
    protected final AsyncProcessListener listener;
    protected Level level = Level.DEBUG;
    
    /**
     * @param regionName
     * @param resourceId
     * @param sources
     * @param sink
     * @param listener   If <code>null</code>, then runs in synchronous mode. Otherwise, async.
     */
    public ContinuousQueryImpl_V2(String regionName, ResourceId resourceId, Source[] sources,
                                  Sink sink, AsyncProcessListener listener) {
        super(regionName, resourceId, sources, sink);
        scheduler = Registry.getInstance().getComponent(CQScheduler.class).getScheduler();
        this.queuedInput = new ConcurrentLinkedQueue<I>();
        this.listener = listener;
    }

    public boolean isSynchronous() {
        return listener == null;
    }
    
    @Override
    public void enqueueInput(I input) throws Exception {
        queuedInput.offer(input);
        logger.log(Level.DEBUG, "Resource:" + resourceId + ";JobID:" +
                jobId.getValue() + " requesting schedule.");
        try {
            this.scheduler.scheduleNow(jobId);
        } catch (SchedulerException ex) {
            logger.log(Level.WARN, "Unable to request schedule for job:" + jobId, ex);
        }
    }

    protected abstract void handleQueuedInput(I input) throws Exception;

    @Override
    public void setId(Id id) {
        this.jobId = id;
    }

    @Override
    public Id getId() {
        return this.jobId;
    }

    @Override
    public void execute(JobContext context) {
        Thread.currentThread().setName("Job-" + jobId);
        logger.log(level, "Executing Job-" + jobId);
        // Implement this method.
        long noOfInputsProcessed = performWork(maxEventsToProcess, this.queuedInput);
        context.setProcessedCount(noOfInputsProcessed);
    }

    @Override
    public void pause() {
        super.pause();
        try {
            scheduler.suspendFutureExecutions(jobId);
        } catch (SchedulerException ex) {
            logger.log(Level.ERROR, "Unable to pause job:" + jobId.getValue(), ex);
        }
    }

    @Override
    public void resume() {
        super.resume();
        try {
            scheduler.resumeJob(jobId);
        } catch (SchedulerException ex) {
            logger.log(Level.ERROR, "Unable to resume job:" + jobId.getValue(), ex);
        }
    }

    @Override
    public void start() throws Exception {
        super.start();
        jobId = scheduler.registerJob(this);
        if(listener != null) {
            listener.beforeStart();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        scheduler.unregisterJob(jobId);
        if(listener != null) {
            listener.afterEnd();
        }
    }

    protected long performWork(final int maxEvents, final ConcurrentLinkedQueue<I> q) {
        long procCount = maxEvents;
        try {
            // Iterate over the elements in the queue and
            // process it till max events limit is reached.

            for (int i = 0; i < maxEvents; i++) {
                // Do pending tasks from previous cycle.
                boolean didWork = performPendingWork();
                // Consume input.
                I objectFromQ = q.poll();
                if (objectFromQ != null) {
                    processQueuedInput(objectFromQ);
                }
                // ---------
                if (didWork == false && objectFromQ == null) {
                    procCount = i;
                    break;
                }
            }
            logger.log(Level.DEBUG, "Job-" + jobId +" processed " + procCount + " inputs");
            // If the queue is not empty, request schedule time once more.
            if(q.isEmpty() == false && scheduler.isJobQueued(jobId) == false) {
                scheduler.scheduleNow(jobId);
            }
        } catch (InterruptedException e) {
            // Do nothing.
        } catch (Throwable t) {
            logError(t);
        }
        return procCount;
    }

    public void processQueuedInput(I objectFromQ) throws Exception {
        Throwable throwable = null;
        boolean started = false;

        try {
            started = attemptCycleStart(CycleType.TRIGGERED);

            if (started) {
                handleQueuedInput(objectFromQ);
            }
        }
        catch (Throwable t) {
            throwable = t;
            throw new Exception(t);
        }
        finally {
            if (started) {
                cycleEnd(CycleType.TRIGGERED, throwable);
            }
        }
    }
        
    public interface AsyncProcessListener {
        void beforeStart();
        void afterEnd();
    }
    
    protected String sanitizeMessage(Throwable t) {
        return t.getMessage();
    }

    protected void logError(Throwable t) {
        String s = sanitizeMessage(t);
        logger.log(Level.ERROR, s, t);

        QueryWatcher watcher = context.getQueryContext().getQueryWatcher();
        QueryWatcher.Run latestRun = watcher.getCurrentRun();
        if (latestRun != null) {
            logger.log(Level.ERROR, "---- Additional Info ----");

            String message = "Most recent run: " + latestRun.getStatus() + ", "
                    + latestRun.getStartTime() + " to " + latestRun.getEndTime();
            logger.log(Level.ERROR, message);

            QueryWatcher.Step step = latestRun.getFirstStep();
            int i = 1;
            while (step != null) {
                message = "  " + i + ")" + step.getResourceId();
                logger.log(Level.ERROR, message);

                i++;

                step = step.getNextStep();
            }

            Throwable error = latestRun.getError();
            if (error != null && error != t) {
                s = sanitizeMessage(error);
                logger.log(Level.ERROR, s, error);
            }
        }
    }

    /**
     * @return <code>-1</code> if all processing <b>appears</b> to have been completed. Anything
     *         greater (in <b>milliseconds</b>) means that the Query is still running - but this
     *         value must not be taken literally.
     */
    public long calcEstimatedFinishTime() {
        long estimatedWaitTime = 0;

        acquireQueryLock();
        try {
            estimatedWaitTime = scheduler.estimateRunTime(jobId);

            if (queuedInput.isEmpty() && estimatedWaitTime == Long.MAX_VALUE) {
                estimatedWaitTime = -1;
            }
        }
        finally {
            relinquishQueryLock();
        }

        return estimatedWaitTime;
    }    
    
}
