package com.tibco.cep.interpreter.evaluator.factory.impl;

import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.impl.TypeInfoImpl;

/**
 * User: nprade
 * Date: 9/7/11
 * Time: 2:16 PM
 */
public class NumberLiteralFactory {


    DecimalLiteralFactory decimalLiteralFactory = new DecimalLiteralFactory();


    public TypedValue make(
            String inputValue
    ) throws Exception {

        try {
            return this.decimalLiteralFactory.make(inputValue);

        } catch (NumberFormatException be) {
            if (inputValue.startsWith("0x") || inputValue.startsWith("0X")) {
                throw new Exception("Unsupported Number Literal :" + inputValue);

            } else {
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
    }


}
