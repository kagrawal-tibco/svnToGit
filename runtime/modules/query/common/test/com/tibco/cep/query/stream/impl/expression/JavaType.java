package com.tibco.cep.query.stream.impl.expression;

import java.util.Calendar;

/*
* Author: Ashwin Jayaprakash Date: Jun 9, 2008 Time: 4:33:54 PM
*/
public enum JavaType {
    BYTE(Byte.TYPE),
    SHORT(Short.TYPE),
    INTEGER(Integer.TYPE),
    LONG(Long.TYPE),
    FLOAT(Float.TYPE),
    DOUBLE(Double.TYPE),

    BOOLEAN(Boolean.TYPE),

    STRING(String.class),

    CALENDAR(Calendar.class),
    OBJECT(Object.class);

    //-----------

    Class javaClass;

    JavaType(Class javaClass) {
        this.javaClass = javaClass;
    }

    public Class getJavaClass() {
        return javaClass;
    }
}
