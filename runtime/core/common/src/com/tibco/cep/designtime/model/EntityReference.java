package com.tibco.cep.designtime.model;


import java.awt.Point;
import java.util.Collection;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 5:57:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EntityReference extends Entity {


    /**
     * Returns the Point corresponding to this Reference in its View.
     *
     * @return The Point corresponding to this Reference in its View.
     */
    Point getLocation();


    /**
     * Returns the Entity being referenced.
     *
     * @return The Entity being referenced.
     */
    Entity getEntity();


    /**
     * Returns the path of the Entity being referenced.
     *
     * @return The path of the Entity being referenced.
     */
    String getEntityPath();


    /**
     * Returns the EntityView in which this Reference resides.
     *
     * @return The EntityView in which this Reference resides.
     */
    EntityView getView();


    /**
     * Returns the set of EntityLinks returned by the previous call to refreshLinks().
     *
     * @return the set of EntityLinks returned by the previous call to refreshLinks().
     * @throws ModelException
     *          Thrown if there is any problem creating the links.
     */
    Collection getLinks() throws ModelException;


    /**
     * Finds a Link that matches the criteria.
     *
     * @param underlyingEntity The path of the Entity to which this Link points.
     * @return The link, if found, or null otherwise.
     * @throws ModelException
     *          Thrown if there is an error while retrieving the Links.
     */
    EntityLink getLink(Entity underlyingEntity) throws ModelException;


    /**
     * Returns all EntityLinks that point from this EntityReference to the specified one.
     *
     * @param er The EntityReference to which all of the returned EntityLinks will point.
     * @return All EntityLinks that point from this EntityReference to the specified one.
     * @throws ModelException
     *          Thrown if an error occurs while fetching the EntityLinks.
     */
    Collection getLinksTo(EntityReference er) throws ModelException;
}
