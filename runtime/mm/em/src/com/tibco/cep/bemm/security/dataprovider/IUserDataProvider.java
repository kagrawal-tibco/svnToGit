/**
 * 
 */
package com.tibco.cep.bemm.security.dataprovider;

import java.util.Iterator;
import java.util.Properties;

import com.tibco.cep.bemm.security.dataprovider.impl.DataProviderException;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.tokens.User;

/**
 * @author aathalye
 *
 */
public interface IUserDataProvider {
	
	
	/**
	 * API to do any initialization of the provider.
	 * <p>
	 * An example could be to build one time setup for
	 * connecting to the back-end system.
	 * </p>
	 * @throws DataProviderException
	 */
	public void init(Properties properties) throws DataProviderException;
	
	/**
	 * Takes a <b>username</b>, and <b>password</b>
	 * combination, and authenticates it with backend 
	 * user data repository.
	 * @param username
	 * @param password
	 * @return an instance of authenticated user, or exception if not authenticated
	 * @throws DataProviderException
	 */
	public User authenticate(String username, String password) throws DataProviderException;
	
	/**
	 * Takes an authenticated <tt>User</tt>, and 
	 * fetches all <tt>Role</tt> user belongs to.
	 * <p>
	 * <b>Role</b> is an abstraction here for any entity
	 * like an LDAP Role, DB Role etc.
	 * </p>
	 * @param user
	 * @return an iterator over all the roles user belongs to
	 * @throws DataProviderException if exception is thrown while fetching them.
	 */
	public Iterator<Role> getUserRoles(User user) throws DataProviderException;
}
