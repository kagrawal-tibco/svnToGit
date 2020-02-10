package com.tibco.rta.service.cluster.impl;

import com.tibco.rta.service.cluster.GMPMessage;
import com.tibco.rta.service.cluster.GMPMessageTypes;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/4/13
 * Time: 2:50 PM
 * Basic subscription message.
 */
public class GMPSubscriptionMessage implements GMPMessage {

    /**
     * The member producing the message.
     */
    private String groupMemberUID;


    @Override
    public GMPMessageTypes getMessageType() {
        return GMPMessageTypes.MEMBER_JOINED;
    }

    @Override
    public void setOriginatingMemberUID(String groupMemberUID) {
        this.groupMemberUID = groupMemberUID;
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
