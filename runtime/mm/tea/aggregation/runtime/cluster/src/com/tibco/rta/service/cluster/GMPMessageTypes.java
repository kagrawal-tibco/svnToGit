package com.tibco.rta.service.cluster;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/4/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public enum GMPMessageTypes {

    /**
     * When a new member joins.
     */
    MEMBER_JOINED,

    /**
     * Quorum complete notification
     */
    QUORUM_NOTIFICATION,

    /**
     * WHen leader notifies that election is complete.
     */
    MEMBER_LEADER,

    /**
     * When a member leaves/killed.
     */
    MEMBER_LEFT,

    /**
     * Member publishes heartbeats upon coming up.
     */
    MEMBER_HEARTBEAT
}
