package com.tibco.cep.driver.jms.serializer;

import java.util.Properties;

import javax.jms.Session;
import javax.jms.TextMessage;

import com.tibco.cep.driver.jms.JMSDestination;
import com.tibco.cep.driver.util.PayloadExceptionUtil;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.JMSSerializationContext;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.ObjectPayload;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.schema.SmParticleTerm;

/**
 * User: ssubrama
 * Date: Jul 22, 2006
 * Time: 11:34:22 AM
 */

/**
 *  TextMessageSerializer - This class is primarily for Payload being send as Text Message
 * The serializer optimally tries to figure out if it is an XML type String, and
 * if so, creates an XiNodePayload otherwise creates a ObjectPayload
 *
 */
public class TextMessageSerializer
        extends AbstractMessageSerializer<TextMessage>
{


	private final static String REPLACE_HYPHENS_WITH_UNDERSCORES =
            "be.jms.TextMessageSerializer.replace.hyphens.with.underscores";


	private static Boolean substitute = null;


    @Override
    protected TextMessage createMessage(
            Session session,
            JMSSerializationContext ctx)
            throws Exception
    {
        return session.createTextMessage();
    }


    @Override
    public void deserializePayload(
            SimpleEvent event,
            TextMessage inputMessage,
            SerializationContext context)
            throws Exception
    {
        RuleSession session = context.getRuleSession();
        String txt = inputMessage.getText();
        if (txt != null) {
            PayloadFactory payloadFactory = session.getRuleServiceProvider().getTypeManager().getPayloadFactory();
            SmParticleTerm payloadTerm = payloadFactory.getPayloadElement(event.getExpandedName());

            EventPayload payload = null;

            if (payloadTerm == null) {
                payload = new ObjectPayload(txt);
                event.setPayload(payload);
            }
            else {
                try {
                	boolean isJSONPayload = ((JMSDestination) context.getDestination()).isJSONPayload();
                    payload = session.getRuleServiceProvider().getTypeManager().getPayloadFactory().createPayload(event.getExpandedName(), txt, isJSONPayload);
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
    protected String convertPropertyName(
            String pname,
            SerializationContext ctx)
    {
    	if(pname != null) {
        	if(checkSubstitute(ctx)) {
        		pname = pname.replace('_','-');
        	}
    	}
    	return pname;
    }
    
//    @Override
//    protected Object substituteMessage(Object obj, SimpleEvent event, SerializationContext ctx) {
//    	return obj;
////    	if(event != null && obj instanceof Message && checkSubstitute(ctx)) {
////    		Message msg = (Message)obj;
////    		msg.get
////    	}
////    	return obj;
//    }


    private boolean checkSubstitute(SerializationContext ctx) {
    	boolean subLocal = false;
    	if(substitute == null) {
        	if(ctx != null) {
        		RuleSession rs = ctx.getRuleSession();
        		if(rs != null) {
        			RuleServiceProvider rsp = rs.getRuleServiceProvider();
        			if(rsp != null) {
        				Properties p = rsp.getProperties();
        				if(p != null) {
        					substitute = Boolean.parseBoolean(p.getProperty(REPLACE_HYPHENS_WITH_UNDERSCORES));
        					subLocal = substitute;
        				}
        			}
        		}
        	}
		} else {
			subLocal = substitute;
		}
    	return subLocal;
    }


    @Override
    protected void serializePayload(
            SimpleEvent event,
            TextMessage tmsg,
            JMSSerializationContext context)
            throws Exception
    {
        final EventPayload payload = event.getPayload();

        if (null != payload) {
            tmsg.setText(payload.toString());
        }
    }


}
