package com.tibco.cep.runtime.hbase.cacheaside;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 6/14/13
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class HbaseSpaceDescriptor {

    private final Map<String, String> fieldDefs;

    public HbaseSpaceDescriptor() {
        this.fieldDefs = new HashMap<String, String>();
    }

    public void put(String field, String fieldType) {
        fieldDefs.put(field, fieldType);
    }

    public String get(String field) {
        return fieldDefs.get(field);
    }

    public Map<String, String> getSpaceDescription() {
        return Collections.unmodifiableMap(fieldDefs);
    }

    public List<String> getSpaceFields() {
        List<String> fields = new LinkedList<String>();
        for (Map.Entry<String, String> mEntry : fieldDefs.entrySet()) {
            fields.add(mEntry.getKey());
        }

        return fields;
    }
}

