/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.protocol.impl;

import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionFactory;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType;
import com.tibco.be.baas.security.authn.saml.protocol.AbstractSAMLObjectComposite;
import com.tibco.be.baas.security.authn.saml.protocol.ISAMLObjectComposite;

/**
 *
 * @author Aditya Athalye
 * Date : 19 Oct, 2011
 */
public class AuthnStatementTypeComposite extends AbstractSAMLObjectComposite<AuthnStatementType> {

    public AuthnStatementTypeComposite() {
        addSAMLObjectComposite(new AuthnContextTypeComposite());
    }
    
    
    @Override
    public AuthnStatementType composeSAMLObject(Map<String, Object> objectAttribues) throws Exception {
        AuthnStatementType authnStatementType = AssertionFactory.eINSTANCE.createAuthnStatementType();
        //Set attrs
        GregorianCalendar authInstant = (GregorianCalendar)objectAttribues.get("AUTHN_INSTANT");
        authnStatementType.setAuthnInstant(DatatypeFactory.newInstance().newXMLGregorianCalendar(authInstant));
        for (ISAMLObjectComposite childComposite : childComposites) {
            ISAMLObject sAMLObject = childComposite.composeSAMLObject(objectAttribues);
            if (sAMLObject instanceof AuthnContextType) {
                authnStatementType.setAuthnContext((AuthnContextType)sAMLObject);
            } 
        }
        return authnStatementType;
    }
}
