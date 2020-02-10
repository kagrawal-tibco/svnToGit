package com.tibco.cep.loadbalancer.impl.server.integ;

import java.util.Properties;

import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.EventSerializer;
import com.tibco.xml.data.primitive.ExpandedName;

/*
* Author: Ashwin Jayaprakash / Date: 4/21/11 / Time: 11:56 AM
*/
public class DummyDestinationConfig implements DestinationConfig{
    protected String name;

    public DummyDestinationConfig(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getURI() {
        return name;
    }

    @Override
    public ExpandedName getDefaultEventURI() {
        //todo
        return null;
    }

    @Override
    public EventSerializer getEventSerializer() {
        //todo
        return null;
    }

    @Override
    public Properties getProperties() {
        //todo
        return null;
    }

    @Override
    public ChannelConfig getChannelConfig() {
        //todo
        return null;
    }

    @Override
    public void setFilter(Event event) {
        //todo

    }

    @Override
    public Event getFilter() {
        //todo
        return null;
    }
}
