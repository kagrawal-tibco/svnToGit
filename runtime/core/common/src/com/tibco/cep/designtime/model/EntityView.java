package com.tibco.cep.designtime.model;


import java.util.Collection;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 6:10:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EntityView extends Entity {


    /**
     * Returns all EntityReferences in this view.
     *
     * @return A Collection of all EntityReferences in this view.
     */
    Collection getReferences();


    /**
     * Returns all String paths referenced in thsi view.
     *
     * @return A collection of all String paths referenced in thsi view.
     */
    Collection getReferencedPaths();


    /**
     * Returns all EntityLinks in this view.
     *
     * @return All EntityLinks in this view.
     * @throws com.tibco.cep.designtime.model.ModelException
     *          Thrown if there was an Exception creating the links.
     */
    Collection getLinks() throws ModelException;


    /**
     * Returns true if this view contains a reference to the specified Entity.
     *
     * @param entity The entity to check for a reference.
     * @return True if the view contains a reference to entity, false otherwise.
     */
    boolean containsReferenceTo(Entity entity);


    /**
     * Returns true if this view contains a reference to the specified Entity.
     *
     * @param entityPath The path of the Entity to check for a reference.
     * @return True if the view contains a reference to entity, false otherwise.
     */
    boolean containsReferenceTo(String entityPath);


    /**
     * Returns an EntityReference for the path specified.
     *
     * @param fullPath The full path of the Entity for which to find a reference.
     * @return The reference if it exists, or null if it doesn't.
     */
    EntityReference getReference(String fullPath);
}
