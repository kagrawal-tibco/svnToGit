package com.tibco.be.model.rdf.primitives;

import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * @author ishaan
 * @version Jan 21, 2005, 4:56:27 PM
 */

//extends RDFPrimitiveTerm because _BaseException.event.property 
//uses the event file format, and events can only have primitive property types
public class RDFBaseExceptionTerm extends RDFPrimitiveTerm {
    /**
     * @param runtimeTerm - The class that implements the runtime Type. Example is com.tibco.be.engine.model.rdf.PropertyAtomXXX
     * @param isReference - Does this Term represent a reference Type.
     */
    public RDFBaseExceptionTerm(String runtimeTerm, boolean isReference) {
        super(runtimeTerm, isReference);
    }

    public String getName() {
        return "Exception";
    }

    public SmType getXSDLTerm() {
        return XSDL.ANY_TYPE;
    }
}
