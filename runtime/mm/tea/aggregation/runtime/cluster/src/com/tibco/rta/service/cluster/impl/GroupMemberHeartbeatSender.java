package com.tibco.rta.service.cluster.impl;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.cluster.GMPClusterStates;
import com.tibco.rta.service.cluster.QuorumBasedGMPCluster;
import com.tibco.rta.service.cluster.GMPMessagePublisher;
import com.tibco.rta.service.cluster.GroupMembershipService;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 24/7/13
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class GroupMemberHeartbeatSender implements Runnable {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_CLUSTER.getCategory());

    @Override
    public void run() {
        try {
            GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

            if (groupMembershipService instanceof QuorumBasedGroupMembershipService) {

                GMPMessagePublisher messagePublisher = ((QuorumBasedGroupMembershipService) groupMembershipService).getMessagePublisher();

                GMPHeartbeatMessage heartbeatMessage = new GMPHeartbeatMessage();

                QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();

                heartbeatMessage.setOriginatingMemberUID(cluster.getHostMember().getUID());

                heartbeatMessage.setMemberState(cluster.getHostMember().getState());

                heartbeatMessage.setClusterState(cluster.getCurrentState().name());

                messagePublisher.publishHeartbeatMessage(heartbeatMessage);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Exception in heartbeat sender", e);
        }
    }
}
