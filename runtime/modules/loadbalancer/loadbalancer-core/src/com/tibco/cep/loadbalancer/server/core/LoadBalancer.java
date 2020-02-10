package com.tibco.cep.loadbalancer.server.core;

import java.util.Map;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.message.DistributableMessage;
import com.tibco.cep.loadbalancer.message.DistributableMessage.KnownHeader;
import com.tibco.cep.loadbalancer.message.MessageHandle;
import com.tibco.cep.loadbalancer.message.SpecialMessage;
import com.tibco.cep.loadbalancer.server.core.sink.SinkException;
import com.tibco.cep.util.annotation.Idempotent;
import com.tibco.cep.util.annotation.ThreadSafe;

/*
* Author: Ashwin Jayaprakash / Date: Mar 19, 2010 / Time: 1:06:20 PM
*/

public interface LoadBalancer extends LoadBalancerMBean {
    void setId(Id id);

    void setSourceId(Id sourceId);

    void setResourceProvider(ResourceProvider resourceProvider);

    Id getId();

    Id getSourceId();

    DistributionTable getDistributionTable();

    @Idempotent
    void start() throws LifecycleException;

    /**
     * The {@link KnownHeader}s may be set by the load balancer. See {@link DistributableMessage#getHeaderValue(KnownHeader)}. The
     * same message instance that has once been sent should not be reused.
     *
     * @param message
     * @return Handle for tracking the progress of the sent message. {@link MessageHandle#getSentAt()} will show a valid time.
     *         <p/>
     *         See {@link MessageHandle#getPostSendException()} if the message was assumed sent but then an error occured after
     *         that, like a transport error and so it was not sure if the message really went out or not.
     * @throws LoadBalancerException If the message was definitely not sent - i.e received an exception while sending or failed to
     *                               send.
     */
    @ThreadSafe
    MessageHandle send(DistributableMessage message) throws LoadBalancerException;

    /**
     * @param specialMessage
     * @return A map (can be empty) containing members and keys and optionally, exception as the value. If the send succeeds, then
     *         the value will be null.
     */
    @ThreadSafe
    Map<Member, SinkException> sendToAll(SpecialMessage specialMessage);

    @Idempotent
    void stop() throws LifecycleException;

    //---------------

    LoadBalancerStatus getLoadBalancerStatus();

    void printReport();
}
