/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.cep.security.authen.sso;

import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.UTF8_ENCODING;

import java.io.IOException;
import java.net.URL;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.tibco.net.mime.Base64Codec;

/**
 * Same as the previous implementation.
 * @author Aditya Athalye
 * Date : 17 Oct, 2011
 */
public class SSOAuthCallbackHandler implements CallbackHandler {

    private String username;
    
    private String encodedPassword;
    
    private String tokenIssuerUrl;

    public SSOAuthCallbackHandler(String tokenIssuerUrl, String username, String encodedPassword) {
        this.tokenIssuerUrl = tokenIssuerUrl;
        this.username = username;
        this.encodedPassword = encodedPassword;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof SSOAuthCallback) {
                SSOAuthCallback ssoAuthCallback = (SSOAuthCallback) callback;
                String decoded = extractCredentials();
                ssoAuthCallback.setUsername(username);
                ssoAuthCallback.setPassword(decoded);
                URL issuerUrl = new URL(tokenIssuerUrl);
                ssoAuthCallback.setIssuerUrl(issuerUrl);
            } else {
                //For the time-being
                throw new UnsupportedCallbackException(callback, "Callback not supported");
            }
        }
    }
    
    private String extractCredentials() throws IOException {
        return Base64Codec.decodeBase64(encodedPassword, UTF8_ENCODING);
    }
}
