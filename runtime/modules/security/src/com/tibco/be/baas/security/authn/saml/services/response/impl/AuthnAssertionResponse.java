/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.services.response.impl;

import java.util.Map;

import com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType;
import com.tibco.be.baas.security.authn.saml.services.response.ISAMLProtocolResponse;

/**
 *
 * @author Aditya Athalye
 * Date : 21 Oct, 2011
 */
public class AuthnAssertionResponse implements ISAMLProtocolResponse {
    
    /**
     * The encoded saml response containing the assertion.
     */
    private ResponseType responseType;
    
    private Map<String, String> responseHeaders;

    @Override
    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    @Override
    public ResponseType getResponseObject() {
        return responseType;
    }

    public AuthnAssertionResponse(ResponseType responseType, Map<String, String> responseHeaders) {
        this.responseType = responseType;
        this.responseHeaders = responseHeaders;
    }
}
