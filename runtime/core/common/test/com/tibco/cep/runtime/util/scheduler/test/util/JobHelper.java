/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.cep.runtime.util.scheduler.test.util;

import com.tibco.cep.runtime.util.scheduler.Job;
import com.tibco.cep.runtime.util.scheduler.impl.AbstractJobImpl;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public class JobHelper {
    private static final Logger logger = Logger.getLogger(JobHelper.class.getName());
    private static long threadId = 0;
    private static final AtomicBoolean stopEventSender = new AtomicBoolean(false);
    private static final ExecutorService eventSender = Executors.newFixedThreadPool(
            1, new ThreadFactory() {

        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "EventSender" + threadId++ + " ");
        }
    });

    private JobHelper() {
    }

    public static void closeEventSender() {
        stopEventSender.set(true);
        shutdownThreadpool(eventSender);
    }

    private static final void shutdownThreadpool(ExecutorService threadpool) {
        threadpool.shutdown();
        try {
            int retry = 0;
            while (retry++ < 10 && !threadpool.awaitTermination(
                    100, TimeUnit.MILLISECONDS)) {
                threadpool.shutdown();
            }
        } catch (InterruptedException ex) {
            threadpool.shutdownNow();
        }
    }

    public static void startEventSender(final List<Job> jobs,
            final long milliSecInterval, final long maxEvents) {
        try {
            eventSender.execute(new Runnable() {
                final Random random = new Random(System.currentTimeMillis());
                final byte[] lock = new byte[0];
                public void run() {
                    while (stopEventSender.get() != true && !Thread.interrupted()) {
                        Collections.shuffle(jobs,
                                new Random(System.currentTimeMillis()));
                        for(Job job : jobs) {
                            sendEvents(job);
                        }
                        long rand = Math.abs(new Random(
                                System.currentTimeMillis()).nextLong());
                        long timeOut = rand % milliSecInterval;
                        if(timeOut == 0) {
                            timeOut = 100;
                        }
                        // comment this when done
                        timeOut = milliSecInterval;
                        synchronized(lock) {                
                            try {
                                lock.wait(timeOut);
                            } catch (InterruptedException ex) {
                                // Ignore
                            }
                        }
                    }
                }

                private long getRandomCount() {
                    long rand = Math.abs(random.nextLong());
                    int count = (int) (rand % maxEvents);
                    if (count == 0) {
                        count = 1;
                    }
                    return count;
                }

                private void sendEvents(Job job) {
                    // Submit an input event to the job.
                    long noOfEvents = getRandomCount();
                    logger.info(job.getId().getValue() +
                            "\t" + noOfEvents);
                    for (int i = 0; i < noOfEvents; i++) {
                        ((AbstractJobImpl)job).queueInput(new Object());
                    }
                }
            });
        } catch (Exception e) {
            // Ignore.
        }
    }
}
