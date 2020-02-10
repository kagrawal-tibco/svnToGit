package com.tibco.cep.loadbalancer.impl.server.core;

import static com.tibco.cep.util.Helper.$logger;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.server.core.Member;
import com.tibco.cep.loadbalancer.server.core.MemberStatus;
import com.tibco.cep.loadbalancer.server.core.sink.Sink;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Mar 17, 2010 / Time: 4:10:33 PM
*/

@LogCategory("loadbalancer.core.server.member")
public class DefaultMember implements Member {
    protected ConcurrentHashMap<Id, Sink> sourceIdToSinks;

    protected Id id;

    protected Logger logger;

    protected ResourceProvider resourceProvider;

    public DefaultMember() {
    }

    public DefaultMember(Id id) {
        this.id = id;
        this.sourceIdToSinks = new ConcurrentHashMap<Id, Sink>();
    }

    @Override
    public Id getId() {
        return id;
    }

    public ResourceProvider getResourceProvider() {
        return resourceProvider;
    }

    public void setResourceProvider(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    @Override
    public MemberStatus getMemberStatus() {
        //todo
        return null;
    }

    public void addSink(Sink sink) {
        sourceIdToSinks.put(sink.getSourceId(), sink);
    }

    @Override
    public Collection<? extends Sink> getEndpoints() {
        return sourceIdToSinks.values();
    }

    public void removeSink(Sink sink) {
        sourceIdToSinks.remove(sink.getSourceId());
    }

    @Override
    public Sink getEndpointFor(Id sourceId) {
        return sourceIdToSinks.get(sourceId);
    }

    @Override
    public void start() throws LifecycleException {
        logger = (logger == null) ? $logger(resourceProvider, getClass()) : logger;

        for (Sink sink : sourceIdToSinks.values()) {
            sink.start();

            logger.log(Level.FINE,
                    String.format("Started Sink [%s] in Member [%s] for Source [%s]", sink.getId(), getId(),
                            sink.getSourceId()));
        }

        logger.log(Level.FINE, String.format("Started Member [%s]", getId()));
    }

    @Override
    public void stop() throws LifecycleException {
        for (Sink sink : sourceIdToSinks.values()) {
            sink.stop();

            logger.log(Level.FINE,
                    String.format("Stopped Sink [%s] in Member [%s] for Source [%s]",
                            sink.getId(), getId(), sink.getSourceId()));
        }

        logger.log(Level.FINE, String.format("Stopped Member [%s]", getId()));
    }

    //--------------

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultMember)) {
            return false;
        }

        DefaultMember that = (DefaultMember) o;

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
}
