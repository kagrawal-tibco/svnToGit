package com.tibco.rta.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.rta.model.mutable.MutableMetadataElement;
import com.tibco.rta.model.serialize.jaxb.adapter.PropertyMapAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DESCRIPTION;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DISPLAY_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_PROPERTIES_NAME;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 15/3/13
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */

@XmlAccessorType(XmlAccessType.NONE)
public abstract class BaseMetadadataElementImpl implements MutableMetadataElement {

    private static final long serialVersionUID = -7310807551773207857L;

    @XmlAttribute
    protected String name;

    protected String description;

    protected String displayName;

    protected Map<String, String> metaProps = new LinkedHashMap<String, String>();

    protected BaseMetadadataElementImpl() {

    }

    protected BaseMetadadataElementImpl(String name) {
        this.name = name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @XmlAttribute(name = ATTR_DISPLAY_NAME)
    @Override
    public String getDisplayName() {
        return displayName;
    }

    @XmlAttribute(name = ATTR_DESCRIPTION)
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseMetadadataElementImpl that = (BaseMetadadataElementImpl) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public void setProperty(String name, String value) {
        metaProps.put(name, value);
    }


    @Override
    public String getProperty(String name) {
        return metaProps.get(name);
    }


    @SuppressWarnings("unused")
    @XmlElement(name = ELEM_PROPERTIES_NAME)
    @XmlJavaTypeAdapter(PropertyMapAdapter.class)
    private Map<String, String> getPropertyValueMap() {
        if (metaProps.size() == 0) {
            return null;
        }
        return metaProps;
    }

    private void setPropertyValueMap(Map<String, String> value) {
        metaProps = value;
    }

    @Override
    @JsonIgnore
    public Collection<String> getPropertyNames() {
        return metaProps.keySet();
    }
    @JsonIgnore
    public Map<String, String> getProperties(){
        return metaProps;
    }
    

    @Override
    public String toString() {
        return "BaseMetadadataElementImpl [name=" + name + ", description=" + description + ", displayName="
                + displayName + "]";
    }
}