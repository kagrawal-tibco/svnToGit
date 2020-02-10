/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.services.impl;

import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType;
import com.tibco.be.baas.security.authn.saml.protocol.SAMLObjectCompositeFactory;
import com.tibco.be.baas.security.authn.saml.protocol.SAMLRequest;
import com.tibco.be.baas.security.authn.saml.protocol.impl.AuthnRequestTypeComposite;
import com.tibco.be.baas.security.authn.saml.services.IIdpAccessService;
import com.tibco.be.baas.security.authn.saml.services.SAMLProtocolType;
import com.tibco.be.baas.security.authn.saml.services.response.ISAMLProtocolResponse;
import com.tibco.be.baas.security.authn.saml.services.response.ISAMLProtocolResponseProcessor;
import com.tibco.be.baas.security.authn.saml.services.response.impl.AuthnAssertionResponse;
import com.tibco.be.baas.security.authn.saml.transport.SAMLProtocolClient;
import com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants;

/**
 *
 * @author Aditya Athalye
 * Date : 20 Oct, 2011
 */
public class DefaultIdpAccessService<H extends HttpEntity, R extends ISAMLProtocolResponse, S extends ISAMLProtocolResponseProcessor<R>> implements IIdpAccessService {

    @Override
    public AuthnAssertionResponse sendAuthnAssertionRequest(String idpServiceURL, Map<String, Object> requestAttributes) throws Exception {
        AuthnRequestTypeComposite authnRequestTypeComposite = SAMLObjectCompositeFactory.getSAMLObjectComposite(AuthnRequestType.class);
        //Build the composite
        AuthnRequestType authnRequestType = authnRequestTypeComposite.composeSAMLObject(requestAttributes);
        SAMLRequest<AuthnRequestType> samlAuthnRequest = new SAMLRequest<AuthnRequestType>(authnRequestType);
        String serializedEncodedAuthnRequest = samlAuthnRequest.toString();
        
        H stringEntity = (H)new StringEntity(serializedEncodedAuthnRequest, GenericSecurityConstants.UTF8_ENCODING);
        //Send encoded saml request as post data inplace of url parameter.
        R assertionResponse = SAMLProtocolClient.<H, R, S>sendPostRequest(idpServiceURL, null, stringEntity, SAMLProtocolType.AUTHN_ASSN_REQUEST_TYPE);
        return (AuthnAssertionResponse)assertionResponse;
    }

    @Override
    public void sendHTTPPostRequest(String idpServiceURL) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}