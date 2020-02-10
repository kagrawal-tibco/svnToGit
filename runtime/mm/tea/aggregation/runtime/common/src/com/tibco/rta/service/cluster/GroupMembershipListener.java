package com.tibco.rta.service.cluster;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/4/13
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public interface GroupMembershipListener {

    /**
     * Listen for member joined notification.
     *
     * @param member
     * @param <G>
     */
    <G extends GroupMember> void memberJoined(G member);

    /**
     * Listen for member left notification.
     *
     * @param member
     * @param <G>
     */
    <G extends GroupMember> void memberLeft(G member);

    /**
     * Listen for member elected notification.
     *
     * @param member
     * @param <G>
     */
    <G extends GroupMember> void onPrimary(G member);

    /**
     * Listen for member made secondary notification.
     *
     * @param member
     * @param <G>
     */
    <G extends GroupMember> void onSecondary(G member);

    /**
     * Listen for network failed notification.
     *
     * @param <G>
     */
    <G extends GroupMember> void networkFailed();

    /**
     * Listen for network established notification.
     *
     * @param <G>
     */
    <G extends GroupMember> void networkEstablished();

    /**
     * Listen for fenced notification.
     *
     * @param <G>
     */
    <G extends GroupMember> void onFenced(G member);

    /**
     * Listen for unfenced notification.
     *
     * @param <G>
     */
    <G extends GroupMember> void onUnfenced(G member);

    /**
     * Listen for primary conflict notification.
     *
     * @param <G>
     */
    <G extends GroupMember> void onConflict(G member);

    /**
     * @param groupMembers
     */
    void quorumComplete(GroupMember... groupMembers);
}
