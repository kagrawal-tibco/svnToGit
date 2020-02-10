package com.tibco.rta.client.notify.impl;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.client.BytesServiceResponse;
import com.tibco.rta.client.ServiceInvocationListener;
import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.tcp.GatheringBytesTask;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.util.CustomByteArrayBuffer;
import com.tibco.rta.util.JMSUtil;
import com.tibco.rta.util.ParserCursor;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;



/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 21/3/13
 * Time: 11:22 AM
 * JMS implementation for demultiplexing messages.
 */
public class AsyncJMSMessageNotifier extends BaseMessageReceptionNotifier implements MessageListener, ServiceInvocationListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    @Override
    public void serviceInvoked(ServiceResponse serviceResponse) {
        //Will be bytes response
        byte[] payload = (byte[]) serviceResponse.getPayload();
        CustomByteArrayBuffer contentChunk = new CustomByteArrayBuffer(payload.length);
        contentChunk.append(payload, 0, payload.length);
        parserCursor = new ParserCursor(0, contentChunk.capacity());

        int headerIndexBegin =
                contentChunk.indexOf(HEADER_DELIM, parserCursor.getPos(), contentChunk.length());
        //Update its position
        parserCursor.updatePos(contentChunk.length());
        if (headerIndexBegin != -1) {
            //Header begins
            int headerIndexEnd = contentChunk.indexOf(HEADER_DELIM, headerIndexBegin + 1, contentChunk.length());
            int headerCapacity = headerIndexEnd - headerIndexBegin - 1;
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Header chunk length [%s]", headerCapacity);
            }
            if (headerChunk == null) {
                headerChunk = new CustomByteArrayBuffer(headerCapacity);
            }
            headerChunk.append(contentChunk.toByteArray(), headerIndexBegin + 1, headerCapacity);
            try {
                dispatch(serviceResponse.getResponseProperties(), contentChunk);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            }
        }
    }


    /**
     * Dispatch to event handlers
     *
     */
    private void dispatch(Properties properties, CustomByteArrayBuffer contentChunk) throws Exception {
        int headerChunkOffset = headerChunk.length() + 2;
        notificationDispatcherService.submit(new GatheringBytesTask(headerChunk.toByteArray(), contentChunk.toByteArray(headerChunkOffset, contentChunk.length()), properties, notificationEventHandlers));
        headerChunk.clear();
    }

    public AsyncJMSMessageNotifier(Map<ConfigProperty, PropertyAtom<?>> configuration) {
        super(configuration);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onMessage(Message message) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Async notification message [%s]", message);
        }
        if (message instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage) message;
            try {
                byte[] bytes = JMSUtil.readBytesFrom(bytesMessage);
                ServiceResponse<byte[]> serviceResponse = new BytesServiceResponse();
                if (bytes != null) {
                    serviceResponse.setPayload(bytes);
                }

                Enumeration<String> propertyNames = bytesMessage.getPropertyNames();
                while (propertyNames.hasMoreElements()) {
                    String propertyName = propertyNames.nextElement();
                    serviceResponse.addProperty(propertyName, message.getStringProperty(propertyName));
                }
                Queue replyTo = ((Queue)bytesMessage.getJMSReplyTo());
                if (replyTo != null) {
                    serviceResponse.addProperty("ReplyToEndpoint", replyTo);
                }
                serviceInvoked(serviceResponse);
            } catch (JMSException e) {
                LOGGER.log(Level.ERROR, "", e);
            }
        }
    }
}
