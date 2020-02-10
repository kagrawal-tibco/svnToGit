package com.tibco.cep.channel;



import com.tibco.cep.driver.jms.JMSDestination;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.EventSerializer;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.data.primitive.ExpandedName;

import javax.jms.BytesMessage;

/**
 * User: Puneet Nayyar
 * Date: October 11, 2005
 * Time: 5:47:53 PM
 */

/**
 * @.synopsis This class provides a sample implementation of serialize and deserialize
 * function for a JMS MapMessage.
 */

public class JMSByteSerializeDeserializeHelper implements EventSerializer {

    Channel.Destination dest = null;


    public SimpleEvent deserialize(Object msg,  SerializationContext context) throws Exception {

        System.out.println("<JMS Byte deSerialize>");
        RuleSession ruleSession = context.getRuleSession();
        ExpandedName name  = ((JMSDestination)dest).getConfig().getDefaultEventURI();
        SimpleEvent event = (SimpleEvent)ruleSession.getRuleServiceProvider().getTypeManager().createEntity(name);
        String[] pnames = event.getPropertyNames();
        BytesMessage mmsg = (BytesMessage)msg;
        // set the event properties if they exist on the 'event'.
        for (int i=0; i<pnames.length; i++) {
            String pname = pnames[i];
            if (pname.equals("JMSCorrelationID"))     event.setProperty(pname, mmsg.getJMSCorrelationID());
            else if (pname.equals("JMSDeliveryMode")) event.setProperty(pname, new Integer(mmsg.getJMSDeliveryMode()));
            else if (pname.equals("JMSExpiration"))   event.setProperty(pname, new Long(mmsg.getJMSExpiration()));
            else if (pname.equals("JMSDestination"))  event.setProperty(pname, mmsg.getJMSDestination().toString());
            else if (pname.equals("JMSMessageID"))    event.setProperty(pname, mmsg.getJMSMessageID());
            else if (pname.equals("JMSPriority"))     event.setProperty(pname, new Integer(mmsg.getJMSPriority()));
            else if (pname.equals("JMSRedelivered"))  event.setProperty(pname, new Boolean(mmsg.getJMSRedelivered()));
            else if (pname.equals("JMSTimestamp"))    event.setProperty(pname, new Long(mmsg.getJMSTimestamp()));
            else if (pname.equals("JMSType"))         event.setProperty(pname, event.getProperty(pname));
            else event.setProperty(pname, mmsg.getObjectProperty(pname));
        }
        byte [] b=new byte[(int) ((BytesMessage) msg).getBodyLength()];
        mmsg.readBytes(b);
        //event.setPayload(new XiNodePayload(b, null));
        return event;

    }

    public Object serialize(SimpleEvent event, SerializationContext context) throws Exception {
        System.out.println("<JMS Bytes serialize>");

        BytesMessage msg = ((JMSDestination)dest).getSession().createBytesMessage();
        // set the Message properties if they exist on the event.

        String [] pnames = event.getPropertyNames();
        for (int i=0; i<pnames.length; i++) {
            String pname = pnames[i];
            if (event.getProperty(pname)==null) continue;
            else if (pname.equals("JMSCorrelationID"))msg.setJMSCorrelationID((String)event.getProperty(pname));
            else if (pname.equals("JMSDeliveryMode")) msg.setJMSDeliveryMode(((Integer)event.getProperty(pname)).intValue());
            else if (pname.equals("JMSExpiration"))   msg.setJMSExpiration(((Long)event.getProperty(pname)).longValue());
            else if (pname.equals("JMSMessageID"))    msg.setJMSMessageID((String)event.getProperty(pname));
            else if (pname.equals("JMSPriority"))     msg.setJMSPriority(((Integer)event.getProperty(pname)).intValue());
            else if (pname.equals("JMSRedelivered"))  msg.setJMSRedelivered(((Boolean)event.getProperty(pname)).booleanValue());
            else if (pname.equals("JMSTimestamp"))    msg.setJMSTimestamp(((Long)event.getProperty(pname)).longValue());
            else if (pname.equals("JMSType"))         msg.setJMSType((String)event.getProperty(pname));
            else msg.setObjectProperty(pname, (event.getProperty(pname)));
        }

        return msg;
    }

    public void setDestination(Channel.Destination dest) {
        this.dest = dest;
    }



}
