/**
 * 
 */
package com.tibco.cep.studio.debug.core.model.var;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.IntegerValue;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.runtime.model.element.PropertyAtomConcept;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

/**
 * @author pdhar
 *
 */
public class ConceptPropertyArrayVariable extends ConceptPropertyVariable
		implements IVariable {
	
	protected String propValue;
	protected int arrayCount;

	/**
	 * @param target
	 * @param tinfo
	 * @param concept TODO
	 * @param name
	 * @param value
	 * @param type
	 */
	public ConceptPropertyArrayVariable(RuleDebugStackFrame frame,
			RuleDebugThread tinfo, ObjectReference concept, String name, Value value, int type) {
		super(frame, tinfo, concept, name, value, type);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the arrayCount
	 */
	public int getArrayCount() {
		return arrayCount;
	}



	/**
	 * @param arrayCount the arrayCount to set
	 */
	public void setArrayCount(int arrayCount) {
		this.arrayCount = arrayCount;
	}
	
	@Override
	public String getValueString() throws DebugException {
		return new Integer(getArrayCount()).toString();
	}



	@Override
	public void init() {
//		processChildren();
		StudioDebugCorePlugin.debug("************ Initialized using ConceptPropertyArrayRow Row *************");
	}
	
	@Override
	public void processChildren() throws DebugException {
		IntegerValue lenRef = DebuggerSupport.getConceptPropertyArrayLength(
				getThreadInfo(), (ObjectReference) getJdiValue());
		arrayCount = lenRef.intValue();
		List<IVariable> arrRows = new ArrayList<IVariable>();
		for (int i = 0; i < arrayCount; i++) {
			AbstractDebugVariable arrRow = null;
			ObjectReference arrValRef = DebuggerSupport
					.getConceptPropertyArrayValueAtom(getThreadInfo(),
							(ObjectReference) getJdiValue(), i);
			if (DebuggerSupport.isPropertyAtomConceptValue(getThreadInfo(), arrValRef)) {
				if (DebuggerSupport.implementsInterface(arrValRef, PropertyAtomConcept.class.getName())) {
					ObjectReference conReference = DebuggerSupport.getPropertyAtomConceptValue(getThreadInfo(), arrValRef);
					arrRow = new ConceptVariable(getStackFrame(),getThreadInfo(), conReference, "[" + i + "]", conReference);
				} 
			} else {
			    arrRow = new ConceptPropertyAtomVariable(
					getStackFrame(), getThreadInfo(), "[" + i + "]", arrValRef,
					ConceptPropertiesVariable.ROW_TYPE_ARRAY_VALUES);
			}
			arrRows.add(arrRow);
		}
		setChildren(arrRows);
	}
	

}
