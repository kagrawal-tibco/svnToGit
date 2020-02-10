package com.tibco.cep.as.kit.map;

import java.io.Serializable;

import com.tibco.cep.as.kit.tuple.DataType;

/*
* Author: Ashwin Jayaprakash / Date: Dec 2, 2010 / Time: 2:29:21 PM
*/
public class KeyValueTupleDef implements Serializable {
    protected String keyColumnName;

    protected DataType keyType;

    protected String valueColumnName;

    protected DataType valueType;

    public KeyValueTupleDef() {
    }

    public KeyValueTupleDef(String keyColumnName, DataType keyType, String valueColumnName, DataType valueType) {
        this.keyColumnName = keyColumnName;
        this.keyType = keyType;
        this.valueColumnName = valueColumnName;
        this.valueType = valueType;
    }

    public KeyValueTupleDef(KeyValueTupleDef copyFrom) {
        this(copyFrom.getKeyColumnName(), copyFrom.getKeyType(),
                copyFrom.getValueColumnName(), copyFrom.getValueType());
    }

    public DataType getKeyType() {
        return keyType;
    }

    public DataType getValueType() {
        return valueType;
    }

    public String getKeyColumnName() {
        return keyColumnName;
    }

    public String getValueColumnName() {
        return valueColumnName;
    }
}
