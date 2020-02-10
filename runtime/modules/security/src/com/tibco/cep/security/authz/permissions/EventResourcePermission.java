/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.EventAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author aathalye
 * 
 */
public class EventResourcePermission extends ResourcePermission {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8908712373371608548L;

	//private static final long serialVersionUID = 1316371980L;
	

	public EventResourcePermission(final IDomainResource resource) {
		this(resource, new EventAction(EventAction.NONE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.ResourcePermission#implies(com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission)
	 */

	public Permit implies(IResourcePermission permission) {
		if (!(permission instanceof EventResourcePermission)) {
			return Permit.DENY;
		}
		// get Actions
		EventAction thisAction = (EventAction) this.action;
		EventAction otherAction = (EventAction) permission.getAction();
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}

	public EventResourcePermission(final IDomainResource resource,
			final EventAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public EventResourcePermission(final IDomainResource resource,
			                       final EventAction action,
			                       final PermissionType permissionType) {
		this.resource = resource;
		if (!(action instanceof EventAction)) {
			throw new IllegalArgumentException(
					"Illegal Action type for this permission");
		}
		this.action = action;
		this.permissionType = permissionType;
	}
}
