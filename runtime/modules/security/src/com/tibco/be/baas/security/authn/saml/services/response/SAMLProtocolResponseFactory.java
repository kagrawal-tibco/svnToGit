/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.services.response;

import java.lang.reflect.Constructor;

import com.tibco.be.baas.security.authn.saml.services.SAMLProtocolType;
import com.tibco.be.baas.security.authn.saml.services.response.impl.AuthnAssertionResponseProcessor;

/**
 *
 * @author Aditya Athalye
 * Date : 21 Oct, 2011
 */
public class SAMLProtocolResponseFactory {
    
    public static SAMLProtocolResponseFactory INSTANCE = new SAMLProtocolResponseFactory();
    
    private SAMLProtocolResponseFactory() {}
    
    public <R extends ISAMLProtocolResponse, S extends ISAMLProtocolResponseProcessor<R>> S getProtocolResponseProcessor(SAMLProtocolType samlProtocolType) throws Exception {
        switch (samlProtocolType) {
            case AUTHN_ASSN_REQUEST_TYPE :
                Constructor<AuthnAssertionResponseProcessor> constructor = AuthnAssertionResponseProcessor.class.getConstructor();
                return (S)constructor.newInstance();
            default: 
                throw new UnsupportedOperationException("Not implemented");
        }
    }
}
