/**
 * 
 */
package com.tibco.cep.security.authz.domain;

import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.RESOURCES_ELEMENT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.tibco.cep.security.authz.core.ACLManager;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


/**
 * A collection class for storing domain resources.
 * <p>
 * ---------------------------------------------------------------
 * Author            Date              Change Desc
 * ---------------------------------------------------------------
 * aathalye         5/3/08             Implemented the IDomainResourceCollection
 *                                     interface.
 * aathalye         19/2/08            Made close, and isCollectionOrganized
 *                                     final.
 * aathalye         14/2/08            Added size, and removeAll methods
 * aathalye         14/2/08            Added sorting and organizing
 *                                     of the collection as an async-
 *                                     hronous task.
 * aathalye         12/2/08            Added support for
 *                                     organizing resources
 *                                     into parent-child hierarchy
 *                                     without parent attribute.
 * aathalye         28/12/07           Creation.      
 * </p>                     
 * @author aathalye
 * 
 */
public class DomainResourceCollection implements IDomainResourceCollection {
	
	private Map<ResourceType, List<IDomainResource>> resourceEntries;
	
	private transient volatile boolean open;//Whether this collection is open for add/remove
	
	private transient Future<Boolean> organized;
	
//	private transient static PluginLoggerImpl TRACE = LoggerRegistry.getLogger(SecurityPlugin.PLUGIN_ID);
	
