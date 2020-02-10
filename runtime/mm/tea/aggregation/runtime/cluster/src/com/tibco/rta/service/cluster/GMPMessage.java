package com.tibco.rta.service.cluster;

import com.tibco.rta.service.cluster.GroupMember;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/4/13
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GMPMessage {


    /**
     * @return
     */
    GMPMessageTypes getMessageType();

    /**
     * Set member producing this message.
     *
     * @param memberUID
     */
    void setOriginatingMemberUID(String memberUID);

    /**
     * Get member producing this message.
     *
     */
    String getOriginatingMemberUID();

    /**
     * Serialize to bytes.
     *
     * @return
     */
    byte[] toBytes();

    /**
     * Serialize to bytes.
     *
     * @return
     */
    String toString();
}
