package com.tibco.rta.service.cluster.impl;

import com.tibco.rta.service.cluster.GMPMessagePublisher;
import com.tibco.rta.service.cluster.GMPMessageTypes;
import com.tibco.rta.service.cluster.util.FTConstants;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.TopicPublisher;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/4/13
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class GMPJMSMessagePublisher extends AbstractGMPJMSMessageProcessor implements GMPMessagePublisher {


    @Override
    public void init(Properties configuration) throws JMSException, NamingException {
        super.init(configuration, "MessagePublisher");
    }

    @Override
    public void publishSubscriptionMessage(GMPSubscriptionMessage message) throws JMSException {
        TextMessage subscriptionTextMessage = topicSession.createTextMessage();
        //Create a text messages
        subscriptionTextMessage.setStringProperty(FTConstants.GMP_JMS_MESSAGE_TYPE, GMPMessageTypes.MEMBER_JOINED.toString());
        subscriptionTextMessage.setStringProperty(FTConstants.GMP_JMS_MESSAGE_MEMBER_ID, message.toString());

        publishMessage(subscriptionTextMessage);
    }

    @Override
    public void publishQuorumNotificationMessage(GMPQuorumNotificationMessage quorumNotificationMessage) throws Exception {
        TextMessage quorumNotificationTextMessage = topicSession.createTextMessage();
        //Create a text messages
        quorumNotificationTextMessage.setStringProperty(FTConstants.GMP_JMS_MESSAGE_TYPE, GMPMessageTypes.QUORUM_NOTIFICATION.toString());
        quorumNotificationTextMessage.setStringProperty(FTConstants.GMP_JMS_MESSAGE_QUORUM_MEMBER_IDS, quorumNotificationMessage.toString());

        publishMessage(quorumNotificationTextMessage);
    }

    @Override
    public void publishElectionCompletedMessage(GMPLeaderElectedMessage leaderElectedMessage) throws Exception {
        TextMessage leaderElectedTextMessage = topicSession.createTextMessage();
        //Create a text messages
        leaderElectedTextMessage.setStringProperty(FTConstants.GMP_JMS_MESSAGE_TYPE, GMPMessageTypes.MEMBER_LEADER.toString());
        leaderElectedTextMessage.setStringProperty(FTConstants.GMP_JMS_MESSAGE_MEMBER_ID, leaderElectedMessage.toString());

        publishMessage(leaderElectedTextMessage);
    }

    @Override
    public void publishHeartbeatMessage(GMPHeartbeatMessage heartbeatMessage) throws Exception {
        TextMessage heartbeatTextMessage = topicSession.createTextMessage();
        //Create a text messages
        heartbeatTextMessage.setStringProperty(FTConstants.GMP_JMS_MESSAGE_TYPE, GMPMessageTypes.MEMBER_HEARTBEAT.toString());
        heartbeatTextMessage.setStringProperty(FTConstants.GMP_JMS_MESSAGE_CLUSTER_STATE, heartbeatMessage.getClusterState());
        heartbeatTextMessage.setStringProperty(FTConstants.GMP_JMS_MESSAGE_MEMBER_STATE, heartbeatMessage.getMemberState().name());
        heartbeatTextMessage.setStringProperty(FTConstants.GMP_JMS_MESSAGE_MEMBER_ID, heartbeatMessage.toString());

        publishMessage(heartbeatTextMessage);

    }

    private void publishMessage(TextMessage textMessage) throws JMSException {

        TopicPublisher messageProducer = topicSession.createPublisher(gmpTopic);

        messageProducer.publish(textMessage);
        //TODO Closing it imp
        messageProducer.close();
    }

	public void stop() throws Exception {
		super.stop();
		
	}
}
