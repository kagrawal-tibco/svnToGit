package com.tibco.rta.service.cluster.impl;

import com.tibco.rta.service.cluster.GMPMessage;
import com.tibco.rta.service.cluster.GMPMessageTypes;
import com.tibco.rta.service.cluster.GroupMemberStates;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 24/7/13
 * Time: 2:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class GMPHeartbeatMessage implements GMPMessage {

    /**
     * The member producing the message.
     */
    private String groupMemberUID;

    /**
     * Whether this member is leader while sending heartbeat.
     */
    private GroupMemberStates memberState;

    /**
     * Every heartbeat should send current cluster state for subsequent joinees to sync up.
     */
    private String clusterState;

    @Override
    public GMPMessageTypes getMessageType() {
        return GMPMessageTypes.MEMBER_HEARTBEAT;
    }

    @Override
    public void setOriginatingMemberUID(String memberUID) {
        this.groupMemberUID = memberUID;
    }

    @Override
    public String getOriginatingMemberUID() {
        return groupMemberUID;
    }

    public GroupMemberStates getMemberState() {
        return memberState;
    }

    public void setMemberState(GroupMemberStates memberState) {
        this.memberState = memberState;
    }

    public String getClusterState() {
        return clusterState;
    }

    public void setClusterState(String clusterState) {
        this.clusterState = clusterState;
    }

    @Override
    public byte[] toBytes() {
        //Right now only send UID
        return toString().getBytes();
    }

    @Override
    public String toString() {
        return groupMemberUID;
    }
}
