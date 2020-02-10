package com.tibco.rta.service.cluster;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/7/13
 * Time: 1:27 PM
 * Representation of a GMP based cluster.
 */
public interface GroupMembershipCluster {

    /**
     * Add new member to this cluster.
     * @param member
     * @param <G>
     */
    <G extends GroupMember> void addMember(G member);

    /**
     * Remove existing member from this cluster.
     * @param member
     * @param <G>
     */
    <G extends GroupMember> void removeMember(G member);

    /**
     * Get list of all members in group.
     * @param <G>
     * @return
     */
    <G extends GroupMember> List<G> getAllGroupMembers();

    /**
     * Get the host member running this service
     * There will be only one at a time.
     *
     * @param <G>
     * @return
     */
    <G extends GroupMember> G getHostMember();

    /**
     * Get the primary SPM engine in this group.
     * There will be only one at a time.
     *
     * @param <G>
     * @return
     */
    <G extends GroupMember> G getPrimaryMember();

    /**
     * Set state of member.
     * @param member
     * @param state
     * @param <G>
     */
    <G extends GroupMember> void setMemberState(G member, GroupMemberStates state);
}
