package com.tibco.cep.studio.debug.core.model.var;

import org.eclipse.debug.core.DebugException;

import com.sun.jdi.LocalVariable;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class ConceptVariableProvider implements IRuleDebugVariableProvider {

	public ConceptVariableProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canProvide(ObjectReference objRef) {
		return DebuggerSupport.isConcept(objRef);
	}

	@Override
	public AbstractDebugVariable getVariable(RuleDebugStackFrame frame,RuleDebugThread tinfo,LocalVariable localVar, ObjectReference objRef, String varName) throws DebugException {
		AbstractDebugVariable var = null;
		if (DebuggerSupport.isScorecard(objRef)) {
			StudioDebugCorePlugin.debug("Returning the Scorecard Var - "
					+ varName);
			var = new ScorecardVariable(frame, tinfo, localVar);
		} else {
			StudioDebugCorePlugin.debug("Returning the Concept Var - " + varName);
			var = new ConceptVariable(frame, tinfo, localVar);
		}
		return var;
	}

	@Override
	public AbstractDebugVariable getVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo, Value jdiValue, String varName) throws DebugException {
		if (DebuggerSupport.isScorecard((ObjectReference) jdiValue)) {
			StudioDebugCorePlugin.debug("Returning the Scorecard Var - " + varName);
			return new ScorecardVariable(frame, tinfo, (ObjectReference) jdiValue, varName, jdiValue);
		} else {
			System.out.println("Returning the Concept Var - " + varName);
			return new ConceptVariable(frame, tinfo, (ObjectReference) jdiValue, varName, jdiValue);
		}
	}

}
