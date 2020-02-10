/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.services.impl;

import java.util.List;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType;
import com.tibco.be.baas.security.authn.saml.services.AssertionVerificationException;
import com.tibco.be.baas.security.authn.saml.services.IAssertionConsumerService;
import com.tibco.be.baas.security.authn.saml.services.IAssertionValidator;
import com.tibco.be.baas.security.authn.saml.utils.SAMLModelSerializationUtils;

/**
 *
 * @author Aditya Athalye
 * Date : 17 Oct, 2011
 */
public class DefaultAssertionConsumerService implements IAssertionConsumerService {
    
    protected List<IAssertionValidator> assertionValidators;
    
    @Override
    public boolean addAssertionValidator(IAssertionValidator assertionValidator) {
        return assertionValidators.add(assertionValidator);
    }
    
    @Override
    public AssertionType processAssertion(String assertionString) throws AssertionVerificationException {
        try {
            //Get the assertion for this
            AssertionType assertionType = (AssertionType)SAMLModelSerializationUtils.unmarshallEObject(assertionString, null);
            //Run validators on asertion
            for (IAssertionValidator assertionValidator : assertionValidators) {
                assertionValidator.validateAssertion(assertionType);
            }
            return assertionType;
        } catch (Exception ex) {
            throw new AssertionVerificationException(ex);
        }
    }

    @Override
    public boolean removeAssertionValidator(IAssertionValidator assertionValidator) {
        return assertionValidators.remove(assertionValidator);
    }
}
