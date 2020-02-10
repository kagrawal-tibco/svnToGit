/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.policy.request.impl;

import org.apache.http.HttpEntity;

import com.tibco.be.baas.security.authn.saml.metadata.policy.request.IPolicyServiceRequestBuilder;
import com.tibco.be.baas.security.authn.saml.metadata.policy.request.IPolicyServiceRequestBuilderInput;
import com.tibco.be.baas.security.authn.saml.metadata.policy.response.PolicyServiceOperationType;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType;

/**
 *
 * @author Aditya Athalye
 * Date : 29 Sep, 2011
 */
public class PolicyServiceRequestBuilderImpl implements IPolicyServiceRequestBuilder {

    @Override
    public <I, H extends HttpEntity> H buildRequest(I input, PolicyServiceOperationType policyServiceOperationType) throws Exception {
        IPolicyServiceRequestBuilderInput policyServiceRequestBuilderInput = null;
        switch (policyServiceOperationType) {
            case ADD_NEW_AUTHN_POLICY :
                //TODO Bad generics used here. Can it be improved?
                policyServiceRequestBuilderInput = new PolicyCreationRequestBuilderInput((PolicyTemplateType)input);
                break;
        }
        if (policyServiceRequestBuilderInput != null) {
            //This is to address unique maximal instance exists for type variable bug in javac.
            return policyServiceRequestBuilderInput.<H>buildRequest();
        }
        return null;
    }
}
