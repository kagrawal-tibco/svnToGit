package com.tibco.cep.driver.jms.serializer;


import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.JMSSerializationContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;

import javax.jms.Message;
import javax.jms.Session;

/**
 * User: ssubrama
 * Date: Mar 15, 2007
 * Time: 2:21:00 PM
 */
public class BasicMessageSerializer
        extends AbstractMessageSerializer<Message>
{


    @Override
    protected Message createMessage(
            Session session,
            JMSSerializationContext ctx)
            throws Exception
    {
        return session.createMessage();
    }


    @Override
    public SimpleEvent deserialize(
            Object message,
            SerializationContext context)
            throws Exception
    {
        return this.deserialize((Message) message, context); // Bypasses auto-selection of the deserializer.
    }


    @Override
    protected void deserializePayload(
            SimpleEvent event,
            Message inputMessage,
            SerializationContext context)
            throws Exception
    {
        // The message payload is not deserialized.
    }


    @Override
    protected void serializePayload(
            SimpleEvent event,
            Message msg,
            JMSSerializationContext context)
            throws Exception
    {
        // The event payload is not serialized.
    }

}
