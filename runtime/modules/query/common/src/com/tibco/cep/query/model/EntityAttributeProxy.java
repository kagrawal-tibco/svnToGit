package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 30, 2007
 * Time: 1:04:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EntityAttributeProxy extends ProxyContext {

    /**
     * Gets the EntityAttribute proxied by this object.
     * @return EntityAttribute proxied by this object.
     */
    EntityAttribute getAttribute();


    /**
     * Gets the AliasedIdentifier, EntityPropertyProxy, EntityAttributeProxy, ScopedIdentifier, Projection, etc
     * that describes, in the query, the Entity that owns of the attribute.
     * @return ModelContext that describes, in the query, the Entity that owns of the attribute.
     */
    ModelContext getAttributeOwner();

}
