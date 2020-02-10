package com.tibco.cep.loadbalancer.impl.message;

import java.util.Enumeration;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;

import com.tibco.cep.driver.jms.JmsHeader;

/*
* Author: Ashwin Jayaprakash / Date: Mar 19, 2010 / Time: 2:56:38 PM
*/
public class DistributableJmsMessage extends SimpleDistributableMessage {
    public DistributableJmsMessage() {
    }

    public DistributableJmsMessage(Message content, String keySeed) throws JMSException {
        super(content, extract(JmsHeader.MESSAGE_ID, content), keySeed);
    }

    public DistributableJmsMessage(Message content, String keySeed, int numKeysToMake) throws JMSException {
        super(content, extract(JmsHeader.MESSAGE_ID, content), keySeed, numKeysToMake);
    }

    private static String extract(JmsHeader jmsHeader, Message message) throws JMSException {
        Object o = jmsHeader.readFrom(message);

        return (o == null) ? null : o.toString();
    }

    @Override
    public String getHeaderValue(String key) {
        try {
            Message message = getContent();

            //Test if this is a user defined header.
            String value = message.getStringProperty(key);
            if (value != null) {
                return value;
            }

            JmsHeader jmsHeader = JmsHeader.valueOf(key);

            return extract(jmsHeader, message);
        }
        catch (IllegalArgumentException e) {
            //Ignore.
        }
        catch (JMSException e) {
            //Ignore.
        }

        return super.getHeaderValue(key);
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> map = super.getHeaders();

        Message message = null;
        try {
            message = getContent();
        }
        catch (Exception e) {
            //Ignore.

            return map;
        }

        try {
            Enumeration enumeration = message.getPropertyNames();

            while (enumeration.hasMoreElements()) {
                String name = enumeration.nextElement().toString();
                String value = message.getStringProperty(name);

                map.put(name, value);
            }
        }
        catch (JMSException e) {
            //Ignore.
        }

        for (JmsHeader jmsHeader : JmsHeader.values()) {
            try {
                String value = extract(jmsHeader, message);

                map.put(jmsHeader.name(), value);
            }
            catch (JMSException e) {
                //Ignore.
            }
        }

        return map;
    }

    @Override
    public Message getContent() {
        return (Message) content;
    }

    //----------------
}