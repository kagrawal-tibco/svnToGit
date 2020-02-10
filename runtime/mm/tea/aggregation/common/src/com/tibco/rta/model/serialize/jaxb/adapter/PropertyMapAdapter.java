package com.tibco.rta.model.serialize.jaxb.adapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_NAME_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_VALUE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_PROPERTY_NAME;

public class PropertyMapAdapter extends XmlAdapter<PropertyMapAdapter.PropertyMap, Map<String, String>> {
    @XmlAccessorType(XmlAccessType.NONE)
    public static class PropertyMap {

        @XmlElement(name = ELEM_PROPERTY_NAME)
        public Collection<PropertyEntry> propertyList = new ArrayList<PropertyEntry>();


    }


    public static class PropertyEntry {
        @XmlAttribute(name = ATTR_NAME_NAME)
        public String key;

        @XmlAttribute(name = ATTR_VALUE_NAME)
        public String value;

    }

    @Override
    public Map<String, String> unmarshal(PropertyMap entryMap) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        for (PropertyEntry entry : entryMap.propertyList) {
            map.put(entry.key, entry.value);
        }
        return map;
    }

    @Override
    public PropertyMap marshal(Map<String, String> map) throws Exception {
        PropertyMap entryMap = null;
        if (map != null) {
            entryMap = new PropertyMap();
            for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                PropertyEntry entry = new PropertyEntry();
                entry.key = mapEntry.getKey();
                entry.value = mapEntry.getValue();
                entryMap.propertyList.add(entry);
            }
        }
        return entryMap;
    }

}
