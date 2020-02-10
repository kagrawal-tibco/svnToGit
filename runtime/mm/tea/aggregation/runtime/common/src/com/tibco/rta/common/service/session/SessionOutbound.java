package com.tibco.rta.common.service.session;

import com.tibco.rta.common.ServiceResponse;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/3/13
 * Time: 4:01 PM
 * Wrapper over underlying outbound transport.
 */
public interface SessionOutbound {


    /**
     * @param bytes
     */
    public void write(byte[] bytes) throws Exception;

    /**
     * Optional operation which can guide underlying transport to flush buffers if any.
     */
    public void finish() throws Exception;

    /**
     * Optional operation which can guide underlying transport to flush buffers if any.
     */
    public ServiceResponse sendAndReceive(Properties properties) throws Exception;
}
