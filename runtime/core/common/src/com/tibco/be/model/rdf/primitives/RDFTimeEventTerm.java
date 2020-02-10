package com.tibco.be.model.rdf.primitives;

import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * @author ishaan
 * @version Jan 21, 2005, 4:56:27 PM
 */
public class RDFTimeEventTerm extends RDFBaseEventTerm {
    /**
     * @param runtimeTerm - The class that implements the runtime Type. Example is com.tibco.be.engine.model.rdf.PropertyAtomXXX
     * @param isReference - Does this Term represent a reference Type.
     */
    public RDFTimeEventTerm(String runtimeTerm, boolean isReference) {
        super(runtimeTerm, isReference);
    }

    public String getName() {
        return "TimeEvent";
    }

    public SmType getXSDLTerm() {
        return XSDL.ANY_TYPE;
    }
}
