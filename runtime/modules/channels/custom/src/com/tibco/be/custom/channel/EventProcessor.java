package com.tibco.be.custom.channel;

/**
 * An <code>EventProcessor</code> interface is a handle provided by BE runtime to the user destination, in order for the user's destination to to assert events into BE
 * @.category public-api
 * @since 5.4
 */

public interface EventProcessor {

	/**
	 * The user destination should invoke this method in order to assert an event into BE
	 *
	 * @param event
	 *            The event received by the channel as serialized by the associated
	 *            {@link BaseEventSerializer Serializer}
	 * 
	 * @throws Exception
	 *             thrown exception
	 * @.category public-api
	 * @since 5.4
	 */
	void processEvent(Event event) throws Exception;

	String getRuleSessionName();
}
