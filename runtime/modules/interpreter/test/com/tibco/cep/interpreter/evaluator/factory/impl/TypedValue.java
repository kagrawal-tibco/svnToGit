package com.tibco.cep.interpreter.evaluator.factory.impl;

import com.tibco.cep.query.model.TypeInfo;

/**
 * User: nprade
 * Date: 9/7/11
 * Time: 4:31 PM
 */
public class TypedValue {


    private final TypeInfo type;
    private final Object value;


    public TypedValue(
            TypeInfo type,
            Object value
    ) {
        this.type = type;
        this.value = value;
    }


    public TypeInfo getType() {
        return type;
    }


    public Object getValue() {
        return value;
    }


}
