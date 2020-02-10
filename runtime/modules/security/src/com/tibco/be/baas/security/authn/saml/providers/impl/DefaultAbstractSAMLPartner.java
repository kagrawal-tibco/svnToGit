/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.providers.impl;

import com.tibco.be.baas.security.authn.saml.providers.SAMLPartner;

/**
 *
 * @author Aditya Athalye
 * Date : 22 Oct, 2011
 */
public abstract class DefaultAbstractSAMLPartner implements SAMLPartner {
    
    protected String issuerURI;
    
    protected String partnerName;
    
    protected String partnerDescription;
    
    public DefaultAbstractSAMLPartner(String issuerURI, String partnerName) {
        this(issuerURI, partnerName, null);
    }

    public DefaultAbstractSAMLPartner(String issuerURI, String partnerName, String partnerDescription) {
        this.issuerURI = issuerURI;
        this.partnerName = partnerName;
        this.partnerDescription = partnerDescription;
    }
    
 
    @Override
    public String[] getAudienceURIs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getIssuerURI() {
        return issuerURI;
    }

    @Override
    public String getName() {
        return partnerName;
    }

    @Override
    public String getPartnerDescription() {
        return partnerDescription;
    }
}
