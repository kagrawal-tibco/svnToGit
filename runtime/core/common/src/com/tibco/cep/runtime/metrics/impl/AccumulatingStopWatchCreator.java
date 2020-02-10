package com.tibco.cep.runtime.metrics.impl;

import java.util.concurrent.atomic.AtomicLong;

import com.tibco.cep.runtime.metrics.CountingStopWatch;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Feb 13, 2009 Time: 6:17:41 PM
*/
public class AccumulatingStopWatchCreator
        implements StopWatchCreator<CountingStopWatch>, SlabDecorator {
    protected AtomicLong accumulatingCounter;

    public AccumulatingStopWatchCreator() {
        this.accumulatingCounter = new AtomicLong();
    }

    //------------

    public SlabDecorator getSlabDecorator() {
        return this;
    }

    public Class<? extends CountingStopWatch> getStopWatchType() {
        return SimpleAccumulatingStopWatch.class;
    }

    public CountingStopWatch createStopWatch(FQName ownerName, SlabProvider slabProvider) {
        return new SimpleAccumulatingStopWatch(ownerName, slabProvider, accumulatingCounter);
    }

    //------------

    public void decorate(Slab slab) {
        slab.addCount(accumulatingCounter.get());
    }
}
