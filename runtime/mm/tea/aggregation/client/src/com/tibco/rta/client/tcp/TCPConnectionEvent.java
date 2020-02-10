package com.tibco.rta.client.tcp;

import java.util.EventObject;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/2/13
 * Time: 8:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class TCPConnectionEvent extends EventObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = -579514864882731881L;

	public TCPConnectionEvent(int connectionEventType) {
        super(connectionEventType);
    }

    /**
     * Key for registering interest in server conn establish notifications.
     */
    public static final int CONNECTION_ESTABLISH_EVENT = 1 << 1;

    /**
     * Key for registering interest in server conn down notifications.
     */
    public static final int CONNECTION_DOWN_EVENT = 1 << 2;

    /**
     * Key for registering interest in client attempting to establish connection.
     */
    public static final int CONNECTION_TRY_EVENT = 1 << 3;
}
