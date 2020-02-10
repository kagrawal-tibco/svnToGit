package com.tibco.cep.studio.debug.core.launch.impl;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;

import com.tibco.cep.studio.debug.core.launch.DebugProfile;

public class DebugProfileImpl extends AbstractProfile implements DebugProfile {

	public DebugProfileImpl(ILaunchConfiguration configuration, IProgressMonitor monitor) {
    	super(RUNTIME_ENV_TYPE.ENV_APPLICATION, configuration,monitor);    	
    } 
    
}
