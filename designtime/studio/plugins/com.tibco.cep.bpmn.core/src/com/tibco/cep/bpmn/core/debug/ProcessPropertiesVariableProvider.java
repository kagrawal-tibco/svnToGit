package com.tibco.cep.bpmn.core.debug;

import org.eclipse.debug.core.DebugException;

import com.sun.jdi.LocalVariable;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
import com.tibco.cep.studio.debug.core.model.var.AbstractDebugVariable;
import com.tibco.cep.studio.debug.core.model.var.IRuleDebugVariableProvider;

public class ProcessPropertiesVariableProvider implements IRuleDebugVariableProvider {

	public ProcessPropertiesVariableProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canProvide(ObjectReference objRef) {
		return DebuggerSupport.isProcessVariables(objRef);
	}

	@Override
	public AbstractDebugVariable getVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo, LocalVariable localVar, ObjectReference objRef, String varName)
			throws DebugException {
		return  new ProcessPropertiesVariable(frame, tinfo, localVar);
	}

	@Override
	public AbstractDebugVariable getVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo, Value jdiValue, String varName) throws DebugException {
		System.out.println("Returning the Concept Var - " + varName);
		return new ProcessPropertiesVariable(frame, tinfo, (ObjectReference) jdiValue, varName, jdiValue);
	}

}
