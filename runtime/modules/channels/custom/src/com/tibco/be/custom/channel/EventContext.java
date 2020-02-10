package com.tibco.be.custom.channel;

import com.tibco.be.custom.channel.framework.Channel;
import com.tibco.be.custom.channel.BaseDestination;


/**
 * Represents the context in which a message that was received from a {@link Channel} which leads to the creation of
 * an {@link Event}.
 * Allows BusinessEvents to acknowledge and/or to reply to the message
 * using the underlying transport(Destination) of the associated <code>Channel</code>.
 * @see BaseDestination#getEventContext(Event)
 * @.category public-api
 * @since 5.4
 */
public interface EventContext {
	
	
	/**
     * Replies with the given <code>Event</code> to
     * the message that was deserialized into the event associated to this <code>EventContext</code>.
     *
     * @param replyEvent reply event
     * @return true if successful
     * @.category public-api
     * @since 5.4
     */
	public boolean reply(Event replyEvent);
	
	 /**
     * Acknowledges the message that was deserialized into the event associated to this <code>EventContext</code>.
     * Also called when an Event is consumed.
     * @.category public-api
     * @since 5.4
     */
	public void acknowledge();
	
	/**
     * Rolls back the message that was deserialized into the event associated to this <code>EventContext</code>.
     * @.category public-api
     * @since 5.4
     */
	public void rollback();
	
	/**
     * Gets the <code>Destination</code>  which received
     * the message that was deserialized into the event associated to this <code>EventContext</code>.
     *
     * @return the <code>Destination</code>  which received the event corresponding to
     *         this <code>EventContext</code>.
     * @.category public-api         
     * @since 5.4
     */
	public BaseDestination getDestination();
	
	
	/**
     * Gets the message that was deserialized into the event associated to this <code>EventContext</code>.
     *
     * @return the message that was deserialized into the event associated to this <code>EventContext</code>.
     * @.category public-api
     * @since 5.4
     */
	public Object getMessage();
}
