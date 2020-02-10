package com.tibco.be.custom.channel;


/**
 * Extends the <code>Event</code> interface by exposing the BE event's identifier <code>long id</code>.
 * It is used on the outbound side like during <code>Destination.sendEvent</code>. The user channel/destination has read access to the events identifier
 * @.category public-api
 * @since 5.4
 */


public interface EventWithId extends Event {
	

	/**
	 * Gets the long internal id of the Event.
	 * 
	 * @return internal event id
	 * @.category public-api
	 */
	long getId();

}
