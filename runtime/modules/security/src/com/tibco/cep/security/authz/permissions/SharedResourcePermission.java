package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.permissions.actions.SharedResourceAction;
import com.tibco.cep.security.authz.utils.PermissionType;


/**
 * 
 * @author smarathe
 *
 */
public class SharedResourcePermission<A extends SharedResourceAction> extends ResourcePermission {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4027955330543510996L;

	public SharedResourcePermission(final IDomainResource resource,
			                                            final A action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public SharedResourcePermission(final IDomainResource resource,
			                                            final A action,
			                                            final PermissionType permissionType) {
		this.resource = resource;
		this.action = action;
		this.permissionType = permissionType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.ResourcePermission#implies(com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Permit implies(IResourcePermission permission) {
		if (!(permission instanceof SharedResourcePermission)) {
			return Permit.DENY;
		}
		
		// get Actions
		A thisAction = (A)this.action;
		A otherAction = (A) permission.getAction();
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}
}
