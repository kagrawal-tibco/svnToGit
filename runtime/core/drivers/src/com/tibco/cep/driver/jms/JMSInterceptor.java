package com.tibco.cep.driver.jms;

import com.tibco.cep.runtime.session.RuleSession;

import java.util.Map;

/*
* Author: Ashwin Jayaprakash / Date: Apr 13, 2010 / Time: 11:46:21 AM
*/

public interface JMSInterceptor {
    /**
     * Called before any start* method are invoked.
     *
     * @param properties
     */
    void setProperties(Map<Object, Object> properties);

    /**
     * @param session
     * @param channel
     * @param destination
     * @return true to continue processing. False to stop at this interceptor.
     */
    boolean startInActiveMode(RuleSession session, BaseJMSChannel channel, JMSDestination destination) throws Exception;

    /**
     * @param session
     * @param channel
     * @param destination
     * @return true to continue processing. False to stop at this interceptor.
     */
    boolean startInPassiveMode(RuleSession session, BaseJMSChannel channel, JMSDestination destination) throws Exception;

    /**
     * @param session
     * @param channel
     * @param destination
     * @return true to continue processing. False to stop at this interceptor.
     */
    boolean startInSuspendMode(RuleSession session, BaseJMSChannel channel, JMSDestination destination) throws Exception;

    /**
     * @param messageContext
     * @return Return the same message context to let the other components in the chain to process the message. null to
     *         prevent/stop further processing.
     */
    JmsMessageContext onMessage(JmsMessageContext messageContext) throws Exception;

    void stop() throws Exception;
}
