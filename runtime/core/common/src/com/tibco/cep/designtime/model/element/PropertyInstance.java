package com.tibco.cep.designtime.model.element;


import com.tibco.cep.designtime.model.Entity;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 8:04:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PropertyInstance extends Entity {


    /**
     * Returns the Instance that owns this PropertyInstance.
     *
     * @return The Instance that owns this PropertyInstance.
     */
    Instance getInstance();


    /**
     * Returns the value of this PropertyInstance.
     *
     * @return The value of this PropertyInstance.
     */
    String getValue();


    /**
     * Returns the PropertyDefinition to which this PropertyInstance belongs.
     *
     * @return The PropertyDefinition to which this PropertyInstance belongs.
     */
    PropertyDefinition getPropertyDefinition();


    /**
     * Returns the name of the owning PropertyDefinition.
     *
     * @return The name of the owning PropertyDefinition.
     */
    String getPropertyDefinitionName();
}
