/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.common;

import org.apache.http.HttpEntity;

/**
 *
 * @author aditya
 * Date : 2 Oct, 2011
 */
public interface IMetadataServiceRequestBuilder<M extends Enum<M> & IMetadataServiceType> {
    
    /**
     * 
     * @param <I>
     * @param <H>
     * @param input
     * @param metadatServiceType
     * @return
     * @throws Exception 
     */
    public <I, H extends HttpEntity> H buildRequest(I input, M metadatServiceType) throws Exception;
}
