package com.tibco.cep.query.stream.impl.expression;

/*
* Author: Ashwin Jayaprakash Date: Jun 10, 2008 Time: 12:30:07 PM
*/
public enum ModelType {
    SIMPLE_EVENT(JavaType.OBJECT, true),
    CONCEPT(JavaType.OBJECT, true),

    PROPERTY_ARRAY_BOOLEAN(JavaType.BOOLEAN),
    PROPERTY_ARRAY_CONCEPT(JavaType.OBJECT),
    PROPERTY_ARRAY_CONCEPT_REFERENCE(JavaType.OBJECT),
    PROPERTY_ARRAY_CONTAINED_CONCEPT(JavaType.OBJECT),
    PROPERTY_ARRAY_DATETIME(JavaType.CALENDAR),
    PROPERTY_ARRAY_DOUBLE(JavaType.DOUBLE),
    PROPERTY_ARRAY_INT(JavaType.INTEGER),
    PROPERTY_ARRAY_LONG(JavaType.LONG),
    PROPERTY_ARRAY_STRING(JavaType.STRING),

    PROPERTY_ATOM_BOOLEAN(JavaType.BOOLEAN),
    PROPERTY_ATOM_CONCEPT(JavaType.OBJECT),
    PROPERTY_ATOM_CONCEPT_REFERENCE(JavaType.OBJECT),
    PROPERTY_ATOM_CONTAINED_CONCEPT(JavaType.OBJECT),
    PROPERTY_ATOM_DATETIME(JavaType.CALENDAR),
    PROPERTY_ATOM_DOUBLE(JavaType.DOUBLE),
    PROPERTY_ATOM_INT(JavaType.INTEGER),
    PROPERTY_ATOM_LONG(JavaType.LONG),
    PROPERTY_ATOM_STRING(JavaType.STRING);

    //-----------

    private static final long serialVersionUID = 1L;

    JavaType javaTypeOfExtractableValue;

    boolean extractablePropertyNameNeeded;

    ModelType(JavaType javaTypeOfExtractableValue) {
        this(javaTypeOfExtractableValue, false);
    }

    ModelType(JavaType javaTypeOfExtractableValue, boolean extractablePropertyNameNeeded) {
        this.javaTypeOfExtractableValue = javaTypeOfExtractableValue;
        this.extractablePropertyNameNeeded = extractablePropertyNameNeeded;
    }

    public boolean isExtractablePropertyNameNeeded() {
        return extractablePropertyNameNeeded;
    }

    public JavaType getJavaTypeOfExtractableValue() {
        return javaTypeOfExtractableValue;
    }
}
