package com.tibco.cep.metric.api;

/**
 * Interface for allowing pub sub via coherence channel
 * 
 * @author ajeswani
 * 
 */
public interface PubSubManager {

	/**
	 * Initializes pub sub notification mechanism
	 * 
	 * @param subscriberName
	 *            Name of client
	 * @param notification
	 *            Call back
	 * @throws Exception
	 *             exception
	 */
	void initialize(String subscriberName, Notification notification)
			throws Exception;

	/**
	 * Adds subscription for specific metric
	 * 
	 * @param subscription
	 *            subscription type
	 * @throws Exception
	 *             exception
	 */
	void subscribe(Subscription subscription) throws Exception;

	/**
	 * Removes client subscription
	 * 
	 * @param subscriptionName
	 *            name of subscription
	 */
	void unSubscribe(String subscriptionName);

}
