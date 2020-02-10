package com.tibco.cep.driver.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * User: nprade
 * Date: 9/13/12
 * Time: 4:49 PM
 */
public class JmsSessionContextProvider {


    private JMSDestination destination;
    private JmsSessionProvider jmsSessionProvider;
    private JmsSessionContext sharedContext;

    public JmsSessionContextProvider(
            JMSDestination destination,
            Connection connection) throws JMSException 
    {
        this.destination = destination;
        this.jmsSessionProvider = new JmsSessionProvider(
                connection,
                destination.getConfig().getJmsAckMode(),
                destination.getConfig().getChannelConfig().isTransacted());
        
        sharedContext = createJmsSessionContext();
    }

    public JmsSessionContext createJmsSessionContext() throws JMSException {
    	return new JmsSessionContext(this, this.destination, this.jmsSessionProvider.createSession());
    }
    
    public JmsSessionContext getSharedJmsSessionContext() {
    	return sharedContext;
    }
}
