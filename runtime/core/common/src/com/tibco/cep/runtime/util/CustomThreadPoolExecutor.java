package com.tibco.cep.runtime.util;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher;
import com.tibco.cep.runtime.session.BEManagedThread;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.SuspendAwareBlockingQueue.SuspendableResource;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
* Author: Ashwin Jayaprakash Date: Mar 12, 2009 Time: 7:11:07 PM
*/

public class CustomThreadPoolExecutor extends ThreadPoolExecutor
        implements AsyncWorkerServiceWatcher.AsyncWorkerService, ExecutableResource, SuspendableResource {
    protected final LinkedBlockingQueue<Runnable> suspendedJobs;

    private volatile boolean suspendRequested;

    protected final ReentrantLock suspendLock;

    protected final Condition suspendCondition;

    protected final String threadPoolName;

    protected FQName name;

    protected ThreadLocal<Integer> threadsWarnedAboutSuspend;

    protected volatile FutureTask<?> suspendAction;

    protected static Logger logger = LogManagerFactory.getLogManager().getLogger(CustomThreadPoolExecutor.class);

    public static CustomThreadPoolExecutor create(String threadPoolName,
                                                  int maximumThreadPoolSize,
                                                  int maxJobQueueSize,
                                                  RuleServiceProvider rsp) {
        CustomDaemonThreadFactory threadFactory =
                new CustomDaemonThreadFactory(threadPoolName, rsp);
        SuspendAwareBlockingQueue<Runnable> workQueue = new SuspendAwareLBQImpl<Runnable>(maxJobQueueSize);

        return new CustomThreadPoolExecutor(threadPoolName,
                maximumThreadPoolSize, maximumThreadPoolSize,
                10 * 60 /* 10 mins */, TimeUnit.SECONDS,
                workQueue, threadFactory, logger);
    }

    /**
     * Starts in suspended mode - {@link #waitForQToClearAfterSuspendRequest()}.
     *
     * @param threadPoolName
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param threadFactory
     * @param logger
     */
    protected CustomThreadPoolExecutor(String threadPoolName, int corePoolSize, int maximumPoolSize,
                                       long keepAliveTime, TimeUnit unit,
                                       SuspendAwareBlockingQueue<Runnable> workQueue,
                                       CustomThreadFactory<? extends BEManagedThread> threadFactory,
                                       Logger logger) {
        super(
                /*
                That old Java Threadpool bug with min pool size
                (http://cs.oswego.edu/pipermail/concurrency-interest/2009-April/006020.html)
                */
                maximumPoolSize, maximumPoolSize,
                keepAliveTime, unit, workQueue, threadFactory,
                new CustomBlockingRejectionHandler(threadPoolName, logger));

        this.threadPoolName = threadPoolName;

        this.suspendedJobs = new LinkedBlockingQueue<Runnable>();
        this.suspendLock = new ReentrantLock();
        this.suspendCondition = this.suspendLock.newCondition();

        this.threadsWarnedAboutSuspend = new ThreadLocal<Integer>();

        //Dup assignment is ok. Double checked locking etc not required.
        CustomThreadPoolExecutor.logger = logger;

        workQueue.setSuspendableResource(this);

        prestartAllCoreThreads();

        setSuspendRequested();
        waitForQToClearAfterSuspendRequest();
    }

    @Override
    public final boolean prestartCoreThread() {
        return super.prestartCoreThread();
    }

    @Override
    public final int prestartAllCoreThreads() {
        return super.prestartAllCoreThreads();
    }

    /**
     * Call {@link #setSuspendRequested()} first.
     */
    private void waitForQToClearAfterSuspendRequest() {
        BlockingQueue<Runnable> q = getQueue();

        suspendLock.lock();
        try {
            //--------------

            sleepUntilQEmpties(q);

            //--------------

            final int maxAllowed = getJobQueueCapacity();

            while (q.isEmpty() == false) {
                int drainLimit = maxAllowed - suspendedJobs.size();

                if (drainLimit <= 0) {
                    logger.log(Level.WARN,
                            "Suspending threadpool [%s] by transferring scheduled tasks to the temporary queue has exceeded its limit." +
                                    " Temporary queue already has [%d] tasks." +
                                    " The threadpool job queue still has [%d] remaining tasks." +
                                    nameToString(), suspendedJobs.size(), q.size());

                    sleepUntilQEmpties(q);
                }

                //Pull out everything and hold them in another queue.
                int c = q.drainTo(suspendedJobs, drainLimit);

                if (c > 0) {
                    logger.log(Level.INFO,
                            "Suspending threadpool [%s] by transferring scheduled tasks to a temporary queue." +
                                    " Transferred [%d] tasks." +
                                    " The threadpool job queue has [%d] remaining tasks." +
                                    " The temporary queue now has [%d] tasks.",
                                    nameToString(), c, q.size(), suspendedJobs.size());
                }
            }
        }
        finally {
            suspendLock.unlock();
        }
    }

    private void sleepUntilQEmpties(BlockingQueue<Runnable> q) {
        for (int loop = 1; loop <= 12 && q.isEmpty() == false; loop++) {
            logger.log(Level.INFO,
                    "Attempting to suspend threadpool [%s] but the job queue has [%d] remaining tasks." +
                            " Waiting for queue to empty. Attempt [%d]", nameToString(), q.size(), loop);

            try {
                //Wait for jobs to finish.
                suspendCondition.await(5, TimeUnit.SECONDS);
            }
            catch (InterruptedException e) {
            }
        }
    }

    @Override
    public final void checkAndBlockIfSuspended() {
        boolean waited = false;

        for (int c = 0; suspendRequested; c++) {
        	suspendLock.lock();
            try {
                Thread caller = Thread.currentThread();

                if (caller instanceof ExecutableResource) {
                    Integer waitCount = threadsWarnedAboutSuspend.get();

                    if (waitCount == null) {
                        //Let this thread finish its job once. If it comes back again, then we will block it.
                        threadsWarnedAboutSuspend.set(1);

                        logger.log(Level.INFO,
                                "Thread [%s] is attempting to submit a job into a suspended threadpool [%s]." +
                                        " It will be allowed this one time.", caller, nameToString());

                        return;
                    }
                    else {
                        logger.log(Level.DEBUG,
                                "Thread [%s] is attempting to submit a job into a suspended threadpool [%s] for the [%d] time." +
                                        " It was allowed the first time, but this time it will be blocked." +
                                        caller, nameToString(), waitCount);

                        threadsWarnedAboutSuspend.set(waitCount + 1);
                    }
                }

                try {
                    suspendCondition.await(1, TimeUnit.SECONDS);
                    waited = true;
                }
                catch (InterruptedException e) {
                    //Ignore.
                }
            }
            finally {
                suspendLock.unlock();
            }
        }

        if (waited) {
            threadsWarnedAboutSuspend.remove();
        }
    } 

    private void setSuspendRequested() {
        suspendRequested = true;
    }
    
    private void unsetSuspendRequested() {
        suspendRequested = false;
    }

    public final boolean isSuspended() {
        if (suspendRequested) {
            if (suspendAction != null) {
                return suspendAction.isDone();
            }
        }
        return false;
    }

    public boolean isStarted() {
        return !isShutdown();
    }

    /**
     * Does not suspend immediately. It starts off an async suspension process. See {@link #setSuspendRequested()} and
     * {@link #suspendAction}.
     */
    public void suspendResource() {
        if (suspendRequested && suspendAction != null) {
            return;
        }

        synchronized (this) {
        	logger.log(Level.DEBUG, "Suspending threadpool [%s]", nameToString());
        	
            setSuspendRequested();

            suspendAction = new FutureTask<Object>(new Runnable() {
                @Override
                public void run() {
                    waitForQToClearAfterSuspendRequest();
                    logger.log(Level.DEBUG, "Suspended threadpool [%s]", nameToString());
                }
            }, null);
            
            Thread suspenderThread = new Thread(suspendAction, "Temp-Resource-Suspender-" + nameToString());
            suspenderThread.setDaemon(true);
            suspenderThread.start();
        }
    }

    public void resumeResource() {
        if (suspendRequested == false) {
            return;
        }

        trySuspendLock();
        try {
            logger.log(Level.DEBUG, "Resuming threadpool [%s]", nameToString());

            if (suspendRequested == false) {
                return;
            }

            unsetSuspendRequested();

            rescheduleSuspended();

            suspendCondition.signalAll();
        }
        finally {
            logger.log(Level.DEBUG, "Resumed threadpool [%s]", nameToString());

            suspendLock.unlock();
        }
    }

    private void trySuspendLock() {
        int loop = 0;
        for (boolean success = false; success != true; loop++) {
            try {
                success = suspendLock.tryLock(5, TimeUnit.SECONDS);
            }
            catch (InterruptedException e) {
                //Ignore.
            }

            if (success == false) {
                if (loop <= 11) {
                    logger.log(Level.WARN,
                            "Attempt to resume threadpool [%s] by acquiring the lock is not succeeding." +
                                    " Attempt [%d]. Lock details [%s]", nameToString(), loop, suspendLock);
                }
                else {
                    String msg =
                            String.format("Attempt to resume threadpool [%s] by acquiring the lock is not succeeding." +
                                    " Attempt [%d]. Lock details [%s]. Giving up!", nameToString(), loop, suspendLock);

                    throw new RuntimeException(msg);
                }
            }
        }
    }

    protected void rescheduleSuspended() {
        SuspendAwareBlockingQueue<Runnable> q = getQueue();

        //Resume by moving the jobs back into the q in the correct order.
        //"Correct order" is probably not going to happen because there seem to be other threads silently inserting to "q".

        Runnable transferredObj = null;
        while ((transferredObj = suspendedJobs.poll()) != null) {
            boolean success = false;
            int x = 0;

            for (; x < 12; x++) {
                try {
                    success = q.offerAlways(transferredObj, 5, TimeUnit.SECONDS);
                }
                catch (InterruptedException e) {
                }

                if (success == false) {
                    logger.log(Level.WARN,
                            "Attempt to resume threadpool [%s] by re-scheduling suspended task did not succeed" +
                                    " (Transfer attempt %d for suspended task [%s])." +
                                    " The threadpool job queue seems to be full already - max [%d], current [%d].",
                                    nameToString(), x, transferredObj.toString(), getJobQueueCapacity(), q.size());
                }
                else {
                    break;
                }
            }

            if (success == false) {
                String msg = String.format(
                        "Attempt to resume threadpool [%s] by re-scheduling suspended task did not succeed" +
                                " Transfer attempted %d for suspended task [%s]. Giving up!" +
                                " The threadpool job queue seems to be full already - max [%d], current [%d].",
                                nameToString(), x, transferredObj.toString(), getJobQueueCapacity(), q.size());

                throw new RuntimeException(msg);
            }
        }
    }

    @Override
    public SuspendAwareBlockingQueue<Runnable> getQueue() {
        return (SuspendAwareBlockingQueue<Runnable>) super.getQueue();
    }

    @Override
    public void shutdown() {
        setSuspendRequested();
        waitForQToClearAfterSuspendRequest();
    }

    @Override
    public List<Runnable> shutdownNow() {
        List<Runnable> list = super.shutdownNow();

        setSuspendRequested();

        return list;
    }

    //-----------

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        if (t instanceof BEManagedThread) {
            try {
                ((BEManagedThread) t).acquireReadLock();
            }
            finally {
                ((BEManagedThread) t).releaseReadLock();
            }
        }

        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        /*try {
            super.afterExecute(r, t);
        }
        finally {
            Thread thread = Thread.currentThread();

            if (thread instanceof BEManagedThread) {
                ((BEManagedThread) thread).releaseReadLock();
            }
        }*/
    }

    //-----------

    public String getThreadPoolName() {
        return threadPoolName;
    }

    public void setName(FQName name) {
        this.name = name;
    }

    //-----------

    public boolean isActive() {
        return isStarted() && !isSuspended();
    }

    public FQName getName() {
        return name;
    }

    public int getNumMaxThreads() {
        return getMaximumPoolSize();
    }

    public int getNumActiveThreads() {
        return getActiveCount();
    }

    public int getJobQueueCapacity() {
        BlockingQueue q = getQueue();

        int c = q.remainingCapacity();
        //Avoid overflow.
        if (c == Integer.MAX_VALUE) {
            return c;
        }

        return (q.size() + c);
    }

    public int getJobQueueSize() {
        return getQueue().size();
    }
    
    private String nameToString() {
    	return (name != null ? name.toString() : null);
    }
}