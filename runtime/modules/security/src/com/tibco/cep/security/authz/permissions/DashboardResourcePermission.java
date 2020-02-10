/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.DashboardResourceAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author Vikram Patil
 */
public class DashboardResourcePermission extends ResourcePermission {
	
	public DashboardResourcePermission(final IDomainResource resource) {
		this(resource, new DashboardResourceAction(DashboardResourceAction.NONE));
	}
	
	public DashboardResourcePermission(final IDomainResource resource,
			final DashboardResourceAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public DashboardResourcePermission(final IDomainResource resource,
			final DashboardResourceAction action,
			final PermissionType permissionType) {

		if (!(action instanceof DashboardResourceAction)) {
			throw new IllegalArgumentException(
					"Illegal Action type for this permission");
		}
		
		this.resource = resource;
		this.action = action;
		this.permissionType = permissionType;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.permissions.ResourcePermission#implies(com.tibco.cep.security.authz.permissions.IResourcePermission)
	 */
	@Override
	public Permit implies(IResourcePermission permission) {
		if (!(permission instanceof DashboardResourcePermission)) {
			return Permit.DENY;
		}
		// get Actions
		DashboardResourceAction thisAction = (DashboardResourceAction) this.action;
		DashboardResourceAction otherAction = (DashboardResourceAction) permission.getAction();
		 
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}

}
