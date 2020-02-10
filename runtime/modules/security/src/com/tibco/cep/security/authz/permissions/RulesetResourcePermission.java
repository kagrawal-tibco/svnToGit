/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.permissions.actions.RulesetAction;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author aathalye
 *
 */
public class RulesetResourcePermission extends ResourcePermission {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3713839814457566833L;

	public RulesetResourcePermission(final IDomainResource resource) {
		this(resource, new RulesetAction(RulesetAction.NONE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.ResourcePermission#implies(com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission)
	 */

	public Permit implies(IResourcePermission permission) {
		if (!(permission instanceof RulesetResourcePermission)) {
			return Permit.DENY;
		}
		// get Actions
		RulesetAction thisAction = (RulesetAction) this.action;
		RulesetAction otherAction = (RulesetAction) permission.getAction();
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}

	public RulesetResourcePermission(final IDomainResource resource,
			                 final RulesetAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public RulesetResourcePermission(final IDomainResource resource,
			                 final RulesetAction action,
			                 final PermissionType permissionType) {
		this.resource = resource;
		if (!(action instanceof RulesetAction)) {
			throw new IllegalArgumentException(
					"Illegal Action type for this permission");
		}
		this.action = action;
		this.permissionType = permissionType;
	}

}
