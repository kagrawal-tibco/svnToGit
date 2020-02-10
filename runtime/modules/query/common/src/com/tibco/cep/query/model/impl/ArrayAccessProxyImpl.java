package com.tibco.cep.query.model.impl;

import java.util.Map;

import com.tibco.cep.query.model.ArrayAccessProxy;
import com.tibco.cep.query.model.EntityAttributeProxy;
import com.tibco.cep.query.model.EntityPropertyProxy;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.ProxyContext;
import com.tibco.cep.query.model.TypeInfo;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Dec 7, 2007
 * Time: 3:16:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArrayAccessProxyImpl
        extends AbstractModelContext
        implements ArrayAccessProxy {


    private Expression indexExpression;


    /**
     * ctor
     *
     * @param parentContext ProxyContext
     * @param indexExpression Expression
     */
    public ArrayAccessProxyImpl(ProxyContext parentContext, Expression indexExpression) {
        super(parentContext);
        this.indexExpression = indexExpression;
    }


    /**
     * @return Map of name to ModelContext valid in this SelectContext
     */
    public Map<Object, ModelContext> getContextMap() {
        return this.parentContext.getContextMap();
    }


    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_ARRAY_EXPRESSION;
    }


    public Expression getArrayIndex() {
        return this.indexExpression;
    }


    /**
     * @return TypeInfoImpl the type information of the context
     */
    public TypeInfo getTypeInfo() throws Exception {
        TypeInfo t;
        if (this.parentContext instanceof EntityPropertyProxy) {
            t = ((EntityPropertyProxy) this.parentContext).getProperty().getTypeInfo();
        }
        else if (this.parentContext instanceof EntityAttributeProxy) {
            t = ((EntityAttributeProxy) this.parentContext).getAttribute().getTypeInfo();
        }
        else {
            t = null;
        }

        if (null == t) {
            return null;
        }
        return t.getArrayItemType();
    }


    public String toString() {
        return this.parentContext + "[" + this.indexExpression + "]";
    }
    

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArrayAccessProxy)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final ArrayAccessProxyImpl that = (ArrayAccessProxyImpl) o;
        return this.indexExpression.equals(that.indexExpression)
                && this.parentContext.equals(that.parentContext);
    }


    public int hashCode() {
        long longHash = this.parentContext.hashCode();
        longHash = 29 * longHash + this.indexExpression.hashCode();
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }

}
