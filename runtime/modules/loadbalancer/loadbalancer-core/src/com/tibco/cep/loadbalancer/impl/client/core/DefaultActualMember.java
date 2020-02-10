package com.tibco.cep.loadbalancer.impl.client.core;

import static com.tibco.cep.util.Helper.$logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.client.core.ActualMember;
import com.tibco.cep.loadbalancer.client.core.ActualMemberStatus;
import com.tibco.cep.loadbalancer.client.core.ActualSink;
import com.tibco.cep.loadbalancer.client.core.ActualSinkStatus;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Mar 23, 2010 / Time: 1:48:10 PM
*/
@LogCategory("loadbalancer.core.client.member")
public class DefaultActualMember implements ActualMember {
    protected ConcurrentHashMap<Id, ActualSink> sourceIdToSinks;

    protected Id id;

    protected ResourceProvider resourceProvider;

    protected Logger logger;

    protected InternalActualMemberStatus actualMemberStatus;

    public DefaultActualMember() {
        this.sourceIdToSinks = new ConcurrentHashMap<Id, ActualSink>();
        this.actualMemberStatus = new InternalActualMemberStatus();
    }

    public DefaultActualMember(Id id) {
        this.id = id;
        this.sourceIdToSinks = new ConcurrentHashMap<Id, ActualSink>();
        this.actualMemberStatus = new InternalActualMemberStatus();
    }

    public ResourceProvider getResourceProvider() {
        return resourceProvider;
    }

    public void setResourceProvider(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public ActualMemberStatus getMemberStatus() {
        return actualMemberStatus;
    }

    public void addSink(ActualSink sink) {
        sourceIdToSinks.put(sink.getSourceId(), sink);
    }

    @Override
    public Collection<? extends ActualSink> getEndpoints() {
        return sourceIdToSinks.values();
    }

    public void removeSink(ActualSink sink) {
        sourceIdToSinks.remove(sink.getSourceId());
    }

    @Override
    public ActualSink getEndpointFor(Id sourceId) {
        return sourceIdToSinks.get(sourceId);
    }

    @Override
    public void start() throws LifecycleException {
        logger = (logger == null) ? $logger(resourceProvider, getClass()) : logger;

        for (ActualSink sink : sourceIdToSinks.values()) {
            sink.start();

            logger.log(Level.FINE, String.format("Started Sink [%s] in Member [%s] for Source [%s]",
                    sink.getId(), getId(), sink.getSourceId()));
        }

        logger.log(Level.INFO, String.format("Started Member [%s]", getId()));
    }

    @Override
    public void stop() throws LifecycleException {
        for (ActualSink sink : sourceIdToSinks.values()) {
            sink.stop();

            logger.log(Level.FINE, String.format("Stopped Sink [%s] in Member [%s] for Source [%s]",
                    sink.getId(), getId(), sink.getSourceId()));
        }

        logger.log(Level.INFO, String.format("Stopped Member [%s]", getId()));
    }

    //--------------

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultActualMember)) {
            return false;
        }

        DefaultActualMember that = (DefaultActualMember) o;

        if (!id.equals(that.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "id=" + id + '}';
    }

    protected class InternalActualMemberStatus implements ActualMemberStatus {
        @Override
        public long getRecentMembershipChangeAt() {
            long lastAt = System.currentTimeMillis() - /*30 mins*/(30 * 60 * 1000);

            for (ActualSink actualSink : sourceIdToSinks.values()) {
                ActualSinkStatus actualSinkStatus = actualSink.getSinkStatus();
                long l = actualSinkStatus.getRecentMembershipChangeAt();

                if (l > lastAt) {
                    lastAt = l;
                }
            }

            return lastAt;
        }

        @Override
        public Collection<Object> getLastKnownMemberIds() {
            HashSet<Object> memberIds = new HashSet<Object>();

            for (ActualSink actualSink : sourceIdToSinks.values()) {
                ActualSinkStatus actualSinkStatus = actualSink.getSinkStatus();
                memberIds.addAll(actualSinkStatus.getLastKnownMemberIds());
            }

            return memberIds;
        }
    }
}
