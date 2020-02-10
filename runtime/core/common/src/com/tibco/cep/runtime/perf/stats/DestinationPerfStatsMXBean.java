package com.tibco.cep.runtime.perf.stats;

import javax.management.MBeanOperationInfo;

import com.tibco.cep.runtime.jmx.Description;
import com.tibco.cep.runtime.jmx.Impact;

@Description("Provides statistics for a destination")
public interface DestinationPerfStatsMXBean {

    @Description("The total number of events received by the destination")
    @Impact(MBeanOperationInfo.INFO)
    public long getTotalEventsReceived();

    @Description("The total number of events sent by the destination")
    @Impact(MBeanOperationInfo.INFO)
    public long getTotalEventsSent();

    @Description("The events received per second by the destination")
    @Impact(MBeanOperationInfo.INFO)
    public double getEventsReceivedPerSecond();

    @Description("The time when the events received per second was caluclated")
    @Impact(MBeanOperationInfo.INFO)
    public String getLastEventReceived();

}
