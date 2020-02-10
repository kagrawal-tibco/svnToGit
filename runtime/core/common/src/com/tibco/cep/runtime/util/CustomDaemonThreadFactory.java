package com.tibco.cep.runtime.util;

import java.util.concurrent.atomic.AtomicInteger;

import com.tibco.cep.runtime.session.BEManagedThread;
import com.tibco.cep.runtime.session.JobGroupManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/*
* Author: Ashwin Jayaprakash Date: Mar 12, 2009 Time: 7:11:07 PM
*/
public class CustomDaemonThreadFactory implements CustomThreadFactory<BEManagedThread> {
    protected final ThreadGroup threadGroup;

    protected final String threadGroupName;

    protected final AtomicInteger threadCounter;

    protected final RuleServiceProvider rsp;

    protected final JobGroupManager jobGroupManager;

    public CustomDaemonThreadFactory(String threadGroupName, RuleServiceProvider rsp) {
        this(threadGroupName, rsp, null);
    }

    /**
     * @param threadGroupName
     * @param rsp
     * @param jobGroupManager Can be <code>null</code>.
     */
    public CustomDaemonThreadFactory(String threadGroupName, RuleServiceProvider rsp,
                                     JobGroupManager jobGroupManager) {
        this.threadGroupName = threadGroupName;
        this.threadGroup = new ThreadGroup(threadGroupName);
        this.threadCounter = new AtomicInteger();
        this.rsp = rsp;
        this.jobGroupManager = jobGroupManager;
    }

    public BEManagedThread newThread(Runnable r) {
        String s = threadGroupName + ".Thread." + threadCounter.incrementAndGet();

        BEManagedThread t = new CustomBEManagedThread(threadGroup, r, s, rsp);
        t.setDaemon(true);

        return t;
    }
}
