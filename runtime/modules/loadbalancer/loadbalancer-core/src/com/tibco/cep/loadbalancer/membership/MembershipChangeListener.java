package com.tibco.cep.loadbalancer.membership;

import java.util.Collection;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.loadbalancer.endpoint.Endpoint;
import com.tibco.cep.loadbalancer.endpoint.EndpointContainer;

/*
* Author: Ashwin Jayaprakash / Date: Mar 22, 2010 / Time: 2:01:05 PM
*/

public interface MembershipChangeListener<E extends Endpoint, C extends EndpointContainer<E>> {
    void start() throws Exception;

    void beginSession(Collection<? extends C> containers);

    /**
     * Should be called only the first time.
     *
     * @param container
     */
    void hasJoined(C container);

    /**
     * Just a hint. Indicates that this container still exists and is in the <b>same state/version</b> as seen first in
     * {@link #hasJoined(EndpointContainer)}.
     * <p/>
     * However, it's status is doubtful - probably because it has not been renewed on time.
     *
     * @param containerId
     */
    void isSuspect(Id containerId);

    /**
     * Just a hint. Indicates that this container still exists and is in the <b>same state/version</b> as seen first in
     * {@link #hasJoined(EndpointContainer)}. It could also mean that the container that was suspected previously is now
     * ok.
     *
     * @param containerId
     */
    void isOk(Id containerId);

    /**
     * When there is a clear indication that the container seen before in {@link #hasJoined(EndpointContainer)} has left
     * or crashed or shutdown.
     *
     * @param containerId
     */
    void hasLeft(Id containerId);

    void endSession();

    void stop() throws Exception;
}
