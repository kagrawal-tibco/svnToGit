package com.tibco.cep.designtime.model.mutable;

import com.tibco.cep.designtime.model.EntityLink;
import com.tibco.cep.designtime.model.EntityReference;


/**
 * Defines a link between two EntityReferences within an EntityView.
 *
 * @author ishaan
 * @version Apr 26, 2004 10:47:18 AM
 */
public interface MutableEntityLink extends EntityLink, MutableEntity {


    /**
     * Sets the starting Reference for this link.
     *
     * @param reference The new starting reference for this link.  If the reference is not of the appropriate type, or not in the view, this method is a NO-OP.
     */
    void setFrom(EntityReference reference);


    /**
     * Sets the ending Reference for this link.
     *
     * @param reference The new ending reference for this link.  If the reference is not of the appropriate type, or not in the view, this method is a NO-OP.
     */
    void setTo(EntityReference reference);


    /**
     * Removes this EntityLink from its 'From' EntityReference.  This is equivalent to getFrom().removeLink(this), if getFrom() does not return null.
     */
    void removeFromOwner();


    /**
     * Sets the property name for this EntityLink.
     *
     * @param propertyName the property name for this Entity Link.
     */
    void setPropertyName(String propertyName);


}
