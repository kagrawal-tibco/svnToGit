package com.tibco.rta.service.cluster;

import com.tibco.rta.service.cluster.impl.GMPHeartbeatMessage;
import com.tibco.rta.service.cluster.impl.GMPLeaderElectedMessage;
import com.tibco.rta.service.cluster.impl.GMPQuorumNotificationMessage;
import com.tibco.rta.service.cluster.impl.GMPSubscriptionMessage;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/4/13
 * Time: 1:56 PM
 * Publish messages over transport dependant destinations for GMP.
 */
public interface GMPMessagePublisher {

    /**
     * Perform any initialization.
     *
     * @param configuration
     * @throws Exception
     */
    public void init(Properties configuration) throws Exception;

    /**
     * A group membership subscription message.
     *
     * @param subscriptionMessage
     */
    public void publishSubscriptionMessage(GMPSubscriptionMessage subscriptionMessage) throws Exception;

    /**
     * A group membership heartbeat message sent periodically by each participant/leader member.
     *
     * @param heartbeatMessage
     */
    public void publishHeartbeatMessage(GMPHeartbeatMessage heartbeatMessage) throws Exception;

    /**
     * A group membership quorum completion message.
     *
     * @param quorumNotificationMessage
     */
    public void publishQuorumNotificationMessage(GMPQuorumNotificationMessage quorumNotificationMessage) throws Exception;

    /**
     * A leader elected message.
     *
     * @param leaderElectedMessage
     */
    public void publishElectionCompletedMessage(GMPLeaderElectedMessage leaderElectedMessage) throws Exception;
}
