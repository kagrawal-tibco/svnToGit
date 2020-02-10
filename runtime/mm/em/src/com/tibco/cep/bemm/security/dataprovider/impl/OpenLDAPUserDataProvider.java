package com.tibco.cep.bemm.security.dataprovider.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

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
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.tokens.TokenFactory;
import com.tibco.cep.security.tokens.User;

/**
 * RMS User data provider for OpenLDAP Directory server
 * @author vdhumal
 *
 */
public class OpenLDAPUserDataProvider extends LDAPUserDataProvider {

	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(OpenLDAPUserDataProvider.class);
	
	public Iterator<Role> getUserRoles(final User user) throws DataProviderException {	
		List<Role> roles = new ArrayList<Role>(1);
		try {
			//Initialise Base Env
			Hashtable<Object, Object> dynEnv = this.initializeDynEnv("simple", user.getUsername(), user.getPassword());
			this.setBaseEnvironment(dynEnv);

			//Base DN
			String baseDN = this.getProperty(DataProviderConstants.PROP_BEMM_LDAP_BASEDN, null);
			LOGGER.log(Level.DEBUG, "Base DN for search is %s", baseDN);

			//Build Search Filter
			String userDN = user.getUsername();
			LOGGER.log(Level.DEBUG, "User DN for fetching roles is %s", userDN);
			String roleAttr = this.getProperty(DataProviderConstants.PROP_BEMM_LDAP_ROLEATTR, "member");
			String searchFilter = this.buildSearchCriterion(userDN, roleAttr);
			LOGGER.log(Level.DEBUG, "Search Filter is %s", searchFilter);

			//Set returning attribute
	        boolean useRoleDN = Boolean.parseBoolean(this.getProperty(DataProviderConstants.PROP_BEMM_USE_ROLE_DN, "true"));
			String roleNameAttr = useRoleDN ? this.getProperty(DataProviderConstants.PROP_BEMM_LDAP_DN_ATTR, "entryDN")
											: DataProviderConstants.ATTR_COMMON_NAME;
			LOGGER.log(Level.DEBUG, "Returning attribute is %s", roleNameAttr);
			SearchControls constraints = new SearchControls();
			constraints.setReturningAttributes(new String[] {roleNameAttr});
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);

			//Search
			DirContext ctx = new InitialDirContext(getBaseEnvironment());
			NamingEnumeration<SearchResult> results = ctx.search(baseDN, searchFilter, constraints);
			
			//Iterate through results
			while (results.hasMore()) {
				SearchResult searchResult = results.next();
				LOGGER.log(Level.DEBUG, "Search result for search filter %s is %s", searchFilter, searchResult.getName());
				Attributes returnedAttributes = searchResult.getAttributes();
				Attribute roleNameAttrObj = returnedAttributes.get(roleNameAttr);
				if (roleNameAttrObj != null && roleNameAttrObj.get() != null) {
					String roleName = roleNameAttrObj.get().toString();
					Role role = TokenFactory.INSTANCE.createRole();
					role.setName(roleName);
					roles.add(role);
				}	
			}
		} catch (NamingException ne) {
			throw new DataProviderException(ne);
		}

		return roles.iterator();
	}
}
