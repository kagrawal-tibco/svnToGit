package com.tibco.cep.studio.debug.core.model.var;

import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class TypeVariable extends RuleDebugVariable implements IVariable {
	public final static String FIELD_FILTER = "type";
	@SuppressWarnings("unused")
	private String type = "";
	private int rowType = -1;
	


	public TypeVariable(RuleDebugStackFrame frame,RuleDebugThread tinfo, String name, Value jdiValue,
			int rowtypeConcept) {
		super(frame, tinfo, "Type", jdiValue);
		this.rowType = rowtypeConcept;
	}
	
	@Override
	public void init() {
		System.out.println("************ Initialized Type Variable - " + rowType + " *************");
	}
	

	public int getRowType() {
		return rowType;
	}
	
	

}
