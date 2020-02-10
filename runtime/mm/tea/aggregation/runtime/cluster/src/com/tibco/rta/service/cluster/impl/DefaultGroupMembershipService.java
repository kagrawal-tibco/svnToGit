package com.tibco.rta.service.cluster.impl;

import com.tibco.rta.log.Level;
import com.tibco.rta.service.cluster.DefaultGMPCluster;
import com.tibco.rta.service.cluster.GMPClusterStates;
import com.tibco.rta.service.cluster.GroupMember;
import com.tibco.rta.service.cluster.GroupMemberStates;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 1/8/13
 * Time: 11:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultGroupMembershipService extends QuorumBasedGroupMembershipService {

    public DefaultGroupMembershipService() {
        //Use default cluster.
        this.cluster = new DefaultGMPCluster();
    }

    @Override
    public <G extends GroupMember> G electPrimary() {
        //Deviates from quorum based one.
        try {

            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Beginning election process");
            }
            //if you reach here condition is met and this will be met only in one engine
            //by virtue of topic.
            GroupMember electedMember = leaderElectionStrategy.electLeader();

            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Elected member [%s]", electedMember);
            }
            /**
             * Each member in the cluster will perform the election process.
             * So potentially everyone could send leader election message.
             * To avoid this, only the member who is the leader should send it.
             */
            if (electedMember != null && cluster.getHostMember().equals(electedMember)) {

                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Elected member [%s] sending election notification", electedMember);
                }
                //Set its state to leader
                cluster.setMemberState(cluster.getHostMember(), GroupMemberStates.LEADER);
                //Set it as primary.
                cluster.setPrimaryMember(electedMember);
                //Transition to leader elected state.
                cluster.setCurrentState(GMPClusterStates.LEADER_ELECTED);
                //Only then should a notification be sent. Else do nothing.
                //Send notification
                GMPLeaderElectedMessage leaderElectedMessage = new GMPLeaderElectedMessage();

                leaderElectedMessage.setOriginatingMemberUID(electedMember.getUID());

                messagePublisher.publishElectionCompletedMessage(leaderElectedMessage);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        } finally {
            if (mainLock.isHeldByCurrentThread()) {
                mainLock.unlock();
            }
        }
        return null;
    }

    @Override
    public synchronized void start() throws Exception {
        super.start();
        //Start cluster
        cluster.start();
    }

    @Override
    public <G extends GroupMember> void signalQuorumComplete(G... groupMembers) {
        throw new UnsupportedOperationException("This operation not supported");
    }


    @Override
    public void publishQuorumNotificationMessage() throws Exception {
        throw new UnsupportedOperationException("This operation not supported");
    }
}
