package com.tibco.cep.bemm.common.exception;

import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.Versionable;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;

public class StaleEntityVersionException extends Exception {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 1678276781773639872L;

	/**
	 * Default constructor
	 */
	public StaleEntityVersionException() {
		super("User has a stale copy");
	}

	public StaleEntityVersionException(Versionable versionable) {		
		super(getMessage(versionable));
	}
	
	private static String getMessage(Versionable versionable) {
		if (versionable instanceof Application) {
			return "User has a stale copy of " + ((Application) versionable).getName(); 
		} else if (versionable instanceof MasterHost) {
			return "User has a stale copy of " + ((MasterHost) versionable).getHostName();
		} else if (versionable instanceof ServiceInstance) {
			return "User has a stale copy of " + ((ServiceInstance) versionable).getName();
		} else if (versionable instanceof DeploymentVariables) {
			return "User has a stale copy of " + ((DeploymentVariables) versionable).getKey();
		}
		return "";
	}
}
