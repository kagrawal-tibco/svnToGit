package com.tibco.rta.common.service.transport.jms;

import com.tibco.rta.common.service.MessageContext;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 19/4/13
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMSMessageContext implements MessageContext {

    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_TRANSPORT_DETAILS.getCategory());

    protected Message incomingMessage;

    private boolean acknowledged;

    public JMSMessageContext(Message incomingMessage) {
        this.incomingMessage = incomingMessage;
    }

    @Override
    public void acknowledge() throws JMSException {
    	try {
    		incomingMessage.acknowledge();
    		if (LOGGER.isEnabledFor(Level.DEBUG)) {
        	LOGGER.log(Level.DEBUG, "Success acknowledging message [%s]", incomingMessage.getJMSMessageID());
    		}
        	acknowledged = true;

    	} catch (JMSException e) {
        	if (LOGGER.isEnabledFor(Level.DEBUG)) {
        		LOGGER.log(Level.DEBUG, "Error acknowledging message [%s]", incomingMessage.getJMSMessageID());        
        	}
        	throw e;
    	}
    }

    @Override
    public boolean isAcked() {
        return acknowledged;
    }

    @Override
    public String toString() {
        return incomingMessage.toString();
    }
}
