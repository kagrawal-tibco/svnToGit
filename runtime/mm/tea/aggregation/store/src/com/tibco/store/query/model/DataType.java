package com.tibco.store.query.model;

import java.sql.Timestamp;

/**
 * An enum to define basic datatypes supported.
 */
public enum DataType {

	INTEGER(Integer.class),

	LONG(Long.class),

	DOUBLE(Double.class),

	STRING(String.class),

	BOOLEAN(Boolean.class),

    OBJECT(Object.class),

    SHORT(Short.class),

    BYTE(Byte.class),
	
    TIMESTAMP(Timestamp.class);	

    private Class<?> dataClass;

    private DataType(Class<?> dataClass) {
        this.dataClass = dataClass;
    }

    public Class<?> getDataClass() {
        return dataClass;
    }

    public static DataType getByClass(Class<?> dataClass) {
        DataType[] dataTypes = DataType.values();
        for (DataType dataType : dataTypes) {
            if (dataType.dataClass.getName().equals(dataClass.getName())) {
                return dataType;
            }
        }
        return null;
    }
}
