package com.tibco.rta.client.transport;

import com.tibco.rta.client.notify.AsyncNotificationEventHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18/3/13
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MessageReceptionStrategy {

    /**
     * Perform initialization activity
     * @throws Exception
     */
    public void init() throws Exception;

    /**
     * Perform initialization activity and wait for specified timeout.
     * @throws Exception
     */
    public void init(long timeout, TimeUnit units) throws Exception;

    /**
     *
     * @throws Exception
     */
    public void shutdown() throws Exception;

    /**
     * Which events session is interested in for notifications.
     *
     */
    public void setInterestEvents(int interestEvents);


    public <A extends AsyncNotificationEventHandler> void registerNotificationHandler(A notificationEventHandler);
}
