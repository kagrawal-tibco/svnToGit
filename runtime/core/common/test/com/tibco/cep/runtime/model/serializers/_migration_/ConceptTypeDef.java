package com.tibco.cep.runtime.model.serializers._migration_;

import com.tibco.cep.runtime.model.serializers._migration_.ModelType;

/*
* Author: Ashwin Jayaprakash Date: Jan 16, 2009 Time: 2:42:37 PM
*/
public class ConceptTypeDef implements TypeDef {
    protected String name;

    protected int version;

    protected PropertyDef[] propertyDefs;

    //------------

    public static class PropertyDef {
        protected String name;

        protected ModelType type;

        protected boolean historyRequired;
    }
}
