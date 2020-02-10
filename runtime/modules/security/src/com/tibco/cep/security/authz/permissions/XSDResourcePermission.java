package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.XSDAction;
import com.tibco.cep.security.authz.utils.PermissionType;
/**
 * 
 * @author smarathe
 *
 */
public class XSDResourcePermission extends SharedResourcePermission<XSDAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3393667392991060164L;

	public XSDResourcePermission(final IDomainResource resource) {
		this(resource, new XSDAction(XSDAction.NONE));
	}

	public XSDResourcePermission(final IDomainResource resource,
			                                        final XSDAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public XSDResourcePermission(final IDomainResource resource,
			                                        final XSDAction action,
			                                        final PermissionType permissionType) {
		super(resource, action, permissionType);
	}
}
