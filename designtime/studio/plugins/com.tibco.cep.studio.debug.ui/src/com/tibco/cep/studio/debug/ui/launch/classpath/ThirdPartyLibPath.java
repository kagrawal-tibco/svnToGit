package com.tibco.cep.studio.debug.ui.launch.classpath;

import com.tibco.cep.studio.debug.core.launch.IStudioDebugLaunchConfigurationConstants;

public class ThirdPartyLibPath extends JavaLibPath {
	
	public ThirdPartyLibPath(String libPath) {
		super(libPath);
	}

	@Override
	public boolean isDefault() {
		return IStudioDebugLaunchConfigurationConstants.DEFAULT_THIRD_PARTY_LIB_NAME.equals(getLibPath());
	}
}
