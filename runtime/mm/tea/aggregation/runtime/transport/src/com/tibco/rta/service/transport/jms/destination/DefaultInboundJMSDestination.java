package com.tibco.rta.service.transport.jms.destination;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.transport.jms.InboundMessageReceiver;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueSession;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 20/3/13
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultInboundJMSDestination implements JMSInboundDestination {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_TRANSPORT.getCategory());


    /**
     * The destination this is bound to.
     */
    private Queue targetQueue;

    /**
     * Reusable session
     */
    private QueueSession queueSession;

    /**
     * Message listener.
     */
    private InboundMessageReceiver inboundMessageReceiver;

    /**
     * Common message consumer.
     */
    private MessageConsumer messageConsumer;

    /**
     * Should this perform any recovery.
     */
    private boolean shouldRecover;


    public DefaultInboundJMSDestination(Properties configuration,
                                        Queue targetQueue,
                                        QueueSession queueSession,
                                        boolean shouldSignalPrimary) throws JMSException {
        this.targetQueue = targetQueue;
        this.queueSession = queueSession;
        //This value can be true only in FT case.
        shouldRecover = Boolean.parseBoolean(configuration.getProperty("shouldRecover"));
        boolean isGMP = Boolean.parseBoolean((String) ConfigProperty.RTA_FT_GMP_ENABLED.getValue(configuration));
        inboundMessageReceiver = (isGMP) ? new InboundMessageReceiver(configuration, queueSession, !shouldRecover) : new InboundMessageReceiver(configuration, queueSession, !shouldRecover, shouldSignalPrimary);
    }


    @Override
    public void start() throws Exception {
        messageConsumer = queueSession.createConsumer(targetQueue);
        messageConsumer.setMessageListener(inboundMessageReceiver);
    }

    @Override
	public void stop() throws Exception {
		if (messageConsumer != null) {
			messageConsumer.close();
		}
	}
}
