package com.tibco.cep.driver.tcp;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Mar 28, 2005
 * Time: 2:52:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TcpListener {
    public void onMessage(TcpConnection s, byte[] data, int length) throws Exception;
}
