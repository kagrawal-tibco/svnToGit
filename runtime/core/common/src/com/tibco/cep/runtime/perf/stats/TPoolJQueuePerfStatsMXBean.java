package com.tibco.cep.runtime.perf.stats;

import javax.management.MBeanOperationInfo;

import com.tibco.cep.runtime.jmx.Description;
import com.tibco.cep.runtime.jmx.Impact;

@Description("Provides thread pool and job queue statistics")
public interface TPoolJQueuePerfStatsMXBean {

    @Description("Maximum number of threads in the thread pool")
    @Impact(MBeanOperationInfo.INFO)
    public int getMaximumThreads();

    @Description("Currently active threads in the thread pool")
    @Impact(MBeanOperationInfo.INFO)
    public int getActiveThreads();

    @Description("Maximum capacity of the job queue associated with the thread pool")
    @Impact(MBeanOperationInfo.INFO)
    public int getQueueCapacity();

    @Description("Current number of jobs lined up in the job queue associated with the thread pool")
    @Impact(MBeanOperationInfo.INFO)
    public int getQueueSize();

}
