package com.tibco.cep.studio.debug.core.model.var;

import org.eclipse.debug.core.DebugException;

import com.sun.jdi.LocalVariable;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class EventVariableProvider implements IRuleDebugVariableProvider {

	public EventVariableProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canProvide(ObjectReference objRef) {
		return DebuggerSupport.isEvent(objRef);
	}

	@Override
	public AbstractDebugVariable getVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo, LocalVariable localVar, ObjectReference objRef, String varName)
			throws DebugException {
		StudioDebugCorePlugin.debug("Returning the Event Var - " + varName);
		return  new EventVariable(frame, tinfo, localVar);
	}
	
	@Override
	public AbstractDebugVariable getVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo, Value jdiValue, String varName) throws DebugException {
		StudioDebugCorePlugin.debug("Returning the Event Var - " + varName);
		return new EventVariable(frame, tinfo, varName, jdiValue);
	}


}
