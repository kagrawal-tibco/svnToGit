package com.tibco.cep.studio.debug.ui.launch.classpath;

import com.tibco.cep.studio.debug.core.launch.IStudioDebugLaunchConfigurationConstants;

public class CustomFunctionLibPath extends JavaLibPath {
	
	public CustomFunctionLibPath(String libPath) {
		super(libPath);
	}

	@Override
	public boolean isDefault() {
		return IStudioDebugLaunchConfigurationConstants.DEFAULT_CUSTOM_FN_LIB_NAME.equals(getLibPath());
	}

}
