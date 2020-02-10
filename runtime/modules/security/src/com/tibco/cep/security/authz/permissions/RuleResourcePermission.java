/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.permissions.actions.RuleAction;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author aathalye
 *
 */
public class RuleResourcePermission extends ResourcePermission {

	private static final long serialVersionUID = 123456789L;
	
	public RuleResourcePermission(final IDomainResource resource) {
		this(resource, new RuleAction(RuleAction.NONE));
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.permissions.ResourcePermission#implies(com.tibco.cep.security.authz.permissions.IResourcePermission)
	 */
	@Override
	public Permit implies(IResourcePermission permission) {
		if (!(permission instanceof RuleResourcePermission)) {
			return Permit.DENY;
		}
		// get Actions
		RuleAction thisAction = (RuleAction) this.action;
		RuleAction otherAction = (RuleAction) permission.getAction();
		 
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}
	
	public RuleResourcePermission(final IDomainResource resource,
			                      final RuleAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public RuleResourcePermission(final IDomainResource resource,
			                      final RuleAction action,
			                      final PermissionType permissionType) {
		this.resource = resource;
		if (!(action instanceof RuleAction)) {
			throw new IllegalArgumentException(
					"Illegal Action type for this permission");
		}
		this.action = action;
		this.permissionType = permissionType;
	}

}
