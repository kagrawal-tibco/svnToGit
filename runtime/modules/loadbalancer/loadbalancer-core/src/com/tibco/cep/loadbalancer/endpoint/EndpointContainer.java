package com.tibco.cep.loadbalancer.endpoint;

import java.util.Collection;

import com.tibco.cep.common.resource.Id;

/*
* Author: Ashwin Jayaprakash / Date: Jul 19, 2010 / Time: 2:18:13 PM
*/

/**
 * Common interface for Loadbalancer endpoints/destinations. 2 implementations can be expected - one for client side and
 * the other side at the server. The one at the server is usually a proxy.
 * <p/>
 * Generally, there is one Endpoint instance per process.
 *
 * @param <E>
 */
public interface EndpointContainer<E extends Endpoint> {
    Id getId();

    Collection<? extends E> getEndpoints();

    /**
     * @param sourceId
     * @return
     * @see Endpoint#getSourceId()
     */
    E getEndpointFor(Id sourceId);
}
