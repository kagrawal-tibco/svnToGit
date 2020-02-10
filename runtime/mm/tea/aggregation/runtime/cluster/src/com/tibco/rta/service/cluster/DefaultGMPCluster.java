package com.tibco.rta.service.cluster;

import com.tibco.rta.engine.DefaultGroupMember;
import com.tibco.rta.log.Level;
import com.tibco.rta.service.cluster.impl.GMPClusterStateMachine;

import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 1/8/13
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultGMPCluster extends QuorumBasedGMPCluster {

    /**
     * Time at which cluster starts and enters LEADER_ABSENT state.
     */
    private long startupInstant;

    @Override
    public void init(Properties configuration) {
        this.hostMember = new DefaultGroupMember();
        //Add self to group
        addMember(hostMember);

        this.stateMachine = new GMPClusterStateMachine();
    }

    public void start() {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting group membership cluster");
        }
        //This is startup instant of this cluster.
        startupInstant = System.currentTimeMillis();
        //Make state leader absent.
        setCurrentState(GMPClusterStates.LEADER_ABSENT);
    }

    @Override
    public <G extends GroupMember> void addMember(G member) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            if (!allGroupMembers.contains(member)) {

                allGroupMembers.add(member);

                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Number of members in cluster [%d]", allGroupMembers.size());
                }
                //No need to ensure quorum.
                //Also notify interested listeners
                for (GroupMembershipListener groupMembershipListener : groupMembershipListeners) {
                    groupMembershipListener.memberJoined(member);
                }
            }
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    @Override
    public <G extends GroupMember> void removeMember(G member) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            allGroupMembers.remove(member);
            //Also notify interested listeners
            for (GroupMembershipListener groupMembershipListener : groupMembershipListeners) {
                groupMembershipListener.memberLeft(member);
            }
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    public long getStartupInstant() {
        return startupInstant;
    }

    @Override
    public <G extends GroupMember> void notifyClusterUnfenced(G fencedMember) {
        super.notifyClusterUnfenced(fencedMember);
        //Advance startup time to current.
        startupInstant = System.currentTimeMillis();
    }
}
