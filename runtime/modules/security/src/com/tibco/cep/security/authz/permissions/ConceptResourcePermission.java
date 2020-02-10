/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.ConceptAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author aathalye
 * 
 */
public class ConceptResourcePermission extends ResourcePermission {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5240275257369288930L;

	public ConceptResourcePermission(final IDomainResource resource) {
		this(resource, new ConceptAction(ConceptAction.NONE));
	}

	public ConceptResourcePermission(final IDomainResource resource,
			final ConceptAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public ConceptResourcePermission(final IDomainResource resource,
			                         final ConceptAction action,
			                         final PermissionType permissionType) {
		this.resource = resource;
		if (!(action instanceof ConceptAction)) {
			throw new IllegalArgumentException(
					"Illegal Action type for this permission");
		}
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
		if (!(permission instanceof ConceptResourcePermission)) {
			return Permit.DENY;
		}
		
		// get Actions
		ConceptAction thisAction = (ConceptAction) this.action;
		ConceptAction otherAction = (ConceptAction) permission.getAction();
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}
}
