package com.tibco.cep.studio.debug.ui.model;

import org.eclipse.core.runtime.CoreException;

import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;

public interface IRuleDebugPresentationProvider {

	String getMarkerType();

	String getRuleBreakpointText(IRuleBreakpoint breakpoint) throws CoreException;

}
