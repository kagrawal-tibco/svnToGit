package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.WSDLAction;
import com.tibco.cep.security.authz.utils.PermissionType;
/**
 * 
 * @author smarathe
 *
 */
public class WSDLResourcePermission extends SharedResourcePermission<WSDLAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3623241716801579704L;

	public WSDLResourcePermission(final IDomainResource resource) {
		this(resource, new WSDLAction(WSDLAction.NONE));
	}

	public WSDLResourcePermission(final IDomainResource resource,
			                                          final WSDLAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public WSDLResourcePermission(final IDomainResource resource,
			                                           final WSDLAction action,
			                                           final PermissionType permissionType) {
		super(resource, action, permissionType);
	}
}
