package com.tibco.cep.driver.jms.serializer;

import com.tibco.be.model.types.Converter;
import com.tibco.cep.driver.jms.JMSDestination;
import com.tibco.cep.driver.jms.JmsDestinationConfig;
import com.tibco.cep.driver.jms.JmsHeader;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.EventSerializer;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.JMSSerializationContext;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.xml.data.primitive.ExpandedName;

import javax.jms.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * User: nicolas
 * Date: 7/15/14
 * Time: 2:55 PM
 */
public abstract class AbstractMessageSerializer<M extends Message>
        implements EventSerializer
{


    protected Logger logger;


    @SuppressWarnings("UnusedParameters")
    protected String convertPropertyName(
            String propertyName,
            SerializationContext ctx)
    {
        return propertyName;
    }


    @SuppressWarnings("UnusedParameters")
    protected Object convertPropertyValue(
            Object value,
            JMSSerializationContext context)
    {
        return (value instanceof Calendar)
                ? Converter.writeCalendar((Calendar) value)
                : value;
    }


    protected SimpleEvent createEvent(
            M message,
            SerializationContext context)
            throws Exception
    {
        ExpandedName en = null;

        if (((JmsDestinationConfig) context.getDestination().getConfig()).isDeserializingEventType()) {
            final String namespace = (String) JmsHeader.BE_NAMESPACE.readFrom(message);
            final String name = (String) JmsHeader.BE_NAME.readFrom(message);

            if ((null != namespace) && (null != name)) {
                en = ExpandedName.makeName(namespace, name);
            }
        }

        if (null == en) {
            en = context.getDestination().getConfig().getDefaultEventURI();
        }

        if (null == en) {
        	throw new Exception("EventType not found in received event, nor a default Event is configured in destination - " + context.getDestination().getURI());
        }

        return (SimpleEvent) context.getRuleSession().getRuleServiceProvider()
                .getTypeManager().createEntity(en);
    }


    protected abstract M createMessage(
            Session session,
            JMSSerializationContext ctx)
            throws Exception;


    @Override
    public SimpleEvent deserialize(
            Object message,
            SerializationContext context)
            throws Exception
    {
        final Message msg = (Message) message;
        final AbstractMessageSerializer deserializer = DeserializerProvider.getInstance().getDeserializer(msg);

        if (null == deserializer) {
        	if(((JMSDestination)context.getDestination()).handleFailedDeserialization(msg)) {
        		return null;
        	} else {
        		throw new Exception("Not supporting JMS Message type " + msg.getClass() + ", message =" + msg);
        	}
        }

        return deserializer.deserialize(msg, context);
    }


    protected SimpleEvent deserialize(
            Message message,
            SerializationContext context)
            throws Exception
    {
        @SuppressWarnings("unchecked")
        final M msg = (M) message;
        final SimpleEvent event = this.createEvent(msg, context);

        this.deserializeExtId(event, msg, context);
        this.deserializeProperties(event, msg, context);
        this.deserializePayload(event, msg, context);

        return event;
    }


    @SuppressWarnings("UnusedParameters")
    protected void deserializeExtId(
            SimpleEvent event,
            M msg,
            SerializationContext context)
            throws Exception
    {
        event.setExtId((String) JmsHeader.BE_EXTID.readFrom(msg));
    }


    protected void deserializeProperties(
            SimpleEvent event,
            M msg,
            SerializationContext context)
            throws Exception
    {
        for (final String name : event.getPropertyNames()) {
            final JmsHeader header = JmsHeader.getByName(name);

            if (null != header) {
                final Object value = header.readFrom(msg);

                if (null != value) {
                    switch (header) {
                        case REPLY_TO:
                            this.setEventProperty(event, name, this.getDestinationName((Destination) value));
                            break;
                        case DESTINATION:
                            this.setEventProperty(event, name, this.getDestinationName((Destination) value));
                            break;
                        default:
                            this.setEventProperty(event, name, value);
                    }
                }
            }
            else if (name.toLowerCase().startsWith("jms")) {
                this.setEventProperty(event, name, this.readProperty(msg, name));
            }
            else if (!this.isReservedProperty(name)) {
                this.setEventProperty(event, name, this.readProperty(msg, this.convertPropertyName(name, context)));
            }
        }
    }


    protected abstract void deserializePayload(
            SimpleEvent event,
            M message,
            SerializationContext context)
            throws Exception;


    protected String getDestinationName(
            Destination destination)
            throws Exception
    {
        if (destination instanceof Queue) {
            return ((Queue) destination).getQueueName();
        }
        else if (destination instanceof Topic) {
            return ((Topic) destination).getTopicName();
        }
        else {
            return null;
        }
    }


    protected Destination getJMSReplyToDestination(
            SerializationContext context,
            String destinationName)
            throws Exception
    {
        final Map<Object, Object> map = new HashMap<>();
        map.put("name", destinationName);

        return ((JMSDestination) context.getDestination())
                .lookUpOrCreateDestination(map);
    }


    @Override
    public void init(
            ChannelManager channelManager,
            DestinationConfig config)
    {
        this.logger = channelManager.getRuleServiceProvider().getLogger(this.getClass());
    }


    protected boolean isReservedProperty(
            String property)
    {
        final String lower = property.toLowerCase();
        return lower.startsWith("jms")
                || lower.startsWith("_jms")
                || lower.startsWith("$")
                || lower.equals(JmsHeader.BE_NAME.getName())
                || lower.equals(JmsHeader.BE_NAMESPACE.getName());
    }


    protected Object readProperty(
            M msg,
            String name)
            throws JMSException
    {
        return msg.getObjectProperty(name);
    }


    @Override
    public Object serialize(
            SimpleEvent event,
            SerializationContext context)
            throws Exception
    {
        final JMSSerializationContext jmsContext = (JMSSerializationContext) context;
        
        //Create message using incoming event context only when, 
        //event is not modified & channel destination is same
    	final EventContext evCtx = event.getContext();

    	if (evCtx != null && !evCtx.isEventModified() && evCtx.getDestination() != null && evCtx.getDestination().getURI().equals(jmsContext.getDestination().getURI())) {
            
    		final Object message = evCtx.getMessage();
            if (message != null) {
                return this.substituteMessage(message, event, context);
            }
        }
    	
        //Otherwise create a new message using destination serializer
        final M message = this.createMessage(jmsContext.getSession(), jmsContext);
        this.serializeEventType(event, message, jmsContext);
        this.serializeProperties(event, message, jmsContext);
        this.serializePayload(event, message, jmsContext);
        this.serializeExtId(event, message, jmsContext);

        return message;
    }


    @SuppressWarnings("UnusedParameters")
    protected void serializeEventType(
            SimpleEvent event,
            M msg,
            JMSSerializationContext context)
            throws JMSException
    {
        if (((JmsDestinationConfig) context.getDestination().getConfig()).isSerializingEventType()) {
            final ExpandedName en = event.getExpandedName();
            JmsHeader.BE_NAME.writeTo(msg, en.getLocalName());
            JmsHeader.BE_NAMESPACE.writeTo(msg, en.getNamespaceURI());
        }
    }


    @SuppressWarnings("UnusedParameters")
    protected void serializeExtId(
            SimpleEvent event,
            M msg,
            JMSSerializationContext context)
            throws JMSException
    {
        if (event.getExtId() != null) {
            JmsHeader.BE_EXTID.writeTo(msg, event.getExtId());
        }
    }


    protected abstract void serializePayload(
            SimpleEvent event,
            M msg,
            JMSSerializationContext context)
            throws Exception;


    protected void serializeProperties(
            SimpleEvent event,
            M msg,
            JMSSerializationContext context)
            throws Exception
    {
        final boolean providerPropertiesAllowed = true;//!((JMSDestination) context.getDestination()).isMQ();
        
        for (final String name : event.getPropertyNames()) {
            final Object value = event.getProperty(name);

            if (null != value) {
                final JmsHeader header = JmsHeader.getByName(name);

                if (null != header) {
                    switch (header) {
                        case REPLY_TO:
                            header.writeTo(msg, this.getJMSReplyToDestination(context, value.toString()));
                            break;
                        case DESTINATION:
                            // Not serialized.
                            break;
                        default:
                            header.writeTo(msg, value);
                    }
                }
                else if (name.toLowerCase().startsWith("jms_")) {
                    if (providerPropertiesAllowed) {
                        this.writeProperty(msg, name, value, context);
                    }
                }
                else if (!this.isReservedProperty(name)) {
                    this.writeProperty(msg, this.convertPropertyName(name, context), value, context);
                }
            }
        }
    }


    protected void setEventProperty(
            SimpleEvent event,
            String name,
            Object value)
            throws Exception
    {
        if (null != value) {
            try {
                event.setProperty(name, value);
            } catch (RuntimeException e) {
                throw new RuntimeException("Failed to set event property '" + name + "'", e);
            } catch (Exception e) {
                throw new Exception("Failed to set event property '" + name + "'", e);
            }
        }
    }


    @SuppressWarnings("UnusedParameters")
    protected Object substituteMessage(
            Object msg,
            SimpleEvent ev,
            SerializationContext ctx)
    {
        return msg;
    }


    protected void writeProperty(
            M msg,
            String name,
            Object value,
            JMSSerializationContext context)
            throws JMSException
    {
        msg.setObjectProperty(name, this.convertPropertyValue(value, context));
    }

}
