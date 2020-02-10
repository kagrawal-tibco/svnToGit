package com.tibco.rta.engine;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.cluster.GroupMember;
import com.tibco.rta.service.cluster.GroupMemberStates;
import com.tibco.rta.service.cluster.GroupMembershipService;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 10/4/13
 * Time: 11:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultGroupMember implements GroupMember {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());

    private String memberUID;

    private String name;

    private GroupMemberStates currentState;

    /**
     * Current heartbeat instant received for this group member.
     */
    private AtomicLong currentHeartbeatInstant;

    public DefaultGroupMember() {
        this(UUID.randomUUID().toString());
    }

    public DefaultGroupMember(String uid) {

        memberUID = uid;

        currentState = GroupMemberStates.PARTICIPANT;

        currentHeartbeatInstant = new AtomicLong(System.currentTimeMillis());
    }

    @Override
    public GroupMemberStates getState() {
        return currentState;
    }

    @Override
    public void setState(GroupMemberStates state) {
        this.currentState = state;
    }

    @Override
    public String getUID() {
        return memberUID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return memberUID;
    }

    @Override
    public boolean isLeader() {
        return currentState == GroupMemberStates.LEADER;
    }

    @Override
    public boolean isParticipant() {
        return currentState == GroupMemberStates.PARTICIPANT;
    }

    @Override
    public boolean isFenced() {
        return currentState == GroupMemberStates.FENCED;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GroupMember)) {
            return false;
        }
        GroupMember otherMember = (GroupMember) obj;

        return memberUID.equals(otherMember.getUID());
    }

    /**
     * Needs to be atomically updated
     *
     * @param heartbeatTime
     */
    public void updateHeartbeat(long heartbeatTime) {
//        try {
//            GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();
//            GroupMember hostMember = groupMembershipService.getCluster().getHostMember();
//
//            if (this.equals(hostMember)) {
//                boolean set = currentHeartbeatInstant.compareAndSet(currentHeartbeatInstant.get(), heartbeatTime);
//                if (LOGGER.isEnabledFor(Level.DEBUG)) {
//                    LOGGER.log(Level.DEBUG, "Heartbeat updated for member [%s] : [%s]", memberUID, set);
//                }
//            }
//        } catch (Exception e) {
//
//        }
        boolean set = currentHeartbeatInstant.compareAndSet(currentHeartbeatInstant.get(), heartbeatTime);
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Heartbeat updated for member [%s] : [%s]", memberUID, set);
        }
    }

    /**
     * @return
     */
    public long getLastHeartbeatInstant() {
        long heartbeatInstant = currentHeartbeatInstant.get();
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Heartbeat instant for member [%s] : [%d]", memberUID, heartbeatInstant);
        }
        return heartbeatInstant;
    }
}
