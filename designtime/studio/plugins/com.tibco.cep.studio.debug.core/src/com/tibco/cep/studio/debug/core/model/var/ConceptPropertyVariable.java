package com.tibco.cep.studio.debug.core.model.var;

import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public abstract class ConceptPropertyVariable extends RuleDebugVariable implements
		IVariable {
	

	
	private ObjectReference conceptRef;
	private int rowType;
	
	/**
	 * 
	 * @param frame
	 * @param tinfo
	 * @param concept
	 * @param name
	 * @param value
	 * @param type
	 */
	public ConceptPropertyVariable(
			RuleDebugStackFrame frame,
			RuleDebugThread tinfo, ObjectReference concept,
			String name, Value value, int type) {
		super(frame, tinfo, name, value);
		this.conceptRef = concept;
		this.rowType = type;
	}
	
	
	
	/**
	 * @return the conceptRef
	 */
	public ObjectReference getConceptRef() {
		return conceptRef;
	}



	/**
	 * @param conceptRef the conceptRef to set
	 */
	public void setConceptRef(ObjectReference conceptRef) {
		this.conceptRef = conceptRef;
	}



	public int getRowType() {
		return rowType;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.rowType = type;
	}
	
	
	
	

}
