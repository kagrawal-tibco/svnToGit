package com.tibco.rta.client.notify;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 1/2/13
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AsyncNotificationEventHandler {

    /**
     *
     */
    public void handleNotificationEvent(AsyncNotificationEvent notificationEvent) throws Exception;

    /**
     *
     * @param header
     * @return
     */
    public boolean canHandle(byte[] header) throws Exception;
}
