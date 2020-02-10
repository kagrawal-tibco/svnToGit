package com.tibco.rta.service.metric;

import com.tibco.rta.Fact;
import com.tibco.rta.common.service.transport.jms.JMSFactMessageContext;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/4/13
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class AckingFactListener implements FactListener<JMSFactMessageContext> {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());

    @Override
    public void onFactAsserted(JMSFactMessageContext messageContext, Fact fact) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Need to ack fact [%s]", fact.getKey());
        }
        if (messageContext != null) {
        	messageContext.acknowledge();
        }
    }
}
