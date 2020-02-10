package com.tibco.cep.driver.jms;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.DefaultSerializationContext;
import com.tibco.cep.runtime.channel.impl.JMSSerializationContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.util.SystemProperty;

import org.apache.commons.collections4.map.LRUMap;

import javax.jms.*;

import java.util.*;
import java.util.regex.Pattern;

/**
 * User: nprade
 * Date: 9/13/12
 * Time: 4:49 PM
 */
public class JmsSessionContext {


    private static final char PROPERTY_CHAR1 = 'J';
    private static final char PROPERTY_CHAR2 = 'M';
    private static final String PROPERTY_DELIVERY_MODE = Character.toString(PROPERTY_CHAR1) + Character.toString(PROPERTY_CHAR2) + "SDeliveryMode";
    private static final String PROPERTY_EXPIRATION = Character.toString(PROPERTY_CHAR1) + Character.toString(PROPERTY_CHAR2) + "SExpiration";
    private static final String PROPERTY_PRIORITY = Character.toString(PROPERTY_CHAR1) + Character.toString(PROPERTY_CHAR2) + "SPriority";
    private static final int WAIT_FOR_CONSUMER_CREATION = 1000;


    private final JMSDestination destination;
    private final Destination jmsDestination;
    private MessageConsumer jmsMessageConsumer;
    private final MessageProducer jmsMessageProducer;
    private final Session jmsSession;

    @SuppressWarnings("unchecked")
    protected final Map<String, Destination> nameToQueue = Collections.synchronizedMap(new LRUMap(Integer.parseInt(
            System.getProperty(SystemProperty.DESTINATION_QUEUE_CACHE_SIZE.getPropertyName(), "100"))));

    @SuppressWarnings("unchecked")
    protected final Map<String, Destination> nameToTopic = Collections.synchronizedMap(new LRUMap(Integer.parseInt(
            System.getProperty(SystemProperty.DESTINATION_TOPIC_CACHE_SIZE.getPropertyName(), "100"))));

    private final List<PendingMessageContext> pendingMessageContexts = new LinkedList<PendingMessageContext>();
    private final JmsSessionContextProvider provider;


    JmsSessionContext(
            JmsSessionContextProvider provider,
            JMSDestination destination,
            Session session)
            throws JMSException {

        this.destination = destination;
        this.jmsSession = session;
        this.jmsMessageProducer = this.jmsSession.createProducer(null);

        //BE-26960: Set delivery delay when configured
		final String propertyName = SystemProperty.JMS_DESTINATION_DELIVERY_DELAY.getPropertyName()
				.replaceAll(Pattern.quote("${destination_uri}"), destination.getURI());
		long deliveryDelay = Long.parseLong(System.getProperty(propertyName, "0"));
        if(deliveryDelay > 0) {
        	 this.jmsMessageProducer.setDeliveryDelay(deliveryDelay);
        }
        
        final String destinationName = this.destination.getConfig().getJmsDestinationName();

        final boolean isQueue = this.destination.getConfig().isQueue();

        final Destination destinationFromJndi = this.destination.getDestinationFromJndi(destinationName);
        if (null == destinationFromJndi) { // Creates it locally if not available, 1.2 behavior.
            this.jmsDestination = isQueue
                    ? this.jmsSession.createQueue(destinationName)
                    : this.jmsSession.createTopic(destinationName);
        }
        else {
            this.jmsDestination = destinationFromJndi;
        }

        if (isQueue) {
            this.nameToQueue.put(destinationName, this.jmsDestination);
        } else {
            this.nameToTopic.put(destinationName, this.jmsDestination);
        }

        this.provider = provider;
    }


