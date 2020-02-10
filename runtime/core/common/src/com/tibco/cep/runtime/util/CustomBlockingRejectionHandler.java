package com.tibco.cep.runtime.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/*
* Author: Ashwin Jayaprakash Date: May 11, 2009 Time: 7:13:04 PM
*/

/**
 * Custom implementation - JDK policies were not sufficient. See http://cs.oswego.edu/pipermail/concurrency-interest/2009-May/006095.html
 */
public class CustomBlockingRejectionHandler implements RejectedExecutionHandler {
    protected String threadPoolName;

    protected Logger logger;

    protected int giveUpAfterAttempts;

    /**
     * @param threadPoolName
     * @param logger
     * @param giveUpAfterAttempts The number of consecutive failures of a job after which the oldest
     *                            job will be discarded. Like {@link java.util.concurrent.ThreadPoolExecutor.AbortPolicy}.
     */
    public CustomBlockingRejectionHandler(String threadPoolName, Logger logger,
                                          int giveUpAfterAttempts) {
        this.threadPoolName = threadPoolName;
        this.logger = logger;
        this.giveUpAfterAttempts = giveUpAfterAttempts;
    }

    public CustomBlockingRejectionHandler(String threadPoolName, Logger logger) {
        this(threadPoolName, logger, Integer.MAX_VALUE);
    }

    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        int i = 1;
        for (; executor.isShutdown() == false && i <= giveUpAfterAttempts; i++) {
            BlockingQueue<Runnable> q = executor.getQueue();

            try {
                if (q.offer(r, 5, TimeUnit.SECONDS)) {
                    return;
                }

                if ((i % 25) == 0) {
                    String s = String.format(
                            "Thread pool [%s] is overloaded and the job submissions are facing" +
                                    " repeated failures. Job [%s] has already been submitted [%s]" +
                                    " times and is still being rejected.",
                            threadPoolName, r, i);
                    logger.log(Level.WARN,s);

                    //Make sure that the pool has at least 1 thread running.
                    executor.prestartCoreThread();
                }
            }
            catch (InterruptedException e) {
            }
        }

        throw new RejectedExecutionException(
                "Job [" + r + "] has already been submitted [" + i + "]" +
                        " times and is still being rejected. Giving up.");
    }
}
