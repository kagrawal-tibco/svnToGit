/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.partner.spi;

import com.tibco.be.baas.security.authn.saml.metadata.common.IMetadataServiceType;

/**
 *
 * @author aditya
 */
public enum PartnerRegistrationOperationType implements IMetadataServiceType {
    
    REGSITER_SP_PARTNER,
    
    REGISTER_IDP_PARTNER,
    
    FETCH_REGISTERED_SP_PARTNERS,
    
    FETCH_REGISTERED_IDP_PARTNERS;
}
