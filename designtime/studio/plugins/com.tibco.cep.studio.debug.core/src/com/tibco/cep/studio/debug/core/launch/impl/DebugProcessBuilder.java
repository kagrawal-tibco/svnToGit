package com.tibco.cep.studio.debug.core.launch.impl;

import org.eclipse.core.runtime.CoreException;

import com.tibco.cep.studio.debug.core.launch.BEProcessBuilder;
import com.tibco.cep.studio.debug.core.launch.RunProfile;

public interface DebugProcessBuilder extends BEProcessBuilder {

	void init() throws CoreException ;

	String getCommandLineString();

	String[] getCommand();

	String getPort();

	String getHostName();

	RunProfile getProfile();

}
