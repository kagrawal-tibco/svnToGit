/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.partner.request.impl;

import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.UTF8_ENCODING;

import java.io.StringWriter;

import org.apache.http.entity.StringEntity;

import com.tibco.be.baas.security.authn.saml.metadata.partner.request.ISAMLPartnerRegistrationRequestBuilderInput;
import com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType;
import com.tibco.be.baas.security.authn.saml.utils.SAMLModelSerializationUtils;

/**
 * Builder input for SAML SP partner registration.
 * @author Aditya Athalye
 * Date : 2 Oct, 2011
 */
public class SPPartnerRegistrationRequestBuilderInput implements ISAMLPartnerRegistrationRequestBuilderInput {
    
    private EntityDescriptorType spEntityDescriptor;
    
    /**
     * 
     * @param spEntityDescriptorType 
     */
    public SPPartnerRegistrationRequestBuilderInput(EntityDescriptorType spEntityDescriptor) {
        this.spEntityDescriptor = spEntityDescriptor;
    }
    
    
    @Override
    public StringEntity buildRequest() throws Exception {
        //serialize it
        StringWriter stringWriter = new StringWriter();
        SAMLModelSerializationUtils.marshallEObject(stringWriter, spEntityDescriptor, null);
        String serializedTemplate = stringWriter.toString();
        return new StringEntity(serializedTemplate, UTF8_ENCODING);
    }
}
