package com.tibco.cep.runtime.model.event;


import com.tibco.cep.runtime.channel.Channel;


/**
 * Represents the context in which a message that was received from a {@link Channel} leads to the creation of
 * a {@link SimpleEvent}.
 * Allows BusinessEvents to acknowledge and/or to reply to the message
 * using the underlying transport of the associated <code>Channel</code>.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface EventContext {


    /**
     * Acknowledges the message that was deserialized into the event associated to this <code>EventContext</code>.
     *
     * @.category public-api
     * @since 2.0.0
     */
    void acknowledge();


    /**
     * Rolls back the message that was deserialized into the event associated to this <code>EventContext</code>.
     *
     * @since 5.2.0
     */
    void rollBack();


    /**
     * Replies with the given <code>SimpleEvent</code> to
     * the message that was deserialized into the event associated to this <code>EventContext</code>.
     *
     * @param replyEvent
     * @return true if successful
     * @.category public-api
     * @since 2.0.0
     */
    boolean reply(SimpleEvent replyEvent);
    
    String replyImmediate(SimpleEvent replyEvent);



    /**
     * Specifies if this <code>EventContext</code> can reply to the original message.
     *
     * @return true if this <code>EventContext</code> can reply to the original message.
     * @.category public-api
     * @since 2.0.0
     */
    boolean canReply();


    /**
     * Gets the <code>Channel.Destination</code>  which received
     * the message that was deserialized into the event associated to this <code>EventContext</code>.
     *
     * @return the <code>Channel.Destination</code>  which received the event corresponding to
     *         this <code>EventContext</code>.
     * @since 2.0.0
     */
    Channel.Destination getDestination();


    /**
     * Gets the message that was deserialized into the event associated to this <code>EventContext</code>.
     *
     * @return the message that was deserialized into the event associated to this <code>EventContext</code>.
     * @.category public-api
     * @since 2.0.0
     */
    Object getMessage();

    /**
     * Returns whether the event was modified.
     * @return
     */
    boolean isEventModified();
}
