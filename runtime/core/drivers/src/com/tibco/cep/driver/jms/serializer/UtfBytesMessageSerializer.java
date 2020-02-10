package com.tibco.cep.driver.jms.serializer;

import javax.jms.BytesMessage;
import javax.jms.Message;

import com.tibco.cep.driver.jms.JMSDestination;
import com.tibco.cep.driver.util.PayloadExceptionUtil;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.JMSSerializationContext;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.ObjectPayload;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.schema.SmParticleTerm;


public class UtfBytesMessageSerializer
        extends BytesMessageSerializer {


    @Override
    public SimpleEvent deserialize(
            Object message,
            SerializationContext context)
            throws Exception
    {
        return (message instanceof BytesMessage)
                ? this.deserialize((Message) message, context) // Bypasses auto-selection of the deserializer.
                : super.deserialize(message, context);
    }


    @Override
    protected void deserializePayload(
            SimpleEvent event,
            BytesMessage inputMessage,
            SerializationContext context)
            throws Exception
    {
        final RuleSession session = context.getRuleSession();
        final String utf = inputMessage.readUTF();

        if ((null != utf) && !utf.isEmpty()) {
            final PayloadFactory payloadFactory = session.getRuleServiceProvider().getTypeManager().getPayloadFactory();
            final SmParticleTerm payloadTerm = payloadFactory.getPayloadElement(event.getExpandedName());

            final byte[] b = utf.getBytes("UTF-8");

            EventPayload payload = null;

            if (payloadTerm == null) {
                payload = new ObjectPayload(b);
                event.setPayload(payload);

            } else {
                try {
                	boolean isJSONPayload = ((JMSDestination) context.getDestination()).isJSONPayload();
                    payload = payloadFactory.createPayload(event.getExpandedName(), b, isJSONPayload);

                } catch (Exception e) {
                    PayloadExceptionUtil.assertPayloadExceptionAdvisoryEvent(e, inputMessage, event, session);
                } finally {
                    event.setPayload(payload);
                }
            }
        }
    }


    @Override
    protected void serializePayload(
            SimpleEvent event,
            BytesMessage msg,
            JMSSerializationContext context)
            throws Exception
    {
        final EventPayload payload = event.getPayload();
        if (null != payload) {
            msg.writeUTF(payload.toString());
        }
    }


}
