package com.tibco.cep.studio.debug.core.model.var;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.Field;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class ProcessContextPropertiesVariable extends RuleDebugVariable {
	
	public static final int ROW_TYPE_PROPTYPES = 1; 	// PropertyAtom, PropertyArray
	public static final int ROW_TYPE_VALUES = 2; 		// Values
	public static final int ROW_TYPE_STATEMACHINE = 3; 	// StateMachine
	public static final int ROW_TYPE_HISTORY = 4; 		// History
	public static final int ROW_TYPE_ARRAY_VALUES = 5; 	// Array Values
	
	public static final String PROPERTIES_ROW_NAME = "Properties";
	private ObjectReference conceptRef;
	private int propCount;

	public ProcessContextPropertiesVariable(
			RuleDebugStackFrame frame,
			RuleDebugThread tinfo, String name, Value value) {
		super(frame, tinfo, name, value);
		setConceptRef((ObjectReference) value);
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
	
	/**
	 * @return the propCount
	 */
	public int getPropCount() {
		return propCount;
	}

	/**
	 * @param propCount the propCount to set
	 */
	public void setPropCount(int propCount) {
		this.propCount = propCount;
	}
	
	@Override
	public String getValueString() throws DebugException {
		return String.valueOf(getPropCount());
	}


	public void processChildren() {
		List<IVariable> vars = new ArrayList<IVariable>();

		try {
			Map<String,Value> propMap = DebuggerSupport.getProcessProperties(getThreadInfo(), getConceptRef());
			AbstractDebugVariable var = null;
			for (Entry<String, Value> prop : propMap.entrySet()) {
				var = null;
				ObjectReference propRef = (ObjectReference)prop.getValue();
				boolean isPropAtom = DebuggerSupport.isConceptPropertyAtom(getThreadInfo(), propRef);
				if (isPropAtom) {
					var = getPropertyAtomRow(prop);
				} else {
					var = getPropertyArrayRow(prop);
				} 
				if (var != null) {
					vars.add(var);
				}
				setPropCount(vars.size()); // Do it this way, so that only the displayed properties are counted
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setChildren(vars);	
	}
	
	
	private AbstractDebugVariable getPropertyAtomRow(Entry<String, Value> prop) throws Exception {
		AbstractDebugVariable var = null;
		String propname = prop.getKey();
		if (!DebuggerSupport.isConceptPropertyStateMachine(getThreadInfo(), (ObjectReference) prop.getValue())) {
			var = new ConceptPropertyAtomVariable(getStackFrame(),getThreadInfo(), propname, prop.getValue(), ROW_TYPE_VALUES);						
		} else if(DebuggerSupport.isConceptPropertyStateMachine(getThreadInfo(), (ObjectReference) prop.getValue())){
			// Handle StateMachine
			String conceptname = DebuggerSupport.getConceptName(getThreadInfo(), getConceptRef());
			propname = propname.substring(AbstractDebugVariable.PROPERTY_PREFIX_2.length()+conceptname.length()+1);
			ObjectReference smProperty = (ObjectReference) prop.getValue();
			if(!DebuggerSupport.implementsInterface(smProperty, Property.PropertyStateMachine.class.getName())) {
				return null;
			}
			ObjectReference smRef = null;
//			for(Field fld: smProperty.referenceType().visibleFields()) {
//				if(fld.name().equals("m_value")) {
//					Value m_value = smProperty.getValue(fld);
//					if(m_value != null) {
//						ObjectReference conceptOrRef = (ObjectReference) m_value;
//						if(DebuggerSupport.implementsInterface(conceptOrRef, StateMachineConcept.class.getName())) {
//							smRef = conceptOrRef;
//							break;
//						}
//					}
//				}
//			}
//			if (smRef != null) {
//				var = new StateMachinePropertyVariable(getStackFrame(),getThreadInfo(),propname,smRef);
//			}
//			String state = "";
//			// See if the SM is started or closed
//            HashMap<Field,Value> smprops = DebuggerSupport.getStateMachineStates(getThreadInfo(), smRef);
//            Set<Map.Entry<Field, Value>> smpropset = smprops.entrySet();
//            for (Map.Entry<Field, Value> smproppair : smpropset) {
//                Value smpropv = smproppair.getValue();
//                String smName = DebuggerSupport.getStateName(getThreadInfo(), (ObjectReference)smpropv);
//                boolean isActive = DebuggerSupport.isStateActive(getThreadInfo(), (ObjectReference)smpropv);
//                boolean isReady = DebuggerSupport.isStateReady(getThreadInfo(), (ObjectReference)smpropv);
//                boolean isExited = DebuggerSupport.isStateExited(getThreadInfo(), (ObjectReference)smpropv);
//                boolean isAmbiguous = DebuggerSupport.isStateAmbiguous(getThreadInfo(), (ObjectReference)smpropv);
//                boolean isTimeoutSet = DebuggerSupport.isStateTimeoutSet(getThreadInfo(), (ObjectReference)smpropv);
//                
//                if (isActive || isReady) {
//                    Field smpropf = smproppair.getKey();
//                    state = smpropf.name();
//                    if (state.startsWith(PROPERTY_PREFIX_2)) {
//                        state = state.substring(PROPERTY_PREFIX_2.length());
//                    }
//                }
//            }
//			var = new ConceptPropertyAtomVariable(getStackFrame(),getThreadInfo(), propname, prop.getValue(), state, ROW_TYPE_STATEMACHINE);
		}
		return var;
	}
	
	private AbstractDebugVariable getPropertyArrayRow(Entry<String, Value> prop) throws Exception {
		AbstractDebugVariable var;
		String propname = prop.getKey();
		var = new ConceptPropertyArrayVariable(getStackFrame(),getThreadInfo(), getConceptRef(), propname, prop.getValue(), ROW_TYPE_VALUES);
		return var;
	}
	
	

}
