package com.tibco.cep.query.model.impl;

import java.util.Map;

import com.tibco.cep.query.model.ArrayLengthAttribute;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeInfo;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Nov 1, 2007
 * Time: 2:23:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArrayLengthAttributeImpl extends AbstractModelContext implements ArrayLengthAttribute {


    private final static TypeInfo TYPE_INFO = TypeInfoImpl.PRIMITIVE_INTEGER;

    /**
     * ctor
     *
     * @param parentContext
     */
    public ArrayLengthAttributeImpl(ModelContext parentContext) {
        super(parentContext);
    }


    /**
     * @return Map of name to ModelContext valid in this SelectContext
     */
    public Map<Object, ModelContext> getContextMap() {
        return parentContext.getContextMap();
    }


    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_ARRAY_LENGTH_ATTRIBUTE;
    }


    public String toString() {
        return parentContext +"@length";
    }

    /**
     * @return TypeInfo the type information of the context
     */
    public TypeInfo getTypeInfo() throws Exception {
        return TYPE_INFO;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArrayLengthAttributeImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final ArrayLengthAttributeImpl that = (ArrayLengthAttributeImpl) o;

        try {
            return this.getTypeInfo().equals(that.getTypeInfo());
        } catch (Exception e) {
            return false;
        }
    }

    public int hashCode() {
        try {
            return this.getTypeInfo().hashCode();
        } catch (Exception e) {
            return 0;
        }
    }


}
