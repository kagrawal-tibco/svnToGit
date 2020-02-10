package com.tibco.rta.service.cluster.impl;

import com.tibco.rta.service.cluster.GMPMessage;
import com.tibco.rta.service.cluster.GMPMessageTypes;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/4/13
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class GMPQuorumNotificationMessage implements GMPMessage {

    /**
     * The member producing the message.
     */
    private String groupMemberUID;

    /**
     * All quorum members.
     */
    private String[] quorumMemberIds;

    @Override
    public GMPMessageTypes getMessageType() {
        return GMPMessageTypes.QUORUM_NOTIFICATION;
    }

    @Override
    public void setOriginatingMemberUID(String groupMemberUID) {
        this.groupMemberUID = groupMemberUID;
    }

    public void setQuorumMemberIds(String... memberIds) {
        this.quorumMemberIds = memberIds;
    }

    @Override
    public String getOriginatingMemberUID() {
        return groupMemberUID;
    }

    public String[] getQuorumMemberIds() {
        return quorumMemberIds;
    }

    @Override
    public byte[] toBytes() {
        String memberIds = toString();
        return memberIds.getBytes();
    }

    @Override
    public String toString() {
        return StringUtils.join(quorumMemberIds, ",");
    }
}
