/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.policy.response;

import com.tibco.be.baas.security.authn.saml.metadata.common.IMetadataServiceResponseFactory;
import com.tibco.be.baas.security.authn.saml.metadata.policy.response.impl.AuthnPolicyCreationResponseProcessor;
import com.tibco.be.baas.security.authn.saml.metadata.policy.response.impl.PolicyTemplatesResponseProcessor;

/**
 *
 * @author Aditya Athalye
 * Date : 28 Sep, 2011
 */
public class PolicyServiceResponseFactory implements IMetadataServiceResponseFactory<IPolicyServiceResponseProcessor, PolicyServiceOperationType> {
   
    /**
     * 
     * @param policyServiceResponseType
     * @return
     * @throws Exception 
     */
    @Override
    public IPolicyServiceResponseProcessor getServiceResponseProcessor(PolicyServiceOperationType policyServiceResponseType) throws Exception { 
        switch (policyServiceResponseType) {
            case LOAD_TEMPLATES :
                return new PolicyTemplatesResponseProcessor();
            case ADD_NEW_AUTHN_POLICY :
                return new AuthnPolicyCreationResponseProcessor();
        }
        return null;
    }
}
