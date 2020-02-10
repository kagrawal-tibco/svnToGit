/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.PERMISSION_ELEMENT;
import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.RESOURCEREF_ATTR;

import java.util.Stack;

import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * @author aathalye
 *
 */
@SuppressWarnings("serial")
public abstract class ResourcePermission implements IResourcePermission {
	
	protected IDomainResource resource;
	
	//TODO remove this
	//private DomainResourceCollection resources;
	
	protected IAction action;
	
	protected PermissionType permissionType;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission#getActions()
	 */
	
	public IAction getAction() {
		return this.action;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission#getResource()
	 */
	
	public IDomainResource getResource() {
		return this.resource;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission#implies(com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission)
	 */
	
	
	public void setResource(IDomainResource resource) {
		this.resource = resource;
	}

	public abstract Permit implies(IResourcePermission permission);
	
	@Deprecated
	protected IDomainResource getResource(IDomainResource resource,
			                              IResourcePermission permission) {
		IDomainResource otherResource = 
					permission.getResource();
		//TODO check for template resource
		Stack<IDomainResource> resourceStack = new Stack<IDomainResource>();
		//Push the root on the stack
		resourceStack.push(resource);
		while (!resourceStack.empty()) {
			IDomainResource top = resourceStack.peek();
			resourceStack.pop();
			top.setVisited(true);
			
			//Check the terminating condition
			if (!top.hasChildren()) {
				if (top.implies(otherResource)) {
					return top;
				}
			}
			//Add all children to stack
			resourceStack.addAll(top.getChildren());
			top.setVisited(false);
		}
		return null;
	}

	public PermissionType getPermissionType() {
		return permissionType;
	}
	
	public void setPermissionType(PermissionType permissionType) {
		this.permissionType = permissionType;
	}
	
	/*@Override
	public boolean equals(Object o) {
		
	}*/
	@Override
	public String toString() {
		return "["+ resource.toString()
		        + ":" + action.toString()
		        + ":" + permissionType.toString()
		        + "]";
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.core.SerializableObject#serialize(com.tibco.xml.datamodel.XiFactory, com.tibco.xml.datamodel.XiNode)
	 */
	@Override
	public void serialize(XiFactory factory, XiNode rootNode) {
		//Create a <permission> element
		XiNode permissionNode = factory.createElement(PERMISSION_ELEMENT);
		//Serialize attribute
		String resourceRef = "#" + resource.getId();
		XiNode resourceRefAttr = factory.createAttribute(RESOURCEREF_ATTR, resourceRef);
		permissionNode.appendChild(resourceRefAttr);
		
		//Serialize action
		action.serialize(factory, permissionNode);
		//Append to rootnode
		rootNode.appendChild(permissionNode);
	}
}
