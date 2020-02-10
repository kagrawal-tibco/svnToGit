package com.tibco.cep.runtime.metrics;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.runtime.management.MetricDef;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 23, 2009 Time: 6:32:07 PM
*/
public interface DataPipe {
    FQName getOwnerName();

    MetricDef getMetricDef();

    /**
     * @return Blocks until an element becomes available.
     * @throws InterruptedException
     */
    Data take() throws InterruptedException;

    int drainTo(Collection<? super Data> c, int maxElements);

    Data poll(long time, TimeUnit unit) throws InterruptedException;

    Data peek();
}
