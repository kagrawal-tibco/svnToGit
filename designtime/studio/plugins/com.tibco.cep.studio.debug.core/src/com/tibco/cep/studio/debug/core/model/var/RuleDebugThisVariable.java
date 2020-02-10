package com.tibco.cep.studio.debug.core.model.var;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class RuleDebugThisVariable extends RuleDebugVariable implements IRuleDebugThisVariable{
	

	public RuleDebugThisVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo,
			String name, Value value) {
		super(frame, tinfo, name,value);
	}

	@Override
	public ObjectReference retrieveValue() {
		return (ObjectReference) getJdiValue();
	}
	
	
	
}
