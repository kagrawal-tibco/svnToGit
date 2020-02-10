/**
 * 
 */
package com.tibco.cep.security.authz.domain;

import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.ID_ATTR;
import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.NAME_ATTR;
import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.RESOURCE_ELEMENT;
import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.TYPE_ATTR;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * @author aathalye
 * 
 */
public class DomainResource implements IDomainResource {
	
	private static final long serialVersionUID = -8042245606431981051L;

	protected String resourceName;

	protected String id;

	protected ResourceType resourceType;

	protected IDomainResource parent;

	protected List<IDomainResource> children;

	protected boolean visited;

	/**
	 * A constructor intended to be used by clients of this ACL system
	 * 
	 * @param resourceName
	 * @param resourceType
	 */
	public DomainResource(final String resourceName,
			final ResourceType resourceType) {
		this.resourceName = resourceName;
		if (resourceType == null) {
			throw new IllegalArgumentException("Resource type cannot be null");
		}
		this.resourceType = resourceType;
		this.children = new ArrayList<IDomainResource>();
	}

	public DomainResource(final String resourceName, final String id,
			final ResourceType resourceType) {
		this(resourceName, id, resourceType, null);
	}

	public DomainResource(final String resourceName, final String id,
			final ResourceType resourceType, final IDomainResource parent) {

		this.resourceName = resourceName;
		this.id = id;
		this.resourceType = resourceType;
		this.parent = parent;
		if (parent != null)
			parent.addChild(this);
		this.children = new ArrayList<IDomainResource>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.domain.IDomainResource#getId()
	 */

	public String getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.domain.IDomainResource#getName()
	 */

	public String getName() {
		return this.resourceName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.domain.IDomainResource#getParent()
	 */

	public IDomainResource getParent() {
		return this.parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.domain.IDomainResource#getType()
	 */

	public ResourceType getType() {
		return this.resourceType;
	}

	@Override
	public boolean equals(final Object o) {
		/*if (o == null) {
			if (this.parent == null) {
				return true;
			}
		}*/
		if (!(o instanceof DomainResource)) {
			return false;
		}
		if (this == o) {
			return true;
		}
		DomainResource other = (DomainResource) o;
		boolean b1 = (id == other.getId()) || (id.equals(other.getId()));
		boolean b2 = this.resourceType.equals(other.getType());
		boolean b3 = (resourceName == other.getName()) ||
						resourceName.equals(other.getName());
		return b1 && b2 && b3;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.domain.IDomainResource#matchesId(com.tibco.cep.projectexplorer.cache.security.authz.domain.IDomainResource)
	 */
	public boolean matchesId(IDomainResource other) {
		if (this.id.equals(other.getId())) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.domain.IDomainResource#implies(com.tibco.cep.projectexplorer.cache.security.authz.domain.IDomainResource)
	 */

	public boolean implies(IDomainResource target) {
		boolean found = false;
		IDomainResource dr = this;
		String targetName = target.getName();
		if (dr != null) {
			// get its name
			String drname = dr.getName();
			if (drname == null) {
				return false;
			}
			if (!(this.resourceType.equals(target.getType()))) {
				return false;
			}
			if (drname.endsWith("*")) {
				// This has wildcard
				// Check to see that targetname length is greater than
				// drname length, so that /Concepts/* should not imply
				// /Concepts
				if (targetName.length() > drname.length()) {
					int offsetLength = drname.length() - 1;
					if (targetName.regionMatches(0, drname, 0, offsetLength)) {
						// we found this resource
						found = true;
					}
				}
			} else {
				// Now check simple equality
				if (targetName.intern() == drname.intern()) {
					found = true;
				}
			}
		}
		return found;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(final IDomainResource o) {
		if (o == null) {
			throw new IllegalArgumentException(
					"The collection cannot contain null values");
		}
		String name1 = this.resourceName;
		String name2 = o.getName();
		if (name1 == null) {
			name1 = "";
		}
		if (name2 == null) {
			name2 = "";
		}
		if (name1.length() < name2.length()) {
			// Return -1
			return 1;
		} else if (name1.length() == name2.length()) {
			return 0;
		}
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.security.authz.domain.IDomainResource#addChild(com.tibco.cep.security.authz.domain.IDomainResource)
	 */
	public boolean addChild(IDomainResource child) {
		if (!(children.contains(child))) {
			child.setParent(this);
			return children.add(child);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.security.authz.domain.IDomainResource#setParent(com.tibco.cep.security.authz.domain.IDomainResource)
	 */
	public void setParent(IDomainResource parent) {
		this.parent = parent;
		
	}

	/**
	 * @return the visited
	 */
	public final boolean isVisited() {
		return visited;
	}

	/**
	 * @param visited
	 *            the visited to set
	 */
	public final void setVisited(boolean visited) {
		this.visited = visited;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.security.authz.domain.IDomainResource#hasChildren()
	 */
	public boolean hasChildren() {
		return children.size() > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.security.authz.domain.IDomainResource#getChildren()
	 */
	public List<IDomainResource> getChildren() {
		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return resourceName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (resourceName != null) {
			hashCode = hashCode ^ resourceName.hashCode();
		}
		if (id != null) {
			hashCode = hashCode ^ id.hashCode();
		}
		if (resourceType != null) {
			hashCode = resourceType.name().hashCode();
		}
		//Commented to avoid ConcurrentModificationException
		/*if (children != null) {
			hashCode = children..hashCode();
		}*/
		return hashCode;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.core.SerializableObject#serialize(com.tibco.xml.datamodel.XiFactory, com.tibco.xml.datamodel.XiNode)
	 */
	@Override
	public void serialize(XiFactory factory, XiNode rootNode) {
		//Create <resource> element
		XiNode resourceNode = factory.createElement(RESOURCE_ELEMENT);
		//Serialize attrs
		XiNode idAttribute = factory.createAttribute(ID_ATTR, id);
		resourceNode.appendChild(idAttribute);
		
		if (resourceName != null) {
			XiNode nameAttribute = factory.createAttribute(NAME_ATTR, resourceName);
			resourceNode.appendChild(nameAttribute);
		}
		
		XiNode resourceTypeAttribute = factory.createAttribute(TYPE_ATTR, resourceType.name());
		resourceNode.appendChild(resourceTypeAttribute);
		
		//Append to root
		rootNode.appendChild(resourceNode);
	}
}
