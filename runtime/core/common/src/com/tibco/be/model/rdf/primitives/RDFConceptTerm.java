package com.tibco.be.model.rdf.primitives;

import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Aug 5, 2004
 * Time: 7:07:37 PM
 * To change this template use Options | File Templates.
 */
public class RDFConceptTerm extends RDFBaseConceptTerm {

    public RDFConceptTerm(String runtimeTerm, boolean isReference) {
        super(runtimeTerm, isReference);
    }

    public String getName() {
        return "ContainedConcept";
    }

    public SmType getXSDLTerm() {
        return XSDL.ANY_TYPE;
    }
}
