package com.tibco.cep.runtime.util.scheduler.internal.impl;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.util.scheduler.Id;
import com.tibco.cep.runtime.util.scheduler.Job;
import com.tibco.cep.runtime.util.scheduler.JobContext;
import com.tibco.cep.runtime.util.scheduler.impl.JobContextImpl;
import com.tibco.cep.runtime.util.scheduler.impl.SchedulerImpl;
import com.tibco.cep.runtime.util.scheduler.internal.Request;

/**
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public class CustomThreadpool extends ThreadPoolExecutor {
    // logger
    private static final Logger logger;

    static {
        logger = LogManagerFactory.getLogManager().getLogger(CustomThreadpool.class.getName());
    }
    // current threadpool size
    private final AtomicInteger tpSize = new AtomicInteger(0);
    // Max pool size
    private final int maxSize;
    // execution tracker - Set of Ids of the jobs that are currently running.
    private final ConcurrentSkipListSet<Id> execTracker;
    // Lock and condition for signaling
    private final Lock lock = new ReentrantLock();
    private final Condition spaceAvailable = lock.newCondition();
    // stop flag
    private final AtomicBoolean stopFlag = new AtomicBoolean(false);
    // Scheduler
    private final SchedulerImpl scheduler;

    public CustomThreadpool(
            SchedulerImpl scheduler,
            int maxSize, int minSize, long keepAliveTime,
            TimeUnit unit) {
        super(minSize, maxSize, keepAliveTime,
                unit, new LinkedBlockingQueue<Runnable>(),
                new RejectedJobHandler(scheduler));
        this.maxSize = maxSize;
        prestartCoreThread();
        this.scheduler = scheduler;
        this.execTracker = scheduler.getExecutionTracker();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        tpSize.incrementAndGet();
        JobRunnable jobRun = (JobRunnable) r;
        logger.log(Level.DEBUG, jobRun.getContext().getJobId().getValue() +
                    "\tStarted execution.");        
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        tpSize.decrementAndGet();
        JobRunnable jobRun = (JobRunnable) r;
        JobContext context = jobRun.getContext();
        // remove the context from the execution map.
        removeJobFromExecMap(context);
        // Signal the scheduler.
        this.scheduler.sendTPSignal();
        return;
    }

    @Override
    protected void terminated() {
        stopFlag.set(true);
        try {
            lock.lock();
            spaceAvailable.signalAll();
        } finally {
            lock.unlock();
        }
        super.terminated();
    }

    public boolean submit(Request request) {
        long timeout = 100;
        int retry = 0;
        long space = maxSize - tpSize.get();
        try {
            lock.lock();
            while (space == 0) {
                if (retry < 25) {
                    // Gradually increase the timeout value
                    // After 25 retries, timeout value defaults to max timeout.
                    timeout += (retry++ * 20);
                }
                spaceAvailable.await(timeout, TimeUnit.MILLISECONDS);
                space = maxSize - tpSize.get();
            }
            if (stopFlag.get() == true) {
                return false;
            }
            logger.log(Level.DEBUG, request.getJobId().getValue() + "\tSubmitted for execution.");
            execute(new JobRunnable(request));
            return true;
        } catch (InterruptedException ex) {
            logger.log(Level.ERROR, request.getJobId().getValue() + "\tUnable to execute job.", ex);
        } finally {
            lock.unlock();
        }
        return false;
    }

    private void removeJobFromExecMap(JobContext context) {
        // Remove the execution context from the map.
        Id jobId = context.getJobId();
        // Remove the job context from the exec map.
        if (execTracker.remove(jobId) == true) {
            logger.log(Level.DEBUG, "Job-" + jobId + " completed by processing " +
                    context.getProcessedCount() + " inputs");
        } else {
            logger.log(Level.WARN,
                    "Job:" + jobId.getValue() + "conflict.");
        }
    }

    public int getSize() {
        return tpSize.get();
    }

    class JobRunnable implements Runnable {

        private final Job job;
        private final JobContext context;

        JobRunnable(Request req) {
            job = scheduler.getJobRegistry().getKey(req.getJobId());
            context = new JobContextImpl(req.getJobId(), req.getRequestTimes());
        }

        JobContext getContext() {
            return context;
        }

        public void run() {
            if (job == null) {
                return;
            }
            job.execute(context);
            return;
        }
    }

    static class RejectedJobHandler implements RejectedExecutionHandler {

        private final SchedulerImpl sched;

        public RejectedJobHandler(SchedulerImpl sched) {
            this.sched = sched;
        }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            sched.sendTPSignal();
            return;
        }
    }
}
