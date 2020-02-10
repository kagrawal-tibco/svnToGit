package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.RuleTemplateInstanceAction;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author vdhumal
 * Resource Permission for RuleTemplateInstance
 */
public class RuleTemplateInstanceResourcePermission extends RuleTemplateResourcePermission {

	private static final long serialVersionUID = -9078591109751757524L;

	public RuleTemplateInstanceResourcePermission(final IDomainResource resource) {
		this(resource, new RuleTemplateInstanceAction(RuleTemplateInstanceAction.NONE));
	}

	public RuleTemplateInstanceResourcePermission(final IDomainResource resource, final RuleTemplateInstanceAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public RuleTemplateInstanceResourcePermission(final IDomainResource resource, final RuleTemplateInstanceAction action,
			final PermissionType permissionType) {
		super(resource, action, permissionType);
	}
	
}
