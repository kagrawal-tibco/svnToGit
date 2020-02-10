package com.tibco.cep.driver.ancillary.api;

import com.tibco.cep.runtime.service.ManagedResource;

/*
* Author: Ashwin Jayaprakash Date: Mar 19, 2009 Time: 1:19:50 PM
*/

public interface Reader extends ManagedResource {
    /**
     * If the {@link #setOptionalListener(com.tibco.cep.driver.ancillary.api.Reader.ReaderListener) listener} is not
     * set, then a blocking read may be performed.
     *
     * @param data
     * @param offset
     * @param length
     * @return Number of bytes read.
     * @throws Exception
     */
    int read(byte[] data, int offset, int length) throws Exception;

    /**
     * Must be set before {@link #start()}.
     *
     * @param listener
     */
    void setOptionalListener(ReaderListener listener);

    ReaderListener getOptionalListener();

    /**
     * @return If <code>true</code>, then the calls to the {@link com.tibco.cep.driver.ancillary.api.Reader.ReaderListener}
     *         have to return quickly to be able to read more data.
     */
    boolean isReaderNotificationSynchronous();

    //------------

    public static interface ReaderListener {
        void onException(Exception e);

        /**
         * @param data
         * @param offset
         * @param length
         * @throws Exception {@link #onException(Exception)} will be called if the session had not already been closed.
         */
        void onData(byte[] data, int offset, int length) throws Exception;

        void onEnd();
    }
}
