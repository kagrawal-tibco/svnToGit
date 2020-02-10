/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.permissions.actions.RuleFunctionAction;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author aathalye
 *
 */
public class RuleFunctionResourcePermission extends ResourcePermission {
	
	private static final long serialVersionUID = 12345678L;
	
	public RuleFunctionResourcePermission(final IDomainResource resource) {
		this(resource, new RuleFunctionAction(RuleFunctionAction.NONE));
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.permissions.ResourcePermission#implies(com.tibco.cep.security.authz.permissions.IResourcePermission)
	 */
	@Override
	public Permit implies(IResourcePermission permission) {
		if (!(permission instanceof RuleFunctionResourcePermission)) {
			return Permit.DENY;
		}
		// get Actions
		RuleFunctionAction thisAction = (RuleFunctionAction) this.action;
		RuleFunctionAction otherAction = (RuleFunctionAction) permission.getAction();
		//TODO The behaviour will be slightly different for Virtual rule functions 
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}
	
	public RuleFunctionResourcePermission(final IDomainResource resource,
			                              final RuleFunctionAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public RuleFunctionResourcePermission(final IDomainResource resource,
			                       		  final RuleFunctionAction action,
			                              final PermissionType permissionType) {
		this.resource = resource;
		if (!(action instanceof RuleFunctionAction)) {
			throw new IllegalArgumentException(
					"Illegal Action type for this permission");
		}
		this.action = action;
		this.permissionType = permissionType;
	}

}
