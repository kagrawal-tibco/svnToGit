/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.services.response.impl;

import java.util.Map;

import com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType;
import com.tibco.be.baas.security.authn.saml.services.response.ISAMLProtocolResponseProcessor;
import com.tibco.be.baas.security.authn.saml.utils.SAMLModelSerializationUtils;
import com.tibco.net.mime.Base64Codec;

/**
 *
 * @author Aditya Athalye
 * Date : 21 Oct, 2011
 */
public class AuthnAssertionResponseProcessor implements ISAMLProtocolResponseProcessor<AuthnAssertionResponse> {

    @Override
    public AuthnAssertionResponse processResponse(byte[] xmlStringBytes, Map<String, String> responseHeaders) throws Exception {
        //Get SAMLResponse header
        String encodedSAMLResponse = responseHeaders.get("SAMLResponse");
        //Decode base 64
        byte[] decodedSAMLResponseBytes = Base64Codec.decodeBase64(encodedSAMLResponse);
        ResponseType samlResponse = (ResponseType)SAMLModelSerializationUtils.unmarshallEObject(decodedSAMLResponseBytes, null);
        return new AuthnAssertionResponse(samlResponse, responseHeaders);
    }
}
