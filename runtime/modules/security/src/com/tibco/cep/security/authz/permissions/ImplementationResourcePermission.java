/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.AbstractImplementationAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author aathalye
 *
 */
public class ImplementationResourcePermission extends ResourcePermission {

	private static final long serialVersionUID = 894872847487L;

	public ImplementationResourcePermission(final IDomainResource resource) {
		this(resource, new AbstractImplementationAction(AbstractImplementationAction.NONE));
	}

	public ImplementationResourcePermission(final IDomainResource resource,
			                                final AbstractImplementationAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public ImplementationResourcePermission(final IDomainResource resource,
			                                final AbstractImplementationAction action,
			                                final PermissionType permissionType) {
		this.resource = resource;
		if (!(action instanceof AbstractImplementationAction)) {
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
		if (!(permission instanceof ImplementationResourcePermission)) {
			return Permit.DENY;
		}
		
		// get Actions
		AbstractImplementationAction thisAction = (AbstractImplementationAction) this.action;
		AbstractImplementationAction otherAction = (AbstractImplementationAction) permission.getAction();
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}

}
