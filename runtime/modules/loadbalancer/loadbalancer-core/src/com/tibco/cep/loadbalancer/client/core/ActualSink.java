package com.tibco.cep.loadbalancer.client.core;

import java.util.Map;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.endpoint.Endpoint;
import com.tibco.cep.util.annotation.Idempotent;
import com.tibco.cep.util.annotation.Optional;

/*
* Author: Ashwin Jayaprakash / Date: Mar 22, 2010 / Time: 5:56:15 PM
*/

public interface ActualSink extends Endpoint {
    void setId(Id id);

    void setSourceId(Id id);

    void setResourceProvider(ResourceProvider resourceProvider);

    void setAdditionalProperties(Map<?, ?> properties);

    @Optional
    void setMessageCustomizer(MessageCustomizer messageCustomizer);

    @Idempotent
    void start() throws LifecycleException;

    void stop() throws LifecycleException;

    ActualSinkStatus getSinkStatus();
}
