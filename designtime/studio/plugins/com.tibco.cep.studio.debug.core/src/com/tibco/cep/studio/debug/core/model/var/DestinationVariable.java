/**
 * 
 */
package com.tibco.cep.studio.debug.core.model.var;

import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

/**
 * @author pdhar
 *
 */
public class DestinationVariable extends RuleDebugVariable implements IVariable {

	/**
	 * @param target
	 * @param tinfo
	 * @param name
	 * @param value
	 */
	public DestinationVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo,
			String name, Value value) {
		super(frame, tinfo, "Destination", value);
	}
	
	
	@Override
	public void init() {
		StudioDebugCorePlugin.debug("************ Initialized Destination Var *************");
	}

}
