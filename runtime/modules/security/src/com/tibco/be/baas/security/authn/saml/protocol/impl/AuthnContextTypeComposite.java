/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.protocol.impl;

import static com.tibco.be.baas.security.authn.saml.protocol.SAMLObjectCompositeUtils.PASSWORD_PROTECTED_TRANSPORT;

import java.util.Map;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionFactory;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType;
import com.tibco.be.baas.security.authn.saml.protocol.AbstractSAMLObjectComposite;

/**
 *
 * @author Aditya Athalye
 * Date : 20 Oct, 2011
 */
public class AuthnContextTypeComposite extends AbstractSAMLObjectComposite<AuthnContextType> {

    @Override
    public AuthnContextType composeSAMLObject(Map<String, Object> objectAttribues) throws Exception {
        AuthnContextType authnContextType = AssertionFactory.eINSTANCE.createAuthnContextType();
        //Set context class ref
        authnContextType.setAuthnContextClassRef(PASSWORD_PROTECTED_TRANSPORT);
        return authnContextType;
    }
}
