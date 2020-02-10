package com.tibco.rta.client.notify.impl;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.client.ServiceInvocationListener;
import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.tcp.GatheringBytesTask;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.util.CustomByteArrayBuffer;
import com.tibco.rta.util.CustomCharArrayBuffer;
import com.tibco.rta.util.ParserCursor;

import java.util.Map;
import java.util.Properties;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 1/2/13
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class AsyncNotificationReactor extends BaseMessageReceptionNotifier implements ServiceInvocationListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    /**
     * Transient data chunk
     */
    private CustomByteArrayBuffer contentChunk;

    private void onData(Properties properties, byte[] bytes) {
        CustomCharArrayBuffer charArrayBuffer = new CustomCharArrayBuffer(bytes.length);
        charArrayBuffer.append(bytes, 0, bytes.length);

        //Check for digit
        try {
            //Chunk size is in Hex
            if (isHexDigit(charArrayBuffer)) {
                String hexChunk = charArrayBuffer.toString().trim();
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Hex digit chunk value [%d]", hexChunk);
                }
                int currentChunkSize = Integer.parseInt(hexChunk, 16);
                if (headerChunk != null) {
                    //Clear previous content
                    headerChunk.clear();
                }
                contentChunk = new CustomByteArrayBuffer(currentChunkSize);
                parserCursor = new ParserCursor(0, contentChunk.capacity());
            } else {
                if (contentChunk != null) {
                    //Keep appending
                    int startPos = 0;
                    if (startsWithCRLF(bytes)) {
                        //Offset CRLF
                        startPos = 2;
                    }
                    contentChunk.append(bytes, startPos, bytes.length - startPos);
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Chunk length [%d]", contentChunk.length());
                    }
                    int headerIndexBegin = contentChunk.indexOf(HEADER_DELIM, parserCursor.getPos(), contentChunk.length());
                    //Update its position
                    parserCursor.updatePos(contentChunk.length());
                    if (headerIndexBegin != -1) {
                        //Footer begins
                        int headerIndexEnd = contentChunk.indexOf(HEADER_DELIM, headerIndexBegin + 1, contentChunk.length());
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Header chunk end [%d]", headerIndexEnd);
                        }
                        if (headerIndexEnd == -1) {
                            //Still not received
                            //Continue the process
                            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                                LOGGER.log(Level.DEBUG, "Header chunk end not received");
                            }
                        } else {
                            //Get header value
                            int headerCapacity = headerIndexEnd - headerIndexBegin - 1;
                            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                                LOGGER.log(Level.DEBUG, "Header chunk length [%d]", headerCapacity);
                            }
                            if (headerChunk == null) {
                                headerChunk = new CustomByteArrayBuffer(headerCapacity);
                            }
                            headerChunk.append(contentChunk.toByteArray(), headerIndexBegin + 1, headerCapacity);
                        }
                    }
                    if (contentChunk.isFull()) {
                        //Data matching chunk size probably received
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Chunk met for size [%d]", contentChunk.length());
                        }
                        dispatch(properties);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    public AsyncNotificationReactor(Map<ConfigProperty, PropertyAtom<?>> configuration) {
        super(configuration);
    }

    public void close() {

    }

    @Override
    public void serviceInvoked(ServiceResponse serviceResponse) {
        byte[] bytes = (byte[]) serviceResponse.getPayload();
        onData(serviceResponse.getResponseProperties(), bytes);
    }

    private boolean startsWithCRLF(byte[] bytes) {
        return (bytes[0] == '\r' && bytes[1] == '\n');
    }

    private boolean isHexDigit(CustomCharArrayBuffer charArrayBuffer) {
        if (charArrayBuffer.isEmpty()) {
            return false;
        }
        //If only contains CRLF
        if (charArrayBuffer.length() == 2) {
            if (charArrayBuffer.indexOf('\r') == 0 && charArrayBuffer.indexOf('\n') == 1) {
                return false;
            }
        }
        for (int i = 0; i < charArrayBuffer.length(); i++) {
            char character = charArrayBuffer.charAt(i);
            if (character == '\n' || character == '\r') {
                continue;
            }
            if (Character.digit(character, 16) < 0) {
                //Not a number
                return false;
            }
        }
        return true;
    }

    /**
     * Dispatch to event handlers
     *
     */
    private void dispatch(Properties properties) throws Exception {
        int headerChunkOffset = headerChunk.length() + 2;
        notificationDispatcherService.submit(new GatheringBytesTask(headerChunk.toByteArray(), contentChunk.toByteArray(headerChunkOffset, contentChunk.length()), properties, notificationEventHandlers));
        contentChunk.clear();
    }
}
