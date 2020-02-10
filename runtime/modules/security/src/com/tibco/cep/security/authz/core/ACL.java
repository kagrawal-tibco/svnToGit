/**
 * 
 */
package com.tibco.cep.security.authz.core;

import java.io.Serializable;
import java.util.List;

import com.tibco.cep.security.authz.domain.IDomainResourceCollection;

/**
 * @author aathalye
 *
 */
public interface ACL extends Serializable, SerializableObject {
	
	/**
	 * A method to return <tt>DomainResourceCollection</tt>
	 * contained in this <tt>ACL</tt> object
	 * @return <tt>IDomainResourceCollection</tt>
	 */
	public IDomainResourceCollection getResources();
	
	/**
	 * Set a <tt>DomainResourceCollection</tt> for this 
	 * <tt>ACL</tt> object
	 * @param resources
	 */
	public void setResources(IDomainResourceCollection resources);
	
	/**
	 * @return a <tt>java.util.List</tt> of all
	 * <tt>ACLEntry</tt> objects inside this <tt>ACL</tt>
	 */
	public List<ACLEntry> getACLEntries();
	
	/**
	 * Set a <tt>java.util.List</tt> of <tt>ACLEntry</tt>
	 * objects for this <tt>ACL</tt> object.
	 * @param resources
	 */
	public void setACLEntries(List<ACLEntry> entries);
}
