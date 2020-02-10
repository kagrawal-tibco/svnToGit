package com.tibco.cep.loadbalancer.impl.server.membership;

import java.util.Collection;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.loadbalancer.impl.server.core.DefaultKernel;
import com.tibco.cep.loadbalancer.membership.MembershipChangeListener;
import com.tibco.cep.loadbalancer.server.core.Member;
import com.tibco.cep.loadbalancer.server.core.sink.Sink;

/*
* Author: Ashwin Jayaprakash / Date: Mar 22, 2010 / Time: 2:38:55 PM
*/
public class ServerMembershipChangeListener implements MembershipChangeListener<Sink, Member> {
    protected DefaultKernel kernel;

    public ServerMembershipChangeListener() {
    }

    public DefaultKernel getKernel() {
        return kernel;
    }

    public void setKernel(DefaultKernel kernel) {
        this.kernel = kernel;
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void beginSession(Collection<? extends Member> containers) {
        for (Member member : containers) {
            kernel.addMember(member);
        }
    }

    @Override
    public void hasJoined(Member container) {
        kernel.addMember(container);
    }

    @Override
    public void isSuspect(Id containerId) {
        kernel.memberIsSuspect(containerId);
    }

    @Override
    public void isOk(Id containerId) {
        kernel.memberIsOk(containerId);
    }

    @Override
    public void hasLeft(Id containerId) {
        kernel.removeMember(containerId);
    }

    @Override
    public void endSession() {
    }

    @Override
    public void stop() throws Exception {
    }
}
