package com.tibco.cep.driver.tibrv.serializer;


import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.model.types.TypeRenderer;
import com.tibco.be.model.types.xsd.XSDTypeRegistry;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.driver.tibrv.TibRvConstants;
import com.tibco.cep.driver.util.IncludeEventType;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.EventSerializer;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvMsgField;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.values.XsDateTime;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;


/**
 * Created by IntelliJ IDEA.
 * User: hzhang
 * Date: Aug 2, 2006
 * Time: 7:33:09 AM
 * To change this template use File | Settings | File Templates.
 */

public class TibRvMsgSerializer implements EventSerializer {


    static TypeRenderer java2xsd_dt_conv =
            XSDTypeRegistry.getInstance().foreignToNative(XsDateTime.class, GregorianCalendar.class);

    protected Logger logger;
    private boolean isDeSerializingEventType;
    private boolean isSerializingEventType;

    //TibRvDestination dest;
    //TibRvChannel channel;
    //DestinationConfig config;


    public TibRvMsgSerializer() {
    }

    public void init(ChannelManager channelManager, DestinationConfig config) {
        this.logger = channelManager.getRuleServiceProvider().getLogger(this.getClass());

        final IncludeEventType includeEventType = IncludeEventType.valueOf(
                channelManager.getRuleServiceProvider().getGlobalVariables()
                        .substituteVariables(config.getProperties()
                                .getProperty("IncludeEventType", IncludeEventType.ALWAYS.toString()))
                        .toString());
        this.isDeSerializingEventType = includeEventType.isOkOnDeserialize();
        this.isSerializingEventType = includeEventType.isOkOnSerialize();
    }


    public SimpleEvent deserialize(Object inputMessage, SerializationContext ctx) throws Exception {
        SimpleEvent event;
        TibrvMsg msg = (TibrvMsg) inputMessage;

        ExpandedName en = null;
        if (this.isDeSerializingEventType) {
            final String ns = (String) msg.get(TibRvConstants.MESSAGE_HEADER_NAMESPACE_PROPERTY);
            final String nm = (String) msg.get(TibRvConstants.MESSAGE_HEADER_NAME_PROPERTY);
            if ((ns != null) && (nm != null)) {
                en = ExpandedName.makeName(ns, nm);
            }
        }
        if (null == en) {
            en = ctx.getDestination().getConfig().getDefaultEventURI();
        }

        Event eventDef = ctx.getDeployedDestinationConfig().getFilter();
        if (eventDef != null) {
            final String eventClassNM = eventDef.getName();
            final String eventClassNS = RDFTnsFlavor.BE_NAMESPACE + eventDef.getNamespace() + eventClassNM;
            ExpandedName ename = ExpandedName.makeName(eventClassNS, eventClassNM);
            if (!en.equals(ename)) {
                return null;
            }
        }


        event = (SimpleEvent) ctx.getRuleSession().getRuleServiceProvider().getTypeManager().createEntity(en);
        HashMap map = new HashMap();
        int numFields = msg.getNumFields();
        for (int i=0; i < numFields; i++) {
            TibrvMsgField field = msg.getFieldByIndex(i);
            map.put(field.name, field.data);
        }


        String[] propertynames = event.getPropertyNames();
        for (int i = 0; i < propertynames.length; i ++) {
            String propertyName = propertynames[i];
            Object obj = map.get(propertyName);
            if(obj != null)
                event.setProperty(propertyName, obj);
            else {
                if ("_sendsubject_".equalsIgnoreCase(propertyName)) {
                    event.setProperty(propertyName, msg.getSendSubject());
                }
                if ("_replysubject_".equalsIgnoreCase(propertyName)) {
                    event.setProperty(propertyName, msg.getReplySubject());
                }
            }
        }
        map.clear();

        deserializePayload(event, msg, ctx);

        String extid = (String) msg.get(TibRvConstants.MESSAGE_HEADER_EXTID_PROPERTY);
        if (extid != null) {
            event.setExtId(extid);
        }

        return event;
    }


    public Object serialize(SimpleEvent event, SerializationContext ctx) throws Exception {

        EventPayload payload = event.getPayload();
        if (payload != null) {
            Object obj = payload.getObject();
            if (obj instanceof TibrvMsg) {
                return (TibrvMsg) obj;
            }
        }

        TibrvMsg outMsg = new TibrvMsg();

        if (this.isSerializingEventType) {
            final ExpandedName en = event.getExpandedName();
            outMsg.add(TibRvConstants.MESSAGE_HEADER_NAMESPACE_PROPERTY, en.getNamespaceURI());
            outMsg.add(TibRvConstants.MESSAGE_HEADER_NAME_PROPERTY, en.getLocalName());
        }

        try {
            String extid = (String) event.getExtId();
            if (extid != null) {
                outMsg.add(TibRvConstants.MESSAGE_HEADER_EXTID_PROPERTY, extid);
            }
        } catch (Exception e) {
            // do nothing
        }
        String[] propertynames = event.getPropertyNames();
        for (int i = 0; i < propertynames.length; i ++) {
            String name = propertynames[i];
            Object property = event.getProperty(name);
            if (property != null) {
                if (property instanceof Boolean) {
                    outMsg.add(name, ((Boolean) property).booleanValue());
                } else if (property instanceof Integer) {
                    outMsg.add(name, ((Integer) property).intValue());
                } else if (property instanceof Long) {
                    outMsg.add(name, ((Long) property).longValue());
                } else if (property instanceof Double) {
                    outMsg.add(name, ((Double) property).doubleValue());
                } else if (property instanceof Calendar) {
                    outMsg.add(name, ((XsDateTime) java2xsd_dt_conv.convertToTypedValue(property)).castAsString());
                } else {
                    outMsg.add(name, property);
                }
            }
        }
        serializePayload(event, outMsg);
//        outMsg.setSendSubject
//                (config.getProperties().getProperty(TibRvConstants.DESTINATION_PROPERTY_SUBJECT.getLocalName()));
        return outMsg;
    }


    protected void serializePayload(SimpleEvent event, TibrvMsg msg) throws Exception {

//        EventPayload payload = event.getPayload();
//        if (payload == null) return;
//
//        TibrvMsg rvMsg = (TibrvMsg)payload.getObject();
//        if (rvMsg == null)  return;
//
//        TibrvMsgField msgField = new TibrvMsgField();
//        msgField.data = rvMsg;
//
//        msg.updateField(msgField);
    }


    protected void deserializePayload(SimpleEvent event, TibrvMsg msg, SerializationContext ctx) throws Exception {

        TibrvMsgPayload payload = new TibrvMsgPayload(event.getExpandedName(), msg);
        event.setPayload(payload);
//        RuleSession session = ctx.getRuleSession();
//        EventPayload payload = session.getRuleServiceProvider().getTypeManager().getPayloadFactory().createPayload(event.getExpandedName(), msg);
//        event.setPayload(payload);
    }


}
