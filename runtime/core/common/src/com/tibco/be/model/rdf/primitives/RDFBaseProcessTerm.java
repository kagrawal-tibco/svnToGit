package com.tibco.be.model.rdf.primitives;

import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * @author pdhar
 *
 */
public class RDFBaseProcessTerm extends RDFBaseTerm {
    /**
     * @param runtimeTerm - The class that implements the runtime Type. Example is com.tibco.be.engine.model.rdf.PropertyAtomXXX
     * @param isReference - Does this Term represent a reference Type.
     */
    public RDFBaseProcessTerm(String runtimeTerm, boolean isReference) {
        super(runtimeTerm, isReference);
    }

    public String getName() {
        return "Process";
    }

    public SmType getXSDLTerm() {  
        return XSDL.ANY_TYPE;
    }
}
