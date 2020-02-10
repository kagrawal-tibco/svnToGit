package com.tibco.cep.studio.debug.core.model.var;

import org.eclipse.debug.core.DebugException;

import com.sun.jdi.LocalVariable;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;


public interface IRuleDebugVariableProvider {
	
	boolean canProvide(ObjectReference objRef);
	
	AbstractDebugVariable getVariable(RuleDebugStackFrame frame,RuleDebugThread tinfo,LocalVariable localVar, ObjectReference objRef, String varName) throws DebugException;

	AbstractDebugVariable getVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo, Value jdiValue, String varName) throws DebugException;

}
