/*
 * Author: Anil Jeswani / Date: Mar 29, 2010 / Time: 2:54:28 PM
 */
package com.tibco.cep.metric.api;


/**
 * Calls back client.
 *
 * @author ajeswani
 *
 */
public interface Notification {

	/**
	 * Notifies the client.
	 *
	 * @param metric
	 *            Updated metric
	 * @param subscriptionNames
	 *            Subscription list
	 * @param notificationType
	 *            Type of update
	 */
	void notify(NotificationPacket packet);
}
