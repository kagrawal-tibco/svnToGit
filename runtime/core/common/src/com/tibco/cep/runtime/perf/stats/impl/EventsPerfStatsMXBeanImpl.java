package com.tibco.cep.runtime.perf.stats.impl;

import com.tibco.cep.runtime.perf.stats.EventsPerfStatsMXBean;
import com.tibco.cep.runtime.perf.stats.TimeUnitUtil;

public class EventsPerfStatsMXBeanImpl implements EventsPerfStatsMXBean {

    private EventsPerfStatsAggregator aggregator;

    public EventsPerfStatsMXBeanImpl(EventsPerfStatsAggregator aggregator) {
        this.aggregator = aggregator;
    }

    @Override
    public long getTotalEventsReceived() {
        return aggregator.getTotalEventCount();
    }

    @Override
    public long getEventsPerSecond() {
        return aggregator.getEventsPerSecond();
    }

    @Override
    public double getAverageEventProcessingTime() {
        return aggregator.getAverageEventProcessingTime();
    }

    @Override
    public long getTimerEventsFired() {
        return aggregator.getTotalTimerEventCount();
    }

    @Override
    public String getLastEventProcessedTime() {
        if (aggregator.getLastEventProcessedTime() == -1) {
            return "No EPS Data";
        }
        return TimeUnitUtil.convert(System.currentTimeMillis() - aggregator.getLastEventProcessedTime());
    }

}
