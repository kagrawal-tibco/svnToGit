package com.tibco.be.model.rdf.primitives;

/**
 * @author ishaan
 * @version Feb 1, 2005, 2:34:37 PM
 */
public abstract class RDFBaseTerm implements RDFUberType {
    protected String runtimeTerm;
    protected boolean isReference;

    /**
     *
     * @param runtimeTerm - The class that implements the runtime Type. Example is com.tibco.be.engine.model.rdf.PropertyAtomXXX
     *
     * @param isReference - Does this Term represent a reference Type.
     */

    public RDFBaseTerm(String runtimeTerm, boolean isReference) {
        this.runtimeTerm = runtimeTerm;
        this.isReference = isReference;
    }

    public String getRuntimeTerm() {
        return runtimeTerm;
    }

    public boolean isA(RDFUberType anotherType) {
        return false;
    }

    public boolean isReference() {
        return isReference;
    }

    public String toString() {
        return getName();
    }	
	
	public boolean equals(Object obj) {
		if (obj instanceof RDFBaseTerm) {
			RDFBaseTerm rdfType = (RDFBaseTerm) obj;
			if(runtimeTerm.equals(rdfType.getRuntimeTerm()) && isReference == rdfType.isReference()){
				return true;
			} else {
				return false;
			}
				
		} else
			return super.equals(obj);
	}
    
    
}
