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
import com.tibco.rta.service.cluster.event.GMPLifecycleEvent;
import com.tibco.rta.service.cluster.event.GMPLifecycleEventListener;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/7/13
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultGMPLifecycleEventListener implements GMPLifecycleEventListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_CLUSTER.getCategory());

    /**
     *
     * @param memberJoinEvent
     */
    @Override
    public void onMemberJoin(GMPLifecycleEvent memberJoinEvent) {
        GroupMember groupMember = (GroupMember) memberJoinEvent.getSource();
        //Add newly joined member to cluster
        try {
            GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

            QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();

            cluster.addMember(groupMember);

            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Member [%s] joined group", groupMember.getUID());
            }
            if (groupMember.equals(cluster.getHostMember())) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Member [%s] waiting for leader notification.", groupMember.getUID());
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    /**
     *
     * @param memberLeftEvent
     */
    @Override
    public void onMemberLeft(GMPLifecycleEvent memberLeftEvent) {
        GroupMember groupMember = (GroupMember) memberLeftEvent.getSource();

        try {
            QuorumBasedGMPCluster cluster = ServiceProviderManager.getInstance().getGroupMembershipService().getCluster();

            //TODO can we do this cleaner way?
            if (cluster.isNetworkFailed()) {
                //Ignore
                return;
            }
            //Remove member from cluster
            cluster.removeMember(groupMember);

            if (groupMember.isLeader()) {
                //If member was leader then we need to re-elect.
                cluster.setCurrentState(GMPClusterStates.LEADER_LEFT);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    /**
     *
     * @param quorumCompleteEvent
     */
    @Override
    public void onQuorumComplete(GMPLifecycleEvent quorumCompleteEvent) {
        GroupMember[] quorumMembers = (GroupMember[]) quorumCompleteEvent.getSource();

        try {
            GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

            QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();
            //Add each member not equal to this member
            for (GroupMember groupMember : quorumMembers) {
                cluster.addMember(groupMember);
            }
            //Also set state to begin election
            cluster.setCurrentState(GMPClusterStates.BEGIN_ELECTION);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    /**
     *
     * @param leaderAbsenceExpiryEvent
     */
    @Override
    public void onLeaderAbsenceExpiry(GMPLifecycleEvent leaderAbsenceExpiryEvent) {
        GroupMember groupMember = (GroupMember) leaderAbsenceExpiryEvent.getSource();

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Leader absent expiry timed out on member [%s]. Proceeding for election process.", groupMember.getUID());
        }

        try {
            GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

            QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();
            //Also set state to begin election
            cluster.setCurrentState(GMPClusterStates.BEGIN_ELECTION);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    /**
     *
     * @param memberElectedEvent
     */
    @Override
    public void onMemberElect(GMPLifecycleEvent memberElectedEvent) {
        GroupMember groupMember = (GroupMember) memberElectedEvent.getSource();

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Member [%s] elected as leader or primary for group", groupMember.getUID());
        }
        //Set its state to primary whether this member is primary or not.
        groupMember.setState(GroupMemberStates.LEADER);
    }

    /**
     *
     * @param memberHeartbeatEvent
     */
    @Override
    public void onMemberHeartbeat(GMPLifecycleEvent memberHeartbeatEvent) {
        GroupMember member = (GroupMember) memberHeartbeatEvent.getSource();
        //Update its heartbeat instant
        if (member != null) {

            try {
                GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();
                //May need to add if newly discovered
                QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();

                cluster.addMember(member);
                //It is possible that this would be null because the quorum is not yet established.
                member.updateHeartbeat(System.currentTimeMillis());

                //If it is currently fenced and we start receiving heartbeats
                //then it has to be unfenced
                if (cluster.isFenced()) {
                    //If this member/service is fenced even though it may have
                    //been leader previously it should not notify listeners.
                    //Notify that we are moving out of fenced state.
                    cluster.setCurrentState(GMPClusterStates.UNFENCED);

                    return;
                }

                if (member.isLeader()) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Leader [%s] has been elected already for this cluster. Member state [%s]", member.getUID(), member.getState());
                    }
                    cluster.setPrimaryMember(member);
                    //Also set current state to elected member.
                    //DO not perform this transition if the cluster is in conflict state
                    if (cluster.getCurrentState() != GMPClusterStates.PRIMARY_CONFLICT) {
                        //The above transition is invalid
                        cluster.setCurrentState(GMPClusterStates.LEADER_ELECTED);
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            }
        }
    }

    @Override
    public void onAbsentSelfHeartbeat(GMPLifecycleEvent absentSelfHeartbeatEvent) {
        GroupMember member = (GroupMember) absentSelfHeartbeatEvent.getSource();
        //Update its heartbeat instant
        if (member != null) {
            try {
                GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

                QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();
                //Transition to fenced state
                cluster.setCurrentState(GMPClusterStates.FENCED);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            }
        }
    }

    /**
     *
     * @param networkFailureEvent
     */
    @Override
    public void onNetworkFailure(GMPLifecycleEvent networkFailureEvent) {
        try {
            GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

            QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();
            //Set state as such
            cluster.setCurrentState(GMPClusterStates.NETWORK_FAILURE);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    @Override
    public void onPrimaryConflict(GMPLifecycleEvent primaryConflictEvent) {
        try {
            GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

            QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();
            //Set state as such
            if (cluster.getCurrentState() != GMPClusterStates.LEADER_ABSENT) {
                //The above transition is invalid
                cluster.setCurrentState(GMPClusterStates.PRIMARY_CONFLICT);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }
}
