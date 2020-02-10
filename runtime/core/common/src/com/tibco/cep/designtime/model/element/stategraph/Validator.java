/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Sep 8, 2004
 */

package com.tibco.cep.designtime.model.element.stategraph;


public interface Validator {


    /**
     * Can the object passed be added to this entity.
     *
     * @param addObject The Object to add to this entity.  The Object can be a List, StateEntity or
     *                  certain subclasses based on the implementing object.
     * @return null if the Object can be added to this object, otherwise return
     *         a string describing the error.
     */
    public String canAdd(
            Object addObject);


    /**
     * Can the object passed be deleted from this entity.
     *
     * @param deleteObject The Object to delete from this entity.  The Object can be a List,
     *                     StateEntity or certain subclasses based on the implementing object.
     * @return null if the Object can be added to this object, otherwise return
     *         a string describing the error.
     */
    public String canDelete(
            Object deleteObject);


    /**
     * Can the object passed act as a start for an action.
     *
     * @param startObject The Object to test if it can start the requested action.
     *                    The Object can be a List, StateEntity or certain subclasses based on the implementing object.
     * @param actionType  The type of action to start.
     * @return null if the Object can be added to this object, otherwise return
     *         a string describing the error.
     */
    public String canStart(
            Object startObject,
            Object actionType);
}// end Validator
