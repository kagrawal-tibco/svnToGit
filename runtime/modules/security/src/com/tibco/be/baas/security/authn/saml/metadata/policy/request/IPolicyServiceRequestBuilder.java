/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.policy.request;

import com.tibco.be.baas.security.authn.saml.metadata.common.IMetadataServiceRequestBuilder;
import com.tibco.be.baas.security.authn.saml.metadata.policy.response.PolicyServiceOperationType;
import com.tibco.be.baas.security.authn.saml.metadata.policy.spi.PolicyStoreService;

/**
 *
 * @author aditya
 * Date : 29 Sep, 2011
 * <p>
 * Marker interface To be used for building post request data for policy store service.
 * @see PolicyStoreService
 * </p>
 */
public interface IPolicyServiceRequestBuilder extends IMetadataServiceRequestBuilder<PolicyServiceOperationType> {
    
}
