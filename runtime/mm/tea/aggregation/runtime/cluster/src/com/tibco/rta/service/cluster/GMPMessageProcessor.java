package com.tibco.rta.service.cluster;

import com.tibco.rta.service.cluster.impl.GMPHeartbeatMessage;
import com.tibco.rta.service.cluster.impl.GMPLeaderElectedMessage;
import com.tibco.rta.service.cluster.impl.GMPQuorumNotificationMessage;
import com.tibco.rta.service.cluster.impl.GMPSubscriptionMessage;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/4/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GMPMessageProcessor {


    /**
     * A group membership subscription message.
     * @param subscriptionMessage
     */
    public void processSubscriptionMessage(GMPSubscriptionMessage subscriptionMessage) throws Exception;

    /**
     * A group quorum notification message.
     * @param quorumNotificationMessage
     */
    public void processQuorumNotificationMessage(GMPQuorumNotificationMessage quorumNotificationMessage) throws Exception;

    /**
     * A primary/leader elected message.
     * @param leaderElectedMessage
     */
    public void processLeaderElectedMessage(GMPLeaderElectedMessage leaderElectedMessage) throws Exception;

    /**
     * A heartbeat message from participating/leading member.
     * @param memberHeartbeatMessage
     */
    public void processMemberHeartbeatMessage(GMPHeartbeatMessage memberHeartbeatMessage) throws Exception;
}
