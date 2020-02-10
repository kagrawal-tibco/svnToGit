package com.tibco.cep.loadbalancer.impl.transport;

import com.tibco.cep.loadbalancer.client.core.ActualSink;
import com.tibco.cep.loadbalancer.impl.client.transport.tcp.ActualTcpSink;
import com.tibco.cep.loadbalancer.impl.server.transport.tcp.TcpSink;
import com.tibco.cep.loadbalancer.message.MessageCodec;
import com.tibco.cep.loadbalancer.server.core.sink.Sink;

/*
* Author: Ashwin Jayaprakash / Date: Jul 8, 2010 / Time: 5:11:39 PM
*/
public enum TransportConfig {
    tcp(TcpSink.class, ActualTcpSink.class, StreamMessageCodec.class);

    //------------

    private Class<? extends Sink> sinkClass;

    private Class<? extends ActualSink> actualSinkClass;

    private Class<? extends MessageCodec> messageCodecClass;

    TransportConfig(Class<? extends Sink> sinkClass,
                    Class<? extends ActualSink> actualSinkClass,
                    Class<? extends MessageCodec> messageCodecClass) {
        this.sinkClass = sinkClass;
        this.actualSinkClass = actualSinkClass;
        this.messageCodecClass = messageCodecClass;
    }

    public String getName() {
        return name();
    }

    public Class<? extends Sink> getSinkClass() {
        return sinkClass;
    }

    public Class<? extends ActualSink> getActualSinkClass() {
        return actualSinkClass;
    }

    public Class<? extends MessageCodec> getMessageCodecClass() {
        return messageCodecClass;
    }
}
