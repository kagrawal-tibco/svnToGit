/**
 * 
 */
package com.tibco.cep.studio.debug.core.model.var;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.Field;
import com.sun.jdi.LongValue;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.runtime.model.element.PropertyAtomBoolean;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.PropertyAtomDouble;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.PropertyAtomString;
import com.tibco.cep.studio.debug.core.model.DebuggerConstants;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

/**
 * @author pdhar
 *
 */
public class ConceptPropertyAtomVariable extends ConceptPropertyVariable implements
		IVariable {
	
	protected String propValue;

	
	/**
	 * @param frame
	 * @param tinfo
	 * @param name
	 * @param value
	 * @param state
	 * @param statemachine_type
	 */
	public ConceptPropertyAtomVariable(
			RuleDebugStackFrame frame,
			RuleDebugThread tinfo, String name, Value value,
			String state, int statemachine_type) {
		super(frame, tinfo, null, name, value, statemachine_type);
		this.propValue = state;
	}
	
	/**
	 * @param frame
	 * @param tinfo
	 * @param name
	 * @param propRef
	 * @param rowTypeValues
	 */
	public ConceptPropertyAtomVariable(
			RuleDebugStackFrame frame,
			RuleDebugThread tinfo, String name, Value propRef,
			int rowTypeValues) {
		super(frame, tinfo, null, name, propRef, rowTypeValues);
		init();
	}
	
	@Override
	public void init() {
		if(getRowType() != ConceptPropertiesVariable.ROW_TYPE_STATEMACHINE) {
			try {
				ObjectReference propRef = (ObjectReference) getJdiValue();
				if (propRef == null)
					return;
				Value v;
				if (getRowType() == ConceptPropertiesVariable.ROW_TYPE_ARRAY_VALUES)
					v = DebuggerSupport.getConceptPropertyAtomValue(getThreadInfo(), propRef);
				else 
					v = getPropertyAtomValue(getThreadInfo(),propRef);
				if (v != null)
					this.propValue = v.toString();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void processChildren() {
		if(getRowType() != ConceptPropertiesVariable.ROW_TYPE_STATEMACHINE) {
			try {
			
				// Display History
				int histSize = DebuggerSupport.getConceptPropertyHistorySize(getThreadInfo(), (ObjectReference)getJdiValue()).intValue();
				List<IVariable> hChildren = new ArrayList<IVariable>();
				for (int h=0; h<histSize; h++) {
					long histTime = DebuggerSupport.getConceptPropertyAtomHistoryTime(getThreadInfo(), (ObjectReference)getJdiValue(), h).longValue();
					if (histTime==0)	// History info not available
						continue;
					ObjectReference histValueRef = DebuggerSupport.getConceptPropertyAtomHistoryValue(getThreadInfo(), (ObjectReference)getJdiValue(), h);
//					Value val = getPropertyAtomValue(getThreadInfo(),histValueRef);
					ConceptPropertyHistoryVariable propertyAtomHistChild = new ConceptPropertyHistoryVariable(getStackFrame(),getThreadInfo(), histTime, histValueRef, ConceptPropertiesVariable.ROW_TYPE_HISTORY);
					hChildren.add(propertyAtomHistChild);
				}
				setChildren(hChildren);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	
	@Override
	public String getValueString() throws DebugException {
		if(propValue != null ) {
			return propValue;
		}
		return super.getValueString();
	}
	

	/**
	 * @param eventThread
	 * @param propRef
	 * @return
	 * @throws DebugException
	 */
	private Value getPropertyAtomValue(RuleDebugThread eventThread,
			ObjectReference propRef) throws DebugException {
		if (DebuggerSupport.implementsInterface(propRef,
				PropertyAtomString.class.getName())) {
			return DebuggerSupport.getPropertyAtomString(eventThread,
					propRef);
		} else if (DebuggerSupport.implementsInterface(propRef,
				PropertyAtomInt.class.getName())) {
			return DebuggerSupport.getPropertyAtomInt(eventThread, propRef);
		} else if (DebuggerSupport.implementsInterface(propRef,
				PropertyAtomLong.class.getName())) {
			return DebuggerSupport
					.getPropertyAtomLong(eventThread, propRef);
		} else if (DebuggerSupport.implementsInterface(propRef,
				PropertyAtomDouble.class.getName())) {
			return DebuggerSupport.getPropertyAtomDouble(eventThread,
					propRef);
		} else if (DebuggerSupport.implementsInterface(propRef,
				PropertyAtomDateTime.class.getName())) {
			ObjectReference cref = (ObjectReference) DebuggerSupport
					.getPropertyAtomDateTime(eventThread, propRef);
			LongValue lval = (LongValue) DebuggerSupport
					.getCalendarTimeInMillis(eventThread, cref);
			String s = DebuggerSupport.formatAsTime(lval.longValue(),
					DebuggerConstants.DEBUGGER_DATE_FORMAT);
			return eventThread.getVM().mirrorOf(s);
		} else if (DebuggerSupport.implementsInterface(propRef,
				PropertyAtomBoolean.class.getName())) {
			return DebuggerSupport.getPropertyAtomBoolean(eventThread,
					propRef);
		} else if (DebuggerSupport.implementsInterface(propRef,
				PropertyAtomConceptReference.class.getName())) {
			return DebuggerSupport.getPropertyAtomConceptId(eventThread,
					propRef);
		} else if (DebuggerSupport.implementsInterface(propRef,
				PropertyAtomContainedConcept.class.getName())) {
			return DebuggerSupport.getPropertyAtomConceptId(eventThread,
					propRef);
		}
		return null;
	}
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private Value getPropertyValueFromVisibleFields() {
		List<Field> fields = ((ObjectReference)getJdiValue()).referenceType().visibleFields();
		for(Field f: fields) {
			if(f.name().startsWith(AbstractDebugVariable.PROPERTY_PREFIX_2) && f.name().endsWith(getName())) {
				Value v = ((ObjectReference)getJdiValue()).getValue(f);
				return v;
			}
		}
		return null;
	}

}
