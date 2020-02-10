package com.tibco.cep.studio.debug.core.model;

import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.EventSet;
import com.tibco.cep.studio.debug.core.model.impl.AbstractSession;
import com.tibco.cep.studio.debug.core.model.impl.AbstractSession.EventState;

public interface IBreakpointHandler {

	boolean handlesMarkerType(String markerType);

	EventState handleBreakpoint(AbstractSession abstractSession,EventSet eventSet, BreakpointEvent bpe, String bpType, RuleDebugThread rdt, IRuleBreakpoint bp, EventState eventState);

}
