package com.tibco.cep.loadbalancer.message;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/*
* Author: Ashwin Jayaprakash / Date: Apr 9, 2010 / Time: 2:34:39 PM
*/

public interface MessageCodec {
    int getMagicHeader();

    void setClassLoader(ClassLoader classLoader);

    /**
     * If the data is from a stream and the full message has not been received, then the return value will be
     * <code>null</code>.
     * <p/>
     * If multiple messages are present in the byte sent, then a {@link List} with all those messages may be returned.
     *
     * @param data
     * @param offset
     * @param length
     * @return
     * @throws Exception
     */
    Object read(byte[] data, int offset, int length) throws Exception;

    /**
     * @param message
     * @param outputStream The stream to write to. Do not close this stream.
     * @return Number of bytes that were written.
     * @throws IOException
     */
    //todo Expose byte[] or NIO buffer instead of a outputstream.
    int write(Object message, OutputStream outputStream) throws Exception;

    void discard();

    void reset();
}
