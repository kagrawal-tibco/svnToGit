package com.tibco.cep.designtime.model.event.mutable;


import java.util.Map;

import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;


/*
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 10, 2004
 * Time: 12:05:38 AM
 */
public interface MutableEventPropertyDefinition extends EventPropertyDefinition {


    void setType(RDFPrimitiveTerm type);


    void setPropertyName(String propertyName);


    /**
     * Sets extended properties.
     */
    void setExtendedProperties(Map props);


}
