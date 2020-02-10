package com.tibco.cep.studio.debug.core.model.impl;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.EventSet;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.DebuggerConstants;
import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.IRuleJavaBreakpoint;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
import com.tibco.cep.studio.debug.core.model.impl.AbstractSession.EventState;

public class RuleBreakpointHandler extends AbstractBreakpointHandler {

	public RuleBreakpointHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean handlesMarkerType(String markerType) {
		return markerType.equals(IRuleBreakpoint.RULEBREAKPOINT_MARKER_TYPE) || markerType.equals(IRuleJavaBreakpoint.JAVA_INTERMEDIATE_BREAKPOINT_MARKER_TYPE);
	}

	@Override
	public EventState handleBreakpoint(AbstractSession session,EventSet eventSet, BreakpointEvent bpe, String bpType, RuleDebugThread rdt, IRuleBreakpoint bp,
			EventState eventState) {
		String source;
		int line;
		try {
			source = bpe.location().sourceName();
			line = bpe.location().lineNumber();

		} catch (AbsentInformationException e) {
			source = "Unknown Source";
			line = -1;
		}
		eventState = super.handleBreakpoint(session,eventSet, bpe, bpType, rdt, bp, eventState);
		try {

			if (!eventState.isHandled()) {
				if (bpType != null) {
					StudioDebugCorePlugin.debug("Breakpoint[" + bpType + ":" + source + ":" + line + "]");
					if (bpType.equals(DebuggerConstants.BREAKPOINT_ENDOFMETHOD)) {
						synchronized (rdt) {
							rdt.clearPreviousStep();
							rdt.setRunning(false);
							rdt.addCurrentBreakpoint(bp);
							rdt.fireSuspendEvent(DebugEvent.BREAKPOINT);
						}
						session.cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_ENDOFMETHOD);

						// Map propertyMap = new HashMap();
						// propertyMap.put(DebuggerConstants.ACTION_FLAGS,
						// rdt.getDebugActionState());
						rdt.stepInto();
						eventState.setResume(true);
						eventState.setHandled(true);
						synchronized (rdt) {
							rdt.setRunning(true);
							rdt.preserveStackFrames();
							rdt.fireResumeEvent(DebugEvent.CLIENT_REQUEST);
							rdt.resumedByVM();
						}
						StudioDebugCorePlugin.debug("Resuming End of Method.");
					} else if (bpType.equals(DebuggerConstants.BREAKPOINT_STEPOUT)) {
						synchronized (rdt) {
							rdt.setRunning(false);
							rdt.addCurrentBreakpoint(bp);
							session.cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_STEPOUT);
							eventState.setResume(onLocatableEvent(session,bpe,rdt));
							eventState.setHandled(true);
						}
					} else if (bpType.equals(DebuggerConstants.BREAKPOINT_PROCESS_RECORDED)) {
						rdt.clearPreviousStep();
						session.cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_STEPOUT);
						session.cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_ENDOFMETHOD);
						eventState.setHandled(true);
						eventState.setResume(session.getRuleDebugTarget().onEndOfRTC(bpe));
						session.setDebugState(session.STATE_RUNNING);
						StudioDebugCorePlugin.debug("Resuming End of RTC.");
					} else {
						session.cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_STEPOUT);
						session.cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_ENDOFMETHOD);
						eventState.setResume(onLocatableEvent(session,bpe,rdt));
						eventState.setHandled(true);
					}
				} else { // unknown type
					eventState.setResume(onLocatableEvent(session,bpe,rdt));
					eventState.setHandled(false);
					StudioDebugCorePlugin.log("Breakpoint type not found. Breakpoint encountered on line " + source + ":" + line);
				}

				if (!eventState.canResume()) {
					rdt.clearPreviousStep();
					session.setDebugState(session.STATE_BREAKPOINT);
				}
			}

		} catch (CoreException e) {
			session.logError(e);
		}
		return eventState;
	}
	
	

}
