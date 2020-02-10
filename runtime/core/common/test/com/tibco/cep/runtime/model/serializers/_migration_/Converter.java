package com.tibco.cep.runtime.model.serializers._migration_;

/*
* Author: Ashwin Jayaprakash Date: Jan 16, 2009 Time: 3:34:48 PM
*/
public class Converter<I extends TypeDef, O extends TypeDef> {
    protected ConversionDef<I, O> converterDef;

    protected ClassLoader newConceptClassLoader;

    public Object convert(Object input) {
        return null;
    }
}
