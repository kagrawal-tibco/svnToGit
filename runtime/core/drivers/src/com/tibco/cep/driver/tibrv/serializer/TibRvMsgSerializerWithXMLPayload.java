package com.tibco.cep.driver.tibrv.serializer;

import com.tibco.cep.driver.util.PayloadExceptionUtil;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvMsgField;
import com.tibco.tibrv.TibrvXml;
import com.tibco.xml.schema.SmParticleTerm;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Aug 10, 2006
 * Time: 4:23:43 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class provides serialize scheme with XML payload.
 * That is in the incoming RV Message there is named field "_payload_" which
 * contains TibrvXML content. This content has the complex Type specified in the
 * event's payload
 */
public class TibRvMsgSerializerWithXMLPayload extends TibRvMsgSerializer {

    public final static String RV_MSG_PAYLOAD = "_payload_";

    protected void serializePayload(SimpleEvent event, TibrvMsg msg) throws Exception {
        EventPayload payload = event.getPayload();
        if (payload == null) return;

        Object data = payload.getObject();
        if (data == null) return;

        byte[] buf = payload.toBytes();
        msg.add(RV_MSG_PAYLOAD, new TibrvXml(buf));
        return;
    }

    protected void deserializePayload(SimpleEvent event, TibrvMsg msg, SerializationContext context) throws Exception {

        RuleSession session = context.getRuleSession();
        PayloadFactory payloadFactory = session.getRuleServiceProvider().getTypeManager().getPayloadFactory();
        SmParticleTerm payloadTerm = payloadFactory.getPayloadElement(event.getExpandedName());

        EventPayload payload = null;
        if (payloadTerm == null) {
            payload = new TibrvMsgPayload(event.getExpandedName(), msg);
        } else {

            Object payloadMsg = msg.getField(RV_MSG_PAYLOAD);
            if (payloadMsg == null) {

                // Convert the message into XML schema. It starts from the top level. The entire message is converted into XML
                payload = new TibrvMsgPayload(event.getExpandedName(), payloadTerm, msg, false);
            } else {
                payloadMsg = ((TibrvMsgField) payloadMsg).data;
                try {
                    if (payloadMsg instanceof TibrvXml) {
                        byte[] b = ((TibrvXml) payloadMsg).getBytes();
                        payload = payloadFactory.createPayload(event.getExpandedName(), b);
                    } else if (payloadMsg instanceof String) {
                        payload = payloadFactory.createPayload(event.getExpandedName(), payloadMsg.toString());
                    } else if (payloadMsg instanceof byte[]) {
                        payload = payloadFactory.createPayload(event.getExpandedName(), (byte[]) payloadMsg);
                    } else if (payloadMsg instanceof TibrvMsg) {
                        payload = new TibrvMsgPayload(event.getExpandedName(), payloadTerm, (TibrvMsg) payloadMsg, true);
                    }
                }
                catch (Exception e) {
                    PayloadExceptionUtil.assertPayloadExceptionAdvisoryEvent(e, msg, event, session);
                }
            }
        }
        
        event.setPayload(payload);
    }
}
