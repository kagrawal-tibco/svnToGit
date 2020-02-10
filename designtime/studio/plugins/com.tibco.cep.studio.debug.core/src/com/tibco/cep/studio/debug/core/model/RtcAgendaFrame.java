package com.tibco.cep.studio.debug.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.StackFrame;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.model.var.AbstractDebugVariable;
import com.tibco.cep.studio.debug.core.model.var.RuleAgendaVariable;
import com.tibco.cep.studio.debug.core.model.var.RuleDebugVariableFactory;

public class RtcAgendaFrame extends RuleDebugStackFrame {

	
	
	
	/**
	 * @param thread
	 * @param sf
	 * @param depth
	 */
	public RtcAgendaFrame(RuleDebugThread thread, StackFrame sf, int depth) {
		super(thread, sf, depth);
	}

	protected List<IVariable> getVariables0() throws DebugException {
		synchronized (fThread) {
			if (fVariables == null) {
				fVariables= new ArrayList<IVariable>();
				List<Value> agendaVars = RuleDebugVariableFactory.getRuleAgendaItems(this,fThread);
				for(Value v: agendaVars) {
					RuleAgendaVariable var = RuleDebugVariableFactory.createRuleAgendaVariable(this, fThread, (ObjectReference) v);
					fVariables.add(var);
				}
			} else if (fRefreshVariables) {
				updateVariables();
			}
			fRefreshVariables = false;
			return fVariables;
		}
	}
	
	@Override
	protected void updateVariables() throws DebugException {
		int index= 0;
		List<Value> locals=  RuleDebugVariableFactory.getRuleAgendaItems(this,(RuleDebugThread)getThread());
		
		int localIndex= -1;
		while (index < fVariables.size()) {
			Object var= fVariables.get(index);
			if (var instanceof RuleAgendaVariable) {
				RuleAgendaVariable local= (RuleAgendaVariable) fVariables.get(index);
				localIndex= locals.indexOf(local.getLocal());
				if (localIndex >= 0) {
					// update variable with new underling JDI LocalVariable
					local.setLocal(locals.get(localIndex));
					locals.remove(localIndex);
					index++;
				} else {
					// remove variable
					fVariables.remove(index);
				}
			} else {
				//field variable of a static frame
				index++;
			}
		}
		// add any new locals
		Iterator<Value> newOnes= locals.iterator();
		while (newOnes.hasNext()) {
			Value var= newOnes.next();
			AbstractDebugVariable rdv = RuleDebugVariableFactory.createRuleAgendaVariable(this, fThread, (ObjectReference) var);
			fVariables.add(rdv);
		}
	}

}
