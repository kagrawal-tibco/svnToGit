package com.tibco.cep.query.model;

import com.tibco.cep.query.model.validation.Resolvable;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 24, 2007
 * Time: 12:16:07 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EntityRegistry  extends NamedContext, Resolvable, RegistryContext {

    
    /**
     * Returns an array of Entity's.
     * @return Entity[]
     */
    Entity[] getEntities();


    /**
     * Looks up an Entity by path.
     * @param path String path of the Entity.
     * @return Entity found at the path, or null if not found.
     */
    Entity getEntityByPath(String path);


}
