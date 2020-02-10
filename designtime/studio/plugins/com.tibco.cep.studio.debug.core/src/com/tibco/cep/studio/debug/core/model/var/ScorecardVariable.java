/**
 * 
 */
package com.tibco.cep.studio.debug.core.model.var;


import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.LocalVariable;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

/**
 * @author pdhar
 *
 */
public class ScorecardVariable extends ConceptVariable implements IVariable {

	/**
	 * @param tinfo
	 * @param scorecard TODO
	 * @param name
	 * @param value
	 * @param target
	 */
	public ScorecardVariable(RuleDebugStackFrame frame,RuleDebugThread tinfo, ObjectReference scorecard,
			String name, Value value) throws DebugException {
		super(frame,tinfo, scorecard, name, value);
	}
	/**
	 * 
	 * @param frame
	 * @param tinfo
	 * @param localVar
	 */
	public ScorecardVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo,
			LocalVariable localVar) throws DebugException {
		super(frame,tinfo,localVar);
	}

	@Override
	public void init() throws DebugException {
		if (DebuggerSupport.isScorecard((ObjectReference) getJdiValue())) {
			super.init();
			String scname = DebuggerSupport.value2String(getThreadInfo(), getEntityExtId());
			setName(scname.substring(scname.lastIndexOf('/')+1));
		}
	}
	
}
