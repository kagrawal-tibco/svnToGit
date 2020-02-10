package com.tibco.rta.client.notify.impl;

import com.tibco.rta.client.BytesServiceResponse;
import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.StringServiceResponse;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.util.JMSUtil;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/3/13
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class SyncJMSNotifier implements MessageListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    private long syncResponseTimeout;

    /**
     * The temp queue created by client for sending and receiving response.
     */
    private TemporaryQueue temporaryQueue;

    /**
     * Listener attached to temp queue.
     */
    private MessageConsumer queueConsumer;

    /**
     * Lock for sync cases.
     */
    private final ReentrantLock syncLock = new ReentrantLock();

    /**
     * Sync resp condition.
     */
    private final Condition syncCondition = syncLock.newCondition();

    private ServiceResponse<?> serviceResponse;

    /**
     * Indicate response processing completeness.
     */
    private volatile boolean responseArrived;

    /**
     *
     * @param syncResponseTimeout
     * @param queueConsumer
     * @param temporaryQueue - The temp queue created by client for sending and receiving response.
     */
    public SyncJMSNotifier(long syncResponseTimeout, MessageConsumer queueConsumer, TemporaryQueue temporaryQueue) {
        this.syncResponseTimeout = syncResponseTimeout;

        this.queueConsumer = queueConsumer;

        this.temporaryQueue = temporaryQueue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onMessage(Message message) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Response for registration %s", message);
        }
        ReentrantLock syncLock = this.syncLock;
        syncLock.lock();

        try {
            if (message instanceof BytesMessage) {
                BytesServiceResponse serviceResponse = new BytesServiceResponse();
                byte[] bytes = JMSUtil.readBytesFrom((BytesMessage)message);
                serviceResponse.setPayload(bytes);
                this.serviceResponse = serviceResponse;
            } else if (message instanceof TextMessage) {
                StringServiceResponse serviceResponse = new StringServiceResponse();
                serviceResponse.setPayload(((TextMessage)message).getText());
                this.serviceResponse = serviceResponse;
            } else {
                //message with no payload
                this.serviceResponse = new StringServiceResponse();
            }

            Enumeration<String> responseProperties = message.getPropertyNames();

            while (responseProperties.hasMoreElements()) {
                String responseProperty = responseProperties.nextElement();
                serviceResponse.addProperty(responseProperty, message.getStringProperty(responseProperty));
            }
            responseArrived = true;

            syncCondition.signalAll();
        } catch (JMSException je) {
            LOGGER.log(Level.ERROR, "", je);
        } finally {
            if (syncLock.isHeldByCurrentThread()) {
                syncLock.unlock();
            }
        }
    }

    public ServiceResponse<?> getServiceResponse() {
        ReentrantLock syncLock = this.syncLock;
        syncLock.lock();

        try {
            while (!responseArrived) {
                boolean complete = syncCondition.await(syncResponseTimeout, TimeUnit.MILLISECONDS);
                if (!complete) {
                    throw new RuntimeException(String.format("Response not received in [%d] milliseconds", syncResponseTimeout));
                }
            }
        } catch (InterruptedException ie) {
            LOGGER.log(Level.ERROR, "", ie);
        } finally {
            if (syncLock.isHeldByCurrentThread()) {
                syncLock.unlock();
            }
            try {
                //Also close consumer.
                queueConsumer.close();
                //Remove temp queue irrespective of timeouts/exceptions
                temporaryQueue.delete();
            } catch (JMSException e) {
                LOGGER.log(Level.ERROR, "", e);
            }
        }
        return serviceResponse;
    }
}
