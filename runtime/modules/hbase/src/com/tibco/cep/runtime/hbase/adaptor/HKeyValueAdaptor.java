package com.tibco.cep.runtime.hbase.adaptor;

import com.tibco.cep.runtime.hbase.types.HDataType;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 10/10/13
 * Time: 12:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class HKeyValueAdaptor {

    private final Map<String, HDataType> fieldDefs;

    public HKeyValueAdaptor() {
        this.fieldDefs = new HashMap<String, HDataType>();
    }

    public void put(String field, HDataType fieldType) {
        fieldDefs.put(field, fieldType);
    }

    public HDataType get(String field) {
        return fieldDefs.get(field);
    }

    public Map<String, HDataType> getSpaceDescription() {
        return Collections.unmodifiableMap(fieldDefs);
    }

    public List<String> getTableFields() {
        List<String> fields = new LinkedList<String>();
        for (Map.Entry<String, HDataType> mEntry : fieldDefs.entrySet()) {
            fields.add(mEntry.getKey());
        }

        return fields;
    }
}
