package com.tibco.rta.service.cluster.impl;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.cluster.DefaultGMPCluster;
import com.tibco.rta.service.cluster.GroupMember;
import com.tibco.rta.service.cluster.GroupMembershipService;
import com.tibco.rta.service.cluster.QuorumBasedGMPCluster;
import com.tibco.rta.service.cluster.event.GMPEventRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 24/7/13
 * Time: 1:45 PM
 * Scan heartbeat signals from each member.
 */
public class GroupMemberHeartbeatScanner extends TimerTask implements Runnable {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_CLUSTER.getCategory());

    /**
     * Allowable interval for cluster to wait in leader_absent state.
     */
    private long ALLOWABLE_LEADER_ABSENCE_INTERVAL;


    /**
     * Milliseconds for member liveness heartbeat.
     */
    private long ALLOWABLE_HEARTBEAT_ABSENCE_INTERVAL;

    public GroupMemberHeartbeatScanner(long allowableLeaderAbsenceThreshold, long allowableLivessHBThreshold) {

        this.ALLOWABLE_LEADER_ABSENCE_INTERVAL = allowableLeaderAbsenceThreshold;

        this.ALLOWABLE_HEARTBEAT_ABSENCE_INTERVAL = allowableLivessHBThreshold;
    }

    @Override
    public void run() {
        try {
            GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

            QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();

            GroupMember hostMember = cluster.getHostMember();

            List<GroupMember> allMembers = groupMembershipService.getAllMembers();

            List<GroupMember> copyList = new ArrayList<GroupMember>(allMembers);

            for (GroupMember groupMember : copyList) {

                if (!groupMember.equals(hostMember)) {
                    //Scan for heartbeats all members but self.
                    long lastHeartbeatInstant = groupMember.getLastHeartbeatInstant();
                    //If this instant is too old flag it for removal
                    long diff = System.currentTimeMillis() - lastHeartbeatInstant;

                    if (diff >= ALLOWABLE_HEARTBEAT_ABSENCE_INTERVAL) {

                        if (LOGGER.isEnabledFor(Level.WARN)) {
                            LOGGER.log(Level.WARN, "No hearbeat received in stipulated time for member [%s]", groupMember);
                            LOGGER.log(Level.WARN, "Member [%s] suspected to be down", groupMember);
                        }
                        //Send notification that this member may be down
                        GMPEventRegistry.INSTANCE.onMemberLeft(groupMember);
                    }
                } else {
                    //If it is same member check its state.
                    //If state is leader absent check elapsed time
                    //post cluster start
                    if (cluster instanceof DefaultGMPCluster) {

                        DefaultGMPCluster defaultGMPCluster = (DefaultGMPCluster) cluster;
                        //Check its current state
                        if (defaultGMPCluster.isLeaderAbsent() || defaultGMPCluster.isUnfenced()) {
                            //Get its startup time
                            long startupInstant = defaultGMPCluster.getStartupInstant();

                            long diff = System.currentTimeMillis() - startupInstant;

                            if (diff >= ALLOWABLE_LEADER_ABSENCE_INTERVAL) {

                                if (LOGGER.isEnabledFor(Level.WARN)) {
                                    LOGGER.log(Level.WARN, "No leader notification received in stipulated time");
                                }
                                //Send notification that this member may be down
                                GMPEventRegistry.INSTANCE.onLeaderAbsenceExpiry(groupMember);
                            }
                        } else {
                            //Check whether it is receiving own heartbeats
                            //Scan for heartbeats all members but self.
                            long lastHeartbeatInstant = groupMember.getLastHeartbeatInstant();
                            //If this instant is too old flag it for removal
                            long diff = System.currentTimeMillis() - lastHeartbeatInstant;

                            if (diff >= ALLOWABLE_HEARTBEAT_ABSENCE_INTERVAL) {

                                if (LOGGER.isEnabledFor(Level.WARN)) {
                                    LOGGER.log(Level.WARN, "No self hearbeats received in stipulated time for member [%s]", groupMember);
                                    LOGGER.log(Level.WARN, "Possible network outage expected for member [%s]", groupMember);
                                }
                                //Send notification that there are no self heartbeats
                                GMPEventRegistry.INSTANCE.onAbsentSelfHeartbeat(groupMember);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Exception in member joined", e);
        }
    }
}
