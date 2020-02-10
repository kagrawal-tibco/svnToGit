package com.tibco.cep.query.stream.monitor;

import java.util.concurrent.atomic.AtomicBoolean;

/*
 * Author: Ashwin Jayaprakash Date: Nov 28, 2007 Time: 6:38:43 PM
 */

/**
 * Thread-safe!
 */
public class QueryConfig {
    protected final AtomicBoolean tracingEnabled;

    public QueryConfig() {
        this.tracingEnabled = new AtomicBoolean(Flags.DEV_MODE);
    }

    public boolean isTracingEnabled() {
        return tracingEnabled.get();
    }

    public void setTracingEnabled(boolean enabled) {
        tracingEnabled.set(enabled);
    }
}
