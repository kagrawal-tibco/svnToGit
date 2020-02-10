package com.tibco.cep.loadbalancer.membership;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.endpoint.Endpoint;
import com.tibco.cep.loadbalancer.endpoint.EndpointContainer;

/*
* Author: Ashwin Jayaprakash / Date: Mar 22, 2010 / Time: 6:21:45 PM
*/

public interface MembershipPublisher<E extends Endpoint, C extends EndpointContainer<E>> {
    Id getId();

    void setId(Id id);

    void setResourceProvider(ResourceProvider resourceProvider) throws LifecycleException;

    void publish(C container) throws LifecycleException;

    void refreshPublication() throws LifecycleException;

    void unpublish() throws LifecycleException;
}
