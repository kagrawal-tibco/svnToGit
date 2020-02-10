package com.tibco.cep.studio.debug.core.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant;

import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;

/*
@author ssailapp
@date Jul 22, 2009 1:29:07 PM
 */

public class ApplicationSourceLookupParticipant extends AbstractSourceLookupParticipant {

	public String getSourceName(Object object) throws CoreException {
		//TODO: Use an implementation of IDebugElement 
		if (object instanceof RuleDebugStackFrame) {
			RuleDebugStackFrame stackFrame = ((RuleDebugStackFrame)object);
			return stackFrame.getSourceName();
		}
		return null;
	}
}
