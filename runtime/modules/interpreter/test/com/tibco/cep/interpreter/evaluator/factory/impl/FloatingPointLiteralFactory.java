package com.tibco.cep.interpreter.evaluator.factory.impl;

import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.impl.TypeInfoImpl;


/**
 * User: nprade
 * Date: 9/7/11
 * Time: 5:15 PM
 */
public class FloatingPointLiteralFactory {


    public TypedValue make(
            String inputValue
    ) throws Exception {

        Number number;
        TypeInfo typeInfo;
        final String value = inputValue.trim();
        try {
            number = Double.valueOf(value);
            typeInfo = new TypeInfoImpl(double.class);

        } catch (NumberFormatException fe) {
            number = Float.valueOf(value);
            typeInfo = new TypeInfoImpl(float.class);
        }
        return new TypedValue(typeInfo, number);
    }


}
