package com.tibco.cep.bemm.security;

import com.tibco.be.bemm.functions.MMActionsCallbackHandler;
import com.tibco.be.bemm.functions.UtilFunctions;
import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.bemm.management.util.XMLHandler;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnUtil;
import com.tibco.cep.runtime.service.management.jmx.principals.MMJmxPrincipal;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.Set;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BEMM",
        category = "Authentication",
        synopsis = "Functions for authenticating using JAAS")
public class SecurityHelper {

	public static MMJmxPrincipal authenticateAsToken(String username, String password) throws GeneralSecurityException, UnsupportedEncodingException {
		LoginContext lc;
		MMJmxPrincipal principal = null;

        username = URLDecoder.decode(username, "UTF-8");
        password = UtilFunctions.decodeBase64Pwd(URLDecoder.decode(password, "UTF-8"));

		try {
			lc = new LoginContext(JMXConnUtil.getLoginModuleConfig("JMXAuthenticator"),
                                        new MMActionsCallbackHandler(username, password));
		} catch (LoginException le) {
			throw new GeneralSecurityException("could not create login context to authenticate", le);
		}
		lc.login();

		Set<Principal> principals = lc.getSubject().getPrincipals();

		for (Principal p : principals) {
			if (p instanceof MMJmxPrincipal)
				principal = (MMJmxPrincipal) p;
		}

		return principal;
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "authenticate",
        synopsis = "This function provides authentication service. If the given\nusername/password is valid, then a serialized token is\nreturned else a null is returned. Any exception encountered is\nthrown as a runtime exception",
        signature = "String authenticate(String username, String password)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "username", type = "String", desc = "The username to be authenticated"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "password", type = "String", desc = "The password of the username")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "An serialized version of the token if authentication\nsucceeds else null"),
        version = "4.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Authenticate the given username/password",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
	public static String authenticate(String username, String password) {
		try {
             //we have to handle the empty password string case separately because somehow we receive "" rather than empty_string
            if (password.equals("\"\""))
                password = "";              //set what should be an empty_string to empty_string.

            //Added to support multibyte and multilingual characters for BE-MM.
            //The behavior will be the same as before for BE-Views and RMS
            username = URLDecoder.decode(username, "UTF-8");
            password = URLDecoder.decode(password, "UTF-8");

			MMJmxPrincipal principal = authenticateAsToken(username, password);

			return serializePrincipal(principal);
		} catch (LoginException ex) {
			// we could not authenticate the user , return a null
            System.err.println("Authentication failed: ");
            System.err.println("  " + ex.getMessage());
			return null;
		} catch (GeneralSecurityException ex) {
			// we had a exception while authenticating the user, throw a runtime exception
			throw new RuntimeException(ex.getMessage(), ex);
		} catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
	}

	public static String serializePrincipal(MMJmxPrincipal principal) {
		if (principal == null) {
			return null;
		}

        final String pattern = "User [{0}] with roles {1} SUCCESSFULLY logged on [{2}]";
        final String msg = MessageFormat.format(pattern, principal.getName(), principal.getRoles(), principal.getAuthTime());

        return XMLHandler.infoXML("Login", msg );
	}
}
