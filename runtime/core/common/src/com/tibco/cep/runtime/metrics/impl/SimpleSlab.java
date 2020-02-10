package com.tibco.cep.runtime.metrics.impl;

import java.util.concurrent.atomic.AtomicLong;

/*
* Author: Ashwin Jayaprakash Date: Jan 23, 2009 Time: 1:01:04 PM
*/
public class SimpleSlab implements Slab {
    protected long slabStartTimeMillis;

    protected long slabEndTimeMillis;

    protected AtomicLong counter;

    protected volatile boolean alreadyProcessed;

    /**
     * @param slabStartTimeMillis Inclusive.
     * @param slabEndTimeMillis   Inclusive.
     */
    public SimpleSlab(long slabStartTimeMillis, long slabEndTimeMillis) {
        this.slabStartTimeMillis = slabStartTimeMillis;
        this.slabEndTimeMillis = slabEndTimeMillis;

        this.counter = new AtomicLong();
    }

    public long getSlabStartTimeMillis() {
        return slabStartTimeMillis;
    }

    public long getSlabEndTimeMillis() {
        return slabEndTimeMillis;
    }

    public long getCount() {
        return counter.get();
    }

    public void incrementCount() {
        counter.incrementAndGet();
    }

    public void addCount(long count) {
        counter.addAndGet(count);
    }

    public boolean isAlreadyProcessed() {
        return alreadyProcessed;
    }

    public void markAlreadyProcessed() {
        this.alreadyProcessed = true;
    }
}
