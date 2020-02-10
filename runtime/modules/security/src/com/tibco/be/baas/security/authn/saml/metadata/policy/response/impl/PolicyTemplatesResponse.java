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
 * Date : 28 Sep, 2011
 */
public class PolicyTemplatesResponse implements IPolicyServiceResponse {
    
    private Set<PolicyTemplateType> policyTemplates;
    
    private Map<String, String> responseHeaders;

    @Override
    public Set<PolicyTemplateType> getResponseObject() {
        return policyTemplates;
    }

    public PolicyTemplatesResponse(Set<PolicyTemplateType> policyTemplates, Map<String, String> responseHeaders) {
        this.policyTemplates = policyTemplates;
        this.responseHeaders = responseHeaders;
    }

    @Override
    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }
}
