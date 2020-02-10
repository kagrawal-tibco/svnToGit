/**
 * 
 */
package com.tibco.cep.studio.debug.core.model.var;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.ArrayReference;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

/**
 * @author pdhar
 *
 */
public class RuleAgendaVariable extends RuleDebugVariable implements IVariable {
	

	/**
	 * @param target
	 * @param tinfo
	 * @param name
	 * @param value
	 */
	public RuleAgendaVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo,
			String name, Value value) {
		super(frame, tinfo, name, value);
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void processChildren() throws DebugException {
		StudioDebugCorePlugin.debug("Fetching children for " + getName());
            List<IVariable> vars = new ArrayList<IVariable>();
		Value agendaParamVal = DebuggerSupport.getAgendaParams(getThreadInfo(),
				(ObjectReference) getJdiValue());
		if (agendaParamVal != null && agendaParamVal instanceof ArrayReference) {

			ArrayReference ruleParams = (ArrayReference) agendaParamVal;
			try {
				ruleParams.disableCollection();
				List<Value> agendaParams = ruleParams.getValues();
				Iterator<Value> it = agendaParams.iterator();
				int i = 0;
				while (it.hasNext()) {
					Value agendaParam = it.next();
					AbstractDebugVariable node = RuleDebugVariableFactory
							.getVarRow(getStackFrame(), getThreadInfo(), "obj["
									+ i + "]", (ObjectReference) agendaParam);
					if (node != null) {
						vars.add(node);
					}
					i++;
				}
			} finally {
				ruleParams.enableCollection();
			}
		}
		setChildren(vars);
	}

	public ObjectReference getLocal() {
		return (ObjectReference) getJdiValue();
	}
	
	public void setLocal(Value value){
		setJdiValue(value);
	}

}
