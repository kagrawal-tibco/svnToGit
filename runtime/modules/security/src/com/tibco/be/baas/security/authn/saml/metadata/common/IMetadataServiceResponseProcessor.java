/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.common;

import java.util.Map;

/**
 * All response processors for MDS services should implement this interface.
 * @author aditya
 * Date : 2 Oct, 2011
 */
public interface IMetadataServiceResponseProcessor<S extends IMetadataServiceResponse> {
    
    /**
     * 
     * @param xmlStringBytes
     * @param responseHeaders
     * @return
     * @throws Exception 
     */
    public S processResponse(byte[] xmlStringBytes, Map<String, String> responseHeaders) throws Exception;
}
