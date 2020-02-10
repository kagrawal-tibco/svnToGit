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

public class KeyDimensionMapAdapter extends XmlAdapter<KeyDimensionMapAdapter.AdaptedMap, Map<String, Object>> {

    @XmlAccessorType(XmlAccessType.NONE)
    public static class AdaptedMap {

        @XmlElement(name = "entry")
        public Collection<EntryObject> entryList = new ArrayList<EntryObject>();

    }


    public static class EntryObject {
        @XmlAttribute(name = "key-name")
        public String key;

        @XmlElement(name = "key-value")
        public Object value;

    }


    @Override
    public Map<String, Object> unmarshal(AdaptedMap entryMap) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        for (EntryObject entry : entryMap.entryList) {
            map.put(entry.key, entry.value);
        }
        return map;
    }


    @Override
    public AdaptedMap marshal(Map<String, Object> map) throws Exception {
        AdaptedMap entryMap = new AdaptedMap();

        for (Map.Entry<String, Object> mapEntry : map.entrySet()) {
            EntryObject entry = new EntryObject();
            entry.key = mapEntry.getKey();
            entry.value = mapEntry.getValue();
            entryMap.entryList.add(entry);
        }
        return entryMap;
    }

}
