/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.partner.request.impl;

import org.apache.http.HttpEntity;

import com.tibco.be.baas.security.authn.saml.metadata.partner.request.ISAMLPartnerRegistrationRequestBuilder;
import com.tibco.be.baas.security.authn.saml.metadata.partner.request.ISAMLPartnerRegistrationRequestBuilderInput;
import com.tibco.be.baas.security.authn.saml.metadata.partner.spi.PartnerRegistrationOperationType;
import com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType;

/**
 *
 * @author Aditya Athalye
 * Date : 3 Oct, 2011
 */
public class SAMLPartnerRegistrationRequestBuilderImpl implements ISAMLPartnerRegistrationRequestBuilder {

    @Override
    public <I, H extends HttpEntity> H buildRequest(I input, PartnerRegistrationOperationType metadatServiceType) throws Exception {
        ISAMLPartnerRegistrationRequestBuilderInput partnerRegistrationRequestBuilderInput = null;
        switch (metadatServiceType) {
            case REGSITER_SP_PARTNER :
                partnerRegistrationRequestBuilderInput = new SPPartnerRegistrationRequestBuilderInput((EntityDescriptorType)input);
                break;
        }
        if (partnerRegistrationRequestBuilderInput != null) {
            //This is to address unique maximal instance exists for type variable bug in javac.
            return partnerRegistrationRequestBuilderInput.<H>buildRequest();
        }
        return null;
    }
}
