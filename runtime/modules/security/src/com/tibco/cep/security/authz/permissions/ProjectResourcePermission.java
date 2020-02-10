/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.permissions.actions.ProjectAction;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author aathalye
 *
 */
public class ProjectResourcePermission extends ResourcePermission {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8262000326035318772L;

	public ProjectResourcePermission(final IDomainResource resource) {
		this(resource, new ProjectAction(ProjectAction.NONE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.ResourcePermission#implies(com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission)
	 */

	public Permit implies(IResourcePermission permission) {
		if (!(permission instanceof ProjectResourcePermission)) {
			return Permit.DENY;
		}
		// get Actions
		ProjectAction thisAction = (ProjectAction) this.action;
		ProjectAction otherAction = (ProjectAction) permission.getAction();
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}

	public ProjectResourcePermission(final IDomainResource resource,
			                         final ProjectAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public ProjectResourcePermission(final IDomainResource resource,
			                         final ProjectAction action,
			                         final PermissionType permissionType) {
		this.resource = resource;
		if (!(action instanceof ProjectAction)) {
			throw new IllegalArgumentException(
					"Illegal Action type for this permission");
		}
		this.action = action;
		this.permissionType = permissionType;
	}

}
