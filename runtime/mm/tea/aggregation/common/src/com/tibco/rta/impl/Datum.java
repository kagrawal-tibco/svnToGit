package com.tibco.rta.impl;

import com.tibco.rta.model.DataType;

import java.io.Serializable;

public class Datum implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8826387077318035843L;

    String key;
    Object value;
    DataType dataType;

    public Datum() {
    }

    public Datum(String key, Object value, DataType datatype) {
        this.key = key;
        this.value = value;
        this.dataType = datatype;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public DataType getDataType() {
        return dataType;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{key=").append(key).append(", value=").append(value).append("}");
        return sb.toString();
    }

}

