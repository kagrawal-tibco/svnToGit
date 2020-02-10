package com.tibco.cep.designtime.model.event;


import java.util.Collection;
import java.util.Map;

import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;


/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 8:32:03 PM
 */
public interface EventPropertyDefinition {


    Event getOwner();


    String getPropertyName();


    RDFPrimitiveTerm getType();


    Collection getAttributeDefinitions();


    EventPropertyDefinition getAttributeDefinition(String attributeName);


    /**
     * Returns the extended properties associated with the EventPropertyDefinition.
     *
     * @return Returns the extended properties associated with the EventPropertyDefinition.
     */
    Map getExtendedProperties();

}
