/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.policy.response;

import com.tibco.be.baas.security.authn.saml.metadata.common.IMetadataServiceType;

/**
 *
 * @author aditya
 */
public enum PolicyServiceOperationType implements IMetadataServiceType {
    
    LOAD_TEMPLATES,
    
    ADD_NEW_AUTHN_POLICY,
    
    REMOVE_AUTHN_POLICY_TEMPLATE;
}
