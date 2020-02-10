package com.tibco.cep.studio.debug.core.model.var;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.Field;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class StateMachinePropertyVariable extends RuleDebugVariable {

	/**
	 * @param frame
	 * @param tinfo
	 * @param name
	 * @param value
	 */
	public StateMachinePropertyVariable(RuleDebugStackFrame frame,
			RuleDebugThread tinfo, String name, Value value) {
		super(frame, tinfo, name, value);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void processChildren() throws DebugException {
		try {
			ArrayList<IVariable> vars = new ArrayList<IVariable>();
			HashMap<Field,Value> smprops = DebuggerSupport.getStateMachineStates(getThreadInfo(), (ObjectReference) getJdiValue());
			Set<Map.Entry<Field, Value>> smpropset = smprops.entrySet();
			for (Map.Entry<Field, Value> smproppair : smpropset) {
				Value smpropv = smproppair.getValue();
				String smName = DebuggerSupport.getStateName(getThreadInfo(), (ObjectReference)smpropv);
				boolean isActive = DebuggerSupport.isStateActive(getThreadInfo(), (ObjectReference)smpropv);
				boolean isReady = DebuggerSupport.isStateReady(getThreadInfo(), (ObjectReference)smpropv);
				boolean isExited = DebuggerSupport.isStateExited(getThreadInfo(), (ObjectReference)smpropv);
				boolean isAmbiguous = DebuggerSupport.isStateAmbiguous(getThreadInfo(), (ObjectReference)smpropv);
				boolean isTimeoutSet = DebuggerSupport.isStateTimeoutSet(getThreadInfo(), (ObjectReference)smpropv);
				String state = "";
				if(isActive) {
					state = "Active";
				} else if(isReady) {
					state = "Ready";					
				} else if(isExited) {
					state = "Exited";
				} else if(isAmbiguous) {
					state = "Ambiguous";
				} else if(isTimeoutSet) {
					state = "Time out";
				}
				StateVariable var = new StateVariable(getStackFrame(),getThreadInfo(),smName,state);
				vars.add(var);				
			}
			setChildren(vars);
		} catch (ClassNotLoadedException e) {
			StudioDebugCorePlugin.log(e);
		}
	}
	
	@Override
	public String getValueString() throws DebugException {
		if(getChildren() != null) {
			return ""+getChildren().size();
		} else 
			return "0";
	}
	

}
