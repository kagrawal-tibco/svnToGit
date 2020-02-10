package com.tibco.rta.service.cluster.event;

import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.cluster.GroupMember;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 25/7/13
 * Time: 10:23 AM
 * Common layer for sending all GMP lifecycle events which will then be distributed to all listeners.
 * Allows decoupling event source and event consumers.
 *
 * @see GMPLifecycleEventListener
 * @see GMPLifecycleEvent
 */
public class GMPEventRegistry {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_CLUSTER.getCategory());

    private GMPEventRegistry() {
    }

    public static final GMPEventRegistry INSTANCE = new GMPEventRegistry();

    private List<GMPLifecycleEventListener> lifecycleEventListeners = new ArrayList<GMPLifecycleEventListener>();

    /**
     * @param member
     * @param <G>
     */
    public <G extends GroupMember> void onMemberJoin(G member) {
        //Create member join event
        GMPLifecycleEvent memberJoinEvent = new GMPLifecycleEvent(member);

        for (GMPLifecycleEventListener eventListener : lifecycleEventListeners) {
            eventListener.onMemberJoin(memberJoinEvent);
        }
    }

    /**
     * @param member
     * @param <G>
     */
    public <G extends GroupMember> void onMemberLeft(G member) {
        //Create member left event
        GMPLifecycleEvent memberLeftEvent = new GMPLifecycleEvent(member);

        for (GMPLifecycleEventListener eventListener : lifecycleEventListeners) {
            eventListener.onMemberLeft(memberLeftEvent);
        }
    }

    /**
     * @param member
     * @param <G>
     */
    public <G extends GroupMember> void onLeaderAbsenceExpiry(G member) {
        //Create leader absence expiry event
        GMPLifecycleEvent leaderAbsenceExpiryEvent = new GMPLifecycleEvent(member);

        for (GMPLifecycleEventListener eventListener : lifecycleEventListeners) {
            eventListener.onLeaderAbsenceExpiry(leaderAbsenceExpiryEvent);
        }
    }

    /**
     * @param members
     * @param <G>
     */
    public <G extends GroupMember> void onQuorumComplete(G... members) {
        //Create quorum complete event
        GMPLifecycleEvent quorumCompleteEvent = new GMPLifecycleEvent(members);

        for (GMPLifecycleEventListener eventListener : lifecycleEventListeners) {
            eventListener.onQuorumComplete(quorumCompleteEvent);
        }
    }

    /**
     * @param member
     * @param <G>
     */
    public <G extends GroupMember> void onMemberElected(G member) {
        //Create member elected event
        GMPLifecycleEvent memberElectedEvent = new GMPLifecycleEvent(member);

        for (GMPLifecycleEventListener eventListener : lifecycleEventListeners) {
            eventListener.onMemberElect(memberElectedEvent);
        }
    }

    /**
     * @param member
     * @param <G>
     */
    public <G extends GroupMember> void onMemberHeartbeat(G member) {
        //Create member heartbeat event
        GMPLifecycleEvent memberHeartbeatEvent = new GMPLifecycleEvent(member);

        for (GMPLifecycleEventListener eventListener : lifecycleEventListeners) {
            eventListener.onMemberHeartbeat(memberHeartbeatEvent);
        }
    }

    /**
     * @param member - The new member causing conflict.
     * @param <G>
     */
    public <G extends GroupMember> void onPrimaryConflict(G member) {
        //Create primary conflict event
        GMPLifecycleEvent primaryConflictEvent = new GMPLifecycleEvent(member);

        for (GMPLifecycleEventListener eventListener : lifecycleEventListeners) {
            eventListener.onPrimaryConflict(primaryConflictEvent);
        }
    }

    /**
     * @param member
     * @param <G>
     */
    public <G extends GroupMember> void onAbsentSelfHeartbeat(G member) {
        //Create member heartbeat event
        GMPLifecycleEvent absentSelfHeartbeatEvent = new GMPLifecycleEvent(member);

        for (GMPLifecycleEventListener eventListener : lifecycleEventListeners) {
            eventListener.onAbsentSelfHeartbeat(absentSelfHeartbeatEvent);
        }
    }

    /**
     * @param networkURL - Could be JMS url
     */
    public void onNetworkFailure(String networkURL) {
        //Create member heartbeat event
        GMPLifecycleEvent networkFailureEvent = new GMPLifecycleEvent(networkURL);

        for (GMPLifecycleEventListener eventListener : lifecycleEventListeners) {
            eventListener.onNetworkFailure(networkFailureEvent);
        }
    }

    public <G extends GMPLifecycleEventListener> void registerLifecycleListener(G listener) {
        lifecycleEventListeners.add(listener);
    }
}
