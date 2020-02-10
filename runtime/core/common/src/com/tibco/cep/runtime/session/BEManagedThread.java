package com.tibco.cep.runtime.session;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.util.ExecutableResource;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 1, 2008
 * Time: 8:45:21 AM
 * To change this template use File | Settings | File Templates.
 */

public class BEManagedThread extends Thread implements Thread.UncaughtExceptionHandler, RuleSession.PostRTCAction,
        ExecutableResource {
    public final static byte THREADTYPE_OTHER = 0;
    public final static byte THREADTYPE_CHANNEL = 1;
    public final static byte THREADTYPE_AGENT = 2;

    private static final int MODE_RUN_FROM_QUEUE = 1;
    private static final int MODE_RUN_REPEATING  = 2;
    private static final int MODE_RUN_DELEGATE   = 3;
    
    protected final ArrayList postActions = new ArrayList(2);
    protected Runnable currentJob;

    protected volatile boolean jobInProgress = false;
    protected volatile boolean shutdown = false;
    protected volatile boolean suspend = false;
    protected volatile boolean supportsAgentActions = true;
    protected volatile int runMode = 0;
    protected RuleServiceProvider RSP = null;
    protected BlockingQueue jobQueue = null;
    protected Runnable singletonJob = null;
    protected long interval = 0L;
    protected final Object suspendLock = new Object();
    protected int threadType = THREADTYPE_OTHER;
    private volatile long windowStart=0L;
    private volatile long lastUpdateTime=0L;
    private long numMsgsInWindow=0L;
    private long numJobsProcessed=0L;
    private JobGroupManager jobGroupManager=null;
    protected Logger logger;


    public BEManagedThread(ThreadGroup group, Runnable target, String name,
                           RuleServiceProvider RSP) {
        super(group, target, name);

        this.RSP = RSP;
        this.logger = RSP.getLogger(this.getClass());
    }

    public BEManagedThread(String name, RuleServiceProvider RSP, int queueSize) {
        super((ThreadGroup) null, name);
        this.RSP = RSP;
        this.logger = RSP.getLogger(this.getClass());
        if (queueSize <= 0) {
            this.jobQueue = new LinkedBlockingQueue(Integer.MAX_VALUE);
        } else {
            this.jobQueue = new LinkedBlockingQueue(queueSize);
        }
        ((RuleServiceProviderImpl) this.RSP).registerExecutableResource(this);
    }

    @Deprecated
    public BEManagedThread(ThreadGroup threadGroup, String name, RuleServiceProvider RSP, BlockingQueue queue) {
        super(threadGroup, name);
        this.RSP = RSP;
        this.logger = RSP.getLogger(this.getClass());
        this.jobQueue = queue;
        ((RuleServiceProviderImpl) this.RSP).registerExecutableResource(this);
    }

    @Deprecated
    public BEManagedThread(String name, RuleServiceProvider RSP, BlockingQueue queue) {
        super((ThreadGroup) null, name);
        this.RSP = RSP;
        this.logger = RSP.getLogger(this.getClass());
        this.jobQueue = queue;
        ((RuleServiceProviderImpl) this.RSP).registerExecutableResource(this);
    }

    public BEManagedThread(String name, RuleServiceProvider RSP, Runnable job, long interval) {
        super(((RuleServiceProviderImpl) RSP).getRSPThreadGroup(), name);
        this.RSP = RSP;
        this.logger = RSP.getLogger(this.getClass());
        this.singletonJob = job;
        this.interval = interval;
        ((RuleServiceProviderImpl) this.RSP).registerExecutableResource(this);
    }

    public void setChannelThreadType() {
        threadType = THREADTYPE_CHANNEL;
    }

    public void setAgentThreadType() {
        threadType = THREADTYPE_AGENT;
    }

    protected void incrementNumJobs() {
        long now=System.nanoTime();

        if ((windowStart==0L) || ((now-lastUpdateTime) > 2000000000L)){
            windowStart=lastUpdateTime=now;
            numMsgsInWindow=1L;
        } else {
            lastUpdateTime=now;
            ++numMsgsInWindow;
        }
        ++numJobsProcessed;
    }

    public double getJobRate() {
        long denom= (lastUpdateTime-windowStart)/(1000000000L);
        if (denom != 0) {
            return numMsgsInWindow/(denom);
        } else {
            return 0.0;
        }
    }
    
    public long getNumJobs() {
        return numJobsProcessed;
    }

    public static boolean isChannelThread() {
        Thread t = Thread.currentThread();
        if (t instanceof BEManagedThread) {
            return (((BEManagedThread) t).threadType == THREADTYPE_CHANNEL);
        } else {
            return false;
        }
    }

    public static boolean isAgentThread() {
        Thread t = Thread.currentThread();
        if (t instanceof BEManagedThread) {
            return (((BEManagedThread) t).threadType == THREADTYPE_AGENT);
        } else {
            return false;
        }
    }

    protected void setSupportsPostAction(boolean supportsAgentAction) {
        this.supportsAgentActions = supportsAgentAction;
    }

    public boolean supportsPostAction() {
        return supportsAgentActions;
    }

    public boolean isStarted() {
        return isAlive();
    }

    public void shutdown() {
        shutdown = true;
        if (Thread.currentThread() != this) {
        while (jobInProgress) {
            try {
                this.logger.log(Level.INFO, "Job is in progress , going to sleep for 5 secs");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }

            if (!jobInProgress) {
                this.logger.log(Level.INFO, "Job has finished, interrupting thread: %s", this.getName());
                interrupt();
                return;
            }
        }
        try {
            if (runMode == MODE_RUN_FROM_QUEUE) {
                schedule(new ShutdownJob());
            }
        } catch (InterruptedException e) {
            this.logger.log(Level.DEBUG, e, "Shutdown interrupted for thread %s", this.getName());
        }
        }
    }

    protected void clearJobs() {
        if (jobQueue != null) {
            jobQueue.clear();
        }
    }

    public Runnable getSingletonJob() {
        return singletonJob;
    }

    public RuleServiceProvider getRuleServiceProvider() {
        return RSP;
    }

    public void schedule(Runnable job) throws InterruptedException {
        check();
        if (jobQueue != null) {
            jobQueue.put(job);
        }
    }

    public boolean isShuttingDown() {
        return shutdown || ((RuleServiceProviderImpl) RSP).isShuttingDown();
    }

    public boolean isRunning() {
        return jobInProgress;
    }

    @Override
    public void suspendResource() {
        suspendThread();
    }

    @Override
    public void resumeResource() {
        resumeThread();
    }

    public void suspendThread() {
        if(isSuspended()) {
            return;
        }
        synchronized (suspendLock) {
            suspend = true;
            if (!jobInProgress) {
                interrupt();
            }
        }
    }

    public void resumeThread() {
        if(!isSuspended()) {
            return;
        }
        synchronized (suspendLock) {
            suspend = false;
            suspendLock.notifyAll();
        }
    }

    protected void waitToResume() throws InterruptedException {
        synchronized (suspendLock) {
            this.logger.log(Level.INFO, "Suspending thread: %s", this.getName());
            while (suspend) {  // keep the loop to avoid spurious wakes
                suspendLock.wait();
            }
            this.logger.log(Level.INFO, "Resuming thread: %s", this.getName());
        }
    }

    protected void check() throws InterruptedException {
        if (suspend) {
            waitToResume();
        }
    }

    public boolean isSuspended() {
        synchronized (suspendLock) {
            return suspend;
        }
    }

    public int getRunMode() {
        return runMode;
    }

    public void executePrologue(){
        postActions.clear();
    }

    public void run() {
        // The return statements will end the loop if the methods return
        // but if they throw an exception the loop will repeat
        while(true) {
            try {
                postActions.clear();
                if (jobQueue != null) {
                    if (this.jobGroupManager != null) {
                        this.runMode= MODE_RUN_FROM_QUEUE;
                        run_from_queue_group();
                        return;
                    } else {
                        this.runMode = MODE_RUN_FROM_QUEUE;
                        run_from_queue();
                        return;
                    }
                } else if (singletonJob != null) {
                    this.runMode = MODE_RUN_REPEATING;
                    run_repeating();
                    return;
                }
            } catch (Throwable t) {
                this.logger.log(Level.ERROR, "Job Error on thread:" + getName(), t);
            }
        }
    }

    public void executeEpilogue() {
        ArrayList m_currentJobs = postActions;
        if (m_currentJobs.size() > 0) {
            for (int j = 0; j < m_currentJobs.size(); j++) {
                Runnable job = (Runnable) m_currentJobs.get(j);
                job.run();
            }
        }
        m_currentJobs.clear();
    }

    public void post(Runnable task) {
        ArrayList m_currentJobs = postActions;
        m_currentJobs.add(task);
    }

    @Override
    public void interrupt() {
        Thread callingThread = Thread.currentThread();
        if (getId() != callingThread.getId()) {
            super.interrupt();
        }
    }

    public void run_from_queue_group() {
        ArrayList jobs = new ArrayList(jobGroupManager.getMaxJobsInaGroup());

        Runnable job = null;
        Thread.currentThread().setContextClassLoader(RSP.getClassLoader());
        while (true) {
            try {
                jobInProgress = false;
                if (shutdown) {
                    ((RuleServiceProviderImpl) RSP).unregisterExecutableResource(this);
                    break;
                }
                //Check required both before and after call to ensureRSP()
                check();
                ((RuleServiceProviderImpl) getRuleServiceProvider()).ensureRSP();
                check();
                jobs.add( jobQueue.take() ) ;
                for (int i=0; i < jobGroupManager.getMaxJobsInaGroup()-1; i++) {
                    Object j = jobQueue.poll();
                    if (j == null) {
                        break;
                    }
                    jobs.add(j);
                }
                jobInProgress = true;
                jobGroupManager.execute(jobs);
                jobs.clear();

                //if (job instanceof ShutdownJob) {
                //    break;
                //}
            } catch (RuntimeException re) {
                final String storageMsg = "Storage is not configured";
                if (re.getMessage() != null && re.getMessage().contains(storageMsg)) {
                    suspendThread();
                } else {
                    throw re;
                }
            } catch (InterruptedException ex) {
                if (!suspend) {
                    break;
                }
            } finally {
                jobInProgress = false;
                incrementNumJobs();
            }
        }
    }

    public void run_from_queue() {
        Runnable job = null;
        Thread.currentThread().setContextClassLoader(RSP.getClassLoader());
        while (true) {
            try {
                jobInProgress = false;
                if (shutdown) {
                    ((RuleServiceProviderImpl) RSP).unregisterExecutableResource(this);
                    break;
                }
                // Check required both before and after call to ensureRSP()
                check();
                ((RuleServiceProviderImpl) getRuleServiceProvider()).ensureRSP();
                check();
                job = (Runnable) jobQueue.take();
                jobInProgress = true;
                execute(job);

                if (job instanceof ShutdownJob) {
                    break;
                }
            } catch (RuntimeException re) {
                final String storageMsg = "Storage is not configured";
                if (re.getMessage() != null && re.getMessage().contains(storageMsg)) {
                    suspendThread();
                } else {
                    throw re;
                }
            } catch (InterruptedException ex) {
                if (!suspend) {
                    break;
                }
            } finally {
                jobInProgress = false;
                incrementNumJobs();
            }
        }
    }

    public void run_repeating() {
        Thread.currentThread().setContextClassLoader(RSP.getClassLoader());
        while (true) {
            try {
                jobInProgress = false;
                if (shutdown) {
                    ((RuleServiceProviderImpl) RSP).unregisterExecutableResource(this);
                    return;
                }
                //Check required both before and after call to ensureRSP()
                check();
                ((RuleServiceProviderImpl) getRuleServiceProvider()).ensureRSP();
                check();
                Thread.sleep(interval);
                jobInProgress = true;
                execute(singletonJob);
            } catch (RuntimeException re) {
                final String storageMsg = "Storage is not configured";
                if (re.getMessage() != null && re.getMessage().contains(storageMsg)) {
                    suspendThread();
                } else {
                    throw re;
                }
            } catch (InterruptedException ex) {
                if (!suspend) {
                    break;
                }
            } finally {
                jobInProgress = false;
            }
        }
    }

    public void execute(Runnable job) {
        currentJob = job;
        try {
            executePrologue();
            job.run();
            executeEpilogue();
        } finally {
            currentJob = null;
        }
    }

    public Runnable getCurrentJob() {
        return currentJob;
    }

    public void uncaughtException(Thread t, Throwable e) {
        if (e instanceof OutOfMemoryError) {
            //((RuleServiceProviderImpl)RSP).onOutOfMemory(t,e);

            System.err.println("OutOfMemoryError from thread: " + t + ". Trace follows.");
            e.printStackTrace();
        } else {
            t.getThreadGroup().uncaughtException(t, e);
        }
    }

    public class ShutdownJob implements Runnable {
        public ShutdownJob() {
        }

        public void run() {
            ((RuleServiceProviderImpl) RSP).unregisterExecutableResource((BEManagedThread) Thread.currentThread());
        }
    }

    //-----------

    static final ReentrantReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock();

    static final ReentrantReadWriteLock.ReadLock READ_LOCK = READ_WRITE_LOCK.readLock();

    static final ReentrantReadWriteLock.WriteLock WRITE_LOCK = READ_WRITE_LOCK.writeLock();

    public static void acquireReadLock() {
        READ_LOCK.lock();
    }

    public static boolean tryAcquireReadLock() {
        return READ_LOCK.tryLock();
    }

    public static void releaseReadLock() {
        READ_LOCK.unlock();
    }

    public static void acquireWriteLock() {
        WRITE_LOCK.lock();
    }

    public static void releaseWriteLock() {
        WRITE_LOCK.unlock();
    }
}
