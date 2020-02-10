/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.FunctionsCatalogAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author aathalye
 *
 */
public class FunctionCatalogResourcePermission extends ResourcePermission {
	
	private static final long serialVersionUID = 1316371989L;
	
	public FunctionCatalogResourcePermission(final IDomainResource resource) {
		this(resource, new FunctionsCatalogAction(FunctionsCatalogAction.NONE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.ResourcePermission#implies(com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission)
	 */

	public Permit implies(IResourcePermission permission) {
		if (!(permission instanceof FunctionCatalogResourcePermission)) {
			return Permit.DENY;
		}
		// get Actions
		FunctionsCatalogAction thisAction = (FunctionsCatalogAction) this.action;
		FunctionsCatalogAction otherAction = (FunctionsCatalogAction) permission.getAction();
		if (thisAction.implies(otherAction)) {
			return Permit.ALLOW;
		}
		return Permit.DENY;
	}

	public FunctionCatalogResourcePermission(final IDomainResource resource,
			final FunctionsCatalogAction action) {
		this(resource, action, PermissionType.BERESOURCE);
	}

	public FunctionCatalogResourcePermission(final IDomainResource resource,
			                                 final FunctionsCatalogAction action,
			                                 final PermissionType permissionType) {
		this.resource = resource;
		if (!(action instanceof FunctionsCatalogAction)) {
			throw new IllegalArgumentException(
					"Illegal Action type for this permission");
		}
		this.action = action;
		this.permissionType = permissionType;
		if(!validateResourcePath(resource)) {
			throw new IllegalArgumentException(
				"Illegal name for this type of resource");
		}
	}
	
	private boolean validateResourcePath(final IDomainResource dr) {
		if (dr == null) {
			throw new IllegalArgumentException("Domain resources cannot be null");
		}
		String drName = dr.getName();
		if (drName == null) {
			//This means it is a template resource
			return true;
		}
		if (!(drName.contains("/"))) {
			return true;
		}
		return false;
	}
}
