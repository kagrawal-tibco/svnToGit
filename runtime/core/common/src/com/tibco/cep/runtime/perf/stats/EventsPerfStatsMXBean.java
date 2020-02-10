package com.tibco.cep.runtime.perf.stats;

import javax.management.MBeanOperationInfo;

import com.tibco.cep.runtime.jmx.Description;
import com.tibco.cep.runtime.jmx.Impact;

@Description("Provides events performance statistics")
public interface EventsPerfStatsMXBean {

    @Description("Total number of events received")
    @Impact(MBeanOperationInfo.INFO)
    public long getTotalEventsReceived();

    @Description("Events Processed Per Second")
    @Impact(MBeanOperationInfo.INFO)
    public long getEventsPerSecond();

    @Description("The time when the event processed per second was calculated")
    @Impact(MBeanOperationInfo.INFO)
    public String getLastEventProcessedTime();

    @Description("The average processing time for an event (in msecs)")
    @Impact(MBeanOperationInfo.INFO)
    public double getAverageEventProcessingTime();

    @Description("Total number of timer events fired")
    @Impact(MBeanOperationInfo.INFO)
    public long getTimerEventsFired();

}
