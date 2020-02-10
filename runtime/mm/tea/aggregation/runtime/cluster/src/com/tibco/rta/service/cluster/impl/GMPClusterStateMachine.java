package com.tibco.rta.service.cluster.impl;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.cluster.GMPClusterStates;
import com.tibco.rta.service.cluster.GroupMember;
import com.tibco.rta.service.cluster.GroupMemberStates;
import com.tibco.rta.service.cluster.GroupMembershipService;
import com.tibco.rta.service.cluster.QuorumBasedGMPCluster;
import com.tibco.rta.service.cluster.event.GMPClusterStateChangeEvent;
import com.tibco.rta.service.cluster.event.GMPStateChangeListener;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/7/13
 * Time: 11:22 AM
 * Simple cluster state machine.
 */
//TODO make the transitions smoother.
public class GMPClusterStateMachine implements GMPStateChangeListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_CLUSTER.getCategory());

    /**
     * Previous state it was in.
     */
    private volatile GMPClusterStates previousState;

    /**
     * Current state it is in.
     */
    private volatile GMPClusterStates currentState;

    /**
     * Main lock
     */
    private final ReentrantLock mainLock = new ReentrantLock();

    /**
     * Instantiate appropriate state using factory.
     */
    private GMPStateFactory stateFactory = new GMPStateFactory();

    public GMPClusterStateMachine() {
        //By default it is started.
        this.currentState = GMPClusterStates.STARTED;
    }

    @Override
    public void onStateChange(GMPClusterStateChangeEvent stateChangeEvent) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            GMPClusterStates changedState = stateChangeEvent.getState();

            if (changedState == currentState) {
                //Self transition would not execute action.
                return;
            }

            GMPState matchingState = stateFactory.getState(changedState);
            //Execute exit action of current state and entry of new state
            GMPState matchingCurrentState = stateFactory.getState(currentState);

            matchingCurrentState.executeExitAction();

            boolean flag = matchingState.executeEntryAction();
            //Only if the transition action was executed change the new state successfully.
            if (flag) {
                //Change current to previous.
                previousState = currentState;
                //Set this to current state
                currentState = changedState;

                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Current state for cluster [%s]", currentState);
                }
            }
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
    }

    public GMPClusterStates getPreviousState() {
        return previousState;
    }


    public GMPClusterStates getCurrentState() {
        return currentState;
    }

    private class GMPStateFactory {

        GMPState getState(GMPClusterStates clusterState) {

            switch (clusterState) {
                case STARTED :
                    //Ignore do nothing
                    break;

                case QUORUM_INCOMPLETE :
                    return new QuorumIncompleteState();

                case QUORUM_COMPLETE :
                    return new QuorumCompleteState();

                case LEADER_ABSENT :
                    return new LeaderAbsentState();

                case BEGIN_ELECTION :
                    return new BeginElectionState();

                case LEADER_ELECTED :
                    return new LeaderElectedState();

                case LEADER_LEFT :
                    return new LeaderLeftState();

                case NETWORK_FAILURE :
                    return new NetworkFailureState();

                case FENCED :
                    return new FencedState();

                case UNFENCED :
                    return new UnfencedState();

                case PRIMARY_CONFLICT :
                    return new PrimaryConflictState();

            }
            return new NoOpState();
        }
    }

    private interface GMPState {

        /**
         * Entry action when cluster enters this state.
         */
        boolean executeEntryAction();

        /**
         * Exit action when cluster leaves this state.
         */
        void executeExitAction();
    }

    /**
     * Do nothing state.
     */
    private class NoOpState implements GMPState {

        @Override
        public boolean executeEntryAction() {
            return true;
        }

        @Override
        public void executeExitAction() {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    private class QuorumIncompleteState implements GMPState {

        @Override
        public boolean executeEntryAction() {
            return true;
        }

        @Override
        public void executeExitAction() {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    private class LeaderAbsentState implements GMPState {

        @Override
        public boolean executeEntryAction() {
            boolean flag;

            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Executing Leader absent state action. Current state [%s]", currentState);
            }
            //Start member heartbeats as well as start liveness scanner.
            try {

                GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

                groupMembershipService.startMemberHeartbeats();

                groupMembershipService.startMemberLivenessScanner();
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            } finally {
                flag = true;
            }

            return flag;
        }

        @Override
        public void executeExitAction() {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    /**
     *
     */
    private class QuorumCompleteState implements GMPState {

        @Override
        public boolean executeEntryAction() {
            boolean flag = false;

            //TODO this should really come from transition condition.
            if (currentState == GMPClusterStates.QUORUM_INCOMPLETE || currentState == GMPClusterStates.NETWORK_FAILURE) {

                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Executing Quorum completion state action. Current state [%s]", currentState);
                }
                //Send quorum complete notification.
                try {

                    GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

                    groupMembershipService.publishQuorumNotificationMessage();
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, "", e);
                } finally {
                    flag = true;
                }
            }
            return flag;
        }

        @Override
        public void executeExitAction() {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    /**
     *
     */
    private class BeginElectionState implements GMPState {

        @Override
        public boolean executeEntryAction() {
            boolean flag = false;

            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Executing begin election state action. Current state [%s]", currentState);
            }
            //Begin election process.
            try {
                GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();
                //Start election.
                groupMembershipService.startElection();
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            }
            return flag;
        }

        @Override
        public void executeExitAction() {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    /**
     *
     */
    private class LeaderElectedState implements GMPState {

        @Override
        public boolean executeEntryAction() {
            boolean flag;

            try {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Executing leader election complete state action. Current state [%s]", currentState);
                }

                GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

                QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();
                //Make an attempt to start heartbeats if not started yet or stopped.
                groupMembershipService.startMemberHeartbeats();
                //Also start heartbeat scanner
                groupMembershipService.startMemberLivenessScanner();

                GroupMember hostMember = cluster.getHostMember();

                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Host member [%s], Primary Member [%s]", hostMember, cluster.getPrimaryMember());
                }

                // If the leader was elected after begin election or post
                // leader left notify
                if (hostMember.equals(cluster.getPrimaryMember())) {
                    // Notify listeners about election complete.
                    // Will be done only by the member on which callback is
                    // invoked.
                    cluster.notifyElectionComplete(hostMember);
                } else {
                    // Notify that this member is secondary
                    cluster.notifySecondary(cluster.getHostMember());
                }

            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            } finally {
                flag = true;
            }
            return flag;
        }

        @Override
        public void executeExitAction() {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    /**
     *
     */
    private class LeaderLeftState extends NoOpState {

        @Override
        public boolean executeEntryAction() {
            boolean flag;

            try {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Executing leader left state action. Current state [%s]", currentState);
                }

                GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();
                //TODO this should really come from transition condition.
                if (currentState == GMPClusterStates.LEADER_ELECTED) {
                    //Start re-election.
                    groupMembershipService.startElection();
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            } finally {
                flag = true;
            }
            return flag;
        }
    }

    /**
     *
     */
    private class NetworkFailureState implements GMPState {

        @Override
        public boolean executeEntryAction() {
            boolean flag;

            try {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Executing network failure state action. Current state [%s]", currentState);
                }

                GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();
                //Stop heartbeat scanners/senders
                groupMembershipService.stopMemberHeartbeats();

                groupMembershipService.stopMemberLivenessScanner();

                QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();
                //Also notify listeners
                cluster.notifyNetworkFailure();
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            } finally {
                flag = true;
            }
            return flag;
        }

        @Override
        public void executeExitAction() {

            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Executing network failure state exit action. Current state [%s]", currentState);
            }

            try {
                GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();
                //Update each member's timestamp
                for (GroupMember groupMember : groupMembershipService.getAllMembers()) {
                    //This will update all member heartbeats to current TS.
                    groupMember.updateHeartbeat(System.currentTimeMillis());
                }
                //Make an attempt to start heartbeats if not started yet or stopped.
                groupMembershipService.startMemberHeartbeats();
                //Also start heartbeat scanner
                groupMembershipService.startMemberLivenessScanner();

                QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();
                //Also notify listeners
                cluster.notifyNetworkReestablish();
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            }
        }
    }

    /**
     *
     */
    private class FencedState extends NoOpState {

        @Override
        public boolean executeEntryAction() {
            boolean flag;

            try {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Executing fenced state action. Current state [%s]", currentState);
                }

                GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();
                //Do not stop heartbeats or scanning for heartbeats because it may be a
                //red herring in terms of which component in the protocol is dead.
                GroupMember fencedMember = groupMembershipService.getCluster().getHostMember();
                //Also set the state for the member in the service
                GroupMember member = groupMembershipService.getOrAddMember(fencedMember.getUID());

                QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();
                //Also reset primary member status since we should lose own primary status.
                if (cluster.getHostMember().equals(cluster.getPrimaryMember())) {
                    if (LOGGER.isEnabledFor(Level.INFO)) {
                        LOGGER.log(Level.INFO, "Resetting previous self primary member status");
                    }
                    cluster.setPrimaryMember(null);
                }
                //Transition the member to fenced state too
                cluster.setMemberState(member, GroupMemberStates.FENCED);
                //Also notify listeners
                cluster.notifyClusterFenced(fencedMember);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            } finally {
                flag = true;
            }
            return flag;
        }
    }

    /**
     *
     */
    private class UnfencedState extends NoOpState {

        @Override
        public boolean executeEntryAction() {
            boolean flag;

            try {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Executing unfenced state entry action. Current state [%s]", currentState);
                }

                GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();
                //Do not stop heartbeats or scanning for heartbeats because it may be a
                //red herring in terms of which component in the protocol is dead.
                GroupMember fencedMember = groupMembershipService.getCluster().getHostMember();
                //Also set the state for the member in the service
                GroupMember member = groupMembershipService.getOrAddMember(fencedMember.getUID());
                //Not transitioning the member to unfenced state purposefully
                QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();
                //Also notify listeners
                cluster.notifyClusterUnfenced(fencedMember);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            } finally {
                flag = true;
            }
            return flag;
        }
    }

    private class PrimaryConflictState extends NoOpState {
        @Override
        public boolean executeEntryAction() {
            boolean flag;

            try {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Executing primary conflict state entry action. Current state [%s]", currentState);
                }

                GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

                QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();

                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Resetting previous primary for this cluster");
                }
                cluster.setPrimaryMember(null);
                //Also notify listeners
                //Right now pass null.
                //This is as good as bringing cluster into no leader state (absent).
                cluster.notifyPrimaryConflict(null);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            } finally {
                flag = true;
            }
            return flag;
        }
    }
}
