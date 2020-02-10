package com.tibco.cep.loadbalancer.client.core;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.loadbalancer.endpoint.EndpointContainer;
import com.tibco.cep.util.annotation.Idempotent;

/*
* Author: Ashwin Jayaprakash / Date: Mar 22, 2010 / Time: 5:54:33 PM
*/

public interface ActualMember extends EndpointContainer<ActualSink> {
    void addSink(ActualSink sink);

    void removeSink(ActualSink sink);

    //---------------

    /**
     * Starts the {@link #getEndpoints() Sinks}.
     *
     * @throws LifecycleException
     */
    @Idempotent
    void start() throws LifecycleException;

    void stop() throws LifecycleException;

    ActualMemberStatus getMemberStatus();
}
