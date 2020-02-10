package com.tibco.be.custom.channel;

import java.util.Collection;

/**
 * An abstraction of a BE runtime event that the channel API uses. It acts as a bridge to the runtime BE events.
 * Instances of Event are transformed into runtime BE event instances on the inbound side, and instances of BE events are transformed to this interface on the outbound side.
 * @.category public-api
 * @since 5.4
 */

public interface Event {

	/**
	 * Gets the unique ID representing this event
	 *
	 * @return extId unique id
	 * @.category public-api
	 * @since 5.4
	 */
	String getExtId();

	/**
	 * Sets the unique ID representing this event
	 *
	 * @param extId
	 *            unique id
	 * @.category public-api
	 * @since 5.4
	 */
	void setExtId(String extId);

	/**
	 * Gets the destination URI associated with this event
	 *
	 * @return destination URI of the destination associated with this event
	 * @.category public-api
	 * @since 5.4
	 */
	String getDestinationURI();

	/**
	 * Sets the destination URI associated with this event
	 *
	 * @param destinationURI
	 *            the URI of the destination
	 * @.category public-api           
	 * @since 5.4
	 */
	void setDestinationURI(String destinationURI);

	/**
	 * Gets the payload of this event
	 *
	 * @return payload a byte array representing the payload
	 * @.category public-api
	 * @since 5.4
	 */
	byte[] getPayload();

	/**
	 * Sets the payload of this event
	 *
	 * @param payload
	 *            a byte array representing the payload
	 * @.category public-api
	 * @since 5.4
	 */
	void setPayload(byte[] payload);

	/**
	 * Gets the eventUri of this event
	 *
	 * @return eventUri the eventUri associated with this event
	 * @.category public-api
	 * @since 5.4
	 */
	String getEventUri();

	/**
	 * Sets the eventUri of this event
	 *
	 * @param eventUri
	 *            the eventUri to be set to this event
	 * @.category public-api
	 * @since 5.4
	 */
	void setEventUri(String eventUri);

	/**
	 * Sets the value of a property by name.
	 *
	 * @param name
	 *            name of the event property.
	 * @param value
	 *            the <code>Object</code> value to be set. The type of value
	 *            must be consistent with the type defined in the model.
	 * @.category public-api
	 * @since 5.4
	 */
	void setProperty(String name, Object value);

	/**
	 * Gets the value of a property by name.
	 *
	 * @param name
	 *            name of the event property.
	 * @return the value of that property.
	 * @.category public-api
	 * @since 5.4
	 */
	Object getPropertyValue(String name);

	/**
	 * Gets all the property names in this event.
	 *
	 * @return all the Property names in Collection&lt;String&gt;
	 * @.category public-api
	 * @since 5.4
	 */
	Collection<String> getAllPropertyNames();

}
