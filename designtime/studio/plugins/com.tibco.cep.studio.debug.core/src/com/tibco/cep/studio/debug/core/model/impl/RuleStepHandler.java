package com.tibco.cep.studio.debug.core.model.impl;

import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STATUS_INVOKE_USER;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STEP_IN;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STEP_OUT;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STEP_OVER;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;

import com.sun.jdi.Location;
import com.sun.jdi.request.StepRequest;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.debug.core.model.AbstractDebugTarget;
import com.tibco.cep.studio.debug.core.model.DebuggerConstants;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.core.model.IStepHandler;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class RuleStepHandler extends AbstractStepHandler implements IStepHandler {

	@Override
	public boolean handlesSession(String currentRuleSession, Map<String, CacheAgent.Type> targetAgentMap) {
		if(currentRuleSession != null ) {
			if (targetAgentMap != null && targetAgentMap.get(currentRuleSession) == CacheAgent.Type.PROCESS) {
				return false;
			}
			return true;
		}
		return false;
	}

	public void stepOver(final RuleDebugThread rdt, final RuleDebugStackFrame frame) throws DebugException {
		final Map<Object, Object> props = new HashMap<Object, Object>();
		rdt.setDebugActionStateFlags(DEBUG_ACTION_STEP_OVER | DEBUG_ACTION_STATUS_INVOKE_USER);
		props.put(DebuggerConstants.ACTION_FLAGS, DEBUG_ACTION_STEP_OVER | DEBUG_ACTION_STATUS_INVOKE_USER);
		final Location stackLocation = frame.getUnderlyingStackFrame().location();
		IMappedResourcePosition pos = ((AbstractDebugTarget) rdt.getDebugTarget()).getSourceMapper().getBEPosition(stackLocation.declaringType().name(),
				stackLocation.lineNumber());
		IPath resourcePath = ((AbstractDebugTarget) rdt.getDebugTarget()).getEntityResourcePath(pos.getResourceName());
		boolean isJavaSource = (resourcePath != null && resourcePath.getFileExtension().equalsIgnoreCase(CommonIndexUtils.JAVA_EXTENSION));
		rdt.preserveStackFrames();
		if (pos != null && (pos.getLineMask() & MappedResourcePosition.LAST_LINE) != 0) { // code pointer is on the last line
			rdt.createStep(StepRequest.STEP_LINE, StepRequest.STEP_INTO, props, true, isJavaSource);
		} else {
			rdt.createStep(StepRequest.STEP_LINE, StepRequest.STEP_OVER, props, true, isJavaSource);
		}
		rdt.setRunning(true);
		rdt.fireResumeEvent(DebugEvent.STEP_OVER);
		rdt.invokeThread();
	}
	
	@Override
	public void stepInto(final RuleDebugThread rdt,final RuleDebugStackFrame frame) throws DebugException {
		rdt.setDebugActionStateFlags(DEBUG_ACTION_STEP_IN | DEBUG_ACTION_STATUS_INVOKE_USER);
		Map<Object,Object> props = new HashMap<Object,Object>();
		props.put(DebuggerConstants.ACTION_FLAGS, DEBUG_ACTION_STEP_IN | DEBUG_ACTION_STATUS_INVOKE_USER);
		final Location stackLocation = frame.getUnderlyingStackFrame().location();
		IMappedResourcePosition pos = ((AbstractDebugTarget) rdt.getDebugTarget()).getSourceMapper().getBEPosition(stackLocation.declaringType().name(),
				stackLocation.lineNumber());
		IPath resourcePath = ((AbstractDebugTarget) rdt.getDebugTarget()).getEntityResourcePath(pos.getResourceName());
		boolean isJavaSource = (resourcePath != null && resourcePath.getFileExtension().equalsIgnoreCase(CommonIndexUtils.JAVA_EXTENSION));
		rdt.createStep(StepRequest.STEP_LINE, StepRequest.STEP_INTO, props, true, isJavaSource);
		rdt.setRunning(true);
		rdt.preserveStackFrames();
		rdt.fireResumeEvent(DebugEvent.STEP_INTO);
		rdt.invokeThread();		
	}
	
	@Override
	public void stepReturn(final RuleDebugThread rdt ,final RuleDebugStackFrame frame) throws DebugException {
		// step out needs to be handled differently because the caller function is in BE 
		// rete code and may not map to a rule or rule-function code always therefore it is
		// is necessary to find the next step location in the following manner
		// check if the previous stack frame from the top most stack frame maps to a
		// displayable line in a rule or rule-function, if it does then call the step out
		// otherwise follow this method
		// 1. goto the end of the current function
		// 2. put hidden break points on all possible BE code location entry points where it
		//    can hit next and wait for the hit
		// 3. once the hit happens, remove the hidden breakpoints
		try {
			
			IStackFrame[] frames = rdt.getStackFrames();
			Map<Object,Object> props = new HashMap<Object,Object>();
			props.put(DebuggerConstants.ACTION_FLAGS, DEBUG_ACTION_STEP_OUT | DEBUG_ACTION_STATUS_INVOKE_USER);
			if(frames.length > 0 && ((RuleDebugStackFrame)frames[1]).getMappedSourceLine() != -1) {
				rdt.setDebugActionStateFlags(DEBUG_ACTION_STEP_OUT | DEBUG_ACTION_STATUS_INVOKE_USER);
				final Location stackLocation = ((RuleDebugStackFrame)frames[1]).getUnderlyingStackFrame().location();
				IMappedResourcePosition pos = ((AbstractDebugTarget) rdt.getDebugTarget()).getSourceMapper().getBEPosition(stackLocation.declaringType().name(),
						stackLocation.lineNumber());
				IPath resourcePath = ((AbstractDebugTarget) rdt.getDebugTarget()).getEntityResourcePath(pos.getResourceName());
				boolean isJavaSource = (resourcePath != null && resourcePath.getFileExtension().equalsIgnoreCase(CommonIndexUtils.JAVA_EXTENSION));
				rdt.createStep(StepRequest.STEP_LINE, StepRequest.STEP_OUT, props,true, isJavaSource);
				rdt.setRunning(true);
				rdt.preserveStackFrames();
				rdt.fireResumeEvent(DebugEvent.STEP_RETURN);
				rdt.invokeThread();
			} else {
				
				//Temporary Fix : BE-16998 : after step out, next assert doesn't hit the breakpoint
				
//				rdt.setDebugActionStateFlags(DEBUG_ACTION_STEP_IN | DEBUG_ACTION_STATUS_INVOKE_USER);
//				rdt.createStepOutBreakPoints(props);
//				rdt.setRunning(true);
//				rdt.preserveStackFrames();
//				rdt.fireResumeEvent(DebugEvent.STEP_INTO);
//				rdt.invokeThread();
				
				stepInto(rdt, frame);
				
			}// end else
		} catch (CoreException e) {
			rdt.abort("Failed to step out", e);
		}
		
	}

}
