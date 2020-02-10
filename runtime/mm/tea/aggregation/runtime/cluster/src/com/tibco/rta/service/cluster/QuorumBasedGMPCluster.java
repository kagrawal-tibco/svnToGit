package com.tibco.rta.service.cluster;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.cluster.event.GMPClusterStateChangeEvent;
import com.tibco.rta.service.cluster.event.GMPEventRegistry;
import com.tibco.rta.service.cluster.impl.GMPClusterStateMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/7/13
 * Time: 11:20 AM
 * Represents a cluster of participating members in GMP.
 */
public class QuorumBasedGMPCluster implements GroupMembershipCluster {

    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_CLUSTER.getCategory());

    /**
     * The member(engine) on which this service is running.
     */
    protected GroupMember hostMember;

    /**
     * The member(engine) which is elected as primary. Will be same as this hostmember if election
     * is performed on same node for now.
     */
    protected GroupMember primaryMember;

    /**
     * Current state the cluster is in.
     */
//    protected volatile GMPClusterStates currentState;

    /**
     * Set of all members in the cluster.
     */
    protected List<GroupMember> allGroupMembers = new ArrayList<GroupMember>();

    /**
     * Quorum for GMP to complete.
     */
    private GroupQuorum groupQuorum;

    /**
     * The state machine associated with state changes.
     */
    protected GMPClusterStateMachine stateMachine;

    /**
     * General purpose lock.
     */
    protected final ReentrantLock mainLock = new ReentrantLock();

    /**
     * Notify interested parties about GMP events. This is meant to be used by external parties.
     */
    protected List<GroupMembershipListener> groupMembershipListeners = new ArrayList<GroupMembershipListener>();


    public void init(Properties configuration) {
        this.stateMachine = new GMPClusterStateMachine();

        int membershipQuorum = Integer.parseInt((String) ConfigProperty.GMP_STARTUP_MIN_QUORUM_COUNT.getValue(configuration));

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Configured quorum [%d]", membershipQuorum);
        }
        groupQuorum = new GroupQuorum(membershipQuorum);
        //Make state incomplete
        setCurrentState(GMPClusterStates.QUORUM_INCOMPLETE);
    }

    public void start() {
    }

    @SuppressWarnings("unchecked")
    public <G extends GroupMember> G getHostMember() {
        return (G) hostMember;
    }

    @SuppressWarnings("unchecked")
    public <G extends GroupMember> G getPrimaryMember() {
        return (G) primaryMember;
    }

    public void setPrimaryMember(GroupMember primaryMember) {
        //Check current state of cluster
        GMPClusterStates currentState = getCurrentState();
        //TODO Need to see if this situation could arise from other states also
        if (currentState == GMPClusterStates.LEADER_ELECTED) {
            //We null it out for resolving conflict
            if (this.primaryMember != null && primaryMember != null) {
                //If primary is already set and new primary is different we have conflict
                if (!this.primaryMember.equals(primaryMember)) {
                    if (LOGGER.isEnabledFor(Level.WARN)) {
                        LOGGER.log(Level.WARN, "Current primary member [%s]. New primary member [%s]", this.primaryMember, primaryMember);
                        LOGGER.log(Level.WARN, "Possible conflict with multiple primaries");
                    }
                    //Attempt to recover from this situation.
                    GMPEventRegistry.INSTANCE.onPrimaryConflict(primaryMember);
                }
            }
        }
        this.primaryMember = primaryMember;
    }

    public GMPClusterStates getCurrentState() {
        return stateMachine.getCurrentState();
    }

    public GMPClusterStates getPreviousState() {
        return stateMachine.getPreviousState();
    }

    public boolean isLeaderAbsent() {
        return getCurrentState() == GMPClusterStates.LEADER_ABSENT || getCurrentState() == GMPClusterStates.PRIMARY_CONFLICT;
    }

    public boolean isLeaderElected() {
        return getCurrentState() == GMPClusterStates.LEADER_ELECTED;
    }

    public boolean isLeaderLeft() {
        return getCurrentState() == GMPClusterStates.LEADER_LEFT;
    }

    public boolean isFenced() {
        return getCurrentState() == GMPClusterStates.FENCED;
    }

    public boolean isUnfenced() {
        return getCurrentState() == GMPClusterStates.UNFENCED;
    }

    public boolean isNetworkFailed() {
        return getCurrentState() == GMPClusterStates.NETWORK_FAILURE;
    }

    public boolean isQuorumIncomplete() {
        return getCurrentState() == GMPClusterStates.QUORUM_INCOMPLETE;
    }

    public void setCurrentState(GMPClusterStates currentState) {
        //Fire change event
        GMPClusterStateChangeEvent stateChangeEvent = new GMPClusterStateChangeEvent(this);

        stateChangeEvent.setState(currentState);

        stateMachine.onStateChange(stateChangeEvent);
    }

    @Override
    public <G extends GroupMember> void setMemberState(G member, GroupMemberStates state) {
        member.setState(state);
    }

    public <G extends GroupMember> void addMember(G member) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            if (!allGroupMembers.contains(member)) {

                allGroupMembers.add(member);
                //Increment quorum
                groupQuorum.increment();

                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Number of members in cluster [%d]", groupQuorum.current());
                }
                //Also notify interested listeners
                for (GroupMembershipListener groupMembershipListener : groupMembershipListeners) {
                    groupMembershipListener.memberJoined(member);
                }
                if (ensureQuorum()) {
                    //Set state to quorum complete
                    setCurrentState(GMPClusterStates.QUORUM_COMPLETE);
                }
            }
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    public boolean ensureQuorum() {
        //TODO expiry
        return groupQuorum.isQuorumPresent();
    }

    @SuppressWarnings("unchecked")
    public <G extends GroupMember> List<G> getAllGroupMembers() {
        return (List<G>) allGroupMembers;
    }

    public <G extends GroupMember> void removeMember(G member) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            allGroupMembers.remove(member);
            //Decrement quorum atomically.
            groupQuorum.decrement();
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


    public <L extends GroupMembershipListener> void addMembershipListener(L listener) {
        groupMembershipListeners.add(listener);
    }

    /**
     * Inform listeners about election complete.
     *
     * @param electedMember
     * @param <G>
     */
    public <G extends GroupMember> void notifyElectionComplete(G electedMember) {
        for (GroupMembershipListener groupMembershipListener : groupMembershipListeners) {
            groupMembershipListener.onPrimary(electedMember);
        }
    }

    /**
     * Inform listeners that this member is secondary.
     *
     * @param secondaryMember
     * @param <G>
     */
    public <G extends GroupMember> void notifySecondary(G secondaryMember) {
        for (GroupMembershipListener groupMembershipListener : groupMembershipListeners) {
            groupMembershipListener.onSecondary(secondaryMember);
        }
    }

    /**
     * Inform listeners about network failure.
     */
    public void notifyNetworkFailure() {
        for (GroupMembershipListener groupMembershipListener : groupMembershipListeners) {
            groupMembershipListener.networkFailed();
        }
    }

    /**
     * Inform listeners about network reestablishment.
     */
    public void notifyNetworkReestablish() {
        for (GroupMembershipListener groupMembershipListener : groupMembershipListeners) {
            groupMembershipListener.networkEstablished();
        }
    }

    /**
     * Inform listeners about this member's cluster service being fenced
     */
    public <G extends GroupMember> void notifyClusterFenced(G fencedMember) {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Cluster entering fenced state");
        }
        for (GroupMembershipListener groupMembershipListener : groupMembershipListeners) {
            groupMembershipListener.onFenced(fencedMember);
        }
    }

    /**
     * Inform listeners about this member's cluster service being unfenced
     */
    public <G extends GroupMember> void notifyClusterUnfenced(G fencedMember) {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Cluster entering unfenced state");
        }
        for (GroupMembershipListener groupMembershipListener : groupMembershipListeners) {
            groupMembershipListener.onUnfenced(fencedMember);
        }
    }

    /**
     * Inform listeners about this member's cluster service being unfenced
     */
    public <G extends GroupMember> void notifyPrimaryConflict(G conflictingMember) {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Cluster entering primary conflict state");
        }
        for (GroupMembershipListener groupMembershipListener : groupMembershipListeners) {
            groupMembershipListener.onConflict(conflictingMember);
        }
    }
}
