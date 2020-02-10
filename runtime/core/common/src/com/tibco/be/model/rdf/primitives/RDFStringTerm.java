package com.tibco.be.model.rdf.primitives;

import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Aug 5, 2004
 * Time: 7:13:35 PM
 * To change this template use Options | File Templates.
 */
public class RDFStringTerm extends RDFPrimitiveTerm {

    public RDFStringTerm(String runtimeTerm, boolean isReference, int typeId) {
        super(runtimeTerm, isReference, typeId);
    }

    public String getName() {
        return "String";
    }

    public SmType getXSDLTerm() {
        return XSDL.STRING;
    }
}
