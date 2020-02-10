package com.tibco.be.model.rdf.primitives;

import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Aug 5, 2004
 * Time: 7:05:00 PM
 * To change this template use Options | File Templates.
 */
public class RDFDateTimeTerm extends RDFPrimitiveTerm {

    public RDFDateTimeTerm(String runtimeTerm, boolean isReference, int typeId) {
        super(runtimeTerm, isReference, typeId);
    }


    public String getName() {
        return "DateTime";
    }

    public SmType getXSDLTerm() {
        return XSDL.DATETIME;
    }


}
