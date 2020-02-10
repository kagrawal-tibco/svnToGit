package com.tibco.rta.client;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 12/11/12
 * Time: 11:07 AM
 * Client side of message when service sends response back
 */
public abstract class ServiceResponse<T> {

    private Properties properties = new Properties();


    public void addProperty(String propertyName, Object value) {
        properties.put(propertyName, value);
    }


    public Properties getResponseProperties() {
        return properties;
    }

    public abstract T getPayload();

    public abstract void setPayload(T payload);
}
