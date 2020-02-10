package com.tibco.cep.runtime.perf.stats.impl;

import com.tibco.cep.runtime.perf.stats.DestinationPerfStatsMXBean;
import com.tibco.cep.runtime.perf.stats.DestinationStats;

public class DestinationPerfStatsMXBeanImpl implements DestinationPerfStatsMXBean {

    private DestinationStats stats;

    public DestinationPerfStatsMXBeanImpl(DestinationStats stats) {
        this.stats = stats;
    }

    @Override
    public long getTotalEventsReceived() {
        return stats.getTotalEventsReceived();
    }

    @Override
    public long getTotalEventsSent() {
        return stats.getTotalEventsSent();
    }

    @Override
    public double getEventsReceivedPerSecond() {
        return stats.getEventsReceivedPerSecond();
    }

    @Override
    public String getLastEventReceived() {
        return stats.getLastEventReceived();
    }
}
