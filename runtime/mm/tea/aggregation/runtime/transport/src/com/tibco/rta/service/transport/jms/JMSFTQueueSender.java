package com.tibco.rta.service.transport.jms;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.QueueSession;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 6/8/14
 * Time: 1:45 PM
 *
 * Publish messages to the SPM FT queue.
 */
public class JMSFTQueueSender implements Runnable {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_TRANSPORT.getCategory());

    private QueueSession ftQueueSession;

    private MessageProducer ftQueueProducer;

    private SessionStateNotifier sessionStateNotifier;

    public JMSFTQueueSender(QueueSession ftQueueSession,
                            MessageProducer ftQueueProducer,
                            SessionStateNotifier sessionStateNotifier) {
        this.ftQueueSession = ftQueueSession;
        this.ftQueueProducer = ftQueueProducer;
        this.sessionStateNotifier = sessionStateNotifier;
    }

    public void run() {
        BytesMessage bytesMessage;
        try {
            bytesMessage = ftQueueSession.createBytesMessage();
            ftQueueProducer.send(bytesMessage);
        } catch (JMSException e) {
            LOGGER.log(Level.ERROR, "", e);
            if (e instanceof javax.jms.IllegalStateException) {
                sessionStateNotifier.onSessionClose(e);
            }
        }
    }
}
