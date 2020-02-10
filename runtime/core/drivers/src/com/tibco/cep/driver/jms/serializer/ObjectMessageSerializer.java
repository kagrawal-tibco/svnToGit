package com.tibco.cep.driver.jms.serializer;

import java.io.Serializable;

import javax.jms.ObjectMessage;
import javax.jms.Session;

import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.JMSSerializationContext;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;

/**
 * User: ssubrama
 * Date: Jul 22, 2006
 * Time: 11:35:28 AM
 */
public class ObjectMessageSerializer
        extends AbstractMessageSerializer<ObjectMessage>
{


    @Override
    protected ObjectMessage createMessage(
            Session session,
            JMSSerializationContext ctx)
            throws Exception
    {
        return session.createObjectMessage();
    }


    @Override
    protected void deserializePayload(
            SimpleEvent event,
            ObjectMessage message,
            SerializationContext context)
            throws Exception
    {
        event.setPayload(
                context.getRuleSession().getRuleServiceProvider().getTypeManager().getPayloadFactory().createPayload(
                        event.getExpandedName(),
                        message.getObject()));
    }


    @Override
    protected void serializePayload(
            SimpleEvent event,
            ObjectMessage omsg,
            JMSSerializationContext context)
            throws Exception
    {
        final EventPayload payload = event.getPayload();

        if (null != payload) {
            omsg.setObject((Serializable) payload.getObject());
        }
    }


}
