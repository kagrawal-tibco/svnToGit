package com.tibco.cep.driver.tcp.impl;

import java.net.SocketAddress;

import com.tibco.cep.driver.tcp.TcpListener;
import com.tibco.cep.driver.tcp.TcpTransportDriver;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Mar 28, 2005
 * Time: 2:52:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class TcpServerImpl extends Thread implements com.tibco.cep.driver.tcp.TcpServer {

    TcpTransportDriver driverImpl;

    protected TcpServerImpl(String svcName, String host, int port, TcpListener lsnr) throws Exception {
        super(svcName);
        driverImpl = new TcpTransportDriverImpl(host, port);
        driverImpl.setSocketListener(lsnr);
    }

    protected TcpServerImpl(String svcName, String host, String portList, TcpListener lsnr) throws Exception {
        super(svcName);
        driverImpl = new TcpTransportDriverImpl(host, portList);
        driverImpl.setSocketListener(lsnr);
    }

    public void run() {
        driverImpl.run();
    }

    public SocketAddress getInetAddress() {
        return driverImpl.getInetAddress();
    }

    public void startListening() {
        this.start();
    }

    public void stopServer() {
        this.interrupt();
    }

    public SocketAddress bind() throws Exception {
        return driverImpl.bind();
    }
}
