package com.tibco.rta.client.notify.handler;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.client.notify.AsyncNotificationEvent;
import com.tibco.rta.client.notify.AsyncNotificationEventHandler;
import com.tibco.rta.client.taskdefs.IdempotentRetryTask;
import com.tibco.rta.client.taskdefs.impl.jms.SessionConfirmationAckTask;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.util.HeaderConstants;

import javax.jms.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/6/13
 * Time: 8:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessionLivenessEventNotificationHandler implements AsyncNotificationEventHandler {

    /**
     * Session reference.
     */
    private DefaultRtaSession session;

    public SessionLivenessEventNotificationHandler(DefaultRtaSession session) {
        this.session = session;
    }

    @Override
    public void handleNotificationEvent(AsyncNotificationEvent notificationEvent) throws Exception {
        //Send request
        Object replyToEndpoint = notificationEvent.get("ReplyToEndpoint");
        int retryCount = (Integer) ConfigProperty.NUM_RETRIES_SYNC_OPS.getValue(session.getConfiguration());
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(session.getConfiguration());

        SessionConfirmationAckTask sessionConfirmationAckTask = new SessionConfirmationAckTask(session.getTransmissionStrategy());
        sessionConfirmationAckTask.setDestination((Queue) replyToEndpoint);
        sessionConfirmationAckTask.setSession(session);

        IdempotentRetryTask retryTask = new IdempotentRetryTask(session, retryCount, waitTime, sessionConfirmationAckTask);
        try {
            retryTask.perform();
        } catch (Throwable throwable) {
            throw new Exception(throwable);
        }
    }

    @Override
    public boolean canHandle(byte[] header) throws Exception {
        String headerString = new String(header, "UTF-8");
        return HeaderConstants.SESSION_LIVENESS_HEADER.equals(headerString);
    }
}
