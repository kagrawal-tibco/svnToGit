package com.tibco.cep.driver.jms.serializer;

import javax.jms.BytesMessage;
import javax.jms.Session;

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

/**
 * User: ssubrama
 * Date: Jul 22, 2006
 * Time: 11:33:20 AM
 */
public class BytesMessageSerializer
        extends AbstractMessageSerializer<BytesMessage>
{


    @Override
    protected BytesMessage createMessage(
            Session session,
            JMSSerializationContext ctx)
            throws Exception
    {
        return session.createBytesMessage();
    }


    @Override
    protected void deserializePayload(
            SimpleEvent event,
            BytesMessage inputMessage,
            SerializationContext context)
            throws Exception
    {
        RuleSession session = context.getRuleSession();
        byte [] b=new byte[(int) inputMessage.getBodyLength()];
        if (b.length > 0) {
            PayloadFactory payloadFactory = session.getRuleServiceProvider().getTypeManager().getPayloadFactory();
            SmParticleTerm payloadTerm = payloadFactory.getPayloadElement(event.getExpandedName());

            EventPayload payload = null;
            inputMessage.readBytes(b);

            if (payloadTerm == null) {
                payload = new ObjectPayload(b);
                event.setPayload(payload);
            }
            else {
                try {
                	boolean isJSONPayload = ((JMSDestination) context.getDestination()).isJSONPayload();
                    payload = session.getRuleServiceProvider().getTypeManager().getPayloadFactory().createPayload(event.getExpandedName(), b, isJSONPayload);
                }
                catch (Exception e) {
                    PayloadExceptionUtil.assertPayloadExceptionAdvisoryEvent(e, inputMessage, event, session);
                }
                finally {
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
            msg.writeBytes(payload.toBytes());
        }
    }


}
