package com.tibco.be.ws.notification;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/5/12
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
public interface INotification<N extends INotificationContext> {


	/**
	 * @param notificationContext
	 * @return boolean true if connection is established, false otherwise
	 */
	public void openConnection(N notificationContext) throws Exception;
	
	/**
	 * @param messageTemplateContents
	 * @throws Exception
	 */
	public void loadMessageTemplates(String messageTemplateContents) throws Exception;

	/**
	 * @param notificationContext
	 * @param notificationType
	 * @param messageTemplateContents
	 * @param messageDataMapObj
	 * @throws Exception
	 */
	public void prepareMessage(N notificationContext, String notificationType, String messageTemplateContents, Map<String, String> messageDataMap) throws Exception;
	
	/**
     * Perform actual notification to the channel
     * @param notificationContext
     * @return
     */
    public NotificationStatus notify(N notificationContext);
    
	/**
	 * @param notificationContext
	 * @return boolean true if connection is established, false otherwise
	 */
	public void closeConnection() throws Exception;        
}
