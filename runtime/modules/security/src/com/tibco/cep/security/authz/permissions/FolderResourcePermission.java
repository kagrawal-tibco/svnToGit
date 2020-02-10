/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.FolderAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author aathalye
 *
 */
public class FolderResourcePermission extends ResourcePermission {
	
	private static final long serialVersionUID = 2416371989L;

	public FolderResourcePermission(final IDomainResource resource) {
		this(resource, new FolderAction(FolderAction.NONE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.ResourcePermission#implies(com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission)
	 */

	public Permit implies(IResourcePermission permission) {
		if (!(permission instanceof FolderResourcePermission)) {
			return Permit.DENY;
		}
		// get Actions
		FolderAction thisAction = (FolderAction) this.action;
		FolderAction otherAction = (FolderAction) permission.getAction();
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}

	public FolderResourcePermission(final IDomainResource resource,
			                 final FolderAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public FolderResourcePermission(final IDomainResource resource,
			                 final FolderAction action,
			                 final PermissionType permissionType) {
		this.resource = resource;
		if (!(action instanceof FolderAction)) {
			throw new IllegalArgumentException(
					"Illegal Action type for this permission");
		}
		this.action = action;
		this.permissionType = permissionType;
	}

}
