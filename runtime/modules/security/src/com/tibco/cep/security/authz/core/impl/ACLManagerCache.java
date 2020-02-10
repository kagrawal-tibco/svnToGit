package com.tibco.cep.security.authz.core.impl;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.security.authz.core.ACLManager;

/*
@author ssailapp
@date Aug 23, 2011
 */

public class ACLManagerCache {

	public static final ACLManagerCache INSTANCE = new ACLManagerCache();
	
	/**
	 * Map of project to {@link ACLManager}
	 */
	private static Map<String, ACLManager> ACL_MANAGERS = new HashMap<String, ACLManager>();

	/**
	 * 
	 * @param project
	 * @return
	 */
	public ACLManager getACLManager(String project) {
		return ACL_MANAGERS.get(project);
	}

	/**
	 * @param projectName
	 * @param aclManager
	 */
	public void putACLManager(String projectName, ACLManager aclManager) {
		ACL_MANAGERS.put(projectName, aclManager);
	}
	
	/**
	 * @param projectName
	 */
	public void removeACLManager(String projectName) {
		ACLManager aclManager = ACL_MANAGERS.get(projectName);
		if (aclManager != null) {
			ACL_MANAGERS.remove(projectName);
		}
	}
}
