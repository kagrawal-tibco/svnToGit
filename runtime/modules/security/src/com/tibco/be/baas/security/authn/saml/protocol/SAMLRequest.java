/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.protocol;

import java.io.StringWriter;

import com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType;
import com.tibco.be.baas.security.authn.saml.utils.SAMLModelSerializationUtils;
import com.tibco.net.mime.Base64Codec;

/**
 * Used to wrap a SAML protocol request from SP to IdP
 * for HTTP Redirect binding.
 * <p>
 * Return base64 encoded form of the request content.
 * </p>
 * @author Aditya Athalye
 * Date : 17 Oct, 2011
 */
public class SAMLRequest<R extends RequestAbstractType> {
    
    private R requestAbstractType;

    public SAMLRequest(R requestAbstractType) {
        this.requestAbstractType = requestAbstractType;
    }

    @Override
    public String toString() {
        try {
            StringWriter stringWriter = new StringWriter();
            SAMLModelSerializationUtils.marshallEObject(stringWriter, requestAbstractType, null);
            //Encode using base64
            String clearString = stringWriter.toString();
            return Base64Codec.encodeBase64(clearString);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
