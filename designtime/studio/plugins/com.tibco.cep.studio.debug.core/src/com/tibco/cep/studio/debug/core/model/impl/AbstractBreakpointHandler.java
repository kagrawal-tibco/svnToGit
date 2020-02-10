package com.tibco.cep.studio.debug.core.model.impl;

import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STATUS_COMPLETED;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;

import com.sun.jdi.Location;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.event.StepEvent;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.DebuggerConstants;
import com.tibco.cep.studio.debug.core.model.IBreakpointHandler;
import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.IRuleDebugTarget;
import com.tibco.cep.studio.debug.core.model.RuleDebugElement;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
import com.tibco.cep.studio.debug.core.model.impl.AbstractSession.EventState;
import com.tibco.cep.studio.debug.smap.SourceMapper;

public abstract class AbstractBreakpointHandler implements IBreakpointHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.debug.core.model.IBreakpointHandler#handleBreakpoint
	 * (com.tibco.cep.studio.debug.core.model.impl.AbstractSession,
	 * com.sun.jdi.event.BreakpointEvent, java.lang.String,
	 * com.tibco.cep.studio.debug.core.model.RuleDebugThread,
	 * com.tibco.cep.studio.debug.core.model.IRuleBreakpoint,
	 * com.tibco.cep.studio.debug.core.model.impl.AbstractSession.EventState)
	 */
	@SuppressWarnings("finally")
	@Override
	public EventState handleBreakpoint(AbstractSession session,EventSet eventSet, final BreakpointEvent bpe, String bpType, RuleDebugThread rdt, IRuleBreakpoint bp,
			EventState eventState) {

		if (bpType != null) {
			if (bpType.equals(DebuggerConstants.BREAKPOINT_DEBUGGER_SERVICE)) {
				try {
					// Always clear the debugger service breakpoint because
					// we
					// do not want to resume while the session is suspended
					// by a break point or step request
					// let the user create the break point on demand
					session.cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_DEBUGGER_SERVICE);
					boolean resume = session.onInputVmTask(bpe);
					eventState.setResume(resume);
				} catch (VMDisconnectedException vme) {
					StudioDebugCorePlugin.log(vme);
				} finally {
					eventState.setHandled(true); // debugger service handle here
					return eventState;
				}
			} else if (bpType.equals(DebuggerConstants.BREAKPOINT_DEBUG_TASK_RESPONSE)) {
				try {
					// Always clear the debug task response breakpoint
					// because we
					// do not want to resume while the session is suspended
					// by a break point or step request
					// let the user create the break point on demand
					session.cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_DEBUG_TASK_RESPONSE);
					boolean resume = session.onResponseAction(bpe);
					eventState.setResume(resume);
				} catch (VMDisconnectedException vme) {
					StudioDebugCorePlugin.log(vme);
				} finally {
					eventState.setHandled(true); // debugger service handle
													// here
					return eventState;
				}
			} else if (bpType.equals(DebuggerConstants.BREAKPOINT_TASK_REGISTRY_INIT)) {
				try {
					synchronized (rdt) {
						try {
							rdt.setRunning(false);
							rdt.addCurrentBreakpoint(bp);
							RuleDebugStackFrame topStackFrame = (RuleDebugStackFrame) rdt.getTopStackFrame();
							ObjectReference trInstance = topStackFrame.getUnderlyingThisObject();
							session.initTaskRegistry(rdt, trInstance);
						} finally {
							rdt.removeCurrentBreakpoint(bp);
							session.cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_TASK_REGISTRY_INIT);
							rdt.resumedByVM();
						}
					}
				} catch (VMDisconnectedException vme) {
					StudioDebugCorePlugin.log(vme);
				} catch (DebugException e) {
					StudioDebugCorePlugin.log("Failed to load Task Registry", e);
				} finally {
					eventState.setResume(true);
					eventState.setHandled(true); // debugger service handle
													// here
					return eventState;
				}
			} else if (bpType.equals(DebuggerConstants.BREAKPOINT_INIT_PROJECT)) {
				try {
					synchronized (rdt) {
						IRuleBreakpoint tbp = AbstractSession.getBreakPointFromEvent(bpe);
						try {
							rdt.setRunning(false);
							rdt.addCurrentBreakpoint(tbp);
							RuleDebugStackFrame topStackFrame = (RuleDebugStackFrame) rdt.getTopStackFrame();
							ObjectReference rspInstance = topStackFrame.getUnderlyingThisObject();
							session.initSourceMap(rdt, rspInstance);
						} finally {
							rdt.removeCurrentBreakpoint(tbp);
							session.cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_INIT_PROJECT);
							rdt.resumedByVM();
						}
					}
				} catch (VMDisconnectedException vme) {
					StudioDebugCorePlugin.log(vme);
				} catch (DebugException e) {
					StudioDebugCorePlugin.log("Failed to load source maps", e);
				} finally {
					eventState.setResume(true);
					eventState.setHandled(true); // debugger service handle
													// here
					return eventState;
				}
			} else if (bpType.equals(DebuggerConstants.BREAKPOINT_WM_START)) {
				try {
					synchronized (rdt) {
						rdt.setRunning(false);
						rdt.addCurrentBreakpoint(AbstractSession.getBreakPointFromEvent(bpe));
						// getDebugThreadInfo().fireSuspendEvent(DebugEvent.BREAKPOINT);
						session.cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_WM_START);
						if (session.getDebugTarget() instanceof IRuleDebugTarget) {
							((IRuleDebugTarget) session.getDebugTarget()).initializeBreakpoints();
						}
						// getDebugThreadInfo().fireResumeEvent(DebugEvent.CLIENT_REQUEST);
						rdt.resumedByVM();
					}

				} catch (VMDisconnectedException vme) {
					StudioDebugCorePlugin.log(vme);
				} catch (DebugException e) {
					StudioDebugCorePlugin.log("Failed to initialize breakpoints", e);
				} finally {
					eventState.setResume(true);
					eventState.setHandled(true); // debugger service handle
													// here
					return eventState;
				}
			} else {
				eventState.setHandled(false); // not handled here let the
				// subclass handle it
			}
		} else {
			StudioDebugCorePlugin.debug("Unknown breakpoint type:" + bpe.location());
		}

		eventState.setHandled(false); // not handled here let the subclass
										// handle it
		return eventState;
	}

	/**
	 * @param session
	 * @param le
	 * @param rdt
	 * @return
	 * @throws DebugException 
	 */
	public boolean onLocatableEvent(AbstractSession session, LocatableEvent le, RuleDebugThread rdt) throws DebugException {

		final Location location = le.location();

		final String javaName = location.declaringType().name();

		final int lineNo = location.lineNumber();

		SourceMapper sourceMapper = session.getRuleDebugTarget().getSourceMapper();

		final IMappedResourcePosition loc = sourceMapper.getBEPosition(javaName, lineNo);

		synchronized (rdt) {
			try {
				if (loc != null) {
					if (!loc.equals(rdt.getLastPosition())) {
						rdt.setLastPosition(loc);
					} else {
						// check if the same line match caused by
						// step,breakpoint event pair then the
						// first event in the pair must have suspended it
						// already
						if (!rdt.isSuspended()) {
							// do not remove the existing step request and let
							// it continue
							rdt.setRunning(true);
							rdt.resumedByVM();
							return true;
						}
					}
					int lastActionType = rdt.getDebugActionType();
					if (le instanceof BreakpointEvent) {
						IRuleBreakpoint bp = RuleDebugElement.getBreakPointFromEvent((BreakpointEvent) le);
						if (bp != null) {
							// this is user defined breakpoint
							StudioDebugCorePlugin.debug("Suspending at Location:" + location.toString());
							rdt.clearPreviousStep();
							rdt.addCurrentBreakpoint(bp);
							rdt.setRunning(false);
							rdt.queueSuspendEvent(DebugEvent.BREAKPOINT);
							rdt.setDebugActionStateFlags(lastActionType | DEBUG_ACTION_STATUS_COMPLETED);
						}
					} else if (le instanceof StepEvent) {
						// found the next step line
						StudioDebugCorePlugin.debug("Stepping on line: " + location);
						rdt.setRunning(false);
						rdt.setStepping(false);
						rdt.queueSuspendEvent(DebugEvent.STEP_END);
						rdt.setDebugActionStateFlags(lastActionType | DEBUG_ACTION_STATUS_COMPLETED);
					}
				} else {
					// no displayable location found in rule code
					// therefore continue the last step request again
					// by not removing it from the EventRequestManager
					if (le instanceof BreakpointEvent) {
						rdt.setRunning(false);
						rdt.addCurrentBreakpoint(RuleDebugElement.getBreakPointFromEvent((BreakpointEvent) le));
					}
					rdt.setRunning(true);
					rdt.resumedByVM();
					return true;
				}
			} catch (Exception e) {
				StudioDebugCorePlugin.log(e);
			}
		} // end sync block
		return false;
	}

}