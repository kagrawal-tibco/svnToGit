package com.tibco.be.model.rdf.primitives;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Oct 16, 2006
 * Time: 9:21:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class RDFBaseAdvisoryEventTerm extends RDFBaseEventTerm {

    /**
     * @param runtimeTerm - The class that implements the runtime Type. Example is com.tibco.be.engine.model.rdf.PropertyAtomXXX
     * @param isReference - Does this Term represent a reference Type.
     */
    public RDFBaseAdvisoryEventTerm(String runtimeTerm, boolean isReference) {
        super(runtimeTerm, isReference);
    }

    public String getName() {
        return "AdvisoryEvent";
    }
}
