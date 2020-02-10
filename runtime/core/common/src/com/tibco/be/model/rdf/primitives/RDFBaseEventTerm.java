package com.tibco.be.model.rdf.primitives;

import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * @author ishaan
 * @version Feb 1, 2005, 2:17:13 PM
 */
public class RDFBaseEventTerm extends RDFBaseTerm {
    /**
     * @param runtimeTerm - The class that implements the runtime Type. Example is com.tibco.be.engine.model.rdf.PropertyAtomXXX
     * @param isReference - Does this Term represent a reference Type.
     */
    public RDFBaseEventTerm(String runtimeTerm, boolean isReference) {
        super(runtimeTerm, isReference);
    }


    public String getName() {
        return "Event";
    }

    public SmType getXSDLTerm() {
        return XSDL.ANY_TYPE;
    }
}
