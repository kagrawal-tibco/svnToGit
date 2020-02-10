package com.tibco.cep.loadbalancer.impl.client.core;

import static com.tibco.cep.util.Helper.$logger;

import java.util.logging.Logger;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.client.core.ActualSink;
import com.tibco.cep.loadbalancer.client.core.ActualSinkStatus;
import com.tibco.cep.loadbalancer.client.core.MessageCustomizer;
import com.tibco.cep.loadbalancer.message.DistributionStrategy;

/*
* Author: Ashwin Jayaprakash / Date: Mar 23, 2010 / Time: 1:48:43 PM
*/
public abstract class AbstractActualSink implements ActualSink {
    protected Id id;

    protected Id sourceId;

    protected DistributionStrategy distributionStrategy;

    protected MessageCustomizer messageCustomizer;

    protected ResourceProvider resourceProvider;

    protected Logger logger;

    public AbstractActualSink() {
    }

    public AbstractActualSink(Id id) {
        this.id = id;
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public void setId(Id id) {
        this.id = id;
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
    public void setResourceProvider(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;

        this.logger = $logger(resourceProvider, getClass());
    }

    public void setDistributionStrategy(DistributionStrategy distributionStrategy) {
        this.distributionStrategy = distributionStrategy;
    }

    @Override
    public DistributionStrategy getDistributionStrategy() {
        return distributionStrategy;
    }

    @Override
    public void setMessageCustomizer(MessageCustomizer messageCustomizer) {
        this.messageCustomizer = messageCustomizer;
    }

    @Override
    public ActualSinkStatus getSinkStatus() {
        //todo
        return null;
    }
}
