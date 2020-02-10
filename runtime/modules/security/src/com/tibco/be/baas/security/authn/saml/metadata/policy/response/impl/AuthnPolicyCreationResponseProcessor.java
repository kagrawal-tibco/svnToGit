/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.policy.response.impl;

import java.util.Map;

import com.tibco.be.baas.security.authn.saml.metadata.policy.response.IPolicyServiceResponseProcessor;

/**
 *
 * @author Aditya Athalye
 * Date : 29 Sep, 2011
 */
public class AuthnPolicyCreationResponseProcessor implements IPolicyServiceResponseProcessor<AuthnPolicyCreationResponse> {
        
    @Override
    public AuthnPolicyCreationResponse processResponse(byte[] xmlStringBytes, Map<String, String> responseHeaders) throws Exception {
        //We do not expect content.
        //Ignore the first param
        boolean policyCreationStatus = Boolean.parseBoolean(responseHeaders.get("status"));
        return new AuthnPolicyCreationResponse(policyCreationStatus, responseHeaders);
    }
}
