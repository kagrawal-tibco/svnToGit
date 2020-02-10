package com.tibco.cep.designtime.model.mutable;


import java.awt.Point;
import java.util.Collection;

import com.tibco.cep.designtime.model.EntityLink;
import com.tibco.cep.designtime.model.EntityReference;
import com.tibco.cep.designtime.model.ModelException;


/**
 * The superinterface for all references (InstanceReference, ConceptReference, etc.) used
 * within somesort of EntityView.
 *
 * @author ishaan
 * @version Mar 19, 2004 6:36:19 PM
 */

public interface MutableEntityReference extends EntityReference, MutableEntity {


    /**
     * Sets the path of the Entity referenced by this EntityReference.
     *
     * @param path The path to the Entity.
     * @throws ModelException
     */

    public void setEntityPath(String path) throws ModelException;


    /**
     * Sets the location of the Reference in its View.
     *
     * @param point The new location for this Reference in its View.
     */
    public void setLocation(Point point);


    /**
     * Returns a set of EntityLinks originating from this EntityReference, contained in its EntityView.
     *
     * @return A set of EntityLinks originating from this EntityReference, contained in its EntityView.
     * @throws ModelException Thrown if there is any problem creating the links.
     */
    public Collection refreshLinks() throws ModelException;


    /**
     * Removes the supplied EntityLink from the this EntityReference, it it exists.
     *
     * @param link The link to remove.
     */
    public void removeLink(EntityLink link);
}
