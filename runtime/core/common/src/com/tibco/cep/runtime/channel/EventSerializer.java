package com.tibco.cep.runtime.channel;


import com.tibco.cep.runtime.model.event.SimpleEvent;


/**
 * Handles serialization/deserialization of events to/from a transport format.
 * <p>In general, a channel type has its own implementation of EventSerializer,
 * which is used by the channel's destinations to transform outgoint events into messages
 * and incoming messages into events.</p>
 * <!-- TODO mention Stateless behavior, Threading model -->
 *
 * @version 2.0
 * @see Channel
 * @see Channel.Destination
 * @since 2.0
 */
public interface EventSerializer {


    void init(ChannelManager channelManager, DestinationConfig config);

    /**
     * Transforms a message received by the Destination of the given context
     * into a SimpleEvent that can be asserted in the RuleSession of that context.
     *
     * @param message an Object representing an incoming message in the Destination's transport.
     * @return the SimpleEvent that represents the incoming message in the RuleSession.
     * @throws Exception if a SimpleEvent could not be created.
     */
    public SimpleEvent deserialize(Object message, SerializationContext context) throws Exception;


    /**
     * Transforms a SimpleEvent of the RuleSession of the given context
     * into a message that can be sent by the Destination of that context.
     *
     * @param event a SimpleEvent in the RuleSession of the context.
     * @return the message Object to be sent on the underlying transport of the Destination.
     * @throws Exception if the message Object could not be created.
     */
    public Object serialize(SimpleEvent event, SerializationContext context) throws Exception;


}
