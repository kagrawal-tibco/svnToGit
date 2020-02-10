package com.tibco.rta.client.tcp;

import com.tibco.rta.client.notify.AsyncNotificationEvent;
import com.tibco.rta.client.notify.AsyncNotificationEventHandler;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

import java.util.List;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 10/2/13
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class GatheringBytesTask implements Runnable {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    /**
     * The header part
     */
    private byte[] headerChunk;

    /**
     * The main content
     */
    private byte[] contentChunk;

    /**
     * Any properties associated with response
     */
    private Properties properties;

    private List<AsyncNotificationEventHandler> notificationEventHandlers;

    public GatheringBytesTask(byte[] headerChunk,
                              byte[] contentChunk,
                              Properties properties,
                              List<AsyncNotificationEventHandler> notificationEventHandlers) {
        this.headerChunk = headerChunk;
        this.contentChunk = contentChunk;
        this.properties = properties;
        this.notificationEventHandlers = notificationEventHandlers;
    }

    @Override
    public void run() {
        try {
            for (AsyncNotificationEventHandler notificationEventHandler : notificationEventHandlers) {
                if (notificationEventHandler.canHandle(headerChunk)) {
                    AsyncNotificationEvent notificationEvent = new AsyncNotificationEvent(contentChunk, properties);
                    notificationEventHandler.handleNotificationEvent(notificationEvent);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }
}
