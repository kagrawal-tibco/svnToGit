package com.tibco.cep.query.stream.partition.purge;

import com.tibco.cep.query.stream.misc.Clock;

/*
 * Author: Ashwin Jayaprakash Date: Dec 3, 2007 Time: 3:48:52 PM
 */

/**
 * Works in 2 parts. First the {@link #getScheduleAt()} returns a time at which the purge-advice is
 * to be applied. When the time comes, the {@link #adviseNow(WindowStats)} is invoked and the result
 * is applied immediately.
 */
public abstract class ScheduledWindowPurgeAdvice implements WindowPurgeAdvice {
    public final long scheduleAt;

    public ScheduledWindowPurgeAdvice(long scheduleAt) {
        this.scheduleAt = Clock.roundToNearestSecondInMillis(scheduleAt);
    }

    public final long getScheduleAt() {
        return scheduleAt;
    }

    /**
     * @param stats
     * @return Cannot be <code>null</code>.
     */
    public abstract ImmediateWindowPurgeAdvice adviseNow(WindowStats stats);
}
