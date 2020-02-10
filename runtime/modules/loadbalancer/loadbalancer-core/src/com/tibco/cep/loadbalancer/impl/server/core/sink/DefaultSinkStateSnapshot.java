package com.tibco.cep.loadbalancer.impl.server.core.sink;

import com.tibco.cep.loadbalancer.impl.server.core.GenericStateSnapshot;
import com.tibco.cep.loadbalancer.server.core.sink.SinkState;
import com.tibco.cep.loadbalancer.server.core.sink.SinkStateSnapshot;

/*
* Author: Ashwin Jayaprakash / Date: Jul 12, 2010 / Time: 11:09:18 AM
*/
public class DefaultSinkStateSnapshot extends GenericStateSnapshot implements SinkStateSnapshot {
    protected SinkState sinkState;

    public DefaultSinkStateSnapshot(long at, SinkState sinkState) {
        super(at);
        this.sinkState = sinkState;
    }

    public DefaultSinkStateSnapshot(long at, Exception exception, SinkState sinkState) {
        super(at, exception);
        this.sinkState = sinkState;
    }

    public SinkState getSinkState() {
        return sinkState;
    }
}