package com.tibco.cep.designtime.model;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 5:43:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EntityLink extends Entity {


    /**
     * The constants used to determine what kind of link this EntityLink is.
     */
    int INHERITANCE_TYPE = 0;
    int PROPERTY_TYPE = 1;


    /**
     * Returns a flag representing the type for this object.
     *
     * @return A flag representing the type for this object.
     */
    int getType();


    /**
     * Returns the label for this EntityLink, as it would appear within its EntityView.
     *
     * @return The label for this EntityLink, as it would appear within its EntityView.
     */
    String getLabel();


    /**
     * Returns the EntityView for this link.
     *
     * @return The EntityView for this link.
     */
    EntityView getView();


    /**
     * Returns the starting reference for this link.
     *
     * @return The starting reference for this link.
     */
    EntityReference getFrom();


    /**
     * Returns the ending reference for this link.
     *
     * @return The ending reference for this link.
     */
    EntityReference getTo();


    /**
     * Returns the property name for this EntityLink.
     *
     * @return The property name for this EntityLink.
     */
    String getPropertyName();


    /**
     * Returns the underlying Entity for this EntityLink.
     *
     * @return The underlying Entity for thie EntityLink, or null if one doesn't exist;
     */
    Entity getEntity();
}
