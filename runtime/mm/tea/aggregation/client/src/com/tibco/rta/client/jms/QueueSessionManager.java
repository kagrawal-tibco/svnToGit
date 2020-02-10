package com.tibco.rta.client.jms;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.QueueSession;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Session and producer manager
 * Created by aathalye on 10/12/14.
 */
public class QueueSessionManager {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    private final ThreadLocal<QueueSession> QUEUE_SESSIONS = new ThreadLocal<QueueSession>();

    private final ThreadLocal<MessageProducer> MESSAGE_PRODUCERS = new ThreadLocal<MessageProducer>();

    /**
     * Maintain collection for every queue session
     */
    private Collection<QueueSession> queueSessionCollection = new CopyOnWriteArrayList<QueueSession>();

    /**
     * Maintain collection for every message producer
     */
    private Collection<MessageProducer> messageProducerCollection = new CopyOnWriteArrayList<MessageProducer>();

    QueueSessionManager() {}

    QueueSession get() throws JMSException {
        return QUEUE_SESSIONS.get();
    }

    MessageProducer getProducer() throws JMSException {
        return MESSAGE_PRODUCERS.get();
    }

    void set(QueueSession queueSession) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Creating new queue session for thread : [%s]", Thread.currentThread().getName());
        }
        QUEUE_SESSIONS.set(queueSession);
        queueSessionCollection.add(queueSession);
    }

    void setProducer(MessageProducer messageProducer) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Creating new message producer for thread : [%s]", Thread.currentThread().getName());
        }
        MESSAGE_PRODUCERS.set(messageProducer);
        messageProducerCollection.add(messageProducer);
    }

    void clear() throws JMSException {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Closing message producers");
        }
        //Close producers
        for (MessageProducer messageProducer : messageProducerCollection) {
            //Close
            messageProducer.close();
        }
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Closing queue sessions");
        }
        //Close sessions
        for (QueueSession queueSession : queueSessionCollection) {
            //Close
            queueSession.close();
        }
    }
}
