/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.services.response;

import java.util.Map;

/**
 *
 * @author Aditya Athalye
 * Date : 21 Oct, 2011
 */
public interface ISAMLProtocolResponse {
    
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
