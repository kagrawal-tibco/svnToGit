package com.tibco.cep.loadbalancer.impl.client.transport.tcp;

import com.tibco.cep.loadbalancer.impl.client.transport.tcp.ActualTcpSink.InternalSessionWorker;
import com.tibco.cep.runtime.model.event.AckInterceptor;
import com.tibco.cep.util.annotation.Optional;

/*
* Author: Ashwin Jayaprakash / Date: Mar 25, 2010 / Time: 1:18:40 PM
*/

public class AckHandle extends ReceivedMessageHandle implements AckInterceptor {
    protected ActualTcpSink sink;

    protected volatile InternalSessionWorker currentSessionWorker;

    public AckHandle(Object uniqueIdOfMessage, Object originalSenderId, String receivedBySessionId, long receivedAt,
                     ActualTcpSink sink) {
        super(uniqueIdOfMessage, originalSenderId, receivedBySessionId, receivedAt);

        this.sink = sink;
    }

    public ActualTcpSink getSink() {
        return sink;
    }

    public InternalSessionWorker getCurrentSessionWorker() {
        return currentSessionWorker;
    }

    public void setCurrentSessionWorker(@Optional InternalSessionWorker currentSessionWorker) {
        //todo Need locking with acknowledge().

        this.currentSessionWorker = currentSessionWorker;
    }

    @Override
    public boolean acknowledge() {
        InternalSessionWorker sw = currentSessionWorker;

        if (sw == null) {
            sink.onAckReceivedButNotSent(originalSenderId, uniqueIdOfMessage, true);
        }
        else {
            sw.sendAck(this, true);
        }

        return true;
    }


    @Override
    public boolean rollBack() {
        final InternalSessionWorker sw = currentSessionWorker;

        if (sw == null) {
            this.sink.onAckReceivedButNotSent(originalSenderId, uniqueIdOfMessage, false);
        }
        else {
            sw.sendAck(this, false);
        }

        return true;
    }


}
