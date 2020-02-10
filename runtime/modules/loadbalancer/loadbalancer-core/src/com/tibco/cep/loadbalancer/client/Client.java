package com.tibco.cep.loadbalancer.client;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.loadbalancer.client.core.ActualMember;
import com.tibco.cep.loadbalancer.membership.MembershipPublisher;
import com.tibco.cep.service.Service;

/*
* Author: Ashwin Jayaprakash / Date: Mar 22, 2010 / Time: 5:48:27 PM
*/

public interface Client extends Service {
    ActualMember getMember();

    MembershipPublisher getMembershipPublisher();

    /**
     * Starts the {@link #getMember()} and {@link #getMembershipPublisher()}.
     */
    void start() throws LifecycleException;

    void stop() throws LifecycleException;
}
