/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.DomainAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author aathalye
 *
 */
public class DomainResourcePermission extends ResourcePermission {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5240275257369288930L;

	public DomainResourcePermission(final IDomainResource resource) {
		this(resource, new DomainAction(DomainAction.NONE));
	}

	public DomainResourcePermission(final IDomainResource resource,
			                        final DomainAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public DomainResourcePermission(final IDomainResource resource,
			                        final DomainAction action,
			                        final PermissionType permissionType) {
		this.resource = resource;
		if (!(action instanceof DomainAction)) {
			throw new IllegalArgumentException(
					"Illegal Action type for this permission");
		}
		this.action = action;
		this.permissionType = permissionType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.ResourcePermission#implies(com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission)
	 */
	@Override
	public Permit implies(IResourcePermission permission) {
		if (!(permission instanceof DomainResourcePermission)) {
			return Permit.DENY;
		}
		
		// get Actions
		DomainAction thisAction = (DomainAction) this.action;
		DomainAction otherAction = (DomainAction) permission.getAction();
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}
}
