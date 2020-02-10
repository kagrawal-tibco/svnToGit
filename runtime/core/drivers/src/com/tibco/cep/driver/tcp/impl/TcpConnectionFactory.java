package com.tibco.cep.driver.tcp.impl;

import com.tibco.cep.driver.tcp.TcpConnection;
import com.tibco.cep.driver.tcp.TcpListener;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Mar 29, 2005
 * Time: 9:49:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class TcpConnectionFactory {

    static TcpConnectionFactory factory = new TcpConnectionFactory();

    public static TcpConnectionFactory factory() {
        return factory;
    }

    public TcpServerImpl newServer(String srvName, String bindAddress, int port, TcpListener lsnr) throws Exception {
        return new TcpServerImpl(srvName, bindAddress, port, lsnr);
    }

    public TcpServerImpl newServer(String srvName, String bindAddress, String portList, TcpListener lsnr) throws Exception {
        return new TcpServerImpl(srvName, bindAddress, portList, lsnr);
    }

    public TcpConnection newConnection(String remoteHost, int port) throws Exception {
        return new TcpConnectionImpl(remoteHost, port);
    }

    public TcpConnection newConnection(String url) throws Exception {
        return new TcpConnectionImpl(url);
    }
}
