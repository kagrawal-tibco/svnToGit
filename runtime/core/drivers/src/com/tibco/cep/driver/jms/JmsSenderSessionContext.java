package com.tibco.cep.driver.jms;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TemporaryQueue;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.impl.JMSSerializationContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 * @author mjinia
 * 
 */
public class JmsSenderSessionContext {

    private static final char PROPERTY_CHAR1 = 'J';
    private static final char PROPERTY_CHAR2 = 'M';
    private static final String PROPERTY_DELIVERY_MODE = JmsSenderSessionContext.PROPERTY_CHAR1 +
                                                         JmsSenderSessionContext.PROPERTY_CHAR2 + "SDeliveryMode";
    private static final String PROPERTY_EXPIRATION = JmsSenderSessionContext.PROPERTY_CHAR1 +
                                                      JmsSenderSessionContext.PROPERTY_CHAR2 + "SExpiration";
    private static final String PROPERTY_PRIORITY = JmsSenderSessionContext.PROPERTY_CHAR1 +
                                                    JmsSenderSessionContext.PROPERTY_CHAR2 + "SPriority";

    private final MessageProducer jmsMessageProducer;
    private final Session jmsSession;
    private final BaseJMSChannel jmsChannel;

    JmsSenderSessionContext(final BaseJMSChannel jmsChannel, final Session session) throws JMSException {

        this.jmsChannel = jmsChannel;
        this.jmsSession = session;
        this.jmsMessageProducer = this.jmsSession.createProducer(null);
    }

    private Logger getLogger() {
        return this.jmsChannel.getLogger();
    }

    private Message createMessage(final SimpleEvent event, final JMSDestination channelDestination,
                                  final Message request) throws Exception {

        final JMSSerializationContext ctx =
                new JMSSerializationContext(RuleSessionManager.getCurrentRuleSession(), channelDestination);
        ctx.setSession(this.jmsSession);
        ctx.setRequestMessage(request);

        return (Message) channelDestination.getEventSerializer().serialize(event, ctx);
    }

    private void overrideProperties(final SimpleEvent event, final Message msg, final Map<?, ?> destinationProperties,
                                    final JMSDestination channelDestination) throws Exception {

        boolean overrideDeliveryMode = true;
        boolean overridePriority = true;
        boolean overrideExpiration = true;

        for (final String name : event.getPropertyNames()) {
            if ((name.length() > 2) && (name.charAt(0) == JmsSenderSessionContext.PROPERTY_CHAR1) &&
                (name.charAt(1) == JmsSenderSessionContext.PROPERTY_CHAR2)) {

                if (name.equals(JmsSenderSessionContext.PROPERTY_DELIVERY_MODE)) {
                    overrideDeliveryMode = false;
                } else if (name.equals(JmsSenderSessionContext.PROPERTY_EXPIRATION)) {
                    overrideExpiration = false;
                } else if (name.equals(JmsSenderSessionContext.PROPERTY_PRIORITY)) {
                    overridePriority = false;
                }
            }
        }

        if (overrideDeliveryMode) {
            msg.setJMSDeliveryMode(channelDestination.getConfig().getJmsDeliveryMode());
        }

        if (overrideExpiration) {
            msg.setJMSExpiration(channelDestination.getConfig().getJmsTtl());
        }

        if (overridePriority) {
            msg.setJMSPriority(channelDestination.getConfig().getJmsPriority());
        }

        if (destinationProperties != null) {

            for (final Map.Entry<?, ?> entry : destinationProperties.entrySet()) {
                final String name = (String) entry.getKey();
                final Object value = entry.getValue();
                if ((null == name) || name.isEmpty() || (null == value)) {
                } else if ("jmsdeliverymode".equals(name)) {
                    msg.setJMSDeliveryMode((value instanceof String) ? Integer.valueOf((String) value)
                                                                    : (Integer) value);
                } else if ("jmspriority".equals(name)) {
                    msg.setJMSPriority((value instanceof String) ? Integer.valueOf((String) value) : (Integer) value);
                } else if ("jmsexpiration".equals(name)) {
                    msg.setJMSExpiration((value instanceof String) ? Long.valueOf((String) value) : (Long) value);
                } else if ("jmscorrelationid".equals(name)) {
                    msg.setJMSCorrelationID(String.valueOf(value));
                } else if (msg.getJMSMessageID() == null) {//check messageId is null to make sure that the message is newly created (and not a received message)
                    msg.setObjectProperty(name, value);
                }
            }
        }
    }

    int send(final Message msg, final Destination jmsDestination, final JMSDestination channelDestination)
            throws JMSException {

        final Channel.State channelState = channelDestination.getChannel().getState();

        if ((channelState == Channel.State.CONNECTED) || (channelState == Channel.State.STARTED)) {

            msg.setJMSDestination(jmsDestination);

            this.getLogger().log(Level.DEBUG, "Sending JMS msg %s", msg);

            this.jmsMessageProducer.send(jmsDestination, msg, msg.getJMSDeliveryMode(), msg.getJMSPriority(),
                                         msg.getJMSExpiration());

            this.getLogger().log(Level.DEBUG, "Sent JMS msg");

            channelDestination.getStats().addEventOut();
            return 0;
        } else {
            this.getLogger().log(Level.ERROR, "send failed, channel in an invalid state: %s", channelState);
            return -1;
        }
    }

