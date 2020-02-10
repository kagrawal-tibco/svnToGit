package com.tibco.cep.as.kit.tuple;

import com.tibco.as.space.FieldDef.FieldType;


/*
* Author: Ashwin Jayaprakash Date: Apr 24, 2009 Time: 4:27:48 PM
*/

public enum DataType {
    Boolean(Boolean.class, FieldType.BOOLEAN),

    Short(Short.class, FieldType.SHORT),
    Integer(Integer.class, FieldType.INTEGER),
    Long(Long.class, FieldType.LONG),
    Float(Float.class, FieldType.FLOAT),
    Double(Double.class, FieldType.DOUBLE),

    Char(Character.class, FieldType.CHAR),
    String(String.class, FieldType.STRING),

    Date(java.util.Date.class, FieldType.DATETIME),

    RawBlob(byte[].class, FieldType.BLOB),
    SerializedBlob(Object.class, FieldType.BLOB);

    //-----------

    Class supportedJavaClass;

    FieldType fieldType;

    /**
     * @param supportedJavaClass
     * @param fieldType          See {@link FieldType#BLOB} and others.
     */
    DataType(Class supportedJavaClass, FieldType fieldType) {
        this.supportedJavaClass = supportedJavaClass;
        this.fieldType = fieldType;
    }

    public Class getSupportedJavaClass() {
        return supportedJavaClass;
    }

    public FieldType getFieldType() {
        return fieldType;
    }
}
