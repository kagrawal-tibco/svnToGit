package com.tibco.be.model.rdf.primitives;

import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Aug 5, 2004
 * Time: 7:10:23 PM
 * To change this template use Options | File Templates.
 */
public class RDFConceptReferenceTerm extends RDFBaseConceptTerm {

    public RDFConceptReferenceTerm(String runtimeTerm, boolean isReference) {
        super(runtimeTerm, isReference);
    }

    public String getName() {
        return "ConceptReference";
    }

    public SmType getXSDLTerm() {
        return XSDL.URI_REFERENCE;
    }

}
