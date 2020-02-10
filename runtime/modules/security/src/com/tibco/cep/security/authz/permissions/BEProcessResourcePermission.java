/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.BEProcessAction;
import com.tibco.cep.security.authz.permissions.actions.EventAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author vpatil
 *
 */
public class BEProcessResourcePermission extends ResourcePermission {

	public BEProcessResourcePermission(final IDomainResource resource) {
		this(resource, new BEProcessAction(EventAction.NONE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.ResourcePermission#implies(com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission)
	 */
	public Permit implies(IResourcePermission permission) {
		if (!(permission instanceof BEProcessResourcePermission)) {
			return Permit.DENY;
		}
		// get Actions
		BEProcessAction thisAction = (BEProcessAction) this.action;
		BEProcessAction otherAction = (BEProcessAction) permission.getAction();
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}

	public BEProcessResourcePermission(final IDomainResource resource,
			final BEProcessAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public BEProcessResourcePermission(final IDomainResource resource,
			                       final BEProcessAction action,
			                       final PermissionType permissionType) {
		this.resource = resource;
		if (!(action instanceof BEProcessAction)) {
			throw new IllegalArgumentException(
					"Illegal Action type for this permission");
		}
		this.action = action;
		this.permissionType = permissionType;
	}
}
