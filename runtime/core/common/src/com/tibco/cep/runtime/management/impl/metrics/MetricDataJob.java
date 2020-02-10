package com.tibco.cep.runtime.management.impl.metrics;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.metrics.DataPipe;
import com.tibco.cep.runtime.metrics.StopWatchOwner;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Feb 9, 2009 Time: 2:55:16 PM
*/
public class MetricDataJob {
    protected DataPipe dataPipe;

    protected FQName fqn;

    protected AtomicBoolean active;

    public MetricDataJob(StopWatchOwner watchOwner) {
        this.active = new AtomicBoolean(true);
        this.dataPipe = watchOwner.getDataPipe();
        this.fqn = watchOwner.getMetricName();
    }

    public DataPipe getDataPipe() {
        return dataPipe;
    }

    public FQName getFqn() {
        return fqn;
    }

    public boolean isActive() {
        return active.get();
    }

    public void deactivate() {
        active.set(false);
    }

    public Data pollNoWait() throws InterruptedException {
        return dataPipe.poll(0, TimeUnit.NANOSECONDS);
    }

    public int drainNoWait(Collection<? super Data> drainTarget, int maxElements) {
        return dataPipe.drainTo(drainTarget, maxElements);
    }
}
