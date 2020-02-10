package com.tibco.cep.driver.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.Message;

import com.tibco.cep.kernel.service.logging.Level;

/**
 * Enabling error endpoint allows applications to redirect unsupported type messages to a separate error queue hence preventing any pending messages in the actual queue.
 * 
 * @author moshaikh
 */
public class JMSErrorEndpointHelper {
	
    public static final String PROPERTY_JMS_ERROR_ENDPOINT_ENABLED = "be.jms.error.endpoint.enable";
    public static final String PROPERTY_JMS_DEFAULT_ERROR_QUEUE_NAME = "be.jms.default.error.queue.name";
    public static final String PROPERTY_JMS_DEFAULT_ERROR_TOPIC_NAME = "be.jms.default.error.topic.name";
    
    public static final String DEFAULT_ERROR_QUEUE_NAME = "be.application.error.queue";
    public static final String DEFAULT_ERROR_TOPIC_NAME = "be.application.error.topic";
    
    public static boolean isErrorEndpointEnabled() {
		return Boolean.parseBoolean(System.getProperty(PROPERTY_JMS_ERROR_ENDPOINT_ENABLED, Boolean.FALSE.toString()));
	}
    
    /**
     * Identifies the error destination and forwards the message to that destination.
     * Also acks the original message.
     * 
     * @param jmsDestination
     * @param message
     * @return 
     */
    public static boolean forwardMessageToErrorEndpoint(JMSDestination jmsDestination, Message message) {
    	JmsSenderSessionContext jmsSenderSessionContext = null;
		try {
			boolean isQueue = jmsDestination.getConfig().isQueue();
			String errorJmsDestName = isQueue ?
					System.getProperty(PROPERTY_JMS_DEFAULT_ERROR_QUEUE_NAME, DEFAULT_ERROR_QUEUE_NAME)
					: System.getProperty(PROPERTY_JMS_DEFAULT_ERROR_TOPIC_NAME, DEFAULT_ERROR_TOPIC_NAME);
			
			Map<String, String> overrideData = new HashMap<String, String>();
			overrideData.put("queue", String.valueOf(isQueue));
			overrideData.put("name", errorJmsDestName);
			
			final Destination errorDestination = jmsDestination.lookUpOrCreateDestination(overrideData);
			
			jmsDestination.logger.log(Level.INFO, "Forwarding message to error queue/topic. Not supporting JMS Message type " + message.getClass() + ", message =" + message);
			if(jmsDestination.getChannel().shouldUseSenderPool() && !jmsDestination.isOneJmsSessionPerMessage()) {
				jmsSenderSessionContext = jmsDestination.getChannel().getSenderSessionContextPool().takeFromPool();
				jmsSenderSessionContext.send(message, errorDestination, jmsDestination);
			} else {
				jmsDestination.getCurrentSessionContext().send(message, errorDestination);
			}
			message.acknowledge();//ACK the original message
			
			return true;
		} catch(Exception e) {
			jmsDestination.logger.log(Level.ERROR, e, "Error while forwarding message to error queue/topic. Message type " + message.getClass() + ", message =" + message);
		} finally {
			if (jmsSenderSessionContext != null) {
				jmsDestination.getChannel().getSenderSessionContextPool().returnToPool(jmsSenderSessionContext);
			}
		}
		
    	return false;
    }
}
