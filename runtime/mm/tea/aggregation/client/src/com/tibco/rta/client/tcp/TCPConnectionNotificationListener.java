package com.tibco.rta.client.tcp;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/2/13
 * Time: 8:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TCPConnectionNotificationListener {

    public void onEvent(TCPConnectionEvent connectionEvent);
}
