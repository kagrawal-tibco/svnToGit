package com.tibco.cep.common.resource;

import java.util.Collection;
import java.util.Set;

import com.tibco.cep.util.annotation.Optional;

/*
* Author: Ashwin Jayaprakash / Date: Jun 3, 2010 / Time: 2:36:16 PM
*/
public interface Configuration extends Resource {
    String getProperty(String key);

    String getProperty(String key, String defaultValue);

    Set<String> propertyNames();

    String setProperty(String key, String value);

    @Optional
    Id getParentId();

    @Optional
    Collection<Id> getChildrenIds();
}
