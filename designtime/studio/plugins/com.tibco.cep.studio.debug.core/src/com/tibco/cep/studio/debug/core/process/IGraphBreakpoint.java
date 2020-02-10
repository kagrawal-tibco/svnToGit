package com.tibco.cep.studio.debug.core.process;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IBreakpoint;

public interface IGraphBreakpoint extends IBreakpoint {
	
	String getTypeName() throws CoreException;
	

}
