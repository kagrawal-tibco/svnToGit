/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.protocol;

import java.util.ArrayList;
import java.util.List;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;

/**
 * Abstract class for Recursive composition pattern.
 * @author Aditya Athalye
 * Date : 19 Oct, 2011
 */
public abstract class AbstractSAMLObjectComposite<S extends ISAMLObject> implements ISAMLObjectComposite {
    
    protected List<ISAMLObjectComposite> childComposites = new ArrayList<ISAMLObjectComposite>();
    
    /**
     * 
     * @param composite 
     */
    public void addSAMLObjectComposite(ISAMLObjectComposite composite) {
        childComposites.add(composite);
    }
}
