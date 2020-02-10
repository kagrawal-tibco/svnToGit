package com.tibco.cep.runtime.metrics.impl;

import com.tibco.cep.runtime.metrics.StopWatch;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 26, 2009 Time: 7:21:55 PM
*/
public interface StopWatchCreator<W extends StopWatch> {
    /**
     * @return Can be <code>null</code> if not required.
     */
    SlabDecorator getSlabDecorator();

    Class<? extends W> getStopWatchType();

    W createStopWatch(FQName ownerName, SlabProvider slabProvider);
}
