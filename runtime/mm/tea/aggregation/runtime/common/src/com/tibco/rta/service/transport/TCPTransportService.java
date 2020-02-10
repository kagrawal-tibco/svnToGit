package com.tibco.rta.service.transport;


import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 *
 */
public interface TCPTransportService extends TransportService {

    /**
     * Write the specified bytes to the client socket channel.
     * @param socketChannel
     * @param bytes
     * @throws IOException
     */
    public void write(SocketChannel socketChannel, byte[] bytes) throws IOException;

}