    private String sendImmediate(final Message msg, final Destination destination,
                                 final JMSDestination channelDestination) throws Exception {

        final Channel.State channelState = channelDestination.getChannel().getState();

        if ((channelState == Channel.State.CONNECTED) || (channelState == Channel.State.STARTED)) {

            msg.setJMSDestination(destination);

            this.getLogger().log(Level.DEBUG, "Sending JMS msg %s", msg);

            this.jmsMessageProducer.send(destination, msg, msg.getJMSDeliveryMode(), msg.getJMSPriority(),
                                         msg.getJMSExpiration());

            this.getLogger().log(Level.DEBUG, "Sent JMS msg");

            channelDestination.getStats().addEventOut();
            return msg.getJMSMessageID();
        }

        else {
            throw new Exception("send failed, channel in an invalid state: " + channelState);
        }
    }

    public int send(final SimpleEvent event, final JmsMessageContext requestContext, final Destination destination,
                    final JMSDestination channelDestination, final Map destinationProperties) throws Exception {

        final Message message =
                this.createMessage(event, channelDestination,
                                   ((null == requestContext) ? null : requestContext.getMessage()));

        if (null != message) {
            this.overrideProperties(event, message, destinationProperties, channelDestination);
            return this.send(message, destination, channelDestination);
        } else {
            return -1;
        }
    }

    public String sendImmediate(final SimpleEvent event, final JmsMessageContext requestContext,
                                final javax.jms.Destination destination, final JMSDestination channelDestination,
                                final Map destinationProperties) throws Exception {

        final Message message =
                this.createMessage(event, channelDestination,
                                   ((null == requestContext) ? null : requestContext.getMessage()));

        if (null != message) {
            this.overrideProperties(event, message, destinationProperties, channelDestination);
            return this.sendImmediate(message, destination, channelDestination);
        } else {
            return null;
        }
    }
    
    /**
     * Sends the msg to specified destination and reads a response from a temporary (JMSReplyTo) queue.
     * @param msg The message to be sent.
     * @param destination Destination to send the message.
     * @param timeout Wait timeout in milliseconds for response.
     * @return Returns the received response message or null if no message received within timeout.
     * @throws Exception
     */
    private Message request(Message msg, Destination destination, final long timeout, JMSDestination channelDestination) throws Exception {
		Channel.State channelState = channelDestination.getChannel().getState();
		
		if ((channelState == Channel.State.CONNECTED)
				|| (channelState == Channel.State.STARTED)) {
			
			TemporaryQueue tempQueue = this.jmsSession.createTemporaryQueue();
			MessageConsumer consumer = this.jmsSession.createConsumer(tempQueue);
			
			msg.setJMSDestination(destination);
			msg.setJMSReplyTo(tempQueue);
			
			this.jmsMessageProducer.send(destination, msg, msg.getJMSDeliveryMode(), msg.getJMSPriority(), msg.getJMSExpiration());
			channelDestination.getStats().addEventOut();
			this.getLogger().log(Level.DEBUG, "Sent JMS msg");
			
			this.getLogger().log(Level.DEBUG, "Reading response msg..");
			Message responseMsg = timeout == -1 ? consumer.receive() : (timeout == 0 ? consumer.receiveNoWait() : consumer.receive(timeout));
			this.getLogger().log(Level.DEBUG, "Response msg received %s", msg);
			
			consumer.close();
			tempQueue.delete();
			
			return responseMsg;
		}
		else {
			this.getLogger().log(Level.WARN, "Channel in an invalid state: %s", channelState);
			return null;
		}
    }
    
    /**
     * Sends the JMS event to specified destination and reads a response. The response is deserialized using <code>referenceChannelDestination</code> configurations.
     * @param event
     * @param requestContext
     * @param destination
     * @param channelDestination
     * @param referenceChannelDestination
     * @param destinationProperties
     * @param timeout
     * @return Received JMS message if <code>referenceChannelDestination</code> is null otherwise the deserialized SimpleEvent. 
     */
    public Object request(SimpleEvent event, JmsMessageContext requestContext, Destination destination, JMSDestination channelDestination,
    		JMSDestination referenceChannelDestination, Map destinationProperties, long timeout) {
    	try {
    		final Message message = this.createMessage(event, channelDestination,
    				((null == requestContext) ? null : requestContext.getMessage()));
    		
    		if (null != message) {
    			this.overrideProperties(event, message, destinationProperties, channelDestination);
    			Message resp = this.request(message, destination, timeout, channelDestination);
    			return resp == null ? null : (referenceChannelDestination == null ? resp : JmsSessionContext.createEvent(resp, referenceChannelDestination));
    		}
    		else {
    			return null;
    		}
    	}
    	catch(Exception e) {
    		this.getLogger().log(Level.ERROR, e, "Exception while request message.");
    		return null;
    	}
    }
}
