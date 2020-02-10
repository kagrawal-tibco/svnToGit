package com.tibco.cep.studio.debug.core.model;

import com.tibco.cep.studio.core.StudioCorePlugin;

public interface IRuleJavaBreakpoint extends IRuleBreakpoint{
	
	static final String JAVA_INTERMEDIATE_BREAKPOINT_MARKER_TYPE = StudioCorePlugin.PLUGIN_ID+".RuleJavaBreakPointMarker"; //$NON-NLS-1$

}
