package com.tibco.rta.service.transport.jms.destination;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.ServiceResponse;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.util.CustomByteArrayBuffer;
import com.tibco.rta.util.JMSUtil;
import com.tibco.rta.util.NoResponseException;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/3/13
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class NotificationsJMSDestination implements JMSOutboundDestination {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_TRANSPORT.getCategory());

    /**
     * All connection config.
     */
    private Properties configuration;

    /**
     * The destination this is bound to.
     */
    private Queue targetQueue;

    /**
     * Reusable session
     */
    private QueueSession queueSession;

    /**
     * Meant for outbound.
     */
    private MessageProducer messageProducer;

    /**
     * Reusable buffer.
     */
//    private CustomByteArrayBuffer byteBuffer;

    /**
     * In case text write is used.
     */
    private String message;


    public NotificationsJMSDestination(Properties connectionConfig, Queue targetQueue, QueueSession queueSession) {
        this.configuration = connectionConfig;
        this.targetQueue = targetQueue;
        this.queueSession = queueSession;
    }

    @Override
    public void start() throws Exception {
        messageProducer = queueSession.createProducer(targetQueue);
        String ttl = (String) ConfigProperty.RTA_JMS_OUTBOUND_MESSAGE_EXPIRY.getValue(configuration);
        messageProducer.setTimeToLive(Long.parseLong(ttl));
    }

    @Override
    public void stop() throws Exception {
		if (messageProducer != null) {
			messageProducer.close();
		}
    }

    @Override
    public void write(byte[] message) throws JMSException {
//        if (byteBuffer == null) {
//            byteBuffer = new CustomByteArrayBuffer(1024);
//        }
//        byteBuffer.append(message, 0, message.length);
    }

    @Override
    public void write(String message) throws Exception {
        this.message = message;
    }

    @Override
    public void flush() throws Exception {
        flush(null,null);
    }

    @Override
    public void flush(Properties properties, CustomByteArrayBuffer localBuffer) throws Exception {
        if (localBuffer == null || localBuffer.isEmpty()) {
            TextMessage textMessage = queueSession.createTextMessage(message);
            if (properties != null) {
                Enumeration<?> propertyNames = properties.propertyNames();
                while (propertyNames.hasMoreElements()) {
                    String propertyName = (String) propertyNames.nextElement();
                    textMessage.setStringProperty(propertyName, properties.getProperty(propertyName));
                }
            }
            messageProducer.send(textMessage);
            message = null;
        } else {
            BytesMessage bytesMessage = queueSession.createBytesMessage();
            if (properties != null) {
                Enumeration<?> propertyNames = properties.propertyNames();
                while (propertyNames.hasMoreElements()) {
                    String propertyName = (String) propertyNames.nextElement();
                    bytesMessage.setStringProperty(propertyName, properties.getProperty(propertyName));
                }
            }
            bytesMessage.writeBytes(localBuffer.buffer());
            messageProducer.send(bytesMessage);
        }
    }

    @Override
    public ServiceResponse sendAndReceive(Properties properties, CustomByteArrayBuffer localBuffer) throws Exception {
        BytesMessage bytesMessage = queueSession.createBytesMessage();

        if (properties != null) {
            Enumeration<?> propertyNames = properties.propertyNames();
            while (propertyNames.hasMoreElements()) {
                String propertyName = (String) propertyNames.nextElement();
                bytesMessage.setStringProperty(propertyName, properties.getProperty(propertyName));
            }
        }
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Client confirmation Message [%s]", bytesMessage);
        }
        if (localBuffer != null && !localBuffer.isEmpty()) {
            bytesMessage.writeBytes(localBuffer.buffer());
        }
        //Create temp queue and set this as response queue
        TemporaryQueue temporaryQueue = queueSession.createTemporaryQueue();
        //Set this as reply queue
        bytesMessage.setJMSReplyTo(temporaryQueue);
        MessageConsumer queueConsumer = queueSession.createConsumer(temporaryQueue);
        ReceiveListener receiveListener = new ReceiveListener(queueConsumer, temporaryQueue);
        //We should be expecting response on the temp queue
        queueConsumer.setMessageListener(receiveListener);
        messageProducer.send(bytesMessage);
        //Get response
        return receiveListener.getServiceResponse();
    }

    private class ReceiveListener implements MessageListener {

        /**
         * Message consumer for temp queue.
         */
        private MessageConsumer messageConsumer;

        /**
         * The temp queue used.
         */
        private TemporaryQueue temporaryQueue;

        private ServiceResponse serviceResponse;

        /**
         * Lock for receiving response.
         */
        private final ReentrantLock responseLock = new ReentrantLock();

        /**
         * Wait on condition till response arrives.
         */
        private final Condition responseCondition = responseLock.newCondition();

        /**
         * Indicate response processing completeness.
         */
        private volatile boolean responseArrived;

        private ReceiveListener(MessageConsumer messageConsumer, TemporaryQueue temporaryQueue) {
            this.messageConsumer = messageConsumer;
            this.temporaryQueue = temporaryQueue;
        }

        @Override
        public void onMessage(Message message) {
            ReentrantLock responseLock = this.responseLock;
            responseLock.lock();

            try {
                serviceResponse = new ServiceResponse();
                Properties responseProperties = JMSUtil.readJMSProperties(message);
                serviceResponse.setProperties(responseProperties);
                //Signal waiters
                responseArrived = true;

                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Response confirmation received");
                }

                responseCondition.signalAll();
            } catch (JMSException e) {
                LOGGER.log(Level.ERROR, "", e);
            } finally {
                if (responseLock.isHeldByCurrentThread()) {
                    responseLock.unlock();
                }
            }
        }

        ServiceResponse getServiceResponse() throws NoResponseException {
            ReentrantLock responseLock = this.responseLock;
            responseLock.lock();

            try {
                while (!responseArrived) {
                    boolean complete = responseCondition.await(10000L, TimeUnit.MILLISECONDS);
                    if (!complete) {
                        throw new NoResponseException(String.format("Response not received in [%d] milliseconds", 10000L));
                    }
                }
            } catch (InterruptedException ie) {
                LOGGER.log(Level.ERROR, "", ie);
            } finally {
                if (responseLock.isHeldByCurrentThread()) {
                    responseLock.unlock();
                }
                try {
                    //Also close consumer.
                    messageConsumer.close();
                    //Remove temp queue irrespective of timeouts/exceptions
                    temporaryQueue.delete();
                } catch (JMSException e) {
                    LOGGER.log(Level.ERROR, "", e);
                }
            }
            return serviceResponse;
        }
    }
}
