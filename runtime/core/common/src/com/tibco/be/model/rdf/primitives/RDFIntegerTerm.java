package com.tibco.be.model.rdf.primitives;

import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Aug 5, 2004
 * Time: 5:55:12 PM
 * To change this template use Options | File Templates.
 */
public class RDFIntegerTerm extends RDFPrimitiveTerm{


    public RDFIntegerTerm(String runtimeTerm, boolean isReference, int typeId) {
        super(runtimeTerm, isReference, typeId);
    }

    public String getName() {
        return "int";
    }



    public SmType getXSDLTerm() {
        return XSDL.INTEGER;
    }
}
