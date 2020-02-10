package com.tibco.be.model.rdf.primitives;

public class RDFBaseSOAPEventTerm extends RDFBaseEventTerm {

    /**
     * @param runtimeTerm - The class that implements the runtime Type. Example is com.tibco.be.engine.model.rdf.PropertyAtomXXX
     * @param isReference - Does this Term represent a reference Type.
     */
	public RDFBaseSOAPEventTerm(String runtimeTerm, boolean isReference) {
		super(runtimeTerm, isReference);
	}

    public String getName() {
        return "SOAPEvent";
    }
}
