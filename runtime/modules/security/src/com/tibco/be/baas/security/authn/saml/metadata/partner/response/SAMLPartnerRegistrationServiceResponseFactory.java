/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.partner.response;

import com.tibco.be.baas.security.authn.saml.metadata.common.IMetadataServiceResponseFactory;
import com.tibco.be.baas.security.authn.saml.metadata.partner.response.impl.SAMLSPPartnerRegistrationResponseProcessor;
import com.tibco.be.baas.security.authn.saml.metadata.partner.spi.PartnerRegistrationOperationType;

/**
 *
 * @author Aditya Athalye
 * Date : 3 Oct, 2011
 */
public class SAMLPartnerRegistrationServiceResponseFactory implements IMetadataServiceResponseFactory<ISAMLPartnerRegistrationServiceResponseProcessor, PartnerRegistrationOperationType> {

    @Override
    public ISAMLPartnerRegistrationServiceResponseProcessor getServiceResponseProcessor(PartnerRegistrationOperationType partnerRegistrationOperationType) throws Exception {
        switch (partnerRegistrationOperationType) {
            case REGSITER_SP_PARTNER :
                return new SAMLSPPartnerRegistrationResponseProcessor();
            //TODO implement other ops    
        }
        return null;
    }
}
