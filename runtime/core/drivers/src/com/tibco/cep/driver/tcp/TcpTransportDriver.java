package com.tibco.cep.driver.tcp;

import java.net.SocketAddress;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Mar 29, 2005
 * Time: 9:56:51 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TcpTransportDriver extends Runnable {
    void setSocketListener(TcpListener lsnr);
    /**
     * Binds to the underlying socket, and returns the address.
     * @return
     * @throws Exception
     */
    SocketAddress bind() throws Exception;
    
    SocketAddress getInetAddress();
}
