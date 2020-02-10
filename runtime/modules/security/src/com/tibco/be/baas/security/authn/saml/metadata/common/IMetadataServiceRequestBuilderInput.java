/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.common;

import org.apache.http.HttpEntity;

/**
 * All MDS service request builder inputs should implement
 * this interface for posting data to BAAS MDS channels.
 * @author aditya
 * Date : 2 Oct, 2011
 */
public interface IMetadataServiceRequestBuilderInput {
    
    /**
     * 
     * @param <H>
     * @return
     * @throws Exception 
     */
    public <H extends HttpEntity> H buildRequest() throws Exception;
    
}
