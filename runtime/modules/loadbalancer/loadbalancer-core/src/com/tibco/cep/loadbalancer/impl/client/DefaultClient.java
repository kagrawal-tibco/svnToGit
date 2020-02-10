package com.tibco.cep.loadbalancer.impl.client;

import static com.tibco.cep.util.Helper.$logger;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.client.Client;
import com.tibco.cep.loadbalancer.impl.client.core.DefaultActualMember;
import com.tibco.cep.loadbalancer.membership.MembershipPublisher;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Mar 23, 2010 / Time: 3:01:42 PM
*/
@LogCategory("loadbalancer.core.client")
public class DefaultClient implements Client {
    protected DefaultActualMember member;

    protected MembershipPublisher membershipPublisher;

    protected ResourceProvider resourceProvider;

    protected Logger logger;

    protected Id resourceId;

    public DefaultClient() {
    }

    public ResourceProvider getResourceProvider() {
        return resourceProvider;
    }

    public void setResourceProvider(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    public Id getResourceId() {
        return resourceId;
    }

    public void setResourceId(Id resourceId) {
        this.resourceId = resourceId;
    }

    public void setMember(DefaultActualMember member) {
        this.member = member;
    }

    @Override
    public DefaultActualMember getMember() {
        return member;
    }

    public void setMembershipPublisher(MembershipPublisher membershipPublisher) {
        this.membershipPublisher = membershipPublisher;
    }

    @Override
    public MembershipPublisher getMembershipPublisher() {
        return membershipPublisher;
    }

    @Override
    public void start() throws LifecycleException {
        logger = (logger == null) ? $logger(resourceProvider, getClass()) : logger;

        logger.log(Level.INFO, String.format("Client [%s] starting", getResourceId()));

        try {
            member.start();

            membershipPublisher.publish(member);
        }
        catch (Exception e) {
            throw new LifecycleException(e);
        }

        logger.log(Level.INFO, String.format("Client [%s] started", getResourceId()));
    }

    @Override
    public void stop() throws LifecycleException {
        logger.log(Level.INFO, String.format("Client [%s] stopping", getResourceId()));

        try {
            membershipPublisher.unpublish();

            member.stop();
        }
        catch (Exception e) {
            throw new LifecycleException(e);
        }

        logger.log(Level.INFO, String.format("Client [%s] stopped", getResourceId()));
    }

    @Override
    public DefaultClient recover(ResourceProvider resourceProvider, Object... params) throws RecoveryException {
        return this;
    }
}