	public DomainResourceCollection() {
		resourceEntries = new EnumMap<ResourceType, List<IDomainResource>>(ResourceType.class);
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.domain.IDomainResourceCollection#add(com.tibco.cep.security.authz.domain.IDomainResource)
	 */
	public <E extends IDomainResource> boolean add(E resource) {
		boolean flag = false;
		if (open) {
			//Get its resource type
			ResourceType resourceType = resource.getType();
			List<IDomainResource> resourcePaths = null;
			//Check if this exists in the hashtable
			synchronized(resourceEntries) {
				if (!resourceEntries.containsKey(resourceType)) {
					resourcePaths =
						new LinkedList<IDomainResource>();
				} else {
					resourcePaths = 
						resourceEntries.get(resourceType);
				}
				if (!resourcePaths.contains(resource)) {
					resourcePaths.add(resource);
				}
				resourceEntries.put(resourceType, resourcePaths);
			}
			flag = true;
		}
		return flag;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.domain.IDomainResourceCollection#remove(com.tibco.cep.security.authz.domain.IDomainResource)
	 */
	public <E extends IDomainResource> boolean remove(final E resource) {
		boolean flag = false;
		if (open) {
			//Get its resource type
			ResourceType resourceType = resource.getType();
			List<IDomainResource> resourcePaths = null;
			//Check if this exists in the hashtable
			synchronized(resourceEntries) {
				if (!resourceEntries.containsKey(resourceType)) {
					return false;
				} else {
					resourcePaths = 
						resourceEntries.get(resourceType);
					resourcePaths.remove(resource);
					if (resourcePaths.isEmpty()) {
						//Remove this from the map
						resourceEntries.remove(resourceType);
					}
				}
			}
			flag = true;
		}
		return flag;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.domain.IDomainResourceCollection#removeAll()
	 */
	public boolean removeAll() {
		boolean flag = false;
		if (open) {
			synchronized (resourceEntries) {
				resourceEntries.clear();
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * Search in this collection to see if the requested resourcepath
	 * is present or not. The search should check for wildcard type also
	 * <p>
	 * This method would be called by client like <code>TreeObject</code>
	 * from which path can be extracted.
	 * @see TreeObject
	 * @param <E>
	 * @param target
	 * @return the matching domain resource
	 */
	@SuppressWarnings("unused")
	public <E extends IDomainResource> boolean search(final E target) {
		//Get the resource type
		if (target == null) {
			return false;
		}
		ResourceType resourceType = target.getType();
		//Check if there is any entry in the collections 
		//table for this resource type
		if (resourceEntries.containsKey(resourceType)) {
			//Now search in this to have a restricted search
			List<IDomainResource> resources =
				resourceEntries.get(resourceType);
			//Search logic here
			return search(resources, target);
		}
		return false;
	}
	
	
	public boolean open() {
		if (!open) {
			open = true;
		}
//		TRACE.logDebug(this.getClass().getName(), 
//				"Opening the collection for modification");
		return open;
	}
	
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
	 *  DomainResourceCollection drc = new DomainResourceCollection();
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
	public final Future<Boolean> close() {
		if (open) {
			open = false;
		}
//		TRACE.logDebug(this.getClass().getName(), 
//				"Closing the collection for modification");
		return organizeResources();
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.domain.IDomainResourceCollection#size()
	 */
	public int size() {
		int counter = 0;
		Iterator<IDomainResource> elements = getElements();
		while (elements.hasNext()) {
			counter++;
			elements.next();
		}
		return counter;
	}
	
	private Future<Boolean> organizeResources() {
		return organized = Executors.newSingleThreadExecutor().submit(new Callable<Boolean>() {
			public Boolean call() {
//				TRACE.logDebug(this.getClass().getName(), 
//							"Entering the callable method");
				Collection<List<IDomainResource>> values = resourceEntries.values();
				boolean b = true;
				for (List<IDomainResource> l : values) {
					Collections.sort(l);
					b &= organizeResources((LinkedList<IDomainResource>)l);
//					TRACE.logDebug(this.getClass().getName(), 
//							"Output of each organize method {0}", b);
				}
//				TRACE.logDebug(this.getClass().getName(), 
//						"Final Output of organize method {0}", b);
				return b;
			}
		});
	}
	
	
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
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @see ACLManager#checkPermission(com.tibco.cep.security.authz.core.impl.ACLContext)
	 */
	public final boolean isCollectionOrganized() throws InterruptedException, ExecutionException {
		if (organized != null) {
			return organized.get();
		} 
		return false;
	}
	
			
	private Boolean organizeResources(final LinkedList<IDomainResource> list) {
		boolean organized = false;
		//TODO explore possibility of using iterators here
		for (IDomainResource outer : list) {
			int position = list.indexOf(outer);
			for (int j = position + 1; j < list.size(); j++) {
				//This temp resource could be parent
				IDomainResource temp = list.get(j);
//				TRACE.logDebug(this.getClass().getName(), 
//							"Temporary resource is {0}", temp);
				String name = outer.getName();
				if (name == null || name.length() == 0) {
					outer.setParent(null);
					break;
				}
				String tempName = temp.getName();
				tempName = (tempName == null) ? "" : tempName;
				if (temp.implies(outer)) {
					//Set temp as parent
//					TRACE.logDebug(this.getClass().getName(), 
//							"Setting resource {0} as parent for" +
//							" resource {1}", temp, outer);
					temp.addChild(outer);
					break;
				}
			}
			organized = true;
		}
		return organized;
	}
	
	private <E extends IDomainResource> boolean search(final List<IDomainResource> list,
			                                           final E target) {
		boolean found = false;
		ListIterator<IDomainResource> i = list.listIterator();
		String targetName = target.getName();
		while (i.hasNext()) {
			IDomainResource dr = i.next();
			if (dr != null) {
				//get its name
				String drname = dr.getName();
				if (drname == null) {
					continue;
				}
				if (drname.endsWith("*")) {
					//This has wildcard
					if (targetName.endsWith("*")) {
						//Do not allow this?
						throw new IllegalArgumentException("Wildcards cnnot be used here");
					}
					//Check to see that targetname length is greater than
					//drname length, so that /Concepts/* should not imply
					// /Concepts
					if (targetName.length() > drname.length()) {
						int offsetLength = drname.length() - 1; 
						if (targetName.regionMatches(0, drname, 0, offsetLength)) {
							//we found this resource
							found = true;
						}
					}
				} else {
					//Now check simple equality
					 if (targetName.intern() == drname.intern()) {
						found = true;
					}
				}
			}
		}
		return found;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.domain.IDomainResourceCollection#getElements()
	 */
	public synchronized Iterator<IDomainResource> getElements() {
		//get all values 
		Collection<List<IDomainResource>> values =
								resourceEntries.values();
		List<IDomainResource> resources = new ArrayList<IDomainResource>();
		for (List<IDomainResource> list : values) {
			for (IDomainResource resource : list) {
				resources.add(resource);
			}
		}
		return resources.iterator();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return resourceEntries.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DomainResourceCollection)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		DomainResourceCollection other = (DomainResourceCollection)obj;
		if (this.getElements().equals(other.getElements())) {
			return true;
		}
		return false;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.core.SerializableObject#serialize(com.tibco.xml.datamodel.XiFactory, com.tibco.xml.datamodel.XiNode)
	 */
	@Override
	public void serialize(XiFactory factory, XiNode rootNode) {
		//Create root <resources> element
		XiNode resourcesNode = factory.createElement(RESOURCES_ELEMENT);
		//Get all values for resources
		Set<ResourceType> keys = resourceEntries.keySet();
		//Group by resources
		for (ResourceType resourceTypeKey : keys) {
			List<IDomainResource> domainResources = resourceEntries.get(resourceTypeKey);
			for (IDomainResource domainResource : domainResources) {
				domainResource.serialize(factory, resourcesNode);
			}
		}
		//Append to root node
		rootNode.appendChild(resourcesNode);
	}
}
