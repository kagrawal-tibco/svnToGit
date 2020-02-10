package com.tibco.be.rms.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Nov 4, 2008
 * Time: 11:22:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class IOServiceThreadFactory implements ThreadFactory {

    private ThreadGroup threadGroup;

    private AtomicInteger threadNumber = new AtomicInteger(1);

    private AtomicInteger poolNumber = new AtomicInteger(1);

    private String threadName;

    private static final String NAME_PREFIX = "RMS-IOService-pool-";

    public IOServiceThreadFactory() {
        SecurityManager systemManager = System.getSecurityManager();
        threadGroup = (systemManager != null)? systemManager.getThreadGroup() :
                                 Thread.currentThread().getThreadGroup();
        threadName = NAME_PREFIX + poolNumber.getAndIncrement();
    }

    public IOServiceThreadFactory(final ThreadGroup threadGroup) {
        this.threadGroup = threadGroup;
        threadName = NAME_PREFIX + poolNumber.getAndIncrement();
    }
    /**
     * Constructs a new <tt>Thread</tt>.  Implementations may also initialize
     * priority, name, daemon status, <tt>ThreadGroup</tt>, etc.
     *
     * @param r a runnable to be executed by new thread instance
     * @return constructed thread
     */
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(threadGroup, r,
                                   threadName + "-" + threadNumber.getAndIncrement());
        //Similar to default implementation
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
