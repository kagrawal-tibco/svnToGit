/**
 * 
 */
package com.tibco.cep.studio.debug.core.model.var;

import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.ObjectReference;
import com.tibco.cep.studio.debug.core.model.DebuggerConstants;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

/**
 * @author pdhar
 *
 */
public class ConceptPropertyHistoryVariable extends ConceptPropertyVariable implements
		IVariable {
	

	/**
	 * @param target
	 * @param tinfo
	 * @param histTime
	 * @param value
	 * @param rowTypeHistory
	 */
	public ConceptPropertyHistoryVariable(RuleDebugStackFrame frame,
			RuleDebugThread tinfo,
			long histTime,
			ObjectReference value,
			int rowTypeHistory) {
		super( frame,
				tinfo,
				null,
				DebuggerSupport.formatAsTime(histTime, DebuggerConstants.DEBUGGER_DATE_FORMAT),
				value, rowTypeHistory);
		
	}

}
