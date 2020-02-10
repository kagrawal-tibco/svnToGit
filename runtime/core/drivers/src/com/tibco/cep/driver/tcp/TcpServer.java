package com.tibco.cep.driver.tcp;

import java.net.SocketAddress;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Mar 29, 2005
 * Time: 10:03:12 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TcpServer {

    void startListening();
    SocketAddress bind() throws Exception;
    SocketAddress getInetAddress();
    void stopServer();
}
