package com.tibco.be.model.rdf.primitives;

import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Mar 29, 2005
 * Time: 2:32:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class RDFDoubleWrapTerm extends RDFPrimitiveTerm {
    /**
     * @param runtimeTerm - The class that implements the runtime Type. Example is com.tibco.be.engine.model.rdf.PropertyAtomXXX
     * @param isReference - Does this Term represent a reference Type.
     */
    public RDFDoubleWrapTerm(String runtimeTerm, boolean isReference) {
        super(runtimeTerm, isReference);
    }

    public String getName() {
        return "Double_Wrapper";
    }

    public SmType getXSDLTerm() {
        return XSDL.DOUBLE;
    }
}
