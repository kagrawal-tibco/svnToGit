package com.tibco.rta.service.cluster.impl;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.engine.DefaultGroupMember;
import com.tibco.rta.log.Level;
import com.tibco.rta.service.cluster.GMPMessageProcessor;
import com.tibco.rta.service.cluster.GMPMessageTypes;
import com.tibco.rta.service.cluster.GroupMember;
import com.tibco.rta.service.cluster.GroupMemberStates;
import com.tibco.rta.service.cluster.GroupMembershipService;
import com.tibco.rta.service.cluster.QuorumBasedGMPCluster;
import com.tibco.rta.service.cluster.event.GMPEventRegistry;
import com.tibco.rta.service.cluster.util.FTConstants;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/4/13
 * Time: 5:01 PM
 * Process GMP messages coming from JMS transport.
 */
public class GMPJMSMessageProcessor extends AbstractGMPJMSMessageProcessor implements GMPMessageProcessor, MessageListener {


    @Override
    public void init(Properties configuration, String subscriberType) throws JMSException, NamingException {
        super.init(configuration, subscriberType);

        MessageConsumer subscriber = topicSession.createSubscriber(gmpTopic);

        subscriber.setMessageListener(this);
    }

//    private String buildSelector() {
//         StringBuilder stringBuilder = new StringBuilder();
//         stringBuilder.append(FTConstants.GMP_JMS_MESSAGE_TYPE);
//         stringBuilder.append(" <> ");
//         stringBuilder.append("'");
//         stringBuilder.append(GMPMessageTypes.MEMBER_HEARTBEAT.toString());
//         stringBuilder.append("'");
//         stringBuilder.append(" OR ");
//         stringBuilder.append(FTConstants.GMP_JMS_MESSAGE_MEMBER_ID);
//         stringBuilder.append(" = ");
//         stringBuilder.append("'");
//         stringBuilder.append(hostMember.getUID());
//         stringBuilder.append("'");
//
//         return stringBuilder.toString();
//     }

    @Override
    public void processSubscriptionMessage(GMPSubscriptionMessage subscriptionMessage) throws Exception {
        //This was the group member uid joining.
        String groupMemberUID = subscriptionMessage.getOriginatingMemberUID();

        GroupMember groupMember = new DefaultGroupMember(groupMemberUID);

        GMPEventRegistry.INSTANCE.onMemberJoin(groupMember);
    }

    @Override
    public void processQuorumNotificationMessage(GMPQuorumNotificationMessage quorumNotificationMessage) throws Exception {
        String[] quorumMemberIds = quorumNotificationMessage.getQuorumMemberIds();
        GroupMember[] groupMembers = new GroupMember[quorumMemberIds.length];

        int loop = 0;
        for (String quorumMemberId : quorumMemberIds) {
            GroupMember groupMember = new DefaultGroupMember(quorumMemberId);
            groupMembers[loop++] = groupMember;
        }
        GMPEventRegistry.INSTANCE.onQuorumComplete(groupMembers);
    }

    @Override
    public void processLeaderElectedMessage(GMPLeaderElectedMessage leaderElectedMessage) throws Exception {
        String groupMemberUID = leaderElectedMessage.getOriginatingMemberUID();
        //Look for this member
        try {
            GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

            GroupMember member = groupMembershipService.getOrAddMember(groupMemberUID);
            ///TODO this member may have been removed during fencing window. So need to create one.
            GMPEventRegistry.INSTANCE.onMemberElected(member);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    @Override
    public void processMemberHeartbeatMessage(GMPHeartbeatMessage memberHeartbeatMessage) throws Exception {
        String groupMemberUID = memberHeartbeatMessage.getOriginatingMemberUID();

        GroupMemberStates memberState = memberHeartbeatMessage.getMemberState();

        boolean isLeader = memberState == GroupMemberStates.LEADER;

        //Look for this member
        try {
            GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

            QuorumBasedGMPCluster cluster = groupMembershipService.getCluster();

            GroupMember member = groupMembershipService.getOrAddMember(groupMemberUID);
            //Just dont take any member actions if current cluster state is fenced
            if (cluster.isFenced()) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Cluster currently fenced. Ignoring possible stale heartbeats.");
                }
            } else {
                //If host member is presently fenced, ignore its leader heartbeat message
                //Get and set member's current state
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Member [%s] current state [%s]. Host member state [%s]. Heartbeat state [%s]", member.getUID(), member.getState(), cluster.getHostMember().getState(), memberState);
                }

                if (member.isFenced()) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Member [%s] currently fenced and ignoring notification of previous leadership.", member.getUID());
                    }
                    //Ignore leadership heartbeats coming from a fenced member.
                    if (!isLeader) {
                        //Sync up member state locally.
                        cluster.setMemberState(member, memberState);
                    }
                } else {
                    //Sync up member state locally.
                    cluster.setMemberState(member, memberState);
                }
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Current state of member [%s] in processor [%s]", member.getUID(), member.getState());
                }
            }
            GMPEventRegistry.INSTANCE.onMemberHeartbeat(member);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    @Override
    public void onMessage(Message message) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "%s", message);
        }
        //Extract properties
        try {
            String messageType = message.getStringProperty(FTConstants.GMP_JMS_MESSAGE_TYPE);
            GMPMessageTypes gmpMessageType = GMPMessageTypes.valueOf(messageType);
            //Get text
            switch (gmpMessageType) {
                case MEMBER_JOINED : {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        String memberId = textMessage.getStringProperty(FTConstants.GMP_JMS_MESSAGE_MEMBER_ID);

                        GMPSubscriptionMessage subscriptionMessage = new GMPSubscriptionMessage();
                        subscriptionMessage.setOriginatingMemberUID(memberId);
                        processSubscriptionMessage(subscriptionMessage);
                    }
                }
                break;
                case MEMBER_LEADER : {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        String memberId = textMessage.getStringProperty(FTConstants.GMP_JMS_MESSAGE_MEMBER_ID);

                        GMPLeaderElectedMessage leaderElectedMessage = new GMPLeaderElectedMessage();
                        leaderElectedMessage.setOriginatingMemberUID(memberId);
                        processLeaderElectedMessage(leaderElectedMessage);
                    }
                }
                break;
                case QUORUM_NOTIFICATION : {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        String memberId = textMessage.getStringProperty(FTConstants.GMP_JMS_MESSAGE_QUORUM_MEMBER_IDS);
                        //Split ids on ,
                        String[] memberIds = memberId.split(",");

                        GMPQuorumNotificationMessage quorumNotificationMessage = new GMPQuorumNotificationMessage();
                        quorumNotificationMessage.setQuorumMemberIds(memberIds);
                        processQuorumNotificationMessage(quorumNotificationMessage);
                    }
                }
                break;
                case MEMBER_HEARTBEAT : {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        String memberId = textMessage.getStringProperty(FTConstants.GMP_JMS_MESSAGE_MEMBER_ID);
                        String clusterState = textMessage.getStringProperty(FTConstants.GMP_JMS_MESSAGE_CLUSTER_STATE);
                        String memberState = textMessage.getStringProperty(FTConstants.GMP_JMS_MESSAGE_MEMBER_STATE);

                        GMPHeartbeatMessage heartbeatMessage = new GMPHeartbeatMessage();
                        heartbeatMessage.setOriginatingMemberUID(memberId);
                        heartbeatMessage.setMemberState(GroupMemberStates.valueOf(memberState));
                        heartbeatMessage.setClusterState(clusterState);
                        processMemberHeartbeatMessage(heartbeatMessage);
                    }
                }
                break;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }
}
