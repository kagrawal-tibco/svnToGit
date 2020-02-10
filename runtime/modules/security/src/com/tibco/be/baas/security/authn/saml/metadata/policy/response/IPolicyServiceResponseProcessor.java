/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.policy.response;

import com.tibco.be.baas.security.authn.saml.metadata.common.IMetadataServiceResponseProcessor;

/**
 * Marker interface for policy store service response processor.
 * @author aditya
 */
public interface IPolicyServiceResponseProcessor<R extends IPolicyServiceResponse> extends IMetadataServiceResponseProcessor<R> {
    
}
