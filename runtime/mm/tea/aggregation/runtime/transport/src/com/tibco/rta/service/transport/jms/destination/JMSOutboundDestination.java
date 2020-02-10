package com.tibco.rta.service.transport.jms.destination;

import com.tibco.rta.common.ServiceResponse;
import com.tibco.rta.util.CustomByteArrayBuffer;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/3/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface JMSOutboundDestination {

    public void start() throws Exception;

    public void stop() throws Exception;

    /**
     * @param message
     * @throws Exception
     */
    public void write(String message) throws Exception;

    /**
     * Write bytes to the underlying transport.
     *
     * @param message
     * @throws Exception
     */
    public void write(byte[] message) throws Exception;

    /**
     * Flush message with body only.
     *
     * @throws Exception
     */
    public void flush() throws Exception;

    /**
     * Flush body with any properties while writing message.
     *
     * @param properties
     * @param byteBuffer 
     * @throws Exception
     */
    public void flush(Properties properties, CustomByteArrayBuffer byteBuffer) throws Exception;

    /**
     * Flush body with any properties while writing message.
     *
     * @param properties
     * @param byteBuffer 
     * @throws Exception
     */
    public ServiceResponse sendAndReceive(Properties properties, CustomByteArrayBuffer byteBuffer) throws Exception;
}
