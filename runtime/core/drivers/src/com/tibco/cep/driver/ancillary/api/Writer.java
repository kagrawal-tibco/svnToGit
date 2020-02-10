package com.tibco.cep.driver.ancillary.api;

import com.tibco.cep.runtime.service.ManagedResource;

/*
* Author: Ashwin Jayaprakash Date: Mar 19, 2009 Time: 1:19:57 PM
*/
public interface Writer extends ManagedResource {
    /**
     * If <code>true</code>, then the call to {@link #write(byte[], int, int)} will block until it
     * is written to the underlying system.
     *
     * @return
     */
    boolean isWriteSynchronous();

    void write(byte[] data, int offset, int length) throws Exception;

    void flush() throws Exception;
}
