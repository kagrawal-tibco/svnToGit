package com.tibco.cep.loadbalancer.membership;

import java.util.Collection;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.endpoint.Endpoint;
import com.tibco.cep.loadbalancer.endpoint.EndpointContainer;

/*
* Author: Ashwin Jayaprakash / Date: Mar 22, 2010 / Time: 1:50:52 PM
*/

public interface MembershipChangeProvider<E extends Endpoint, C extends EndpointContainer<E>> {
    Id getId();

    void setId(Id id);

    /**
     * Has to call {@link MembershipChangeListener#beginSession(Collection)}.
     *
     * @param resourceProvider
     * @param changeListener
     * @throws LifecycleException
     */
    void start(ResourceProvider resourceProvider, MembershipChangeListener<E, C> changeListener)
            throws LifecycleException;

    /**
     * Call this after {@link #start(ResourceProvider, MembershipChangeListener)}.
     *
     * @throws LifecycleException
     */
    void refresh() throws LifecycleException;

    /**
     * Has to call {@link MembershipChangeListener#endSession()}.
     *
     * @throws LifecycleException
     */
    void stop() throws LifecycleException;
}
