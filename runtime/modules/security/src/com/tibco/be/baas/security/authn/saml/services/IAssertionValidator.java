/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.services;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType;

/**
 * To be implemented by all SPs requiring validation
 * of assertions for TTL etc.
 * @author aditya
 * Date : 17 Oct, 2011
 */
public interface IAssertionValidator {
    
    /**
     * 
     * @param assertion
     * @return 
     */
    public boolean validateAssertion(AssertionType assertion);
}
