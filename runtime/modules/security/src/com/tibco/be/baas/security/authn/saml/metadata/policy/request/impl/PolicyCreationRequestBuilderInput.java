/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.policy.request.impl;

import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.UTF8_ENCODING;

import java.io.StringWriter;

import org.apache.http.entity.StringEntity;

import com.tibco.be.baas.security.authn.saml.metadata.policy.request.IPolicyServiceRequestBuilderInput;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType;
import com.tibco.be.baas.security.authn.saml.utils.SAMLModelSerializationUtils;

/**
 *
 * @author Aditya Athalye
 * Date : 29 Sep, 2011
 */
public class PolicyCreationRequestBuilderInput implements IPolicyServiceRequestBuilderInput {
    
    private PolicyTemplateType authnPolicy;

    public PolicyCreationRequestBuilderInput(PolicyTemplateType authnPolicy) {
        this.authnPolicy = authnPolicy;
    }
    
    //TODO possible build the jaxb object here too.
    @Override
    public StringEntity buildRequest() throws Exception {
        //serialize it
        StringWriter stringWriter = new StringWriter();
        SAMLModelSerializationUtils.marshallEObject(stringWriter, authnPolicy, null);
        String serializedTemplate = stringWriter.toString();
        return new StringEntity(serializedTemplate, UTF8_ENCODING);
    }
}
