package com.tibco.rta.service.cluster;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/4/13
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public interface GroupMember {

    String getUID();

    String getName();

    GroupMemberStates getState();

    void setState(GroupMemberStates state);

    boolean isLeader();

    boolean isParticipant();

    boolean isFenced();

    void updateHeartbeat(long heartbeatTime);

    long getLastHeartbeatInstant();
}
