package com.tibco.be.migration;

import java.util.Properties;
import java.util.PropertyPermission;

import com.tibco.be.util.BEProperties;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 14, 2008
 * Time: 1:48:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEMigrationProperties implements BEMigrationConstants{
    private static Properties properties;

    static {
        Properties systemProperties = System.getProperties();
        String usingSystemProperties = systemProperties.getProperty(USING_SYSTEM_PROPERTIES_KEY);
        if (usingSystemProperties == null || usingSystemProperties.equalsIgnoreCase(Boolean.TRUE.toString())) {
            properties = systemProperties;
        } else {
            // use systemProperties for a snapshot

            properties = new Properties();
            // snapshot of System properties for uses of getProperties who expect to see framework properties set as System properties
            // we need to do this for all system properties because the properties object is used to back

            properties.putAll(systemProperties);
        }
        properties.putAll(BEProperties.loadDefault());
    }

    public static Properties getProperties() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null)
            sm.checkPropertiesAccess();
        return properties;
    }

    public static String getProperty(String key) {
        return getProperty(key, null);
    }

    public static String getProperty(String key, String defaultValue) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null)
            sm.checkPropertyAccess(key);
        return properties.getProperty(key, defaultValue);
    }

    public static String setProperty(String key, String value) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null)
            sm.checkPermission(new PropertyPermission(key, "write"));
        return (String) properties.put(key, value);
    }

    public static String clearProperty(String key) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null)
            sm.checkPermission(new PropertyPermission(key, "write"));
        return (String) properties.remove(key);
    }
}
