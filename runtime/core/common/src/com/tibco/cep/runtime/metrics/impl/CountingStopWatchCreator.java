package com.tibco.cep.runtime.metrics.impl;

import com.tibco.cep.runtime.metrics.CountingStopWatch;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Feb 13, 2009 Time: 6:17:41 PM
*/
public class CountingStopWatchCreator implements StopWatchCreator<CountingStopWatch> {
    public CountingStopWatch createStopWatch(FQName ownerName, SlabProvider slabProvider) {
        return new SimpleCountingStopWatch(ownerName, slabProvider);
    }

    public Class<? extends CountingStopWatch> getStopWatchType() {
        return SimpleCountingStopWatch.class;
    }

    public SlabDecorator getSlabDecorator() {
        return null;
    }
}
