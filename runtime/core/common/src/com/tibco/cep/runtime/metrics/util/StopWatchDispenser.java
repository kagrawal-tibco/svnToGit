package com.tibco.cep.runtime.metrics.util;

import com.tibco.cep.runtime.metrics.StopWatch;
import com.tibco.cep.runtime.metrics.StopWatchOwner;

/*
* Author: Ashwin Jayaprakash Date: Feb 13, 2009 Time: 4:24:15 PM
*/
public class StopWatchDispenser<W extends StopWatch> {
    protected StopWatchOwner<W> watchOwner;

    protected ThreadLocal<W> threadLocalWatches;

    protected W sharedWatch;

    public StopWatchDispenser(StopWatchOwner<W> watchOwner) {
        this(watchOwner, true);
    }

    public StopWatchDispenser(StopWatchOwner<W> watchOwner, boolean threadLocal) {
        this.watchOwner = watchOwner;
        if (threadLocal) {
            this.threadLocalWatches = new ThreadLocal<W>();
        }
        else {
            this.sharedWatch = watchOwner.createStopWatch();
        }
    }

    public StopWatchOwner<W> getWatchOwner() {
        return watchOwner;
    }

    public W createOrGetStopWatchForThread() {
        W watch = (threadLocalWatches == null) ? sharedWatch : threadLocalWatches.get();
        if (watch != null) {
            return watch;
        }

        watch = watchOwner.createStopWatch();
        threadLocalWatches.set(watch);

        return watch;
    }
}
