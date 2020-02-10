package com.tibco.cep.designtime.model.event.mutable.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.designtime.model.event.mutable.MutableEventPropertyDefinition;


/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 10, 2004
 * Time: 12:02:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultMutableEventPropertyDefinition implements MutableEventPropertyDefinition {


    private static final String IS_SET_ATTRIB_NAME = "isSet";
    public static final List m_atomAttributes = new ArrayList();


    static {
        m_atomAttributes.add(new DefaultMutableEventPropertyDefinition(null, IS_SET_ATTRIB_NAME, (RDFPrimitiveTerm) RDFTypes.BOOLEAN));
    }


    protected DefaultMutableEvent event;
    protected String propertyName;
    protected RDFPrimitiveTerm type;
    protected Map extendedProperties;


    public DefaultMutableEventPropertyDefinition(DefaultMutableEvent event, String propertyName, RDFPrimitiveTerm type) {
        this.event = event;
        this.propertyName = propertyName;
        this.type = type;
        this.extendedProperties = new TreeMap();
    }


    public Event getOwner() {
        return event;
    }


    /**
     * @return a String
     */
    public String getPropertyName() {
        return propertyName;
    }


    /**
     * @return an RDFPrimitiveTerm
     */
    public RDFPrimitiveTerm getType() {
        return type;
    }


    /**
     * @param type
     */
    public void setType(RDFPrimitiveTerm type) {
        this.type = type;
    }


    /**
     * @param propertyName
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }


    /**
     * @return a Collection
     */
    public Collection getAttributeDefinitions() {
        return m_atomAttributes;
    }


    /**
     * @param attributeName
     * @return an EventPropertyDefinition
     */
    public EventPropertyDefinition getAttributeDefinition(String attributeName) {
        return matchAttribute(attributeName, m_atomAttributes);
    }


    /**
     * @param attributeName
     * @param list
     * @return an EventPropertyDefinition
     */
    private EventPropertyDefinition matchAttribute(String attributeName, List list) {
        for (int i = 0; i < list.size(); i++) {
            EventPropertyDefinition prop = (EventPropertyDefinition) list.get(i);
            if (prop.getPropertyName().equals(attributeName)) {
                return prop;
            }
        }
        return null;
    }


    public void setExtendedProperties(Map props) {
        if (null == props) {
            this.extendedProperties = getDefaultExtendedProperties();
        } else {
            this.extendedProperties = props;
        }

        HashMap bs = (HashMap) extendedProperties.get(Entity.EXTPROP_PROPERTY_BACKINGSTORE);
        if (bs == null) {
            bs = new HashMap();
            extendedProperties.put(Entity.EXTPROP_PROPERTY_BACKINGSTORE, bs);
        }

        String columnName= (String) bs.get(Entity.EXTPROP_PROPERTY_BACKINGSTORE_COLUMNNAME);
        if (columnName == null) {
            bs.put(Entity.EXTPROP_PROPERTY_BACKINGSTORE_COLUMNNAME, "");
        }

        String maxLength= (String) bs.get(Entity.EXTPROP_PROPERTY_BACKINGSTORE_MAXLENGTH);
        if (maxLength == null) {
            bs.put(Entity.EXTPROP_PROPERTY_BACKINGSTORE_MAXLENGTH, "");
        }

//        super.setExtendedProperties(props);    //To change body of overridden methods use File | Settings | File Templates.
    }


    public static Map<String, Object> getDefaultExtendedProperties() {
        final Map<String, Object> extendedProperties = new LinkedHashMap<String, Object>();
        final Map<String, Object> backingStoreProperties = new LinkedHashMap<String, Object>();
        backingStoreProperties.put("Column Name", "");
        backingStoreProperties.put("Max Length", "");
        extendedProperties.put("Backing Store", backingStoreProperties);
        return extendedProperties;
    }


    public Map getExtendedProperties() {
        return this.extendedProperties;
    }


}
