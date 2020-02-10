package com.tibco.cep.loadbalancer.server;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.membership.MembershipChangeProvider;
import com.tibco.cep.loadbalancer.server.core.Kernel;
import com.tibco.cep.service.Service;
import com.tibco.cep.util.annotation.Idempotent;

/*
* Author: Ashwin Jayaprakash / Date: Mar 16, 2010 / Time: 4:02:07 PM
*/

public interface Server extends Service {
    ResourceProvider getResourceProvider();

    Kernel getKernel();

    MembershipChangeProvider getMembershipChangeProvider();

    /**
     * Starts the {@link #getKernel()} and {@link #getMembershipChangeProvider()}.
     *
     * @throws LifecycleException
     */
    @Idempotent
    void start() throws LifecycleException;

    void stop() throws LifecycleException;
}
