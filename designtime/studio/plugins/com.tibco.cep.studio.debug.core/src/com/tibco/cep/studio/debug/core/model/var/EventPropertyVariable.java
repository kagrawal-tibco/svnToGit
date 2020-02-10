/**
 * 
 */
package com.tibco.cep.studio.debug.core.model.var;


import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

/**
 * @author pdhar
 *
 */
public class EventPropertyVariable extends RuleDebugVariable implements IVariable {
	
	private int type;
	private String typeStr;

	/**
	 * @param tinfo
	 * @param name
	 * @param jdiValue
	 * @param type
	 * @param target
	 */
	public EventPropertyVariable(
			RuleDebugStackFrame frame,
			RuleDebugThread tinfo, String name, Value jdiValue,
			int type) {
		super(frame, tinfo, name, jdiValue);
		setType(type);
//		setTypeStr(DebuggerSupport.getPropertyTypeString(getThreadInfo().thread(), type));
		setTypeStr(DebuggerSupport.getPropertyTypeString(getThreadInfo(), type));
	}
	
	/**
	 * @return
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the typeStr
	 */
	public String getTypeStr() {
		return typeStr;
	}

	/**
	 * @param typeStr the typeStr to set
	 */
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}


	@Override
	public String getName() {
		final String s = getTypeStr() != null ? getTypeStr(): String.valueOf(getType());
		return super.getName()+" [Type:"+ s +"]";
	}
	


}
