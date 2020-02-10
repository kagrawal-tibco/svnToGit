package com.tibco.cep.loadbalancer.impl.server.transport.mem;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.loadbalancer.impl.server.core.sink.AbstractSink;
import com.tibco.cep.loadbalancer.impl.server.core.sink.DefaultSinkStateSnapshot;
import com.tibco.cep.loadbalancer.message.DistributableMessage;
import com.tibco.cep.loadbalancer.message.MessageHandle;
import com.tibco.cep.loadbalancer.message.SpecialMessage;
import com.tibco.cep.loadbalancer.server.core.sink.SinkException;
import com.tibco.cep.loadbalancer.server.core.sink.SinkState;
import com.tibco.cep.loadbalancer.server.core.sink.SinkStateSnapshot;

/*
* Author: Ashwin Jayaprakash / Date: Mar 18, 2010 / Time: 3:42:07 PM
*/
public class MemSink extends AbstractSink {
    public MemSink() {
    }

    public MemSink(Id id) {
        super(id);
    }

    @Override
    public void start() throws LifecycleException {
    }

    @Override
    public MessageHandle send(DistributableMessage message) throws SinkException {
        return null;
    }

    @Override
    public void send(SpecialMessage specialMessage) throws SinkException {
    }

    @Override
    public SinkStateSnapshot checkState() {
        return new DefaultSinkStateSnapshot(System.currentTimeMillis(), SinkState.on);
    }

    @Override
    public void stop() throws LifecycleException {
    }
}
