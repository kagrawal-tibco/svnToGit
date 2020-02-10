package com.tibco.cep.interpreter.evaluator.factory.impl;

import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.impl.TypeInfoImpl;

/**
 * User: nprade
 * Date: 9/7/11
 * Time: 2:16 PM
 */
public class DecimalLiteralFactory {


    public TypedValue make(
            String inputValue
    ) throws Exception {

        if (inputValue.startsWith("0x") || inputValue.startsWith("0X")) {
            return this.make(inputValue.substring("0x".length()).trim(), 16);

        } else if (inputValue.startsWith("0")) {
            return this.make(inputValue.substring("0".length()).trim(), 8);

        } else {
            return this.make(inputValue.trim(), 10);
        }
    }


    public TypedValue make(
            String value,
            int radix
    ) throws Exception {

        Number number;
        TypeInfo typeInfo;

        if (value.endsWith("l") || value.endsWith("L")) {
            number = Long.valueOf(value, radix);
            typeInfo = new TypeInfoImpl(long.class);

        } else {
//            try {
//                number = Byte.valueOf(value, radix);
//                typeInfo = new TypeInfoImpl(byte.class);
//
//            } catch (NumberFormatException be) {
//                try {
//                    number = Short.parseShort(value, radix);
//                    typeInfo = new TypeInfoImpl(short.class);
//
//                } catch (NumberFormatException se) {
                    try {
                        number = Integer.valueOf(value, radix);
                        typeInfo = new TypeInfoImpl(int.class);

                    } catch (NumberFormatException ie) {
                        try {
                        number = Long.valueOf(value, radix);
                        typeInfo = new TypeInfoImpl(long.class);

                        } catch (NumberFormatException le) { // May be bigger than a long can hold(?)
                            if (radix != 10) {
                                throw le;
                            }
                            try {
                                number = Double.valueOf(value);
                                typeInfo = new TypeInfoImpl(double.class);

                            } catch (NumberFormatException de) {
                                number = Float.valueOf(value);
                                typeInfo = new TypeInfoImpl(float.class);
                            }
                        }
                    }
//                }
//            }
        }
        return new TypedValue(typeInfo, number);
    }


}