    public void createConsumer(
            String jmsSelector,
            String ruleSessionName)
            throws JMSException {

        final JmsDestinationConfig destinationConfig = this.destination.getConfig();
        String sharedSubscriptionName = destinationConfig.getSharedSubscriptionName();

        if (destinationConfig.isQueue()) {
            this.jmsMessageConsumer = this.jmsSession.createConsumer(this.jmsDestination, jmsSelector);

        }
        else if ((null == destinationConfig.getJmsDurableName())
                || destinationConfig.getJmsDurableName().trim().isEmpty()) {

        	if(sharedSubscriptionName!=null && sharedSubscriptionName.trim().isEmpty()){
        		this.jmsMessageConsumer = this.jmsSession.createConsumer(this.jmsDestination, jmsSelector, false);
        	}else{
        		this.jmsMessageConsumer = this.jmsSession.createSharedConsumer((Topic)this.jmsDestination, destinationConfig.getSharedSubscriptionName(), jmsSelector);
        	}

        }
        else {
            final String jmsDurableSubscriberName = destinationConfig.getJmsDurableName()
                    .replaceAll("%%SessionName%%", ruleSessionName)
                    .replaceAll(" ", "_");

            this.getLogger().log(Level.INFO, "Durable Name : " + jmsDurableSubscriberName);
            this.jmsMessageConsumer = null;
            for (int attempt = 1; (null == this.jmsMessageConsumer); attempt++) {
                try {
                	if(sharedSubscriptionName==null || sharedSubscriptionName.trim().isEmpty()){
	                    this.jmsMessageConsumer = this.jmsSession.createDurableSubscriber(
	                            (Topic) this.jmsDestination,
	                            jmsDurableSubscriberName,
	                            jmsSelector,
	                            false);
                	}else{
                		this.jmsMessageConsumer = this.jmsSession.createSharedDurableConsumer((Topic)this.jmsDestination, destinationConfig.getSharedSubscriptionName(), jmsSelector);
                	}
                }
                catch (JMSException e) {
                    try {
                        if (10 == attempt) {
                            this.getLogger().log(Level.ERROR,
                                    "Exception while creating consumer. Tried %d times, giving up.", attempt);
                            throw e;
                        }
                        else {
                            this.getLogger().log(Level.WARN, e,
                                    "Exception while creating consumer, attempt #%d. " +
                                            "It could be a configuration issue, " +
                                            "or the JMS Server connection may be severed on the primary.",
                                    attempt);
                            Thread.sleep(WAIT_FOR_CONSUMER_CREATION);
                        }
                    }
                    catch (Exception inner) {
                        throw e;
                    }
                }
            }
        }
    }


    private Message createMessage(
            SimpleEvent event,
            JMSDestination channelDestination,
            Message request)
            throws Exception {

        if (null == channelDestination) {
            channelDestination = this.destination;
        }

        final JMSSerializationContext ctx = new JMSSerializationContext(
                RuleSessionManager.getCurrentRuleSession(),
                channelDestination);
        ctx.setSession(this.jmsSession);
        ctx.setRequestMessage(request);

        return (Message) channelDestination.getEventSerializer().serialize(event, ctx);
    }
    
    /**
     * Deserialize the message and return the Event instance for it.
     * @param message
     * @param channelDestination
     * @return
     * @throws Exception
     */
    public static SimpleEvent createEvent(Message message, JMSDestination channelDestination) throws Exception {
    	SerializationContext sci = new DefaultSerializationContext(RuleSessionManager.getCurrentRuleSession(), channelDestination);
    	return channelDestination.getEventSerializer().deserialize(message, sci);
    }
    
    public void destroy() {
        if (null != this.jmsSession) {
            try {
                this.getLogger().log(Level.DEBUG,
                        "Destroying the JMS session for destination %s",
                        this.destination.getURI());

                this.jmsSession.close();
            }
            catch (JMSException e) {
                this.getLogger().log(Level.DEBUG, e,
                        "Error while destroying the JMS session for destination %s",
                        this.destination.getURI());
            }
        }
    }


    public Destination getJmsDestination() {
        return this.jmsDestination;
    }


    public Destination getJmsDestination(
            String destinationName,
            boolean isQueue)
            throws Exception {

        if ((null == destinationName) || destinationName.isEmpty()) {
            destinationName = this.destination.getConfig().getJmsDestinationName();
        }

        final Map<String, Destination> destinationCache = isQueue ? this.nameToQueue : this.nameToTopic;
        Destination newDestination = destinationCache.get(destinationName);

        if (null == newDestination) {
            newDestination = this.destination.getDestinationFromJndi(destinationName);

            if (null == newDestination) {
                // If not available, creates it in local server, 1.2 behavior.

                final BaseJMSChannel channel = this.destination.getChannel();

                if (isQueue) {
                    if ((!channel.hasQueue()) && channel.getConfig().isUseJNDI()) {
                        throw new Exception("Channel is configured to use JNDI for topic connection factory only "
                                + "but  override property is specified to use queue. Message will not be sent.");
                    }
                    newDestination = this.jmsSession.createQueue(destinationName);
                }
                else {
                    if ((!channel.hasTopic()) && channel.getConfig().isUseJNDI()) {
                        throw new Exception("Channel is configured to use JNDI for queue connection factory only "
                                + "but override property is specified to use topic. Message will not be sent.");
                    }
                    newDestination = this.jmsSession.createTopic(destinationName);
                }
            }

            destinationCache.put(destinationName, newDestination);
        }

        return newDestination;
    }


