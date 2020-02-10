/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.providers.impl;

import java.util.Map;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;
import com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType;
import com.tibco.be.baas.security.authn.saml.protocol.ISAMLObjectComposite;
import com.tibco.be.baas.security.authn.saml.protocol.SAMLObjectCompositeFactory;
import com.tibco.be.baas.security.authn.saml.providers.SAMLIdpPartner;

/**
 *
 * @author Aditya Athalye
 * Date : 22 Oct, 2011
 */
public class DefaultSAMLIdpPartnerImpl<S extends ISAMLObject, B extends ISAMLObjectComposite> extends DefaultAbstractSAMLPartner implements SAMLIdpPartner {
    
    /**
     * To be called from catalog function.
     * @param issuerURI
     * @param partnerName 
     */
    public DefaultSAMLIdpPartnerImpl(String issuerURI, String partnerName) {
        super(issuerURI, partnerName);
    }

    @Override
    public ResponseType processAuthnAssertionQuery(Map<String, Object> responseAttributes) throws Exception {
        //TODO add any validations that may be required.
        B responseType = SAMLObjectCompositeFactory.<S, B>getSAMLObjectComposite(ResponseType.class);
        return (ResponseType)responseType;
    }
}
