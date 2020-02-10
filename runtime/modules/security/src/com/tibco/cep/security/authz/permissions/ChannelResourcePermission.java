/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.ChannelResourceAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author aathalye
 *
 */
public class ChannelResourcePermission extends ResourcePermission {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5240275257369288930L;

	public ChannelResourcePermission(final IDomainResource resource) {
		this(resource, new ChannelResourceAction(ChannelResourceAction.NONE));
	}

	public ChannelResourcePermission(final IDomainResource resource,
			                         final ChannelResourceAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public ChannelResourcePermission(final IDomainResource resource,
			                         final ChannelResourceAction action,
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
	public Permit implies(IResourcePermission permission) {
		if (!(permission instanceof ChannelResourcePermission)) {
			return Permit.DENY;
		}
		
		// get Actions
		ChannelResourceAction thisAction = (ChannelResourceAction) this.action;
		ChannelResourceAction otherAction = (ChannelResourceAction) permission.getAction();
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}

}
