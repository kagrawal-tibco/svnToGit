package com.tibco.cep.bpmn.core.debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.LocalVariable;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
import com.tibco.cep.studio.debug.core.model.var.AbstractDebugVariable;
import com.tibco.cep.studio.debug.core.model.var.RuleDebugLocalVariable;
import com.tibco.cep.studio.debug.core.model.var.RuleDebugVariable;
import com.tibco.cep.studio.debug.core.model.var.RuleDebugVariableFactory;

public class ProcessPropertiesVariable extends RuleDebugLocalVariable {
	
	protected ObjectReference variablesRef;
	private Map<String,ObjectReference> variableMap = new LinkedHashMap<String,ObjectReference>();

	public ProcessPropertiesVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo, LocalVariable localVar) throws DebugException {
		super(frame,tinfo,localVar);
		setVariablesRef((ObjectReference) getJdiValue());
		init();
	}

	public ProcessPropertiesVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo, ObjectReference variablesContext, String varName, Value jdiValue) throws DebugException {
		super(frame,tinfo,varName,jdiValue);
		setVariablesRef(variablesContext);
		init();
	}
	
	

	/**
	 * @return the variablesRef
	 */
	public ObjectReference getVariablesRef() {
		return variablesRef;
	}

	/**
	 * @param variablesRef the variablesRef to set
	 */
	public void setVariablesRef(ObjectReference variablesRef) {
		this.variablesRef = variablesRef;
	}

	@Override
	public void init() throws DebugException {
		if (getJdiValue() instanceof ObjectReference
				&& (DebuggerSupport.isProcessVariables((ObjectReference) getJdiValue()))) {
				this.variableMap = getVariables(getThreadInfo(),(ObjectReference) getJdiValue());
				
		}
	}
	
	/**
	 * @param eventThread
	 * @param jdiValue
	 * @return
	 * @throws Exception
	 */
	protected Map<String,ObjectReference> getVariables(RuleDebugThread eventThread, ObjectReference jdiValue) throws DebugException {
		return (DebuggerSupport.getVariables(eventThread, (ObjectReference) jdiValue));
	}

	@Override
	public void processChildren() throws DebugException {
		setChildren(Arrays.asList(getVariablesFromObjectReference(getVariablesRef()))); 

	}

	private IVariable[] getVariablesFromObjectReference(ObjectReference obj) throws DebugException {
		if (obj == null)
        	return new IVariable[0];
        List<IVariable> vars = new ArrayList<IVariable>();
        List<RuleDebugVariable> crows = getVariableRows();
        if(crows != null) {
        	vars.addAll(crows);
        }
        return vars.toArray(new IVariable[vars.size()]);
	}
	
	 /**
     * 
     * @return
	 * @throws DebugException 
     */
	private List<RuleDebugVariable> getVariableRows() throws DebugException {
		List<RuleDebugVariable> vlist = new ArrayList<RuleDebugVariable>();
		if(variableMap != null && !variableMap.isEmpty()){
			for(Entry<String, ObjectReference> entry:variableMap.entrySet()) {
			  AbstractDebugVariable var = RuleDebugVariableFactory.getVarRow(getStackFrame(), getThreadInfo(), entry.getKey(), entry.getValue());
			  if(var instanceof RuleDebugVariable) {
				  vlist.add((RuleDebugVariable) var);
			  }
			}			
		}		
		
    	return vlist;
	}
	
	@Override
	public void clearChildren() {
		super.clearChildren();
		if(variableMap != null) {
			variableMap.clear();
		}
	}



}
