/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.policy.response.impl;

import java.util.Map;
import java.util.Set;

import com.tibco.be.baas.security.authn.saml.metadata.policy.response.IPolicyServiceResponse;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType;

/**
 *
 * @author Aditya Athalye
 * Date : 4 Oct, 2011
 */
public class AuthnPoliciesResponse implements IPolicyServiceResponse {
    
    private Set<PolicyTemplateType> authnPolicies;
    
    private Map<String, String> responseHeaders;

    @Override
    public Set<PolicyTemplateType> getResponseObject() {
        return authnPolicies;
    }

    public AuthnPoliciesResponse(Set<PolicyTemplateType> authnPolicies, Map<String, String> responseHeaders) {
        this.authnPolicies = authnPolicies;
        this.responseHeaders = responseHeaders;
    }

    @Override
    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }
}
