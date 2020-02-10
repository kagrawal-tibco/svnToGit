/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.cep.security.authen.sso;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import com.tibco.be.baas.security.authn.saml.common.SSOToken;
import com.tibco.cep.security.authen.UserDataProviderFactory;
import com.tibco.cep.security.dataprovider.IUserDataProvider;
import com.tibco.cep.security.tokens.User;

/**
 *
 * @author Aditya Athalye
 * Date : 17 Oct, 2011
 */
public class SSOLoginModule implements LoginModule {
    
    protected Subject subject;
    
    protected CallbackHandler callbackHandler;
    
    protected SSOToken principal;
    
    private String username;
    
    private String password;
    
    private URL tokenIssuerUrl;
    
    private boolean authSucceeded;
    
    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean commit() throws LoginException {
        if (!authSucceeded) {
            return false;
        }
        principal = new SSOToken(username, tokenIssuerUrl, new Date());
        if (!subject.getPrincipals().contains(principal)) {
            subject.getPrincipals().add(principal);
        }
        username = null;
        password = null;
        return true;
    }

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public boolean login() throws LoginException {
        Callback[] callbacks = new Callback[1];
        SSOAuthCallback ssoAuthCallback = new SSOAuthCallback();
        callbacks[0] = ssoAuthCallback;
        try {
            callbackHandler.handle(callbacks);
            username = ssoAuthCallback.getUsername();
            password = ssoAuthCallback.getPassword();
            tokenIssuerUrl = ssoAuthCallback.getIssuerUrl();
            IUserDataProvider provider = UserDataProviderFactory.INSTANCE.getProvider();
            User authenticatedUser = provider.authenticate(username, password);
            if (authenticatedUser == null) {
                throw new LoginException("Authentication Failed");
            }
            return authSucceeded = (authenticatedUser != null);
        } catch (UnsupportedCallbackException e) {
            throw new LoginException(e.getMessage());
        } catch (IOException e) {
            throw new LoginException(e.getMessage());
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }
    }

    @Override
    public boolean logout() throws LoginException {
        return false;
    }
}