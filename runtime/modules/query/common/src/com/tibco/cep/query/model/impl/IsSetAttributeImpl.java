package com.tibco.cep.query.model.impl;

import java.util.Map;

import com.tibco.cep.query.model.IsSetAttribute;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeInfo;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Nov 1, 2007
 * Time: 2:23:03 PM
 */
public class IsSetAttributeImpl extends AbstractModelContext implements IsSetAttribute {


    private final static TypeInfo TYPE_INFO = TypeInfoImpl.PRIMITIVE_BOOLEAN;


    /**
     * ctor
     *
     * @param parentContext
     */
    public IsSetAttributeImpl(ModelContext parentContext) {
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
        return ModelContext.CTX_TYPE_IS_SET_ATTRIBUTE;
    }


    public String toString() {
        return parentContext + "@isSet";
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
        if (!(o instanceof IsSetAttributeImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final IsSetAttributeImpl that = (IsSetAttributeImpl) o;

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