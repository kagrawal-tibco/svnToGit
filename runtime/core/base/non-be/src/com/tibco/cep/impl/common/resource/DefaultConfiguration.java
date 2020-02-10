package com.tibco.cep.impl.common.resource;

import java.util.Collection;
import java.util.Properties;
import java.util.Set;

import com.tibco.cep.common.resource.Configuration;
import com.tibco.cep.common.resource.Id;

/*
* Author: Ashwin Jayaprakash / Date: Jun 3, 2010 / Time: 2:42:40 PM
*/
public class DefaultConfiguration implements Configuration {
    protected Properties properties;

    protected Id resourceId;

    protected Id parentId;

    protected Collection<Id> childrenIds;

    public DefaultConfiguration(Id resourceId) {
        this(resourceId, new Properties());
    }

    public DefaultConfiguration(Id resourceId, Properties properties) {
        this.resourceId = resourceId;
        this.properties = properties;
    }

    @Override
    public String setProperty(String key, String value) {
        return (String) properties.setProperty(key, value);
    }

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    @Override
    public Set<String> propertyNames() {
        return properties.stringPropertyNames();
    }

    @Override
    public Id getParentId() {
        return parentId;
    }

    public void setParentId(Id parentId) {
        this.parentId = parentId;
    }

    @Override
    public Collection<Id> getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(Collection<Id> childrenIds) {
        this.childrenIds = childrenIds;
    }

    @Override
    public Id getResourceId() {
        return resourceId;
    }
}
