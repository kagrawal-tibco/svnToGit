package com.tibco.cep.runtime.metrics.impl;

import com.tibco.cep.runtime.metrics.StopWatch;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Feb 13, 2009 Time: 6:17:41 PM
*/
public class SimpleStopWatchCreator implements StopWatchCreator<StopWatch> {
    public StopWatch createStopWatch(FQName ownerName, SlabProvider slabProvider) {
        return new SimpleStopWatch(ownerName, slabProvider);
    }

    public Class<? extends StopWatch> getStopWatchType() {
        return SimpleStopWatch.class;
    }

    public SlabDecorator getSlabDecorator() {
        return null;
    }
}
