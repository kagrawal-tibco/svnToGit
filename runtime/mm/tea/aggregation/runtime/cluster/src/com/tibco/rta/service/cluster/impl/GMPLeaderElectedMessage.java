package com.tibco.rta.service.cluster.impl;

import com.tibco.rta.service.cluster.GMPMessage;
import com.tibco.rta.service.cluster.GMPMessageTypes;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 10/4/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class GMPLeaderElectedMessage implements GMPMessage {

    /**
     * The member producing the message.
     */
    private String groupMemberUID;

    @Override
    public GMPMessageTypes getMessageType() {
        return GMPMessageTypes.MEMBER_LEADER;
    }

    @Override
    public void setOriginatingMemberUID(String memberUID) {
        this.groupMemberUID = memberUID;
    }

    @Override
    public String getOriginatingMemberUID() {
        return groupMemberUID;
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
