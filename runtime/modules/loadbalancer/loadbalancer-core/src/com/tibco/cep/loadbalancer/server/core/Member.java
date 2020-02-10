package com.tibco.cep.loadbalancer.server.core;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.loadbalancer.endpoint.EndpointContainer;
import com.tibco.cep.loadbalancer.server.core.sink.Sink;
import com.tibco.cep.util.annotation.Idempotent;

/*
* Author: Ashwin Jayaprakash / Date: Mar 16, 2010 / Time: 3:21:31 PM
*/

public interface Member extends EndpointContainer<Sink> {
    @Idempotent
    void start() throws LifecycleException;

    void stop() throws LifecycleException;

    MemberStatus getMemberStatus();
}
