package com.tibco.cep.runtime.metrics;

import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 22, 2009 Time: 3:03:08 PM
*/
public interface StopWatchOwner<W extends StopWatch> {
    /**
     * Also the name of this owner.
     *
     * @return
     */
    FQName getMetricName();

    StopWatchOwnerOption[] getOptions();

    DataPipe getDataPipe();

    Class<? extends W> getStopWatchType();

    W createStopWatch();

    void discard(StopWatch watch);
}
