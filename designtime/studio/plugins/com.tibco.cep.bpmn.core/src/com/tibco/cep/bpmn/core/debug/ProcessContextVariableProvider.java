package com.tibco.cep.bpmn.core.debug;

import org.eclipse.debug.core.DebugException;

import com.sun.jdi.LocalVariable;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
import com.tibco.cep.studio.debug.core.model.var.AbstractDebugVariable;
import com.tibco.cep.studio.debug.core.model.var.IRuleDebugVariableProvider;

public class ProcessContextVariableProvider implements IRuleDebugVariableProvider {
	
	

	public ProcessContextVariableProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canProvide(ObjectReference objRef) {
		return DebuggerSupport.isProcessContext(objRef);
	}

	@Override
	public AbstractDebugVariable getVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo, LocalVariable localVar, ObjectReference objRef, String varName)
			throws DebugException {
		return new ProcessContextVariable(frame, tinfo, localVar);
	}

	@Override
	public AbstractDebugVariable getVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo, Value jdiValue, String varName) throws DebugException {
		StudioDebugCorePlugin.debug("Returning the Process Context Var - " + varName);
		return new ProcessContextVariable(frame, tinfo, (ObjectReference) jdiValue, varName, jdiValue);
	}

}
