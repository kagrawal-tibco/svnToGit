package com.tibco.rta.impl.util;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/2/13
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface QueueEventNotificationListener {

    public void notifyQueueEvent(QueueEvent queueEvent) throws Exception;
}
