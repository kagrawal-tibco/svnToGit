package com.tibco.rta.client.notify;

import java.util.EventObject;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 1/2/13
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class AsyncNotificationEvent extends EventObject {

	private static final long serialVersionUID = -3829306624230529703L;

    private Properties properties;

	public AsyncNotificationEvent(byte[] source, Properties properties) {
        super(source);
        this.properties = properties;
    }

    public Object get(String key) {
        return properties.get(key);
    }
}
