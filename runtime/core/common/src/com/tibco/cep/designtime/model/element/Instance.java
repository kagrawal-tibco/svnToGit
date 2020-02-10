package com.tibco.cep.designtime.model.element;


import java.util.Collection;
import java.util.List;

import com.tibco.cep.designtime.model.Entity;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 7:37:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Instance extends Entity {


    /**
     * A collection of tbe paths of the secondary domains for this Instance.
     *
     * @return a Collection
     */
    Collection getSecondaryDomains();


    Collection getAllPropertyInstances();


    /**
     * @param definition
     * @return a Collection
     */
    Collection getPropertyInstances(PropertyDefinition definition);


    /**
     * @param propertyName
     * @return a List
     */
    List getPropertyInstances(String propertyName);


    /**
     * Returns true is the referenced PropertyDefinition can be instantiated for this Instance, false otherwise.
     *
     * @param propertyName The name of the PropertyDefinition to test.
     * @return true, if the referenced PropertyDefinition can be instantiated, false otherwise.
     */
    boolean canInstantiate(String propertyName);


    /**
     * Returns the Conccept that defines this Instance.
     *
     * @return The Concept that defines this Instance.
     */
    Concept getConcept();
}
