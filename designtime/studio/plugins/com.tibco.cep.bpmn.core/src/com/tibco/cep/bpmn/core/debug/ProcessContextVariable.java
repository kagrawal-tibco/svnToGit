package com.tibco.cep.bpmn.core.debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.LocalVariable;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
import com.tibco.cep.studio.debug.core.model.var.ProcessContextPropertiesVariable;
import com.tibco.cep.studio.debug.core.model.var.RuleDebugLocalVariable;
import com.tibco.cep.studio.debug.core.model.var.RuleDebugVariable;

public class ProcessContextVariable extends RuleDebugLocalVariable {
	
	protected ObjectReference processContextRef;
	protected ObjectReference processVariables;



	public ProcessContextVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo, LocalVariable localVar) throws DebugException {
		super(frame,tinfo,localVar);
		setProcessContextRef((ObjectReference) getJdiValue());
		init();
	}

	public ProcessContextVariable(RuleDebugStackFrame frame, RuleDebugThread tinfo, ObjectReference processContext, String varName, Value jdiValue) throws DebugException {
		super(frame,tinfo,varName,jdiValue);
		setProcessContextRef(processContext);
		init();
	}
	
	@Override
	public void init() throws DebugException {
		if (getJdiValue() instanceof ObjectReference
			&& (DebuggerSupport.isProcessContext((ObjectReference) getJdiValue()))) {
			this.processVariables = getProcessVariables(getThreadInfo(),(ObjectReference) getJdiValue());
			
		}
	}
	/**
	 * @param eventThread
	 * @param jdiValue
	 * @return
	 * @throws Exception
	 */
	protected ObjectReference getProcessVariables(RuleDebugThread eventThread, ObjectReference jdiValue) throws DebugException {
		return (DebuggerSupport.getProcessVariables(eventThread, (ObjectReference) jdiValue));
	}
	
	
	
	
	/**
	 * @return the processContextRef
	 */
	public ObjectReference getProcessContextRef() {
		return processContextRef;
	}

	/**
	 * @param processContextRef the processContextRef to set
	 */
	public void setProcessContextRef(ObjectReference processContextRef) {
		this.processContextRef = processContextRef;
	}

	/**
	 * @return the processVariables
	 */
	public ObjectReference getProcessVariables() {
		return processVariables;
	}

	/**
	 * @param processVariables the processVariables to set
	 */
	public void setProcessVariables(ObjectReference processVariables) {
		this.processVariables = processVariables;
	}
	
	
	
	/**
	 * 
	 */
	public void processChildren() {
		setChildren(Arrays.asList(getProcessContextChildrenFromObjectReference(getProcessVariables()))); 
	}
	

	/**
	 * 
	 * @param obj
	 * @return
	 */
	protected IVariable[] getProcessContextChildrenFromObjectReference(ObjectReference obj) {
        if (obj == null)
        	return new IVariable[0];
        List<IVariable> vars = new ArrayList<IVariable>();
        RuleDebugVariable crow = getProcessVariableRows();
        if(crow != null) {
        	vars.add(crow);
        }
        return vars.toArray(new IVariable[vars.size()]);
    }
	
	
    /**
     * 
     * @return
     */
	private RuleDebugVariable getProcessVariableRows() {
		
		ProcessContextPropertiesVariable properties = new ProcessContextPropertiesVariable(getStackFrame(),
																	getThreadInfo(),
																	ProcessContextPropertiesVariable.PROPERTIES_ROW_NAME,
																	getProcessVariables());	
		
		
    	return properties;
	}







}
