package com.tibco.cep.runtime.metrics.impl;

import java.util.concurrent.atomic.AtomicLong;

import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 26, 2009 Time: 6:36:41 PM
*/
public class SimpleAccumulatingStopWatch<S extends Slab> extends SimpleCountingStopWatch<S> {
    protected AtomicLong sharedCounter;

    public SimpleAccumulatingStopWatch(FQName ownerName, SlabProvider slabProvider,
                                       AtomicLong sharedCounter) {
        super(ownerName, slabProvider);

        this.sharedCounter = sharedCounter;
    }

    @Override
    protected void flushPrivateCounter() {
        sharedCounter.addAndGet(privateCounter);

        super.flushPrivateCounter();
    }
}