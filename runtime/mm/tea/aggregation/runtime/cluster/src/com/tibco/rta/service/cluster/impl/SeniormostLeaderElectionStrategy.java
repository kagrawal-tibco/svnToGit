package com.tibco.rta.service.cluster.impl;

import com.tibco.rta.service.cluster.GMPLeaderElectionStrategy;
import com.tibco.rta.service.cluster.GroupMember;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 10/4/13
 * Time: 2:21 PM
 * The one who was seniormost in the ring. Simple
 */
public class SeniormostLeaderElectionStrategy implements GMPLeaderElectionStrategy {

    /**
     * In this case the host member himself will be leader.
     */
    private GroupMember hostMember;

    public void setHostMember(GroupMember hostMember) {
        this.hostMember = hostMember;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <G extends GroupMember> G electLeader() {
        return (G) hostMember;
    }
}
