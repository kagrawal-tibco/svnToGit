package com.tibco.be.rms.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.security.SecurityHelper;
import com.tibco.cep.security.authen.RCPAuthCallbackHandler;
import com.tibco.cep.security.tokens.AuthToken;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Apr 11, 2008
 * Time: 12:05:56 PM
 * To change this template use File | Settings | File Templates.
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "RMS.Login",
        synopsis = "Functions for Authentication")
public class AuthenticationHelper {


	/**
	 *
	 * @param username
	 * @param password
	 * @return
	 * @throws GeneralSecurityException
	 * @throws LoginException
	 * @deprecated see {@link SecurityHelper#authenticate(String, String)}
	 */
	public static String authenticate(String username, String password) throws GeneralSecurityException, LoginException {
        LoginContext lc;
        AuthToken token = null;
        try {
            lc = new LoginContext("RCPAuthenticator",
                    new RCPAuthCallbackHandler(username, password));
            //System.out.println(lc);
            //This method throwing an exception is a shame
        } catch (LoginException le) {
        	throw new GeneralSecurityException(
        			"could not create login context to authenticate",le);
        } catch (SecurityException se) {
        	throw new GeneralSecurityException(
        			"no permission to create login context to authenticate",se);
        }
        lc.login();

        Set<Principal> principals = lc.getSubject().getPrincipals();

        for (Principal p : principals) {
            if (p instanceof AuthToken) {
                token = (AuthToken)p;
                token.getName();
            }
        }
        String tokenString = null;
        try {
            tokenString = serializeAuthToken(token);
        }  catch (IOException e) {
        	throw new GeneralSecurityException(
        			"could not serialize authenticated token",e);
        }

        return tokenString;
	}

    @com.tibco.be.model.functions.BEFunction(
        name = "login",
        synopsis = "This function authenticates this user against file or ldap.\nThis can be used to authenticate against any backend store.",
        signature = "String login(String username, String password)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "username", type = "String", desc = "the input directory to look for class files"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "password", type = "String", desc = "the base64 encoded password string")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Authenticate using the passed username, and password",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String login(String username, String password) {
        LoginContext lc = null;
        AuthToken token = null;
        try {
            lc = new LoginContext("RCPAuthenticator",
                    new RCPAuthCallbackHandler(username, password));

        } catch (LoginException le) {

            System.out
                    .println("Cannot create LoginContext. " + le.getMessage());

        } catch (SecurityException se) {
            System.err
                    .println("Cannot create LoginContext. " + se.getMessage());
        }  catch (Exception e) {
            e.printStackTrace();
        }
        try {
            lc.login();

            Set<Principal> principals = lc.getSubject().getPrincipals();

            for (Principal p : principals) {
                if (p instanceof AuthToken) {
                    token = (AuthToken)p;
                    token.getName();
                }
            }

        } catch (LoginException le) {
            le.printStackTrace();
            System.err.println("Authentication failed: ");
            System.err.println("  " + le.getMessage());
        }
        String tokenString = null;
        try {
            tokenString = serializeAuthToken(token);
        }  catch (IOException e) {
            e.printStackTrace();
            System.err.println("  " + e.getMessage());
        }

        return tokenString;
    }



    private static String serializeAuthToken(final AuthToken token) throws IOException {
        if (token == null) {
            return null;
        }
        //1/15/2010 - Modified By Anand To Support NON EMF Persistence Of Token
        return SecurityHelper.serializeAuthToken(token);
    }
}
