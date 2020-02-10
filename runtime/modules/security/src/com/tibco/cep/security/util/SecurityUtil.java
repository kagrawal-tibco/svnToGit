package com.tibco.cep.security.util;

import java.util.List;

import com.tibco.cep.security.authz.core.ACLManager;
import com.tibco.cep.security.authz.core.impl.ACLContext;
import com.tibco.cep.security.authz.core.impl.ACLManagerCache;
import com.tibco.cep.security.authz.domain.DomainResource;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.security.tokens.Role;

public class SecurityUtil {


	/**
	 * DO NOT USE THIS API
	 * To call this method:
	 * 
	 * Caller <code>
	 *  ACLManager manager = ACLManager.newInstance(stream);
	 *  <li>
	 *  //This stream will be the one which contains bits of
	 *  </li>
	 *  <li>
	 *  //Access control config file. If the native file is encrypted,
	 *  </li>
	 *  <li>
	 *  //It should be decrypted before using <b>newInstance</b>.
	 *  </li>
	 *  </p>
	 *  SecurityUtil.checkACL()
	 *  <p>
	 *  //This can then be called from caller
	 *  </p>
	 *  </code>
	 * 
	 * @param resourcePath
	 * @param resourceType
	 * @param userRoles
	 * @param action
	 * @return
	 */
	public static Permit checkACL(final String resourcePath,
			final ResourceType resourceType, final List<Role> userRoles,
			final IAction action) {

		// Put ACL check here
		Permit permit = Permit.DENY;
		try {
			IDomainResource resource = new DomainResource(resourcePath,
					resourceType);
			ACLContext ctx = null;
			ACLManager manager = null;//ACLManagerImpl.newInstance();
			if (manager == null) {
//				TRACE.logError(SecurityUtil.class.getName(),
//						"ACL is not loaded in memory::", new Exception(
//								"ACL is not loaded in memory::"));
			} else {
				for (Role role : userRoles) {
					ctx = manager.createACLContext(role, resource, action,
							PermissionType.BERESOURCE);
					permit = manager.checkPermission(ctx);
					if (Permit.ALLOW.equals(permit)) {
						break;
					}
				}
				/*TRACE
						.logInfo(
								"SecurityUtil",
								"Access requested {0} for resource {1} {2} by role {3}",
								action, resource, permit, ctx.getPrincipal());*/
			}
		} catch (Exception e) {
//			TRACE.logError(SecurityUtil.class.getName(),
//					"Exception while requesting ACL check", e);
		}
		return permit;
	}
	
	/**
	 * DO NOT USE
	 * @param resourcePath
	 * @param resourceType
	 * @param userRoles
	 * @param action
	 * @return
	 */
	public static Permit checkDomainACL(final String resourcePath,
			final ResourceType resourceType, final List<Role> userRoles,
			final IAction action) {

		// Put ACL check here
		Permit permit = Permit.DENY;
		try {
			IDomainResource resource = new DomainResource(resourcePath,
					resourceType);
			ACLContext ctx = null;
			ACLManager manager = null;//ACLManagerImpl.newInstance();
			if (manager == null) {
//				TRACE.logError(SecurityUtil.class.getName(),
//						"ACL is not loaded in memory::", new Exception(
//								"ACL is not loaded in memory::"));
			} else {
				for (Role role : userRoles) {
					ctx = manager.createACLContext(role, resource, action,
							PermissionType.DOMAINMODEL);
					permit = manager.checkPermission(ctx);
					if (Permit.ALLOW.equals(permit)) {
						break;
					}
				}
//				TRACE
//						.logInfo(
//								"SecurityUtil",
//								"Access requested {0} for resource {1} {2} by role {3}",
//								action, resource, permit, ctx.getPrincipal());
			}
		} catch (Exception e) {
//			TRACE.logError(SecurityUtil.class.getName(),
//					"Exception while requesting ACL check", e);
		}
		return permit;
	}
	
	/**
	 * e.g: Usage
	 * <p>
	 * Permit permit = {@link SecurityUtil#ensurePermission(<projectName>, /Virtual_RF/DT1.dt, ResourceType.IMPLEMENTATION, List, new AbstractImplementationAction("READ"), PermissionType.BERESOURCE)}
	 * </p>
	 * @param project
	 * @param resourcePath
	 * @param resourceType
	 * @param userRoles
	 * @param action
	 * @param permissionType
	 * @return
	 */
	public static Permit ensurePermission(final String project, 
			                              final String resourcePath,
			                              final ResourceType resourceType, 
			                              final List<Role> userRoles,
			                              final IAction action,
			                              final PermissionType permissionType) {
		ACLManager aclManager = ACLManagerCache.INSTANCE.getACLManager(project);
		return ensurePermission(aclManager, resourcePath, resourceType, userRoles, action, permissionType);
	}
	
	/**
	 * e.g: Usage
	 * <p>
	 * Permit permit = {@link SecurityUtil#ensurePermission(aclManager, /Virtual_RF/DT1.dt, ResourceType.IMPLEMENTATION, List, new AbstractImplementationAction("READ"), PermissionType.BERESOURCE)}
	 * </p>
	 * @param aclManager
	 * @param resourcePath
	 * @param resourceType
	 * @param userRoles
	 * @param action
	 * @param permissionType
	 * @return
	 */
    public static Permit ensurePermission(final ACLManager aclManager,
			                              final String resourcePath,
			                              final ResourceType resourceType,
			                              final List<Role> userRoles,
			                              final IAction action,
			                              final PermissionType permissionType) {
        Permit permit = Permit.DENY;
        if (aclManager == null) {
            //TODO Log it
        } else {
            IDomainResource resource = new DomainResource(resourcePath, resourceType);
            ACLContext ctx = null;
            try {
                for (Role role : userRoles) {
                    ctx = aclManager.createACLContext(role, resource, action, permissionType);
                    permit = aclManager.checkPermission(ctx);
                    if (Permit.ALLOW.equals(permit)) {
                        break;
                    }
                }
            } catch (Exception e) {
            	  throw new RuntimeException(e);
            }
        }
        return permit;
    }
}
