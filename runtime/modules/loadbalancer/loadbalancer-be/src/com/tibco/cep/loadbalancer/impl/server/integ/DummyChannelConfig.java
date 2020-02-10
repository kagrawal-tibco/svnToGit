package com.tibco.cep.loadbalancer.impl.server.integ;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.designtime.model.service.channel.WebApplicationDescriptor;
import com.tibco.cep.runtime.channel.ChannelConfig;

/*
* Author: Ashwin Jayaprakash / Date: 4/21/11 / Time: 12:01 PM
*/
public class DummyChannelConfig implements ChannelConfig {
    protected String name;

    public DummyChannelConfig(String name) {
        this.name = name;
    }

    @Override
    public ConfigurationMethod getConfigurationMethod() {
        //todo
        return null;
    }

    @Override
    public Collection getDestinations() {
        //todo
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Properties getProperties() {
        //todo
        return null;
    }

    @Override
    public String getReferenceURI() {
        //todo
        return null;
    }

    @Override
    public String getType() {
        //todo
        return null;
    }

    @Override
    public String getServerType() {
        //todo
        return null;
    }

    @Override
    public String getURI() {
        //todo
        return null;
    }

    @Override
    public List<WebApplicationDescriptor> getWebApplicationDescriptors() {
        throw new UnsupportedOperationException("Operation not supported");
    }

    public boolean isActive() {
   	return true;
    }

}
