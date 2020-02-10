package com.tibco.cep.security;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.baas.security.authn.saml.common.SSOToken;
import com.tibco.cep.security.authen.RCPAuthCallbackHandler;
import com.tibco.cep.security.authen.sso.SSOAuthCallbackHandler;
import com.tibco.cep.security.tokens.*;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;

import org.apache.commons.lang3.StringEscapeUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.*;

import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.UTF8_ENCODING;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Security",
        category = "Authentication",
        synopsis = "Functions for authenticating using JAAS")
public class SecurityHelper {

    public static AuthToken authenticateAsToken(String username, String password) throws GeneralSecurityException {
        LoginContext lc;
        AuthToken token = null;
        try {
            lc = new LoginContext("RCPAuthenticator", new RCPAuthCallbackHandler(username, password));
            lc.login();
        } catch (LoginException le) {
            throw new GeneralSecurityException(le);
        }
        Set<Principal> principals = lc.getSubject().getPrincipals();

        for (Principal p : principals) {
            if (p instanceof AuthToken) {
                token = (AuthToken) p;
                token.getName();
            }
        }

        return token;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createUUID",
        synopsis = "Generate a universally unique identifier",
        signature = "String createUUID()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Generate a universally unique identifier.",
        cautions = "",
        fndomain = {ACTION, QUERY, CONDITION, BUI},
        example = ""
    )
    public static String createUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     *
     * @param tokenIssuerUrl
     * @param username
     * @param password
     * @return
     * @throws GeneralSecurityException
     * @throws LoginException
     */
    private static SSOToken returnSSOToken(String tokenIssuerUrl, String username, String password) throws GeneralSecurityException, LoginException {
        LoginContext loginContext;
        SSOToken token = null;
        try {
            loginContext = new LoginContext("SSOAuthenticator", new SSOAuthCallbackHandler(tokenIssuerUrl, username, password));
        } catch (LoginException le) {
            throw new GeneralSecurityException("could not create login context to authenticate", le);
        }
        loginContext.login();
        Set<Principal> principals = loginContext.getSubject().getPrincipals();

        for (Principal principal : principals) {
            if (principal instanceof SSOToken) {
                token = (SSOToken) principal;
            }
        }

        return token;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "ssoAuthenticate",
        synopsis = "Authenticate the given username/password and create an SSO token\nin Base64 encoded form to be embedded as cookie.",
        signature = "String ssoAuthenticate(String tokenIssuerUrl, String username, String password)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tokenIssuerUrl", type = "String", desc = "The url of the issuing idp."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "username", type = "String", desc = "The username to be authenticated."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "password", type = "String", desc = "The password of the username.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "An serialized version of the sso token if authentication\nsucceeds else null"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Authenticate the given username/password and create an SSO token\nin Base64 encoded form to be embedded as cookie.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String ssoAuthenticate(String tokenIssuerUrl, String username, String password) {
        try {
            //Added to support multibyte and multilingual characters for BE-MM.
            //The behavior will be the same as before for BE-Views and RMS
            if (username == null || username.isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty");
            }
            username = URLDecoder.decode(username, UTF8_ENCODING);
            if (password != null) {
                password = URLDecoder.decode(password, UTF8_ENCODING);
            }

            SSOToken ssoToken = returnSSOToken(tokenIssuerUrl, username, password);
            return ssoToken.toBase64();
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
            //Added to support multibyte and multilingual characters for BE-MM.
            //The behavior will be the same as before for BE-Views and RMS
            if (username == null || username.isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty");
            }
            username = URLDecoder.decode(username, UTF8_ENCODING);
            if (password != null) {
                password = URLDecoder.decode(password, UTF8_ENCODING);
            }

            AuthToken token = authenticateAsToken(username, password);
            return serializeAuthToken(token);
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

    public static String serializeAuthToken(AuthToken token) {
    	return serializeAuthToken(token, true);
    }

    public static String serializeAuthToken(AuthToken token, boolean includeAuthz) {
        if (token == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder("<token>");
        Authen authen = token.getAuthen();
        if (authen != null) {
            sb.append("<authen");
            if (authen.getAuthInstant() != null) {
                sb.append(" authinstant=\"" + authen.getAuthInstant().getTime() + "\"");
            }
            if (authen.getAuthenticatedBy() != null) {
                sb.append(" authenticatedby=\"" + StringEscapeUtils.escapeXml(authen.getAuthenticatedBy()) + "\"");
            }
            if (authen.getSessionToken() != null) {
                sb.append(" sessiontoken=\"" + StringEscapeUtils.escapeXml(authen.getSessionToken()) + "\"");
            }
            sb.append(" ttl=\"" + authen.getTimeToLive() + "\">");
            if (authen.getUser() != null) {
                sb.append("<user username=\"" + authen.getUser().getUsername() + "\"/>");
            }
            sb.append("</authen>");
        }
        if (includeAuthz) {
	        Authz authz = token.getAuthz();
	        if (authz != null) {
	            sb.append("<authz>");
	            for (Role role : authz.getRoles()) {
	                sb.append("<role name=\"" + StringEscapeUtils.escapeXml(role.getName()) + "\"/>");
	            }
	            sb.append("</authz>");
	        }
        }    
        sb.append("</token>");
        return sb.toString();
    }
    
    @SuppressWarnings("unchecked")
    public static AuthToken deserializeAuthToken(String tokenString) {
        if (tokenString == null || tokenString.trim().length() == 0) {
            return null;
        }
        try {
        	String tokenStr = new String(tokenString.getBytes("UTF-8"), "UTF-8");
            // create an instance of the parser
            XiParser parser = XiParserFactory.newInstance();
            // parse the document
            final XiNode doc = parser.parse(new InputSource(new StringReader(tokenStr)));
            XiNode tokenNode = doc.getFirstChild();
            AuthToken authToken = TokenFactory.INSTANCE.createAuthToken();
            // authen parsing
            XiNode authenNode = XiChild.getChild(tokenNode, ExpandedName.makeName("authen"));
            if (authenNode != null) {
                Authen authen = TokenFactory.INSTANCE.createAuthen();
                // auth instance date
                String authInstant = authenNode.getAttributeStringValue(ExpandedName.makeName("authinstant"));
                if (authInstant != null) {
                    try {
                        authen.setAuthInstant(new Date(Long.parseLong(authInstant)));
                    } catch (NumberFormatException ex) {
                        // do nothing
                    }
                }
                // authenticated By
                String authBy = authenNode.getAttributeStringValue(ExpandedName.makeName("authenticatedby"));
                authen.setAuthenticatedBy(authBy);
                // session token
                String sessionToken = authenNode.getAttributeStringValue(ExpandedName.makeName("sessiontoken"));
                authen.setSessionToken(sessionToken);
                // ttl
                String ttl = authenNode.getAttributeStringValue(ExpandedName.makeName("ttl"));
                if (ttl != null) {
                    try {
                        authen.setTimeToLive(Integer.parseInt(ttl));
                    } catch (NumberFormatException ex) {
                        // do nothing
                    }
                }
                // user
                XiNode userNode = XiChild.getChild(authenNode, ExpandedName.makeName("user"));
                if (userNode != null) {
                    User user = TokenFactory.INSTANCE.createUser();
                    String username = userNode.getAttributeStringValue(ExpandedName.makeName("username"));
                    user.setUsername(username);
                    authen.setUser(user);
                }

                authToken.setAuthen(authen);
            }

            // authz parsing
            XiNode authzNode = XiChild.getChild(tokenNode, ExpandedName.makeName("authz"));
            if (authzNode != null) {
                Authz authz = TokenFactory.INSTANCE.createAuthz();
                List<Role> roles = authz.getRoles();
                Iterator rolesIterator = XiChild.getIterator(authzNode, ExpandedName.makeName("role"));
                while (rolesIterator.hasNext()) {
                    XiNode roleNode = (XiNode) rolesIterator.next();
                    String roleName = roleNode.getAttributeStringValue(ExpandedName.makeName("name"));
                    Role role = TokenFactory.INSTANCE.createRole();
                    role.setName(roleName);
                    roles.add(role);
                }
                authToken.setAuthz(authz);
            }

            return authToken;
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

//    public static void main(String[] args) {
//        AuthToken token = TokenFactory.INSTANCE.createAuthToken();
//
//        Authen authen = TokenFactory.INSTANCE.createAuthen();
//        authen.setAuthInstant(new Date());
//        authen.setAuthenticatedBy("Test");
//        authen.setSessionToken(UUID.randomUUID().toString().toString());
//        authen.setTimeToLive(10);
//
//        User user = TokenFactory.INSTANCE.createUser();
//        user.setUsername("anpatil");
//        user.setPassword("password");
//        authen.setUser(user);
//
//        token.setAuthen(authen);
//
//        Authz authz = TokenFactory.INSTANCE.createAuthz();
//        String[] roleNames = new String[]{"RoleA", "Admin", "Novice"};
//        for (String roleName : roleNames) {
//            Role role = TokenFactory.INSTANCE.createRole();
//            role.setName(roleName);
//            authz.getRoles().add(role);
//        }
//
//        token.setAuthz(authz);
//
//        System.out.println(token);
//        String serializeAuthToken = serializeAuthToken(token);
//        System.out.println(serializeAuthToken);
//
//        AuthToken deserializeAuthToken = deserializeAuthToken(serializeAuthToken);
//        System.out.println(deserializeAuthToken);
//
//        System.out.println(deserializeAuthToken.equals(token));
//    }
}
