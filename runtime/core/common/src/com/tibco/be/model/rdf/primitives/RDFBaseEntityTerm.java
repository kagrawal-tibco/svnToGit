package com.tibco.be.model.rdf.primitives;

import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Aug 23, 2005
 * Time: 12:54:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class RDFBaseEntityTerm extends RDFBaseTerm {
    /**
     * @param runtimeTerm - The class that implements the runtime Type. Example is com.tibco.be.engine.model.rdf.PropertyAtomXXX
     * @param isReference - Does this Term represent a reference Type.
     */
    public RDFBaseEntityTerm(String runtimeTerm, boolean isReference) {
        super(runtimeTerm, isReference);
    }

    public String getName() {
        return "Entity";
    }

    public SmType getXSDLTerm() {  
        return XSDL.ANY_TYPE;
    }
}
