package com.tibco.cep.loadbalancer.impl.membership;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.endpoint.Endpoint;
import com.tibco.cep.loadbalancer.endpoint.EndpointContainer;
import com.tibco.cep.util.annotation.Lazy;

/*
* Author: Ashwin Jayaprakash / Date: Jul 19, 2010 / Time: 3:55:48 PM
*/

public interface MembershipInfo<E extends Endpoint, C extends EndpointContainer<E>> {
    /**
     * @return Id of the container ({@link #createContainer(ResourceProvider)}) that is represented by the info object.
     */
    Id getContainerId();

    /**
     * @return A String that uniquely identifies this information from previous changes.
     */
    String getVersion();

    /**
     * @return Time at which this info was created.
     */
    long getTimestamp();

    @Lazy
    C createContainer(ResourceProvider resourceProvider);
}
