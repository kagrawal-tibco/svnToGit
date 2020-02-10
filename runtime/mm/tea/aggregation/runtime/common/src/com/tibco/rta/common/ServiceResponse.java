package com.tibco.rta.common;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/6/13
 * Time: 4:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceResponse {

    private Properties properties;

    public ServiceResponse() {
        properties = new Properties();
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
