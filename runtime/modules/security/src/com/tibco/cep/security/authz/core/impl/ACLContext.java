/**
 * 
 */
package com.tibco.cep.security.authz.core.impl;

import java.security.Principal;
import java.util.List;

import com.tibco.cep.security.authz.core.ACLEntry;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.DefaultResourcePermissionFactory;
import com.tibco.cep.security.authz.permissions.IResourcePermission;
import com.tibco.cep.security.authz.permissions.PermissionsCollection;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;
import com.tibco.cep.security.tokens.Role;

/**
 * @author aathalye
 * 
 */
public class ACLContext {

	// This can be user, or a role based on
	// user-centric/role-centric ACL.
	private Principal principal;

	private List<ACLEntry> aclEntries;

	private IDomainResource resource;

	private IAction action;

	private PermissionType permissionType;

	protected ACLContext() {
	}

	protected ACLContext(final Principal p, final List<ACLEntry> aclEntries,
			final IDomainResource resource, final IAction action) {
		if (p == null || aclEntries == null || resource == null
				|| action == null) {
			throw new IllegalArgumentException("Invalid argument");
		}
		this.principal = p;
		this.aclEntries = aclEntries;
		this.resource = resource;
		this.action = action;
		this.permissionType = PermissionType.BERESOURCE;
	}

	protected ACLContext(final Principal p, final List<ACLEntry> aclEntries,
			final IDomainResource resource, 
			final IAction action,
			final PermissionType permissionType) {
		if (p == null || aclEntries == null || resource == null
				|| action == null) {
			throw new IllegalArgumentException("Invalid argument");
		}
		this.principal = p;
		this.aclEntries = aclEntries;
		this.resource = resource;
		this.action = action;
		this.permissionType = permissionType;
	}

	/**
	 * @return the principal
	 */
	public final Principal getPrincipal() {
		return principal;
	}

	/**
	 * @return the resource
	 */
	protected final IDomainResource getRequestedResource() {
		return resource;
	}

	/**
	 * @return the action
	 */
	protected final IAction getRequestedAction() {
		return action;
	}

	/**
	 * @param principal
	 *            the principal to set
	 */
	public final void setPrincipal(Principal principal) {
		this.principal = principal;
	}

	/**
	 * @param aclEntries
	 *            the aclEntries to set
	 */
	public final void setAclEntries(List<ACLEntry> aclEntries) {
		this.aclEntries = aclEntries;
	}

	/**
	 * @param resource
	 *            the resource to set
	 */
	public final void setResource(IDomainResource resource) {
		this.resource = resource;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public final void setAction(IAction action) {
		this.action = action;
	}

	private ACLEntry getMatchingACLEntry(final Role role) {
		// TODO optimization here
		for (ACLEntry aclEntry : aclEntries) {
			if (aclEntry.getRole().isSemanticallyEquivalent(role)) {
				return aclEntry;
			}
		}
		return null;
	}

	protected Permit checkPermission() throws Exception {
		// Get matching ACLEntry for this role
		if (!(principal instanceof Role)) {
			return null;
		}
		ACLEntry aclEntry = getMatchingACLEntry((Role) principal);
		IResourcePermission requestedPermission = DefaultResourcePermissionFactory
				.newInstance().getPermission(resource, action);
		requestedPermission.setPermissionType(permissionType);
		// get the permission collection for this entry
		if (aclEntry != null) {
			PermissionsCollection collection = aclEntry.getPermissions();
			return collection.checkPermission(requestedPermission);
		} 
		return Permit.DENY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof ACLContext)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ACLContext ctx = (ACLContext) obj;
		if (!(this.principal.equals(ctx.getPrincipal()))) {
			return false;
		}
		if (!(this.resource.equals(ctx.getRequestedResource()))) {
			return false;
		}
		if (!(this.action.equals(ctx.getRequestedAction()))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hashCode = principal.hashCode() ^ resource.hashCode() ^ action.hashCode();
		return hashCode;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[Resource->");
		sb.append(resource.getName());
		sb.append("]");
		sb.append("[Prinicpal->");
		sb.append(principal.getName());
		sb.append("]");
		sb.append("[Action->");
		sb.append(action.toString());
		return sb.toString();
	}
}
