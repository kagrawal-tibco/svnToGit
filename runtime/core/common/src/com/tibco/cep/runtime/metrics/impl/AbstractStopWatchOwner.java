package com.tibco.cep.runtime.metrics.impl;

import com.tibco.cep.runtime.metrics.StopWatch;
import com.tibco.cep.runtime.metrics.StopWatchOwnerOption;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.SystemProperty;

import java.util.concurrent.ConcurrentHashMap;

/*
* Author: Ashwin Jayaprakash Date: Jan 22, 2009 Time: 2:44:41 PM
*/
public abstract class AbstractStopWatchOwner<W extends StopWatch, S extends Slab>
        implements InternalStopWatchOwner<W, S>, SlabProvider<S> {
    protected static final long RESOLUTION_MILLIS = Integer.getInteger(
            SystemProperty.METRIC_RESOLUTION.getPropertyName(), 5 * 1000);

    protected StopWatchCreator<W> creator;

    protected ConcurrentHashMap<Long, S> startTimeMillisAndSlabs;

    protected SlabDecorator newSlabDecorator;

    protected SlabWorker<S> slabWorker;

    protected FQName metricDefName;

    protected FQName metricName;

    protected StopWatchOwnerOption[] options;

    public AbstractStopWatchOwner(FQName metricDefName, FQName metricName) {
        this.metricDefName = metricDefName;
        this.metricName = metricName;
    }

    public FQName getMetricDefName() {
        return metricDefName;
    }

    public FQName getMetricName() {
        return metricName;
    }

    public StopWatchOwnerOption[] getOptions() {
        return options;
    }

    public void init(StopWatchCreator<W> watchCreator, SlabWorker<S> slabWorker,
                     StopWatchOwnerOption... options) {
        this.creator = watchCreator;
        this.newSlabDecorator = watchCreator.getSlabDecorator();
        this.slabWorker = slabWorker;
        this.options = options;
        this.startTimeMillisAndSlabs = new ConcurrentHashMap<Long, S>();
    }

    public void discard() {
        if (startTimeMillisAndSlabs != null) {
            startTimeMillisAndSlabs.clear();
        }
    }

    //------------

    public Class<? extends W> getStopWatchType() {
        return creator.getStopWatchType();
    }

    public W createStopWatch() {
        return creator.createStopWatch(metricName, this);
    }

    @SuppressWarnings({"SuspiciousMethodCalls"})
    public void discard(StopWatch watch) {
    }

    //------------

    public long getSlabResolutionMillis() {
        return RESOLUTION_MILLIS;
    }

    public S fetchSlab(long startTimeMillis) {
        long slabResolutionMillis = getSlabResolutionMillis();
        long diff = (startTimeMillis % slabResolutionMillis);
        Long roundedOffSlabStartTime = startTimeMillis - diff;

        S slab = startTimeMillisAndSlabs.get(roundedOffSlabStartTime);
        if (slab != null) {
            return slab;
        }

        //-------------

        long slabEndTimeMillis = roundedOffSlabStartTime + slabResolutionMillis;
        slab = slabWorker.createSlab(roundedOffSlabStartTime, slabEndTimeMillis);

        if (newSlabDecorator != null) {
            newSlabDecorator.decorate(slab);
        }

        S existingSlab = startTimeMillisAndSlabs.putIfAbsent(roundedOffSlabStartTime, slab);
        if (existingSlab != null) {
            slab = existingSlab;
        }
        else {
            onNewSlab(slab);
        }

        return slab;
    }

    protected abstract void onNewSlab(S slab);
}
