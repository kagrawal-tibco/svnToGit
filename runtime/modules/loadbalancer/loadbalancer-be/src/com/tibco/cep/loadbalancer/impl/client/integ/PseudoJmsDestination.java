package com.tibco.cep.loadbalancer.impl.client.integ;

import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.driver.jms.BaseJMSChannel;
import com.tibco.cep.driver.jms.JMSDestination;
import com.tibco.cep.driver.jms.JMSInterceptor;
import com.tibco.cep.driver.jms.JmsMessageContext;
import com.tibco.cep.loadbalancer.client.core.MessageCustomizer;
import com.tibco.cep.loadbalancer.impl.client.ClientMaster;
import com.tibco.cep.loadbalancer.message.DistributableMessage;
import com.tibco.cep.loadbalancer.message.DistributableMessage.KnownHeader;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.util.annotation.LogCategory;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Map;
import java.util.logging.Level;

import static com.tibco.cep.util.Helper.$logger;

/*
* Author: Ashwin Jayaprakash / Date: Apr 14, 2010 / Time: 11:32:29 AM
*/

@LogCategory("loadbalancer.be.client.destination.jms")
public class PseudoJmsDestination extends PseudoDestination<BaseJMSChannel, JMSDestination> implements JMSInterceptor {
    @Override
    public boolean startInActiveMode(RuleSession session, BaseJMSChannel channel, JMSDestination destination) {
        super.start(session, channel, destination);

        return false;
    }

    @Override
    protected MessageCustomizer createMessageCustomizer() {
        return new JmsMessageCustomizer();
    }

    @Override
    public void setProperties(Map<Object, Object> properties) {
        super.setProperties(properties);
    }

    @Override
    public boolean startInPassiveMode(RuleSession session, BaseJMSChannel channel, JMSDestination destination) {
        ResourceProvider resourceProvider = ClientMaster.getResourceProvider();
        logger = $logger(resourceProvider, getClass());

        logger.log(Level.INFO,
                String.format("Starting [%s : %s : %s : %s] in passive mode", session.getName(), channel.getName(),
                        destination.getName(), getClass().getSimpleName()));

        return false;
    }

    @Override
    public boolean startInSuspendMode(RuleSession session, BaseJMSChannel channel, JMSDestination destination) {
        ResourceProvider resourceProvider = ClientMaster.getResourceProvider();
        logger = $logger(resourceProvider, getClass());

        logger.log(Level.INFO,
                String.format("Starting [%s : %s : %s : %s] in suspend mode", session.getName(), channel.getName(),
                        destination.getName(), getClass().getSimpleName()));

        return false;
    }

    @Override
    public final JmsMessageContext onMessage(JmsMessageContext messageContext) {
        return messageContext;
    }

    @Override
    public JmsMessageContext unpackAsEventOrEventContext(Object message) {
        return destination.createEventContext(message);
    }

    //--------------

    protected static class JmsMessageCustomizer extends AbstractMessageCustomizer {
        @Override
        protected void setBoolean(DistributableMessage distributableMessage, KnownHeader knownHeader, boolean value)
                throws JMSException {
            Message message = (Message) distributableMessage.getContent();
            message.setBooleanProperty(knownHeader.name(), value);
        }

        @Override
        protected void setString(DistributableMessage distributableMessage, KnownHeader knownHeader, String value)
                throws JMSException {
            Message message = (Message) distributableMessage.getContent();
            message.setStringProperty(knownHeader.name(), value);
        }

        @Override
        protected void setLong(DistributableMessage distributableMessage, KnownHeader knownHeader, long value)
                throws JMSException {
            Message message = (Message) distributableMessage.getContent();
            message.setLongProperty(knownHeader.name(), value);
        }
    }
}