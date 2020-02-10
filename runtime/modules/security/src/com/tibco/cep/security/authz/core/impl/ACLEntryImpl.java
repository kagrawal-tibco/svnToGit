/**
 * 
 */
package com.tibco.cep.security.authz.core.impl;

import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.ENTRY_ELEMENT;
import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.NAME_ATTR;
import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.ROLE_ELEMENT;

import com.tibco.cep.security.authz.core.ACLEntry;
import com.tibco.cep.security.authz.permissions.IResourcePermission;
import com.tibco.cep.security.authz.permissions.PermissionsCollection;
import com.tibco.cep.security.tokens.Role;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * @author aathalye
 *
 */
public class ACLEntryImpl implements ACLEntry {
	
	private static final long serialVersionUID = 284287429874L;
	
	private Role role;
	
	private PermissionsCollection permissions;
	
	public ACLEntryImpl() {
		this(null, null);
	}
	
	public ACLEntryImpl(final Role role) {
		this(role, new PermissionsCollection());
	}
	
	public ACLEntryImpl(final Role role, 
			            final PermissionsCollection permissions) {
		this.role = role;
		this.permissions = permissions;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACLEntry#addPermission(com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission)
	 */
	
	public boolean addPermission(IResourcePermission permission) {
		return permissions.addPermission(permission);
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACLEntry#getPermissions()
	 */
	
	public PermissionsCollection getPermissions() {
		// TODO Auto-generated method stub
		return permissions;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACLEntry#getRole()
	 */
	
	public Role getRole() {
		return role;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACLEntry#removePermission(com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission)
	 */
	
	public boolean removePermission(IResourcePermission permission) {
		return permissions.removePermission(permission);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACLEntry#setRole(com.tibco.cep.projectexplorer.cache.security.authz.core.Role)
	 */
	
	public void setRole(Role role) {
		this.role = role;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.core.ACLEntry#setPermissions(com.tibco.cep.security.authz.permissions.PermissionsCollection)
	 */
	public void setPermissions(PermissionsCollection permissionsCollection) {
		this.permissions = permissionsCollection;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.core.SerializableObject#serialize(com.tibco.xml.datamodel.XiFactory, com.tibco.xml.datamodel.XiNode)
	 */
	@Override
	public void serialize(XiFactory factory, XiNode rootNode) {
		//Create a <entry> element
		XiNode aclEntryNode = factory.createElement(ENTRY_ELEMENT);
		//Serialize role
		XiNode roleNode = factory.createElement(ROLE_ELEMENT);
		//serialize role name
		XiNode roleNameAttribute = factory.createAttribute(NAME_ATTR, role.getName());
		roleNode.appendChild(roleNameAttribute);
		//Append it to root entry node
		aclEntryNode.appendChild(roleNode);
		
		//Serialize permissions for this entry
		permissions.serialize(factory, aclEntryNode);
		
		//Append to entries node
		rootNode.appendChild(aclEntryNode);
	}
}
