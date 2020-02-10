package com.tibco.be.model.rdf.primitives;

import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * @author ishaan
 * @version Feb 1, 2005, 1:03:13 PM
 */
public class RDFBaseConceptTerm extends RDFBaseTerm {
    /**
     * @param runtimeTerm - The class that implements the runtime Type. Example is com.tibco.be.engine.model.rdf.PropertyAtomXXX
     * @param isReference - Does this Term represent a reference Type.
     */
    public RDFBaseConceptTerm(String runtimeTerm, boolean isReference) {
        super(runtimeTerm, isReference);
    }

    public String getName() {
        return "Concept";
    }

    public SmType getXSDLTerm() {  
        return XSDL.ANY_TYPE;
    }
}
