package com.tibco.cep.designtime.model.element;


import com.tibco.cep.designtime.model.EntityReference;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 7:30:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ConceptReference extends EntityReference {


    /**
     * Returns the Concept to which this Object is a reference.
     *
     * @return The Concept to which this Object is a reference.
     */
    Concept getConcept();
}
