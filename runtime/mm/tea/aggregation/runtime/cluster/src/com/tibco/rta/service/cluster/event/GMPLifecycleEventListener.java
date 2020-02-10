package com.tibco.rta.service.cluster.event;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 25/7/13
 * Time: 10:16 AM
 * To change this template use File | Settings | File Templates.
 */
public interface GMPLifecycleEventListener {

    /**
     * @param memberJoinEvent
     */
    void onMemberJoin(GMPLifecycleEvent memberJoinEvent);

    /**
     * @param memberLeftEvent
     */
    void onMemberLeft(GMPLifecycleEvent memberLeftEvent);

    /**
     * @param quorumCompleteEvent
     */
    void onQuorumComplete(GMPLifecycleEvent quorumCompleteEvent);

    /**
     * @param leaderAbsenceExpiryEvent
     */
    void onLeaderAbsenceExpiry(GMPLifecycleEvent leaderAbsenceExpiryEvent);

    /**
     * @param memberElectedEvent
     */
    void onMemberElect(GMPLifecycleEvent memberElectedEvent);

    /**
     * @param memberHeartbeatEvent
     */
    void onMemberHeartbeat(GMPLifecycleEvent memberHeartbeatEvent);

    /**
     * @param absentSelfHeartbeatEvent
     */
    void onAbsentSelfHeartbeat(GMPLifecycleEvent absentSelfHeartbeatEvent);


    /**
     * @param networkFailureEvent
     */
    void onNetworkFailure(GMPLifecycleEvent networkFailureEvent);


    /**
     * @param primaryConflictEvent
     */
    void onPrimaryConflict(GMPLifecycleEvent primaryConflictEvent);
}
