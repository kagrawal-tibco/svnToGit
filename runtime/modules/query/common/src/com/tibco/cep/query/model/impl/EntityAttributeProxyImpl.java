package com.tibco.cep.query.model.impl;

import java.util.Map;

import com.tibco.cep.query.model.EntityAttribute;
import com.tibco.cep.query.model.EntityAttributeProxy;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeInfo;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 30, 2007
 * Time: 1:05:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityAttributeProxyImpl
        extends AbstractModelContext
        implements EntityAttributeProxy {


    private EntityAttribute attribute;


    /**
     * ctor
     *
     * @param parentContext
     * @param attribute
     */
    public EntityAttributeProxyImpl(ModelContext parentContext, EntityAttribute attribute) {
        super(parentContext);
        this.attribute = attribute;
    }


    /**
     * Gets the EntityAttribute proxied by this object.
     *
     * @return EntityAttribute proxied by this object.
     */
    public EntityAttribute getAttribute() {
        return this.attribute;
    }


    /**
     * Gets the AliasedIdentifier, EntityPropertyProxy, EntityAttributeProxy, ScopedIdentifier, Projection, etc
     * that describes, in the query, the Entity that owns of the attribute.
     *
     * @return ModelContext that describes, in the query, the Entity that owns of the attribute.
     */
    public ModelContext getAttributeOwner() {
        return this.parentContext;
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
        return ModelContext.CTX_TYPE_ENTITY_ATTRIBUTE_PROXY;
    }


    /**
     * @return TypeInfoImpl the type information of the context
     */
    public TypeInfo getTypeInfo() throws Exception {
        return this.attribute.getTypeInfo();
    }


    public String toString() {
        return this.parentContext + "@" + this.attribute.getName();
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntityAttributeProxyImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }
        final EntityAttributeProxyImpl that = (EntityAttributeProxyImpl) o;
        return this.attribute.equals(that.attribute)
                && this.parentContext.equals(that.parentContext);
    }


    public int hashCode() {
        long longHash = this.parentContext.hashCode();
        longHash = longHash * 29 + this.attribute.hashCode();
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }

}
