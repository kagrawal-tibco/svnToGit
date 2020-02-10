package com.tibco.be.ws.notification;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/5/12
 * Time: 2:31 PM
 * interface to tag context used by an impl of {@linkplain INotification}
 */
public interface INotificationContext {

    /**
     *  Perform any initialization for notification configuration.
     *  All config should be packaged as key value pairs for context to initialize itself.
     * @param contextPropertiesMap
     */
	public void initialize(Map<String, String> contextPropertiesMap);

    /**
     * Prepare message to be sent as notification to appropriate channel. Massage the message if needed.
     * @param notificationType
     * @param messageTemplateContents
     * @param messageData
     */
	public void prepareMessage(String notificationType, String messageTemplateContents, Map<String, String> messageData) throws Exception;
}
