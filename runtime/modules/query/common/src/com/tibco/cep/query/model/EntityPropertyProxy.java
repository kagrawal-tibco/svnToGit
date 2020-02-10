package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 30, 2007
 * Time: 1:00:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EntityPropertyProxy extends ProxyContext {

    /**
     * Gets the EntityProperty proxied by this object.
     * @return EntityProperty proxied by this object.
     */
    EntityProperty getProperty();


    /**
     * Gets the AliasedIdentifier, EntityPropertyProxy, EntityAttributeProxy, ScopedIdentifier, Projection, etc
     * that describes, in the query, the Entity that owns of the property.  
     * @return ModelContext that describes, in the query, the Entity that owns of the property.
     */
    ModelContext getPropertyOwner();

}
