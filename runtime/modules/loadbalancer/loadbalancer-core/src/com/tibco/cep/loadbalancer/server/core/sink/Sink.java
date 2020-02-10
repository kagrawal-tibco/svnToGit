package com.tibco.cep.loadbalancer.server.core.sink;

import java.util.Properties;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.endpoint.Endpoint;
import com.tibco.cep.loadbalancer.message.DistributableMessage;
import com.tibco.cep.loadbalancer.message.DistributionStrategy;
import com.tibco.cep.loadbalancer.message.MessageCodec;
import com.tibco.cep.loadbalancer.message.MessageHandle;
import com.tibco.cep.loadbalancer.message.SpecialMessage;
import com.tibco.cep.util.annotation.Idempotent;
import com.tibco.cep.util.annotation.ThreadSafe;

/*
* Author: Ashwin Jayaprakash / Date: Mar 16, 2010 / Time: 2:54:46 PM
*/

public interface Sink extends Endpoint {
    void setId(Id id);

    void setSourceId(Id sourceId);

    void setResourceProvider(ResourceProvider resourceProvider);

    void setProperties(Properties properties);

    void setCodec(MessageCodec messageCodec);

    void setDistributionStrategy(DistributionStrategy distributionStrategy);

    //---------------

    @Idempotent
    void start() throws LifecycleException;

    /**
     * @param message
     * @return
     * @throws SinkException Due to a lower layer exception.
     */
    @ThreadSafe
    MessageHandle send(DistributableMessage message) throws SinkException;

    @ThreadSafe
    void send(SpecialMessage specialMessage) throws SinkException;

    /**
     * @param stateSnapshot This value will also get stored as the latest {@link SinkStatus#getStateSnapshot()}  in
     *                      {@link #getSinkStatus()}.
     */
    @ThreadSafe
    void setState(SinkStateSnapshot stateSnapshot);

    /**
     * The purpose of this method is to reset this sink into a definite state - {@link SinkState#on} or {@link
     * SinkState#off} and nothing in between. Can take corrective action if required.
     *
     * @return This value will also get stored as the latest {@link SinkStatus#getStateSnapshot()} in {@link
     *         #getSinkStatus()}.
     */
    SinkStateSnapshot checkState();

    void stop() throws LifecycleException;

    //---------------

    SinkStatus getSinkStatus();
}
