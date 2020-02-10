package com.tibco.cep.runtime.metrics.impl;

import com.tibco.cep.runtime.metrics.StopWatch;
import com.tibco.cep.runtime.metrics.StopWatchOwner;
import com.tibco.cep.runtime.metrics.StopWatchOwnerOption;

/*
* Author: Ashwin Jayaprakash Date: Jan 26, 2009 Time: 7:15:15 PM
*/

/**
 * Internal service interface.
 */
public interface InternalStopWatchOwner<W extends StopWatch, S extends Slab>
        extends StopWatchOwner<W> {
    void init(StopWatchCreator<W> stopWatchCreator, SlabWorker<S> slabWorker,
              StopWatchOwnerOption... options);

    void discard();
}
