package com.tibco.cep.util;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/*
* Author: Ashwin Jayaprakash / Date: Oct 26, 2009 / Time: 4:35:11 PM
*/

public class ThreadSafeInitializer<T> {
    private static final int initialAttemptsValue = 1;

    protected final ReentrantReadWriteLock lock;

    protected final WriteLock writerLock;

    protected final ReadLock readerLock;

    protected final int maxRetriesAcrossThreads;

    private volatile T cachedResult;

    private volatile int currentAttemptNumAcrossThreads;

    protected Logger logger;

    public ThreadSafeInitializer(int maxRetriesAcrossThreads, Logger logger) {
        this.lock = new ReentrantReadWriteLock();
        this.writerLock = this.lock.writeLock();
        this.readerLock = this.lock.readLock();

        this.maxRetriesAcrossThreads = maxRetriesAcrossThreads;
        this.currentAttemptNumAcrossThreads = initialAttemptsValue;

        this.logger = logger;
    }

    public int getMaxRetriesAcrossThreads() {
        return maxRetriesAcrossThreads;
    }

    /**
     * Return the results of a prior job's result that was executed from the same or different
     * thread. If the result is <code>null</code> then use the job provided to get the results.
     * <p/>
     * If the job fails, then only {@link #maxRetriesAcrossThreads} attempts will be made across all
     * threads.
     *
     * @param job
     * @return
     * @throws Exception If the results are <code>null</code> even after the specified attempts.
     */
    public T getCachedOrCallJob(Callable<T> job) throws Exception {
        T localFetchResult = cachedResult;
        if (localFetchResult != null) {
            logger.log(Level.DEBUG, getClass().getName(), "Fetch returning existing value", null);

            return localFetchResult;
        }

        //-------------

        for (; ;) {
            //Try to acquire a write-lock if no other thread has already acquired it.
            boolean writeLockSuccess = writerLock.tryLock();
            if (writeLockSuccess) {
                try {
                    logger.log(Level.DEBUG, getClass().getName(), "Acquired write-lock", null);

                    if (cachedResult == null) {
                        if (currentAttemptNumAcrossThreads <= maxRetriesAcrossThreads) {
                            callActual(job);
                        }
                        else {
                            giveUp();
                        }
                    }
                }
                finally {
                    writerLock.unlock();

                    logger.log(Level.DEBUG, getClass().getName(), "Released write-lock", null);
                }
            }

            //-------------

            //Otherwise, just wait until the write-lock-thread finishes.
            boolean readLockSuccess = readerLock.tryLock(1, TimeUnit.SECONDS);
            if (readLockSuccess) {
                try {
                    localFetchResult = cachedResult;
                    if (localFetchResult != null) {
                        logger.log(Level.DEBUG, getClass().getName(),
                                "Fetch returning existing value",
                                null);

                        return localFetchResult;
                    }
                    else if (currentAttemptNumAcrossThreads <= maxRetriesAcrossThreads) {
                        giveUp();
                    }
                }
                finally {
                    readerLock.unlock();
                }
            }
            else {
                logger.log(Level.WARN, getClass().getName(),
                        "Read-lock could not be acquired even after 1 sec", null);
            }
        }
    }

    private void callActual(Callable<T> job) throws Exception {
        try {
            cachedResult = job.call();

            logger.log(Level.DEBUG, getClass().getName(), "Fetch returned: " + cachedResult, null);
        }
        catch (Throwable t) {
            logger.log(Level.WARN, getClass().getName(),
                    "Fetch threw an error. [Attempt " + currentAttemptNumAcrossThreads + "]", t);
        }
        finally {
            currentAttemptNumAcrossThreads++;
        }
    }

    private void giveUp() throws Exception {
        throw new Exception("Fetch is still null even after attempting [" +
                (currentAttemptNumAcrossThreads - 1) + "] times. Giving up");
    }

    public void reset() {
        writerLock.lock();
        try {
            currentAttemptNumAcrossThreads = initialAttemptsValue;
            cachedResult = null;
        }
        finally {
            writerLock.unlock();
        }
    }
}
