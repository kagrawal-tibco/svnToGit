package com.tibco.cep.driver.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


/**
 * User: nicolas
 * Date: 7/23/14
 * Time: 3:52 PM
 */
public enum JmsHeader
{


    BE_EXTID("_extId_") {

        @Override
        public String readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getStringProperty(this.getName());
        }

        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            msg.setStringProperty(this.getName(), String.valueOf(o));
        }
    },


    BE_NAME("_nm_") {

        @Override
        public String readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getStringProperty(this.getName());
        }

        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            msg.setStringProperty(this.getName(), String.valueOf(o));
        }
    },


    BE_NAMESPACE("_ns_") {

        @Override
        public String readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getStringProperty(this.getName());
        }

        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            msg.setStringProperty(this.getName(), String.valueOf(o));
        }
    },


    CORRELATION_ID("JMSCorrelationID") {

        @Override
        public String readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getJMSCorrelationID();
        }

        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            msg.setJMSCorrelationID(String.valueOf(o));
        }
    },


    DELIVERY_COUNT("JMSXDeliveryCount") {

        @Override
        public Integer readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getObjectProperty(this.getName()) != null ? msg.getIntProperty(this.getName()) : null;
        }

        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            if (null != o) {
                msg.setIntProperty(this.getName(), ((Number) o).intValue());
            }
        }
    },


    DELIVERY_MODE("JMSDeliveryMode") {

        @Override
        public Integer readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getJMSDeliveryMode();
        }

        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            msg.setJMSDeliveryMode(
                    (null == o) ? Message.DEFAULT_DELIVERY_MODE : ((Number) o).intValue());
        }
    },


    DESTINATION("JMSDestination") {

        @Override
        public Destination readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getJMSDestination();
        }


        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            msg.setJMSDestination((Destination) o);
        }
    },


    EXPIRATION("JMSExpiration") {

        @Override
        public Long readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getJMSExpiration();
        }

        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            if (null != o) {
                msg.setJMSExpiration(((Number) o).longValue());
            }
        }
    },


    GROUP_ID("JMSXGroupID") {

        @Override
        public Object readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getStringProperty(this.getName());
        }

        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            msg.setStringProperty(this.getName(), String.valueOf(o));
        }
    },


    GROUP_SEQ("JMSXGroupSeq") {

        @Override
        public Integer readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getObjectProperty(this.getName()) != null ? msg.getIntProperty(this.getName()) : null;
        }


        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            if (null != o) {
                msg.setIntProperty(this.getName(), ((Number) o).intValue());
            }
        }
    },


    MESSAGE_ID("JMSMessageID") {

        @Override
        public String readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getJMSMessageID();
        }

        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            msg.setJMSMessageID(String.valueOf(o));
        }
    },


    PRIORITY("JMSPriority") {

        @Override
        public Integer readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getJMSPriority();
        }


        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            msg.setJMSPriority((null == o) ? Message.DEFAULT_PRIORITY : ((Number) o).intValue());
        }
    },


    REDELIVERED("JMSRedelivered") {
        @Override
        public Boolean readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getJMSRedelivered();
        }


        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            msg.setJMSRedelivered((Boolean) o);
        }
    },


    REPLY_TO("JMSReplyTo") {

        @Override
        public Destination readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getJMSReplyTo();
        }

        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            msg.setJMSReplyTo((Destination) o);
        }
    },


    TIMESTAMP("JMSTimestamp") {

        @Override
        public Long readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getJMSTimestamp();
        }


        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            if (null != o) {
                msg.setJMSTimestamp(((Number) o).longValue());
            }
        }
    },


    TYPE("JMSType") {

        @Override
        public String readFrom(
                Message msg)
                throws JMSException
        {
            return msg.getJMSType();
        }


        @Override
        public void writeTo(
                Message msg,
                Object o)
                throws JMSException
        {
            msg.setJMSType(String.valueOf(o));
        }
    },


    ;


    private static final Map<String, JmsHeader> NAME_TO_ENUM = new HashMap<>();


    static {
        for (final JmsHeader h : EnumSet.allOf(JmsHeader.class)) {
            NAME_TO_ENUM.put(h.name, h);
        }
    }


    private final String name;


    private JmsHeader(
            final String name)
    {
        this.name = name;
    }


    public static JmsHeader getByName(
            String name)
    {
        return NAME_TO_ENUM.get(name);
    }


    public String getName()
    {
        return this.name;
    }


    public abstract Object readFrom(
            Message msg)
            throws JMSException;


    public abstract void writeTo(
            Message msg,
            Object o)
            throws JMSException;


}
