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

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_ATTR_REF_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_NAME_NAME;

public class MapAdapter extends XmlAdapter<MapAdapter.EntryMap, Map<String, String>> {

    @XmlAccessorType(XmlAccessType.NONE)
    public static class EntryMap {

        @XmlElement
        public Collection<Entry> entryList = new ArrayList<Entry>();


    }


    public static class Entry {
        @XmlAttribute(name = ATTR_NAME_NAME)
        public String key;

        @XmlAttribute(name = ATTR_ATTR_REF_NAME)
        public String value;

    }

    @Override
    public Map<String, String> unmarshal(EntryMap entryMap) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        for (Entry entry : entryMap.entryList) {
            map.put(entry.key, entry.value);
        }
        return map;
    }

    @Override
    public EntryMap marshal(Map<String, String> map) throws Exception {
        EntryMap entryMap = new EntryMap();

        for (Map.Entry<String, String> mapEntry : map.entrySet()) {
            Entry entry = new Entry();
            entry.key = mapEntry.getKey();
            entry.value = mapEntry.getValue();
            entryMap.entryList.add(entry);
        }
        return entryMap;
    }

}
