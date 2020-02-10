package com.tibco.cep.dashboard.psvr.plugin;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author apatil
 *
 */
abstract class PropertyBasedFactory {
    
    protected Properties properties;

    protected void loadPropertyFile(String propertyFileName) throws IOException{
        properties = new Properties();
        URL propertyLocationURL = PropertyBasedFactory.class.getResource("/"+propertyFileName);
        properties.load(propertyLocationURL.openStream());
    }
    
    protected String getValue(String key){
        return (String) properties.get(key);
    }
    
    public Enumeration<Object> keys() {
        return properties.keys();
    }
}