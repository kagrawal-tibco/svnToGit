package com.tibco.cep.loadbalancer.impl.membership.file;

import com.tibco.cep.loadbalancer.endpoint.Endpoint;
import com.tibco.cep.loadbalancer.endpoint.EndpointContainer;
import com.tibco.cep.loadbalancer.impl.membership.MembershipInfo;

/*
* Author: Ashwin Jayaprakash / Date: Mar 24, 2010 / Time: 10:58:19 AM
*/

public class ScannedFileInfo<E extends Endpoint, C extends EndpointContainer<E>> {
    protected long lastModifiedTime;

    protected MembershipInfo<E, C> membershipInfo;

    public ScannedFileInfo(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public MembershipInfo<E, C> getMembershipInfo() {
        return membershipInfo;
    }

    public void setMembershipInfo(MembershipInfo<E, C> membershipInfo) {
        this.membershipInfo = membershipInfo;
    }
}
