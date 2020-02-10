package com.tibco.rta.service.transport;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/11/12
 * Time: 5:24 PM
 * Abstract representation. Consists of properties and payload
 */
public class Message {
    
    private Properties properties = new Properties();
    
    private Object payload;

    public void addProperty(String propertyName, Object value) {
        properties.put(propertyName, value);
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public Properties getMessageProperties() {
        return properties;
    }
}