    public MessageConsumer getJmsMessageConsumer() {
        return this.jmsMessageConsumer;
    }


//    public MessageProducer getJmsMessageProducer() {
//        return this.jmsMessageProducer;
//    }


    public Session getJmsSession()
            throws JMSException {

        return this.jmsSession;
    }


    private Logger getLogger() {
        return this.destination.getLogger();
    }


    private void overrideProperties(
            SimpleEvent event,
            Message msg,
            Map<?, ?> destinationProperties)
            throws Exception {

        boolean overrideDeliveryMode = true;
        boolean overridePriority = true;
        boolean overrideExpiration = true;

        for (final String name : event.getPropertyNames()) {
            if ((name.length() > 2)
                    && (name.charAt(0) == PROPERTY_CHAR1)
                    && (name.charAt(1) == PROPERTY_CHAR2)) {

                if (name.equals(PROPERTY_DELIVERY_MODE)) {
                    overrideDeliveryMode = false;
                }
                else if (name.equals(PROPERTY_EXPIRATION)) {
                    overrideExpiration = false;
                }
                else if (name.equals(PROPERTY_PRIORITY)) {
                    overridePriority = false;
                }
            }
        }

        if (overrideDeliveryMode) {
            msg.setJMSDeliveryMode(this.destination.getConfig().getJmsDeliveryMode());
        }

        if (overrideExpiration) {
            msg.setJMSExpiration(this.destination.getConfig().getJmsTtl());
        }

        if (overridePriority) {
            msg.setJMSPriority(this.destination.getConfig().getJmsPriority());
        }

        if (destinationProperties != null) {

            for (final Map.Entry<?, ?> entry: destinationProperties.entrySet()) {
                final String name = (String) entry.getKey();
                final Object value = entry.getValue();
                if ((null == name) || name.isEmpty() || (null == value)) {
                }
                else if ("jmsdeliverymode".equals(name)) {
                    msg.setJMSDeliveryMode((value instanceof String)
                            ? Integer.valueOf((String) value)
                            : (Integer) value);
                }
                else if ("jmspriority".equals(name)) {
                    msg.setJMSPriority((value instanceof String)
                            ? Integer.valueOf((String) value)
                            : (Integer) value);
                }
                else if ("jmsexpiration".equals(name)) {
                    msg.setJMSExpiration((value instanceof String)
                            ? Long.valueOf((String) value)
                            : (Long) value);
                }
                else if ("jmscorrelationid".equals(name)) {
                    msg.setJMSCorrelationID(String.valueOf(value));
                }
                else if ("jmsreplyto".equals(name)) {
                	Map<Object, Object> map = new HashMap<>();
                    map.put("name", String.valueOf(value));
                    Destination destination = this.destination.lookUpOrCreateDestination(map);
                    msg.setJMSReplyTo(destination);
                }
                else if (msg.getJMSMessageID() == null) {//check messageId is null to make sure that the message is newly created (and not a received message)
                    msg.setObjectProperty(name, value);
                }
            }
        }
    }


    int send(
            Message msg,
            Destination destination)
            throws JMSException {

        final Channel.State channelState = this.destination.getChannel().getState();

        if ((channelState == Channel.State.CONNECTED)
                || (channelState == Channel.State.STARTED)) {

            msg.setJMSDestination(destination);

            this.getLogger().log(Level.DEBUG, "Sending JMS msg %s", msg);

            this.jmsMessageProducer.send(destination, msg,
                    msg.getJMSDeliveryMode(),
                    msg.getJMSPriority(),
                    msg.getJMSExpiration());

            this.getLogger().log(Level.DEBUG, "Sent JMS msg");

            this.destination.getStats().addEventOut();
            return 0;
        }

        else if (channelState == Channel.State.INITIALIZED) {

            //Queue it for later use and send log info
            //TODO - Fixed Size queue implementation. and Roll off the top
            this.getLogger().log(Level.DEBUG, "Channel not started, queuing message for later delivery.");
            this.pendingMessageContexts.add(new PendingMessageContext(msg, destination));

            return 1;
        }

        else {
            this.getLogger().log(Level.ERROR, "Channel in an invalid state: %s", channelState);
            return -1;
        }
    }


