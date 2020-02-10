/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.common;

import java.util.Map;

/**
 * Gene
 * @author aditya
 * Date : 1 Oct, 2011
 */
public interface IMetadataServiceResponse {
    
    /**
     * Get the response object held by the impl.
     * @return 
     */
    public <T> T getResponseObject();
    
    /**
     * Get HTTP headers sent as response.
     * @return 
     */
    public Map<String, String> getResponseHeaders();
}
