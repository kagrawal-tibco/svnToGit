package com.tibco.cep.studio.debug.core.model;

import com.sun.jdi.event.BreakpointEvent;


/*
 @author ssailapp
 @date Jul 30, 2009 4:49:29 PM
 */

public interface IRuleDebugTarget extends IRuleRunTarget {

	DebugSession getSession();

	void disconnected();

	void initializeBreakpoints();
	
	public boolean onEndOfRTC(BreakpointEvent bpe);

}
