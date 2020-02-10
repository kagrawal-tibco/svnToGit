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
public class TypeIdVariable extends RuleDebugVariable implements IVariable {

	
	/**
	 * @param frame
	 * @param tinfo
	 * @param name
	 * @param value
	 */
	public TypeIdVariable(RuleDebugStackFrame frame,
			RuleDebugThread tinfo, String name, Value value) {
		super(frame, tinfo, "TypeId", value);
	}
	
	
	@Override
	public void init() {
		StudioDebugCorePlugin.debug("************ Initialized using TypeId Row *************");
	}

}
