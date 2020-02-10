package com.tibco.rta.service.cluster;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 24/7/13
 * Time: 11:23 AM
 * The states in which the cluster will be.
 */
public enum GMPClusterStates {

    /**
     * When at least one member starts.
     */
    STARTED,

    /**
     * Immediately after cluster startup, it enters into absent state.
     */
    LEADER_ABSENT,

    /**
     * When cluster is started but quorum condition not met.
     */
    QUORUM_INCOMPLETE,

    /**
     * Upon quorum condition completion.
     */
    QUORUM_COMPLETE,

    /**
     * Upon beginning election process.
     */
    BEGIN_ELECTION,

    /**
     * When leader election is completed.
     */
    LEADER_ELECTED,

    /**
     * When leader is killed/goes away.
     */
    LEADER_LEFT,

    /**
     * Any known network failure.
     */
    NETWORK_FAILURE,

    /**
     * A member auto fences itself if it detects that there it is malfunctioning
     * or there is a network issue on its side but not necessarily a failure.
     */
    FENCED,

    /**
     * Member is unfenced when network condition restores.
     */
    UNFENCED,

    /**
     * WHen more than one member tries to become primary.
     */
    PRIMARY_CONFLICT

}
