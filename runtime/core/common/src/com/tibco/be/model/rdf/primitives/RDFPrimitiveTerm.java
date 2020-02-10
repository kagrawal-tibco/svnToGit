package com.tibco.be.model.rdf.primitives;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Aug 5, 2004
 * Time: 6:08:52 PM
 * To change this template use Options | File Templates.
 */
public abstract class RDFPrimitiveTerm extends RDFBaseTerm {

    /**
     * @param runtimeTerm - The class that implements the runtime Type. Example is com.tibco.be.engine.model.rdf.PropertyAtomXXX
     * @param isReference - Does this Term represent a reference Type.
     */
    public RDFPrimitiveTerm(String runtimeTerm, boolean isReference) {
        this(runtimeTerm, isReference, -1);
    }
    protected RDFPrimitiveTerm(String runtimeTerm, boolean isReference, int typeId) {
        super(runtimeTerm, isReference);
        this.typeId = typeId;
    }

    //STRING_TYPEID, INT_TYPEID, etc in RDFTypes.    
    final protected int typeId;
    
    public int getTypeId() {
        return typeId;
    }
}
