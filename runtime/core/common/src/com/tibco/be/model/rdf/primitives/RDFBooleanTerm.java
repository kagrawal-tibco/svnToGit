package com.tibco.be.model.rdf.primitives;

import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * @author ishaan
 * @version Aug 5, 2004, 8:27:16 PM
 */
public class RDFBooleanTerm extends RDFPrimitiveTerm {

    public RDFBooleanTerm(String runtimeTerm, boolean isReference, int typeId) {
        super(runtimeTerm, isReference, typeId);
    }

    public String getName() {
        return "boolean";
    }

    public SmType getXSDLTerm() {
        return XSDL.BOOLEAN;
    }
}
