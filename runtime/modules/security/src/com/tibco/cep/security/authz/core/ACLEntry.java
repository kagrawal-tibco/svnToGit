/**
 * 
 */
package com.tibco.cep.security.authz.core;


import java.io.Serializable;

import com.tibco.cep.security.authz.permissions.IResourcePermission;
import com.tibco.cep.security.authz.permissions.PermissionsCollection;
import com.tibco.cep.security.tokens.Role;

/**
 * @author aathalye
 *
 */
public interface ACLEntry extends Cloneable, Serializable, SerializableObject {
	
	/**
	 * Adds a new <tt>IResourcePermission</tt> object
	 * to this <tt>ACLEntry</tt>.
	 * <p>
	 * This method returns <tt>false</tt> if the 
	 * <tt>IResourcePermission</tt> exists inside the 
	 * <tt>ACLEntry</tt>. 
	 * @param permission
	 * @return true on successful addition
	 * @see IResourcePermission#equals(Object)
	 */
	public boolean addPermission(IResourcePermission permission);
	
	/**
	 * Removes an existing <tt>IResourcePermission</tt> object
	 * from this <tt>ACLEntry</tt>.
	 * <p>
	 * This method returns <tt>false</tt> if the 
	 * <tt>IResourcePermission</tt> does not exist in 
	 * <tt>ACLEntry</tt>. 
	 * @param permission
	 * @return true on successful addition
	 * @see IResourcePermission#equals(Object)
	 */
	public boolean removePermission(IResourcePermission permission);
	
	/**
	 * @return The <tt>PermissionsCollection</tt> associated
	 * with this <tt>ACLEntry</tt>.
	 */
	public PermissionsCollection getPermissions();
	
	/**
	 * Set <tt>PermissionsCollection</tt> for this <tt>ACLEntry</tt>.
	 * @param permissionsCollection
	 */
	public void setPermissions(PermissionsCollection permissionsCollection);
	
	/**
	 * Set <tt>Role</tt> for this <tt>ACLEntry</tt>
	 * @param role
	 */
	public void setRole(Role role);
	
	/**
	 * @return The <tt>Role</tt> associated with this <tt>
	 * ACLEntry</tt>
	 */
	public Role getRole();
}
