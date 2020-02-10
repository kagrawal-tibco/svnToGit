package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.CDDAction;
import com.tibco.cep.security.authz.utils.PermissionType;
/**
 * 
 * @author yrajput
 *
 */
public class CDDResourcePermission extends SharedResourcePermission<CDDAction> {

	private static final long serialVersionUID = -3670953959071872422L;

	public CDDResourcePermission(final IDomainResource resource) {
		this(resource, new CDDAction(CDDAction.NONE));
	}

	public CDDResourcePermission(final IDomainResource resource,
			                                        final CDDAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public CDDResourcePermission(final IDomainResource resource,
			                                        final CDDAction action,
			                                        final PermissionType permissionType) {
		super(resource, action, permissionType);
	}
}
