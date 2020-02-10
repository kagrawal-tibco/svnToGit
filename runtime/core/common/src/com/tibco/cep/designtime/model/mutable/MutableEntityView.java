package com.tibco.cep.designtime.model.mutable;


import java.util.Collection;

import com.tibco.cep.designtime.model.EntityReference;
import com.tibco.cep.designtime.model.EntityView;
import com.tibco.cep.designtime.model.ModelException;


/**
 * This is the base interface for all views on the Ontology.
 *
 * @author ishaan
 * @version Mar 19, 2004 5:32:37 PM
 */
public interface MutableEntityView extends EntityView, MutableEntity {


    /**
     * Returns only valid EntityLinks in this view.
     *
     * @return All valid EntityLinks in thsi view.
     * @throws ModelException Thrown if there was an Exception creating the links.
     */
    public Collection refreshLinks() throws ModelException;


    /**
     * Removes the specified reference from this view.
     *
     * @param path The reference to remove.
     */
    public void removeReference(String path);


    /**
     * Creates a reference to the specified Entity in this diagram.
     *
     * @param entityPath The Entity for which to create a reference.
     * @throws ModelException Thrown if instance is null, or a reference to entityPath exists in the view.
     */
    public MutableEntityReference createReference(String entityPath) throws ModelException;


    /**
     * Adds an existing EntityReference to this EntityView.
     *
     * @param reference The reference to add.
     * @throws ModelException Thrown if the this.containsReferenceTo(reference.getEntityPath()) returns true.
     */
    public void addEntityReference(EntityReference reference) throws ModelException;
}
