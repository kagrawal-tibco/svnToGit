package com.tibco.cep.loadbalancer.server.core;

import java.util.Collection;
import java.util.Map;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.util.annotation.Idempotent;

/*
* Author: Ashwin Jayaprakash / Date: Mar 16, 2010 / Time: 3:05:14 PM
*/

public interface Kernel extends KernelMBean {
    void setResourceProvider(ResourceProvider resourceProvider);

    void setId(Id id);

    Id getId();

    //----------------

    /**
     * Keyed on {@link LoadBalancer#getSourceId()}.
     * <p/>
     * Calls {@link LoadBalancer#start()} internally.
     *
     * @param loadBalancer
     * @throws LifecycleException
     */
    void addLoadBalancer(LoadBalancer loadBalancer) throws LifecycleException;

    Collection<? extends Id> getSourceIds();

    Map<? extends Id, ? extends LoadBalancer> getSourceIdAndLoadBalancers();

    /**
     * Calls {@link LoadBalancer#stop()} internally.
     *
     * @param sourceId
     * @throws LifecycleException
     */
    void removeLoadBalancer(Id sourceId) throws LifecycleException;

    //----------------

    /**
     * Call {@link #addLoadBalancer(LoadBalancer)} before adding any members.
     *
     * @param member
     * @throws LifecycleException
     */
    void addMember(Member member) throws LifecycleException;

    Collection<? extends Member> getMembers();

    void memberIsSuspect(Id memberId) throws LifecycleException;

    void memberIsOk(Id memberId) throws LifecycleException;

    void removeMember(Id memberId) throws LifecycleException;

    //----------------

    /**
     * Starts the {@link #getSourceIdAndLoadBalancers()} and {@link #getMembers()}.
     *
     * @throws LifecycleException
     */
    @Idempotent
    void start() throws LifecycleException;

    void stop() throws LifecycleException;
}
