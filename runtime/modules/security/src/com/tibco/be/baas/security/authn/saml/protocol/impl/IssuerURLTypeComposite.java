/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.protocol.impl;

import java.util.Map;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionFactory;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;

/**
 *
 * @author Aditya Athalye
 * Date : 19 Oct, 2011
 */
public class IssuerURLTypeComposite extends NameIdTypeComposite {

    @Override
    public NameIDType composeSAMLObject(Map<String, Object> objectAttribues) {
        NameIDType issuerURLType = AssertionFactory.eINSTANCE.createNameIDType();
        String issuerUrl = (String)objectAttribues.get("ISSUER_URL");
        issuerURLType.setValue(issuerUrl);
        return issuerURLType;
    }
}
