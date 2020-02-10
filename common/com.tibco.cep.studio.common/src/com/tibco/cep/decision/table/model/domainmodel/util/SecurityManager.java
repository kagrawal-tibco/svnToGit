/**
 * 
 */
package com.tibco.cep.decision.table.model.domainmodel.util;

import com.tibco.cep.decision.table.model.dtmodel.ResourceType;

/**
 * @author aathalye
 *
 */
public class SecurityManager { 
	
//	private static IAction getAction(com.tibco.cep.security.authz.utils.ResourceType resourceType,
//            final String action) {
//		switch (resourceType) {
//		case CONCEPT:
//			return new ConceptAction(action);
//		case EVENT:
//			return new EventAction(action);
//		case PROPERTY:
//			return new PropertyAction(action);
//		default:
//			break;
//		}
//		return null;
//	}
	
	
	public static boolean checkPermission(final String action,
			                              final String resourcePath,
			                              final ResourceType resType) {
		return true;
		//Convert this to ACL Resource Type
		/*com.tibco.cep.security.authz.utils.ResourceType resourceType =
			com.tibco.cep.security.authz.utils.ResourceType.valueOf(resType.getLiteral());
		
		IDomainResource resource = new DomainResource(resourcePath, resourceType);
		ACLContext ctx = null;
		ACLManager manager = null; 
		Permit permit = Permit.DENY;
		//TODO fetch all user roles from a session
		try {
			manager = ACLManagerImpl.newInstance();
			Role role = tokenFactory.eINSTANCE.createRole();
			role.setName("BU");
			ctx = manager.createACLContext(role, resource,
					getAction(resourceType, action), PermissionType.DOMAINMODEL);
			permit = manager.checkPermission(ctx);
		} catch (Exception e) {
			SecurityException ex = new SecurityException(e);
			TRACE.logError(SecurityManager.class.getName(), 
				       "Requested operation cannot be completed", ex);
			throw ex;
		}
		if (Permit.ALLOW.equals(permit)) {
			return true;
		}
		SecurityException ex = new SecurityException("The requested operation cannot be permitted");
		TRACE.logError(SecurityManager.class.getName(), 
			       "Requested operation cannot be completed", ex);
		throw ex;*/
	}
}
