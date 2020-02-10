package com.tibco.cep.runtime.metrics;

import java.util.Collection;

import com.tibco.cep.runtime.service.Service;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 23, 2009 Time: 11:08:48 AM
*/
public interface StopWatchManager extends Service {
    /**
     * @param metricDefName
     * @param metricName
     * @param type
     * @param options
     * @return
     * @throws RuntimeException If an owner already exists with the same name but is of a different
     *                          {@link StopWatchOwner#getStopWatchType() type} or the type is not
     *                          supported.
     */
    <W extends StopWatch> StopWatchOwner<W> fetchOwner(FQName metricDefName,
                                                       FQName metricName,
                                                       Class<W> type,
                                                       StopWatchOwnerOption... options);

    Collection<? extends StopWatchOwner> fetchOwners();

    //-----------

    void addListener(StopWatchOwnerLifecycleListener listener);

    void removeListener(StopWatchOwnerLifecycleListener listener);
}
