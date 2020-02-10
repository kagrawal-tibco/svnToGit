package com.tibco.cep.studio.debug.core.launch;

import org.eclipse.core.runtime.CoreException;



public interface RemoteProfile {

	String getPortNo();

	String getHostName();

	void init() throws CoreException;

}
