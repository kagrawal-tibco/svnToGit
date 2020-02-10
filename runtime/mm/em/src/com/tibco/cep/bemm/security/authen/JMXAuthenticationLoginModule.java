package com.tibco.cep.bemm.security.authen;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import com.tibco.cep.bemm.security.authen.mm.MMJmxPrincipalImpl;
import com.tibco.cep.bemm.security.authen.utils.MD5Hashing;
import com.tibco.cep.bemm.security.dataprovider.IUserDataProvider;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnUtil;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.tokens.User;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 1/19/11
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMXAuthenticationLoginModule implements LoginModule{

	private Subject subject;
	protected CallbackHandler callbackHandler;
	private boolean succeeded;
	private boolean isSecurityEnabled;
	private boolean isAuthenticSysOperation;

	protected String username;
	protected String password;
	private Iterator<Role> userRoles;

    @Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
	}

    @Override
    public boolean login() throws LoginException {
		Callback[] callbacks = new Callback[2];
	    callbacks[0] = new NameCallback("username");
	    callbacks[1] = new PasswordCallback("password", false);
        try {
            callbackHandler.handle(callbacks);
            username = ((NameCallback)callbacks[0]).getName();

            char[] tmpPassword = ((PasswordCallback)callbacks[1]).getPassword();
	        password = new String(tmpPassword);
	        ((PasswordCallback)callbacks[1]).clearPassword();

            return authenticate();

        } catch (UnsupportedCallbackException e) {
			throw new LoginException(e.getMessage());
		} catch (IOException e) {
			throw new LoginException(e.getMessage());
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		}
    }

    private boolean authenticate() throws Exception {
        final RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().
                getProvider(System.getProperty(SystemProperty.ENGINE_NAME.getPropertyName()));

        //In the second 'if', in case the property JMXUtil.BE_PROPERTIES.JMX_CONNECTOR_AUTHENTICATE is not set,
        // the condition will return False. Therefore no authentication will be performed.
         if (isAuthenticSysOperation = isAuthenticSystemOperation(rsp)) {
            succeeded = true;
        } else if( !(isSecurityEnabled = JMXConnUtil.isSecurityEnabled(rsp) ) ) {
            succeeded = true;
        } else { //perform authentication
            IUserDataProvider provider = UserDataProviderFactory.INSTANCE.getProvider();
            User authenticatedUser = provider.authenticate(username, password);
            if (authenticatedUser == null) {
                throw new LoginException("Authentication Failed");
            }
            userRoles = provider.getUserRoles(authenticatedUser);
            succeeded = true;
        }
        return succeeded;
    }

    private boolean isAuthenticSystemOperation(RuleServiceProvider rsp) {
        final String hashUser = MD5Hashing.getMD5Hash(JMXConnUtil.getConnIP());
        final String hashPwd = MD5Hashing.getMD5Hash(
                JMXConnUtil.getConnIP()+":"+JMXConnUtil.getConnPort(rsp) );

        return  hashUser.equals(username) && hashPwd.equals(password);
    }

	@Override
	public boolean commit() throws LoginException {
        try {
            if (!succeeded) {
                return false;
            }

            MMJmxPrincipalImpl principal = new MMJmxPrincipalImpl(username, isSecurityEnabled, isAuthenticSysOperation);
            principal.setRoles(userRoles);

            if (!subject.getPrincipals().contains(principal)) {
                subject.getPrincipals().add(principal);
            }
            return true;
        } finally {
            username = null;
            password = null;
            isSecurityEnabled=true;
            isAuthenticSysOperation=false;
        }
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
