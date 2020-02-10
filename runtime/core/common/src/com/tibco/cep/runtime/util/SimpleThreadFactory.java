package com.tibco.cep.runtime.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/*
* Author: Ashwin Jayaprakash Date: Mar 18, 2009 Time: 12:53:47 AM
*/
public class SimpleThreadFactory implements ThreadFactory {
    protected final ThreadGroup threadGroup;

    protected final String threadGroupName;

    protected final AtomicInteger threadCounter;

    public SimpleThreadFactory(String threadGroupName) {
        this.threadGroupName = threadGroupName;
        this.threadGroup = new ThreadGroup(threadGroupName);
        this.threadCounter = new AtomicInteger();
    }

    public Thread newThread(Runnable r) {
        String s = threadGroupName + ".Thread." + threadCounter.incrementAndGet();

        Thread t = new Thread(threadGroup, r, s);
        t.setDaemon(true);

        return t;
    }
}
