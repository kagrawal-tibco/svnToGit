package com.tibco.cep.driver.tcp;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Mar 29, 2005
 * Time: 10:08:23 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TcpConnection {
    boolean connect() throws Exception;

    boolean connect(String userId, String passwd) throws Exception;

    int send(byte[] data) throws Exception;

    int send(byte[] data, boolean noAck) throws Exception;

    byte[] sendWithReply(byte[] data) throws Exception;

    byte[] recv(int timeOut) throws Exception;

    void close() throws Exception;

    String getURL();
}
