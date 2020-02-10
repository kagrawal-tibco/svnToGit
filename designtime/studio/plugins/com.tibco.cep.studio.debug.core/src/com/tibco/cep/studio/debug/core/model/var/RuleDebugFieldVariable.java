package com.tibco.cep.studio.debug.core.model.var;

import com.sun.jdi.Field;
import com.sun.jdi.ReferenceType;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class RuleDebugFieldVariable extends RuleDebugVariable implements IRuleDebugFieldVariable{

	public RuleDebugFieldVariable(RuleDebugStackFrame frame,
			RuleDebugThread tinfo, String name, Field field,
			ReferenceType declaringType) {
		super(frame, tinfo, name, declaringType.getValue(field));
//		init();
	}	

}
