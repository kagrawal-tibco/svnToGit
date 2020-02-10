/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import static com.tibco.cep.security.authz.utils.ACLConstants.XMLConstants.PERMISSIONS_ELEMENT;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.tibco.cep.security.authz.core.SerializableObject;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * <p>
 * ---------------------------------------------------------------
 * Author            Date              Change Desc
 * ---------------------------------------------------------------
 * aathalye         21/10/08           Removed the serial version UID for
 *                                     backward compatibility
 * aathalye         15/2/08            Added size, and removeAll methods
 * aathalye         25/12/07           Creation 
 * @author aathalye
 * 
 */
@SuppressWarnings("serial")
public class PermissionsCollection implements Serializable, SerializableObject {
	
	//private static final long serialVersionUID = 1416371989L;

	private Map<IAction, List<IResourcePermission>> permissions;
	
//	private static PluginLoggerImpl TRACE = LoggerRegistry.getLogger(SecurityPlugin.PLUGIN_ID);

	public PermissionsCollection() {
		permissions = new HashMap<IAction, List<IResourcePermission>>();
	}

	/**
	 * Adds a new <tt>IResourcePermission</tt> to this collection. This
	 * method is thread-safe.
	 * <p>
	 * This method can be invoked only through trusted code
	 * </p>
	 * 
	 * @param permission
	 * @return true/false
	 */
	public boolean addPermission(final IResourcePermission permission) {
		// TODO security check
		// TODO put check to see whether resource exists in collection or not
		// Get action in this permission
		IAction action = permission.getAction();
		if (action == null) {
			return false;
		}
		if (permission == null) {
			return false;
		}
		List<IResourcePermission> permissionsList;
		synchronized (permissions) {
			if (!permissions.containsKey(action)) {
				permissionsList = new LinkedList<IResourcePermission>();
			} else {
				permissionsList = permissions.get(action);
			}
			if (!permissionsList.contains(permission)) {
				permissionsList.add(permission);
			}
			permissions.put(action, permissionsList);
		}
		return true;
	}

