/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import java.io.Serializable;

import com.tibco.cep.security.authz.core.SerializableObject;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author aathalye
 *
 */
public interface IResourcePermission extends Serializable, SerializableObject {
	
	/**
	 * Return the resource this <code>IResourcePermission</code>
	 * is associated with
	 * @return the associated resource
	 */ 
	public IDomainResource getResource();
	
	/**
	 * Set resource for this <code>IResourcePermission</code>
	 * @return the associated resource
	 */ 
	public void setResource(IDomainResource resource);
	
	/**
	 * Returns the <code>IAction</code> this 
	 * <code>IResourcePermission</code> encapsulates
	 * @return the action 
	 */ 
	public IAction getAction();
	
	/**
	 * A <code>IResourcePermission</code> implies another
	 * <code>IResourcePermission</code> if the <code>
	 * IAction</code> it encapsulates matches the <code>
	 * IAction</code> encapsulated by the other permission,
	 * and the <code>IDomainResource</code> it points to
	 * <code>implies</code> the other <code>IResourcePermission
	 * </code> resource.
	 * 
	 * @see IDomainResource#implies(IDomainResource)
	 * @return permit 
	 */ 
	public Permit implies(IResourcePermission permission);
	
	public PermissionType getPermissionType();
	
	void setPermissionType(PermissionType permissionType);
}
