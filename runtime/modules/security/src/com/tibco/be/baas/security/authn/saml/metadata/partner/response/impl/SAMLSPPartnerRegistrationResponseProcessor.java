/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.partner.response.impl;

import java.util.Map;

import com.tibco.be.baas.security.authn.saml.metadata.partner.response.ISAMLPartnerRegistrationServiceResponseProcessor;
import com.tibco.be.baas.security.authn.saml.metadata.partner.spi.PartnerRegistrationStatus;

/**
 *
 * @author Aditya Athalye
 * Date : 3 Oct, 2011
 */
public class SAMLSPPartnerRegistrationResponseProcessor implements ISAMLPartnerRegistrationServiceResponseProcessor<SAMLSPPartnerRegistrationResponse> {

    @Override
    public SAMLSPPartnerRegistrationResponse processResponse(byte[] xmlStringBytes, Map<String, String> responseHeaders) throws Exception {
        //We do not expect content.
        //Ignore the first param
        int spRegistrationStatusCode = Integer.parseInt(responseHeaders.get("statusCode"));
        PartnerRegistrationStatus partnerRegistrationStatus = new PartnerRegistrationStatus(spRegistrationStatusCode);
        return new SAMLSPPartnerRegistrationResponse(partnerRegistrationStatus, responseHeaders);
    }
}