	/**
	 * Remove a <tt>IResourcePermission</tt> from this collection This
	 * method is thread-safe.
	 * <p>
	 * This method can be invoked only through trusted code
	 * </p>
	 * 
	 * @param permission
	 * @return
	 */
	public boolean removePermission(final IResourcePermission permission) {
		// TODO security check
		// Get action in this permission
		IAction action = permission.getAction();
		if (action == null) {
			return false;
		}
		if (permission == null) {
			return false;
		}
		List<IResourcePermission> permissionsList;
		synchronized (permissions) {
			if (!permissions.containsKey(action)) {
				// There is no action matching this key
				return false;
			} else {
				permissionsList = permissions.get(action);
				if (permissionsList.contains(action)) {
					permissionsList.remove(action);
					return true;
				}
				// If last entry from this list is removed,
				// purge it from the map anyway
				if (permissionsList.isEmpty()) {
					permissions.remove(action);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Clear all elements inside this <tt>PermissionsCollection</code>.
	 * @return whether collection was flushed or not
	 */
	public boolean removeAll() {
		boolean flag = false;
		synchronized (permissions) {
			permissions.clear();
			flag = true;
		}
		return flag;
	}
	
	/**
	 * A utility method to return count of all resources
	 * present in this <tt>PermissionsCollection</tt>
	 * @return count
	 */
	public int size() {
		int counter = 0;
		Iterator<IResourcePermission> elements = getElements();
		while (elements.hasNext()) {
			counter++;
			elements.next();
		}
		return counter;
	}

	
	/**
	 * Calls <code>checkPermission</code> of each
	 * <code>IResourcePermission</code> inside this
	 * collection.
	 * @see IResourcePermission#implies(IResourcePermission)
	 * @see Permit
	 * @param requestedPermission
	 * @return a Permit
	 * @throws Exception
	 */
	public Permit checkPermission(final IResourcePermission requestedPermission) throws Exception {
//		TRACE.logDebug(this.getClass().getName(), 
//						"Inside checkPermission");
		//TRACE.logError("Inside checkPermission", new Exception());
		Permit permit = Permit.DENY;
		if (requestedPermission == null) {
			permit = Permit.DENY;
		}
		// Get the associated action
		IAction requestedAction = requestedPermission.getAction();
		if (requestedAction == null) {
			IllegalArgumentException t = new IllegalArgumentException(
						"Action {0} requested cannot be null");
//			TRACE.logError(t, new Object[]{requestedAction});
			throw t;
		}
		// Get the list from map
		synchronized(permissions) {
			List<IResourcePermission> permissionsList = permissions
					.get(requestedAction);
			if (permissionsList != null) {
				int counter = 0;
				for (final IResourcePermission perm : permissionsList) {
					if (perm.getResource().implies(requestedPermission.getResource())) {
						IDomainResource specificResource = ResourceUtil.getResource(perm.getResource(), requestedPermission);
//						TRACE.logDebug("Closest matching resource for requested resource {0} is : {1}", 
//								          requestedPermission.getResource(), specificResource);
						//Turn visited flag for all children and the resource itself to false
						ResourceUtil.unvisitChildren(perm.getResource());
						//Once you find a non-null resource here
						//search all remaining permissions for this resource
						//Set this resource in requested permission
						requestedPermission.setResource(specificResource);
						permit = implies(permissionsList, 
										requestedPermission);
						break;
					}
					counter++;
				}
			}
		}
		return permit;
	}
	
	//TODO Add some indexing here probably?
	private Permit implies(List<IResourcePermission> permissions,
						   IResourcePermission requestedPermission) {
		//Search permissions inside this list matching the specified resource
		//There should be only 1
		for (IResourcePermission perm : permissions) {
			if (perm.getResource().equals(requestedPermission.getResource())) {
				//Check for permission type also
				if (perm.getPermissionType() == (requestedPermission.getPermissionType())) {
//					TRACE.logDebug("Configured permission selected for this resource {0} is : {1}", 
//					               requestedPermission.getResource(), perm);
					//Call implies of this permission
					return perm.implies(requestedPermission);
				}
			}
		}
		//Since no matching permission for this resource
		//was found, let us see if there is a permission
		//available for its parent and so on.
		IDomainResource parent = requestedPermission.getResource().getParent();
		if (parent != null) {
			requestedPermission.setResource(parent);
			return implies(permissions, requestedPermission);
		}
		return Permit.DENY;
	}
	
	private static class ResourceUtil {

		/**
		 * This method will match most specific configured
		 * <code>IDomainResource</code> for a requested
		 * <code>IDomainResource</code>. 
		 * <p>
		 * e.g:
		 * If requested resource is <i>/Concepts/Concept/C</i>
		 * and configured resources are
		 * <li>
		 * <i>/Concepts/*</i> and 
		 * </li>
		 * <li>
		 * <i>/Concepts/Concept/*</i>
		 * </li>
		 * then <i>/Concepts/Concept/*</i> will be returned
		 * by this method.
		 * </p>
		 * @param resource
		 * @param permission
		 * @return the most specific resource matching the requested resource
		 */
		static IDomainResource getResource(IDomainResource resource,
				                           IResourcePermission permission) {
			IDomainResource otherResource = permission.getResource();
			// TODO check for template resource
			Stack<IDomainResource> resourceStack = new Stack<IDomainResource>();
//			TRACE.logDebug(ResourceUtil.class.getName(),
//					       "Parent resource is :{0}",
//					       resource);
			//Add DFS here
			resourceStack.push(resource);
			while (!resourceStack.empty()) {
				//Get the topmost element
				IDomainResource top = resourceStack.peek();
//				TRACE.logDebug(ResourceUtil.class.getName(),
//					       "Top element in stack is :{0}",
//					       top);
				//Check if it is visited
				if (!top.isVisited()) {
					//set visited to true
					top.setVisited(true);
					//Check to see if it has any unvisited children
					if (!hasUnvisitedChildren(top)) {
//						TRACE.logDebug(ResourceUtil.class.getName(),
//							       "No unvisited children left for resource :{0}",
//							       top);
						top = resourceStack.pop();
						if (top.implies(otherResource)) {
							//Before returning set visited to false,
							//as we might use it again as well as set
							//its children to unvisited
							top.setVisited(false);
							//unvisitChildren(top);
							return top;
						}
					}
					for (IDomainResource child : top.getChildren()) {
						//Add each unvisited child to the stack
						if (!child.isVisited()) {
							resourceStack.push(child);
						}
					}
				}
			}
			return null;
		}
		
		private static boolean hasUnvisitedChildren(IDomainResource resource) {
			for (IDomainResource child : resource.getChildren()) {
				if (!child.isVisited()) {
					//Also set visited to false
					resource.setVisited(false);
					return true;
				}
			}
			return false;
		}
		
		/**
		 * This should return a list of all children of this
		 * resource which have their visited flag set to true
		 * @param resource
		 * @return
		 */
		@SuppressWarnings("unused")
		private static List<IDomainResource> getChildrenVisited(IDomainResource resource) {
			List<IDomainResource> list = new ArrayList<IDomainResource>();
			for (IDomainResource child : resource.getChildren()) {
				if (child.isVisited()) {
					list.add(child);
				}
			}
			return list;
		}
		
		/**
		 * This method will recursively set each child resource's visited flag
		 * to false.
		 * @param resource
		 */
		static void unvisitChildren(final IDomainResource resource) {
//			TRACE.logDebug(ResourceUtil.class.getName(),
//							       "Unvisiting cildren");
			Stack<IDomainResource> resourceStack = 
								new Stack<IDomainResource>();
			Map<IDomainResource, List<IDomainResource>> map =
				new HashMap<IDomainResource, List<IDomainResource>>();
			resourceStack.push(resource);
			while (!resourceStack.empty()) {
				//Get the topmost element
				IDomainResource top = resourceStack.peek();
				//Check if it is visited. If visited unvisit it
				if (top.isVisited()) {
					top.setVisited(false);
				}
				List<IDomainResource> copyChildren = map.get(top);
				if (copyChildren == null) {
					//Create it and add to map
					copyChildren = new ArrayList<IDomainResource>(top.getChildren());
					map.put(top, copyChildren);
				}
				if (copyChildren.isEmpty()) {
					//if (!top.isVisited()) {
					//Pop it off if it has no children,
					//and its visited flag is false
					top = resourceStack.pop();
					//Also remove it from copied list
					List<IDomainResource> parentCopyChildren = map.get(top.getParent());
					if (parentCopyChildren != null) {
						parentCopyChildren.remove(top);
					}
				}
				//Push only those children having visited flag true on stack
				for (IDomainResource child : copyChildren) {
					resourceStack.push(child);
				}
			}
			
		}
	}

	/**
	 * A method which will return all <code>IResourcePermission
	 * </code> objects
	 * which are present in this collection.
	 * 
	 * @return iterator over all elements in this collection
	 */
	public synchronized Iterator<IResourcePermission> getElements() {
		// get all values
		Collection<List<IResourcePermission>> values = permissions.values();
		List<IResourcePermission> permissionsList = new ArrayList<IResourcePermission>();
		for (List<IResourcePermission> list : values) {
			for (IResourcePermission permission : list) {
				permissionsList.add(permission);
			}
		}
		return permissionsList.iterator();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.core.SerializableObject#serialize(com.tibco.xml.datamodel.XiNode)
	 */
	@Override
	public void serialize(XiFactory factory, XiNode rootNode) {
		//Create root <permissions> node
		XiNode permissionsNode = factory.createElement(PERMISSIONS_ELEMENT);
		
		Iterator<IResourcePermission> allPermissions = getElements();
		while (allPermissions.hasNext()) {
			//Serialize each permission
			IResourcePermission permission = allPermissions.next();
			permission.serialize(factory, permissionsNode);
		}
		//Append to entry node
		rootNode.appendChild(permissionsNode);
	}
}
