package com.tibco.cep.driver.http.server.impl.servlet.model;

import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaProperty;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomStringSimple;

/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 29/8/11
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleSessionStringAttribute extends PropertyAtomStringSimple {
    private final static MetaProperty META_PROP = new MetaProperty(null, (byte)0, 0, (byte)PropertyDefinition.PROPERTY_TYPE_STRING, false, null, false);

    public SimpleSessionStringAttribute(Concept owner, String defaultValue) {
        super(owner, defaultValue);
    }

    public int getPropertyIndex() {
        return 0;
    }

    @Override
    public MetaProperty getMetaProperty() {
        return META_PROP;
    }
}
