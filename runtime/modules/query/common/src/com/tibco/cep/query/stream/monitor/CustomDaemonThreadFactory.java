package com.tibco.cep.query.stream.monitor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.tibco.cep.query.stream.core.Registry;

/*
* Author: Ashwin Jayaprakash Date: Apr 20, 2008 Time: 2:48:37 PM
*/
public class CustomDaemonThreadFactory implements ThreadFactory {
    protected final CustomThreadGroup threadGroup;

    protected final String name;

    protected final AtomicInteger counter;

    protected final int threadPriority;

    /**
     * Uses {@link Thread#NORM_PRIORITY}.
     *
     * @param threadGroup
     * @param name
     */
    public CustomDaemonThreadFactory(CustomThreadGroup threadGroup, String name) {
        this(threadGroup, name, Thread.NORM_PRIORITY);
    }

    /**
     * @param threadGroup
     * @param name
     * @param threadPriority
     */
    public CustomDaemonThreadFactory(CustomThreadGroup threadGroup, String name,
                                     int threadPriority) {
        this.threadGroup = threadGroup;
        this.name = name;
        this.counter = new AtomicInteger();
        this.threadPriority = threadPriority;
    }

    public CustomDaemonThread newThread(Runnable r) {
        String s = name + "-" + counter.incrementAndGet();

        FactoryDaemonThread thread = new FactoryDaemonThread(threadGroup, s, r);
        thread.setPriority(threadPriority);

        return thread;
    }

    protected static class FactoryDaemonThread extends CustomDaemonThread {
        private Runnable runnable;

        public FactoryDaemonThread(CustomThreadGroup threadGroup, String threadId,
                                   Runnable runnable) {
            super(threadGroup, new ResourceId(threadId));

            this.runnable = runnable;
        }

        @Override
        public void run() {
            try {
                runnable.run();
            }
            catch (Throwable t) {
                Logger logger = Registry.getInstance().getComponent(Logger.class);
                logger.log(Logger.LogLevel.ERROR, t);
            }
            finally {
                runCompleted();

                runnable = null;
            }
        }
    }
}
