package com.tibco.rta.service.cluster;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 24/7/13
 * Time: 9:29 AM
 * To change this template use File | Settings | File Templates.
 */
public enum GroupMemberStates {

    /**
     * When a member joins the cluster it becomes a participant by default.
     */
    PARTICIPANT,

    /**
     * When a member is elected leader/primary for cluster it enters this state.
     */
    LEADER,

    /**
     * If a member gets fenced for any reason.
     */
    FENCED,

    /**
     * If member unfences.
     */
    UNFENCED
}
