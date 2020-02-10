package com.tibco.rta.client.taskdefs.impl.jms;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.RtaConnectionEx;
import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.jms.JMSRtaConnection;
import com.tibco.rta.client.notify.impl.AsyncJMSMessageNotifier;
import com.tibco.rta.client.taskdefs.ConnectionAwareTaskManager;
import com.tibco.rta.client.taskdefs.ConnectionTask;
import com.tibco.rta.client.transport.impl.jms.JMSMessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.util.ServiceConstants;

import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 21/3/13
 * Time: 10:10 AM
 * Open a listener connection to notifications queue
 */
public class NotificationsPipelineTask extends ConnectionTask<JMSMessageTransmissionStrategy, AsyncJMSMessageNotifier> {

    /**
     * Timeout
     */
    private long timeout;

    /**
     *
     */
    private TimeUnit units;

    public NotificationsPipelineTask(JMSMessageTransmissionStrategy transmissionStrategy, long timeout, TimeUnit units) {
        super(transmissionStrategy);
        this.timeout = timeout;
        this.units = units;
        //Mark this as async task to skip sleep.
        //@see IdempotentRetryTask#perform
        this.sync = false;
    }

    public void setSession(DefaultRtaSession session) {
        this.session = session;
    }


    @Override
    public Object perform() throws Exception {
        //Set priority higher than fact priority for connection establish.
        properties.put(ServiceConstants.MESSAGE_PRIORITY, "5");
        return establishAsyncPipeline();
    }

    private RtaConnectionEx establishAsyncPipeline() throws Exception {

        ConnectionAwareTaskManager taskManager = session.getTaskManager();

        //This check is required to avoid race condition
        //generated because the reconnect timer fires multiple
        //times resulting in multiple connection requests with
        //same session info.
        if (taskManager.isStarting() || taskManager.isConnectionDown()) {

            JMSRtaConnection ownerConnection = (JMSRtaConnection) messageTransmissionStrategy.getOwnerConnection();
            String notificationsEndpoint = properties.get(ConfigProperty.JMS_OUTBOUND_QUEUE.getPropertyName());
            String endpoint = RtaSessionUtil.getEndpoint(ServiceType.ASYNC, ownerConnection);
            ServiceResponse<?> serviceResponse = ownerConnection.establishPipeline(endpoint, properties, "Hello", session, timeout, units);
            String errorMessage = serviceResponse.getResponseProperties().getProperty(ServiceConstants.ERROR);
            //If it has no error only then attach
            if (errorMessage == null && session.getName() != null) {
                ownerConnection.attachAsyncConsumer(messageNotifier, notificationsEndpoint, session.getClientId());
            }
        }
        return messageTransmissionStrategy.getOwnerConnection();
    }

    public void shutdown() throws Exception {
        //Do not close connection as was done earlier.
        messageNotifier.close();
    }

    @Override
    public void salvage() throws Exception {
        JMSRtaConnection oldConnection = (JMSRtaConnection) messageTransmissionStrategy.getOwnerConnection();
        //Also copy the older connection's sessions.
        JMSRtaConnection newConnection = new JMSRtaConnection(oldConnection.getConnectionConfigurationAsMap(), oldConnection.getConnectedSessions());
        //If this goes through
        messageTransmissionStrategy.setOwnerConnection(newConnection);
    }

    @Override
    public String getTaskName() {
        return "Notifications";
    }
}
