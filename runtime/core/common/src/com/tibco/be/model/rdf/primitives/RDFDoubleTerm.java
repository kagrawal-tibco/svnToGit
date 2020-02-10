package com.tibco.be.model.rdf.primitives;

import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Aug 5, 2004
 * Time: 6:06:08 PM
 * To change this template use Options | File Templates.
 */
public class RDFDoubleTerm extends RDFPrimitiveTerm {

    public RDFDoubleTerm(String referenceName, boolean isReference, int typeId) {
        super(referenceName, isReference, typeId);
    }

    public String getName() {
        return "double";
    }

    public SmType getXSDLTerm() {
        return XSDL.DOUBLE;
    }


}
