package com.tibco.cep.loadbalancer.impl.server.core;

import com.tibco.cep.loadbalancer.server.core.StateSnapshot;
import com.tibco.cep.util.annotation.Optional;

/*
* Author: Ashwin Jayaprakash / Date: Jul 12, 2010 / Time: 11:03:54 AM
*/
public class GenericStateSnapshot implements StateSnapshot {
    protected long at;

    protected
    @Optional
    Exception exception;

    public GenericStateSnapshot(long at) {
        this.at = at;
    }

    public GenericStateSnapshot(long at, Exception exception) {
        this.at = at;
        this.exception = exception;
    }

    public long getAt() {
        return at;
    }

    @Optional
    public Exception getException() {
        return exception;
    }
}
