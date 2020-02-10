package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.permissions.actions.RuleTemplateAction;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author vdhumal
 * Resource Permission for RuleTemplate 
 */
public class RuleTemplateResourcePermission extends ResourcePermission {

	private static final long serialVersionUID = -5455611830375854846L;

	public RuleTemplateResourcePermission(final IDomainResource resource) {
		this(resource, new RuleTemplateAction(RuleTemplateAction.NONE));
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.permissions.ResourcePermission#implies(com.tibco.cep.security.authz.permissions.IResourcePermission)
	 */
	@Override
	public Permit implies(IResourcePermission permission) {
		if (!(permission instanceof RuleTemplateResourcePermission)) {
			return Permit.DENY;
		}
		// get Actions
		RuleTemplateAction thisAction = (RuleTemplateAction) this.action;
		RuleTemplateAction otherAction = (RuleTemplateAction) permission.getAction();
		 
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}
	
	public RuleTemplateResourcePermission(final IDomainResource resource,
			                      final RuleTemplateAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public RuleTemplateResourcePermission(final IDomainResource resource,
			                      final RuleTemplateAction action,
			                      final PermissionType permissionType) {
		this.resource = resource;
		if (!(action instanceof RuleTemplateAction)) {
			throw new IllegalArgumentException(
					"Illegal Action type for this permission");
		}
		this.action = action;
		this.permissionType = permissionType;
	}

	
}

