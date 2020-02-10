package com.tibco.cep.studio.debug.core.model.var;

import org.eclipse.debug.core.DebugException;

import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class StateVariable extends RuleDebugVariable {
	
	String state;



	/**
	 * @param frame
	 * @param tinfo
	 * @param name
	 * @param value
	 */
	public StateVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo,
			String name, String state) {
		super(frame, tinfo, name, null);
		setState(state);
	}
	
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String getValueString() throws DebugException {
		return getState();
	}
	
	

}
