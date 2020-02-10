package com.tibco.cep.runtime.util.scheduler.impl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.util.scheduler.Id;
import com.tibco.cep.runtime.util.scheduler.Job;
import com.tibco.cep.runtime.util.scheduler.Scheduler;
import com.tibco.cep.runtime.util.scheduler.SchedulerException;
import com.tibco.cep.runtime.util.scheduler.internal.CustomQueue;
import com.tibco.cep.runtime.util.scheduler.internal.JobRegistry;
import com.tibco.cep.runtime.util.scheduler.internal.JobRegistryException;
import com.tibco.cep.runtime.util.scheduler.internal.Request;
import com.tibco.cep.runtime.util.scheduler.internal.impl.CustomThreadpool;
import com.tibco.cep.runtime.util.scheduler.internal.impl.JobQueueImpl;
import com.tibco.cep.runtime.util.scheduler.internal.impl.JobRegistryImpl;
import com.tibco.cep.runtime.util.scheduler.internal.impl.RequestImpl;

/**
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public class SchedulerImpl implements Scheduler, Runnable {
    // logger
    private static final Logger logger;

    static {
        logger = LogManagerFactory.getLogManager().getLogger(SchedulerImpl.class.getName());
    }
    // Stop flag
    private final AtomicBoolean stopFlag;
    // Max Threadpool size
    private final int TP_SIZE = 20;
    // SchedulerImpl thread.
    private final ExecutorService dispThread;
    // Threadpool
    private CustomThreadpool threadpool;
    // Job registry
    private final JobRegistry<Job, Id> jobRegistry;
    // Job Id index.
    private final AtomicLong jobIndex = new AtomicLong(0);
    // Job queue
    private final CustomQueue<Request> queue;
    // Lock and condition for signalling
    private final Lock lock = new ReentrantLock();
    // TP Signal condition
    private final Condition tpSignal = lock.newCondition();
    // Suspend list and lookup map.
    private final ConcurrentSkipListSet<Id> suspendedJobs = new ConcurrentSkipListSet<Id>();
    private final ConcurrentMap<Id, Request> suspendedRequests =
            new ConcurrentHashMap<Id, Request>();

    // Scheduler stats variables
    private final AtomicLong noOfRuns = new AtomicLong(0);
    private final AtomicLong  startTime = new AtomicLong(0);
    private final AtomicLong avgRunTime = new AtomicLong(1000);
    private final ConcurrentSkipListSet<Id> execTracker;

    public SchedulerImpl() {
        // Job registry
        jobRegistry = new JobRegistryImpl<Job, Id>();        
        this.stopFlag = new AtomicBoolean(false);
        this.dispThread = Executors.newSingleThreadExecutor();
        this.queue = new JobQueueImpl();
        this.execTracker = new ConcurrentSkipListSet<Id>();
    }

    @Override
    public void start(final Parameters parameters) {
        int maxSize = 10, minSize = 2;
        if (parameters.getMaxThreads() > 0) {
            maxSize = parameters.getMaxThreads();
        }
        if (parameters.getMinThreads() > 0) {
            minSize = parameters.getMinThreads();
        }
        this.threadpool = new CustomThreadpool(this, maxSize, minSize, 30, TimeUnit.SECONDS);
        if (parameters.getName() != null) {
            this.threadpool.setThreadFactory(new ThreadFactory() {

                private int counter = 0;

                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setName(parameters.getName() + ":" + counter++);
                    return t;
                }
            });
        }
        this.dispThread.execute(this);
    }

    @Override
    public void stop() {
        this.stopFlag.set(true);
        // Close the job queue.
        logger.log(Level.DEBUG, "Closing the job queue.");
        this.queue.close();
        // Shutdown the scheduler threadpool
        logger.log(Level.DEBUG, "Shutting down threadpool.");
        shutdownExecutor(this.threadpool);
        // Shutdown the dispatcher.
        logger.log(Level.DEBUG, "Shutting down dispatcher.");
        shutdownExecutor(dispThread);
    }

    @Override
    public Id registerJob(Job job) throws SchedulerException {
        long index = jobIndex.incrementAndGet();
        try {
            Id result = jobRegistry.addEntry(job, new IdImpl(index));
            if (result.getValue() != index) {
                // Make an effort to reset the counter back.
                jobIndex.compareAndSet(index, index - 1);
            }
            return result;
        } catch (JobRegistryException ex) {
            // Make an effort to reset the counter back.
            jobIndex.compareAndSet(index, index - 1);
            throw new SchedulerException("Job Registration exception.", ex);
        }
    }

    @Override
    public void unregisterJob(Id jobId) throws SchedulerException {
        try {
            // Remove the job from the job queue.
            queue.removeItem(jobId);
            // Remove the job from the registry.
            jobRegistry.removeEntry(jobId);
            // Remove the job from suspend set
            if (this.suspendedJobs.remove(jobId) == true) {
                this.suspendedRequests.remove(jobId);
            }
        } catch (Exception ex) {
            throw new SchedulerException("Job Id is being used by another job", ex);
        }
    }

    @Override
    public List<? extends Id> getRegisteredJobIds() {
        return Arrays.asList(jobRegistry.getValues().toArray(new Id[0]));
    }

    @Override
    public void scheduleNow(Id jobId) throws SchedulerException {
        // add a request with current timestamp.
        scheduleAt(jobId, System.currentTimeMillis());
    }

    @Override
    public void scheduleAt(Id jobId, long timestamp) throws SchedulerException {
        if (jobId == null) {
            throw new SchedulerException("JobId cannot be null.");
        }
        Request request = new RequestImpl(jobId, timestamp);
        if (this.suspendedJobs.contains(jobId) == true) {
            Request req = this.suspendedRequests.putIfAbsent(jobId, request);
            if (req != null) {
                req.addRequest(req);
            }
        } else {
            // add a request to the job queue with given timestamp.
            queue.addItem(request);
        }
    }

    @Override
    public void suspendFutureExecutions(Id jobId) throws SchedulerException {
        if (jobId == null) {
            throw new SchedulerException("Job not registered with the scheduler.");
        }
        this.suspendedJobs.add(jobId);
        // Remove the job from the job queue.
        Request request = this.queue.removeItem(jobId);
        // Add this request to the suspend queue.
        if (request != null) {
            Request req = this.suspendedRequests.putIfAbsent(jobId, request);
            // Already an entry is made in the suspended request lookup map.
            if (req != null) {
                req.addRequest(request);
            }
        }
    }

    @Override
    public long estimateRunTime(Id jobId) {
        if(this.queue.hasItem(jobId)) {
            return 1000;
        } else if(execTracker.contains(jobId)) {
            return avgRunTime.get();
        }
        return -1;  
    }

    @Override
    public boolean isJobQueued(Id jobId) {
        return queue.hasItem(jobId);
    }
    
    @Override
    public void resumeJob(Id jobId) throws SchedulerException {
        if (this.suspendedJobs.remove(jobId) == true) {
            Request req = this.suspendedRequests.remove(jobId);
            if (req != null) {
                this.queue.addItem(req);
            }
        }
    }

    public JobRegistry<Job, Id> getJobRegistry() {
        return jobRegistry;
    }

    @Override
    public void run() {
        while (!Thread.interrupted() && stopFlag.get() == false) {
            try {
                int retries = 0;
                long timeout = 100;
                while (this.threadpool.getSize() == TP_SIZE) {
                    if (retries++ < 40) {
                        timeout += 10;
                    }
                    try {
                        lock.lock();
                        tpSignal.await(timeout, TimeUnit.MILLISECONDS);
                    } finally {
                        lock.unlock();
                    }
                }
                Request request = this.queue.getItem();
                Id jobId = request.getJobId();
                // Execute the job.
                if (execTracker.add(request.getJobId()) == true) {
                    this.threadpool.submit(request);
                    long runs = noOfRuns.incrementAndGet();
                    long currTime = System.currentTimeMillis();
                    if(startTime.compareAndSet(0, currTime) == false) {
                        long avgTime = (currTime - startTime.get())/runs;
                        avgRunTime.set(avgTime);
                    }
                    logger.log(Level.DEBUG, "Job-" + jobId + " submitted for execution.");
                } else {
                    // Penalize the job for running slow.                    
                    request.penalize();
                    logger.log(Level.DEBUG, "Job-" + jobId + " penalized for slow run.");
                    // put it back into the queue.
                    queue.addItem(request);
                }
            } catch (InterruptedException ex) {
                if (this.stopFlag.get() == false) {
                    logger.log(Level.ERROR, "Interrupt received.", ex);
                    continue;
                }
            }
        }
    }

    public ConcurrentSkipListSet<Id> getExecutionTracker() {
        return this.execTracker;
    }

    private void shutdownExecutor(ExecutorService execService) {
        execService.shutdown();
        try {
            // Wait for dispatcher to exit.
            while (execService.awaitTermination(100, TimeUnit.MILLISECONDS) == false) {
                execService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            // Do nothing.
        }
    }

    public void sendTPSignal() {
        try {
            lock.lock();
            tpSignal.signal();
        } finally {
            lock.unlock();
        }
    }

    private boolean isJobRunning(Request item) {
        // Check if the job is running.
        return execTracker.contains(item.getJobId());
    }
}
