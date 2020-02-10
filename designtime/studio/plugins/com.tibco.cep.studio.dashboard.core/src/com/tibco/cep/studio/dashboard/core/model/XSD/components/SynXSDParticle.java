package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDParticle;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDParticleContent;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDTerm;

/**
 * @
 *  
 */
public class SynXSDParticle implements ISynXSDParticle {

    
    private int minOccurs = 1;

    private int maxOccurs = -1;
    
    private ISynXSDTerm term;
    
    public SynXSDParticle() {
        
    }
    public SynXSDParticle(ISynXSDTerm term,int minOccurs,int maxOccurs) {
        
        if(null == term) {
            throw new IllegalArgumentException("term can not be null");
        }
        if (minOccurs < 0 ) {
            throw new IllegalArgumentException("minOccurs must be >= 0");
        }
        else if(minOccurs> maxOccurs) {
            throw new IllegalArgumentException("minOccurs can not be greater than maxOccurs");
        }
        else {
            this.minOccurs = minOccurs;
        }

        if (maxOccurs < -1) {
            throw new IllegalArgumentException("maxOccurs must be >= -1 (-1 meand unbounded)");
        }
        else if (maxOccurs < minOccurs) {
            throw new IllegalArgumentException("maxOccurs can not be less than minOccurs");
        }
        else {
            this.maxOccurs = maxOccurs;
        }
        
    }
    
    
    /**
     * Content can be a Model Group, Model Group Definition, 
     */
    private ISynXSDParticleContent content =null;

    /**
     * @return Returns the minOccurs.
     */
    public int getMinOccurs() {
        return minOccurs;
    }

    /**
     * @param minOccurs
     *            The minOccurs to set.
     */
    public void setMinOccurs(int minOccurs) {
        if (minOccurs < 0) {
            throw new IllegalArgumentException("minOccurs must be a non-negative number");
        }
        this.minOccurs = minOccurs;
    }

    /**
     * @return Returns the maxOccurs.
     */
    public int getMaxOccurs() {
        return maxOccurs;
    }

    /**
     * @param maxOccurs
     *            The maxOccurs to set.
     */
    public void setMaxOccurs(int maxOccurs) {
        if (maxOccurs < -1) {
            throw new IllegalArgumentException("maxOccurs must be a non-negative number, or -1 for unbounded");
        }

        this.maxOccurs = maxOccurs;
    }
    /**
     * @return Returns the content.
     */
    public ISynXSDParticleContent getContent() {
        return content;
    }
    
    /**
     * @param content The content to set.
     */
    public void setContent(ISynXSDParticleContent content) {
        this.content = content;
    }
    
    
    public Object clone() throws CloneNotSupportedException {
        SynXSDParticle pClone = new SynXSDParticle(getTerm(),getMinOccurs(),getMaxOccurs());
        return pClone;
    }
    
    
    /**
     * @return Returns the term.
     */
    public ISynXSDTerm getTerm() {
        return term;
    }
    /**
     * @param term The term to set.
     */
    public void setTerm(ISynXSDTerm term) {
        this.term = term;
    }
}
