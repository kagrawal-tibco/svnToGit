package com.tibco.cep.driver.jms.serializer;


import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.JMSSerializationContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;

import javax.jms.*;


/**
 * User: ssubrama
 * Date: Jul 6, 2006
 * Time: 8:26:09 AM
 */
public class MapMessageSerializer
        extends AbstractMessageSerializer<MapMessage>
{


    @Override
    protected MapMessage createMessage(
            Session session,
            JMSSerializationContext ctx)
            throws Exception
    {
        return session.createMapMessage();
    }


    @Override
    protected void deserializePayload(
            SimpleEvent event,
            MapMessage msg,
            SerializationContext context)
            throws Exception
    {
        // The message payload is deserialized through readProperty.
    }


    @Override
    protected Object readProperty(
            MapMessage msg,
            String name)
            throws JMSException
    {
        return msg.getObject(name);
    }


    @Override
    protected void serializePayload(
            SimpleEvent event,
            MapMessage msg,
            JMSSerializationContext context)
            throws Exception
    {
        // The event properties are serialized into the message body through writeProperty.
        // The event payload is not serialized.
    }


    @Override
    protected void writeProperty(
            MapMessage msg,
            String name,
            Object value,
            JMSSerializationContext context)
            throws JMSException
    {
        msg.setObject(name, this.convertPropertyValue(value, context));
    }


}
