/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.permissions.actions.PropertyAction;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author aathalye
 *
 */
public class PropertyResourcePermission extends ResourcePermission {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 777298079097134961L;

	public PropertyResourcePermission(final IDomainResource resource) {
		this(resource, new PropertyAction(PropertyAction.NONE));
	}

	public PropertyResourcePermission(final IDomainResource resource,
									  final PropertyAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public PropertyResourcePermission(final IDomainResource resource,
			                          final PropertyAction action,
			                          final PermissionType permissionType) {
		this.resource = resource;
		if (!(action instanceof PropertyAction)) {
			throw new IllegalArgumentException(
					"Illegal Action type for this permission");
		}
		this.action = action;
		this.permissionType = permissionType;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.permissions.ResourcePermission#implies(com.tibco.cep.security.authz.permissions.IResourcePermission)
	 */
	@Override
	public Permit implies(IResourcePermission permission) {
		if (!(permission instanceof PropertyResourcePermission)) {
			return Permit.DENY;
		}
		//TODO Put in check for read permission of parent resource
		PropertyAction thisAction = (PropertyAction) this.action;
		PropertyAction otherAction = (PropertyAction) permission.getAction();
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}
}
