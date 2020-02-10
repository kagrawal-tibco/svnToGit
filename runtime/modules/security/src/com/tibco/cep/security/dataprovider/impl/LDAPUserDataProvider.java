/**
 *
 */
package com.tibco.cep.security.dataprovider.impl;

import com.tibco.cep.security.dataprovider.impl.DataProviderConstants;
import javax.naming.Context;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.naming.CommunicationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.security.dataprovider.IUserDataProvider;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.tokens.TokenFactory;
import com.tibco.cep.security.tokens.User;
import com.tibco.cep.security.tokens.impl.UserImpl;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;

/**
 * @author aathalye
 *
 */
public class LDAPUserDataProvider implements IUserDataProvider {

    public static final String BE_AUTH_LDAP_TYPE = "be.auth.ldap.type";
    public static final String LDAP_PROVIDER_TYPE_OPENLDAP = "openldap";
	
    private Hashtable<Object, Object> baseEnv = new Hashtable<Object, Object>();

    private Properties properties;

    private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LDAPUserDataProvider.class);

    /**
     *
     * @param properties
     * @throws DataProviderException
     */
    public void init(Properties properties) throws DataProviderException {
        if (properties == null) {
            throw new IllegalArgumentException("Invalid properties");
        }
        this.properties = properties;
        baseEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        String host = properties.getProperty(DataProviderConstants.PROP_LDAP_HOST);
        String port = properties.getProperty(DataProviderConstants.PROP_LDAP_PORT);
        String adminDN = properties.getProperty(DataProviderConstants.PROP_LDAP_ADMINDN);
        String adminPassword = decrypt("admin", properties.getProperty(DataProviderConstants.PROP_LDAP_ADMINPASSWD));
        if (host == null || port == null) {
            final String msg = host == null ? "Host" : "Port";
            LOGGER.log(Level.WARN, msg + " is null");
        }

        //These null values have to be handled here because if we try to the null values in
        // the HashMap bellow, a NPE will be thrown.
        if (adminDN == null || adminPassword == null ) {
            StringBuilder builder = new StringBuilder("\t");
            builder.append(DataProviderConstants.PROP_LDAP_HOST).append("=").append(host).append("\n\t").
            append(DataProviderConstants.PROP_LDAP_PORT).append("=").append(port).append("\n\t").
            append(DataProviderConstants.PROP_LDAP_ADMINDN).append("=").append(adminDN).append("\n\t").
            append(DataProviderConstants.PROP_LDAP_ADMINPASSWD).append("=").append(adminPassword).append("\n\t");

            throw new SecurityException(String.format("LDAP is misconfigured: \n %s", builder.toString()));
        }
        boolean ssl = Boolean.valueOf(properties.getProperty(DataProviderConstants.PROP_LDAP_SSL));
        if (ssl == true) {
            baseEnv.put(Context.PROVIDER_URL, "ldaps://" + host + ":" + port + "/");
            String trustStorePwd = properties.getProperty(DataProviderConstants.JAVAX_NET_SSL_TRUSTSTOREPASSWORD);
            if (trustStorePwd != null) {
                trustStorePwd = decrypt("trust store", trustStorePwd);
                System.setProperty(DataProviderConstants.JAVAX_NET_SSL_TRUSTSTOREPASSWORD, trustStorePwd);
            }
        }
        else {
            baseEnv.put(Context.PROVIDER_URL, "ldap://" + host + ":" + port + "/");
        }
        // Use administrator login to bind at startup
        baseEnv.put(Context.SECURITY_PRINCIPAL, adminDN);
        baseEnv.put(Context.SECURITY_CREDENTIALS, adminPassword);
    }

    private String decrypt(String name, String password) {
        try {
            boolean isObfuscated = ObfuscationEngine.hasEncryptionPrefix(password);
            if (isObfuscated) {
                return new String(ObfuscationEngine.decrypt(password));
            } else {
                LOGGER.log(Level.WARN, "%s password is not obfuscated", name);
                return password;
            }
        } catch (AXSecurityException axe) {
            throw new SecurityException(String.format("Misconfigured LDAP %s password is not obfuscated", name));
        }
    }

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws DataProviderException
     */
	public User authenticate(final String username, String password) throws DataProviderException {
		LDAPUser ldapUser = getDNFromUserID(username);
		if (ldapUser == null) {
			throw new DataProviderException("Username not found");
		}
		LOGGER.log(Level.DEBUG, "Matching user DN for username %s is %s", username, ldapUser.getDistinguishedName());
        LOGGER.log(Level.DEBUG, "Matching Principal for username %s is %s", username, ldapUser.getPrincipalName());
        // We will not allow blank passwords because the protocol
        // converts blank passwords to "none" authentication
        // which means any user can login without password
        if (password == null || password.trim().length() == 0) {
            throw new DataProviderException("Blank passwords are not allowed");
        }
        try {
            //Domain name required for auth in case found
            String userDN = (ldapUser.getPrincipalName() != null) ? ldapUser.getPrincipalName() : ldapUser.getDistinguishedName();
			Hashtable<Object, Object> dynEnv = initializeDynEnv("simple", userDN, password);
			//we will attempt to connect the system using the username/password
			createInitialDirContext(dynEnv);
			//We were able to authenticate with the username/password
			ldapUser.setPassword(password);
		} catch (NamingException ne) {
			throw new DataProviderException(ne);
		}
		return ldapUser;
	}

    /**
     * Fetch user roles and those roles will be either in fully qualified form
     * or only common name for those will be used.
     * <p>
     * This will be driven by the property <b>"be.auth.ldap.useRoleDN"</b>
     * </p>
     * @param user
     * @return
     * @throws DataProviderException
     */
    public Iterator<Role> getUserRoles(final User user) throws DataProviderException {
        //Should role dn be retrieved or role names only
        boolean useRoleDN = Boolean.parseBoolean(properties.getProperty(DataProviderConstants.PROP_USE_ROLE_DN, "true"));
		// TODO handle groups
		List<Role> roles = new ArrayList<Role>(1);
		try {
			String userDN = user.getUsername();
			if (user instanceof LDAPUser) {
	            //Use distinguished name for searches.
				userDN = ((LDAPUser) user).getDistinguishedName();
			}
			Hashtable<Object, Object> dynEnv = initializeDynEnv("simple", userDN, user.getPassword());
			DirContext ctx = createInitialDirContext(dynEnv);
			LOGGER.log(Level.DEBUG, "User DN for fetching roles is %s", userDN);
            //Get distinguished name for user.
            String roleAttr = properties.getProperty(DataProviderConstants.PROP_LDAP_ROLEATTR, "nsroledn");
            String[] attrs = { roleAttr };
            Attributes attributes = ctx.getAttributes(userDN, attrs);
            Attribute attr = attributes.get(roleAttr);
            if (attr != null) {
                LOGGER.log(Level.DEBUG, "Attribute retrieved is %s", attr.getID());
                NamingEnumeration<?> result = attr.getAll();
                while (result.hasMore()) {
                    Object attrV = result.next();
                    String roleDN = (String) attrV;
                    LOGGER.log(Level.DEBUG, "Role DN for user %s retrieved is %s", user.getUsername(), roleDN);
                    if (!useRoleDN) {
                        //Get property representing DN
                        String dnAttributeName = properties.getProperty(DataProviderConstants.PROP_LDAP_DN_ATTR, DataProviderConstants.ATTR_DISTINGUISHED_NAME);
                        LOGGER.log(Level.DEBUG, "Using DN attribute %s", dnAttributeName);
                        //Look up for this object and fetch its cn attribute
                        SearchControls constraints = new SearchControls();
                        constraints.setCountLimit(1);
                        constraints.setReturningAttributes(new String[] {DataProviderConstants.ATTR_COMMON_NAME});
                        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
                        String searchFilter = buildSearchCriterion(roleDN, dnAttributeName);
                        LOGGER.log(Level.DEBUG, "Search Filter is %s", searchFilter);
                        NamingEnumeration<SearchResult> results = ctx.search(roleDN, searchFilter, constraints);
                        if (results != null && results.hasMore()) {
                            SearchResult searchResult = results.next();
                            Attributes searchedAttributes = searchResult.getAttributes();
                            Attribute attribute = searchedAttributes.get(DataProviderConstants.ATTR_COMMON_NAME);
                            if (attribute != null) {
                                roleDN = (String)attribute.get();
                                LOGGER.log(Level.DEBUG, "Common name for role %s", roleDN);
                            }
                        }
                    }
                    Role role = TokenFactory.INSTANCE.createRole();
                    role.setName(roleDN);
                    roles.add(role);
                }
            }
            ctx.close();
        } catch (NamingException ne) {
            throw new DataProviderException(ne);
        }
        return roles.iterator();
    }

	@Override
	public String getUserNotifyId(String username) throws DataProviderException {
		DirContext ctx = null;
		String userNotifyId = null;
		try {
            String userIdAttr = properties.getProperty(DataProviderConstants.PROP_LDAP_UIDATTR, "uid");
			String searchFilter = buildSearchCriterion(username, userIdAttr);
			LOGGER.log(Level.DEBUG, "Search Filter is %s", searchFilter);

			String userNotifyIdAttr = properties.getProperty(DataProviderConstants.PROP_LDAP_USER_NOTIFY_ID_ATTR, DataProviderConstants.ATTR_USER_PRINCIPAL_NAME);			
			
			ctx = new InitialDirContext(baseEnv);
			SearchControls constraints = new SearchControls();
			constraints.setCountLimit(1);
			constraints.setReturningAttributes(new String[] {userNotifyIdAttr});
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);

			String baseDN = properties.getProperty(DataProviderConstants.PROP_LDAP_BASEDN);
			LOGGER.log(Level.DEBUG, "Base DN for search is %s", baseDN);

			NamingEnumeration<SearchResult> results = ctx.search(baseDN, searchFilter, constraints);			
			if (results != null && results.hasMore()) {
				SearchResult searchResult = results.next();
                Attributes searchedAttributes = searchResult.getAttributes();
				LOGGER.log(Level.DEBUG, "Search result for search filter %s is %s", searchFilter, searchResult.getName());
                if (searchedAttributes != null && searchedAttributes.size() > 0) {
                    Attribute userNotifyIdAttribute = searchedAttributes.get(userNotifyIdAttr);
                    if (userNotifyIdAttribute != null) {
                    	userNotifyId = (String)userNotifyIdAttribute.get();
                    }
                }
			}
		} catch (NamingException ne) {
			throw new DataProviderException("could not search " + username, ne);
		} finally {
			if (ctx != null){
				try {
					ctx.close();
				} catch (NamingException ne) {
					LOGGER.log(Level.WARN, "Error closing Ldap context");
				}
			}
		}		
		return userNotifyId;
	}

	protected Hashtable<Object, Object> initializeDynEnv(final String authType, final String userDN, final String password) {
		final Hashtable<Object, Object> dynEnv = new Hashtable<Object, Object>(baseEnv);
		dynEnv.put(Context.SECURITY_AUTHENTICATION, authType);
		dynEnv.put(Context.SECURITY_PRINCIPAL, userDN);
		dynEnv.put(Context.SECURITY_CREDENTIALS, password);
        return dynEnv;
    }

    protected DirContext createInitialDirContext(Hashtable<Object, Object> dynEnv) throws NamingException {
        DirContext ctx = null; 
        try {
        	ctx = new InitialDirContext(dynEnv);
        } catch (CommunicationException ce) { //re-try in case of first time connection timeouts
            LOGGER.log(Level.DEBUG, ce, ce.getMessage());
            LOGGER.log(Level.DEBUG, "Re-trying connection...");
            ctx = new InitialDirContext(dynEnv);
        }        
    	return ctx;
    }

    /**
     *
     * @param searchValue -> The value to be searched
     * @param attribute -> The attribute in LDAP to search this value with.
     * @return
     */
    protected String buildSearchCriterion(String searchValue, String attribute) {
        StringBuilder sb = new StringBuilder();
        sb.append("(&(");
        sb.append(attribute);
        sb.append("=");
        sb.append(searchValue);
        sb.append(")");
        String objectClass = properties.getProperty(DataProviderConstants.PROP_LDAP_OBJECTCLASS_ATTR, "*");
        if (objectClass != null) {
            sb.append("(");
            sb.append("objectclass");
            sb.append("=");
            sb.append(objectClass);
            sb.append(")");
        }
        sb.append(")");
        return sb.toString();
    }
    
	protected String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue); 
	}    

    /**
     * @param envProps -> LDAP Env properties
     */
    protected void setBaseEnvironment(Hashtable<Object, Object> envProps) {
        baseEnv.putAll(envProps);
    }

    /**
     * @return a copy of the Hashtable with the base env props
     */
    protected Hashtable<Object, Object> getBaseEnvironment() {
        return (new Hashtable<Object, Object>(baseEnv));
    }

    protected class LDAPUser extends UserImpl {

        /**
		 * 
		 */
		private static final long serialVersionUID = 3209753451772768408L;

		/**
         * Principal name also referred as domain name.
         */
        private String principalName;

        /**
         * The fully qualified DN.
         */
        private String distinguishedName;

        private LDAPUser(String userName, String principalName, String distinguishedName) {
        	setUsername(userName);
        	this.principalName = principalName;
            this.distinguishedName = distinguishedName;
        }

        public String getPrincipalName() {
            return principalName;
        }

        public String getDistinguishedName() {
            return distinguishedName;
        }

    }

    /**
     *
     * @param userId
     * @return ldapUser
     * @throws DataProviderException
     */
    private LDAPUser getDNFromUserID(final String userId) throws DataProviderException {
        DirContext ctx = null;
        LDAPUser ldapUser = null;
        try {
            String userIdAttr = properties.getProperty(DataProviderConstants.PROP_LDAP_UIDATTR, "uid");
            String searchFilter = buildSearchCriterion(userId, userIdAttr);
            LOGGER.log(Level.DEBUG, "Search Filter is %s", searchFilter);
            ctx = createInitialDirContext(baseEnv);
            SearchControls constraints = new SearchControls();
            constraints.setCountLimit(1);
            //See if RFC 822 style principal name exists.
            constraints.setReturningAttributes(new String[] {userIdAttr, DataProviderConstants.ATTR_USER_PRINCIPAL_NAME});
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String baseDN = properties.getProperty(DataProviderConstants.PROP_LDAP_BASEDN);
            LOGGER.log(Level.DEBUG, "Base DN for search is %s", baseDN);
            NamingEnumeration<SearchResult> results = ctx.search(baseDN, searchFilter, constraints);
            if (results != null && results.hasMore()) {
                SearchResult searchResult = results.next();
                String userDN = searchResult.getName() + "," + baseDN;
                Attributes searchedAttributes = searchResult.getAttributes();
                LOGGER.log(Level.DEBUG, "Search result for search filter %s is %s", searchFilter, searchResult.getName());
                if (searchedAttributes == null || searchedAttributes.size() == 0) {
                    ldapUser = new LDAPUser(userId, null, userDN);
                } else {
                    //Look for principal name
                    Attribute userPrincipalAttribute = searchedAttributes.get(DataProviderConstants.ATTR_USER_PRINCIPAL_NAME);
                    String principal = null;
                    if (userPrincipalAttribute != null) {
                        //If this is not string we are doomed.
                        principal = (String)userPrincipalAttribute.get();
                    }
                    Attribute userIdAttribute = searchedAttributes.get(userIdAttr);
                    String userName = null;                    
                    if (userIdAttribute != null) {
                    	userName = (String)userIdAttribute.get();
                    }
                    ldapUser = new LDAPUser(userName, principal, userDN);
                }
            }
            return ldapUser;
        } catch (NamingException e) {
            LOGGER.log(Level.ERROR, e, e.getMessage());
            throw new DataProviderException("could not search " + userId, e);
        } finally {
            if (ctx != null){
                try {
                    ctx.close();
                } catch (NamingException ignore) {
                }
            }
        }
    }
}
