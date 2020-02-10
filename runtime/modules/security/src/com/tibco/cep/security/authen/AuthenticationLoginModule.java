/**
 * 
 */
package com.tibco.cep.security.authen;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import com.tibco.cep.security.cep_securityVersion;
import com.tibco.cep.security.dataprovider.IUserDataProvider;
import com.tibco.cep.security.tokens.AuthToken;
import com.tibco.cep.security.tokens.Authen;
import com.tibco.cep.security.tokens.Authz;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.tokens.TokenFactory;
import com.tibco.cep.security.tokens.User;

/**
 * @author aathalye
 * 
 */
public class AuthenticationLoginModule implements LoginModule {

    private static final String AUTHENTICATED_BY = cep_securityVersion.getComponent() + cep_securityVersion.getVersion();
    protected Subject subject;
    protected CallbackHandler callbackHandler;
    private boolean succeeded;
    private AuthToken principal;
    private String username;
    private String password;
    private Iterator<Role> userRoles;

    private static final ReentrantLock LOGIN_LOCK = new ReentrantLock();

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public boolean login() throws LoginException {
        Callback[] callbacks = new Callback[1];
        RCPAuthCallback cb = new RCPAuthCallback();
        callbacks[0] = cb;
        try {
            callbackHandler.handle(callbacks);
            username = cb.getUsername();
            password = cb.getPassword();
            IUserDataProvider provider = UserDataProviderFactory.INSTANCE.getProvider();
            LOGIN_LOCK.lock();
            User authenticatedUser = provider.authenticate(username, password);
            if (authenticatedUser == null) {
                throw new LoginException("Authentication Failed");
            }
            username = authenticatedUser.getUsername();
            userRoles = provider.getUserRoles(authenticatedUser);
            return succeeded = true;
        } catch (UnsupportedCallbackException e) {
            throw new LoginException(e.getMessage());
        } catch (IOException e) {
            throw new LoginException(e.getMessage());
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        } finally {
            LOGIN_LOCK.unlock();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean commit() throws LoginException {
        if (!succeeded) {
            return false;
        }
        User user = TokenFactory.INSTANCE.createUser();
        user.setUsername(username);
        principal = TokenFactory.INSTANCE.createAuthToken();
        Authen authen = TokenFactory.INSTANCE.createAuthen();
        authen.setUser(user);
        authen.setAuthInstant(new Date());
        authen.setAuthenticatedBy(AUTHENTICATED_BY);
        principal.setAuthen(authen);
        Authz authz = TokenFactory.INSTANCE.createAuthz();
        List rolesList = authz.getRoles();
        while (userRoles.hasNext()) {
            rolesList.add(userRoles.next());
        }
        principal.setAuthz(authz);
        if (!subject.getPrincipals().contains(principal)) {
            subject.getPrincipals().add(principal);
        }
        username = null;
        password = null;
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        return false;
    }
}
