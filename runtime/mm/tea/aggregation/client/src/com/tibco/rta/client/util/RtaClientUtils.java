package com.tibco.rta.client.util;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.model.DataType;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.property.impl.PropertyAtomBoolean;
import com.tibco.rta.property.impl.PropertyAtomInt;
import com.tibco.rta.property.impl.PropertyAtomLong;
import com.tibco.rta.property.impl.PropertyAtomObject;
import com.tibco.rta.property.impl.PropertyAtomString;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by aathalye on 11/11/14.
 * <p>
 *     Utility class to provide some convenience methods for API users.
 * </p>
 */
public class RtaClientUtils {

    /**
     * Create SPM server URL based on transport type and other related transport properties.
     * @param configuration : Properties can be read from file or can be created programmatically.
     * @return A server url to connect to SPM/async transport.
     */
    public static String createSPMServerUrl(Properties configuration) {
        String serverUrl;
        String transportType = ConfigProperty.TRANSPORT_TYPE.getPropertyName();

        if ("HTTP".equalsIgnoreCase(configuration.getProperty(transportType))) {
            String host = (String) ConfigProperty.TRANSPORT_HTTP_HOSTNAME.getValue(configuration);
            String port = ConfigProperty.TRANSPORT_HTTP_PORT.getValue(configuration).toString();
            serverUrl = "http://" + host + ":" + port;
        } else {
            serverUrl = (String) ConfigProperty.JMS_PROVIDER_JNDI_URL.getValue(configuration);
        }
        return serverUrl;
    }

    /**
     * A convenience method to create a configuration map from the input property configuration.
     * <p>
     *     The map generated can be used to create the {@link com.tibco.rta.RtaSession}
     * </p>
     * @param configuration : Properties can be read from file or can be created programmatically.
     */
    public static Map<ConfigProperty, PropertyAtom<?>> setupConfigurationMap(Properties configuration) {
        Map<ConfigProperty, PropertyAtom<?>> configurationMap = new HashMap<ConfigProperty, PropertyAtom<?>>();
        Enumeration<?> propertyNamesEnumeration = configuration.propertyNames();

        while (propertyNamesEnumeration.hasMoreElements()) {
            String propertyName = (String) propertyNamesEnumeration.nextElement();
            ConfigProperty configProperty = ConfigProperty.getByPropertyName(propertyName);
            if (configProperty != null) {
                configurationMap.put(configProperty, getPropertyAtom(configProperty.getDataType(), configProperty.getValue(configuration)));
            }
        }
        return configurationMap;
    }

    /**
     * Gets a property atom wrapper for a raw property value based on data type.
     * @param dataType : DataType enum of the property. @see DataType
     * @param value : Value of property
     */
    public static PropertyAtom<?> getPropertyAtom(DataType dataType, Object value) {
        switch (dataType) {
            case BOOLEAN:
                return new PropertyAtomBoolean(Boolean.parseBoolean((String) value));
            case STRING:
                return new PropertyAtomString((String) value);
            case INTEGER:
                return new PropertyAtomInt(Integer.parseInt((String) value));
            case LONG:
                return new PropertyAtomLong(Long.parseLong((String) value));
            default:
                return new PropertyAtomObject(value);
        }
    }
    
    public static Object cloneOf (Object object) {
    	
    	if (object instanceof Serializable) {
    		Object obj = null;
    		byte[] bytes = null;
    		
    		try { //Serialize
    			ByteArrayOutputStream bos = new ByteArrayOutputStream();
    			ObjectOutputStream oos = new ObjectOutputStream(bos);
    			oos.writeObject(object);
    			bytes = bos.toByteArray();
    			oos.close();
    			bos.close();
    		}catch (Exception e){
    			e.printStackTrace(); 
    		}
    		
    		try{ //DeSerialize
    			InputStream is = new ByteArrayInputStream(bytes);
    			ObjectInputStream ois = new ObjectInputStream(is);
    			obj = ois.readObject();
    			ois.close();
    			is.close();
    		}catch (Exception e){
    			e.printStackTrace(); 
    		}
    		return obj;
    	}
    	return null;
    }
}
