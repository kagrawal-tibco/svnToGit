/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Jul 26, 2004
 * Time: 9:02:27 PM
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable;


import java.util.List;

import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.designtime.model.element.stategraph.StateEntity;


public interface MutableStateComposite extends StateComposite, MutableState {


    /**
     * Add the entity passed to this composite state.
     *
     * @param index    Where to add the new entity.
     * @param newState The new entity to add to this composite state.
     */
    void addEntity(
            int index,
            StateEntity newState);


    /**
     * Add the entity passed to this composite state.
     *
     * @param index    Where to add the new entity.
     * @param newState The new entity to add to this composite state.
     */
    void moveEntity(
            int index,
            StateEntity newState);


    /**
     * Add a concurrent machine into the list of concurrent machines in this state.
     *
     * @param index Where to insert the new concurrent machine.  If index > size, the
     *              new machine is added at the end of the list.
     */
    void addRegion(
            int index,
            StateComposite concurrentMachine) throws ModelException;


    /**
     * Delete the entity at the index passed from this composite state.  No action
     * is taken if index is beyond end of List.
     *
     * @param index Which entity to delete.
     */
    void deleteEntity(
            int index);


    /**
     * Delete the entity passed from this composite state.  No action is taken if entity is not present.
     * This removes a delete State's transitions
     *
     * @param entity Which entity to delete.
     */
    void deleteEntity(
            StateEntity entity);


    /**
     * Delete the entity passed from this composite state.  No action is taken if entity is not present.
     * this leaves the delete states transitions
     *
     * @param entity Which entity to delete.
     */
    void removeEntity(
            StateEntity entity);


    /**
     * Delete a region (set of concurrent sub-states) from the list of regions in this state.
     *
     * @param index The index of the region to delete.  If index > size, no action is taken.
     */
    void deleteRegion(
            int index) throws ModelException;


    /**
     * Delete the region (set of concurrent sub-states) passed from the list of regions in this state.
     *
     * @param region The region to delete.
     */
    void deleteRegion(
            MutableStateComposite region) throws ModelException;


    /**
     * Set whether this composite state is a concurrent state.
     *
     * @param isConcurrentState Should this composite state be a concurrent state.
     */
    void setConcurrentState(
            boolean isConcurrentState);


    /**
     * Set the list of regions (concurrent sub-states).  Each element of the List returned should
     * be a StateComposite.
     *
     * @param regions A list of regions (where each element of the List is a StateComposite).
     */
    void setRegions(
            List regions) throws ModelException;


    /**
     * Set the timeout (in milliseconds) for this concurrent set of states.
     *
     * @param milliseconds The new number of milliseconds before this concurrent
     */
    void setTimeout(
            int milliseconds);


}// end interface StateComposite
