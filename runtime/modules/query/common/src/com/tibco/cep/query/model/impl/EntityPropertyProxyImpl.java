package com.tibco.cep.query.model.impl;

import java.util.Map;

import com.tibco.cep.query.model.EntityProperty;
import com.tibco.cep.query.model.EntityPropertyProxy;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeInfo;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 30, 2007
 * Time: 1:10:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityPropertyProxyImpl
        extends AbstractModelContext
        implements EntityPropertyProxy {


    private EntityProperty property;


    /**
     * ctor
     *
     * @param parentContext
     * @param property
     */
    public EntityPropertyProxyImpl(ModelContext parentContext, EntityProperty property) {
        super(parentContext);
        this.property = property;
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
        return ModelContext.CTX_TYPE_ENTITY_PROPERTY_PROXY;
    }


    /**
     * Gets the EntityProperty proxied by this object.
     *
     * @return EntityProperty proxied by this object.
     */
    public EntityProperty getProperty() {
        return this.property;
    }


    /**
     * Gets the AliasedIdentifier, EntityPropertyProxy, EntityAttributeProxy, ScopedIdentifier, Projection, etc
     * that describes, in the query, the Entity that owns of the property.
     *
     * @return ModelContext that describes, in the query, the Entity that owns of the property.
     */
    public ModelContext getPropertyOwner() {
        return this.parentContext;
    }


    /**
     * @return TypeInfoImpl the type information of the context
     */
    public TypeInfo getTypeInfo() throws Exception {
        return this.property.getTypeInfo();
    }


    public String toString() {
        return this.parentContext + "." + this.property.getName();
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof EntityPropertyProxyImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }
        final EntityPropertyProxyImpl that = (EntityPropertyProxyImpl) o;

        return this.property.equals(that.property)
                && this.parentContext.equals(that.parentContext);
    }


    public int hashCode() {
        long longHash = this.parentContext.hashCode();
        longHash = longHash * 29 + this.property.hashCode();
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }

}
