/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.policy.response.impl;

import java.util.Map;

import com.tibco.be.baas.security.authn.saml.metadata.policy.response.IPolicyServiceResponse;

/**
 *
 * @author Aditya Athalye
 * Date : 29 Sep, 2011
 */
public class AuthnPolicyCreationResponse implements IPolicyServiceResponse {
    
    private Map<String, String> responseHeaders;
    
    private boolean creationStatus;

    @Override
    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    @Override
    public Boolean getResponseObject() {
        return creationStatus;
    }

    public AuthnPolicyCreationResponse(boolean creationStatus, Map<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
        this.creationStatus = creationStatus;
    }
}
