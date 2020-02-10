package com.tibco.cep.studio.debug.core.model;

import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugException;

public interface ISourceInfoProvider {

	IPath getSourcePath(RuleDebugStackFrame frame) throws DebugException ;

	IResourcePosition getResourcePosition(RuleDebugStackFrame frame) throws DebugException ;

}
