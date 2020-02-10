package com.tibco.cep.loadbalancer.impl.server.integ;

import java.util.Map;

import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.impl.AbstractChannel;
import com.tibco.cep.runtime.model.event.SimpleEvent;

/*
* Author: Ashwin Jayaprakash / Date: 4/21/11 / Time: 11:18 AM
*/
public class DummyChannel extends AbstractChannel {
    public DummyChannel(ChannelManager manager, String uri) {
        super(manager, uri, new DummyChannelConfig(uri));
    }

    @Override
    public void connect() throws Exception {
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    public void send(SimpleEvent event, String destinationURI, Map overrideData) throws Exception {
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public DummyDestination createDestination(DestinationConfig config) {
        return new DummyDestination(this, config.getName());
    }
}
