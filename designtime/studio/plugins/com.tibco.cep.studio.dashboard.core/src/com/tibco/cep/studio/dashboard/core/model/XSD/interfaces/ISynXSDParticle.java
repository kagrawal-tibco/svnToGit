package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;


/**
 * @
 *  
 */
public interface ISynXSDParticle {


    

    /**
     * @return Returns the minOccurs.
     */
    public abstract int getMinOccurs();
    

    /**
     * Sets the minimum occurence required
     * @param min
     * @return
     */
    public abstract void setMinOccurs(int min);

    /**
     * @return Returns the maxOccurs.
     */
    public abstract int getMaxOccurs();
    
    /**
     * Sets the maximum occurence allowed
     * @param min
     * @return
     */
    public abstract void setMaxOccurs(int max);
    
    
    public abstract ISynXSDTerm getTerm();

}