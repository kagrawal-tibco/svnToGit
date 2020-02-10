package com.tibco.cep.designtime.model.element.mutable;


import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.PropertyInstance;
import com.tibco.cep.designtime.model.mutable.MutableEntity;


/**
 * A PropertyInstance is an Entity representing an instantiation of a
 * PropertyDefinition contained on a Concept, on an Instance of that Concept.
 *
 * @author ishaan
 * @version Mar 18, 2004 4:41:44 PM
 */

public interface MutablePropertyInstance extends PropertyInstance, MutableEntity {


    /**
     * Sets the value of this PropertyInstance.
     *
     * @param value The new value.
     * @throws com.tibco.cep.designtime.model.ModelException
     *          Thrown if the new value does not match the type of the PropertyInstance's definition.
     */
    public void setValue(String value) throws ModelException;


    /**
     * Returns true if this PropertyInstance's value has been set, false otherwise.
     *
     * @return true if this PropertyInstance's value has been set, false otherwise.
     */
    public boolean hasBeenSet();


}
