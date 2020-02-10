package com.tibco.cep.studio.debug.core.launch;

import org.eclipse.core.runtime.CoreException;


public interface RemoteProcessBuilder extends BEProcessBuilder{

	void init() throws CoreException;

	String getHostName();

	String getPort();

}
