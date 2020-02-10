/**
 * 
 */
package com.tibco.cep.security.authz.domain;

import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.Future;

import com.tibco.cep.security.authz.core.ACLManager;
import com.tibco.cep.security.authz.core.SerializableObject;

/**
 * @author aathalye
 *
 */
public interface IDomainResourceCollection extends Serializable, SerializableObject {
	/**
	 * Add a <code>IDomainResource</code> to the collection.
	 * Can be called only after the collection is opened
	 * for modification using <code>open()</code>
	 * @param <E>
	 * @param resource
	 * @return whether resource was added or not
	 * @see IDomainResourceCollection#open()
	 */
	public <E extends IDomainResource> boolean add(E resource);
	
	/**
	 * Remove a <code>IDomainResource</code> from the collection.
	 * Can be called only after the collection is opened
	 * for modification using <code>open()</code>
	 * @param <E>
	 * @param resource
	 * @return whether resource was removed or not.
	 * @see IDomainResourceCollection#open()
	 */
	public <E extends IDomainResource> boolean remove(final E resource);
	
	/**
	 * Clear all elements inside this <code>IDomainResourceCollection</code>.
	 * @return whether collection was flushed or not
	 */
	public boolean removeAll();
	
	/**
	 * Method to be called to open a collection for modification.
	 * This method has to be called before any resource <code>
	 * IDomainResource</code> is added to, or removed from the collection.
	 * @return whether collection can be opened or not.
	 * @see IDomainResourceCollection#close()
	 */
	public boolean open();
	
	/**
	 * This method indicates that the collection is closed for 
	 * any addition/removal operations.
	 * <p>
	 * Call this method when addition/removal operations are done.
	 * Since this method also shuffles the collection, this method
	 * has to be called once add/remove has been done on this
	 * collection
	 * </p>
	 * <p>
	 * Example
	 * <code>
	 *  IDomainResourceCollection drc = new DomainResourceCollection();
	 *  drc.open();
	 *  drc.add(r1);
	 *  drc.add(r2);
	 *  drc.close();
	 *  </code>
	 * <p>
	 * The method is <tt>final</tt> to prevent a different implementation
	 * from a subclass
	 * </p> 
	 * @return whether collection is collection is closed/open
	 */
	public Future<Boolean> close();
	
	/**
	 * A utility method to return count of all resources
	 * present in this <code>DomainResourceCollection</code>
	 * @return count
	 */
	public int size();
	
	/**
	 * @return iterator on all elements as part of this collection
	 */
	public Iterator<IDomainResource> getElements();
	

	/**
	 * This method will block until this <code>DomainResourceCollection
	 * </code> is organized to form proper parent-child relationships amongst
	 * its member resources.
	 * <p>
	 * This method should be called before any call to <code>checkPermission</code>
	 * through the <code>ACLManager</code> is issued, since its success depends
	 * on the proper organization of this collection.
	 * </p>
	 * <p>
	 * The method is <tt>final</tt> to prevent a different implementation
	 * from a subclass
	 * </p>
	 * @return whether collection is organized or not
	 * @throws Exception
	 * @see ACLManager#checkPermission(com.tibco.cep.security.authz.core.impl.ACLContext)
	 */
	public boolean isCollectionOrganized() throws Exception;
}
