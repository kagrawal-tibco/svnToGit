/**
 * 
 */
package com.tibco.cep.security.authz.permissions;

import com.tibco.cep.security.authz.domain.DomainResourceCollection;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.xml.datamodel.XiNode;

/**
 * @author aathalye
 *
 */
public interface IResourcePermissionFactory {
	
	/**
	 * @param node representing a permission
	 * @return permission 
	 */
	public IResourcePermission getPermission(XiNode node);
	
	/**
	 * Return a <code>IResourcePermission</code> which encapsulates
	 * this <code>IDomainResource</code>, and <code>IAction</code>
	 * @param resource
	 * @param action
	 * @return permission
	 */
	public IResourcePermission getPermission(IDomainResource resource,
			                                 IAction action);
	
	public IResourcePermission getPermission(IDomainResource dr,
		  									 String permType,
                                             String actionType, 
                                             Permit permit);
	
	/**
	 * @return the wrapped resources
	 */
	public DomainResourceCollection getResources();
}
