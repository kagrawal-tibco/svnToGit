package com.tibco.cep.container.standalone.hawk.methods.om;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.session.RuleSession;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetEventMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;
    protected int m_processId;
    protected String m_hostName;


    public GetEventMethod(HawkRuleAdministrator hma) {
        super("getEvent", "Retrieves an Event from a Session.", AmiConstants.METHOD_TYPE_INFO, "Line");
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session", ""));
        args.addElement(new AmiParameter("Id", "Id of the Event", ""));
        args.addElement(new AmiParameter("External", "True if using the event's external Id, false if using the internal Id.", false));
        return args;
    }


    public AmiParameterList getReturns() {
        AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Line", "Line number.", 0));
        values.addElement(new AmiParameter("Session", "Name of the Session.", ""));
        values.addElement(new AmiParameter("Type", "Attribute or Property.", ""));
        values.addElement(new AmiParameter("Name", "Name of the Attribute or Property.", ""));
        values.addElement(new AmiParameter("Value", "Value of the Attribute or Property.", ""));
        return values;
    }//getReturns


    void fillInOneReturnsEntry(AmiParameterList values, int line, String name, EntityHawkAttribute attribute) {
        values.addElement(new AmiParameter("Line", "Line number.", line));
        values.addElement(new AmiParameter("Session", "Name of the Session.", name));
        values.addElement(new AmiParameter("Type", "Attribute or Property.",
                EntityHawkAttribute.ATTRIBUTE == attribute.getType() ? "Attribute" : "Property"));
        values.addElement(new AmiParameter("Name", "Name of the Attribute or Property.", attribute.getName()));

        String value = attribute.getValue();
        if ((attribute.getType() == EntityHawkAttribute.ATTRIBUTE)
                && EntityHawkAttribute.TYPE.equals(attribute.getName())) {
            final String prefix = BEProperties.getInstance().getString("be.codegen.rootPackage", "be.gen");
            if (value.startsWith(prefix)) {
                value = value.substring(prefix.length() + 1);
            }
        }
        values.addElement(new AmiParameter("Value", "Value of the Attribute or Property.", value));
    }


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final AmiParameterList values = new AmiParameterList();
            final String id = inParams.getString(1);
            final boolean isExternal = inParams.getBoolean(2).booleanValue();
            String sessionName = inParams.getString(0);

            if ((null == id) || "".equals(id.trim())) {
                throw new Exception("Please provide an event ID.");
            }

            RuleSession[] sessions;
            if ((null == sessionName) || "".equals(sessionName.trim())) {
                sessions = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSessions();
            } else {
                final RuleSession s = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSession(sessionName);
                if (null == s) {
                    throw new Exception("Invalid session name: " + sessionName);
                }
                sessions = new RuleSession[]{s};
            }

            int line = 0;
            for (int i = 0; i < sessions.length; i++) {
                final RuleSession session = sessions[i];
                sessionName = session.getName();
                final Event event = this.getEvent(id, isExternal, session);
                if (null != event) {
                    for (Iterator it = this.getEventAttributes(event).iterator(); it.hasNext(); line++) {
                        this.fillInOneReturnsEntry(values, line, sessionName, (EntityHawkAttribute) it.next());
                    }//for
                }//if
            }//for

            return values;
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke


    protected Event getEvent(String id, boolean isExternal, RuleSession session) throws Exception {
        if (isExternal) {
            return session.getObjectManager().getEvent(id);
        } else {
            return session.getObjectManager().getEvent(Long.parseLong(id));
        }
    }


    protected List getEventAttributes(Event event) {
        if (event instanceof SimpleEvent) {
            return this.getSimpleEventAttributes((SimpleEvent) event);
        }
        if (event instanceof TimeEvent) {
            return this.getTimeEventAttributes((TimeEvent) event);
        }
        return null;
    }


    protected List getSimpleEventAttributes(SimpleEvent simpleEvent) {
        List attrList = new LinkedList();
        EntityHawkAttribute attrib;
        attrib = new EntityHawkAttribute("id", String.valueOf(simpleEvent.getId()), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        attrib = new EntityHawkAttribute(EntityImpl.ATTRIBUTE_EXTID, simpleEvent.getExtId(), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        attrib = new EntityHawkAttribute("type", simpleEvent.getClass().getName(), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        //attrib = new EntityHawkAttribute("status", decodeStatus(), EntityHawkAttribute.ATTRIBUTE);  //todo - add this
        //attrList.add(attrib);
        attrib = new EntityHawkAttribute("ttl", String.valueOf(simpleEvent.getTTL() < 0 ? -1 : simpleEvent.getTTL()), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);

        //payload
        EventPayload obj = simpleEvent.getPayload();
        if (obj != null) {
            //todo SS- convert to XML?
            attrib = new EntityHawkAttribute("payload", obj.toString(), EntityHawkAttribute.ATTRIBUTE);
            attrList.add(attrib);

        }
//        attrList.add(attrib);
//        EventPayload ep= getPayload();
//        try {
//            if (ep != null) {
//                XiNode xiNode= ep.getNode();
//                Writer writer=new StringWriter();
//                DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(writer, "UTF-8");
//                xiNode.serialize(handler);
//                attrib = new EntityHawkAttribute("payload", writer.toString(), EntityHawkAttribute.ATTRIBUTE);
//                attrList.add(attrib);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //properties
        String[] names = simpleEvent.getPropertyNames();
        for (int i = 0; i < names.length; i++) {
            try {
                Object value = simpleEvent.getProperty(names[i]);
                if (value != null) {
                    attrib = new EntityHawkAttribute(names[i], value.toString(), EntityHawkAttribute.PROPERTY);
                    attrList.add(attrib);
                }
            }
            catch (NoSuchFieldException ex) {
                ex.printStackTrace();
            }
        }
        return attrList;
    }


    protected List getTimeEventAttributes(TimeEvent timeEvent) {
        List attrList = new LinkedList();
        EntityHawkAttribute attrib;
        attrib = new EntityHawkAttribute("id", String.valueOf(timeEvent.getId()), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        attrib = new EntityHawkAttribute(EntityImpl.ATTRIBUTE_EXTID, timeEvent.getExtId(), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        attrib = new EntityHawkAttribute("type", timeEvent.getClass().getName(), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        //attrib = new EntityHawkAttribute("status", decodeStatus(), EntityHawkAttribute.ATTRIBUTE);  //todo - add this
        //attrList.add(attrib);
        attrib = new EntityHawkAttribute("ttl", String.valueOf(timeEvent.getTTL() < 0 ? -1 : timeEvent.getTTL()), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        attrib = new EntityHawkAttribute("repeating", String.valueOf(timeEvent.isRepeating()), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        if (timeEvent.isRepeating()) { //static time event
            attrib = new EntityHawkAttribute("interval", String.valueOf(timeEvent.getInterval()), EntityHawkAttribute.ATTRIBUTE);
            attrList.add(attrib);
        } else {  //rule based time event
            attrib = new EntityHawkAttribute("scheduleTime", Format.formatCalendar(timeEvent.getScheduledTime()), EntityHawkAttribute.ATTRIBUTE);
            attrList.add(attrib);
            attrib = new EntityHawkAttribute("closure", timeEvent.getClosure(), EntityHawkAttribute.ATTRIBUTE);
            attrList.add(attrib);
        }

        return attrList;
    }
}//class


