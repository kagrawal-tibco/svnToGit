/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.services.response;

import java.util.Map;

/**
 *
 * @author aditya
 * Date : 21 Oct, 2011
 */
public interface ISAMLProtocolResponseProcessor<S extends ISAMLProtocolResponse> {
    
    /**
     * 
     * @param xmlStringBytes
     * @param responseHeaders
     * @return
     * @throws Exception 
     */
    public S processResponse(byte[] xmlStringBytes, Map<String, String> responseHeaders) throws Exception;
}
