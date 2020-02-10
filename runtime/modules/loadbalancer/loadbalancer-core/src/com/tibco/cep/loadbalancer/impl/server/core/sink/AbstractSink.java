package com.tibco.cep.loadbalancer.impl.server.core.sink;

import static com.tibco.cep.util.Helper.$logger;

import java.util.Properties;
import java.util.logging.Logger;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.message.DistributionStrategy;
import com.tibco.cep.loadbalancer.message.MessageCodec;
import com.tibco.cep.loadbalancer.server.core.sink.Sink;
import com.tibco.cep.loadbalancer.server.core.sink.SinkState;
import com.tibco.cep.loadbalancer.server.core.sink.SinkStateSnapshot;

/*
* Author: Ashwin Jayaprakash / Date: Mar 18, 2010 / Time: 3:31:39 PM
*/
public abstract class AbstractSink implements Sink {
    protected Id id;

    protected Id sourceId;

    protected MessageCodec messageCodec;

    protected DistributionStrategy distributionStrategy;

    protected DefaultSinkStatus sinkStatus;

    protected Logger logger;

    protected ResourceProvider resourceProvider;

    protected Properties properties;

    protected AbstractSink() {
        this.sinkStatus =
                new DefaultSinkStatus(new DefaultSinkStateSnapshot(System.currentTimeMillis(), SinkState.off));
    }

    protected AbstractSink(Id id) {
        this.id = id;

        this.sinkStatus =
                new DefaultSinkStatus(new DefaultSinkStateSnapshot(System.currentTimeMillis(), SinkState.off));
    }

    @Override
    public void setId(Id id) {
        this.id = id;
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public void setSourceId(Id sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public Id getSourceId() {
        return sourceId;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public void setResourceProvider(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;

        this.logger = $logger(resourceProvider, getClass());
    }

    @Override
    public void setCodec(MessageCodec messageCodec) {
        this.messageCodec = messageCodec;
    }

    public void setDistributionStrategy(DistributionStrategy distributionStrategy) {
        this.distributionStrategy = distributionStrategy;
    }

    @Override
    public DistributionStrategy getDistributionStrategy() {
        return distributionStrategy;
    }

    @Override
    public void setState(SinkStateSnapshot stateSnapshot) {
        sinkStatus.setStateSnapshot(stateSnapshot);
    }

    @Override
    public DefaultSinkStatus getSinkStatus() {
        return sinkStatus;
    }

    //---------------

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "id=" + id + '}';
    }
}
