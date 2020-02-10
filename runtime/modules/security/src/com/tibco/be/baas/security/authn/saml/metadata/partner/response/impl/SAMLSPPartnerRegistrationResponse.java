/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.partner.response.impl;

import java.util.Map;

import com.tibco.be.baas.security.authn.saml.metadata.partner.response.ISAMLPartnerRegistrationServiceResponse;
import com.tibco.be.baas.security.authn.saml.metadata.partner.spi.PartnerRegistrationStatus;

/**
 *
 * @author Aditya Athalye
 * Date : 3 Oct, 2011
 */
public class SAMLSPPartnerRegistrationResponse implements ISAMLPartnerRegistrationServiceResponse {
    
    private Map<String, String> responseHeaders;
    
    private PartnerRegistrationStatus registrationStatus;

    @Override
    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    @Override
    public PartnerRegistrationStatus getResponseObject() {
        return registrationStatus;
    }

    public SAMLSPPartnerRegistrationResponse(PartnerRegistrationStatus registrationStatus, Map<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
        this.registrationStatus = registrationStatus;
    }
}
