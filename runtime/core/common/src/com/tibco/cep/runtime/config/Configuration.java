package com.tibco.cep.runtime.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/*
* Author: Ashwin Jayaprakash Date: Nov 10, 2008 Time: 3:57:19 PM
*/
public class Configuration {
    /**
     * {@value}
     */
    public static final String ROOT_CONFIG_NAME = "$_root_$";

    protected final String name;

    protected final Properties properties;

    protected Configuration parent;

    protected Map<String, Configuration> children;

    /**
     * @param name
     * @param properties
     */
    public Configuration(String name, Properties properties) {
        this.name = name;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * @return Can be <code>null</code>.
     */
    public Configuration getParent() {
        return parent;
    }

    /**
     * @return Can be <code>null</code>.
     */
    public Map<String, Configuration> getChildren() {
        return children;
    }

    public void setParent(Configuration parent) {
        this.parent = parent;
    }

    public void setChildren(Map<String, Configuration> children) {
        this.children = children;
    }

    //-------------

    /**
     * Discards children recursively. Also, severs connection with parent.
     */
    public void discard() {
        if (children != null) {
            for (Configuration configuration : children.values()) {
                configuration.discard();
            }

            children.clear();
            children = null;
        }

        parent = null;
    }

    /**
     * Automatically sets the parent for the child.
     *
     * @param child
     */
    public void addChild(Configuration child) {
        if (children == null) {
            children = new HashMap<String, Configuration>();
        }

        String childName = child.getName();

        children.put(childName, child);

        child.setParent(this);
    }

    //-------------

    public void setProperty(String name, String value) {
        properties.setProperty(name, value);
    }

    /**
     * @param propertyName
     * @return The value if present. <code>null</code> otherwise.
     */
    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

    /**
     * @param propertyName
     * @return The value if present. If <code>null</code> and the {@link #getParent() parent} is
     *         present, then tries to get the value from the parent and tries recursively upwards.
     */
    public String getPropertyRecursively(String propertyName) {
        String value = properties.getProperty(propertyName);

        if (value == null && parent != null) {
            value = parent.getPropertyRecursively(propertyName);
        }

        return value;
    }
}
