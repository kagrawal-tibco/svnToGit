package com.tibco.cep.designtime.model.element.mutable;


import com.tibco.cep.designtime.model.EntityChangeListener;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.Instance;
import com.tibco.cep.designtime.model.mutable.MutableEntity;


/**
 * An Instance is an Entity whose relation to Concept is analogous
 * to a Java object's relationship to its class.
 *
 * @author ishaan
 * @version Mar 16, 2004 8:38:55 PM
 */

public interface MutableInstance extends Instance, MutableEntity, EntityChangeListener {


    /**
     * @param folderPath
     * @throws com.tibco.cep.designtime.model.ModelException
     *
     */
    public void addSecondaryDomain(String folderPath) throws ModelException;


    /**
     * @param folderPath
     */
    public void removeSecondaryDomain(String folderPath);


    /**
     * Removes the specified PropertyInstance from this Instance.
     *
     * @param propertyInstance The PropertyInstance to remove.
     */
    public void deletePropertyInstance(MutablePropertyInstance propertyInstance);


    /**
     * Deletes a PropertyInstance from this Instance corresponding to the specified name and index.
     *
     * @param name  The name of the PropertyInstance/its PropertyDefinition.
     * @param index The index which to delete (in case the multiplicity is greater than one).  Note, this may reorder other PropertyInstances.
     */
    public void deletePropertyInstance(String name, int index);


    /**
     * Removes all Instances of a PropertyDefinition.
     *
     * @param pd The definition to remove.
     */
    public void deletePropertyInstances(MutablePropertyDefinition pd);


    /**
     * Creates a PropertyInstance for this Instance.
     *
     * @param propertyName The name of the definition to instantiate.
     * @return The new PropertyInstance.
     * @throws ModelException If the reference PropertyDefinition does not belong to owning Concept, or no more instances of it may be created.
     */
    public MutablePropertyInstance createPropertyInstance(String propertyName) throws ModelException;


    /**
     * Sets the defining Concept for this Instance.
     *
     * @param concept The defining Concept for this Instance.
     * @throws ModelException if concept is null or otherwise invaild.
     */
    public void setConcept(MutableConcept concept) throws ModelException;
}
