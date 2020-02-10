/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.services;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType;

/**
 *
 * @author Aditya Athalye
 * Date : 17 Oct, 2011
 * To be implemented by SAML service providers wishing
 * to consume SAML assertions sent by Identity Provider. (BAAS).
 */
public interface IAssertionConsumerService {
    
    /**
     * Get the IdP sent SAML {@link AssertionType} after validating it
     * using the provided validators.
     * @param assertionString -> Clear text assertion string.
     * @param assertionValidators
     * @return the actual assertion.
     */
    public AssertionType processAssertion(String assertionString) throws AssertionVerificationException;
    
    /**
     * Add a validator to the list
     * @param assertionValidator
     * @return 
     */
    public boolean addAssertionValidator(IAssertionValidator assertionValidator);
    
    /**
     * Remove validator from the list.
     * @param assertionValidator
     * @return 
     */
    public boolean removeAssertionValidator(IAssertionValidator assertionValidator);
}
