/**
 * 
 */
package com.tibco.cep.security.authz.domain;

import java.io.Serializable;
import java.util.List;

import com.tibco.cep.security.authz.core.SerializableObject;
import com.tibco.cep.security.authz.utils.ResourceType;

/**
 * The interface to represent each <b>resource</b>
 * which can come under the purview of the <b>ACL</b>
 * system.
 * @author aathalye
 *
 */
public interface IDomainResource extends Comparable<IDomainResource>, Serializable, SerializableObject {
	
	/**
	 * Return the fully qualified name/path of this resource
	 * as in the TIBCO designer tool
	 * @return name
	 */
	public String getName();
	
	/**
	 * Return the <i>Id</i> attribute value of this 
	 * resource which can be used for referencing
	 * @return id
	 */
	public String getId();
	
	/**
	 * Return the type of this resource
	 * @see ResourceType
	 * @return resourceType
	 */
	public ResourceType getType();
	
	/**
	 * Return the optional parent of this resource.
	 * A Rule/DT can have a parent as ruleset, while
	 * attribute/property can parent as Concept/Event.
	 * @return parent
	 */
	public IDomainResource getParent();
	
	/**
	 * Sets the parent for this resource
	 * @param parent
	 */
	public void setParent(IDomainResource parent);
	
	/**
	 * A method to compare 2 resources only based on their id
	 * @param other
	 * @return true|false
	 */
	public boolean matchesId(IDomainResource other);
	
	/**
	 * A method to check is requested resource implies this 
	 * resource. 
	 * <p>
	 * e.g: /Concept/* implies /Concept/Account
	 * </p>
	 * @param other
	 * @return true|false
	 */
	public boolean implies(IDomainResource other);
	
	/**
	 * Add a new child to this domain resource
	 * @param child
	 * @return whether child was added or not
	 */
	public boolean addChild(IDomainResource child);
	
	/**
	 * Check if a Domain resource has been visited or not.
	 * <p>
	 * Required for a tree-traversal algorithm
	 * </p>
	 * @return
	 */
	public boolean isVisited();
	
	public void setVisited(boolean visited);
	
	/**
	 * Check if this resource has any children or not
	 * @return true or false
	 */
	public boolean hasChildren();
	
	
	/**
	 * @return a <code>List</code> of this 
	 * <code>IDomainResource</code>'s children
	 */
	public List<IDomainResource> getChildren();
}