    public int send(
            SimpleEvent event,
            JmsMessageContext requestContext,
            Destination destination,
            JMSDestination channelDestination,
            Map destinationProperties)
            throws Exception {

        synchronized (this.jmsSession) {
            final Message message = this.createMessage(event, channelDestination,
                    ((null == requestContext) ? null : requestContext.getMessage()));

            if (null != message) {
                this.overrideProperties(event, message, destinationProperties);
                return this.send(message, destination);
            }
            else {
                return -1;
            }
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
    private Message request(Message msg, Destination destination, final long timeout) throws Exception {
		Channel.State channelState = this.destination.getChannel().getState();
		
		if ((channelState == Channel.State.CONNECTED)
				|| (channelState == Channel.State.STARTED)) {
			
			TemporaryQueue tempQueue = this.jmsSession.createTemporaryQueue();
			MessageConsumer consumer = this.jmsSession.createConsumer(tempQueue);
			
			msg.setJMSDestination(destination);
			msg.setJMSReplyTo(tempQueue);
			
			this.jmsMessageProducer.send(destination, msg, msg.getJMSDeliveryMode(), msg.getJMSPriority(), msg.getJMSExpiration());
			this.destination.getStats().addEventOut();
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
    		Message response = null;
	    	synchronized (this.jmsSession) {
	    		final Message message = this.createMessage(event, channelDestination,
	    				((null == requestContext) ? null : requestContext.getMessage()));
	    		
	    		if (null != message) {
	    			this.overrideProperties(event, message, destinationProperties);
	    			response = this.request(message, destination, timeout);
	    		}
	    	}
	    	return response == null ? null : (referenceChannelDestination == null ? response : createEvent(response, referenceChannelDestination));
    	}
    	catch(Exception e) {
    		this.getLogger().log(Level.ERROR, e, "Exception while request message.");
    		return null;
    	}
    }
    
    private String sendImmediate(
            Message msg,
            Destination destination)
            throws Exception {


        final Channel.State channelState = this.destination.getChannel().getState();

        if ((channelState == Channel.State.CONNECTED)
                || (channelState == Channel.State.STARTED)) {

            msg.setJMSDestination(destination);

            this.getLogger().log(Level.DEBUG, "Sending JMS msg %s", msg);

            this.jmsMessageProducer.send(destination, msg,
                    msg.getJMSDeliveryMode(),
                    msg.getJMSPriority(),
                    msg.getJMSExpiration());

            this.getLogger().log(Level.DEBUG, "Sent JMS msg");

            this.destination.getStats().addEventOut();
            return msg.getJMSMessageID();
        }

        else {
            throw new Exception("Channel in an invalid state: " + channelState);
        }
    }


    public String sendImmediate(
            SimpleEvent event,
            JmsMessageContext requestContext,
            javax.jms.Destination destination,
            JMSDestination channelDestination,
            Map destinationProperties)
            throws Exception {

        synchronized (this.jmsSession) {
            final Message message = this.createMessage(event, channelDestination,
                    ((null == requestContext) ? null : requestContext.getMessage()));

            if (null != message) {
                this.overrideProperties(event, message, destinationProperties);
                return this.sendImmediate(message, destination);
            }
            else {
                return null;
            }
        }

    }


    public void sendPendingMessages()
            throws Exception {

        synchronized (this.jmsSession) {
            for (final PendingMessageContext pmc : new ArrayList<PendingMessageContext>(this.pendingMessageContexts)) {
                this.send(pmc.message, pmc.destination);
                this.pendingMessageContexts.remove(pmc);
            }
        }
    }


    private static class PendingMessageContext {


        public final Message message;
        public final Destination destination;


        PendingMessageContext(
                Message msg,
                Destination destination) {

            this.destination = destination;
            this.message = msg;
        }
    }


}
