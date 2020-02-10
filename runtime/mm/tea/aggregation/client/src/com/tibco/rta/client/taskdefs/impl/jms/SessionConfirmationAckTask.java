package com.tibco.rta.client.taskdefs.impl.jms;

import com.tibco.rta.client.jms.JMSRtaConnection;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.client.transport.impl.jms.JMSMessageTransmissionStrategy;
import com.tibco.rta.impl.DefaultRtaSession;

import javax.jms.Destination;
import javax.jms.Queue;

import static com.tibco.rta.util.ServiceConstants.SESSION_ID;
import static com.tibco.rta.util.ServiceConstants.RESPONSE_CLIENT_ALIVE;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 14/6/13
 * Time: 10:15 AM
 * Send confirmation for session ack. Only used for JMS transport.
 */
public class SessionConfirmationAckTask extends AbstractClientTask {

    /**
     * The temp destination to respond to.
     */
    private Destination destination;

    /**
     * Reference to the session.
     */
    private DefaultRtaSession session;

    public SessionConfirmationAckTask(MessageTransmissionStrategy messageTransmissionStrategy) {
        super(messageTransmissionStrategy);
    }

    @Override
    public Object perform() throws Throwable {
        addProperty(SESSION_ID, session.getClientId());
        addProperty(RESPONSE_CLIENT_ALIVE, "true");


        if (messageTransmissionStrategy instanceof JMSMessageTransmissionStrategy) {
            JMSMessageTransmissionStrategy jmsMessageTransmissionStrategy = (JMSMessageTransmissionStrategy) messageTransmissionStrategy;
            JMSRtaConnection connection = (JMSRtaConnection) jmsMessageTransmissionStrategy.getOwnerConnection();
            connection.sendMessage((Queue) destination, properties, "");
        }
        return null;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setSession(DefaultRtaSession session) {
        this.session = session;
    }

    @Override
    public String getTaskName() {
        return "SessionConfirmationAckTask";
    }
}
