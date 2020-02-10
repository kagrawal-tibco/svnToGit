package com.tibco.cep.loadbalancer.impl.client.transport.mem;

import java.util.Map;
import java.util.Properties;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.loadbalancer.impl.client.core.AbstractActualSink;

/*
* Author: Ashwin Jayaprakash / Date: Mar 23, 2010 / Time: 3:39:26 PM
*/
public class ActualMemSink extends AbstractActualSink {
    public ActualMemSink() {
    }

    public ActualMemSink(Id id) {
        super(id);
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public void setAdditionalProperties(Map<?, ?> properties) {
    }

    @Override
    public void start() throws LifecycleException {
    }

    @Override
    public void stop() {
    }
}
