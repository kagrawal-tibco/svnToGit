package com.tibco.cep.studio.debug.core.model.var;


import java.text.MessageFormat;

import com.sun.jdi.LocalVariable;
import com.sun.jdi.NativeMethodException;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class RuleDebugLocalVariable extends RuleDebugVariable implements
		IRuleDebugLocalVariable {
	
	private LocalVariable localVar;
	
	public RuleDebugLocalVariable(
			RuleDebugStackFrame frame,
			RuleDebugThread tinfo, String varName, Value jdiValue) {
		super(frame, tinfo, varName, jdiValue);
	}

	public RuleDebugLocalVariable(
			RuleDebugStackFrame frame,
			RuleDebugThread tinfo, LocalVariable localVar) {
		super(frame, tinfo, localVar.name(), frame.getFrame().getValue(
				localVar));
		setLocal(localVar);
	}

	@Override
	public LocalVariable getLocal() {
		return localVar;
	}

	@Override
	public void setLocal(LocalVariable localVariable) {
		this.localVar = localVariable;
		setName(this.localVar.name());
		Value val = null;
		try {
			val = getStackFrame().getFrame().getValue(this.localVar);
		} catch (NativeMethodException e) {
		} catch (RuntimeException e) {
			StudioDebugCorePlugin.log(MessageFormat.format("{0} occurred getting variable value.",new Object[] {e.toString()}), e); 
		}
		setJdiValue(val);
	}

}
