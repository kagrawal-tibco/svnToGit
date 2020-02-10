package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.RuleTemplateViewAction;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author vdhumal
 * Resource Permission for RuleTemplateView 
 */
public class RuleTemplateViewResourcePermission extends RuleTemplateResourcePermission {

	private static final long serialVersionUID = 7026427385665068586L;

	public RuleTemplateViewResourcePermission(final IDomainResource resource) {
		this(resource, new RuleTemplateViewAction(RuleTemplateViewAction.NONE));
	}

	public RuleTemplateViewResourcePermission(final IDomainResource resource, final RuleTemplateViewAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public RuleTemplateViewResourcePermission(final IDomainResource resource, final RuleTemplateViewAction action,
			final PermissionType permissionType) {
		super(resource, action, permissionType);
	}

}
